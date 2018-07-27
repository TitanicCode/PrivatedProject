package com.shenrun.p2pproject.user.service;

import com.common.dto.ResultData;
import com.shenrun.p2pproject.user.pojo.UserPojo;

import javax.servlet.http.Cookie;

/**
 * Created by Administrator on 2018/7/14.
 */
public interface UserService {
    /**
     * 注册用户
     * @param userTypeId
     * @param value
     * @param userPojo
     * @param phoCode
     * @param picCode
     * @throws Exception 如果抛出异常，表示插入失败
     */
    //校验注册信息
    ResultData checkRegister(UserPojo userPojo,String userTypeId,String value,String phoCode,String picCode,String confirmPassword);
    void insertUser(UserPojo userPojo,String userTypeId)throws Exception;
    ResultData findUserByUserNameAndPasswordAndUserStatus(String userName,String password);
}
