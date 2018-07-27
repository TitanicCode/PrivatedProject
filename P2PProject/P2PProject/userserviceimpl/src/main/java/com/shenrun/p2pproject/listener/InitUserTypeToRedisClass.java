package com.shenrun.p2pproject.listener;

import com.common.redis.client.JedisInterface;
import com.shenrun.p2pproject.user.mapper.UserMapper;
import com.shenrun.p2pproject.user.pojo.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by Administrator on 2018/7/26.
 */
public class InitUserTypeToRedisClass {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisInterface jedisClient;


    public void InitUserType() throws Exception {

        List<UserType> userTypes = userMapper.selectAllUserType();
        for (UserType userType : userTypes) {
            Jedis jedis = jedisClient.getJedis();
            //这个地方最好是以userTypeId为键，因为这个值在userpojo中存在冗余数据，使用起来更方便，可以使用userpojo调用usertype的属性
            jedisClient.hSet(userType.getUserTypeId(),userType,jedis);
            jedis.close();
        }
    }
}
