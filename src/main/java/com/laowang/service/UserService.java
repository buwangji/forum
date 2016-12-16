package com.laowang.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.laowang.dao.UserDao;
import com.laowang.entity.User;
import com.laowang.util.Config;
import com.laowang.util.EmailUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.sun.corba.se.impl.util.RepositoryId.cache;

/**
 * Created by Administrator on 2016/12/16.
 */
public class UserService {
    private UserDao userDao = new UserDao();
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
}
