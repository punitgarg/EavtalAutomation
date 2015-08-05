package com.ig.egreement.testing;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import com.ig.agreement.agreementsigning.signagreement.SignAgreement;
import com.ig.agreement.editagreement.DeleteAgreement;
import com.ig.agreement.editagreement.EditAgreement;
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

public class Test {

	static WebDriver driver;

	public static void main(String[] args) throws Exception {

		driver = Util.getWebDriver();

		UserSelection userSel = new UserSelection(driver);

		UserLoginpage userLogin = userSel.login(Configuration.USER_TYPE);

		HomePage homePage;
		try {
			if (userSel.getUserType().equalsIgnoreCase(
					Configuration.PRIVATE_USER)) {

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

			CreateAgreement createAgreement = homePage.createAgreementpage();

			Contractor contractor = "privateUser".equals(userSel.getUserType()) ? PrivateContractor
					.getContractor() : EnterpriseContractor.getContractor();

			if (Util.getpageTitle(driver).equalsIgnoreCase(
					Configuration.AGREEMENT_CREATE_PAGE_TITLE)) {
				CreateAgreementType agreementType = createAgreement
						.createAgreementType();

				agreementType.createAgreement(Configuration.AGREEMENT_NAME,
						userSel.getUserType(), Configuration.UPLOADED_FILE,
						Configuration.ATTACHMENT_LOCATION_URL,
						PrivateContractor.getContractor());
			} else {
				System.out
						.println("Not on correct page while creating agreement");
			}

			EditAgreement agreementEdit = homePage.editAgreementpage();
			System.out.println(":PAGE: " + Util.getpageTitle(driver));
			if (Util.getpageTitle(driver).equalsIgnoreCase(
					Configuration.MY_AGREEMENT_PAGE_TITLE)) {
				agreementEdit.editAgreement(userSel.getUserType(), contractor);
				System.out.println(Util.getpageTitle(driver));
				if (Util.getpageTitle(driver).equalsIgnoreCase(
						Configuration.AGREEMENT_CREATED_SUCCESS_PAGE_TITLE)) {
					System.out
							.println("Contract has been edited successfully ");
				}
			} else {
				System.out
						.println("User is not currently on my Agreement Page and hence cannot edit the agreement");
			}

			DeleteAgreement deleteAgreement = homePage.deleteAgreementpage();
			System.out.println(":PAGE: " + Util.getpageTitle(driver));
			if (Util.getpageTitle(driver).equalsIgnoreCase(
					Configuration.MY_AGREEMENT_PAGE_TITLE)) {
				if (deleteAgreement.deleteRejectedAgreement())
					System.out
							.println("Agreement has been deleted successfully");
				else
					System.out
							.println("Some error occurs while deleting agreement");
			}

			SignAgreement agreementSigner = homePage.signAgreementpage();
			if (Util.getpageTitle(driver).equalsIgnoreCase(
					Configuration.MY_AGREEMENT_PAGE_TITLE)) {
				String signedPage = "";
				if (userLogin.getLoginMode().equalsIgnoreCase(
						Configuration.LOGIN_BANKID)) {
					agreementSigner.signAgreementWithBankId(userSel
							.getBankIDPassword());
				} else if (userLogin.getLoginMode().equalsIgnoreCase(
						Configuration.LOGIN_MOBILEBANKID)) {
					agreementSigner.signAgreementWithMobileBankId(userSel
							.getMobileBankIDIdentificationNumber());
				} else {
					System.out.println(" Wrong Mode is chosen");
				}
				signedPage = Util.getpageTitle(driver);
				if (signedPage
						.equals(Configuration.AGREEMENT_SIGNED_SUCCESS_PAGE_TITLE)) {
					System.out
							.println("Contract has been signed successfully ");
				}
			} else {
				System.out
						.println("User is not currently on my Agreement Page and hence cannot sign the agreement");
			}

		} catch (NoAlertPresentException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// Util.tearDown(driver);
		}
	}
}
