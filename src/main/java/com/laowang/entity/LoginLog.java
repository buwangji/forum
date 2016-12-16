package com.laowang.entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/12/15.
 */
public class LoginLog {
    private Integer id;
    private Timestamp logintime;
    private String ip;
    private Integer username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getLogintime() {
        return logintime;
    }

    public void setLogintime(Timestamp logintime) {
        this.logintime = logintime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }
}
