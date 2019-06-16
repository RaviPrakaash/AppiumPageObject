package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;


import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class ForgotPinStep1Page used to enter the mobile number and request for OTP.
 */
public class ForgotPinStep1Page extends BasePage   // Consolidated for all Step1 Step2 Step3
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The functionality description text for the page. */
	@FindBy(id="fl_main_helo")
	private WebElement descriptionTitle;
	
	/** The mobile email textfield. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="fl_text_username")
	private WebElement mobileEmailTextfield;
	
	/** The send otp button. */
	@iOSXCUITFindBy(iOSNsPredicate="name contains 'OTP'")
	@AndroidFindBy(id="fl_btnReqOtp")
	private WebElement sendOtpButton;
	
	/** The otp text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[2]")
	@AndroidFindBy(id="main_mbOtp_edit")
	private WebElement otpTextField;
	
	/** The read sms button. */
	@FindBy(id="main_readsms_button")
	private WebElement readSmsButton;
	
	/** The submit button. */
	@iOSXCUITFindBy(accessibility="Submit")
	@AndroidFindBy(id="fl_btnSubmit")
	private WebElement submitButton;
	
	/** OTP Send Success Alert title */
	@iOSXCUITFindBy(iOSNsPredicate="value='Success!'")
	private WebElement otpSentAlertTitle;
	
	/** OTP Send Success Alert message */
	@iOSXCUITFindBy(iOSNsPredicate="value contains'Your One Time Password'")
	private WebElement otpSentAlertMsg;
	
	/**
	 * Instantiates a new ForgotPinStep1Page.
	 *
	 * @param driver the driver
	 */
	public ForgotPinStep1Page(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);	}
	
	public void navigateBack(WebDriver driver)
	{
			navigateLink.click();
			Log.info("======== Navigate back button is clicked to cancel forgot PIN======== ");
	}
	/**
	 * Enters the mobile number and requests for OTP, if number is present in a different device, OTP is retrieved from DB API based on the bankCode.
	 *
	 * @param mobileEmail the mobile email
	 * @param bankCode the bank code
	 */
	public void changePin(String mobileEmail,String bankCode)
	{
		boolean otpReceived=false;
		
		Log.info("======== Entering Mobile Number : "+mobileEmail+"========");
		if(!mobileEmailTextfield.getText().equals(mobileEmail))
		    mobileEmailTextfield.sendKeys(mobileEmail);
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Clicking on Send OTP buttton ========");
		sendOtpButton.click();
		
		if(Generic.isIos(driver))		
			verifyOTPSentMsg();
		
    	Log.info("======== Waiting for auto generated OTP ========");        	
    	if(Generic.checkOTPMobile(mobileEmail))
    	{
	    	for(int i=0;i<7;i++)
	    	{
	    		if(Generic.isIos(driver)) break; // wait for Android only
	    		
	    		Generic.wait(2);
	    		if(descriptionTitle.getText().contains("Answer")) //next page
	    			{
	    				otpReceived=true;
	    				break;
	    			}
	    	}
    	}
    	
    	//Generic.waitUntilElementTextVisible(driver,otpTextField,"Enter the OTP you received",15);  // Wait for otp 
    	if(!otpReceived)
    	{   		
    		Log.info("======== Retrieving OTP from DB and entering ========");
    		otpTextField.sendKeys(Generic.getOTP(mobileEmail,bankCode,Generic.getPropValues("FORGETPASSWORD", BaseTest1.configPath)));  // Pass mobileNo Parameter and Static bankcode from calling method
    		
    		if(Generic.isIos(driver))
    			submitButton.click();
    	}
	}
	
	/**
	 *  Verifies the alert message for OTP Send success 
	 *  
	 */
	public void verifyOTPSentMsg()
	{
		try 
		{
			Log.info("======== Verifying OTP Send Status : "+otpSentAlertTitle.getText()+" ========");
			Log.info("======== Verifying Alert Message : "+otpSentAlertMsg.getText()+" ========");		
			okButton.click();
		}
		catch(Exception e)
		{
			Assert.fail("OTP Success Alert message not displayed \n"+e.getMessage());
		}
	}
}
