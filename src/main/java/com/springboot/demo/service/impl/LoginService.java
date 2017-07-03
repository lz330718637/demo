package com.springboot.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.springboot.demo.pojo.primary.FirstUser;
import com.springboot.demo.pojo.primary.FirstUserRepository;
import com.springboot.demo.service.ILoginService;

@Service
public class LoginService implements ILoginService {
	// 这里注入相应的jpa去数据库中查询是否有相应的用户
	@Autowired
	private FirstUserRepository userRepository;

	@Override
	public FirstUser login(String name, String password) {
		List<FirstUser> userList = userRepository.findByName(name);
		for (FirstUser user : userList) {
			// 下面判断返回结果中的用户中表中的密码是否正确
			if (user.getPassword().equals(password))
				return user;
		}
		return null;
	}

//	// 这个是后期为了增加权限认证时添加的
//	@Override
//	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		List<FirstUser> userList = userRepository.findByName(name);
//		FirstUser user = null;
//		for (FirstUser user1 : userList) {
//			if (user1 == null) {
//				throw new UsernameNotFoundException(String.format("User with username=%s was not found", name));
//			}
//			//这里目前认为数据库中的用户的名字是唯一的，所以返回的用户列表现在认为只有一个,如果有多个的话，这里的操作会讲用户列表中的最后一个用户返回。
//			user=user1;
//		}
//		return user;
//	}
}
