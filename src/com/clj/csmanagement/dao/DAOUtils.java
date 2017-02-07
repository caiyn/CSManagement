package com.clj.csmanagement.dao;

public class DAOUtils {

	public static String convertBoolean2String(Boolean b) {
		if (b == null)
			return null;
		return b.booleanValue() ? "1" : "0";
	}

	public static Boolean convertString2Boolean(String value, Boolean defaultValue) {
		if (value == null)
			return defaultValue;
		if ("0".equals(value))
			return Boolean.FALSE;
		else if ("1".equals(value))
			return Boolean.TRUE;
		return defaultValue;
	}

	public static boolean convertString2Boolean(String value) {
		return convertString2Boolean(value, Boolean.FALSE);
	}
}
