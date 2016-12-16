package com.laowang.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2016/12/16.
 */
public class Config {

    public static Properties properties = new Properties();
    static{
        try {
            properties.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("读取config.properties文件异常",e);
        }
    }
    public static String get(String key){
        return properties.getProperty(key);

    }
}
