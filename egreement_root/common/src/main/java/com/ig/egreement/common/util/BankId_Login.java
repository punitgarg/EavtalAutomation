package com.ig.egreement.common.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BankId_Login {
	
	/*public BankId_Login(WebDriver driver) {
		super();
		this.driver1 = driver;
	}*/


	WebDriver driver1;
	
	private int mobileIdentity;

	public void setUp() throws MalformedURLException{
		//Set up desired capabilities and pass the Android app-activity and app-package to Appium
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		capabilities.setCapability("BROWSER_NAME", "Android"); 
		capabilities.setCapability("deviceName","Android");
		capabilities.setCapability("platformName","Android");
		capabilities.setCapability("device","Android");
	 
		capabilities.setCapability("appPackage", "com.bankid.bus");
		capabilities.setCapability("appActivity","com.bankid.bus.activities.InitActivity");
		capabilities.setCapability("appWaitActivity","com.bankid.bus.activities.InitActivity, com.bankid.bus.activities.ActivationWaitActivity, com.bankid.bus.activities.SignIdleActivity, com.bankid.bus.application.BankIDActivity");
		capabilities.setCapability("appWaitPackage", "com.bankid.bus");
		
	 
		driver1 = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}

	public void enterIdentificationNumberforLogin(String mobileIdentificationNumber) throws Exception {
		
		mobileIdentity = Integer.parseInt(mobileIdentificationNumber);
		
		System.out.println(" Entered into TestCall for login");
		
		WebDriverWait wait = new WebDriverWait(driver1, 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text='1']")));
	   WebElement one=driver1.findElement(By.xpath("//android.widget.TextView[@text='1']"));
	   WebElement four=driver1.findElement(By.xpath("//android.widget.TextView[@text='4']"));
	   WebElement seven=driver1.findElement(By.xpath("//android.widget.TextView[@text='7']"));
	   WebElement eight=driver1.findElement(By.xpath("//android.widget.TextView[@text='8']"));
	   WebElement nine=driver1.findElement(By.xpath("//android.widget.TextView[@text='9']"));
	   WebElement six=driver1.findElement(By.xpath("//android.widget.TextView[@text='6']"));
	   WebElement identify=driver1.findElement(By.xpath("//android.widget.TextView[@text='Iden- tify']"));
	   one.click();
	   four.click();
	   seven.click();
	   eight.click();
	   nine.click();
	   six.click();
	   identify.click();
	}

	public void enterIdentificationNumberforSigning(String mobileIdentificationNumber) throws Exception {
		
		mobileIdentity = Integer.parseInt(mobileIdentificationNumber);
		
		System.out.println(" Entered into TestCall for signing");
		
		WebDriverWait wait = new WebDriverWait(driver1, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.className("android.widget.EditText")));
	   WebElement securityCode = driver1.findElement(By.className("android.widget.EditText"));
	   securityCode.click();
	   System.out.println(" Entered into TestCall for signing and entering pin");
	   wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text='1']")));
	   WebElement one=driver1.findElement(By.xpath("//android.widget.TextView[@text='1']"));
	   WebElement four=driver1.findElement(By.xpath("//android.widget.TextView[@text='4']"));
	   WebElement seven=driver1.findElement(By.xpath("//android.widget.TextView[@text='7']"));
	   WebElement eight=driver1.findElement(By.xpath("//android.widget.TextView[@text='8']"));
	   WebElement nine=driver1.findElement(By.xpath("//android.widget.TextView[@text='9']"));
	   WebElement six=driver1.findElement(By.xpath("//android.widget.TextView[@text='6']"));
	   WebElement sign=driver1.findElement(By.xpath("//android.widget.TextView[@text='Sign']"));
	   /*UiObj*/
	   
	   one.click();
	   four.click();
	   seven.click();
	   eight.click();
	   nine.click();
	   six.click();
	   sign.click();
	   
	}
	
	
	public void teardown() {
		//close the app
		driver1.quit();
	}
	}

