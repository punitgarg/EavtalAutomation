package com.ig.egreement.common.pageobjectrepository;

import org.openqa.selenium.WebDriver;

import com.ig.egreement.common.util.Configuration;
import com.ig.egreement.common.util.Contractor;
import com.ig.egreement.common.util.Util;

/*
 * Class for creating Free Text Agreement
 */
public class MergedAgreement extends CreateAgreementType {

	public MergedAgreement(WebDriver webdriver) {
		super(webdriver);
		this.driver = webdriver;
		System.out.println(":: MergedAgreement page :: "
				+ MergedAgreement.class);
	}

	private WebDriver driver;

	Util utilInstance = new Util();

	@Override
	public String createAgreement(String name, String userType, boolean uploadFile,
			String filePath, Contractor contractor) throws Exception {
		String createAgreementPage = "";
		utilInstance.editAgreementName(driver, name);
		if (utilInstance.uploadAttachment(driver, filePath))
			System.out
					.println(" File uploaded successfully or is already uploaded");
		else
			System.out.println(" some error occurs while uploading file");
		if (utilInstance.saveMergedAgreement(driver, filePath)) {
			if (utilInstance.addContractor(driver, userType, contractor))
				System.out.println(" :: Contractor : " + contractor.toString()
						+ "has been added successfully");
			else {
				System.out
						.println(" :: Some error occured while adding contractor:: "
								+ contractor.toString());
			}
			if (utilInstance.submitAgreement(driver)) {
				createAgreementPage = utilInstance.postAgreement(driver);
			} else {
				System.out
						.println(":: Some error has occured while submitting the agreement after adding the contractors::");
			}
		} else {
			System.out
					.println(" Some error has occured while saving the agreement and current page is not addContractor page");
		}
		return createAgreementPage;
	}

	@Override
	public String editAgreement(String userType, boolean editAgreementName, String name,
			boolean uploadFile, boolean deleteUploadedFile, String filePath,
			Contractor contractor) throws Exception {
		String editAgreementPage = "";
		if (editAgreementName)
			utilInstance.editAgreementName(driver, name);

		if (deleteUploadedFile) {
			utilInstance.deleteFile(Configuration.DELETE_FILE_NAME);
		}

		if (utilInstance.uploadAttachment(driver, filePath))
			System.out
					.println(" File uploaded successfully or is already uploaded");
		else
			System.out.println(" some error occurs while uploading file");
		/*
		 * if (utilInstance.getUploadedFilesList().size() == 0){
		 * 
		 * }
		 */
		if (utilInstance.saveMergedAgreement(driver, filePath)) {
			if (utilInstance.addContractor(driver, userType, contractor))
				System.out.println(" :: Contractor : " + contractor.toString()
						+ "has been added successfully");
			else {
				System.out
						.println(" :: Some error occured while adding contractor:: "
								+ contractor.toString());
			}
			if (utilInstance.submitAgreement(driver)) {
				editAgreementPage = utilInstance.postAgreement(driver);
			} else {
				System.out
						.println(":: Some error has occured while submitting the agreement after adding the contractors::");
			}
		} else {
			System.out
					.println(" Some error has occured while saving the agreement and current page is not addContractor page");
		}
		return editAgreementPage;
	}
}
