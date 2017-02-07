package com.clj.csmanagement.management.login.business.impl;

import java.util.List;

import com.clj.csmanagement.dto.User;
import com.clj.csmanagement.management.login.business.LoginBS;
import com.clj.csmanagement.management.login.dao.LoginDAO;
import com.clj.csmanagement.util.CSMContext;
import com.clj.csmanagement.util.IDGenerator;


public class LoginBSImpl implements LoginBS {

	private LoginDAO loginDAO;
	public void registerBS(User user) {
		// TODO Auto-generated method stub
		String userId = IDGenerator.generateId();
		user.setUserId(userId);
		loginDAO.registerDAO(user);
	}
	public String loginBS(String userName,String password) {
		// TODO Auto-generated method stub
		List<User> userList = loginDAO.loginDAO(userName,password);
		if(userList.size()>0){
			CSMContext.getInstance().setUserId(userList.get(0).getUserId());
			CSMContext.getInstance().setUserName(userList.get(0).getUserName());
			CSMContext.getInstance().setUserRecord(Integer.toString(userList.get(0).getUser_record()));
			if(userList.get(0).getUserName().equals("admin"))
				return "admin";
			else
				return "normal";
		}else{
			return "errorpage";
		}
	}
	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}
	public LoginDAO getLoginDAO() {
		return loginDAO;
	}

}
