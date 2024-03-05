package com.nowcoder.community;
import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SensitiveTests {
    @Autowired
    SensitiveFilter sensitiveFilter;

    @Test
    public void testFilter(){
        String text = "我要嫖娼我爱赌博我像吸毒想打架斗殴";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

        text = "我☹要嫖娼我爱×赌博我像☹☜吸毒想×♀♠打架斗殴";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }
}
