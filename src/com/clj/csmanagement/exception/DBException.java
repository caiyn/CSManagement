package com.clj.csmanagement.exception;

public class DBException extends SystemException {

	private static final long serialVersionUID = -2252432878834569134L;

	/**
	 * 构造方法
	 */
	public DBException() {
	}

	/**
	 * 构造方法
	 */
	public DBException(String msg) {
		super(msg);
	}

	/**
	 * 构造方法
	 */
	public DBException(Throwable ex) {
		super(ex);
	}

	/**
	 * 构造方法
	 */
	public DBException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
