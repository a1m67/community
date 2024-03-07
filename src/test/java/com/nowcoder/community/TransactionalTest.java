package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
//指定配置类
@ContextConfiguration(classes = CommunityApplication.class)
public class TransactionalTest {

    @Autowired
    AlphaService alphaService;

    @Test
    public void  testSave1(){
        String obj =(String) alphaService.save1();
        System.out.println(obj);
    }

    @Test
    public void  testSave2(){
        String obj =(String) alphaService.save2();
        System.out.println(obj);
    }

    @Test
    public void insertintouser(){
        for (int i=130;i<131;i++){
            System.out.println("insert into user(id,username,password,salt,email,type,status,activation_code,header_url,create_time)VALUES('"+i+"','ab"+i+"','6d80496f52255efa7a722f388e9d2324','afe62','nowcoder+"+i+"@sina.com',0,1,'','http://images.nowcoder.com/head/677t.png','2019-04-06 21:57:34');");
        }
    }

}
