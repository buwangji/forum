package com.laowang.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/12/16.
 */
public class EmailUtil {

    private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);
    public static void sendEmail(String toAddress,String subject,String conte){
        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName(Config.get("email.smpt"));
        htmlEmail.setSmtpPort(Integer.valueOf(Config.get("email.port")));
        htmlEmail.setAuthentication(Config.get("email.username"),Config.get("email.password"));
        htmlEmail.setCharset("UTF-8");
        htmlEmail.setStartTLSEnabled(true);

        try {
            htmlEmail.setFrom(Config.get("email.frommail"));
            htmlEmail.setSubject(subject);
            htmlEmail.setHtmlMsg(conte);
            //htmlEmail.setHtmlMsg(context);
            htmlEmail.addTo(toAddress);
            htmlEmail.send();

        } catch (EmailException e) {
            logger.error("向{}发送邮件失败",toAddress);
            throw new RuntimeException("发送邮件失败:" + toAddress);
        }

    }


}
