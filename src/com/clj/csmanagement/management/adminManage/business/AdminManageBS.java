package com.clj.csmanagement.management.adminManage.business;

import com.clj.csmanagement.dto.User;
import com.clj.csmanagement.dto.UserDTO;

public interface AdminManageBS {

	public UserDTO allUsers();
	
	public UserDTO selectedUsers(String sc);
	
	public void addUser(User user);
	
	public void deleteUsers(User user);
	
	public void updateUser(User user);
	
	public void updateUserSelled(User user);
}
