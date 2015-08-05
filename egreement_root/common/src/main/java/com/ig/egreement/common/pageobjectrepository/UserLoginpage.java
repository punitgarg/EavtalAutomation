package com.ig.egreement.common.pageobjectrepository;

import org.openqa.selenium.WebDriver;

public class UserLoginpage {

	public UserLoginpage(WebDriver driver) {
		this.driver = driver;
		System.out.println(":: UserLoginpage page :: " + UserLoginpage.class);
	}

	private WebDriver driver;

	private String loginMode;

	public String getLoginMode() {
		return loginMode;
	}

	public void setLoginMode(String loginMode) {
		this.loginMode = loginMode;
	}

	public HomePage privateUserLogin(String modeType, String personNo,
			String mobileIdentificationNo) throws Exception {
		System.out
				.println(":: User is logging in using MobileBankId Mode with details:: Modetype::"
						+ modeType
						+ " , PersonNo.::"
						+ personNo
						+ " , mobileIdentificationNo.::"
						+ mobileIdentificationNo);
		return new HomePage(driver);
	}

	public HomePage privateUserLogin(String modeType, String bankIDPassword)
			throws Exception {
		System.out
				.println(":: User is logging in using BankID Mode with details:: Modetype::"
						+ modeType + " , BankIdPassword.::" + bankIDPassword);
		return new HomePage(driver);
	}

	public HomePage enterpriseUserLogin(String modeType, String orgNumber,
			String personNo, String mobileIdentificationNo) throws Exception {
		System.out
				.println(":: User is logging in using MobileBankId Mode with details:: Modetype::"
						+ modeType
						+ " , orgNumber::"
						+ orgNumber
						+ " , PersonNo.::"
						+ personNo
						+ " , mobileIdentificationNo.::"
						+ mobileIdentificationNo);
		return new HomePage(driver);
	}
	
	public HomePage enterpriseUserLogin(String modeType, String orgNumber,String bankIDPassword)
			throws Exception {
		System.out
				.println(":: User is logging in using BankID Mode with details:: Modetype::"
						+ modeType + " , orgNumber::" + orgNumber + " , BankIdPassword.::" + bankIDPassword);
		return new HomePage(driver);
	}

}
