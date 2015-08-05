package com.ig.egreement.common.pageobjectrepository;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ig.egreement.common.util.AgreementType;
import com.ig.egreement.common.util.PrivateContractor;
import com.ig.egreement.common.util.Util;

public class CreateAgreement {

	public CreateAgreement(WebDriver driver) {
		System.out.println(":: Create agreement page ::"
				+ CreateAgreement.class);
		this.driver = driver;
	}

	private WebDriver driver;

	Util utilInstance = new Util();

	private String agreementType;

	public String getAgreementType() {
		return agreementType;
	}

	private static final By LOCATOR_FREE_TEXT_AGREEMENT_LINK = By
			.partialLinkText("ett eget");
	private static final By LOCATOR_DYNAMIC_PDF_LIST = By
			.xpath("//div[@id='bottomContent']//a");

	private static final By LOCATOR_MERGED_AGREEMENT_LINK = By
			.partialLinkText("Ladda upp färdigutformat");

	public void createAgreement(String name, boolean uploadFile,
			String filePath, PrivateContractor contractor) {

	}

	public CreateAgreementType createAgreementType() {
		this.agreementType = setAgreementType();
		if (this.agreementType
				.equalsIgnoreCase(AgreementType.FREE_TEXT_AGREEMENT
						.getAgreeType())) {
			utilInstance
					.getWebElement(driver, LOCATOR_FREE_TEXT_AGREEMENT_LINK)
					.click();
			return new FreeTextAgreement(driver);
		} else if (this.agreementType
				.equalsIgnoreCase(AgreementType.DYNAMIC_PDF_AGREEMENT
						.getAgreeType())) {
			getDynamicPdfTemplate().click();
			return new DyanmicPDFAgreement(driver);
		} else {
			utilInstance.getWebElement(driver, LOCATOR_MERGED_AGREEMENT_LINK)
					.click();
			return new MergedAgreement(driver);
		}

	}

	private String setAgreementType() {

		// String[] agreementTypes = { "mergedAgreement", "dynamicpdfAgreeent",
		// "freetextAgreement" };
		// String selectedMode = (agreementTypes[new Random()
		// .nextInt(agreementTypes.length)]);
		// System.out.println(" :: Mode Selected for agreement creation:: "
		// + selectedMode);
		// return selectedMode;
		return "freetextAgreement";
	}

	private WebElement getDynamicPdfTemplate() {
		List<WebElement> templateList = utilInstance.getWebElementList(driver,
				LOCATOR_DYNAMIC_PDF_LIST);
		return templateList.get(new Random().nextInt(templateList.size()));

	}
}
