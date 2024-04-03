package com.nowcoder.community;

import com.nowcoder.community.CommunityApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//指定配置类
@ContextConfiguration(classes = CommunityApplication.class)
public class QuartzTests {
    @Autowired
    private Scheduler scheduler;

    @Test
    public void  testDeleteJob() {
        try {
            boolean isDelete = scheduler.deleteJob(new JobKey("alphaJob","alphaJobGroup"));
            System.out.println(isDelete);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
