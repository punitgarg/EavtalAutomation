package com.ig.egreement.createagreement.freetextagreement;

import org.openqa.selenium.WebDriver;

import com.ig.egreement.common.pageobjectrepository.CreateAgreement;
import com.ig.egreement.common.util.Contractor;
import com.ig.egreement.common.util.Util;

/*
 * Class for creating Free Text Agreement
 */
public class FreeTextAgreement extends CreateAgreement {

	public FreeTextAgreement(WebDriver webdriver) {
		super(webdriver);
		this.driver = webdriver;
		System.out.println(" In Free text Agreement creation class");
	}
	
	private WebDriver driver;

	Util utilInstance = new Util();

	@Override
	public void createAgreement(String name, boolean uploadFile,
			String filePath, Contractor contractor) {
		utilInstance.enterAgreementName(driver, name);
		if (uploadFile) {
			utilInstance.uploadAttachment(driver, filePath);
		}
		utilInstance.saveAgreement(driver);
		if (utilInstance.addContractor(driver, contractor))
			System.out.println("Contractor : " + contractor.toString()
					+ "has been added successfully");
		utilInstance.submitAgreement(driver);
		utilInstance.postAgreement(driver);
		System.out
				.println("Agreement created and email is sent to the parties");
	}
}
