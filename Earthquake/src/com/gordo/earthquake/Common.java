package com.gordo.earthquake;

public class Common {
	
	public static String escapeQuery(String value) {
		return value.replace("'", "''");
	}
}
