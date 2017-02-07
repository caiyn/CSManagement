package com.clj.csmanagement.management.adminManage.action;

import com.clj.csmanagement.common.CSMAction;
import com.clj.csmanagement.dto.User;
import com.clj.csmanagement.dto.UserDTO;
import com.clj.csmanagement.management.adminManage.business.AdminManageBS;

public class AdminManageAC extends CSMAction {

	private static final long serialVersionUID = 7218660778975953583L;
	
	private UserDTO userDTO;

	private User user;
	
	private String sc;
	
	private AdminManageBS adminManageBS;

	public String allUsers(){
		userDTO = adminManageBS.allUsers();
		return JSON;
	}
	public String selectedUsers(){
		userDTO = adminManageBS.selectedUsers(sc);
		return JSON;
	}
	public String addUser(){
		adminManageBS.addUser(user);
		return JSON;
	}
	public String updateUser(){
		adminManageBS.updateUser(user);
		return JSON;
	}
	public String deleteUsers(){
		adminManageBS.deleteUsers(user);
		return JSON;
	}
	public String updateUserSelled(){
		adminManageBS.updateUserSelled(user);
		return JSON;
	}
	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public User getUser() {
		return user;
	}
	public void setAdminManageBS(AdminManageBS adminManageBS) {
		this.adminManageBS = adminManageBS;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setSc(String sc) {
		this.sc = sc;
	}
}
