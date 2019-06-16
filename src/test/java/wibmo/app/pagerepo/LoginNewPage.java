package wibmo.app.pagerepo;

import static org.testng.Assert.fail;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import library.Generic;
import library.HandleLoginPopup;

import org.codehaus.groovy.runtime.HandleMetaClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import wibmo.app.testScripts.BaseTest1;
import wibmo.app.testScripts.Login.BaseTest;

import library.Log;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * The Class LoginNewPage mainly used to login with the pre selected user or navigate to change user. 
 */
public class LoginNewPage extends BasePage 
{
	
	/** The driver. */
	private WebDriver driver;

	/** The soft assert. */
	private SoftAssert softAssert;

	/** The Home Key by clicking on which we can navigate to Welcome Page */
	@iOSXCUITFindBy(accessibility="ic home")
	@AndroidFindBy(id="home_button_login")
	private WebElement homeButton;

	/** The secure pin text field. */
	@FindBy(id="l_passwordEditText")
	private WebElement securePinTextField;

	/** The login button. */
	@iOSXCUITFindBy(iOSNsPredicate="type=='XCUIElementTypeButton' AND name=='Login'")
	@AndroidFindBy(id="login_button")
	private WebElement loginButton;

	/** The forgot pin link. */
	@FindBy(id="txn_forgot_pin")
	private WebElement forgotPinLink;

	/** The enter pin err msg. */
	@FindBy(xpath="//*[contains(@resource-id,'message')]") 
	private WebElement enterPinErrMsg;

	/** The enter valid pin err msg. */
	@FindBy(xpath="//*[contains(@resource-id,'message')]") 
	private WebElement enterValidPinErrMsg;

	/** The error message alert. */
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Please'")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'message')]")
	private WebElement errMsg;

