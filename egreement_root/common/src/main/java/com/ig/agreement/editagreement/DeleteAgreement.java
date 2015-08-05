package com.ig.agreement.editagreement;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ig.egreement.common.util.Util;

public class DeleteAgreement {

	public DeleteAgreement(WebDriver driver) {
		System.out.println(":: Edit agreement page :: " + EditAgreement.class);
		this.driver = driver;
	}

	private WebDriver driver;

	Util utilInstance = new Util();

	public static final By LOCATOR_REJECTED_AGREEMENTS_TAB = By.id("rejected");

	public static final By LOCATOR_DRAFTED_AGREEMENTS_TAB = By.id("drafts");

	public static final By LOCATOR_REJECTED_AGREEMENT_LIST = By
			.xpath("//table[@class='tableStyleRejected myAgreementsTable']/tbody/tr");

	public static final By LOCATOR_DRAFTED_AGREEMENT_LIST = By
			.xpath("//table[@class='tableStyleDrafted myAgreementsTable']/tbody/tr");

	public static final By LOCATOR_SUCCESS_MESSAGE = By
			.xpath("//div[@class='successmsg']");

	public static final By LOCATOR_ERROR_MESSAGE = By
			.xpath("//div[@class='errormsg']");

	public boolean deleteRejectedAgreement() {
		boolean isAgreementDeleted = false;
		utilInstance.getWebElement(driver, LOCATOR_REJECTED_AGREEMENTS_TAB)
				.click();
		List<WebElement> originalRejectedAgreementList = rejectedAgreementList(LOCATOR_REJECTED_AGREEMENT_LIST);
		int originalRejectedAgreementListSize = originalRejectedAgreementList
				.size();
		if (originalRejectedAgreementListSize == 0) {
			System.out.println("there is no agreement to be deleted");
			return isAgreementDeleted;
		}
		WebElement rejectedAgreement = originalRejectedAgreementList
				.get(new Random().nextInt(originalRejectedAgreementList.size()));
		rejectedAgreement.findElement(By.xpath("td[5]/a")).click();
		driver.switchTo().alert().accept();
		List<WebElement> successMsgs = rejectedAgreementList(LOCATOR_SUCCESS_MESSAGE);
		if (successMsgs.size() != 0)
			isAgreementDeleted = true;
		return isAgreementDeleted;
	}

	public boolean deleteDraftedAgreement() {
		boolean isAgreementDeleted = false;
		utilInstance.getWebElement(driver, LOCATOR_DRAFTED_AGREEMENTS_TAB)
				.click();
		List<WebElement> originalDraftedAgreementList = rejectedAgreementList(LOCATOR_DRAFTED_AGREEMENT_LIST);
		int originalDraftedAgreementListSize = originalDraftedAgreementList
				.size();
		if (originalDraftedAgreementListSize == 0)
			return isAgreementDeleted;
		WebElement draftedAgreement = originalDraftedAgreementList
				.get(new Random().nextInt(originalDraftedAgreementListSize));
		System.out.println(" Size of list " + originalDraftedAgreementListSize);
		System.out.println("Selected Agreement: " + draftedAgreement.getText());
		// draftedAgreement.findElement(By.xpath(/*"/td[5]/a[contains(@text,'Ta bort')]"*/)).click();
		draftedAgreement.findElement(By.xpath("td[5]/a")).click();
		driver.switchTo().alert().accept();
		List<WebElement> successMsgs = rejectedAgreementList(LOCATOR_SUCCESS_MESSAGE);
		if (successMsgs.size() != 0)
			isAgreementDeleted = true;
		return isAgreementDeleted;
	}

	private List<WebElement> rejectedAgreementList(By locator) {
		return utilInstance.getWebElementList(driver, locator);
	}

}
