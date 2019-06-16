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
 * The Class OTPDialogue used to enter or skip OTP 
 * 
 */
public class OTPDialogue 
{
	/** The driver. */
	private WebDriver driver;
	
	/** The OTP Dialogue Title */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Enter OTP')]")
	private WebElement otpDialogueTitle;
	
	@FindBy(className="android.widget.EditText")
	private WebElement otpTxtField;
	
	@FindBy(xpath="//android.widget.Button[starts-with(@text,'Read SMS')]")
	private WebElement readSMSBtn;
	
	@FindBy(id="approve_textbutton")
	private WebElement submitBtn;
	
	@FindBy(id="cancel_textbutton")
	private WebElement cancelBtn;
	
	@FindBy(className="android.widget.CheckBox")
	private WebElement noOTPChkBox;
	
/** 
	 * Instantiates a new SimVerificationPage.
	 *
	 * @param driver the driver
	 */
	public OTPDialogue(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(25)),this); 
	}
	
	public void enterOTPFromSMS(int smsWaitSeconds)
	{
		otpDialogueTitle.isDisplayed(); 
		
		Log.info("======== Waiting for SMS =======");
		Generic.wait(smsWaitSeconds);
		
		Log.info("======== Clicking on Read SMS button =======");
		readSMSBtn.click();
		
		clickSubmit();
	}
	
	public void enterOTPFromAPI(String eventId)
	{
				
		 // Generic.getOTP
		//  Update eventid in config file
		//  enter into txtfield 
		// clicksubmit
		
	}
	
	public void clickSubmit()
	{
		Log.info("======== Clicking on Submit button =======");
		submitBtn.click();		
	}
	
	public void navigateBack()
	{
		Log.info("======== Navigating Back ========");
		driver.navigate().back();
		
	}
	
	
	
	
	
}
