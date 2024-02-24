package com.nowcoder.community.util;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.logging.Logger;

@Component
public class MailClient {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MailClient.class);
    //发邮件通过这个类实现
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;
    //subject主题
    public void sendMail(String to, String subject, String content){
        try {
            //发送邮件主要通过MimeMessage
            MimeMessage message = mailSender.createMimeMessage();
            //Spring提供了邮件助手
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            //发送html类型的
            helper.setText(content,true);

            mailSender.send(helper.getMimeMessage());
        } catch (Exception e) {
            logger.error("发送邮件失败"+e.getMessage());
        }


    }


}
