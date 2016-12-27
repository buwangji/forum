package com.laowang.entity;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/12/27.
 */
public class Notify {
    private Integer id;
    private Integer userid;
    private String content;
    private Timestamp createtime;
    private Integer state;
    private Timestamp readtime;
    public static  final Integer NOTIFY_STATE_UNREAD = 0;
    public static  final Integer NOTIFY_STATE_READ = 1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Timestamp getReadtime() {
        return readtime;
    }

    public void setReadtime(Timestamp readtime) {
        this.readtime = readtime;
    }

    public static Integer getNotifyStateUnread() {
        return NOTIFY_STATE_UNREAD;
    }

    public static Integer getNotifyStateRead() {
        return NOTIFY_STATE_READ;
    }
}
