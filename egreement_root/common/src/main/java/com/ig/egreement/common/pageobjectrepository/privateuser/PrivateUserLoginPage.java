package com.ig.egreement.common.pageobjectrepository.privateuser;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.ig.egreement.common.pageobjectrepository.HomePage;
import com.ig.egreement.common.pageobjectrepository.UserLoginpage;
import com.ig.egreement.common.util.Configuration;
import com.ig.egreement.common.util.Util;

public class PrivateUserLoginPage extends UserLoginpage {

	public PrivateUserLoginPage(WebDriver webDriver) {
		super(webDriver);
		this.driver = webDriver;
		System.out.println(":: PrivateUserLoginPage page :: "
				+ PrivateUserLoginPage.class);
	}

	private WebDriver driver;

	Util utilInstance = new Util();

	private static final By LOCATOR_LOGIN_MODE_SELECT_DROPDOWN = By
			.id("selectBox");

	private static final By LOCATOR_LOGIN_SUBMIT_BUTTON = By
			.id("loginSubmitButton");

	// private static final By LOCATOR_LOGIN_MODE_MOBILE_BANK_ID = By
	// .partialLinkText("on another device");

	/*
	 * Login with BankID
	 */
	@Override
	public HomePage privateUserLogin(String loginModeType, String bankIDPassword)
			throws Exception {
		super.setLoginMode(loginModeType);
		loginDropDown().selectByVisibleText(Configuration.DROPDOWN_TEXT_BANKID);
		utilInstance.getWebElement(driver, LOCATOR_LOGIN_SUBMIT_BUTTON).click();
		try {
			utilInstance.login(bankIDPassword);
			if (utilInstance.checkAlert()) {
				driver.switchTo().alert().accept();
			}
		} catch (NoAlertPresentException e) {
		} catch (TimeoutException e) {
		} catch (Exception e) {
		}
		if (Util.getpageTitle(driver).equalsIgnoreCase("Min sida"))
			return new HomePage(driver);
		else
			throw new Exception("Login is failed. Please try again!!!");
	}

	/*
	 * Login with mobile bank ID
	 */
	@Override
	public HomePage privateUserLogin(String loginModeType, String personNo,
			String mobileIdentificationNo) throws Exception{
		super.setLoginMode(loginModeType);
		loginDropDown().selectByVisibleText(
				Configuration.DROPDOWN_TEXT_MOBILE_BANKID);
		utilInstance.getWebElement(driver, LOCATOR_LOGIN_SUBMIT_BUTTON).click();
		try {
			utilInstance.login(driver, personNo, mobileIdentificationNo);
			if (utilInstance.checkAlert()) {
				driver.switchTo().alert().accept();
			}
		} catch (NoAlertPresentException e) {
			System.out.println(PrivateUserLoginPage.class
					+ "No Alert present exception occured while doing login"
					+ e.getMessage());
		} catch (TimeoutException e) {
			System.out.println(PrivateUserLoginPage.class
					+ "TimeOut exception occured while doing login"
					+ e.getMessage());
		} catch (Exception e) {
			System.out.println(PrivateUserLoginPage.class
					+ "Some exception occured while doing login"
					+ e.getMessage());
		}
		if (Util.getpageTitle(driver).equalsIgnoreCase("Min sida"))
			return new HomePage(driver);
		else
			throw new Exception("Login is failed. Please try again!!!");
	}

	private Select loginDropDown() {
		return new Select(utilInstance.getWebElement(driver,
				LOCATOR_LOGIN_MODE_SELECT_DROPDOWN));
	}
}
