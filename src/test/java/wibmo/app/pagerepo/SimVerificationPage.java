package wibmo.app.pagerepo;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import library.CSR;
import library.Generic;

import org.openqa.selenium.By;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import library.Log;

/**
 * The Class SimVerificationPage obtained after clicking on Verify SIM under Settings Page
 * Inherits elements and methods from VerifyPhonePage which also performs SIM Verification
 * 
 */
public class SimVerificationPage extends VerifyPhonePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The Sim Verification Status message */
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Your'")
	@AndroidFindBy(id="device_verification_text")
	private WebElement simVerStatusMsg;
	
	@AndroidFindBy(id="verify_button")
	private WebElement verifyBtn;
	
		
	
/** 
	 * Instantiates a new SimVerificationPage.
	 *
	 * @param driver the driver
	 */
	public SimVerificationPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this); 
	}
	
	/**
	 *  Checks whether the Sim is verified or not
	 * 
	 * @return true if Sim is Verified
	 */
	public boolean checkSimVerificationStatus()
	{
		Generic.waitUntilAnyTextVisible(driver, simVerStatusMsg, 30);
		
		String verificationStatusMessage=simVerStatusMsg.getText();
		
		Log.info("======== Verifying SIM verification status :"+verificationStatusMessage+"========");
		if(verificationStatusMessage.contains("Sorry!"))
			Assert.fail("SIM card not detected in phone \n");
		
		return !verificationStatusMessage.contains("not");		
		
	}
	
	/**
	 *  Pass values based on udid
	 * 
	 * @param data
	 */
	public void verifySim(String data)
	{
		String mobileNo=data.split(",")[0],
				   SIMProvider=data.split(",")[2];
		
		Log.info("======== Clicking on verify button ========");
		click(verifyBtn);
		
		for(WebElement d: dropdowns)   // Select any/all Sim and Number dropdowns
		{										 
			d.click();
			Generic.wait(1);
			
			for(WebElement simNumber : selectWithinList) // Select SIM Provider / mobileNo under each dropdown 
			{
				String dropdownElement=simNumber.getText();
				if(Generic.containsIgnoreCase(dropdownElement, SIMProvider) || Generic.containsIgnoreCase(dropdownElement, mobileNo))
				{
					simNumber.click();
					break;
				}
			}		
			try{continueButton.isDisplayed();}catch(Exception e1){Assert.fail(mobileNo+" or "+ SIMProvider+" not found \n"+e1.getMessage());}
		}
		
		continueButton.click();
		
		waitOnProgressBarId(90);
		
		handleSMSAlert();
		
	}
	
	public void navigateBack()
	{
		Log.info("======== Navigating Back ========");
		driver.navigate().back();
		
	}
	
	
	
	
	
}
