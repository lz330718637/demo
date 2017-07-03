package com.springboot.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.pojo.primary.FirstUser;
import com.springboot.demo.pojo.primary.FirstUserRepository;
import com.springboot.demo.pojo.secondary.SecondUser;
import com.springboot.demo.pojo.secondary.SecondUserRepository;

@Service
public class UserService implements com.springboot.demo.service.IUserService {
	// 这里需要注入相应的repository, 因为这里我使用了多数据源的访问,所以我这里再加入连接第二个数据库的repository
	@Autowired
	FirstUserRepository userRepository;
	
	@Autowired
	SecondUserRepository user2Repository;
	

	@Override
	public List<FirstUser> findUserByName(String name) {
		// 此时根据repository来查询数据库
		List<FirstUser> userList = userRepository.findByName(name);
		return userList;
	}

	@Override
	public void saveUser(FirstUser user) {
		//这里我使用一个user对象，然后同时传入两个数据库中去
	   userRepository.save(user);
		//创建能插入第二个数据库的对象
		SecondUser user2 =new SecondUser(null,user.getName(),user.getPassword(),user.getAddress(),user.getAge()) ;
		user2Repository.save(user2);
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.delete(id);

	}

	@Override
	public void updateUser(FirstUser user) {
		userRepository.save(user);
	}

	@Override
	public FirstUser findUserById(Long id) {
		FirstUser  user = userRepository.findOne(id);
		return user;
	}

	@Override
	public List<FirstUser> findAllUser() {
		List<FirstUser> userList = userRepository.findAll();
		return userList;
	}


}
