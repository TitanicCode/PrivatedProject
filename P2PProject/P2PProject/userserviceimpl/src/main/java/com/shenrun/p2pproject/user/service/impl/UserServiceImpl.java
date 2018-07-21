package com.shenrun.p2pproject.user.service.impl;

import com.common.constantcode.ConstantCodeUtil;
import com.common.dto.ResultData;
import com.common.redis.client.JedisClient;
import com.shenrun.p2pproject.user.mapper.UserMapper;
import com.shenrun.p2pproject.user.pojo.UserPojo;
import com.shenrun.p2pproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;

/**
 * Created by Administrator on 2018/7/14.
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;

    /**
     * 注册用户
     * @param userTypeId
     * @param cookies
     * @param userPojo
     * @throws Exception 如果抛出异常，表示插入失败
     */
    //校验注册信息,要注意，如果信息对了就不需要返回什么，信息错了就可以返回错误直接结束方法了，所以最好判断如果不合法，就返回错误。
    @Override
    public ResultData checkRegister(UserPojo userPojo,String userTypeId, Cookie[] cookies,String phoCode,String picCode) {

        //验证用户类型：借款or投资
        String type = userMapper.selectUserTypeId(Integer.parseInt(userTypeId));
        userPojo.setUserTypeId(type);
        //加热数据思想

        phone//cookies问题

        //手机验证码验证
        //获取用户输入的手机号码
        String inputPhoneNum = userPojo.getPhoneNum();
        if (inputPhoneNum==null||inputPhoneNum.equalsIgnoreCase("")){
           return ResultData.setError(ConstantCodeUtil.ERROR,"请输入正确手机号",null);
        }
        String phoCodeServe = jedisClient.get(inputPhoneNum, jedisClient.getJedis());
        if (!phoCode.equalsIgnoreCase(phoCodeServe)){
            return ResultData.setError(ConstantCodeUtil.PHONECODEERROR,"验证码错误",null);
        }

        //图片验证码验证
        for (Cookie cookie:cookies) {
            if(cooki)
        }



    }


        return null;
    }

    @Override
    public void InsertUser(UserPojo userPojo) throws Exception{
        userMapper.InsertUser(userPojo);
    }
}
