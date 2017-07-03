package com.springboot.demo.service;

import java.util.List;

import com.springboot.demo.pojo.User;
import com.springboot.demo.pojo.primary.FirstUser;

public interface IUserService {
	List<FirstUser> findUserByName(String name);
	public void saveUser(FirstUser user);
	public void deleteUser(Long id);
	public void updateUser(FirstUser user);
	public FirstUser findUserById(Long id);
    public List<FirstUser> findAllUser();
}
