package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


//使用容器管理bean
@Service
//@Scope("prototype") 较少使用
public class AlphaService {

    @Autowired
    AlphaDao alphaDao;

//    @Autowired
//    UserMapper userMapper;

    public AlphaService(){
        System.out.println("实例化AlphaService");
//        System.out.println(userMapper.selectById(101));
    }


    //在构造器之后调用方法的注解
    @PostConstruct
    public void init(){
        System.out.println("初始化AlphaService");
    }

    //销毁前调用
    @PreDestroy
    public void destroy(){
        System.out.println("销毁AlphaService");
    }

    public String select(){
        return alphaDao.select();
    }

}
