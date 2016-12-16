package com.laowang.dao;

import com.laowang.entity.User;
import com.laowang.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by Administrator on 2016/12/15.
 */
public class UserDao {

    public Object FindByUserName(String name) {
        String sql = "select*from t_user where username = ?";
        return DbHelp.query(sql,new BeanHandler<>(User.class),name);
    }

    public User findByEmail(String email) {
        String sql = "select * from t_user where email = ?";
        return DbHelp.query(sql,new BeanHandler<>(User.class),email);
    }
    public void save(User user) {
        String sql = "insert into t_user(username, password, email, phone, state, avatar) VALUES (?,?,?,?,?,?)";
        DbHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar());
    }

}
