package com.clj.csmanagement.management.login.action;

import com.clj.csmanagement.common.CSMAction;
import com.clj.csmanagement.dto.User;
import com.clj.csmanagement.management.login.business.LoginBS;


public class LoginAC extends CSMAction {

	private static final long serialVersionUID = 1232163273328403741L;

	private User user;

	private LoginBS loginBS;
	
	private String userName;
	
	private String password;
	
	public String loginAction(){
		return loginBS.loginBS(userName,password);
	}
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	public void setLoginBS(LoginBS loginBS) {
		this.loginBS = loginBS;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
}
