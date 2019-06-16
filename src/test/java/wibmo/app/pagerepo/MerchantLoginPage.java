package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import library.Log;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import library.Generic;
import wibmo.app.testScripts.BaseTest1;
import wibmo.webapp.pagerepo.DVCFetchPage;
import wibmo.webapp.pagerepo.DeviceVerificationPage;

/**
 * The Class MerchantLoginPage is the Login Page under the Merchant Web Overlay 
 */
public class MerchantLoginPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The login id text field. */
	@FindBy(xpath="(//android.widget.EditText)[1]|//*[@id='input_0']")
	private WebElement loginIdTextField;
	
	/** The secure pin text field. */
	@FindBy(xpath="(//android.widget.EditText)[2]|//input[@type='password']")
	private WebElement securePinTextField;
	
	/** The login id text displayed. */
	@FindBy(xpath="//android.view.View[contains(@content-desc,'Login ID')]")
	private WebElement loginIdText;
	
	@FindBy(xpath="//*[contains(@content-desc,'click here')]")
	private WebElement loginLink;
	
	/** The login page check used to navigate to the Merchant Login Page from the default Register page. */
	@FindBy(xpath="//*[contains(@content-desc,'Login ID') or contains(@content-desc,'click here')]")
	private WebElement loginPageCheck;
	
	@FindBy(xpath="//*[contains(@content-desc,'Enter Secure PIN') or contains(text(),'Enter Secure PIN')]")
	private WebElement wibmoPinTxt;
	
	@FindBy(xpath="//*[contains(@content-desc,'Phone Number') or contains(@id,'input_0')]") 
	private WebElement phoneNumberTxt;
	
	/** The login button. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'Login')]")
	private WebElement loginButton;
	
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'CONTINUE')] | //button[@type='submit']")
	private WebElement continueBtn;	
	
	/** The cancel button. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'CANCEL')]") // Cancel
	private WebElement cancelButton;
	
	@FindBy(xpath="//*[contains(@resource-id,'text1')]") 
	private List<WebElement> cancelReasons;
	
	/** The remember password checkbox. */
	@FindBy(className="android.widget.CheckBox")
	private WebElement rememberCheckbox;
	
	/** The invalid pin error message. */
	@FindBy(xpath="//android.view.View[contains(@content-desc,'wrong')]")
	private WebElement invalidPinMsg;
	
	/** The dvc check used to check for the occurence of the DVC page after Login. */
	@FindBy(xpath="//*[contains(@content-desc,'DVC') or contains(@content-desc,'Select')]") // DVC screen or SelectCard Screen
	private WebElement dvcCheck;
	
	/** The DVC textfield. */
	@FindBy(className="android.widget.EditText")
	private WebElement dvcTextfield;
	
	/** The login button under the DVC screen. */
	@FindBy(xpath="//*[@text='Login']") 
	private WebElement dvcLoginBtn;
	
	/** The OK button. */
	@FindBy(xpath="//*[contains(@resource-id,'button1')]")
	private WebElement okBtn;
	
	/**
	 * Instantiates a new MerchantLoginPage.
	 *
	 * @param driver the driver
	 */
	public MerchantLoginPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Verifies Login page.If by default Register Page is displayed instead of Login Page , it navigates to the login page.
	 * So this method should be used for every Merchant Login.   
	 */
	public void verifyLoginPage()
	{
		
		
		System.out.println("Context under Login Page : "+((AndroidDriver)driver).getContext());
		
		
		try{new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(phoneNumberTxt));}catch(Exception e)
		{Assert.fail("Login/Welcome Screen not found\n"+e.getMessage());}	
		Generic.switchToWebView(driver);
		
		if(true) return; // Registered User and Unregistered User flow based on the Number Entered.
		
		Log.info("======== Verifying Login Page ========");
		try
		{
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(loginPageCheck));
		}
		catch(Exception e)
		{
			Assert.fail("Page taking too much time to load or page not found\n"+e.toString());
		}		
		//if(loginPageCheck.getAttribute("name").contains("click")) Use when login page occurs directly
		{
			Generic.wait(6); // Wait for bouncing Overlay to settle
			Log.info("======== Clicking on click here link ========");
			loginLink.click();
			//try{loginPageCheck.click();}catch(Exception e){Log.info("== Delay due to Click Here ==");}
			
			try
			{
				new WebDriverWait(driver,45).until(ExpectedConditions.visibilityOf(loginIdText));
			}
			catch(Exception e)
			{
				Assert.fail("Login Page taking too much time to load or page not found\n"+e.toString());
			}
		}			
	}
	
	/**
	 * Performs a Login by entering LoginId and securePin.
	 *
	 * @param data the data
	 */
	public void login(String data)
	{	
		int i=0;
		String values[]=data.split(",");
		String loginId=values[i++],securePin=values[i++];
		
		try{new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(phoneNumberTxt));}catch(Exception e)
		{Assert.fail("Login/Welcome Screen not found\n"+e.getMessage());} 
		
		Log.info("======== Entering LoginId : "+loginId+" ========");
		//if(!loginIdTextField.getAttribute("name").equals(loginId))
		{
			Generic.forceClearAttribute(driver,loginIdTextField);
			loginIdTextField.sendKeys(loginId);
			Generic.hideKeyBoard(driver);
		}
		
		Log.info("======== Clicking on Continue button in Welcome Page  ======== ");
		//loginButton.click();
		continueBtn.click();			
		enterSecurePin(securePin);		
	}
	
	public void enterSecurePin(String securePin)
	{
		try{new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(wibmoPinTxt));}catch(Exception e)
		{Assert.fail("Secure Pin textfield not found\n"+e.getMessage());} 		
		
		Log.info("======== Entering Secure Pin ========");
		securePinTextField.sendKeys(securePin);
		Generic.hideKeyBoard(driver);
		//rememberCheckbox.click();		
		
		Log.info("======== Clicking on Continue button in Login Page ======== ");
		//loginButton.click();
		continueBtn.click();	
	}
	
	/**
	 * Verifies invalid pin error message.
	 *
	 * @param data the data
	 */
	public void verifyInvalidPin(String data)
	{
		int i=0;
		String values[]=data.split(",");
		String loginId=values[0],invalidPin=values[5];
		
		verifyLoginPage();
		
		Log.info("======== Entering LoginId : "+loginId+" ========");
		if(!loginIdTextField.getAttribute("contentDescription").equals(loginId))
		{
			Generic.forceClearAttribute(driver,loginIdTextField);
			loginIdTextField.sendKeys(loginId);
			Generic.hideKeyBoard(driver);
		}
		
		Log.info("======== Clicking on Continue button in Welcome Page  ======== ");
		//loginButton.click();
		continueBtn.click();		
		
		Log.info("======== Entering Invalid Pin :"+invalidPin+"========");
		securePinTextField.sendKeys(invalidPin);
		Generic.hideKeyBoard(driver);
		//rememberCheckbox.click();		
		
		Log.info("======== Clicking on Continue button ======== ");
		//loginButton.click();
		continueBtn.click();
		
		try
		{
			Log.info("======== Verifying Invalid Pin error message :"+invalidPinMsg.getAttribute("contentDescription")+"========");
			Assert.assertTrue(invalidPinMsg.isDisplayed(), "Invalid Pin error message not displayed\n");
		}
		catch(Exception e)
		{
			Assert.fail("Invalid Pin error message not found\n"+e.getMessage());
		}		
		Generic.forceClearAttribute(driver,securePinTextField);		
	}
	
	/**
	 * Enters the DVC retrieved from the DB if the DVC page occurs after Login.
	 *
	 * @param data the data
	 * @param bankCode the bank code
	 */
	public void handleDVCTrusted(String data,String bankCode)
	{
		
		String mobileNo=data.split(",")[0];
		if(Generic.switchToWebView(driver)) 
		{
			new DeviceVerificationPage(driver).adddvcdetails(Generic.getOTP(mobileNo,bankCode,Generic.getPropValues("DVC", BaseTest1.configPath)));
			return;
		}
		
		try
		{
			new WebDriverWait(driver,45).until(ExpectedConditions.visibilityOf(dvcCheck));
		}
		catch(Exception e)
		{
			Assert.fail("Page not found\n"+e.getMessage());
		}		
		if(dvcCheck.getAttribute("contentDescription").contains("DVC"))
		{	
			Log.info("======== Retrieving and entering DVC ========");
			dvcTextfield.sendKeys(Generic.getOTP(mobileNo,bankCode,Generic.getPropValues("DVC", BaseTest1.configPath)));
			
			Log.info("======== Clicking on DVC Continue button ========");
			//dvcLoginBtn.click();
			continueBtn.click();
		}	
	}
	
	/**
	 * Cancels login.
	 */
	public void cancelLogin()
	{
		Log.info("======== Click on Cancel Button ========");
		cancelButton.click();
		okBtn.click();	
		
		handleCancelReason();
	}
	
	public void clickOK()
	{
		Log.info("======== Click on ok Button ========");
		okBtn.click();
	}
	
	public void handleCancelReason()
	{			
		Generic.wait(1);
		Log.info("======== Handling Cancel reason ========");		
		try{cancelReasons.get(new Random().nextInt(cancelReasons.size()-1)).click();}catch(Exception e){Log.info("== Delay due to non occrence of Cancel reason list ==");}		
	}

}
