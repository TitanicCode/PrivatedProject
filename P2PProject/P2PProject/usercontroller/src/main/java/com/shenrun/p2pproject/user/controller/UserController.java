package com.shenrun.p2pproject.user.controller;

import com.common.constantcode.ConstantCodeUtil;
import com.common.dto.ResultData;
import com.common.redis.client.JedisInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenrun.p2pproject.user.pojo.UserPojo;
import com.shenrun.p2pproject.user.service.UserService;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    @Autowired
    private RabbitTemplate rabbitTemplate;


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

    //更新用户信息
    public ResultData updateUser(UserPojo user) throws JsonProcessingException {
        int updateInt = userService.updateUser(user);
        if (updateInt==1){
            //更新成功
            //问题,缓存中的用户信息怎么办,缓存的一致性问题,因为我们修改了数据库,缓存需要同步更新,更新的方式,如何更新?
            //链接redis 更新一下就ok了
            //产生新的问题,如果做的事情特别多,消耗的时间很长,用户那边可能会超时,但是我们这边操作继续,超时后用户可能会刷新重新提交,那么要解决幂等性问题
            //所以综上考虑,不应该在代码中直接链接redis,而是想办法放到别的地方执行
            //可以使用消息服务,即数据库更新完成后直接把结果告诉用户，而其他诸如更新缓存等服务器方需要做的业务通过消息告诉相关机器，让他们慢慢处理，如果处理失败，那么我们就通过后续补偿通知用户
            //发送方,用于告诉接收方,你可以做什么事情了，按照预先定义好的规则发送消息给另外一个机器更新操作
            //MQ message queue 消息队列(数据结构,数组,链表,栈,队列,图,树...)
            //此处更新用户资料成功后,发送一条消息给所有需要同步更新数据的地方,让他们接收到之后在自己内部处理更新
            //将消息发送到消息服务器有两种方式：
            //方式1 将user对象直接转成json（转成json实质就是把对象转成字符串）发过去,但是注意这个user对象里面的属性不全,因为用户没有更改的属性是没有值的,但是原先可能有值
            //所以如果使用方式一，你传递过去之后，在那边你需要判断对应属性是否为null，如果是null，那么该属性就不能更新。
            //这种方式效率相对高，但此方式不建议发送大量的数据，因为mq（消息队列）对大数据传输不友好
            //方式2 发送user的id过去,然后发送操作标记(描述要执行什么操作的)，告诉消息服务机器对哪个对象执行什么样的操作
            //例如约定type代表执行的操作的类型,当type==2时，表示执行更新；data代表操作需要的数据，当data=1时，表示需要对id为2的用户执行type指定的操作
            //{"type":2,"data":"主键"}
            Map<String,String> map=new HashMap<>();
            map.put("type","2");
            map.put("data",user.getUserId());
            ObjectMapper objectMapper=new ObjectMapper();
            String jsonUpdateMq = objectMapper.writeValueAsString(map);
            rabbitTemplate.convertAndSend(jsonUpdateMq);

            return ResultData.setOk(null);//更新成功.主要是成功,不一定非得显示其他信息
        }
        //返回错误数据
        return ResultData.setError("更新失败");//如果可以返回具体失败信息最好
}


    @ResponseBody
    @RequestMapping("/testrabbit")
    public String testRabbit() {
        rabbitTemplate.convertAndSend("我就不信邪了,再叽歪,弄死你,我本是天庭的玉皇大帝");
        return "不拖延,做了再说";
    }
}
