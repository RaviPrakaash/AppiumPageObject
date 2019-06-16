package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import library.Log;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;

/**
 * The Class MobileRecharge3DSPage.
 */
public class MobileRecharge3DSPage extends BasePage
{

	/** The driver. */
	private WebDriver driver;
	
	/** The card number displayed in masked format. */
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'XXXX'")
	@AndroidFindBy(xpath="//*[contains(@content-desc,'XXXX XXXX XXXX') or contains(@text,'XXXX')]")
	private WebElement maskedCardNumber;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'Order Id' or value beginswith 'XXXX' or value contains 'CVV'")
	@AndroidFindBy(xpath="//*[contains(@content-desc,'XXXX') or contains(@text,'XXXX') or contains(@resource-id,'rc_status_btnOkay') or contains(@text,'CVV')]")
	private WebElement pageCheck;	
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'Order Id' or value contains 'CVV'")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'rc_status_btnOkay') or contains(@text,'CVV')]")
	private WebElement cvvChk;
	
	/** The secure pin text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(className="android.widget.EditText")
	private WebElement securePinTextField;
	
	/** The submit button. */
	@FindBy(xpath="(//android.widget.Button)[2]")
	private WebElement submitButton;
	
	/** The cancel button. */
	@iOSXCUITFindBy(iOSNsPredicate="name='Cancel' and visible=true")
	@AndroidFindBy(xpath="//android.widget.Button[contains(@resource-id,'Cancel') or contains(@content-desc,'cancel')]") // Cancel buttons for Wibmo Card & Corp card. Or Close Authentication Page Cancel 
	private WebElement cancelButton; 
	
	/** List of all buttons in 3DS page. */
	@FindBy(className="android.widget.Button")     // id="cmdSubmit"
	private List<WebElement> buttons;
	
	/** The 3DS cancel confirm OK button. */
	@iOSXCUITFindBy(iOSNsPredicate="name=[ci]'OK'") 
	@AndroidFindBy(xpath="//*[contains(@resource-id,'button1')]")
	private WebElement cancelConfirmOKBtn;
	
	/** The cvv text field. */
	@FindBy(className="android.widget.EditText")
	private WebElement cvvTextField;
	
	/** The cvv submit button. */
	@FindBy(xpath="//*[contains(@resource-id,'approve_textbutton') or contains(@text,'Continue')]")
	private WebElement cvvSubmitButton;
	
	/** The submit check. */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'title_text') or contains(@resource-id,'message')]")
	private WebElement submitCheck;
	
	/** The popup cancel btn. */
	@FindBy(xpath="//*[@text='Cancel']") 
	private WebElement popupCancelBtn;
	
	/** The 3DS authentication failed error message. */
	@iOSXCUITFindBy(iOSNsPredicate="name contains 'failed'")
	@AndroidFindBy(xpath="//*[contains(@content-desc,'failed') or contains(@text,'failed')]") // Authentication failed in page or alert
	private WebElement authErrMsg;
	
	/** The progress bar displayed when 3DS page is loading. */
	@FindBy(id="smoothprogressbarParent")
	private WebElement progressBar; 	
	
	/** The static int i,j used to count recursions and will be reset to 0 after recursion completes. */
	public static int i,j;
	
	/**
	 * Instantiates a new mobile recharge 3 DS page.
	 *
	 * @param driver the driver
	 */
	public  MobileRecharge3DSPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Waits and Verifies the occurrence of 3DS page during Mobile Recharge.
	 * If 3DS page is not detected by autowebview capability it executes a normal tap to identify the 3DS page
	 */
	public void verifyMobileRecharge3DS()
	{
		i++;
		Log.info("======== Verifying 3DS Page ========");
		try 
		{
			new WebDriverWait(driver,20).until(ExpectedConditions.visibilityOf(pageCheck));
			
		} catch (Exception e1) 
		{
			Generic.tap3DS(driver);
			if(i>=3)
			{
				i=0;
				Assert.fail("3DS Page not Displayed \n" +e1.getMessage());
			}
			verifyMobileRecharge3DS();			
			return;
		}		
		i=0;		
		try 
		{
			Assert.assertTrue(maskedCardNumber.isDisplayed());
			Log.info("======== Verifying Secure Pin text field ========");
			Assert.assertTrue(securePinTextField.isDisplayed());
		} 
		catch (Exception e) 
		{
			Assert.fail("3DS Page not Displayed \n" +e.getMessage());
		}
		i=0;
	}
	
	/**
	 * Waits and Verifies for the occurence of 3DS page. 
	 * If 3DS page is not detected by autowebview capability it executes a normal tap to identify the 3DS page.
	 * The occurence of page to be verified by pageCheck element
	 * 
	 */
	public void verify3DS()
	{	
		i++; // Attempt with non static i for parallel
		Log.info("======== Verifying 3DS page ========");
		try
		{
			new WebDriverWait(driver,20).until(ExpectedConditions.visibilityOf(pageCheck));
			
		}
		catch(Exception e)
		{
			Log.info("======== Executing tap 3DS ========");
			Generic.tap3DS(driver);
			if(i>=3)
			{
				i=0;
				Assert.fail("3DS page taking too much time to load or not found , stopping Add money\n"+e.getMessage());
			}			
			verify3DS();		
		}
		i=0;
	}
	
	/**
	 * Executes  Mobile recharge by entering the 3DS pin and entering the cvv if it occurs.
	 *
	 * @param cardDetails the card details
	 */
	public String  executeRecharge(String cardDetails)
	{
		
		// ==== //
		if (true) return executeRechargeGeneric(cardDetails);
		// ==== //
		
		if(!cardDetails.contains(":"))
		{
			Log.info("======== :: "+ cardDetails +" :: Card Details for 3DS transaction not found , executing ITP ========");
			return "";
		}
		
		String pin=cardDetails.split(":")[1],cvv=cardDetails.split(":")[2];
		
		if(!Generic.checkWalletITPDirect(cardDetails))
		{
			verifyMobileRecharge3DS();		
			submitMobileRecharge3DS(pin);
		}
		enterCvv(cvv);
		return "";
		
	}
	
	public String executeRechargeGeneric(String cardDetails)
	{
		String flow="",pin,cvv="123";// Default	
		
		
		// ==== Handle 3DS flow ==== //
		verify3DS();
		String checker= Generic.getAttribute(pageCheck, "contentDescription")+pageCheck.getText();
		
		if(!checker.contains("XXXX")) 
			flow+="3DSNotFound"; //ITP flow without cvv
		else
		{
			pin=cardDetails.split(":")[1];
			cvv=cardDetails.split(":")[2];
			submitMobileRecharge3DS(pin);
			flow+="3DSFound";
		}		
		// ==== Handle optional CVV ==== //
				
		
		if(enterCvv(cvv))
			return flow+"CVVFound";
		else
			return flow+"CVVNotFound";		
	}
	
	
	
	
	
	
	/**
	 * Enters the 3DS pin and simulates the Submit button click.
	 *
	 * @param pin the pin
	 */
	public void submitMobileRecharge3DS(String pin)
	{				
		Log.info("======= Entering Secure Pin : "+pin+" ========");
		securePinTextField.sendKeys(pin);
		Generic.hideKeyBoard(driver);	
		
		Log.info("======== Clicking on Submit button ========");	
		click3DSsubmit();		
	}
	
	
	
	/**
	 * Verifies 3DS authentication fail error message.
	 */
	public void verifyAuthErrMsg()
	{
		
		String msg;
		
		try 
		{
			new WebDriverWait(driver, 45).until(ExpectedConditions.visibilityOf(authErrMsg));
			
			msg=Generic.isAndroid(driver)?Generic.getAttribute(authErrMsg, "contentDescription"):Generic.getAttribute(authErrMsg, "name");
			
			Log.info("======== Verifying message : "+msg+" ========");
			Assert.assertTrue(authErrMsg.isDisplayed(), "Authentication error message not found\n");
			
		} catch (Exception e) 
		{
			Assert.fail("Authentication error message not found\n"+e.getMessage());
		}		
	}
	
	/**
	 * Cancel 3DS transaction.
	 */
	public void cancel3DS()
	{
		verifyMobileRecharge3DS();
		Log.info("======== Clicking on Cancel button ========");
		
		cancelButton.click();
		cancelConfirmOKBtn.click();
		
		if(Generic.isIos(driver))
		{
			Log.info("======== Handling Payment not Processed alert ========");
			Generic.wait(5);
			cancelConfirmOKBtn.click();
		}
			
	}
	
	/**
	 * Enters cvv if the cvv prompt occurs.
	 *
	 * @param cvv the cvv
	 */
	public boolean enterCvv(String cvv)
	{
		j++;
		
		try 
		{
			new WebDriverWait(driver,40).until(ExpectedConditions.visibilityOf(cvvChk));			
		} 
		catch (Exception e)
		{
			Assert.fail("Page not found or taking too much time to load. Stopping execution\n");			
		}
		j=0;
		if(cvvChk.getText().contains("CVV"))
		{
			Log.info("======== Entering CVV : "+cvv+"========");
			cvvTextField.sendKeys(cvv);
			Generic.hideKeyBoard(driver);
			cvvSubmitButton.click();
			return true;
		}
		
		return false;
	}
	
	
}
