package com.laowang.dao;

import com.laowang.entity.Admin;
import com.laowang.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by Administrator on 2016/12/28.
 */
public class AdminDao {
    public Admin findAdminByName(String adminName) {
        String sql = "select * from admin where adminname = ?";
        return DbHelp.query(sql,new BeanHandler<Admin>(Admin.class),adminName);
    }
}
