package com.clj.csmanagement.exception;

public class AjaxException extends SystemException {

	private static final long serialVersionUID = 1755128612223832347L;

	private String[] textPrarams = null;
	public AjaxException() {
	}

	public AjaxException(String msg) {
		super(msg);
	}

	public AjaxException(Throwable ex) {
		super(ex);
	}

	public AjaxException(String msg, Throwable ex) {
		super(msg, ex);
	}

	public String[] getTextPrarams() {
		return textPrarams;
	}

	public void setTextPrarams(String[] textPrarams) {
		this.textPrarams = textPrarams;
	}
}
