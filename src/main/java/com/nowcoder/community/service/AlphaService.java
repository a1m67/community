package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;


//使用容器管理bean
@Service
//@Scope("prototype") 较少使用
public class AlphaService {
    private static final Logger logger = LoggerFactory.getLogger(AlphaService.class);

    @Autowired
    AlphaDao alphaDao;

    //杜撰业务，要在创建用户后自动发一个帖子
    @Autowired
    UserMapper userMapper;

    @Autowired
    DiscussPostMapper discussPostMapper;

    @Autowired
    TransactionTemplate transactionTemplate;


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

    //事务注解
    //isolation 是隔离等级、propagation是事务传播机制
    //常用的事务传播机制有
    // REQUIRED : 支持当前事务（外部事物（调用者的事务）），如果不存在则创建新事务
    // REQUIRES NEW : 创建一个新事务，并且暂停当前事务(外部事务)
    // NESTED : 如果当前存在事务（外部事物），则嵌套在该事物中执行(独立的提交和回滚)，否则等同于REQUIRED
    @Transactional(isolation = Isolation.READ_COMMITTED , propagation = Propagation.REQUIRED)
    //声明式事务
    public Object save1(){
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5( "123"+user.getSalt()));
        user.setEmail("alpha@qq.com");
        user.setHeaderUrl("http://images.nowcoder.com/head/20t.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);


        //新增帖子
        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(user.getId());
        discussPost.setTitle("Hello NowCoder");
        discussPost.setContent("新人报道");
        discussPost.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(discussPost);


        Integer.valueOf("abc");

        return "ok";
    }


    //编程式
    public Object save2(){
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                User user = new User();
                user.setUsername("beta");
                user.setSalt(CommunityUtil.generateUUID().substring(0,5));
                user.setPassword(CommunityUtil.md5( "123"+user.getSalt()));
                user.setEmail("beta@qq.com");
                user.setHeaderUrl("http://images.nowcoder.com/head/80t.png");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);


                //新增帖子
                DiscussPost discussPost = new DiscussPost();
                discussPost.setUserId(user.getId());
                discussPost.setTitle("你好牛客");
                discussPost.setContent("newCoder Coming");
                discussPost.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(discussPost);


                Integer.valueOf("abc");

                return "ok";
            }
        });
    }

    //该让方法在多线程环境下，被异步调用
    @Async
    public void execute1() {
        logger.debug("execute1");
    }

    @Scheduled(initialDelay = 10000, fixedRate = 1000)
    public void execute2() {
        logger.debug("execute2");
    }
    
    //insert into user(id,username,password,salt,email,type,status,activation_code,header_url,create_time)VALUES('163','ab163','6d80496f52255efa7a722f388e9d2324','afe62','nowcoder163@sina.com',0,1,'','http://images.nowcoder.com/head/677t.png','2019-04-06 21:57:34');
}
