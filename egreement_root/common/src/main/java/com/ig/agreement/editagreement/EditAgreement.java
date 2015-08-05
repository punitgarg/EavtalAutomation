package com.ig.agreement.editagreement;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ig.egreement.common.pageobjectrepository.CreateAgreementType;
import com.ig.egreement.common.pageobjectrepository.DyanmicPDFAgreement;
import com.ig.egreement.common.pageobjectrepository.FreeTextAgreement;
import com.ig.egreement.common.pageobjectrepository.MergedAgreement;
import com.ig.egreement.common.util.Configuration;
import com.ig.egreement.common.util.Contractor;
import com.ig.egreement.common.util.PrivateContractor;
import com.ig.egreement.common.util.Util;

public class EditAgreement {

	public EditAgreement(WebDriver driver) {
		System.out.println(":: Edit agreement page :: " + EditAgreement.class);
		this.driver = driver;
	}

	private WebDriver driver;

	Util utilInstance = new Util();

	List<WebElement> draftedAgreements;

	private static final By LOCATOR_EDIT_AGREEMENTS_TAB = By.id("drafts");

	private static final By LOCATOR_DRAFTED_AGREEMENTS = By
			.xpath("//table[@class='tableStyleDrafted myAgreementsTable']/tbody/tr");

	// private static final By LOCATOR_REJECTED_AGREEMENTS = By.id("rejected");

	public String editAgreement(String userType, Contractor contractor) throws NullPointerException, Exception {
		System.out.println(" :: Editing agreement proces has been started :: ");
		try {
			return getAgreementCreatePage().editAgreement(userType, 
					Configuration.EDIT_AGREEMENT_NAME,
					Configuration.AGREEMENT_NAME, Configuration.UPLOADED_FILE,
					Configuration.DELETE_UPLOADED_FILE,
					Configuration.ATTACHMENT_LOCATION_URL,
					contractor);
		} catch (NullPointerException e) {
			throw new NullPointerException(e.getMessage());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	private CreateAgreementType getAgreementCreatePage()
			throws NullPointerException, Exception {
		System.out
				.println(":: Checking if page is agreement showpage while editing the agreement ::");
		draftedAgreements = getDraftAgreementsList();
		System.out.println(":: size of list of draft agreements on page1 ::"
				+ draftedAgreements.size());
		if (draftedAgreements.size() != 0)
			getDraftedAgreement(draftedAgreements).click();
		else
			throw new NullPointerException("There is no agreement to be edited");
		if (Util.getpageTitle(driver).contains("Skapa")) {
			if (Util.getCurrentUrl(driver).contains("FreeText"))
				return new FreeTextAgreement(driver);
			else if (Util.getCurrentUrl(driver).contains("Attached"))
				return new MergedAgreement(driver);
			else
				return new DyanmicPDFAgreement(driver);
		} else {
			System.out
					.println(" :: page other than agreement creation page ::");
			throw new Exception("Page is other than create agreement page");
		}
	}

	private WebElement getDraftedAgreement(List<WebElement> darftAgreementList) {
		/*
		 * WebElement selectedAgreement = darftAgreementList.get(new Random()
		 * .nextInt(darftAgreementList.size()));
		 */
		WebElement selectedAgreement = darftAgreementList.get(6);
		System.out.println(":: selected agreement:: "
				+ selectedAgreement.getText());
		return selectedAgreement.findElement(By.tagName("td"));
	}

	private List<WebElement> getDraftAgreementsList() {

		utilInstance.getWebElement(driver, LOCATOR_EDIT_AGREEMENTS_TAB).click();
		return utilInstance.getWebElementList(driver,
				LOCATOR_DRAFTED_AGREEMENTS);
	}

}
