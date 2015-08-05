package com.ig.egreement.common.pageobjectrepository;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ig.egreement.common.pageobjectrepository.enterpriseuser.EnterpriseUserLoginPage;
import com.ig.egreement.common.pageobjectrepository.privateuser.PrivateUserLoginPage;
import com.ig.egreement.common.util.Configuration;
import com.ig.egreement.common.util.UserType;
import com.ig.egreement.common.util.Util;

public class UserSelection {

	final static Logger logger = Logger.getLogger(UserSelection.class);

	private WebDriver driver;

	private String user;

	private String personNumber;

	private String mobileBankIDIdentificationNumber;

	private String bankIDPassword;

	Util utilInstance = new Util();

	// Setup and Initializing WebDriver
	public UserSelection(WebDriver webDriver) {
		this.driver = webDriver;
		System.out.println(":: UserSelection page :: " + UserSelection.class);
	}

	// Selecting userType
	public UserLoginpage login(String userType) {
		System.out.println(" In login page");
		this.setBankIDPassword(Configuration.USER_BANKID_PASSWORD);
		this.setPersonNumber(Configuration.USER_MOBILE_BANK_ID_PERSON_NO);
		this.setMobileBankIDIdentificationNumber(Configuration.USER_MOBILE_BANK_ID_IDENTIFICATION_NO);
		if (userType.equalsIgnoreCase(UserType.PRIVATE_USER.getUserType())) {
			if (!Util.getpageTitle(driver).equalsIgnoreCase(
					Configuration.USER_LOGIN_PAGE))
				utilInstance.getWebElement(driver,
						Configuration.LOCATOR_PRIVATE_USER).click();
			this.user = UserType.PRIVATE_USER.getUserType();
			return new PrivateUserLoginPage(driver);
		} else {
			if (!Util.getpageTitle(driver).equalsIgnoreCase(
					Configuration.USER_LOGIN_PAGE))
				utilInstance.getWebElement(driver,
						Configuration.LOCATOR_ENTERPRISE_USER).click();
			this.user = UserType.ENTERPRISE_USER.getUserType();
			return new EnterpriseUserLoginPage(driver);
		}
	}

	public String getUserType() {
		logger.info("UserType: " + this.user);
		return this.user;
	}

	public String getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(String perNumber) {
		this.personNumber = perNumber;
	}

	public String getMobileBankIDIdentificationNumber() {
		return mobileBankIDIdentificationNumber;
	}

	public void setMobileBankIDIdentificationNumber(
			String mobileBankIDIdentiNumber) {
		this.mobileBankIDIdentificationNumber = mobileBankIDIdentiNumber;
	}

	public String getBankIDPassword() {
		return bankIDPassword;
	}

	public void setBankIDPassword(String bankIDPass) {
		this.bankIDPassword = bankIDPass;
	}

}
