package com.laowang.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;


/**
 * Created by Administrator on 2016/12/16.
 */

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static Logger logger = LoggerFactory.getLogger(StringUtils.class);

    /**
     * 转换字符串ISO8859-1--->UTF-8
     * @param str
     * @return
     */
    public static String IsoToUTF8(String str){
        try {
            return new String(str.getBytes("ISO8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("字符串{}转换异常",str);
            throw new RuntimeException("字符串"+str+"转换异常",e);
        }
    }

}
