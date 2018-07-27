package com.shenrun.p2pproject.user.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/26.
 */
public class UserType implements Serializable {
    private String userTypeId;
    private String userTypeName;
    private String type;

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "userTypeId='" + userTypeId + '\'' +
                ", userTypeName='" + userTypeName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
