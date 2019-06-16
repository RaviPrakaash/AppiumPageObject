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
import library.Log;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;

/**
 * The Class DTHRecharge3DSPage checks and performs 3DS related operations during DTH Recharge.
 */
public class DTHRecharge3DSPage extends BasePage
{

	/** The driver. */
	private WebDriver driver;
	
	/** The pageChk used to check between 3DS page / CVV / TransactionFinal Page	*/
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'Order Id' or value beginswith 'XXXX' or value contains 'CVV'")
	@AndroidFindBy(xpath="//*[starts-with(@content-desc,'XXXX') or starts-with(@text,'XXXX') or contains(@resource-id,'status_btnOkay') or contains(@text,'CVV')]")  // status_btnOkay same as rc_status_btnOkay
	private WebElement pageChk; 
	
	/** The masked card number. */
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'XXXX'")
	@AndroidFindBy(xpath="//android.view.View[contains(@content-desc,'XXXX XXXX XXXX') or contains(@text,'XXXX')]")
	private WebElement maskedCardNumber;
	
	/** The secure pin text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(className="android.widget.EditText")
	private WebElement securePinTextField;
	
	/** The submit button. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'Submit')]")
	private WebElement submitButton;
	
	/** The buttons 3DS. */
	@FindBy(className="android.widget.Button")
	private List<WebElement> buttons3DS;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'Order Id' or value contains 'CVV'")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'CVV2/CVC2')] | //android.widget.Button[contains(@content-desc,'OK') or contains(@content-desc,'Ok') or contains(@text,'OK') or contains(@text,'Ok')]")
	private WebElement cvvChk;
	
	/** The cvv text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(className="android.widget.EditText")
	private WebElement cvvTextField;
	
	/** The cvv submit button. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and name contains 'CVV'")
	@AndroidFindBy(id="approve_textbutton")
	private WebElement cvvSubmitButton;
	
	/** The auth fail msg. */
	@FindBy(xpath="//*[contains(@content-desc,'failed') or contains(@text,'failed')]") // Authentication failed
	private WebElement authFailMsg;
	
	/** The cancel button. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'Cancel')]")
	private WebElement cancelButton;
	
	/** The ok confirm button. */
	@FindBy(xpath="//*[contains(@resource-id,'button1')]")
	private WebElement okConfirmButton;
	
	/** The static i used to count recursions and will be reset to 0 after recursion completes. */
	public static int i;
	
	/**
	 * Instantiates a new DTHRecharge3DSPage.
	 *
	 * @param driver the driver
	 */
	public  DTHRecharge3DSPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);	}
	
	/**
	 * Executes  DTH recharge by entering the 3DS pin and entering the cvv if it occurs.
	 * Can be used for both 3DS and ITP flows
	 *
	 * @param cardDetails the card details
	 */
	public void executeDTHRechargeGeneric(String cardDetails)
	{
		if(!cardDetails.contains(":"))
		{
			Log.info("======== Card Details for 3DS transaction not found , trying to execute ITP ========");
			return;
		}		
		String pin=cardDetails.split(":")[1],cvv=cardDetails.split(":")[2];
		
		if(verifyDTHRechargeGeneric()) // if 3DS then continue
		{
			submitDTHRecharge3DS(pin);
			enterCvv(cvv);
		}				
	}
	
	/**
	 * Waits and Verifies the occurrence of 3DS page during DTH Recharge. To be used for both 3DS & ITP flows.
	 * If 3DS page is not detected by autowebview capability it executes a normal tap to identify the 3DS page
	 */
	public boolean verifyDTHRechargeGeneric()
	{
		i++;

		Log.info("======== Verifying 3DS Page ========");
		try 
		{			
			Generic.wait(5);  // Override previous button check
			//new WebDriverWait(driver,15).until(ExpectedConditions.presenceOfElementLocated(By.className("android.widget.EditText")));
			new WebDriverWait(driver,15).until(ExpectedConditions.visibilityOf(pageChk));
			System.out.println("pageChk "+pageChk.getText());
		} 
		catch (Exception e1) 
		{
			Generic.tap3DS(driver);
			if(i>=3)
			{
				i=0;
				Assert.fail("Page not Displayed \n" +e1.getMessage());
			}
			return verifyDTHRechargeGeneric();			
			
		}		
		i=0;		
		try 
		{
			String flowChk=pageChk.getText()+Generic.getAttribute(pageChk,"contentDescription");
			
			System.out.println("flowChk : "+flowChk);
			
			if(!Generic.containsIgnoreCase(flowChk, "XXXX")) // Check between 3DS & ITP flows
			{
				Log.info("== 3DS page not detected ==");
				return false;
			}
			
			Assert.assertTrue(maskedCardNumber.isDisplayed());
			Log.info("======== Masked card No. : "+	Generic.getAttribute(maskedCardNumber, "contentDescription")+maskedCardNumber.getText()+" ========"); 
			Log.info("======== Verifying Secure Pin text field ========");
			Assert.assertTrue(securePinTextField.isDisplayed());
		} 
		catch (Exception e) 
		{
			Assert.fail("3DS Page was not Displayed \n" +e.getMessage());
		}	
		return true;
	}
	
	
	
	/**
	 * Waits and Verifies the occurrence of 3DS page during DTH Recharge.
	 * If 3DS page is not detected by autowebview capability it executes a normal tap to identify the 3DS page
	 */
	public void verifyDTHRecharge3DS()
	{
		i++;
		Log.info("======== Verifying 3DS Page ========");
		try 
		{
			new WebDriverWait(driver,25).until(ExpectedConditions.visibilityOf(pageChk));
			
		} catch (Exception e1) 
		{
			Generic.tap3DS(driver); // Obsolete logic not need with UiAuto2
			if(i>=3)
			{
				i=0;
				Assert.fail("3DS Page not Displayed \n" +e1.getMessage());
			}
			verifyDTHRecharge3DS();			
			return;
		}		
		i=0;		
		
//		try  // Masked Card / 3DS page occurence depends on Card used / Wallet card flag for 3DS 
//		{
//			Assert.assertTrue(maskedCardNumber.isDisplayed());
//			Log.info("======== Masked card No. : "+	maskedCardNumber.getAttribute("contentDescription")+" ========");
//			Log.info("======== Verifying Secure Pin text field ========");
//			Assert.assertTrue(securePinTextField.isDisplayed());
//		} 
//		catch (Exception e) 
//		{
//			Assert.fail("3DS Page not Displayed \n" +e.getMessage());
//		}		

		
	}
	
	/**
	 * Enters the 3DS pin and simulates the Submit button click. 
	 *
	 * @param pin the pin
	 */
	public void submitDTHRecharge3DS(String pin)
	{
		if(!Generic.checkTextInPageSource(driver, "XXXX").contains("XXXX")) return;
		
		Log.info("======== Entering 3DS pin ========");
		securePinTextField.sendKeys(pin);
		Generic.hideKeyBoard(driver);
		click3DSsubmit();	
	}
	
	/**
	 * Cancels DTH Recharge 3DS.
	 */
	public void cancelDTHRecharge3DS()
	{
		Log.info("======== Clicking on Cancel Button ========");
		for(WebElement b:buttons3DS)
			if(b.getAttribute("contentDescription").toLowerCase().contains("cancel"))
			{
				b.click();break;
			}			
		Log.info("======== Clicking on Cancel OK Button ========");
		okConfirmButton.click();
	}
	
	/**
	 * Executes  DTH recharge by entering the 3DS pin and entering the cvv if it occurs.
	 *
	 * @param cardDetails the card details
	 */
	public void executeDTHRecharge(String cardDetails)
	{
		if(!cardDetails.contains(":"))
		{
			Log.info("======== Card Details for 3DS transaction not found , trying to execute ITP ========");
			return;
		}		
		String pin=cardDetails.split(":")[1],cvv=cardDetails.split(":")[2];
	
		if(!Generic.checkWalletITPDirect(cardDetails))
		{
			verifyDTHRecharge3DS();
			submitDTHRecharge3DS(pin);
		}
		enterCvv(cvv);		
	}

	/**
	 * Enters cvv if the cvv prompt occurs.
	 *
	 * @param cvv the cvv
	 */
	public void enterCvv(String cvv)
	{
		try 
		{
			new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(cvvChk));
		} 
		catch (Exception e)
		{
			Assert.fail("Page not found or taking too much time to load. Stopping execution\n");
		}
		
		if(cvvChk.getText().contains("CVV"))
		{
			Log.info("======== Entering CVV : "+cvv+" ========");			
			cvvTextField.sendKeys(cvv);
			Generic.hideKeyBoard(driver);			
			Log.info("======== Clicking on CVV Submit ========");
			cvvSubmitButton.click();	
		}
	}
	
	/**
	 * Verifies 3DS authentication fail error message.
	 */
	public void verifyAuthenticationErrMsg()
	{
		Generic.wait(5);
		//Generic.tap3DS(driver);
		try 
		{
			new WebDriverWait(driver, 45).until(ExpectedConditions.visibilityOf(authFailMsg));
			Log.info("======== Verifying message : "+authFailMsg.getAttribute("contentDescription")+" ========");
			Assert.assertTrue(authFailMsg.isDisplayed());
		} 
		catch (Exception e) 
		{
			Assert.fail("Authentication Error message not found \n"+e.getMessage());
		}
		
	}
}
