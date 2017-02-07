package com.clj.csmanagement.dto;

import java.util.ArrayList;
import java.util.List;


public class UserDTO {

	List<User> userList = new ArrayList<User>();

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
}
