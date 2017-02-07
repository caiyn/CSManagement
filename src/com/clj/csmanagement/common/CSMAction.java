package com.clj.csmanagement.common;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class CSMAction extends ActionSupport {

	private static final long serialVersionUID = 5361820154583550093L;

	/**
	 * 日志对象
	 */
	protected final static Logger log = Logger.getLogger(CSMAction.class);
	/**
	 * 返回JSON数据的result Key
	 */
	public static final String JSON = "jsonResult";
	/**
	 * 返回无权访问页面的result Key
	 */
	protected static final String NO_PERMISSION = "nopermission";
	
	/**
	 * 返回检索式错误页面的result Key
	 */
	protected static final String SEARCH_ERROR = "searchError";
	
	/**
	 * 返回选择企业专题库页面的result Key
	 */
	protected static final String CHOOSE_SUBJECT = "chooseSubject";
	/**
	 * 
	 * <p>以JSON类型返回指定的变量</p>
	 * 
	 * @param target
	 * @return
	 * @return: String
	 */
	protected String toJSON(String... target) {
		// TODO 处理指定的JSON数据
		return JSON;
	}
}
