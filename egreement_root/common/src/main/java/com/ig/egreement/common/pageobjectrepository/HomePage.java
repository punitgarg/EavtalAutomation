package com.ig.egreement.common.pageobjectrepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.ig.agreement.agreementsigning.signagreement.SignAgreement;
import com.ig.agreement.editagreement.DeleteAgreement;
import com.ig.agreement.editagreement.EditAgreement;
import com.ig.egreement.common.util.Util;

public class HomePage {
	public HomePage(WebDriver webDriver) {
		this.driver = webDriver;
		System.out.println(":: HomePage page :: " + HomePage.class);
		System.out.println("HomePage Title: " + Util.getpageTitle(driver));
	}

	private WebDriver driver;

	Util utilInstance = new Util();

	private static final By LOCATOR_CREATE_AGREEMENT = By
			.linkText("Skapa avtal");

	private static final By LOCATOR_MY_AGREEMENTS = By
			.partialLinkText("Mina avtal");

	public CreateAgreement createAgreementpage() {
		utilInstance.getWebElement(driver, LOCATOR_CREATE_AGREEMENT).click();
		return new CreateAgreement(driver);
	}

	public SignAgreement signAgreementpage() {
		goToMyAgreements();
		return new SignAgreement(driver);
	}
	
	public EditAgreement editAgreementpage(){
		goToMyAgreements();
		return new EditAgreement(driver);
	}
	
	public DeleteAgreement deleteAgreementpage(){
		goToMyAgreements();
		return new DeleteAgreement(driver);
	}
	
	public String getPageTitle(){
		return Util.getpageTitle(driver);
	}
	
	private void goToMyAgreements(){
		utilInstance.getWebElement(driver, LOCATOR_MY_AGREEMENTS).click();
	}

}
