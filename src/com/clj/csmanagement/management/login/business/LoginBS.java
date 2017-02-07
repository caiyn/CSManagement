package com.clj.csmanagement.management.login.business;

import java.util.List;

import com.clj.csmanagement.dto.User;


public interface LoginBS {

	public String loginBS(String userName,String password);
	
	public void registerBS(User user);
}
