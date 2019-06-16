package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;
import library.Log;
import wibmo.app.testScripts.BaseTest1;
import wibmo.app.testScripts.Registration.BaseTest;

/**
 * The Class VerifyMobilePage used to register the mobile number after receiving/entering the MVC.
 */
public class VerifyMobilePage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	private SoftAssert softAssert;
	
	/** The verify mobile page title. */
	@iOSXCUITFindBy(accessibility="Complete Registration")
	@AndroidFindBy(xpath="//*[@text='Verify Mobile']") 
	private WebElement verifyMobileTitle; 
	
	/** The MVC text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(id="main_mbOtp_edit")
	private WebElement inputMvcTextField;	
	
	/** The Complete Registration button compatible for Half Registered Userr from Web SDK */
	@iOSXCUITFindBy(iOSNsPredicate="name in {'Complete Registration','Login'}")
	@AndroidFindBy(xpath="//android.widget.Button[contains(@resource-id,'main_btnReg') or contains(@resource-id,'main_btnLogin')]") 
	private WebElement completeRegistrationButton;
	
	/** The incorrect MVC error message. */
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Mobile verification'")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'message')]")
	private WebElement incorrectOtpErrMsg;
	
	/** The error message OK button. */
	@FindBy(xpath="//*[contains(@resource-id,'button2')]")
	private WebElement OkBtn;
	
	
	/**
	 * Instantiates a new VerifyMobilePage.
	 *
	 * @param driver the driver
	 */
	public VerifyMobilePage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		softAssert=new SoftAssert();
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}	
	
	/**
	 * Performs a Complete registration after receiving/entering the MVC.
	 * If MVC is not received within 15 seconds then MVC corresponding to the bankCode is retrieved from DB and entered.
	 * 
	 * @param mobileEmail the mobile email
	 * @param bankCode the bank code
	 */
	public void completeRegistration(String mobileEmail,String bankCode)
	{		
		
		if(!Generic.checkOTPMobile(mobileEmail))
		{
			
		Log.info("======== Retrieving OTP from DB and entering ========");
        	inputMvcTextField.sendKeys(Generic.getOTP(mobileEmail,bankCode,Generic.getPropValues("MVC", BaseTest1.configPath)));
        	
        	Generic.hideKeyBoard(driver);
    		Log.info("======== Clicking on Complete Registration button ========");
    		completeRegistrationButton.click();	
    		
    		return;
    		
		}
		
		Log.info("======== Waiting for MVC ======== ");
		if(!Generic.waitUntilElementTextVisible(driver,inputMvcTextField,"MVC",15)) 
    		{
			Log.info("======== Retrieving OTP from DB and entering ========");
			inputMvcTextField.sendKeys(Generic.getOTP(mobileEmail,bankCode,Generic.getPropValues("MVC", BaseTest1.configPath)));
        	// Pass mobileNo Parameter and Static bankcode from calling method
    		}   
		Generic.hideKeyBoard(driver);
		Log.info("======== Clicking on Complete Registration button ========");
		completeRegistrationButton.click();	
			
	}	
	
	public void clearMVCField()
	{
		Log.info("======== Clearing MVC field =======");
		inputMvcTextField.clear();
	}
	
	public void completeRegistrationHalfRegisteredUser(String mobileEmail,String bankCode)
	{
		
	}
	
	/**
	 * Verifies the acceptance of all valid values from the previous Register Page.
	 */
	public void verifyTitle()
	{
		Log.info("======== Verifying acceptance of valid data from Register page ========");
		try 
		{
			Assert.assertTrue(verifyMobileTitle.isDisplayed(),"Valid inputs was not accepted \n");
		} catch (Exception e) 
		{
			Assert.fail("Valid inputs was not accepted \n"+groupExecuteFailMsg()+e.getMessage());
		}		
	}	
	
	/**
	 * Enters Old MVC and verifies the corresponding error message.
	 *
	 * @param oldMvc the old mvc
	 */
	public void verifyOldMvc(String oldMvc)
	{			
		String msg;
		
		Log.info("======== Populating Old MVC :" +oldMvc+" ========");		
				
		inputMvcTextField.sendKeys(oldMvc);		
		Generic.hideKeyBoard(driver);	
		
		completeRegistrationButton.click();
		Generic.wait(2);
		
		try
		{
			okBtn.isDisplayed();
			 msg=Generic.isAndroid(driver)?alertMessage.getText():incorrectOtpErrMsg.getText();
			 
			Log.info("======== Verifying Error Message : "+msg+"========");		
			Assert.assertTrue(msg.contains("OTP") || msg.contains("verification required"),"Wrong Error message or error message not found\n");
			Log.info("======== Clicking on OK button ========");
			okBtn.click();
		}
		catch(Exception e)
		{
			Assert.fail("Wrong Error message or error message not found\n"+groupExecuteFailMsg()+e.getMessage());
		}		
	}
	
	/**
	 * Enters Incorrect MVC and verifies the corresponding error message.
	 *
	 * @param data the data
	 */
	public void verifyIncorrectOTP(String data)
	{
		String OTP=data.split(",")[10],msg;
			
		
		Log.info("======== Entering incorrect OTP : "+OTP+" ========");
		
		inputMvcTextField.clear();
		inputMvcTextField.sendKeys(OTP);	
		Generic.hideKeyBoard(driver);
		completeRegistrationButton.click();
		Generic.wait(2);
		
		try
		{
			okBtn.isDisplayed();
			msg=Generic.isAndroid(driver)?alertMessage.getText():incorrectOtpErrMsg.getText();
			
			Log.info("======== Verifying error message : "+msg+" ========");
			Assert.assertTrue(msg.contains("OTP") || msg.contains("verification required") ,"Incorrect Error message");	
			Log.info("======== Clicking on OK button ========");
			okBtn.click();
		}
		catch(Exception e)
		{
			Assert.fail("Error message not found\n"+groupExecuteFailMsg()+e.getMessage());
		}		
	}
	
	public void verifyAppError()
	{
		String msg, expectedContent="Sorry, app had an error";
		
		try {
			
		okBtn.isDisplayed();
		Log.info("======== Verifying message : "+(msg=alertMessage.getText())+" ========");
		Assert.assertTrue(msg.contains(expectedContent), "Invalid error message \n ");		
		okBtn.click();
		
		}
		catch(Exception e){Assert.fail("Error message not found\n");}
		
	}
	
	public String groupExecuteFailMsg()
	{
		if(BaseTest.groupExecute) 		
			return "\nFailed under Group Execution "+BaseTest.groupTestID+" : "+BaseTest.groupTestScenario+"\n";	
		else
			return "";
	}

}
