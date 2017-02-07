package com.clj.csmanagement.exception;


public class CSMException extends Exception {

	private static final long serialVersionUID = 2837724518953122022L;


	/**
	 * 检索与修正错误代码
	 */
	public static final String ERROR_CODE_SEARCH = "01";

	/**
	 * 文献浏览错误代码
	 */
	public static final String ERROR_CODE_VIEW = "02";

	/**
	 * 统计分析错误代码
	 */
	public static final String ERROR_CODE_STATISTIC = "03";

	/**
	 * 系统管理错误代码
	 */
	public static final String ERROR_CODE_SYS_MANAGE = "04";

	/**
	 * 数据管理错误代码
	 */
	public static final String ERROR_CODE_DATA_MANAGE = "05";

	private String[] messageParameters = null;

	public CSMException(String message) {
		super(message);
	}

	public CSMException(String message, String[] msgParams) {
		super(message);
		setMessageParameters(msgParams);
	}

	public CSMException(String message, Throwable ex) {
		super(message, ex);
	}

	public CSMException(String message, String[] msgParams, Throwable ex) {
		super(message, ex);
		setMessageParameters(msgParams);
	}

	public String[] getMessageParameters() {
		return messageParameters;
	}

	public CSMException setMessageParameters(String[] msgParams) {
		this.messageParameters = msgParams;
		return this;
	}
	
	public CSMException(Throwable ex){
		super(ex);
	}
}
