package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.instrument.classloading.tomcat.TomcatLoadTimeWeaver;
import org.testng.Assert;
import wibmo.app.testScripts.BaseTest1;
import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
//import io.appium.java_client.TouchAction;
//import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.CSR;
import library.Generic;

/**
 * The Class MerchantHomePage is the Home Page for the Merchant simulator app.
 */
public class MerchantHomePage extends BasePage
{	
	/** The driver. */
	private WebDriver driver;
	
	@FindBy(xpath="//*[contains(@text,'Wibmo SDK')]")
	private WebElement merchantPageTitle;
	
	/** The transaction description text field. */
	@FindBy(xpath="//android.widget.EditText[contains(@resource-id,'merchant_txn_desc_value')]")
	private WebElement txnDescTextField;
	
	/** The amount text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[-1]")
	@AndroidFindBy(xpath="//android.widget.EditText[contains(@resource-id,'status_merchant_amount_value')]")
	private WebElement amtTextField;
	
	/** The payment radio button in IAP version 1. */
	@FindBy(xpath="//android.widget.RadioButton[contains(@resource-id,'radio_payment')]")
	private WebElement paymentRadioButton;
	
	/** The wibmo button for doing payment. */
	@iOSXCUITFindBy(iOSNsPredicate="name beginswith 'Pay'")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'One Step Payment')]")
	private WebElement wibmoButton;	
	
	/** The select app list checks the occurence of the Select App list when multiple apps are installed. */
	@FindBy(xpath="//*[contains(@text,'Complete action using') or contains(@content-desc,'Payment') or contains(@resource-id,'txn_program_title')] | //android.widget.Button")
	private WebElement selectAppList;
	
	/** The select browser checks the occurence of the browser list when multiple browsers are installed. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Complete')] | //android.view.View[contains(@content-desc,'resCode')]")
	private WebElement selectBrowser;
	
	/**  The just once button present with the Select App list. */
	@FindBy(xpath="//android.widget.Button[contains(@resource-id,'button_once') or contains(@resource-id,'got_it_button')] | //android.view.View[contains(@content-desc,'Payment')] | //android.widget.TextView[contains(@resource-id,'txn_program_title') or contains(@resource-id,'status_text')]")
	private WebElement justOnceButton;	
	
	/** The just once browser present with Select Browser list.. */
	@FindBy(xpath="//android.widget.Button[contains(@resource-id,'button_once')] | //android.view.View[contains(@content-desc,'resCode')]")
	private WebElement justOnceBrowser;
	
	/** SecurePin dialogue check */ 
	@AndroidFindBy(xpath="//*[contains(@resource-id,'txn_') or contains(@resource-id,'got_it')]")
	private WebElement securePinDialogueChk;
	
	/** The secure pin title. */
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Secure' or value beginswith 'Login'")
	@AndroidFindBy(id="txn_pin_label")
	private WebElement securePinTitle;
	
	/** The secure pin text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(id="main_pwd_edit")
	private WebElement securePinTextField;
	
	/** The secure pin login button. */
	@iOSXCUITFindBy(accessibility="Login")
	@AndroidFindBy(id="approve_textbutton")
	private WebElement securePinLoginButton;
	
	@FindBy(xpath="//*[contains(@resource-id,'button2')]") // Or Add Wait for UiAuto2
	private WebElement okBtn;
	
	/** The secure pin cancel button. */
	@FindBy(id="cancel_textbutton")
	private WebElement securePinCancelButton;
	
	@FindBy(xpath="//*[contains(@resource-id,'text1')]") 
	private List<WebElement> cancelReasons;
	
	/** The cancel confirmation yes button. */
	@FindBy(xpath="//*[contains(@resource-id,'button1')]") 
	private WebElement cancelYesBtn;
	
	@FindBy(xpath="//*[contains(@resource-id,'button2')]") 
	private WebElement cancelNoBtn;
	
	/** The transaction status check used to check whether a transaction has completed or not. */
	@iOSXCUITFindBy(accessibility="Ok")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@resource-id,'status_text') or contains(@resource-id,'start_qco_button') or contains(@text,'Wibmo SDK')]")
	private WebElement transactionStatusCheck;
	
	/** The transaction status message. */
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'Your payment'")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@resource-id,'status_text')]")
	private WebElement transactionStatusMessage;	
	
	@iOSXCUITFindBy(accessibility="Ok")
	private WebElement transactionStatusMessageOkBtn;
	
	/** The page check used to check between 3DS or ITP flow. */
	@FindBy(xpath="//*[starts-with(@content-desc,'XXXX') or starts-with(@text,'XXXX')] | //android.widget.TextView[contains(@resource-id,'status_text') or contains(@text,'Wibmo SDK')]") // either 3DS or merchant home page
	private WebElement pageCheck;
	
	/** The faster check out button . */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'Save for faster checkout')]")
	private WebElement fasterCheckOutBtn;
	
	/** The payment success message. */
	@FindBy(xpath="//android.view.View[contains(@content-desc,'Payment successful')]")
	private WebElement paymentSuccessMsg;
	
	/** The data pickup page. */
	@FindBy(xpath="//android.view.View[contains(@content-desc,'resCode')]")
	private WebElement dataPickupPage;
	
	/** The remind me later link. */
	@FindBy(xpath="//android.view.View[contains(@content-desc,'remind me later')]")
	private WebElement remindMeLaterLink;
	
	/** The verify failure message. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'user abort')]")
	private WebElement verifyFailureMsg;	

	/** The wibmo IAP merchant id text. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Wibmo IAP Merchant ID')]")
	private WebElement wibmoIAPMerchantId;

	/** The click more opens the More Options menu. */
	@FindBy(xpath="//android.widget.ImageView[contains(@content-desc,'More options')]")
	private WebElement clickMore;

	/** The settings link under More Options. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Settings')]")
	private WebElement settingsLink;
	
	/** Used to check whether the default landing page in Merchant App is Settings Page or Merchant Home Page . */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Wibmo SDK Test') or contains(@text,'App Package Name')]")
	private WebElement defaultSettingsPageCheck;
	
	@FindBy(xpath="//*[contains(@resource-id,'txn_') or contains(@resource-id,'got_it_button')]")
	private WebElement coachChk;
	
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	private  WebElement optionalLoginIdTxtField;

	@iOSXCUITFindBy(accessibility="Continue")
	private WebElement optinalLoginContinueBtn;
	
	
	//------------------------ Radio Buttons for IAPV2 --------------------------------//
	
	/** The iapv1 radio button. */
	@FindBy(xpath="//android.widget.RadioButton[contains(@resource-id,'radio_inapp_v1')]")
	private WebElement iapv1RadioBtn;
		
	/** The iapv2 radio button. */
	@FindBy(xpath="//android.widget.RadioButton[contains(@resource-id,'radio_inapp_v2')]")
	private WebElement iapv2RadioBtn;
	
	/** The w2fa radio button. */
	@FindBy(xpath="//android.widget.RadioButton[contains(@resource-id,'radio_2fa')]")
	private WebElement w2faRadioBtn;
	
	/** The wpay radio button. */
	@iOSXCUITFindBy(accessibility="WPay")
	@AndroidFindBy(xpath="//android.widget.RadioButton[contains(@resource-id,'radio_payment')]")
	private WebElement wpayRadioBtn;
	
	/** The amount known true radio button/selector. */
	@iOSXCUITFindBy(iOSNsPredicate="name='true' and visible=true")
	@AndroidFindBy(xpath="//android.widget.RadioButton[contains(@resource-id,'amt_known_value_true')]")
	private WebElement amountKnownTrueRadioBtn;
	
	/** The amount known false radio button/selector. */
	@iOSXCUITFindBy(iOSNsPredicate="name='false' and visible=true")
	@AndroidFindBy(xpath="//android.widget.RadioButton[contains(@resource-id,'amt_known_value_false')]")
	private WebElement amountKnownFalseRadioBtn;
	
	/** The charge later true radio/select button. */
	@iOSXCUITFindBy(iOSNsPredicate="name='true' and visible=true")
	@AndroidFindBy(xpath="//android.widget.RadioButton[contains(@resource-id,'charge_ondpu_true')]")
	private WebElement chargeLaterTrueRadioBtn;
	
	/** The charge later false radio/select button. */
	@iOSXCUITFindBy(iOSNsPredicate="name='false' and visible=true")
	@AndroidFindBy(xpath="//android.widget.RadioButton[contains(@resource-id,'charge_ondpu_false')]")
	private WebElement chargeLaterFalseRadioBtn;
	
	/** The charge status true radio button. */
	@iOSXCUITFindBy(iOSNsPredicate="name='true' and visible=true")
	@AndroidFindBy(xpath="//android.widget.RadioButton[contains(@resource-id,'charge_onstatus_true')]")
	private WebElement chargeStatusTrueRadioBtn;
	
	/** The charge status false radio button. */
	@iOSXCUITFindBy(iOSNsPredicate="name='false' and visible=true")
	@AndroidFindBy(xpath="//android.widget.RadioButton[contains(@resource-id,'charge_onstatus_false')]")
	private WebElement chargeStatusFalseRadioBtn;
	
	/** The check status data pickup button. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and name beginswith 'Check Status'")
	@AndroidFindBy(xpath="//android.widget.Button[contains(@resource-id,'button_check_status') or contains(@resource-id,'button_do_data_pickup')]")
	private WebElement checkStatusDataPickupBtn;	
	
	/** The result status. */
	@iOSXCUITFindBy(iOSNsPredicate="name contains 'hashVer'")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@resource-id,'result_status') or contains(@resource-id,'result_data_pickup')]")
	private WebElement resultStatus;	
	
	/** The charge amount textfield. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(className="android.widget.EditText")
	private WebElement chargeAmtTextfield;
	
	/** The charge amount ok button. */
	@iOSXCUITFindBy(accessibility="Ok")
	@AndroidFindBy(xpath="//*[@text='Ok' or @text='OK']")
	private WebElement chargeAmtOkBtn;
	
	@AndroidFindBy(xpath="//*[contains(@resource-id,'txn_forgot_pin')]")
	private WebElement forgotPinLnk;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeButton[`name=\"true\" or name=\"false\"`][1]")
	private WebElement amtKnownBtn;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeButton[`name=\"true\" or name=\"false\"`][2]")
	private WebElement chargeLaterBtn;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeButton[`name=\"true\" or name=\"false\"`][3]")
	private WebElement chargeStatusBtn;
	
	@iOSXCUITFindBy(accessibility="Ok")
	private WebElement sufOkBtn;
	
	
	/**
	 * Instantiates a new MerchantHomePage.
	 *
	 * @param driver the driver
	 */
	public MerchantHomePage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * 
	 *  INI/CSV KeyValue Compatible 
	 * 
	 * @param txnDetails as loginId,securePin,amt,txnType
	 * @see executePayment(data)
	 * 
	 */
	public void executePayment(String...txnDetails)
	{
		String loginId=txnDetails[0],securePin=txnDetails[1],amt=txnDetails[2],txnType=txnDetails[txnDetails.length-1];				
		executePayment(loginId+','+securePin+','+amt+','+txnType);
	}
	
	/**
	 * Executes a merchant payment after configuring the environment settings and entering all the values 
	 * Currently not using selectWithoutApp() for triggering WebOverlay
	 *
	 * @param data the data
	 */
	public void executePayment(String data)
	{	
		//================= Set Environment Preconditions =======//
		
		if(Generic.checkEnv("staging"))
			setStaging();
		
		//=======================================================//
		String[] str=data.split(",");
		
		String amt=str[2].contains("@")?str[3]:str[2];
		Log.info("======== Entering Amount : "+amt+" ========");
		if(!amtTextField.getText().equals(amt))
		{
			amtTextField.clear();			
			amtTextField.sendKeys(amt);
			Generic.hideKeyBoard(driver);
		}		
		 
		enterTxnDesc(data);
		
		setTransactionConfiguration(data);
		
		Log.info("======== Clicking on Wibmo Pay button ========");	
		clickWibmoButton();	
		
	}
	
	public void clickWibmoButton()
	{
		Log.info("======== Clicking on Wibmo button ========");	
		Generic.scroll(driver, "One Step");
		wibmoButton.click();		
	}
	
	/**
	 * Sets the transaction configuration by selecting the appropriate radio buttons based on the TestData.
	 * IAP TestData should contain either of IAPV1,IAPV2,Taxi 
	 * @param data the new transaction configuration
	 */
	public void setTransactionConfiguration(String data)
	{
		
		if(Generic.isIos(driver))
			{setTxnConfigIos(data);return;}	
		
		if(Generic.containsIgnoreCase(data, "IAPV1"))
		{
		    Log.info("======== Selecting IAPV1 ========");
			iapv1RadioBtn.click(); 
			return;
		}
		
		iapv2RadioBtn.click();
		if(Generic.containsIgnoreCase(data, "Taxi"))
		{
			Log.info("======== Selecting Taxi ========");
			amountKnownFalseRadioBtn.click();
			chargeLaterTrueRadioBtn.click();
			return;
		}
		
		if(Generic.containsIgnoreCase(data, "IAPV2"))
		{
			Log.info("======== Selecting IAPV2 ========"); // To Execute Multiple Selection from Single TC , restart App & Replace TestData
			amountKnownTrueRadioBtn.click();
			chargeLaterTrueRadioBtn.click();
			return;  
		}		
	}
	
	public void setTxnConfigIos(String data)
	{
		// ======== IAPV1 ======== //
		if(Generic.containsIgnoreCase(data, "IAPV1"))
		{
			Log.info("======== Setting Amount Known & Charge Later for IAPV1 ========");
			
			if(Generic.getAttribute(amtKnownBtn, "name").equals("false"))  
			{
				amtKnownBtn.click();
				Generic.wait(1);
				amountKnownTrueRadioBtn.click();
			}
			
			if(chargeLaterBtn.getAttribute("name").equals("true"))
			{
				chargeLaterBtn.click();
				Generic.wait(1);
				chargeLaterFalseRadioBtn.click();
			}
		}
		// ========IAPV2============ //
		
		if(Generic.containsIgnoreCase(data, "IAPV2"))
		{
			Log.info("======== Setting Amount Known & Charge Later for IAPV2 ========");
			
			if(amtKnownBtn.getAttribute("name").equals("false"))
			{
				amtKnownBtn.click();
				Generic.wait(1);
				amountKnownTrueRadioBtn.click();
			}
			
			if(chargeLaterBtn.getAttribute("name").equals("false"))
			{
				chargeLaterBtn.click();
				Generic.wait(1);
				chargeLaterTrueRadioBtn.click();
			}
		}
		
		if(Generic.containsIgnoreCase(data, "Taxi"))
		{
			Log.info("======== Setting Amount Known & Charge Later for Taxi ========");
			
			if(amtKnownBtn.getAttribute("name").equals("true"))
			{
				amtKnownBtn.click();
				Generic.wait(1);
				amountKnownFalseRadioBtn.click();
			}
			
			if(chargeLaterBtn.getAttribute("name").equals("false"))
			{
				chargeLaterBtn.click();
				Generic.wait(1);
				chargeLaterTrueRadioBtn.click();
			}
		}
		
	}
	
	/**
	 * Clicks on check status on data pickup button. Can be used for both V1 and V2
	 *
	 * @param data the data
	 */
	public void clickCheckStatusDataPickup(String chargeStatus)
	{
		if(Generic.isAndroid(driver))  // ======================= Android ====================== //
		{	
			if(Generic.checkTextInPageSource(driver, "Response").contains("notFound"))
			{
				Generic.swipeToBottom(driver);
			}
			else
			{
				Log.info("======== Scrolling to Charge On Status Check ========");
				Generic.scroll(driver,"Charge On");
			}
			
			//==== IAPV1 ==== //
			if(Generic.isAndroid(driver) && checkStatusDataPickupBtn.getText().toLowerCase().contains("pickup"))
			{
				Log.info("======== Clicking on Data Pickup button ========");
				checkStatusDataPickupBtn.click();
				return;			 
			}
			
			//==== IAPV2 ==== //
			Log.info("======== Selecting Charge Status : "+chargeStatus+" ========");
			if(chargeStatus.toLowerCase().contains("true"))
				chargeStatusTrueRadioBtn.click();
			else
				chargeStatusFalseRadioBtn.click();	
		}
		// =================== IOS =================== //
		else
		{
			Log.info("======== Selecting Charge Status : "+chargeStatus+" ========");
			
				if(chargeStatus.toLowerCase().contains("true") && Generic.getAttribute(chargeStatusBtn, "name").contains("false"))  // true ==> true
				{
					chargeStatusBtn.click();
					chargeStatusTrueRadioBtn.click();
				}
				if(chargeStatus.toLowerCase().contains("false") && Generic.getAttribute(chargeStatusBtn, "name").contains("true")) // false ==> false
				{
					chargeStatusBtn.click();
					chargeStatusFalseRadioBtn.click();
				}			
		}
		
		Log.info("======== Clicking on Charge Status button ========");
		checkStatusDataPickupBtn.click();		
		
	}
	
	/**
	 * Enters transaction description.
	 * Used to check for Transaction Entry in CSR based on TransDesc.
	 *
	 * @param data the data
	 */
	public void enterTxnDesc(String data)
	{
		if(Generic.isIos(driver)) return;  
		
		
		 // Used to check Transaction Entry based on TransDesc
		if(!data.contains("TY_")) return;
		String txnDesc=data.split(",")[4];
		
		Log.info("======== Entering Transaction Description : "+txnDesc+" ========");
		CSR.txnDesc=txnDesc; 			// Used for checking Init Success 
		txnDescTextField.clear();
		txnDescTextField.sendKeys(txnDesc);
		Generic.hideKeyBoard(driver);		
		
	}
	
	/**
	 * Waits for the optional occurence of the Select App list and selects the app based on the programName.
	 *  
	 *
	 * @param programName the program name
	 */
	public void selectApp(String programName)
	{
		
		if(BaseTest1.checkEnv("qa")) return; // Select App screen does not occur for QA		
		if(Generic.isIos(driver)) {		wpayRadioBtn.click();		return;}
		
		//Generic.wait(1); // UiAuto2
		//new WebDriverWait(driver, 35).until(ExpectedConditions.visibilityOf(selectAppList)); // Requires more device specific enhancement when other program IAPs are automated
		//if(!selectAppList.getText().contains("Complete")) return; 
		
		if(!Generic.waitUntilTextInPage(driver, 40, "Complete action","txn_program_title","got_it_button").contains("Complete")) return; // App Select screen / Payment screen / Coach / Web Overlay TBA
				
		Log.info("======== Selecting app : "+programName+" ========");
		String programXp="//android.widget.TextView[contains(@text,'"+programName+"')]";
		
		driver.findElement(By.xpath(programXp)).click();		
		
		try
		{
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(justOnceButton));
		}
		catch(Exception e)
		{
			Assert.fail("Page or overlay not found \n"+e.getMessage());
		}
		
		try{
			Generic.wait(1);
			String justOnceCheck=(justOnceButton.getAttribute("resourceId")+justOnceButton.getText()+justOnceButton.getAttribute("contentDescription")).toLowerCase();
			if(justOnceCheck.contains("just") || justOnceCheck.contains("once"))
			{	
				Log.info("======== Clicking on Just Once button ========");	
				justOnceButton.click();		
			}
			}catch(Exception e){Log.info("======== Execution delayed since just once button was not found ========");}		
	}
	
	/**
	 * Waits for the optional occurence of the Select App list and then cancels app selection.
	 * Used to open the Web Overlay.
	 */
	public void selectWithoutApp()
	{
		if(true) return ;// App Invisibility set from Merchant App Settings
		
		if(BaseTest1.checkEnv("qa"))
		{
			Log.info("======== Assuming QA app not present to trigger Web Overlay screen ========");
			return;
		}	
		
		/*if(BaseTest1.checkEnv("qa"))
		{
			Log.info("======== Clearing App Data to trigger Web Overlay screen ========");
			BaseTest1.clearApp();
			return;
		}*/
		try
		{
			new WebDriverWait(driver,45).until(ExpectedConditions.visibilityOf(selectAppList));
		}
		catch(Exception e)
		{
			Assert.fail("Page taking too much time to load,stopping execution \n"+e.getMessage());
		}
		
		if(!selectAppList.getText().contains("Complete")) return;
		
		Log.info("======== Cancelling App Selection List to trigger Web Overlay ========");
		Generic.wait(2); // Server sync attempt
		driver.navigate().back();		
	}
	
	/**
	 *  Handles the card Authentication failed alert 
	 *  Accept alert if  tryAnotherCard is true
	 *   To be upgraded to  Android AlertAccept
	 *   
	 * @param tryAnotherCard
	 */
	public void handleCardAuthFailAlert(boolean tryAnotherCard)
	{
		Log.info("======== Handling Card Authentication Failed Alert ========");
		if(tryAnotherCard)
			cancelYesBtn.click();
		else
			cancelNoBtn.click();
	}
	
	/**
	 * Verifies the occurence of Secure Pin prompt.
	 */
	public void verifySecurePinPresent()
	{
		Log.info("======== Verifying Secure PIN screen ========");		
		try
		{
			String verifySecurePin=securePinTitle.getText();
			Assert.assertTrue(verifySecurePin.contains("PIN") || verifySecurePin.contains("ID") , "Secure PIN screen not displayed correctly\n");
		}
		catch(Exception e)
		{
			Assert.fail("Secure PIN screen not displayed \n"+e.getMessage());e.printStackTrace();
		}		
	}
	
	public boolean checkSecurePinOccurence()
	{
		
		Log.info("======== Verifying optional occurence of Secure Pin screen ========");
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(securePinDialogueChk));
		return driver.getPageSource().contains("txn_pin_label");  // txn_pin_label = Secure PIN text  or use Generic.getAtribute();
		
	}
	
	public void handleCoach()
	{
		System.out.println("Handling early Coach");
		coachChk.click();
	}
	
	/**
	 * Enters secure pin into the secure pin prompt.
	 *
	 * @param data the data
	 */
	public void enterSecurePin(String data)
	{
		verifySecurePinPresent();
		String pin=data.split(",")[1];	
		
		if(securePinTitle.getText().contains("Login"))	 
		{
			Log.info("======== Handling optinal LoginId field ========");
			optionalLoginIdTxtField.sendKeys(data.split(",")[0]);
			optinalLoginContinueBtn.click();
		}
		
		Log.info("======== Entering PIN in IAP ========");
		securePinTextField.sendKeys(pin);	
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Clicking on Login Button in IAP ========");
		securePinLoginButton.click();		// Add request Time Out Handling in Generic.merchantVerifiedLogin();
		
	}
	
	public void handleRequestTimeOut(String data)
	{		
		Log.info("======== Handling Request Time Out ========");
		okBtn.click();
		
		enterSecurePin(data);	
	}
	

	/**
	 * Enters static or dynamic id into the wibmoIAPMerchantId under the merchant app settings.
	 *
	 * @param data the data
	 */
	public void enterStaticOrDynamicId(String data)
	{
		int i= 4;
		String[] values = data.split(",");
		String id=values[i++];

		Log.info("======== Clicking on More ========");
		clickMore.click();
		
		Log.info("======== Clicking on Settings ========");
		settingsLink.click();
		
		Generic.scroll(driver, "Wibmo IAP Merchant ID");
		
		Log.info("======== Entering Wibmo IAP Merchant ID :"+id+" ========" );
		if(!wibmoIAPMerchantId.getText().equals(id))
		{
			wibmoIAPMerchantId.click();
			wibmoIAPMerchantId.sendKeys(id);
			
			
			Log.info("======== Clicking on OK Button ========");
			okBtn.click();
			Generic.wait(2);
			driver.navigate().back();			
		}
	}
	
	/**
	 * Verifies the absence of secure pin prompt.
	 *
	 * @param data the data
	 */
	public void verifySecurePinAbsent(String data)
	{
		Log.info("======== Verifying absence of secure pin layout ========");
		String xp = "//android.widget.TextView[contains(@resource-id,'approve_textbutton') or contains(@resource-id,'txn_pin_label')] | //android.widget.Button";
		try {
			new WebDriverWait(driver, 45).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));
		} catch (Exception e) 
		{
			Assert.fail("Page taking too much time to load "+e.getMessage());
		}
		
		if(driver.findElement(By.xpath(xp)).getAttribute("resourceId").contains("txn_pin_label"))
		Assert.fail("Secure Pin layout was displayed ");
		
	}
	
	/**
	 * Verifies merchant payment success.
	 *
	 * @return the txnId
	 */
	public String verifyMerchantSuccess()
	{
		String msg ="Default";
		String txnId;
		
		// Generic.switchToNativeApp(driver); 
		//try
		{
			new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOf(transactionStatusCheck));
			Generic.scroll(driver,"Merchant Server");			
			//if(transactionStatusCheck.getAttribute("resourceId").contains("qco"))
			//try{Generic.scroll(driver, "CHECK STATUS");}catch(Exception e){Log.info("======== Scroll delay ========");}
		}
		/*catch(Exception e)
		{		
			Assert.fail("Transaction status message not found \n"+e.getMessage());
		}*/
		
		try
		{
			 msg=transactionStatusMessage.getText();
			Log.info("======== Verifying status message : " +msg+" ========");
			Assert.assertTrue(msg.contains("success")," Transaction was not successful \n");
		}
		catch(Exception e)
		{
			Assert.fail("Transaction was not successful \n "+e.getMessage());
		}
		
		if(Generic.isAndroid(driver))
		{
			Log.info("======== Verifying transaction ID : "+msg.split(";")[1].split(":")[1]+" ========");
			Assert.assertTrue(msg.contains("wPayTxnId") && msg.split(";")[1].split(":")[1].length()>5," Transaction Id not found \n");
			
			//Log.info("======== Verifying "+msg.split(";")[2]+" ========");
			//Assert.assertTrue(msg.contains("dataPickUpCode") && msg.split(";")[2].split(":")[1].length()>5," Pickup code not found \n");
		}
		else
		{
			Log.info("======== Verifying transaction ID : "+(txnId=msg.split(":")[1].split("PickUp")[0].trim())+" ========");
			Assert.assertTrue(txnId.contains("201") && txnId.length()>5," Transaction Id not found \n");
			transactionStatusMessageOkBtn.click();
			msg=txnId;
		}

		return msg;
	}
	
	/**
	 * Verifies an ITP transaction.
	 */
	public void verifyITP()
	{		
		try
		{
			MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
			mp3p.enterCvv("");	 // Default blank value		
			
		Log.info("======== Verifying ITP ========");
		try
		{
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(pageCheck));
		}
		catch(TimeoutException e)
		{
			Assert.fail("ITP was not executed since Page was taking too much time to load or Page not found \n"+e.getMessage());
		}		
		if(pageCheck.getAttribute("contentDescription").contains("XXXX"))
			Assert.fail("======== ITP was not executed ========");	
		
		} catch(Exception e){Assert.fail("ITP not executed "+e.getMessage());}
	}
	
	/**
	 * Verifies a non ITP transaction.
	 */
	public void verifyNonITP()
	{
		Log.info("======== Verifying non ITP flow ========");
		try
		{
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(pageCheck));
		}
		catch(TimeoutException e)
		{
			Assert.fail("Page taking too much time to load or Page not found, stopping execution \n"+e.getMessage());
		}		
		if(pageCheck.getAttribute("resourceId").contains("status_text"))
			Assert.fail(" ITP was executed, without 3DS page ");	
	}
	
