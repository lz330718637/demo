package com.springboot.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springboot.demo.pojo.primary.FirstUser;
import com.springboot.demo.pojo.primary.FirstUserRepository;
import com.springboot.demo.pojo.secondary.SecondUserRepository;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { DemoApplication.class })
public class DemoApplicationTests {

	@Autowired
	FirstUserRepository user1Repository;
	@Autowired
	SecondUserRepository user2Repository;
	
	@Autowired
	RedisTemplate<Object,Object> redisTemplate;

	@Test
	public void text() {

		// 下面写入插入数据库一的数据
//		user1Repository.save(new  FirstUser(null, "尹俊峰", "yinjufeng", "浙江杭州", 25));
//		user1Repository.save(new FirstUser(null, "贺方舟", "hefangzhou", "浙江杭州", 25));
		
		// 下面测试是否启用缓存
		List<FirstUser> userList = user1Repository.findByName("李阵");
		for(FirstUser user:  userList){
			int age = user.getAge();
			System.out.println("第一次打印age = " +age);
		}
		
		List<FirstUser> userList2 = user1Repository.findByName("李阵");
		for(FirstUser user :userList2){
			int age = user.getAge();
			System.out.println("第二次打印age =" + age);
		}
	}
}
