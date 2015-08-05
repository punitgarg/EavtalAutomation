package com.ig.egreement.testing;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import com.ig.egreement.common.pageobjectrepository.CreateAgreement;
import com.ig.egreement.common.pageobjectrepository.CreateAgreementType;
import com.ig.egreement.common.pageobjectrepository.HomePage;
import com.ig.egreement.common.pageobjectrepository.UserLoginpage;
import com.ig.egreement.common.pageobjectrepository.UserSelection;
import com.ig.egreement.common.util.Configuration;
import com.ig.egreement.common.util.PrivateContractor;
import com.ig.egreement.common.util.Util;

public class TestEnterpriseUserCreatingAgreement {

	final static Logger logger = Logger.getLogger(TestEnterpriseUserCreatingAgreement.class);

	static WebDriver driver;

	public static void main(String[] args) throws Exception {
		driver = Util.getWebDriver();

		UserSelection userSel = new UserSelection(driver);

		UserLoginpage userLogin = userSel.login(Configuration.USER_TYPE);

		String loginMode = Configuration.LOGIN_MODE;
		
		String userType = userSel.getUserType();

		HomePage homePage;
		try {
			if (userType.equalsIgnoreCase(
					Configuration.PRIVATE_USER)) {

				if (loginMode.equalsIgnoreCase(Configuration.LOGIN_BANKID))
					homePage = userLogin.privateUserLogin(
							Configuration.LOGIN_BANKID,
							userSel.getBankIDPassword());
				else
					homePage = userLogin.privateUserLogin(
							Configuration.LOGIN_MOBILEBANKID,
							userSel.getPersonNumber(),
							userSel.getMobileBankIDIdentificationNumber());

			}
			// TODO to set condition for enterprise users
			else {
				homePage = userLogin
						.privateUserLogin(Configuration.LOGIN_BANKID,
								userSel.getBankIDPassword());
			}
			if (homePage.getPageTitle().equalsIgnoreCase(
					Configuration.HOME_PAGE)) {
				System.out.println("Login is successful");
				// For creating the new agreement
				CreateAgreement createAgreement = homePage
						.createAgreementpage();
				String agreementPostedPage = "";
				if (Util.getpageTitle(driver).equalsIgnoreCase(
						Configuration.AGREEMENT_CREATE_PAGE_TITLE)) {
					CreateAgreementType agreementType = createAgreement
							.createAgreementType();

					agreementPostedPage = agreementType.createAgreement(
							Configuration.AGREEMENT_NAME, userType, 
							Configuration.UPLOADED_FILE,
							Configuration.ATTACHMENT_LOCATION_URL,
							PrivateContractor.getContractor());
					if (agreementPostedPage
							.equalsIgnoreCase(Configuration.AGREEMENT_CREATED_SUCCESS_PAGE_TITLE)) {
						System.out
								.println(" Agreement is created successfully!!!");
					} else {
						System.out
								.println("Some error has occued while creating agreement!!! please try again!!!");
					}
				} else {
					System.out
							.println("Not on correct page while creating agreement");
				}
			} else {
				System.out.println("Login is not successful");
			}
		} catch (NoAlertPresentException e) {
			System.out
					.println("Some Exception has occured while accpeting the pop-up: "
							+ e.getMessage());
		} catch (Exception e) {
			System.out
					.println("Some Exception has occured while creating the agreement: "
							+ e.getMessage());
		} finally {
			Util.tearDown(driver);
		}

	}
}
