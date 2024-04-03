package com.nowcoder.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
// 开启定时任务，默认不开
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}
