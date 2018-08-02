package com.shenrun.p2pproject.user.pojo;

import com.common.annotation.SkipRedis;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/14.
 * 实现序列化的原因是 我们从controller中传递到service的时候需要经过网络
 */
public class UserPojo implements Serializable {
    private String userId;
    private String userName;
    private String phoneNum;
    @SkipRedis
    private String password;
    private String refererCode;
    private String myRefererCode;
    private String userTypeId;
    private String creditLevelId;
    private String creditLevelName;
    private String userStatus;
    private String email;
    private String userTypeName;
    private String nickName;
    private String realName;
    private String idNum;
    private Integer emailStatus;
    private Integer realNameVerifiedStatus;
    private Integer sceneVerifiedStatus;
    private String payPassword;

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

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public Integer getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(Integer emailStatus) {
        this.emailStatus = emailStatus;
    }

    public Integer getRealNameVerifiedStatus() {
        return realNameVerifiedStatus;
    }

    public void setRealNameVerifiedStatus(Integer realNameVerifiedStatus) {
        this.realNameVerifiedStatus = realNameVerifiedStatus;
    }

    public Integer getSceneVerifiedStatus() {
        return sceneVerifiedStatus;
    }

    public void setSceneVerifiedStatus(Integer sceneVerifiedStatus) {
        this.sceneVerifiedStatus = sceneVerifiedStatus;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
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
                ", userTypeId='" + userTypeId + '\'' +
                ", creditLevelId='" + creditLevelId + '\'' +
                ", creditLevelName='" + creditLevelName + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", email='" + email + '\'' +
                ", userTypeName='" + userTypeName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", idNum='" + idNum + '\'' +
                ", emailStatus=" + emailStatus +
                ", realNameVerifiedStatus=" + realNameVerifiedStatus +
                ", sceneVerifiedStatus=" + sceneVerifiedStatus +
                ", payPassword='" + payPassword + '\'' +
                '}';
    }
}
