package com.ig.egreement.testing;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import com.ig.agreement.editagreement.DeleteAgreement;
import com.ig.egreement.common.pageobjectrepository.HomePage;
import com.ig.egreement.common.pageobjectrepository.UserLoginpage;
import com.ig.egreement.common.pageobjectrepository.UserSelection;
import com.ig.egreement.common.util.Configuration;
import com.ig.egreement.common.util.Util;

public class TestDeleteAgreement {

	final static Logger logger = Logger.getLogger(TestDeleteAgreement.class);

	static WebDriver driver;

	static String loginMode = Configuration.LOGIN_MODE;

	public static void main(String[] args) {
		driver = Util.getWebDriver();

		UserSelection userSel = new UserSelection(driver);

		UserLoginpage userLogin = userSel.login(Configuration.USER_TYPE);

		String userType = userSel.getUserType();

		HomePage homePage;
		try {
			if (userType.equalsIgnoreCase(Configuration.PRIVATE_USER)) {
				homePage = loginPrivateUser(userLogin, userSel);
			} else {
				homePage = loginEnterpriseUser(userLogin, userSel);
			}
			if (homePage.getPageTitle().equalsIgnoreCase(
					Configuration.HOME_PAGE)) {

				System.out.println("Login is successful");
				// For signing the agreement
				DeleteAgreement deleteAgreement = homePage
						.deleteAgreementpage();
				System.out.println(":PAGE: " + Util.getpageTitle(driver));
				if (Util.getpageTitle(driver).equalsIgnoreCase(
						Configuration.MY_AGREEMENT_PAGE_TITLE)) {
					if (deleteAgreement.deleteRejectedAgreement())
						System.out
								.println(" Agreement has been deleted successfully");
					else
						System.out
								.println("Some error occurs while deleting agreement");
				}
			} else {
				System.out.println("Login is failed");
			}
		} catch (NoAlertPresentException e) {
			System.out
					.println("NoAlert Exception has occured while accpeting the pop-up: "
							+ e.getMessage());
		} catch (Exception e) {
			System.out
					.println("Some Exception has occured while editing the agreement: "
							+ e.getMessage());
		} finally {
			Util.tearDown(driver);
		}
	}

	private static HomePage loginPrivateUser(UserLoginpage userLogin,
			UserSelection userSel) throws Exception {
		HomePage homePage;
		if (loginMode.equalsIgnoreCase(Configuration.LOGIN_BANKID))
			homePage = userLogin.privateUserLogin(Configuration.LOGIN_BANKID,
					userSel.getBankIDPassword());
		else
			homePage = userLogin.privateUserLogin(
					Configuration.LOGIN_MOBILEBANKID,
					userSel.getPersonNumber(),
					userSel.getMobileBankIDIdentificationNumber());
		return homePage;
	}

	private static HomePage loginEnterpriseUser(UserLoginpage userLogin,
			UserSelection userSel) throws Exception {
		HomePage homePage;
		if (loginMode.equalsIgnoreCase(Configuration.LOGIN_BANKID))
			homePage = userLogin.enterpriseUserLogin(
					Configuration.LOGIN_BANKID, Configuration.ORG_NUMBER,
					userSel.getBankIDPassword());
		else
			homePage = userLogin.enterpriseUserLogin(
					Configuration.LOGIN_MOBILEBANKID, Configuration.ORG_NUMBER,
					userSel.getPersonNumber(),
					userSel.getMobileBankIDIdentificationNumber());
		return homePage;
	}
}
