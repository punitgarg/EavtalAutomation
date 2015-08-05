package com.ig.agreement.agreementsigning.signagreement;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ig.egreement.common.util.Configuration;
import com.ig.egreement.common.util.Util;

public class SignAgreement {

	public SignAgreement(WebDriver driver) {
		System.out.println(":: Signing agreement page :: "
				+ SignAgreement.class);
		this.driver = driver;
	}

	private WebDriver driver;

	Util utilInstance = new Util();

	List<WebElement> postedAgreements;

	private static final By LOCATOR_CHECKBOX_ONLY_OWNER = By.id("onlyOwner");

	private static final By LOCATOR_CHECKBOX_ONLY_OTHER = By.id("onlyOther");

	private static final By LOCATOR_POSTED_AGREEMENTS_TAB = By.id("posted");

	private static final By LOCATOR_POSTED_AGREEMENTS = By
			.xpath("//table[@class='tableStylePosted myAgreementsTable']/tbody/tr");

	private static final By LOCATOR_SIGN_BUTTON = By.className("normalButton");

	// private static final By LOCATOR_REJECTED_AGREEMENTS = By.id("rejected");

	private static final By LOCATOR_NEXT_BUTTON = By.className("nextToButton");

	public String signAgreementWithBankId(String bankIdPassword)
			throws Exception {
		System.out.println(SignAgreement.class
				+ ":: Signing agreement with BankID has been started :: ");
		try {
			if (getAgreementShowpage())
				return utilInstance.login(bankIdPassword);
			else {
				throw new Exception("Agreement has already been signed");
			}
		} catch (NullPointerException e) {
			throw new NullPointerException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public void signAgreementWithMobileBankId(
			String mobileBankIdIdentificationNumber) throws Exception {
		System.out
				.println(SignAgreement.class
						+ " :: Signing agreement with Mobile BankID has been started :: ");
		if (getAgreementShowpage())
			utilInstance.signAgreementWithMobileBankID(driver,
					mobileBankIdIdentificationNumber);
		else
			throw new Exception("Agreement has already been signed");
	}

	public String signAgreementNormallyWithBankId(String bankIdPassword)
			throws Exception {
		System.out.println(SignAgreement.class
				+ ":: Signing agreement with BankID has been started :: ");
		utilInstance.getWebElement(driver, LOCATOR_SIGN_BUTTON).click();
		return utilInstance.login(bankIdPassword);
	}

	public void signAgreementNormallyWithMobileBankId(
			String mobileBankIdIdentificationNumber) throws Exception {
		System.out
				.println(SignAgreement.class
						+ ":: Signing agreement with Mobile BankID has been started ::");
		utilInstance.getWebElement(driver, LOCATOR_SIGN_BUTTON).click();
		utilInstance.signAgreementWithMobileBankID(driver,
				mobileBankIdIdentificationNumber);
	}

	private boolean getAgreementShowpage() throws NullPointerException,
			Exception {
		boolean isAgreementSigningPending = true;
		selectCheckBox();
		postedAgreements = getPostedAgreementsList();
		if (postedAgreements.size() != 0)
			getPostedAgreement(postedAgreements).click();
		else
			throw new NullPointerException(
					"There are no agreements waiting for sign");
		if (Util.getpageTitle(driver).equalsIgnoreCase(
				Configuration.AGREEMENT_SHOWPAGE_TITLE)) {
			List<WebElement> nextButtons = utilInstance.getWebElementList(
					driver, LOCATOR_NEXT_BUTTON);
			if (nextButtons.size() != 0) {
				nextButtons.get(0).click();
			} else {
				isAgreementSigningPending = false;
			}

		} else {
			throw new Exception("Page is other than AgreementSHowPage");
		}

		return isAgreementSigningPending;
	}

	private void selectCheckBox() {
		System.out
				.println(":: Selecting checkboxes for showing the pending agreements ::");
		if (!utilInstance.getWebElement(driver, LOCATOR_CHECKBOX_ONLY_OWNER)
				.isSelected())
			utilInstance.getWebElement(driver, LOCATOR_CHECKBOX_ONLY_OWNER)
					.click();
		if (!utilInstance.getWebElement(driver, LOCATOR_CHECKBOX_ONLY_OTHER)
				.isSelected())
			utilInstance.getWebElement(driver, LOCATOR_CHECKBOX_ONLY_OTHER)
					.click();
	}

	private WebElement getPostedAgreement(List<WebElement> postedAgreementList) {
		WebElement selectedAgreement = postedAgreementList.get(new Random()
				.nextInt(postedAgreementList.size()));
		System.out.println(":: selected agreement:: "
				+ selectedAgreement.getText());
		return selectedAgreement.findElement(By.tagName("td"));
	}

	private List<WebElement> getPostedAgreementsList() {
		utilInstance.getWebElement(driver, LOCATOR_POSTED_AGREEMENTS_TAB)
				.click();
		return utilInstance
				.getWebElementList(driver, LOCATOR_POSTED_AGREEMENTS);
	}

}
