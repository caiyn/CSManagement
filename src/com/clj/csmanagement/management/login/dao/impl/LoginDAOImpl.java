package com.clj.csmanagement.management.login.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.clj.csmanagement.dao.CSMDAO;
import com.clj.csmanagement.dto.User;
import com.clj.csmanagement.management.login.dao.LoginDAO;


public class LoginDAOImpl extends CSMDAO implements LoginDAO {
	
	private static final String SQL_REGISTER = "INSERT INTO FORUM_USERS (USER_ID,USER_NAME,USER_AGE,USER_CREATE_TIME) VALUES(?,?,?,sysdate)";
	
	private static final String SQL_LOGIN = "SELECT * FROM CSM_USERS WHERE USER_NAME=? AND USER_PASSWORD=?";
	
	private static class UserRowMapper implements RowMapper<User>{

		public User mapRow(ResultSet rs, int rowNumber) throws SQLException {
			User user = new User();
//			user.setId(rs.getString("USER_ID"));
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
	public void registerDAO(User user) {
		// TODO Auto-generated method stub
		Object[] params = new Object[] { user.getUserId(), user.getUserName(),user.getUserAge()};
		try {
			this.update(SQL_REGISTER, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<User> loginDAO(String userName,String password) {
		// TODO Auto-generated method stub
		Object[] params = new Object[] {userName,password};
		return this.query(SQL_LOGIN, params,new UserRowMapper());
	}
}
