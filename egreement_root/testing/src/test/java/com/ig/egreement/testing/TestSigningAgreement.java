package com.ig.egreement.testing;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import com.ig.agreement.agreementsigning.signagreement.SignAgreement;
import com.ig.egreement.common.pageobjectrepository.HomePage;
import com.ig.egreement.common.pageobjectrepository.UserLoginpage;
import com.ig.egreement.common.pageobjectrepository.UserSelection;
import com.ig.egreement.common.util.Configuration;
import com.ig.egreement.common.util.Util;

public class TestSigningAgreement {

	final static Logger logger = Logger.getLogger(TestSigningAgreement.class);

	static WebDriver driver;

	static String loginMode = Configuration.LOGIN_MODE;

	public static void main(String[] args) throws Exception {
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
				// For signing the agreement
				SignAgreement agreementSigner = homePage.signAgreementpage();
				if (Util.getpageTitle(driver).equalsIgnoreCase(
						Configuration.MY_AGREEMENT_PAGE_TITLE)) {
					signAgreement(agreementSigner, userSel);
					String signedPage = Util.getpageTitle(driver);
					if (signedPage
							.equals(Configuration.AGREEMENT_SIGNED_SUCCESS_PAGE_TITLE)) {
						System.out
								.println(" :: Contract has been signed successfully ");
					}
				} else {
					System.out
							.println(" User is not currently on my Agreement Page and hence cannot sign the agreement");
				}
			} else {
				System.out.println("Login is failed");
			}
		} catch (NoAlertPresentException e) {
			System.out
					.println("Some Exception has occured while accpeting the pop-up: "
							+ e.getMessage());
		} catch (Exception e) {
			System.out
					.println("Some Exception has occured while signing the agreement: "
							+ e.getMessage());
		} finally {
			/*Util.tearDown(driver);*/
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

	private static void signAgreement(SignAgreement agreementSigner,
			UserSelection userSel) throws Exception {
		/*if (loginMode.equalsIgnoreCase(Configuration.LOGIN_BANKID)) {
			agreementSigner.signAgreementWithBankId(userSel
					.getBankIDPassword());
		} else {
			agreementSigner.signAgreementWithMobileBankId(userSel
					.getMobileBankIDIdentificationNumber());
		} */
		
		agreementSigner.signAgreementWithMobileBankId(userSel
				.getMobileBankIDIdentificationNumber());
	}
}
