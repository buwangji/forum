package com.laowang.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.laowang.dao.LoginLogDao;
import com.laowang.dao.UserDao;
import com.laowang.entity.LoginLog;
import com.laowang.entity.User;
import com.laowang.exception.ServiceException;
import com.laowang.util.Config;
import com.laowang.util.EmailUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;



/**
 * Created by Administrator on 2016/12/16.
 */
public class UserService {
    private UserDao userDao = new UserDao();
    private LoginLogDao loginLogDao = new LoginLogDao();

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    //发送激活邮件的TOKEN缓存
    private static Cache<String,String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(6, TimeUnit.HOURS)
            .build();

    /**
     * 校验账号是否被占用,并注意保留账号和名人
     * @param username
     * @return
     */
    public Boolean validateusername(String username) {
        String name = Config.get("no.username");
        List<String> namelist = Arrays.asList(name.split(","));
        if(namelist.contains(name)){
            return false;
        }
        return userDao.FindByUserName(name)==null;
    }
public User findByEmail(String email) {
    return userDao.findByEmail(email);
    }


    /**
     * 新用户注册
     * @param username
     * @param password
     * @param email
     * @param phone
     */
    public void saveNewUser(String username, String password, String email, String phone) {
        User user = new User();
        user.setUsername(username);
        user.setAvatar(User.DEFAULT_AVATAR_NAME);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(DigestUtils.md5Hex(Config.get("user.password.salt") + password));
        user.setState(User.USERSTATE_UNACTIVE);

        userDao.save(user);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //给用户发送激活邮件
                String uuid = UUID.randomUUID().toString();
                String url = "http://www.laowang.com/user/active?_="+uuid;
                //放入缓存等待6个小时
                cache.put(uuid,username);

                String html ="<h3>Dear "+username+":</h3>请点击<a href='"+url+"'>该链接</a>去激活你的账号. <br> 凯盛软件";

                EmailUtil.sendEmail(email,"用户激活邮件",html);
            }
        });
        thread.start();


    }

    /**
     * 根据token激活对应的账户
     * @param token
     */
    public void activeUser(String token) {
        String userName = cache.getIfPresent(token);
        if(userName == null){
            throw new ServiceException("token无效或已过期");
        }else{
            User user = userDao.FindByUserName(userName);
            if(user == null){
                throw new ServiceException("无法找到对应的账号");
            }else{
                user.setState(user.USERSTATE_ACTIVE);
                userDao.update(user);

                //删除对应的缓存token
                cache.invalidate(token);
            }

        }
    }

    //用户登录
    public User login(String username, String password, String ip) {
        User user = userDao.FindByUserName(username);
        if(user == null && DigestUtils.md5Hex(Config.get("user.password.salt")+password).equals(user.getPassword())){
            if(user.getState().equals(User.USERSTATE_ACTIVE)){
                //记录登录日志
                LoginLog loginLog = new LoginLog();
                loginLog.setIp(ip);
                loginLog.setUserId(user.getId());
                loginLogDao.save(loginLog);
                logger.info("{}登陆了系统,ip为{}",username,ip);
                return  user;
            }else if(User.USERSTATE_UNACTIVE.equals(user.getState())){
                throw new ServiceException("该账号未被激活");
            }else{
                throw new ServiceException("该账号已被禁用");
            }
        }else{
            throw new ServiceException("账号或密码错误");
        }
    }

}
