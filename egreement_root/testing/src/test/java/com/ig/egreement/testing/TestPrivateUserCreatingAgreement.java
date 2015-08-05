package com.ig.egreement.testing;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import com.ig.agreement.agreementsigning.signagreement.SignAgreement;
import com.ig.egreement.common.pageobjectrepository.CreateAgreement;
import com.ig.egreement.common.pageobjectrepository.CreateAgreementType;
import com.ig.egreement.common.pageobjectrepository.HomePage;
import com.ig.egreement.common.pageobjectrepository.UserLoginpage;
import com.ig.egreement.common.pageobjectrepository.UserSelection;
import com.ig.egreement.common.util.Configuration;
import com.ig.egreement.common.util.Contractor;
import com.ig.egreement.common.util.EnterpriseContractor;
import com.ig.egreement.common.util.PrivateContractor;
import com.ig.egreement.common.util.Util;

public class TestPrivateUserCreatingAgreement {

	final static Logger logger = Logger
			.getLogger(TestPrivateUserCreatingAgreement.class);

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
				System.out.println("Login is successful");

				CreateAgreement createAgreement = homePage
						.createAgreementpage();
				if (Util.getpageTitle(driver).equalsIgnoreCase(
						Configuration.AGREEMENT_CREATE_PAGE_TITLE)) {
					CreateAgreementType agreementType = createAgreement
							.createAgreementType();
					String agreementPostedPage = createAgreement(agreementType,
							userType);
					if (agreementPostedPage
							.equalsIgnoreCase(Configuration.AGREEMENT_CREATED_SUCCESS_PAGE_TITLE)) {
						System.out
								.println(" Agreement is created successfully!!!");
						if (Configuration.SIGN_AGREEMENT_WHILE_CREATING) {
							signAgreement(userSel);
							Thread.sleep(5000);
							String signedPage = Util.getpageTitle(driver);
							System.out.println("Signed Page: " + signedPage);
							if (signedPage
									.equals(Configuration.AGREEMENT_SIGNED_SUCCESS_PAGE_TITLE)) {
								System.out
										.println(" :: Contract has been signed successfully ");
							} else {
								System.out.println("Signing failed");
							}
						}
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
					.println("Some Exception has occured while creating the agreement: "
							+ e.getMessage());
		} catch (Exception e) {
			System.out
					.println("Some Exception has occured while creating the agreement: "
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

	private static String createAgreement(CreateAgreementType agreementType,
			String userType) throws Exception {
		System.out.println("UserType: " + userType);
		Contractor contractor = Configuration.PRIVATE_USER
				.equalsIgnoreCase(userType) ? PrivateContractor.getContractor()
				: EnterpriseContractor.getContractor();
		System.out.println(" Contractor Type: " + contractor.getClass());
		return agreementType.createAgreement(Configuration.AGREEMENT_NAME,
				userType, Configuration.UPLOADED_FILE,
				Configuration.ATTACHMENT_LOCATION_URL, contractor);
	}

	private static void signAgreement(UserSelection userSel) throws Exception {
		if (loginMode.equalsIgnoreCase(Configuration.LOGIN_BANKID)) {
			new SignAgreement(driver).signAgreementNormallyWithBankId(userSel
					.getBankIDPassword());
		} else {
			new SignAgreement(driver)
					.signAgreementNormallyWithMobileBankId(userSel
							.getMobileBankIDIdentificationNumber());
		}

	}
}
