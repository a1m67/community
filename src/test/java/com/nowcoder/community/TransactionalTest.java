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
}
