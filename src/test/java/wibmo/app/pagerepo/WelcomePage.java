package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import library.Generic;
import library.HandleLoginPopup;
//import library.PopupHandles;
import wibmo.app.testScripts.BaseTest1;
import wibmo.app.testScripts.Registration.BaseTest;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.snowtide.pdf.layout.u;

import library.Log;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * The Class WelcomePage represents the Welcome page with Register and Login buttons.
 */
public class WelcomePage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	private SoftAssert softAssert;

	/** The register button. */
	@FindBy(id="textbutton_reg")
	private WebElement registerButton;

	/** The login button. */
	@FindBy(id="textbutton_login")
	private WebElement loginButton;

	/** The forgot pin link. */
	@iOSXCUITFindBy(accessibility="Forgot PIN")
	@AndroidFindBy(xpath="//*[@text='Forgot PIN']") 
	private WebElement forgotPinLink;

	/**  The change user link under more options. */
	@iOSXCUITFindBy(accessibility="Change User")
	@AndroidFindBy(xpath="//*[@text='Change User']")
	private WebElement changeUserLink;
	
	/** The userCheck used to discern between Login Page & Welcome Page */
	@iOSXCUITFindBy(iOSNsPredicate="name CONTAINS 'Logging' or type=='XCUIElementTypePageIndicator'")  // Both should have value attribute
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@resource-id,'l_footer_text') or contains(@resource-id,'textbutton_reg')]")
	private WebElement userCheck;
	
	@FindBy(id="l_footer_text")
	private WebElement loggedInUserTxt;

	/** The login id textfield. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="fl_text_username")
	private WebElement loginIdTextfield;

	/** The login btn in select user page */
	@FindBy(id="fl_btnLogin")
	private WebElement loginBtn;
	
	/** The login btn in Welcome page with Register and Login buttons */
	@FindBy(id="textbutton_login")
	private WebElement welcomeLoginBtn;

	/** The menu ok button. */
	@FindBy(id="menu_ok")
	private WebElement menuOkButton;

	/** The reg check used to check the presence of Register button. */
	@FindBy(id="textbutton_reg")
	private List<WebElement> regCheck;

	/** The error message. */
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Please'")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'message')]")
	private WebElement errMsg;
	
	/** The error message OK button. */
	@FindBy(xpath="//*[contains(@resource-id,'button2')]")
	private WebElement OkBtn;	

	@FindBy(id="fl_main_version")
    private WebElement aboutProgramVersion;	
	
	/** The register optional click. */
	@iOSXCUITFindBy(accessibility="Register")
	@AndroidFindBy(xpath="//android.widget.TextView[@text='+91' or @text='Register']")
	private WebElement registerOptionalClick;

	/** The login button check used to check for the presence of Login button. */
	@FindBy(xpath="//android.widget.TextView[@text='Login'] | //android.widget.ImageView[contains(@resource-id,'button_go')]")
	private WebElement loginButtonCheck;
	
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypePageIndicator' or value contains 'Logging'")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'Logging') or contains(@text,'Register')]")
	private WebElement welcomePageChk;
	
	@FindBy(id="continueBtn")
	private WebElement agreeRootAlertChkBox;
	
	
	/**
	 * Instantiates a new WelcomePage.
	 *
	 * @param driver the driver
	 */
	public WelcomePage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
		softAssert=new SoftAssert();
	}
	
	/**
	 * Navigates to the Welcome page if the current Page is Login Page.
	 *
	 * @param driver the driver
	 */
	public  void gotoWelcome()
	{
		handleEmulatorRootAlert();
		
		if(welcomePageChk.getText().contains("Logging"))
			new LoginNewPage(driver).gotoWelcome();
	}		

	/**
	 * Navigates to the Register Page by clicking on the register button.
	 * 
	 */
	public void register()
	{
		Log.info("======== Clicking on Register button ========");
		registerOptionalClick.click();
	}
	
	/**
	 * Navigates to Login Page.
	 */
	public void login()
	{
		Log.info("======== Clicking on Login button ========");
		loginButton.click();
	}	

	/**
	 * Navigates to Forgot Pin page.
	 */
	public void goToForgotPin()
	{
		moreOptions.click();
		
		Log.info("======== Click on forgot pin link ========");
		forgotPinLink.click();
	}

	/**
	 * Changes the user to the given loginId.
	 *
	 * @param loginId the login id
	 */
	public void selectUser(String loginId)
	{
		//new PopupHandles(driver).start();	
		handleEmulatorRootAlert();
		
		if(checkLoggedInUser(loginId)) 
		{
			Log.info("======== Logging in as "+loginId+" ========");	
			return;
		}
		
		//loggedInUserTxt.click();
		
		moreOptions.click();
	
		Generic.wait(1); 		// UiAuto2
		changeUserLink.click();		

		enterLoginId(loginId);
	}
	
	/**
	 * Checks whether the selected user is already selected for Login or not.
	 *
	 * @param loginId the login id
	 * @return true if the given loginId is already selected for login
	 * 
	 */
	public boolean checkLoggedInUser(String loginId)
	{
		System.out.println("Selecting User :  "+loginId);
		Generic.wait(1); // UiAuto2 
		String loggedInUser=userCheck.getText();	// (loggedInUser.charAt(loggedInUser.length()-9)==loginId.charAt(loginId.length()-9))
		System.out.println("Logged in user check : "+loggedInUser);
		
		return loggedInUser.substring(loggedInUser.length()-4).equals(loginId.substring(loginId.length()-4)) 
				&& (loggedInUser.charAt(loggedInUser.length()-9)==loginId.charAt(loginId.length()-9)) 
				&& !loginId.contains("@"); // Check last four digits in Login Page, put charAt(1) if necessary	
	}
	
	/**
	 * Enters the loginId.
	 *
	 * @param loginId the loginId
	 */
	public void enterLoginId(String loginId)
	{
		Log.info("======== Selecting "+loginId+" for Login ========");
		
		if(Generic.isIos(driver))
		{
			Generic.setValue(driver,loginIdTextfield, loginId);
			//Generic.sendKeys(driver, loginIdTextfield, loginId);
			Generic.iosKey_Go(driver);		
			return;
		}
		
		//=================================================//
		
		if(!loginIdTextfield.getText().equals(loginId))
		{
			loginIdTextfield.clear();
			loginIdTextfield.sendKeys(loginId); 
		}		
		loginBtn.click();	
		BaseTest1.setAppDataPresent();
	}

	/**
	 * Changes the user to an incorrect loginId so that Phone verification page can occur.
	 *
	 * @param data the data
	 */
	public void changeUserTemp(String data)
	{
		String loginId=data.split(",")[0];
		
		if(Generic.isIos(driver)) return; // TBI 
		//if(!loginId.contains("@") && !BaseTest1.getPhoneVerifyStatus()) return; // Not an OTP mobile number && no need to goto phone verify page
		
		moreOptions.click();
		changeUserLink.click();

		if(loginIdTextfield.getText().contains("Registered Mobile"))
		{
			navigateLink.click();return;
		}		
		if(!loginIdTextfield.getText().contains(loginId))
		{
			menuOkButton.click();return;
		}		

		Log.info("======== Entering temporary loginId ========");
		loginIdTextfield.clear();
		loginIdTextfield.sendKeys(Generic.generateMobileNumber()); // Enter temp value , no need to parameterise
		loginBtn.click();			
	}

	
	/**
	 * Checks for the presence of register button.
	 *
	 * @return true, if register button is present.
	 * @deprecated 
	 */
	public boolean registerButtonPresent()    // Not used currently To choose between Kotak's VerifyMobileRegister , and  other Banks
	{
		if(!regCheck.isEmpty())		
			return true;
		else
			return false;
	}

	/**
	 * Verifies blank username error message.
	 */
	public void verifyErrMsg()
	{
		String msg;
		Log.info("======== Verifying blank username error Message ========");	

		try 
		{
			okBtn.isDisplayed();
			msg=Generic.isAndroid(driver)?alertMessage.getText():errMsg.getText();
			
			Assert.assertTrue(msg.contains("Please enter"),groupExecuteFailMsg()+"Error message not displayed \n");
			Log.info("======== Clicking on OK button ========");
			okBtn.click();
			
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+"Error message not displayed correctly\n"+e.getMessage());
		}
	}
	
	/**
	 * Verifies whether the app is installed or not.
	 */
	public void verifyApp()
	{		
		String bundleId=Generic.getPropValues("PACKAGENAME", BaseTest1.configPath);
		Log.info("======== Verifying App Installation ========");
		Assert.assertTrue(((AndroidDriver)driver).isAppInstalled(bundleId),"Error in retrieving app data for "+bundleId);		
		
		getAppVersion();
	}
	
	/**
	 *  Retrieves the version displayed under the About Page 
	 */
	public void getAppVersion()
    {
        String versionDislayed;
        if(Generic.isIos(driver)) return; // TBI
        
        try{
            
        	moreOptions.click();
        aboutLink.click();
        
        Log.info("======== Retreiving App Version "+(versionDislayed=aboutProgramVersion.getText())+" ========");        
        BaseTest1.appVersion=versionDislayed;    
        
        navigateLink.click();
        
        }catch(Exception e){}        
    }
	
	/**
	 *  
	 *  Handles an emulator root alert in emulator 
	 * 
	 */
	public void handleEmulatorRootAlert()
	{
		if(!Generic.isEmulator(driver))
			return;
		
		waitOnProgressBarId(20);		
		
		if(Generic.waitUntilTextInPage(driver, 10, "Warning","Login").contains("Warning")) // Warning Page / WelcomePage / LoginPage
		{
			Log.info("======== Handling Root Alert in Emulator ========"); 
			click(agreeRootAlertChkBox);
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

	

}
