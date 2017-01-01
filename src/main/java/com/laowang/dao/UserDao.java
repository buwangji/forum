package com.laowang.dao;

import com.laowang.entity.LoginLog;
import com.laowang.entity.User;
import com.laowang.util.DbHelp;
import com.laowang.util.Page;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */
public class UserDao {

    public User FindByUserName(String name) {
        String sql = "select * from t_user where username = ?";
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


    public void update(User user) {
        String sql = "update t_user set password=? , email=? , phone=? , state=? , avatar=? where id = ?";
        DbHelp.update(sql,user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar(),user.getId());
    }

    public User findByid(Integer integer) {
        String sql = "select * from t_user where id = ?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),integer);
    }

    public Integer count() {
        String sql = "select count(*) from t_user where state != 0 order by id";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public List<User> FindAllUser(Page<User> page) {
        String sql = "select tl.logintime ,MAX(tl.logintime),tl.ip , tu.* from t_user tu ,t_login_log tl where tu.id=tl.userid  and tu.state != 0  GROUP BY tu.id limit ?,?";
        return DbHelp.query(sql, new AbstractListHandler<User>() {
            @Override
            protected User handleRow(ResultSet resultSet) throws SQLException {
                User user = new BasicRowProcessor().toBean(resultSet,User.class);
                LoginLog loginLog = new LoginLog();
                loginLog.setLoginTime(resultSet.getTimestamp("logintime"));
                loginLog.setIp(resultSet.getString("ip"));
                user.setLoginLog(loginLog);
                return user;
            }
        }, page.getStart(), page.getPageSize());
    }
}
