package com.laowang.dao;

import com.laowang.entity.LoginLog;
import com.laowang.util.DbHelp;

/**
 * Created by Administrator on 2016/12/17.
 */
public class LoginLogDao {


    public void save(LoginLog loginLog) {
        String sql = "insert into t_login_log(id,userid) values(?,?)";
        DbHelp.update(sql,loginLog.getId(),loginLog.getUserId());
    }
}
