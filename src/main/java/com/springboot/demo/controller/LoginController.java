package com.springboot.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.springboot.demo.pojo.primary.FirstUser;
import com.springboot.demo.service.IUserService;
import com.springboot.demo.service.impl.LoginService;

@Controller
public class LoginController {

	// 这里注入userservice
	@Autowired
	LoginService loginService;

	@Autowired
	IUserService userService;
	
	//现在这个数据是经过security模块传过来的
 @RequestMapping("/login" )
 public String login(){
	 return"login";
 }
 
 @RequestMapping("/success")
 public String success(HttpSession session,Model model){
	 FirstUser user = (FirstUser) session.getAttribute("user");
	 model.addAttribute("user", user);
	 return "success";
 }
 
 @RequestMapping("/registry")
 public String registry(){
	 return "user/registry";
 }

 
 @RequestMapping(value="/registry", method={RequestMethod.POST})
 public String  registry(Model model,String username, String password, String address, int age){
	 FirstUser user = new FirstUser(null,username,password,address,age);
	 userService.saveUser(user);
	 //用户注册后，跳转到登陆成功的界面,这里用户插入后，没有在查询用户操作
	 model.addAttribute("user", user);
	 return "success";
 }
 
 
 //使用了权限认证后，好像所有的登陆都不会转发到这里来了
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public String login(Model model, HttpSession session, String name, String password) {
		// 这里调用业务层查看是否有用户
		FirstUser user = loginService.login(name, password);
		//现在根据用户名得到了用户，这里因为加入了验证，所以没有用到之前写的方法 用户验证密码是否正确的   现在在下面实现一下
		
		if (user != null) {
			//之前表里的属性是name  加入了UserDetails后重写了其中的getUserName方法，所以现在下面的是getUsername方法
			session.setAttribute("name", user.getName());
			model.addAttribute("user", user);
			return "success";
		} else
			return "err";
	}
}
