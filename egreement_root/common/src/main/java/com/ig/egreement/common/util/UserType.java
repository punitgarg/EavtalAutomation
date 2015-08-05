package com.ig.egreement.common.util;

public enum UserType {
	
	PRIVATE_USER("privateuser"), ENTERPRISE_USER(
			"enterpriseuser");

	private String userType;

	private UserType(String user) {
		userType = user;
	}

	public String getUserType() {
		return userType;
	}

}
