package com.shenrun.p2pproject.user.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/14.
 * 实现序列化的原因是 我们从controller中传递到service的时候需要经过网络
 */
public class UserPojo implements Serializable {
    private String userId;
    private String userName;
    private String phoneNum;
    private String password;
    private String refererCode;
    private String myRefererCode;
    private String userTypeId;
    private String creditLevelId;
    private String creditLevelName;
    private Short userStatus;
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefererCode() {
        return refererCode;
    }

    public void setRefererCode(String refererCode) {
        this.refererCode = refererCode;
    }

    public String getMyRefererCode() {
        return myRefererCode;
    }

    public void setMyRefererCode(String myRefererCode) {
        this.myRefererCode = myRefererCode;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getCreditLevelId() {
        return creditLevelId;
    }

    public void setCreditLevelId(String creditLevelId) {
        this.creditLevelId = creditLevelId;
    }

    public String getCreditLevelName() {
        return creditLevelName;
    }

    public void setCreditLevelName(String creditLevelName) {
        this.creditLevelName = creditLevelName;
    }

    public Short getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Short userStatus) {
        this.userStatus = userStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserPojo{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", password='" + password + '\'' +
                ", refererCode='" + refererCode + '\'' +
                ", myRefererCode='" + myRefererCode + '\'' +
                ", usertyId='" + userTypeId + '\'' +
                ", creditLevelId='" + creditLevelId + '\'' +
                ", creditLevelName='" + creditLevelName + '\'' +
                ", userStatus=" + userStatus +
                ", email='" + email + '\'' +
                '}';
    }
}