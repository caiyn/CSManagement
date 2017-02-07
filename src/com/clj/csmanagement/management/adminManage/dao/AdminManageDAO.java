package com.clj.csmanagement.management.adminManage.dao;

import java.util.List;

import com.clj.csmanagement.dto.User;
import com.clj.csmanagement.dto.UserDTO;

public interface AdminManageDAO {

	public List<User> allUsers();
	
	public List<User> selectedUsers(String sc);
	
	public List<User> selectedUsersByID(String userId);
	
	public void addUser(User user,String userId);
	
	public void updateUser(User user);
	
	public void updateUserSelled(int userRecord,String userId);
	
	public void deleteUsers(User user);
}