/**
 * Verifies guest payment success.
 */
public void verifyGuestPayment()
{
	Log.info("======== Verify guest payment and click on Faster checkout button ========");
	try 
	{
		Assert.assertTrue(paymentSuccessMsg.isDisplayed());
		fasterCheckOutBtn.click();
	} catch (Exception e) 
	{
		Assert.fail("Payment Success Message not found"+e.getMessage());
	}	
}

/**
 * Selects the default browser the Select Browser list.
 * The default browser is currently set to Chrome.
 */
public void selectBrowser()
{		
	new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(selectBrowser));
	
	if(!selectBrowser.getText().contains("Complete")) return;
	
	Log.info("======== Selecting default browser ========");	
	driver.findElement(By.name("Chrome")).click();		
	try
	{
		new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOf(justOnceBrowser));
	}
	catch(Exception e)
	{
		Assert.fail("Page not found \n"+e.getMessage());
	}
	
	if(justOnceBrowser.getAttribute("resourceId").contains("button_once"))
	{	Log.info("======== Clicking on Just Once button ========");	
		justOnceBrowser.click();		
	}			
}

/**
 * Performs a data pickup.
 */
public String performDataPickup()
{	
	Log.info("======== Clicking on Data Pickup ========");
	//new TouchAction((AndroidDriver)driver).longPress(transactionStatusMessage).perform();
	Generic.scroll(driver, "MERCHANT SERVER");	
	checkStatusDataPickupBtn.click();
	
	Log.info("======== Verifying SUF ========");
	try{Generic.scroll(driver, "resCode");  } catch(Exception e) {Assert.fail(" Result Status SUF not found "+e.getMessage());e.printStackTrace();} // MERCHANT SERVER
	
	String suf=resultStatus.getText();
	
	Log.info("======== Verifying transaction enquiry status message : "+suf+" ========");
	Assert.assertTrue(suf.length()>1, "Transaction Enquiry Status message (S/U/F) not found\n");	
	
	return suf;
}	

