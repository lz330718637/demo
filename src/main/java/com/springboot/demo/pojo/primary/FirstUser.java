package com.springboot.demo.pojo.primary;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FirstUser  {
	
	@Id
	@GeneratedValue
	private Long  id;
	private String name;
	private int age;
	private String address;
	private String password;
	
	public FirstUser(){
		super();
	}
	
	public FirstUser(Long id, String name, String password,String address, int age){
		this.name = name;
		this.address = address;
		this.password = password;
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long  id) {
		this.id =  id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
