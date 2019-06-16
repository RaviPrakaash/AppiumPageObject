package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;

/**
 * The Class VerifyPhonePage used to perform/skip phone verification after selecting the SIM and mobile number.   
 *  Can also be used as SIM Verification Page under Settings 
 */
public class VerifyPhonePage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;	
	
	/** The soft assert. */
	private SoftAssert softAssert;

	/** The personal device checkbox. */
	@iOSXCUITFindBy(className="XCUIElementTypeSwitch")
	@AndroidFindBy(id="check_personal_device")
	private WebElement personalCheckbox;

	/** The select sim dropdown button. */
	@FindBy(xpath="//*[@text='Select SIM']") 
	private WebElement selectSim;

	/** The select number dropdown button. */
	@FindBy(xpath="//*[@text='Select number']") 
	private WebElement selectNumber;

	/** The select sim / number list used to open both SIM and number dropdowns. */
	@FindBy(xpath="//*[contains(@resource-id,'text1')]") 
	private List<WebElement> selectSimNumberList;
	
	/** The select sim / number list used to open both SIM and number dropdowns. */
	@FindBy(className="android.widget.Spinner")
	protected List<WebElement> dropdowns;
	
	/**  Used to select SIM/Number within the SIM/Number dropdowns. */
	@FindBy(xpath="//*[contains(@resource-id,'text1')]")
	protected List<WebElement> selectWithinList;

	/** The continue button also acts as Skip button */
	@iOSXCUITFindBy(accessibility="Continue")
	@AndroidFindBy(id="main_btnContinue")
	protected WebElement continueButton; //Continue Button also becomes Skip Button

	/** The select sim dropdown list. */
	@FindBy(xpath="//*[contains(@resource-id,'text1')]")
	private List<WebElement> selectSimList;

	/** The select number dropdown list. */
	@FindBy(xpath="//*[contains(@resource-id,'text1')]")
	private List<WebElement> selectNumberList;

	/** The dont ask again checkbox which occurs when skipping Phone Verification. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeSwitch[-1]")
	@AndroidFindBy(id="check_dont_ask_again_v2")
	private WebElement skipDontAskCheckbox;

	/** The verify phone error message. */
	@FindBy(xpath="//*[contains(@resource-id,'message')]")
	private WebElement verifyPhoneErrMsg;

	/** The checker used to check the page occuring after this page .Verify DevicePage/HomePage/CardSelectionPage/SMS Alert*/
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Home') or contains(@text,'Device') or contains(@resource-id,'approve_textbutton')] | //android.widget.Button[contains(@text,'OK') or contains(@text,'Ok')]|//*[contains(@resource-id,'message')]")
	private WebElement checker;
	
	/** The verify abort button. */
	@FindBy(id="abort_button")
	private WebElement verifyAbortButton;
	
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Verify')]")
	private WebElement phoneVerificationIdentifier; 
	
	

	
	/**
	 * Instantiates a new VerifyPhonePage.
	 *
	 * @param driver the driver
	 */
	public VerifyPhonePage(WebDriver driver)
	{
		super(driver);
		this.driver= driver;
		softAssert = new SoftAssert();
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this); 
	}

	/**
	 *  
	 * 
	 * @param data
	 */
	public void loginWithPersonalDeviceWithSkip(String data)
	{
		String mobileNo=data.split(",")[0],
			   SIMProvider=data.split(",")[2];
		
		WebDriverWait wait= new WebDriverWait(driver,90);		// Wait for SIM Verification to complete
		
		Log.info("======== Login With Verify Personal Device , Performing SIM Verification ========");
		
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
		
		handleSMSAlert();
				
		
		
		// ==== Handle Verify Phone Authentication failed alert ==== //
		if (checker.getText().toLowerCase().contains("ok") && checker.getAttribute("resourceId").contains("button2")) 
		{
			Log.info("======== Skipping Phone verification ========");
			driver.findElement(By.id("button2")).click();
			skipDontAskCheckbox.click();
			continueButton.click();	
			Generic.wait(5);
			return;	// goto to VerifyDevice Page from verifyLogin() method, gotoHome() will not be executed		
		}			
		gotoHome();		
		
	}
	
	/**
	 * Tries to Login by selecting the SIM and Number. 
	 * If the Phone verification is taking too much time it aborts Phone Verification by clicking on 'Lets login ...' button.
	 * If Phone Verification fails then it skips the page and goes to Verify Device page.
	 * 
	 * @see loginWithPersonalDeviceWithSkip(String)
	 * 
	 */
	public void loginWithPersonalDeviceWithSkip()
	{
		Log.info("======== Login With Verify Personal Device ========");
		
		for(WebElement e: selectSimNumberList)   // Select any/all Sim and Number dropdowns
		{
			e.click(); 
			selectWithinList.get(1).click();
		}
		continueButton.click();
		//  Wait until presence of Home page or Verify device Page 	or Verify Failed popup
		
		WebDriverWait wait= new WebDriverWait(driver,90);		// 60 + 30 sec taken by try catch 
		try
		{
			wait.until(ExpectedConditions.visibilityOf(checker));	
			Generic.wait(2);			
		}
		catch(Exception e)
		{
			Assert.fail(" Page is taking too much time to load , stopping execution\n"+e.getMessage());
			
			/*Log.info("======== Page taking too much time to load , trying to click on Lets Login by cancelling phone verification  ========");
			try
			{
				verifyAbortButton.click();
				gotoHome();	
				return;	
			}
			catch(Exception e1)
			{
				Assert.fail(" Page is taking too much time to load , stopping execution\n"+e1.getMessage());
			}*/			
		}			
		
		// ==== Handle Verify Phone Authentication failed alert ==== //
		if (checker.getText().toLowerCase().contains("ok") && checker.getAttribute("resourceId").contains("button2"))
		{
			Log.info("======== Skipping Phone verification ========");
			driver.findElement(By.id("button2")).click();
			skipDontAskCheckbox.click();
			continueButton.click();	
			Generic.wait(5);
			return;	// goto to VerifyDevice Page from verifyLogin() method		
		}			
		gotoHome();		
	}	
	
	/**
	 * Waits for the next page after Verify Phone page. 
	 * Based on the Phone Verification status the next page could be Home Page,Verify Device page, Merchant Payment screen or Prepaid popup. 
	 */
	public void gotoHome()  // Wait for Home page or Verify Device page 
	{				  
		WebDriverWait wait= new WebDriverWait(driver,90);
		
		try
		{
			wait.until(ExpectedConditions.visibilityOf(checker));
		}
		catch(Exception e)
		{
			Assert.fail(" Page is taking too much time to load , stopping execution");
		}		
	}
	
	/**
	 * This method is used exclusively by LGN_005 which verifies Phone Verification failure.
	 */
	public void loginWithPersonalDevice()  // ==== To be used by LGN_005 and Verify SIM ==== //
	{
		try
		{
			new WebDriverWait(driver, 45).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[contains(@text,'Verify')]")));
		}
		catch(Exception e)
		{
			Assert.fail("Verify Phone page not found"+e.getMessage());
		}
		
		Log.info("======== Login With Verify Personal Device ========");
		if(pageTitle.getText().contains("Device"))
		{			
			Log.info("== Verify Device page found instead of Verify Phone page =="); 	
			return;
		}
		
		for(WebElement e: selectSimNumberList)   // Select any/all Sim and Number dropdowns
		{
			e.click();
			selectWithinList.get(1).click();
		}
		continueButton.click();
		//  Wait until presence of Home page or Verify device Page 		
		
		new WebDriverWait(driver,90).until(ExpectedConditions.visibilityOf(checker));
		
		if(checker.getText().toLowerCase().contains("ok") && checker.getAttribute("resourceId").contains("button1"))
		{	
			checker.click();
			
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(checker));
		}
		if(checker.getText().contains("OK") && checker.getAttribute("resourceId").contains("button2")) return;	// LGN_05 will verify this	
		
		//gotoHome();	No need to go to Home since Error messages and skip buttons need to be validated	
	}
	
	

	/**
	 * Login without personal device i.e Skips Phone Verification after unchecking personal device checkbox.
	 */
	public void loginWithoutPersonalDevice() {
		//new WebDriverWait(driver, 45).pollingEvery(1, TimeUnit.SECONDS).until(ExpectedConditions.visibilityOf(personalCheckbox));
		waitOnProgressBarId(45);
		if (pageTitle.getText().contains("Phone")) {

			Log.info("======== Unchecking personal device ========");
			personalCheckbox.click();
			Log.info("======== Selecting Dont ask again checkbox ========");
			skipDontAskCheckbox.click();
			Log.info("======== Clicking on Skip button ========");
			continueButton.click(); // Click on Skip , Skip=Continue
		}
	}
	
	/**
	 * Verifies the presence of Skip button while skipping Phone verification.
	 */	 
	public void verifySkip()
	{
		Assert.assertEquals(continueButton.getText(), "Skip");
	}

	/**
	 * Verifies the Phone verification failed message.
	 */
	public void deviceAndPhonVerificationErrMsg()
	{
		Log.info("======== Device Verification Error Message ========");

		try {
			softAssert.assertTrue(verifyPhoneErrMsg.getText().contains("failed"));
			softAssert.assertAll();
		} catch (Exception e){} 
		
		Log.info("======== Clicking on Verification failed OK button ========");
		okButton.click();
	}
	
	/**
	 * Waits for phone verification page to occur.
	 *
	 * @param driver the driver
	 */
	public  void waitForPhoneVerification() 
	{
		String xp="//android.widget.TextView[contains(@text,'Verify')]"; 
		try
		{
			new WebDriverWait(driver, 40).until(ExpectedConditions.visibilityOf(phoneVerificationIdentifier));		
		}
		catch(Exception e)
		{
			Assert.fail("Phone Verification page not found\n"+e.getMessage());
		}
	}
	
	
	public void handleSMSAlert()
	{
		// if postpaid sim from config.properties then return;
			// BaseTest1.checkPostPaidSIM(); 
		
		WebDriverWait wait=new WebDriverWait(driver, 60);
		
		wait.until(ExpectedConditions.visibilityOf(checker));
		
		if(!Generic.getAttribute(checker, "resourceId").contains("message"))
			return; // No Alert found
		else
		{
			if(checker.getText().contains("verification could not be completed"))
				Assert.fail("Phone Verification failed");
			
			Log.info("== Handling SMS Alert ==");
			okButton.click();
			
			waitOnProgressBarId(60);
		}
		
		wait.until(ExpectedConditions.visibilityOf(checker));
		
		if(!Generic.getAttribute(checker, "resourceId").contains("message"))
			return; // No Alert found
		else
		{
			Log.info("== Handling second SMS Alert ==");
			okButton.click();
			
			wait.until(ExpectedConditions.visibilityOf(checker));
		}
				
		
		
	}

	/**
	 *  
	 * 
	 */
	public void verifyPhonePage()
	{
		Log.info("======== Verifying Phone Verification Page ========");
		Assert.assertTrue(pageTitle.getText().contains("Verify"), "Phone Verification Page not found \n");		
	}

}
