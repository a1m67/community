package com.nowcoder.community;

import com.nowcoder.community.dao.*;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.Message;
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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
//指定配置类
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    DiscussPostMapper discussPostMapper;

    @Autowired
    LoginTicketMapper loginTicketMapper;

    @Autowired
    MessageMapper messageMapper;
    @Test
    public void testSelectUser() {
        User user = userMapper.selectById(101);
        System.out.println(user);

        user = userMapper.selectByName("liubei");
        System.out.println(user);

        user = userMapper.selectByEmail("nowcoder25@sina.com");
        System.out.println(user);
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());

        int row = userMapper.insertUser(user);
        System.out.println(row);
        System.out.println(user.getId());

    }

    @Test
    public void updateUser() {
       int row = userMapper.updateStatus(151,1);
       System.out.println(row);

       row = userMapper.updateHeader(151,"http://www.nowcoder.com/100.png");
       System.out.println(row);

       row = userMapper.updatePassword(151,"hell0");
       System.out.println(row);

    }



    @Test
    public void testSelectPosts(){
        List<DiscussPost> list =discussPostMapper.selectDiscussPosts(149,0,10,0);
        for (DiscussPost post : list){
            System.out.println(post);
        }

        int rows = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(rows);
    }

    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(100);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(1);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectByTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

        loginTicketMapper.updateStatus("abc",0);
        System.out.println(loginTicketMapper.selectByTicket("abc"));
    }

    @Test
    public void testSelectMessage(){
        List<Message> list = messageMapper.selectConversations(111,0,20);
        for (Message message:list){
            System.out.println(message);
        }

        System.out.println(messageMapper.selectConversationCount(111));

        list =messageMapper.selectLetters("111_112",0,10);
        for (Message message:list){
            System.out.println(message);
        }

        System.out.println(messageMapper.selectLetterCount("111_112"));

        System.out.println(messageMapper.selectLetterUnreadCount(131,"111_131"));
    }

}
