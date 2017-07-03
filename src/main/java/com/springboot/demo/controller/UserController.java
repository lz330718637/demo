package com.springboot.demo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.demo.pojo.primary.FirstUser;
import com.springboot.demo.service.IUserService;

@Controller
public class UserController {
	// 这里我先让controller返回json数据
	@Autowired
	IUserService userService;

	@RequestMapping("/findUserByName")
	public List<FirstUser> findUserByName(String name) {
		return userService.findUserByName(name);
	}

	@RequestMapping("/save")
	public String save(Model model,String name, String password, String address, int age) throws SQLException {
		FirstUser user = new FirstUser(null, name, password, address, age);
		userService.saveUser(user);
		model.addAttribute("user"	, user);
		return "success";
	}

	@RequestMapping("/delete")
	public String delete(Model model,Long id) {
		userService.deleteUser(id);
		//这里删除过后，需要跳转到所有用户界面
		List<FirstUser> userList = userService.findAllUser();
		model.addAttribute("userList", userList);
		return "user/users";
	}

	
	@RequestMapping("/update")
	public String update(Model model,Long id){
		FirstUser user = userService.findUserById(id);
		model.addAttribute("user", user);
		return "user/update";
	}
	@RequestMapping(value="/update",method={RequestMethod.POST})
	public String update(Model model,Long id,String name, String address, int age){
		FirstUser user = userService.findUserById(id);
		user.setName(name);
		user.setAddress(address);
		user.setAge(age);
		//执行跟新操作
		userService.saveUser(user);
		
		//这里更新玩用户后，跳转到所有用户界面
		List<FirstUser> userList = userService.findAllUser();
		model.addAttribute("userList", userList);
		return "user/users";
	}
	
	
	@RequestMapping("/findAllUser")
	public String findAllUser(Model model){
		List<FirstUser> userList = userService.findAllUser();
		model.addAttribute("userList", userList);
		return "user/users";
	}
}
