package com.ig.egreement.testing;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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

public class TestNG_Test {

	WebDriver driver;
	Util utilInstance;
	String loginMode;
	String userType;
	HomePage homePage;
	CreateAgreement createAgreement;
	CreateAgreementType agreementType;

	@BeforeTest
	public void beforeMethod() {
		driver = Util.getWebDriver();
		utilInstance = new Util();
		loginMode = Configuration.LOGIN_MODE;
		userType = Configuration.USER_TYPE;
	}

	@Test
	public void testPrivateUserSelection() {
		utilInstance.getWebElement(driver, Configuration.LOCATOR_PRIVATE_USER)
				.click();
		String actualPageTitle = Util.getpageTitle(driver);
		Assert.assertEquals(actualPageTitle, Configuration.USER_LOGIN_PAGE);
	}

	@Test(dependsOnMethods = "testPrivateUserSelection")
	public void testPrivateUserLogin() {
		UserSelection userSel = new UserSelection(driver);

		UserLoginpage userLogin = userSel.login(Configuration.USER_TYPE);
		try {
			if (userType.equalsIgnoreCase(Configuration.PRIVATE_USER)) {
				homePage = loginPrivateUser(userLogin, userSel);
			} else {
				homePage = loginEnterpriseUser(userLogin, userSel);
			}
			Assert.assertEquals(homePage.getPageTitle(),
					Configuration.HOME_PAGE);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test(dependsOnMethods = "testPrivateUserLogin")
	public void testCreateAgreementPage() {
		System.out.println("HomePage: " + homePage.toString());
		createAgreement = homePage.createAgreementpage();
		Assert.assertEquals(Util.getpageTitle(driver),
				Configuration.AGREEMENT_CREATE_PAGE_TITLE);
	}

	@Test(dependsOnMethods = "testCreateAgreementPage")
	public void testCreateAgreement() {
		agreementType = createAgreement.createAgreementType();
		try {
			String actual = createAgreement(agreementType,
					userType);
			String expected = Configuration.AGREEMENT_CREATED_SUCCESS_PAGE_TITLE;
			Assert.assertEquals(actual, expected);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterTest
	public void afterMethod() {
		driver.quit();
	}

	private HomePage loginEnterpriseUser(UserLoginpage userLogin,
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

	private HomePage loginPrivateUser(UserLoginpage userLogin,
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

}
