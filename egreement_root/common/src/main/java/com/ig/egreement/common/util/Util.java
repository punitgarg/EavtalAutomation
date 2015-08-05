package com.ig.egreement.common.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Util {

	public static WebDriver driver;

	private static final By LOCATOR_CONTRACTOR_FIRST_NAME = By.id("name");

	private static final By LOCATOR_CONTRACTOR_LAST_NAME = By.id("lastName");

	private static final By LOCATOR_CONTRACTOR_EMAIL_ID = By.id("email");

	private static final By LOCATOR_CONTRACTOR_PERSON_NUMBER = By
			.id("personNumber");

	private static final By LOCATOR_AGREEMENT_NAME = By.id("name");

	private static final By LOCATOR_FILE_UPLOAD_BUTTON = By.id("file_upload");

	private static final By LOCATOR_SAVE_NEXT_BUTTON = By
			.id("saveAndNextButton");

	private static final By LOCATOR_ADD_CONTRACTOR = By.id("create");

	private static final By LOCATOR_SUBMIT_BUTTON = By
			.id("daysUntilRejectionFormSubmit");

	private static final By LOCATOR_ERROR_MSG = By
			.xpath("//label[@class='error']");

	private static final By LOCATOR_ERROR = By
			.xpath("//div[@class='errormsg']");

	private static final By LOCATOR_ERROR_NO_CONTRACTOR = By
			.partialLinkText("Avtalet måste innehålla minst en avtalspart");

	private static final By LOCATOR_POST_AGREEMENT = By.id("postAgreement");

	// TODO : to correct id of the textbox
	private static final By LOCATOR_MOBILE_BANK_ID_TEXTBOX = By
			.id("visiblePnr");

	private static final By LOCATOR_MOBILE_BANK_ID_TEXTBOX_SUBMIT_BUTTON = By
			.xpath("//input[@class='btn btn-default dropdown-toggle submit']");

	private static final By LOCATOR_CONTRACTOR_TYPE = By.id("partyAddSelector");

	private static final By LOCATOR_ENTERPRISE_CONTRACTOR_ORGNAIZATION_NUMBER = By
			.id("organizationNumber");

	private static final By LOCATOR_ENTERPRISE_CONTRACTOR_REFERENCE_PERSON_NAME = By
			.id("referencePersonName");

	private static final By LOCATOR_ENTERPRISE_CONTRACTOR_REFERENCE_EMAIL = By
			.id("referenceEmail");

	private static final By LOCATOR_PRIVATE_CONTRACTOR_FIRST_NAME = By
			.id("firstName");

	/*
	 * return static instance of Web driver which will be used throughout the
	 * application
	 */
	public static WebDriver getWebDriver() {
		if (driver == null) {
			driver = new FirefoxDriver();
			driver.get(Configuration.APLLICATION_URL);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			// driver.manage().window().maximize();
		}
		return driver;
	}

	/*
	 * Return the required web element
	 */
	public WebElement getWebElement(WebDriver currentDriver, By by) {
		return currentDriver.findElement(by);
	}

	/*
	 * Return the list of web elements
	 */
	public List<WebElement> getWebElementList(WebDriver currentDriver, By by) {
		return currentDriver.findElements(by);
	}

	/*
	 * To check if alert is present
	 */
	public boolean checkAlert() throws NoAlertPresentException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		if (wait.until(ExpectedConditions.alertIsPresent()) != null)
			return true;
		else {
			throw new NoAlertPresentException("No Alert is present");
		}
	}

	/*
	 * Set the agreement name
	 */
	public void editAgreementName(WebDriver driver, String name) {
		WebElement nameField = this.getWebElement(driver,
				LOCATOR_AGREEMENT_NAME);
		nameField.clear(); // to clear the default agreement name in case
		// agreement is created by dynamic pdf template
		nameField.sendKeys(name);
	}

	/*
	 * Upload attachment to the agreement
	 */
	public boolean uploadAttachment(WebDriver driver, String filepath) {
		boolean fileuploadSuccess = false;
		String fileName = filepath.substring(filepath.lastIndexOf("\\") + 1);
		System.out.println("File To be uploaded is:: " + fileName);
		if (!checkIfFileAlreadyUploaded(fileName)) {
			this.getWebElement(driver, LOCATOR_FILE_UPLOAD_BUTTON).click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.enterValueIntoWindowPopUp(filepath);
			if (isFileUploadSuccessful(fileName))
				fileuploadSuccess = true;
		} else {
			System.out.println(" File is already uploaded");
			fileuploadSuccess = true;
		}
		return fileuploadSuccess;
	}

	/*
	 * Submit agreement after uploading attachment and adding contractor
	 */
	public boolean submitAgreement(WebDriver driver) {
		this.getWebElement(driver, LOCATOR_SUBMIT_BUTTON).click();
		if (getpageTitle(driver).equalsIgnoreCase(
				Configuration.AGREEMENT_POSTING_PAGE_TITLE))
			return true;
		else
			return false;
	}

	/*
	 * Add Contractor/party to the agreement
	 */
	public boolean addContractor(WebDriver driver, String userType,
			Contractor contractor) {
		if (userType.equalsIgnoreCase(Configuration.PRIVATE_USER)) {
			this.setPrivateContractor((PrivateContractor) contractor, userType);
		} else {
			Select selectContractorType = new Select(getWebElement(driver,
					LOCATOR_CONTRACTOR_TYPE));
			if (Configuration.CONTRACTOR_TYPE.equalsIgnoreCase("enterprise")) {
				EnterpriseContractor cont = (EnterpriseContractor) contractor;
				selectContractorType.selectByVisibleText("Företag");
				getWebElement(driver,
						LOCATOR_ENTERPRISE_CONTRACTOR_ORGNAIZATION_NUMBER)
						.sendKeys(cont.getOrgNumber());
				getWebElement(driver,
						LOCATOR_ENTERPRISE_CONTRACTOR_REFERENCE_PERSON_NAME)
						.sendKeys(cont.getReferencePerson());
				getWebElement(driver,
						LOCATOR_ENTERPRISE_CONTRACTOR_REFERENCE_EMAIL)
						.sendKeys(cont.getEmailId());
			} else {
				selectContractorType.selectByVisibleText("Privatperson");
				this.setPrivateContractor((PrivateContractor) contractor,
						userType);
			}
		}
		this.getWebElement(driver, LOCATOR_ADD_CONTRACTOR).click();
		return isContractorAdditionSuccessful(driver);
	}

	private boolean isContractorAdditionSuccessful(WebDriver driver) {

		boolean isContractorAddedSuccessfull = false;
		try {
			List<WebElement> errorMsg = this.getWebElementList(driver,
					LOCATOR_ERROR);
			List<WebElement> errorMsgs = this.getWebElementList(driver,
					LOCATOR_ERROR_MSG);
			if (errorMsg.size() != 0 || errorMsgs.size() != 0) {
				System.out
						.println("Error occurred while adding contractor: "
								+ errorMsgs.size()
								+ "contractor already added: "
								+ errorMsg.get(0).getText());
			} else
				isContractorAddedSuccessfull = true;
		} catch (NoSuchElementException e) {
			System.out
					.println(" Contractor has been added successfully and no exception hs occured");
			isContractorAddedSuccessfull = true;
		}
		return isContractorAddedSuccessfull;
	}

	/*
	 * Set Contractor details
	 */
	private void setPrivateContractor(PrivateContractor contractor,
			String userType) {
		if (userType.equalsIgnoreCase(Configuration.PRIVATE_USER)) {
			this.getWebElement(driver, LOCATOR_CONTRACTOR_FIRST_NAME).sendKeys(
					contractor.getFirstName());
		} else {
			this.getWebElement(driver, LOCATOR_PRIVATE_CONTRACTOR_FIRST_NAME)
					.sendKeys(contractor.getFirstName());
		}
		this.getWebElement(driver, LOCATOR_CONTRACTOR_LAST_NAME).sendKeys(
				contractor.getLastName());
		this.getWebElement(driver, LOCATOR_CONTRACTOR_PERSON_NUMBER).sendKeys(
				contractor.getPersonNumber());
		this.getWebElement(driver, LOCATOR_CONTRACTOR_EMAIL_ID).sendKeys(
				contractor.getEmailId());
	}

	/*
	 * Post Agreement and send email to parties
	 */
	public String postAgreement(WebDriver driver) {
		this.getWebElement(driver, LOCATOR_POST_AGREEMENT).click();
		return getpageTitle(driver);
	}

	/*
	 * Save and submit agreement after entering name, uploading file and other
	 * details
	 */
	public boolean saveAgreement(WebDriver driver) {
		this.getWebElement(driver, LOCATOR_SAVE_NEXT_BUTTON).click();
		if (getpageTitle(driver).equalsIgnoreCase(
				Configuration.ADD_CONTRACTOR_PAGE_TITLE))
			return true;
		else
			return false;
	}

	/*
	 * For Merged Agreement
	 */
	public boolean saveMergedAgreement(WebDriver driver, String filepath)
			throws Exception {
		boolean checkContractorPage = false;
		if (getUploadedFilesList().size() != 0) {
			System.out
					.println("File is already uploaded while creating/editing the merged agreement");
			this.getWebElement(driver, LOCATOR_SAVE_NEXT_BUTTON).click();
			if (getpageTitle(driver).equalsIgnoreCase(
					Configuration.ADD_CONTRACTOR_PAGE_TITLE))
				checkContractorPage = true;
		} else {
			System.out
					.println("No File is uploaded while creating/editing the merged agreement and hence is being uploaded");
			if (uploadAttachment(driver, filepath)) {
				System.out
						.println("File has been uploaded successfully while creating/editing merged agreement");
				this.getWebElement(driver, LOCATOR_SAVE_NEXT_BUTTON).click();
				if (getpageTitle(driver).equalsIgnoreCase(
						Configuration.ADD_CONTRACTOR_PAGE_TITLE))
					checkContractorPage = true;
			} else {
				System.out
						.println(" There is some exception in uploading file while creating/editing merged agreement. Try again with some other file");
				throw new Exception(
						"Exception while uploading image to Merged Agreement");
			}
		}
		return checkContractorPage;
	}

	public static void tearDown(WebDriver driver) {
		driver.close();
	}

	/*
	 * to accept pop-up of BankID app and entering password
	 */
	public String login(final String password) {
		/* Thread.sleep(5000); */
		try {
			Runtime.getRuntime().exec("C:\\help\\help.bat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (acceptPopup()) {
					timer.schedule(new TimerTask() {
						@Override
						public void run() {

						}
					}, 30);
				}
			}
		}, 0);
		return getpageTitle(driver);
	}

	/*
	 * Login using Person No and mobile identification no
	 */
	public void login(WebDriver driver, String personNumber,
			String mobileIdentificationNumber) throws Exception {

		this.getWebElement(driver, LOCATOR_MOBILE_BANK_ID_TEXTBOX).sendKeys(
				personNumber);
		this.getWebElement(driver, LOCATOR_MOBILE_BANK_ID_TEXTBOX_SUBMIT_BUTTON)
				.click();
		BankId_Login mobileID = new BankId_Login();
		mobileID.setUp();
		mobileID.enterIdentificationNumberforLogin(mobileIdentificationNumber);
		mobileID.teardown();
	}

	public void signAgreementWithMobileBankID(WebDriver driver,
			String mobileIdentificationNumber) throws Exception {
		BankId_Login mobileID = new BankId_Login();
		/* BankId_Login mobileID = new BankId_Login(driver); */
		mobileID.setUp();
		mobileID.enterIdentificationNumberforSigning(mobileIdentificationNumber);
		/* mobileID.teardown(); */
		/* return getpageTitle(driver); */

	}

	/*
	 * To enter the bankIdPassword or uploading attachment in the agreement
	 * using windows pop-up and robot class
	 */
	private void enterValueIntoWindowPopUp(String password) {
		System.out.println("Using Robot Class");
		setClipboardData(password);
		try {
			Robot robot = new Robot();
			robot.delay(2000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Method to copy the file location on clipboard
	 */
	private static void setClipboardData(String password) {
		StringSelection stringSelection = new StringSelection(password);
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(stringSelection, stringSelection);
	}

	// method for accepting the window pop, if any
	private boolean acceptPopup() {

		System.out.println(" In Click Enter" + System.currentTimeMillis());
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			return true;
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	/*
	 * Return the title of the current page
	 */
	public static String getpageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getWindowHandle(WebDriver driver) {
		return driver.getWindowHandle();
	}

	public static String getCurrentUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	private boolean checkIfFileAlreadyUploaded(String fileName) {
		boolean fileAlreadyUploaded = false;
		List<WebElement> listFiles = getUploadedFilesList();
		if (listFiles.size() == 0) {
			return false;
		}
		int i = 0;
		for (WebElement we : listFiles) {
			i++;
			if (we.getText().contains(fileName)) {
				fileAlreadyUploaded = true;
				break;
			} else {
				if (i == listFiles.size()) {
					System.out.println(fileName
							+ " is not already uploaded");
					return false;
				}
			}
		}
		return fileAlreadyUploaded;
	}

	private boolean isFileUploadSuccessful(String fileName) {
		boolean fileuploadSuccess = false;
		List<WebElement> listFiles = getWebElementList(driver,
				By.xpath("//ul[@id='uploadedFiles']/li"));
		for (WebElement we : listFiles) {
			if (we.getText().contains(fileName)) {
				fileuploadSuccess = true;
				break;
			}
		}
		return fileuploadSuccess;
	}

	/*
	 * public static boolean checkCorrectPage(WebDriver driver, String
	 * expectedPage) { if (getpageTitle(driver).equalsIgnoreCase(expectedPage))
	 * { return true; } else return false; }
	 */
	public boolean deleteFile(String fileName) {
		List<WebElement> uploadedFiles = getUploadedFilesList();
		System.out.println(" size of uploaded files list before deletion : "
				+ uploadedFiles.size());
		if (uploadedFiles.size() == 0) {
			System.out
					.println(" size of uploadedfiles List is zero and hence returning");
			return false;
		}
		System.out.println(" File to be deleted: " + fileName);
		int i = 0;
		for (WebElement we : uploadedFiles) {
			i++;
			if (we.getText().contains(fileName)) {
				we.findElement(
						By.xpath("//a[contains(@href,'/attachment/deleteAttachment')]"))
						.click();
				break;
			} else {
				if (i == uploadedFiles.size()) {
					System.out.println(fileName
							+ " is not present in the uploaded files");
					return false;
				}
			}
		}
		return checkIfFileDeleted(fileName);
	}

	public List<WebElement> getUploadedFilesList() {
		List<WebElement> listFiles = getWebElementList(driver,
				By.xpath("//ul[@id='uploadedFiles']/li"));
		/*
		 * System.out.println(" size of uploaded files list: " +
		 * listFiles.size());
		 */
		return listFiles;
	}

	private boolean checkIfFileDeleted(String fileName) {
		boolean fileDeletedSuccessful = true;
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<WebElement> uploadedFiles = getUploadedFilesList();
		System.out.println("Size of uploaded files list after deletion: "
				+ uploadedFiles.size());
		for (WebElement we : uploadedFiles) {
			if (we.getText().contains(fileName)) {
				fileDeletedSuccessful = false;
				break;
			}
		}
		if (fileDeletedSuccessful)
			System.out.println(" File deletion is successful");
		else
			System.out.println(" File deletion is not successful!!! try again");
		return fileDeletedSuccessful;
	}

	public List<WebElement> getContractorAdditionErrorList() {
		return getWebElementList(driver, LOCATOR_ERROR_NO_CONTRACTOR);
	}

	/*
	 * public boolean isAnyContractorAdded(){
	 * 
	 * boolean isContractorAdded = true;
	 * 
	 * return false; }
	 */
}