public void performDataPickup(String csv)
{
	String suf=performDataPickup();	
	
	Log.info("======== Verifying SUF fields ========");
	for(String f:csv.split(","))
	{
		Log.info(f);
		Assert.assertTrue(Generic.containsIgnoreCase(suf, f), f+" not found in SUF\n");
	}
}

/**
 * Verifies the presence of Remind me link to skip full registration and clicks it
 */
public void verifyRemindMeLink()
{
	Log.info("======== Verifying Successful Payment and Skip Registration link ========");
	try 
	{
		new WebDriverWait(driver,45).until(ExpectedConditions.visibilityOf(paymentSuccessMsg));
		Assert.assertTrue(paymentSuccessMsg.isDisplayed());
		remindMeLaterLink.click();
	} catch (Exception e) 
	{
		Assert.fail("Payment Success Message not found\n"+e.getMessage());
	}
}

/**
 * Verifies transaction failure message.
 */
public String verifyFailureMessage()
{
	String statusMsg="DefaultMsg";
	
	try
	{
		new WebDriverWait(driver,10).until(ExpectedConditions.visibilityOf(merchantPageTitle));
		Generic.swipeToBottom(driver);
		new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOf(transactionStatusMessage));
	}
	catch(Exception e)
	{
		Assert.fail("Failed Transaction status message not found \n"+e.getMessage());
	}	
	try 
	{
		statusMsg = transactionStatusMessage.getText();
		Log.info("======== Verifying Failure Message : "+statusMsg+" ========");
		Assert.assertTrue(statusMsg.toLowerCase().contains("failure") || statusMsg.toLowerCase().contains("failed"));		
	} catch (Exception e) 
	{
		Assert.fail("Failure message not found\n"+e.getMessage());
	}
	return statusMsg;
}

