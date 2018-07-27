package com.shenrun.p2pproject.user.mapper;

import com.shenrun.p2pproject.user.pojo.UserPojo;
import com.shenrun.p2pproject.user.pojo.UserType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/7/16.
 */

public interface UserMapper {
    void insertUser(UserPojo userPojo);

    String selectUserTypeId(int type);

    //因为是两个参数输入sql语句，所以需要加mabits的注解
    //根据用户名和密码查询用户,主要用于登录操作,实际上如果你的用户体量非常非常大,比如阿里,腾讯 用户信息一般都在缓存中,这样查询会比较快
    UserPojo selectByUserNameAndPasswordAndUserStatus(@Param("userName") String userName,@Param("password") String password);

    //数据预热查询userType,这个数据是通过预热以及缓存同步机制放到redis中的,不会出现单独查询某个的情况,所以此处没有条件
    List<UserType> selectAllUserType();
}
