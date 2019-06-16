package wibmo.app.pagerepo;   

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import wibmo.app.testScripts.BaseTest1;
import wibmo.app.testScripts.Registration.BaseTest;
import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
//import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;

/**
 * The Class RegisterMobilePage used to enter the mobile number for registration.
 */
public class RegisterMobilePage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	private SoftAssert softAssert;
	
	/** The mobileEmail text field. */
	@FindBy(id="main_email_edit")
	private WebElement mobileTextField;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[-1]")
	@AndroidFindBy(id="referral_code_edit")
	private WebElement referralCodeTextfield;
	
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Referral code'")
	private WebElement referralCodeErrMsg;
	
	/** The continue button. */
	@iOSXCUITFindBy(iOSNsPredicate="name='Continue'")
	@AndroidFindBy(id="main_btnSubmitInvite")
	private WebElement continueButton;
	
	/** The continue button generic to multiple banks. */
	@iOSXCUITFindBy(iOSNsPredicate="name='Continue'")
	@AndroidFindBy(xpath="//android.widget.Button[contains(@resource-id,'main_btnSubmit')]")
	private WebElement continueButtonGeneric;
	
	/** The Alert message for error. */
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Please enter' or value beginswith 'Sorry'")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'message')]") 
	private WebElement errMsg;
	
	/** The error message OK button. */
	@FindBy(xpath="//*[contains(@resource-id,'button2')]")
	private WebElement errMsgOk;
	
	
	
	/**  The kotak logintextfield currently not used. */
	@FindBy(id="value_username")
	private WebElement kotLogintextField;
	
	/** The kotak login text field checker used to check the presence by using List instead of try catch. */
	@FindBy(id="value_username")
	private List<WebElement> kotLoginTextFieldCheck;  // Use List.size() to check presence of element instead of findElement which throws exception for checking presence ( avoid try catch)

	/** The mobile text field checker used to check the presence by using List instead of try catch. */
	@FindBy(id="main_email_edit")
	private List<WebElement> mobileTextFieldCheck; // Use List.size() to check presence of element instead of findElement which throws exception for checking presence ( avoid try catch)
	
	/** The kotak send register button currently not used. */
	@FindBy(id="button_go")
	private WebElement kotSendRegisterBtn;	
	
	/** The mobile text field to enter the unregistered mobile number */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="mobile_edit")
	private WebElement mobileTextFieldGeneric;	
	
	
	/**
	 * Instantiates a new RegisterMobilePage.
	 *
	 * @param driver the driver
	 */
	public RegisterMobilePage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		softAssert=new SoftAssert();
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);	
	}
	
	/**
	 * Enters the mobile number to be registered.
	 *
	 * @param mobileNumber the mobile number
	 */
	public void registerMobile(String mobileNumber)
	{		
		new BaseTest1().selectQaProgram(driver);
		Log.info("======== Entering Mobile Number : "+ mobileNumber+ " ========");
		
		if(!mobileNumber.equals(mobileTextFieldGeneric.getText()))     
		{	
			mobileTextFieldGeneric.clear();
			mobileTextFieldGeneric.sendKeys(mobileNumber);
		}	
		
		Generic.hideKeyBoard(driver);
		continueButtonGeneric.click();		
	}
	
	public void registerMobile(String mobileNumber,String referralCode)
	{
		enterMobileNumber(mobileNumber);
		
		enterReferralCode(referralCode);
		
		clickContinue();
		
	}
	
	public void enterMobileNumber(String mobileNumber)
	{
		new BaseTest1().selectQaProgram(driver);
		
		Log.info("======== Entering Mobile Number : "+ mobileNumber+ " ========");		
		
		if(!mobileNumber.equals(mobileTextFieldGeneric.getText()))     
		{	
			mobileTextFieldGeneric.clear();
			mobileTextFieldGeneric.sendKeys(mobileNumber);
			Generic.hideKeyBoard(driver);
		}	
	}
	
	public void clickContinue()
	{
		Log.info("======== Clicking on Register Continue button ========");   
		continueButtonGeneric.click();	
	}
	
	public void enterReferralCode(String referralCode)
	{
		Log.info("======== Entering Referral Code : "+referralCode+" ========");
		
		if(!referralCodeTextfield.getText().isEmpty())
			referralCodeTextfield.clear();
				
		referralCodeTextfield.sendKeys(referralCode);
		Generic.hideKeyBoard(driver);
	}
	
	public void verifyReferralCodeErrMsg()
	{
		String msg;		
		try
		{
			okBtn.isDisplayed();
			msg=Generic.isAndroid(driver)?alertMessage.getText():referralCodeErrMsg.getText();
						
			Log.info("======== Verifying Referral Code error message : "+msg+" ========");
			Assert.assertTrue(Generic.containsIgnoreCase(msg, "Referral"), "Incorrect Referral Code error message \n");			
		}
		catch(Exception e)
		{
			Assert.fail("Referral Code error message not found\n");
		}
		
		Log.info("======== Clicking on Error message OK ========");
		okBtn.click();
		
		if(Generic.isIos(driver)) // Normalize navigation
		{
			Generic.wait(3);
			navigateLink.click();
			new WelcomePage(driver).register();
		}
			
	}
	
	
	/**
	 * Used to enter the mobile number to be registered for Kotak.
	 *
	 * @param mobileNumber the mobile number
	 */
	public void registerMobileKotak(String mobileNumber)
	{
		kotLogintextField.sendKeys(mobileNumber);
		/*Generic.hideKeyBoard(driver);*/
		kotSendRegisterBtn.click();
	}	
	
	/**
	 * Verifies blank number error message.
	 */
	public void verifyBlankNumberErrMsg()
	{	
		String msg;
		Log.info("======== Verifying Blank Number Error message ========");
		try 
		{
			okBtn.isDisplayed();
			msg=Generic.isAndroid(driver)?alertMessage.getText():errMsg.getText();
			Log.info("======== Error Message : "+msg+" ========");
			
			softAssert.assertTrue(msg.contains("Please enter Mobile") || msg.contains("valid 10 digit"));	
			okBtn.click();
			} 
		catch (Exception e) 
		{
			softAssert.fail(groupExecuteFailMsg()+e.getMessage());
		}		
		softAssert.assertAll();
	}
	
	public void verifyAlreadyRegisteredNumberErrMsg(){
		
		String msg;
		Log.info("=========Verifying already registered number error message========");
		try{
			
			okBtn.isDisplayed();
			msg=Generic.isAndroid(driver)?alertMessage.getText():errMsg.getText();
			Log.info("======== Error Message : "+msg+" ========");		
			softAssert.assertTrue(errMsg.getText().contains("Sorry, you already have"));
			okBtn.click();
		}
		catch (Exception e)
		{
			softAssert.fail(groupExecuteFailMsg()+e.getMessage());
		}
		softAssert.assertAll();
	}
	
	/**
	 * Verifies invalid number error message.
	 */
	public void verifyInvalidNumberErrMsg()
	{
		String msg;
		Log.info("======== Verifying Error Message=====");
		try 
		{
			
			okBtn.isDisplayed();
			msg=Generic.isAndroid(driver)?alertMessage.getText():errMsg.getText();
			
			Log.info("======== Error Message : "+msg+" ========");
			
			softAssert.assertTrue(msg.contains("valid 10 digit"),groupExecuteFailMsg()+"Invalid Error Message");
			okBtn.click();
			
			softAssert.assertAll();
			
		} catch (Exception e) 
		{
			Assert.fail("Error message not found\n"+groupExecuteFailMsg()+e.getMessage());
		}
			
	}
	
	/**
	 * Only the first 10 digits entered should be accepted when entering a number greater than 10 digits.
	 *
	 * @param mobileNumber the mobile number
	 */
	public void verify10DigitNumber(String mobileNumber)
	{
		Log.info("======== Entering more than 10 digits :"+mobileNumber+" ========");
		mobileTextFieldGeneric.sendKeys(mobileNumber);	
		
		Log.info("======== Verifying for 10 digit number entered ========");
		try 
		{
			String numberEntered=mobileTextFieldGeneric.getText();
			softAssert.assertEquals(numberEntered.length(), 10);			
		} 
		catch (Exception e) 
		{
			softAssert.fail(groupExecuteFailMsg()+e.getMessage());
		}			
		softAssert.assertAll();
	}
	
	public String groupExecuteFailMsg()
	{
		if(BaseTest.groupExecute) 		
			return "\nFailed under Group Execution "+BaseTest.groupTestID+" : "+BaseTest.groupTestScenario+"\n";	
		else
			return "";
	}
	
}