	/** The ok button. */
	@iOSXCUITFindBy(accessibility="Ok")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'button2')]") // Or Add Wait for UiAuto2
	private WebElement ok;

	/** The authentication failed error message. */
	@FindBy(xpath="//*[contains(@resource-id,'message')]")
	private WebElement authenticationFailedErrMsg;
	
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeStaticText'  and (value contains ' Verification' or value contains 'Hi ' or value contains 'try again')")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@resource-id,'message') or contains(@resource-id,'title_text')]")
	private WebElement pinCheck;

	/** The pin attempts failure error message. */
	@FindBy(xpath="//*[contains(@resource-id,'message')]") 
	private WebElement pinAttemptsFailureErrMsg;	
	
	/** The delete pin button*/
	@iOSXCUITFindBy(accessibility="⌫")
	@AndroidFindBy(id="back_button")
	private WebElement delBtn;
	
	/** The Change User link under More options. */
	@FindBy(xpath="//*[@text='Change User']") 
	private WebElement moreChangeUser;		
	
	@FindBy(xpath="//*[@text='Cancel']") 
	private WebElement loadCancelBtn;	
	


	/**
	 * Instantiates a LoginNewPage.
	 *
	 * @param driver the driver
	 */
	public LoginNewPage(WebDriver driver)
	{
		super(driver);
		this.driver= driver;
		softAssert = new SoftAssert();
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}

	/**
	 * Login by entering securePin.
	 * Each button in the Login screen is clicked separately since the securePin field does not accept keyboard input.i.e sendKeys does not work.
	 *
	 * @param securePin the secure pin
	 */
	public void login(String securePin)
	{
		new BaseTest1().selectQaProgram(driver);		
		
		Log.info("======== Entering secure pin : "+securePin+" ========");
		AppiumDriver adriver=(AppiumDriver)driver;
		
		if(!securePin.equals(" "))
				for(int i=0;i<securePin.length();i++)
					if(Generic.isIos(adriver))
						adriver.findElementByAccessibilityId(securePin.charAt(i)+"").click();
					else
					{
						if(securePin.charAt(0)==securePin.charAt(1))Generic.wait(2);	 	// UiAuto2 button click redundancy 						
							adriver.findElementByXPath("//*[@text='i']".
								replace('i', securePin.charAt(i))).
								click();     
					}
			Log.info("======== Clicking on Login button ========");
			loginButton.click();
			
			BaseTest1.setAppDataPresent();	
	
		waitForProgressBar();
		
	}	
	
	/**
	 * Handles request time out alert and re enters securePin 
	 * 
	 * @param data "loginId,securePin,..."
	 */
	public void handleRequestTimeOut(String data)
	{		
		String xp="//android.widget.TextView[contains(@resource-id,'title_text') or contains(@resource-id,'message')]",
			   buttonKeyXpath="//*[@text='i']";
		
		if(driver.findElement(By.xpath(xp)).getText().toLowerCase().contains("request") || driver.findElement(By.xpath(xp)).getText().toLowerCase().contains("sorry") )
		{
			int size=data.split(",").length;
			String securePin=data.split(",")[1].matches("[a-z A-Z]+")?data.split(",")[size-2]:data.split(",")[1]; // Login after Registration
					
			Log.info("======== Handling Request timed out popup ========");
			ok.click();
			
			Log.info("======== Attempting Relogin with pin : "+securePin+"========");
			for(int i=0;i<securePin.length();i++)
			{
				if(securePin.charAt(0)==securePin.charAt(1))Generic.wait(2);	
				driver.findElement(By.xpath(buttonKeyXpath.replace('i', securePin.charAt(i)))).click();
			}
			Log.info("======== Clicking on Login button ========");
			loginButton.click();			
		}		
	}
	
	public void waitForProgressBar()
	{
		Generic.wait(2);	
		waitOnProgressBarId(45);
	}
	
	/**
	 * Navigates to Welcome Page by clicking on Home key.
	 */
	public void gotoWelcome()
	{
		Log.info("======== Clicking on Home button in login page ========");
		homeButton.click();
	}

	/**
	 * Verifies blank pin error message.
	 */
	public void verifyEnterPinErrMsg()
	{
		String msg;
		
		Log.info("======== Verify Pin Error Message ========");
		msg=Generic.isAndroid(driver)?alertMessage.getText():errMsg.getText();
		
		try {
			okBtn.isDisplayed();
			softAssert.assertTrue(msg.contains("Please enter PIN!") || msg.contains("Secure PIN"));
			clickOK();
			
			softAssert.assertAll();
		} catch (Exception e) 
		{}		
	}

	/**
	 * Verifies invalid pin error message.
	 */
	public void verifyEnterValidPinErrMsg(String data)
	{		
		try 
		{
			okBtn.isDisplayed();
			String msg=Generic.isAndroid(driver)?alertMessage.getText():errMsg.getText(); 
			
			if(msg.toLowerCase().contains("request")) // Handle R	equest Timeout
			{
				handleRequestTimeOut(data.split(",")[0]+","+data.split(",")[3]); 						// Enter previous pin
				msg=Generic.isAndroid(driver)?alertMessage.getText():errMsg.getText(); // Get actual error message
			}			
			
			Log.info("======== Verifying Invalid Pin Error Message : "+msg+" ========");
			softAssert.assertTrue(msg.contains("authentication") || Generic.containsIgnoreCase(msg, "PIN"),"Error message not found\n");
			clickOK();
			
			softAssert.assertAll();
		} catch (Exception e) 
		{
			Assert.fail("Invalid Pin error message was not found\n"+e.getMessage());
		}
	}


	/**
	 * Verifies Authentication Failed error message.
	 */
	public void authenticatioFailedErrMsg()
	{
		String msg;
		
		Log.info("======== Verifying Authentication Failed Error Message ========");
		

		try {
			okBtn.isDisplayed();
			msg=Generic.isAndroid(driver)?alertMessage.getText():errMsg.getText();
			
			Log.info("======== Verifying Message : "+msg+" ========");
			softAssert.assertTrue(msg.contains("Your authentication has failed!") || msg.contains("valid Login"));
			clickOK();
			softAssert.assertAll();
		} catch (Exception e) 
		{
			Assert.fail("Authentication Error message not displayed\n"+e.getMessage());
		}
	}
	
	public void verifyMultisessionAlert(String loginId,String securePin)
	{
		String msg,expectedContent="Your last session was terminated incorrectly";
		
		try {
		
			okBtn.isDisplayed();
			
			if(alertMessage.getText().contains("Request"))
					handleRequestTimeOut(loginId+','+securePin);
		
			Log.info("======== Verifying Multisession Message : "+(msg=alertMessage.getText())+" ========");
			Assert.assertTrue(msg.contains(expectedContent), "Error in validating multisession alert");
			
			okBtn.click();
		}
		catch(Exception e) {Assert.fail("Multisession alert not found");}
		
	}
	
	
	public void clickOK()
	{
		okBtn.click();
	}

	/**
	 * Validates error messages for invalid mobile/email loginIds
	 */
	public void loginIdValidation()
	{
		Log.info("======== Email Id validation email is Not Registered  ========");
		
		try 
		{
			Assert.assertTrue(authenticationFailedErrMsg.getText().contains("authentication"),groupExecuteFailMsg()+"LoginId authentication message not displayed correctly\n");
			clickOK();
		} 
		catch (Exception e) 
		{
			Assert.fail("LoginId authentication message not displayed correctly\n");
		}		
	}
	
	/**
	 * Returns the TestCaseID and Scenario if the current TestCase is under Group execution 
	 * 
	 * @return String TestCaseID and Scenario if under Group execution
	 */
	public String groupExecuteFailMsg()
	{
		if(BaseTest.groupExecute) 		
			return "\nFailed under Group Execution "+BaseTest.groupTestID+" : "+BaseTest.groupTestScenario+"\n";	
		else
			return "";
	}

	public boolean checkPinEntered()
	{
		try{
			
		String chkTxt=pinCheck.getText();
		if(chkTxt.contains("authentication") || chkTxt.contains("try"))
		{
			Log.info("======== Wrong pin was entered , Validating message : "+chkTxt+" ========");
			Log.info("======== Clicking on message OK button ========");
			ok.click();
			return false;
		}
		}catch(Exception e){Log.info("== Delay due to pin check == \n"+e.getMessage());}
		
		return true;		
	}
	
	
	/**
	 *  Clears previously entered PIN, Android only. 
	 *  Mainly used as a postcondition
	 * 
	 * @param pin
	 */
	public void clearPin(String pin) 
	{
		Log.info("======== Clearing previously entered PIN ========");
		
		for(int i=0;i<pin.length();i++)
			delBtn.click();
	}
}

