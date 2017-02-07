package com.clj.csmanagement.management.adminManage.business.impl;

import java.util.List;

import com.clj.csmanagement.dto.User;
import com.clj.csmanagement.dto.UserDTO;
import com.clj.csmanagement.management.adminManage.business.AdminManageBS;
import com.clj.csmanagement.management.adminManage.dao.AdminManageDAO;
import com.clj.csmanagement.util.CSMContext;
import com.clj.csmanagement.util.IDGenerator;

public class AdminManageBSImpl implements AdminManageBS {

	private AdminManageDAO adminManageDAO;
	
	public UserDTO allUsers() {
		// TODO Auto-generated method stub
		UserDTO userDTO = new UserDTO();
		List<User> userList1 = adminManageDAO.allUsers();
		userDTO.setUserList(userList1);
		return userDTO;
	}
	public UserDTO selectedUsers(String sc) {
		// TODO Auto-generated method stub
		UserDTO userDTO = new UserDTO();
		List<User> userList2 = adminManageDAO.selectedUsers(sc);
		userDTO.setUserList(userList2);
		return userDTO;
	}

	public void setAdminManageDAO(AdminManageDAO adminManageDAO) {
		this.adminManageDAO = adminManageDAO;
	}

	public void addUser(User user) {
		// TODO Auto-generated method stub
		String userId = IDGenerator.generateId();
		adminManageDAO.addUser(user,userId);
	}
	public void deleteUsers(User user) {
		// TODO Auto-generated method stub
		adminManageDAO.deleteUsers(user);
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		adminManageDAO.updateUser(user);
	}
	public void updateUserSelled(User user) {
		// TODO Auto-generated method stub
		String userId = CSMContext.getInstance().getUserId();
		List<User> userList3 = adminManageDAO.selectedUsersByID(userId);
		int userRecord = userList3.get(0).getUser_record() + user.getUser_record();
		adminManageDAO.updateUserSelled(userRecord,userId);
	}

}
