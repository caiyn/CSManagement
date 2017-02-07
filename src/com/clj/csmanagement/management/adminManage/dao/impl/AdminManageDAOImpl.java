package com.clj.csmanagement.management.adminManage.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.clj.csmanagement.dao.CSMDAO;
import com.clj.csmanagement.dto.User;
import com.clj.csmanagement.dto.UserDTO;
import com.clj.csmanagement.management.adminManage.dao.AdminManageDAO;

public class AdminManageDAOImpl extends CSMDAO implements AdminManageDAO {

	private static final String SQL_ALL_USERS = "SELECT * FROM CSM_USERS";
	
	private static final String SQL_SEARCH_USERS = "SELECT * FROM CSM_USERS WHERE ";
	
	private static final String SQL_SEARCH_USERS_SELLED = "SELECT * FROM CSM_USERS WHERE USER_ID=?";
	
	private static final String SQL_ADD_USER = "INSERT INTO csm_users (USER_NAME,USER_AGE,USER_SALARY,USER_POSITION,USER_ID,USER_CREATE_TIME) VALUES (?,?,?,?,?,NOW())";
	
	private static final String SQL_UPDATE_USER = "update csm_users set USER_NAME=?,USER_AGE=?,USER_SALARY=?,USER_POSITION=? where USER_ID = ?";
	
	private static final String SQL_UPDATE_USER_SELLED = "update csm_users set USER_RECORD=? where USER_ID = ?";
	
	private static final String SQL_DELETE_USER = "DELETE FROM csm_users where USER_ID = ?";

	private static class UserRowMapper implements RowMapper<User>{

		public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
			User user = new User();
			user.setUserId(rs.getString("USER_ID"));
			user.setUserName(rs.getString("USER_NAME"));
			user.setUserCreateTime(rs.getString("USER_CREATE_TIME"));
			user.setUserAge(rs.getInt("USER_AGE"));
			user.setPassword(rs.getString("USER_PASSWORD"));
			user.setUser_position(rs.getString("USER_POSITION"));
			user.setUser_record(rs.getInt("USER_RECORD"));
			user.setUser_salary(rs.getInt("USER_SALARY"));
			return user;
		}
		
	}
	public List<User> allUsers() {
		// TODO Auto-generated method stub
		return this.query(SQL_ALL_USERS,new UserRowMapper());
	}
	public List<User> selectedUsers(String sc) {
		// TODO Auto-generated method stub
		String sql = SQL_SEARCH_USERS;
		sql+=" USER_NAME like '%"+sc+"%'";
		return this.query(sql, new UserRowMapper());
	}
	public void addUser(User user, String userId) {
		// TODO Auto-generated method stub
		Object[] params = new Object[] {user.getUserName(),user.getUserAge(),user.getUser_salary(),user.getUser_position(),userId};
		this.update(SQL_ADD_USER, params);
	}
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		Object[] params = new Object[] {user.getUserName(),user.getUserAge(),user.getUser_salary(),user.getUser_position(),user.getUserId()};
		this.update(SQL_UPDATE_USER, params);
	}
	public void deleteUsers(User user) {
		// TODO Auto-generated method stub
		Object[] params = new Object[] {user.getUserId()};
		this.update(SQL_DELETE_USER, params);
	}
	public void updateUserSelled(int userRecord, String userId) {
		// TODO Auto-generated method stub
		Object[] params = new Object[] {userRecord,userId};
		this.update(SQL_UPDATE_USER_SELLED, params);
	}
	public List<User> selectedUsersByID(String userId) {
		Object[] params = new Object[] {userId};
		return this.query(SQL_SEARCH_USERS_SELLED, params,new UserRowMapper());
	}


}
