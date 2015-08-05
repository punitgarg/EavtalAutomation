package com.ig.egreement.common.util;

import org.openqa.selenium.By;

public class Configuration {

	public static final By LOCATOR_PRIVATE_USER = By
			.xpath("//a[contains(@class,'nextToButtonLogin')]");

	public static final By LOCATOR_ENTERPRISE_USER = By
			.xpath("//a[contains(@class,'backToButtonLogin')]");
	
	public static final String USER_LOGIN_PAGE = "E-avtal | Elektronisk underskrift | Elektroniska avtal";

	public static final String APLLICATION_URL = "http://staging-eavtal.qa3.intelligrape.net/";
	public static final String PRIVATE_USER = "privateUser";
	public static final String ENTERPRISE_USER = "enterpriseUser";
	public static final String USER_TYPE = PRIVATE_USER;

	public static final String DROPDOWN_VALUE_BANKID = "6";
	public static final String DROPDOWN_VALUE_MOBILE_BANKID = "7";

	public static final String DROPDOWN_TEXT_BANKID = "e-leg: BankID/Nordea";
	public static final String DROPDOWN_TEXT_MOBILE_BANKID = "e-leg: Mobilt BankID";

	public static final String LOGIN_MODE = "mobilebankid";
	public static final String LOGIN_MOBILEBANKID = "mobilebankid";
	public static final String LOGIN_BANKID = "bankid";

	public static final String USER_BANKID_PASSWORD = "abcde12345";
	public static final String USER_MOBILE_BANK_ID_PERSON_NO = "198601025037";
	public static final String USER_MOBILE_BANK_ID_IDENTIFICATION_NO = "147896";
	public static final String ORG_NUMBER = "986586-9656";

	public static final String AGREEMENT_NAME = "Test_Agreement_Automation";
	// Contractor details
	public static final String CONTRACTOR_FIRST_NAME = "Neetu";
	public static final String CONTRACTOR_LAST_NAME = "Neetu";
	public static final String CONTRACTOR_PERSON_NUMBER = "198601025029"/* "198601025037" */;
	public static final String CONTRACTOR_EMAIL_ID = "punit.garg@tothenew.com";
	//
	public static final String HOME_PAGE = "Min sida";

	public static final String AGREEMENT_SHOWPAGE_TITLE = "Visa Avtal";

	public static final String AGREEMENT_CREATE_PAGE_TITLE = "Skapa avtal";

	public static final String AGREEMENT_CREATED_SUCCESS_PAGE_TITLE = "Utskickat avtal";

	public static final String MY_AGREEMENT_PAGE_TITLE = "Mina avtal";

	public static final String AGREEMENT_SIGNED_SUCCESS_PAGE_TITLE = "Bekräftelse på avtalsunderskrift";

	public static final String ADD_CONTRACTOR_PAGE_TITLE = "Visa Avtalsparter";

	public static final String AGREEMENT_POSTING_PAGE_TITLE = "Granska";

	// Configuration for deleting the uploaded image

	public static final boolean DELETE_UPLOADED_FILE = false;

	public static final String DELETE_FILE_NAME = "Desert.jpg";

	// Configuration for uploading attachment
	public static final boolean UPLOADED_FILE = false;

	public static final String ATTACHMENT_LOCATION_URL = "C:\\Users\\Punit Garg\\Desktop\\Desert.jpg";

	public static final boolean EDIT_AGREEMENT_NAME = false;

	public static final boolean SIGN_AGREEMENT_WHILE_CREATING = true;

	public static final String CONTRACTOR_TYPE = "enterprise";

	// Enterprise Contractor DETAILS

	public static final String ENTERPRISE_CONTRACTOR_ORG_NUMBER = "111111-1111";

	public static final String ENTERPRISE_CONTRACTOR_REFERENCE_PERSON = "Neetu";

	public static final String ENTERPRISE_CONTRACTOR_EMAIL = "punit.garg@tothenew.com";

}