/**
 * Navigates to Merchant App settings page.
 */
public void gotoSettings()
{
	// if(defaultSettingsPageCheck.getText().contains("App")) return; // Already in the Settings Page UiAuto2 issue
	
	if(Generic.checkTextInPageSource(driver, "Wibmo SDK Test","App Package Name").contains("App")) return;
	
	Log.info("======== Clicking on More ========");
	clickMore.click();
	
	Generic.wait(1); // UiAuto2
	
	Log.info("======== Clicking on Settings ========");
	settingsLink.click();
	
}

/**
 * Cancels the secure pin prompt.
 */
public void cancelSecurePin() 
{
	verifySecurePinPresent();
	Log.info("======== Clicking on Cancel button ========");
	securePinCancelButton.click();	
	Generic.wait(2);
	cancelYesBtn.click();	
	
	handleCancelReason();
}

public void handleCancelReason()
{		
	Log.info("======== Handling Cancel reason ========");		
	try{cancelReasons.get(new Random().nextInt(cancelReasons.size()-1)).click();}catch(Exception e){Log.info("== Delay due to non occrence of Cancel reason list ==");}		
}

/**
 * Sets the merchant environment settings to QA .
 */
public void setQA()
{
	if(Generic.isIos(driver)) return;
	
	Log.info("======== Checking for QA Settings in Merchant App ========");
	gotoSettings();
	
	MerchantSettingsPage msp=new MerchantSettingsPage(driver);
	msp.setQASettings();
	
}

