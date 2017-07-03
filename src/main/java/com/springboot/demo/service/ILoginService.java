package com.springboot.demo.service;

import com.springboot.demo.pojo.primary.FirstUser;

public interface ILoginService {
	public FirstUser login(String name , String password);

}
