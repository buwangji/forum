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
import com.laowang.util.StringUtils;
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
    //发送找回密码的token缓存
    private static Cache<String ,String> passwordCache = CacheBuilder.newBuilder()
            .expireAfterWrite(30,TimeUnit.MINUTES)
            .build();
    //限制操作频率的缓存
    private  static Cache<String ,String> sessionCache = CacheBuilder.newBuilder()
            .expireAfterWrite(59,TimeUnit.SECONDS)
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
        if(user != null && DigestUtils.md5Hex(Config.get("user.password.salt")+password).equals(user.getPassword())){
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

    /**
     * 找回密码
     * @param sessionid 客户端的session,限制客户端的操作频率
     * @param type 找回密码的方式
     * @param value 电子邮件或手机号码
     */
    public void foundpassword(String sessionid, String type, String value) {
        if(sessionCache.getIfPresent(sessionid) == null){
            if("phone".equals(type)){
                //TODO 根据手机号码找回密码
            }else if("email".equals(type)){
                User user = userDao.findByEmail(value);
                if(user != null){
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String uuid = UUID.randomUUID().toString();
                            String url = "http://www.laowang.com/foundpassword/newpassword?token=" + uuid;

                            passwordCache.put(uuid,user.getUsername());
                            String html = user.getUsername()+"<br>请点击该<a href='"+url+"'>链接</a>进行找回密码操作，链接在30分钟内有效";
                            EmailUtil.sendEmail(value,"密码找回邮件",html);
                        }
                    });
                    thread.start();
                }
            }
            sessionCache.put(sessionid,"提交频率");
        }else{
            throw new ServiceException("操作频率过快,请稍后再试");
        }


    }

    /**
     * 根据链接中的token来寻找用户
     * @param token 找回密码需要的token
     * @return
     */
    public User foundpasswordbytoken(String token) {
        String username = passwordCache.getIfPresent(token);
        if(StringUtils.isEmpty(username)){
            throw new ServiceException("token过期无效");
        }else {
            User user = userDao.FindByUserName(username);
            if(user == null){
                throw new ServiceException("无法找到该账号");
            }else{
                return user;
            }

        }

    }

    /**
     * 根据链接中的token来重置密码
     * @param id 用户id
     * @param token 链接中的token
     * @param password 新密码
     */
    public void resetPassword(String id, String token, String password) {
        if(passwordCache.getIfPresent(token) == null){
            throw new ServiceException("token过期无效");
        }else{
            User user = userDao.findByid(Integer.valueOf(id));
            user.setPassword(DigestUtils.md5Hex(Config.get("user.password.salt")+password));
            userDao.update(user);
            //删除token
            passwordCache.invalidate(token);
            logger.info("{}重置了密码",user.getUsername());
        }
    }

    /**
     * 修改用户的邮箱
     * @param user
     * @param email
     */
    public void updateEmail(User user, String email) {
        user.setEmail(email);
        userDao.update(user);
    }

    /**
     * 修改用户的密码
     * @param user
     * @param oldPassword
     * @param newPassword
     */
    public void updatePaasword(User user, String oldPassword, String newPassword) {
        String salt = Config.get("user.password.salt");
        if(DigestUtils.md5Hex(salt + oldPassword).equals(user.getPassword())) {
            newPassword = DigestUtils.md5Hex(salt + newPassword);
            user.setPassword(newPassword);
            userDao.update(user);
        } else {
            throw new ServiceException("原始密码错误");
        }

    }


}