/**
 * Sets the merchant environment settings to Staging .
 */
public void setStaging()
{
	if(Generic.isIos(driver)) return;
	
	Log.info("======== Checking for Staging Settings in Merchant App ========");	
	gotoSettings();
	
	MerchantSettingsPage msp=new MerchantSettingsPage(driver);
	msp.setStagingSettings();	
}

/**
 * Checks the presence of S/U/F.
 */
public String checkSUF()
{
	Generic.scroll(driver, "Merchant Server");	// page_up keycode can be tried instead
	if(Generic.isAndroid(driver) && checkStatusDataPickupBtn.getAttribute("resourceId").contains("check_status")) // IAPV2/Taxi
	{
		Log.info("======== Scrolling to SUF ========");		
		try
		{
			Generic.scroll(driver, "resCode");
		}
		catch(Exception e) {Log.info("Response SUF not found");}
	}	
	
	String suf=Generic.isAndroid(driver)?resultStatus.getText():Generic.getAttribute(resultStatus, "name");
	Log.info("======== Verifying transaction enquiry status message : "+suf+" ========");
	Assert.assertTrue(suf.length()>1, "Transaction Enquiry Status message (S/U/F) not found\n");	// try catch not required , resultStatus element will be present by default
	
	return suf;
}

/**
 * Checks the presence of S/U/F.
 * 
 * To be enhanced to checkSUF(String... csv)
 */
