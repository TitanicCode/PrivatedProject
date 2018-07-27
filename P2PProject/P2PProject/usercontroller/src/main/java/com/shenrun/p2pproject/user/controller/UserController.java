package com.shenrun.p2pproject.user.controller;

import com.common.constantcode.ConstantCodeUtil;
import com.common.dto.ResultData;
import com.common.redis.client.JedisInterface;
import com.shenrun.p2pproject.user.pojo.UserPojo;
import com.shenrun.p2pproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/14.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JedisInterface jedisClient;

    //注册
    /**
     * @param user
     * @param confirmpassword
     * @param request
     * @param piccode 图片验证码
     * @param phocode 手机验证码
     * @param type 用户的类型 1 是投资 2是借款
     * @return
     */
    @ResponseBody
    @RequestMapping("/register")
    public ResultData register(UserPojo user, HttpServletRequest request, String phocode, String piccode, String type,String confirmpassword){
        Cookie[] cookies = request.getCookies();
        if (cookies==null){
            return ResultData.setError(ConstantCodeUtil.PICCODEERROR,"拒绝访问，浏览器cookies设置异常",null);
        }
        String value=null;
        for (Cookie cookie:cookies) {
            if(cookie.getName().equalsIgnoreCase("suibian")){
                value = cookie.getValue();
            }
                break;
        }
        ResultData resultData = userService.checkRegister(user, type, value, phocode, piccode, confirmpassword);

        if (resultData.getData()!=null){
            try {
                userService.insertUser(user,type);
                return ResultData.setOk(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //返回成功的语句不能放在这里，因为try-catch执行顺序是：捕获异常时，try内语句不再往下执行，但try-catch下面的语句会继续执行，所以如果插入语句出错，再返回成功就不对了
            //return ResultData.setOk(null);
            return resultData;
        }else{
            return resultData;
        }

    }

    //登录
    @ResponseBody
    @RequestMapping("/login")
    public ResultData userLogin(String userName, String password, HttpServletResponse response) throws Exception {
        ResultData loginCheck = userService.findUserByUserNameAndPasswordAndUserStatus(userName, password);
        UserPojo user = (UserPojo) loginCheck.getData();
        //如果登陆成功，则将数据存储到redis,目的是当我们是分布式或者集群的时候在其他机器可以同步获取到用户的相关信息
        if (user!=null){
            //TODO 通过修改缓存中用户密码的值来确定用户是否设置过支付密码 0代表没有设置,1代表设置了
            //user.setPayPassword(user.getPayPassword() == null ? "0" : "1");

            Jedis jedis=jedisClient.getJedis();
            //这里要把user登录信息存入jedis，但是总不能把密码存进去吧，此时可以使用注解解决这一问题
            //在userpojo类中的密码属性添加注解，然后在jedis存信息的时候解析注解就可以实现了。
            //本程序使用的是注解SkipRedis
            jedisClient.hSet(user.getUserId()+"loginInfo",user,jedis);
            jedis.expire(user.getUserId()+"loginInfo",60*60*24*30);
            jedis.close();

            //把key放到cookie中给用户返回
            Cookie cookie=new Cookie("loginInfo",user.getUserId()+"loginInfo");
            //应该设置过期时间,和redis的过期时间同步即可
            cookie.setMaxAge(60*60*24*30);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return loginCheck;
    }

    /**
     * 获取用户信息,用于在页面上显示用户信息的,以免显示未登录,需要登录
     * 操作流程 因为明文已经将用户的信息放到redis中,然后将用户的id放入到了cookie中,所以下次用户请求的时候会带着cookie过来,然后我们就可以获取到cookie去redis中查询,然后给页面返回,由页面显示
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserInfo")
    public ResultData getUserInfo(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String value=null;
        String userId=null;

        if (cookies!=null||!cookies.equals("")){
            for (Cookie cookie:cookies) {
                if(cookie.getName().equals("loginInfo")){
                    value=cookie.getValue();
                    userId = value.substring(0, value.lastIndexOf("loginInfo"));
                    break;
                }
            }
            if (value!=null||!value.equalsIgnoreCase("")) {
                Jedis jedis = jedisClient.getJedis();
                String userName=jedisClient.hGet(value,"userName",jedis);
                jedis.close();
                if (userName!=null||!userName.equalsIgnoreCase("")){
                    Map<String,String> map=new HashMap<>();
                    map.put("userName",userName);
                    map.put("userId",userId);
                    return ResultData.setOk(map);
                }
            }
        }else{
            return ResultData.setError(ConstantCodeUtil.ERROR,"登录超时或未登录",null);
        }
        return null;
    }
}
