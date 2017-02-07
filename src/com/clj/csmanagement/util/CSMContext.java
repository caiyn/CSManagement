package com.clj.csmanagement.util;


public class CSMContext {
	private static CSMContext instance = null;
	private CSMContext(){}
	public static CSMContext getInstance(){
		if(instance == null){
			instance = new CSMContext();
		}
		return instance;
	}
	public void setUserId(String userId){
		CSMSession.getInstance().setAttribute(CSM_SESSION_USER_ID, userId);
	}
	public String getUserId(){
		return (String)CSMSession.getInstance().getAttribute(CSM_SESSION_USER_ID);
	}
	public void setUserRecord(String userSalary){
		CSMSession.getInstance().setAttribute(CSM_SESSION_USER_RECORD, userSalary);
	}
	public String getUserRecord(){
		return (String)CSMSession.getInstance().getAttribute(CSM_SESSION_USER_RECORD);
	}
	
	public void setUserName(String userName){
		CSMSession.getInstance().setAttribute(CSM_SESSION_USER_NAME, userName);
	}
	public String getUserName(){
		return (String)CSMSession.getInstance().getAttribute(CSM_SESSION_USER_NAME);
	}
	
	//用户ID
	private static final String CSM_SESSION_USER_ID = "CSM_USER_ID";
	//用户名
	private static final String CSM_SESSION_USER_NAME="CSM_USER_NAME";
	
	private static final String CSM_SESSION_USER_RECORD="CSM_USER_RECORD";
}
