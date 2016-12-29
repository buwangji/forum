package com.laowang.service;

import com.laowang.dao.AdminDao;
import com.laowang.entity.Admin;
import com.laowang.exception.ServiceException;
import com.laowang.util.Config;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/12/28.
 */
public class AdminService {
    Logger logger = LoggerFactory.getLogger(AdminService.class);
    AdminDao adminDao = new AdminDao();

    /**
     * 管理员登录
     * @param adminName
     * @param password
     * @param ip
     * @return
     */
    public Admin login(String adminName, String password, String ip) {
        Admin admin = adminDao.findAdminByName(adminName);
        if(admin != null && admin.getPassword().equals(DigestUtils.md5Hex(Config.get("user.password.salt")+password))){
            logger.debug("管理员{}登录了后台管理系统,IP为:{}",adminName,ip);
            return admin;
        }else{
            throw new ServiceException("账号或密码错误");
        }
    }


}
