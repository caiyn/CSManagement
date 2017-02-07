package com.clj.csmanagement.util;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

public class CSMSession {
	private Map<String, Object> sessionMap = null;
	private static CSMSession instance = null;
	private CSMSession(){
		//每次调用之前都刷新session
		refreshSession();
	}
	public static CSMSession getInstance(){
		instance = new CSMSession();
		return instance;
	}
	/**
	 * 
	 * <p>[刷新session]</p>
	 * 
	 */
	private void refreshSession() {
		sessionMap = ActionContext.getContext().getSession();
	}
	
	public void setAttribute(String name, Object value) {
		sessionMap.put(name, value);
	}

	public Object getAttribute(String name) {
		return sessionMap.get(name);
	}

	public void removeAttribute(String name) {
		sessionMap.remove(name);
	}
}
