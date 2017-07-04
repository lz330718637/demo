package com.springboot.demo.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.springboot.demo.pojo.primary.FirstUser;
import com.springboot.demo.service.impl.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserService userService;
	// 这里设置允许那些路径下的页面需要设置安全验证
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//如果直接输入登陆网址时，当登陆成功后，默认成功的跳转地址为“/”,现在发现的问题是这个不能将登陆成功后的数据渲染给模板
		http
		        .authorizeRequests().antMatchers("/","/registry").permitAll().anyRequest().authenticated().and()
		        .formLogin().loginPage("/login").successHandler(new AuthenticationSuccessHandler(){
					@Override
					public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res,
							Authentication auth) throws IOException, ServletException {
						HttpSession session = req.getSession();
						List<FirstUser> userList = userService.findUserByName(auth.getName());
						for(FirstUser user :userList){
							//这里将userList中的数据加入到session中,这里加入的只有列表最后一个user
							session.setAttribute("user", user);
						}
						res.sendRedirect("/success");
					}
		        })
		        .failureHandler(new AuthenticationFailureHandler(){

					@Override
					public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException exception) throws IOException, ServletException {
						// 这里目前登陆失败的的验证我还没有写
						
					}
		        }).permitAll().and()
		        .logout().permitAll();
	}
	
	@Autowired
	protected void configGloable(AuthenticationManagerBuilder auth)throws Exception{
		
		//这里下面我不知道设置的这个用户名和密码好像是固定好的了，理论上应该使用从数据库中查询出来的用户名和密码
		//之前设置的一直有问题，加了默认成功页面时，可以跳转！但是每次从系启动应用时，第一次都会出现没有访问权限问题
		//这里设置的前提是讲login页面中的username属性的标签的name改为username，这里我猜是这里的user验证时，取出的是前面名字为username的标签
		auth.inMemoryAuthentication().withUser("李阵").password("lizhen").roles("USER");
		
		//auth.userDetailsService(this.loginService);
	}
}
