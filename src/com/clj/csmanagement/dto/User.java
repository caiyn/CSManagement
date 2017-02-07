package com.clj.csmanagement.dto;

public class User {

	private String userName = null;
	
	private String userId = null;
	
	private int userAge = 0;
	
	private String userCreateTime = null;
	
	private String password = null;

	private String user_position = null;
	
	private int user_record = 0;
	
	private int user_salary = 1000;
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserCreateTime() {
		return userCreateTime;
	}

	public void setUserCreateTime(String userCreateTime) {
		this.userCreateTime = userCreateTime;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setUser_position(String user_position) {
		this.user_position = user_position;
	}

	public String getUser_position() {
		return user_position;
	}


	public int getUser_record() {
		return user_record;
	}

	public void setUser_record(int userRecord) {
		user_record = userRecord;
	}

	public int getUser_salary() {
		return user_salary;
	}

	public void setUser_salary(int userSalary) {
		user_salary = userSalary;
	}
	
	
}
