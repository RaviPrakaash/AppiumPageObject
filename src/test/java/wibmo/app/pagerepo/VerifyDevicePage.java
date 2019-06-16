package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class VerifyDevicePage used to Login after receiving/entering the OTP.
 */
public class VerifyDevicePage extends BasePage
{	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	private SoftAssert softAssert;

	/** The input dvc text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="main_mbOtp_edit")
    private WebElement inputDvcTextField;
	
	/** The read sms link. */
	@FindBy(id="main_readsms_button")
	private WebElement readSmsLink;
	
	/** The login button. */
	@iOSXCUITFindBy(id="Login")
	@AndroidFindBy(id="main_btnLogin")
	private WebElement loginButton;
	
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'INVALID'")
	private WebElement otpErrMsg;

    /** The resend dvc icon. */
    @FindBy(id="shield_icon")
    private WebElement resendDvcIcon;
    
    /** The trust device check box. */
    @FindBy(id="check_device_trust")
    private WebElement trustDeviceCheckBox;
    
//    /** The page title. */
//    @FindBy(id="title_text")
//    private WebElement pageTitle;
    
	/** The ok button. */
	@FindBy(xpath="//*[contains(@resource-id,'button2')]") 
	protected WebElement okButton;	
	
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeStaticText'  and value contains ' Verification' and visible=true")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'Verify')]")
	private WebElement phoneVerificationChk;
    
    /**
     * Instantiates a new VerifyDevicePage.
     *
     * @param driver the driver
     */
    public VerifyDevicePage(WebDriver driver)
	{
     	super(driver);
		this.driver= driver;
		softAssert = new SoftAssert();
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
    
    /**
     * Enters the OTP and clicks on Login button.
     * If OTP is not received within 15 seconds then OTP is retrieved from the DB and entered with the Trusted device status. 
     *
     * @param mobileNo the mobile no
     * @param bankCode the bank code
     */
    public void loginWithTrustedVerifyDevice(String mobileNo,String bankCode)	
    {   
    	if(pageTitle.getText().contains("Phone"))
    	{
    		Log.info("======== "+pageTitle.getText()+" Page found instead of Verify Device Page ========");
    		new VerifyPhonePage(driver).loginWithoutPersonalDevice();	    		
    	}
    	Log.info("======== Login With Verify Device(trust it) ========");
    	
    	if(Integer.parseInt(mobileNo.substring(0,1))>6)
    	{
    		Log.info("======== Waiting for auto generated OTP ========");
    		Generic.waitUntilElementTextVisible(driver,inputDvcTextField,"DVC",15);  // Wait for DVC 
    	}
    	
    	if(!inputDvcTextField.getText().matches("\\d{6}"))  
    	{
    		Log.info("======== Retrieving OTP from DB and entering ========");    		
    		inputDvcTextField.sendKeys(Generic.getOTP(mobileNo,bankCode,Generic.getPropValues("DVC",BaseTest1.configPath)));  // Pass mobileNo Parameter and Static bankcode from calling method
    		Generic.hideKeyBoard(driver);
    	}      	
    	Log.info("======== Clicking on Login button ========");
    	try{loginButton.click();}catch(Exception e){Generic.scroll(driver,"Login").click();e.printStackTrace();} 
    }
    
    
    /**
     *  Clears the DVC textfield
     */
    public void clearDVC(){
    	inputDvcTextField.clear();
    }
    
    /**
     *  Enters the wrong DVC and verifies the error message
     * 
     * @param wrongOtp
     */
    public void verifyEnterWrongDVC(String wrongOtp)
    {
    		String errMsg;
    		
    	
	    	inputDvcTextField.sendKeys(wrongOtp);
	    	Generic.hideKeyBoard(driver);
	    	loginButton.click();
	    	okBtn.isDisplayed();
	    	
	    	errMsg=Generic.isAndroid(driver)?alertMessage.getText():otpErrMsg.getText();
	    	Log.info("======== Verifying Wrong OTP messge : "+errMsg+" ========");
	    	Assert.assertEquals(errMsg, "INVALID OTP FOR DEVICE VERIFICATION");
	    	okBtn.click();
    }
    
        
    /**
     * Enters the OTP and clicks on Login button.
     * If OTP is not received within 15 seconds then OTP is retrieved from the DB and entered with the Untrusted device status.
     *
     * @param mobileNo the mobile no
     * @param bankCode the bank code
     */
    public void loginWithUntrustedVerifyDevice(String mobileNo,String bankCode)
    {  
    	
      	if(Integer.parseInt(mobileNo.substring(0,1))>6)  // // Wait for DVC  
    	{
    		Log.info("======== Waiting for auto generated OTP ========");
    		Generic.waitUntilElementTextVisible(driver,inputDvcTextField,"DVC",15);  
    	}
    	
     	if(!inputDvcTextField.getText().matches("\\d{6}"))  
    	{
    		Log.info("======== Retrieving OTP from DB and entering ========");    		
    		inputDvcTextField.sendKeys(Generic.getOTP(mobileNo,bankCode,Generic.getPropValues("DVC",BaseTest1.configPath)));  // Pass mobileNo Parameter and Static bankcode from calling method
    		Generic.hideKeyBoard(driver);
    	}      	
    	Log.info("======== Clicking on Login button ========");
    	try{loginButton.click();}catch(Exception e){Generic.scroll(driver,"Login").click();e.printStackTrace();} 
    
    }
    
    /**
     * 	
     * 
     * @return true if Phone Verification page occurred  
     */
    public boolean handlePhoneVerification()
    {
    	     boolean phoneVerificationOccurence=false;
    	     
    	      if (phoneVerificationOccurence=phoneVerificationChk.getText().contains ("Phone"))
    					new VerifyPhonePage(driver).loginWithoutPersonalDevice();
    	      
     	return phoneVerificationOccurence;    	
    }
	
}