public void checkSUF(String csv)
{	
	String suf=checkSUF();	
	
	Log.info("======== Verifying SUF fields ========");
	for(String f:csv.split(","))
	{
		Log.info(f);
		suf=suf.replaceAll(" ", "").replaceAll("\"", ""); 		// Handle Whitespaces & Quotes . Compatibility between Android & IOS platform responses
		f=f.replaceAll(" ", "").replaceAll("\"", "");  
				
		Assert.assertTrue(Generic.containsIgnoreCase(suf, f), f+" not found in SUF\n");
	}
	
	if(Generic.isIos(driver)) 
		sufOkBtn.click();
		
}

/**
 * Enters the charge amount for a Charge On Status transaction.
 *
 * @param chargeAmt the charge amt
 */
public void enterChargeAmount(String chargeAmt)
{		
	chargeAmtOkBtn.isDisplayed(); // Prevent Stale 
	
	Log.info("======== Entering Amount : "+chargeAmt+" ========");
	if(!chargeAmtTextfield.getText().equals(chargeAmt))
	{
		chargeAmtTextfield.clear();
		chargeAmtTextfield.sendKeys(chargeAmt); 
	}
	
	if(Generic.isAndroid(driver))
	{
		Generic.hideKeyBoard(driver);
		Generic.wait(1); 	// UiAuto2
	}
	
	chargeAmtOkBtn.click();
}

/**
 *  Configures the settings such that hash fail occurs.
 */
public void setHashFail()
{
	gotoSettings();
	
	MerchantSettingsPage msp=new MerchantSettingsPage(driver);
	msp.setHashFailValue();	
	
	driver.navigate().back();
}

public void clickForgotPinLnk()
{
	Log.info("======== Clicking on Forgot Pin link ========");
	forgotPinLnk.click();
}

/**
 * 	Parses the txnId from Txn Status Message. Ex : success; wPayTxnId: 201807241004513094cQ44eI0; ... 
 * 
 * @param txnStatusMsg
 * @return txnId 
 */
public String parseTxnIdFromTxnStatus(String txnStatusMsg)
{
	return txnStatusMsg.split(";")[1]
										 .split(":")[1]
										 .trim();			
}

public void verifyAuthenticationFailedMsg() 
{
	String msg;
	
	try
	{
		okBtn.isDisplayed(); 
		Log.info("======== Verifying message : "+(msg=alertMessage.getText())+" ========");
		Assert.assertTrue(msg.contains("authentication"), "Authentication failed message not found\n");
		okBtn.click();
	}
	catch(Exception e)
	{
		Assert.fail("Authentication fail message not found \n"+e.getMessage());
	}
}

	
}
