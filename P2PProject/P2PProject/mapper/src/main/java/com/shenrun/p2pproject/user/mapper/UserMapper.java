package com.shenrun.p2pproject.user.mapper;

import com.shenrun.p2pproject.user.pojo.UserPojo;

/**
 * Created by Administrator on 2018/7/16.
 */
public interface UserMapper {
    void insertUser(UserPojo userPojo);
    String selectUserTypeId(int type);
}
