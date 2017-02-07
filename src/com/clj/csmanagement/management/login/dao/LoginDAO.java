package com.clj.csmanagement.management.login.dao;

import java.util.List;

import com.clj.csmanagement.dto.User;


public interface LoginDAO {

	public void registerDAO(User user);
	
	public List<User> loginDAO(String userName,String password);
}
