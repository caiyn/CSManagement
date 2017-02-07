package com.clj.csmanagement.util;

import java.util.UUID;

public class IDGenerator {

	public static String generateId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String generateId(String sequenceName) {
		return generateId();
	}
}
