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
public class CommunityApplicationTests implements ApplicationContextAware {

	@Test
	public void contextLoads() {
	}
	private ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Test
	public void testApplication(){
		System.out.println(applicationContext);

		//获取bean
		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
		System.out.println(alphaDao.select());

		alphaDao = (AlphaDao) applicationContext.getBean("AlphaHibernateImpl");
		System.out.println(alphaDao.select());
	}


	@Test
	public void testBeanManagement(){
		AlphaService alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);

		alphaService = applicationContext.getBean(AlphaService.class);
		System.out.println(alphaService);
	}

	//装配第三方bean
	@Test
	public void testBeanConfig(){
		System.out.println(new Date());

		SimpleDateFormat simpleDateFormat =
				applicationContext.getBean(SimpleDateFormat.class);

		System.out.println(simpleDateFormat.format(new Date()));


	}


	@Autowired
	AlphaService alphaService;

	@Autowired
	//用于指定类名注入
	@Qualifier("AlphaHibernateImpl")
	AlphaDao alphaDao;
	@Autowired
	SimpleDateFormat simpleDateFormat;
	@Test
	public void DependencyInject(){

		System.out.println(alphaDao);
		System.out.println(alphaService);
		System.out.println(simpleDateFormat);
	}



}
