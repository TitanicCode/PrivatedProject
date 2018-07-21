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
     * @param userPojo
     * @throws Exception 如果抛出异常，表示插入失败
     */
    //校验注册信息
    ResultData checkRegister(UserPojo user,String userTypeId,Cookie[] cookies,String phoCode,String picCode);
    void InsertUser(UserPojo userPojo)throws Exception;
}
