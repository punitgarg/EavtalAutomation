package com.ig.egreement.common.pageobjectrepository;

import org.openqa.selenium.WebDriver;

import com.ig.egreement.common.util.Contractor;
import com.ig.egreement.common.util.Util;

public class CreateAgreementType {

	public CreateAgreementType(WebDriver driver) {
		System.out.println(":: CreateAgreementPage ::"
				+ CreateAgreementType.class);
		this.driver = driver;
	}

	private WebDriver driver;

	public String createAgreement(String name, String userType,
			boolean uploadFile, String filePath, Contractor contractor)
			throws Exception {
		return Util.getpageTitle(driver);

	}

	public String editAgreement(String userType, boolean editAgreementName,
			String name, boolean uploadFile, boolean deleteUploadedFile,
			String filePath, Contractor contractor) throws Exception {
		return Util.getpageTitle(driver);

	}

	Util utilInstance = new Util();
}