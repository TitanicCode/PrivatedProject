package com.shenrun.p2pproject.user.controller;

import com.shenrun.p2pproject.user.pojo.UserPojo;
import com.shenrun.p2pproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/7/14.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * @param user
     * @param request
     * @param piccode 图片验证码
     * @param phocode 手机验证码
     * @param usertypeid 用户的类型 1 是投资 2是借款
     * @return
     */
    //TODO 返回值问题 传入参数问题
    @ResponseBody
    @RequestMapping("/register")
    public void register(UserPojo user, HttpServletRequest request, String phocode, String piccode, String usertypeid){
        Cookie[] cookies = request.getCookies();
        userService.checkRegister(user,usertypeid,cookies,phocode,piccode);
    }
}
