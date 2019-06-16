package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
//import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import library.Log;
import wibmo.webapp.pagerepo.Merchant3DSPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.offset.PointOption;
import library.Generic;

/**
 * The Class MerchantPayment3DSPage checks and performs 3DS related operations during Merchant Payment.
 */
public class MerchantPayment3DSPage extends BasePage
{	
	/** The driver. */
	private WebDriver driver;

	/** The card number displayed in Masked format. */
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'XXXX'")
	@AndroidFindBy(xpath="//android.view.View[contains(@content-desc,'XXXX XXXX XXXX') or contains(@text,'XXXX')]")
	private WebElement maskedCardNumber;

	/** The secure pin text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(className="android.widget.EditText") //android.widget.EditText|//*[contains(@resource-id),'Password']
	private WebElement securePinTextField;

	/** The submit button. */
	@FindBy(xpath="(//android.widget.Button)[2]")
	private WebElement submitButton;

	/** The cancel button. */
	@FindBy(xpath="//*[contains(@text,'Cancel') or contains(@content-desc,'Cancel')]")
	private WebElement cancelButton;

	/** The List of 3DS buttons. */
	@FindBy(className="android.widget.Button")
	private List<WebElement> buttons;

	/** The cancel confirmation alert OK btn. */
	@FindBy(xpath="//*[contains(@resource-id,'button1')]")
	private WebElement cancelConfirmOKBtn;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'CVV' or value contains 'ID: 201'")
	@AndroidFindBy(xpath="//*[contains(@text,'CVV2/CVC2') or contains(@content-desc,'CVV2/CVC2') or contains(@text,'Wibmo SDK') or contains(@resource-id,'title_text') or contains(@resource-id,'got_it')]")
	private WebElement cvvChk;

	/** The cvv text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(className="android.widget.EditText")
	private WebElement cvvTextField;

	/** The cvv submit button. */
	@iOSXCUITFindBy(accessibility="Approve")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'approve_textbutton') or contains(@content-desc,'CONTINUE') or contains(@text,'CONTINUE')]")	//App CVV button or WebOverlay CVV button		//(id="approve_textbutton")
	private WebElement cvvSubmitButton;

	/** The 3DS authentication error message. */
	@FindBy(xpath="//*[contains(@content-desc,'failed') or contains(@text,'failed')]") // Authentication failed
	private WebElement authErrMsg;
	
	@FindBy(xpath="//*[contains(@content-desc,'blocked') or contains(@text,'failed')]")
	private WebElement blockMsg;

	/** The page check mainly used to check between 3DS and ITP flow. */
	@iOSXCUITFindBy(iOSNsPredicate="name contains 'XXXX' or value contains 'ID: 201' or name contains 'CVV'") 
	@AndroidFindBy(xpath="//*[starts-with(@content-desc,'XXXX') or starts-with(@text,'XXXX') or contains(@content-desc,'More options') or contains(@text,'CVV')]") // either 3DS or merchant home page or CVV screen
	private WebElement pageCheck;

	/** The timeout check used to prevent Appium server timeout. */
	@FindBy(xpath="//android.view.View")
	private WebElement timeoutCheck;
	
	/** The submit check mainly used to check whether the submit button is clicked successfully. */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'title_text') or contains(@resource-id,'message') or contains(@text,'Authentication') or contains(@text,'CVV2/CVC2')]")
	private WebElement submitCheck;
	
	/** The popup cancel btn. */
	@FindBy(xpath="//*[@text='Cancel']") 
	private WebElement popupCancelBtn;	
	
	/** The progress bar displayed when 3DS page is loading. */
	@FindBy(xpath="//*[contains(@resource-id,'smoothprogressbar')]")
	private WebElement progressBar;	
	
	/** The i. */
	public static int i=0;
	
	/**
	 *	 Instantiates a new MerchantPayment3DSPage.
	 *
	 * @param driver the driver
	 */
	public  MerchantPayment3DSPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}

	/**
	 * Execute merchant payment by entering the 3DS pin and entering the cvv if it occurs.
	 * Can be upgraded to return String result of transaction flow 
	 *
	 * @param cardDetails the card details as Cardname:pin:cvv
	 * @return true if cvv screen occurs
	 * @see AddMoney3DSPage executeTransaction()
	 * 
	 */
	public boolean executeMerchantPayment(String cardDetails)
	{
		if(!cardDetails.contains(":"))
		{
			Log.info("======== :: "+ cardDetails +" :: Card Details for 3DS transaction not found , executing ITP ========");
			return false;
		}		

		String pin=cardDetails.split(":")[1],cvv=cardDetails.split(":")[2];
		
//		if(!Generic.checkWalletITPDirect(cardDetails))
//		{
			i=0;
			verifyMerchantPayment3DS();
			
			String checker=Generic.getAttribute(pageCheck, "contentDescription")+pageCheck.getText();
			
			if(!checker.contains("XXXX") )	// Iterator i and pagecheck are static 	
				Log.info("======== Transaction was executed without 3DS ========");				
			else
			{
				Log.info("======== Executing 3DS Transaction ========");
				submitPayment(pin);
			}
		//}
	return enterCvv(cvv);		
	}	
	
	/**
	 * Enters the 3DS pin and simulates the Submit button click.
	 *
	 * @param pin the pin
	 */
	public void submitPayment(String pin)
	{
		verifyMerchantPayment3DS();		
		
		pin=pin.split(":")[0];

		Log.info("======== Entering Secure Pin : "+pin+"========");
		securePinTextField.sendKeys(pin);
		Generic.hideKeyBoard(driver);

		Log.info("======== Clicking on Submit button ========");			
		click3DSsubmit();
		
		if(pin.contains(":"))
			enterCvv(pin.split(":")[1]);
	}
	
	
	
	/**
	 * Enters cvv if the cvv prompt occurs.
	 *
	 * @param cvv the cvv
	 * @return true if cvv prompt occurs
	 */
	public boolean enterCvv(String cvv)
	{
		
		try
		{		
			Generic.wait(4); // UiAuto2
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(cvvChk));			
		} 
		catch (Exception e)
		{
			Assert.fail("Page not found or taking too much time to load. Stopping execution\n");e.printStackTrace();
		}		
		
		String cvvCheck=cvvChk.getText()+Generic.getAttribute(cvvChk, "contentDescription");
		
		Log.info("======== Verifying optional occurence of CVV prompt ========");
		if(Generic.containsIgnoreCase(cvvCheck, "CVV"))			
		{
			if(!cvv.isEmpty())
			{
				Log.info("======== Entering CVV : "+cvv+"========");
				cvvTextField.sendKeys(cvv);
				Generic.hideKeyBoard(driver);
			}
			
			Log.info("======== Clicking on CVV continue submit ========");
			cvvSubmitButton.click();
			return true;
		}	
		return false;
	}	
	
	/**
	 * Verifies the occurrence of CVV Screen 
	 * 
	 * @param cvvStatusFound
	 * @param cvvStatusExpected
	 */
	public void verifyCVVOccurence(boolean cvvStatusFound,boolean cvvStatusExpected) // Use for checking mandatory occurrence  on absence of CVV screen
	{
		Log.info("==== Verifying the "+(cvvStatusExpected?"":"non ")+"occurence of CVV screen ====");
		
		if(cvvStatusExpected)	//cvvStatusFound==cvvStatusExpected		
			Assert.assertTrue(cvvStatusFound, "CVV Screen not found\n"); 
		else
			Assert.assertFalse(cvvStatusFound, "CVV Screen occured\n");
	}
	
	/**
	 * Waits and Verifies the mandatory occurrence of CVV prompt.
	 */
	public void verifyCVV()
	{
		String xp="//*[contains(@text,'CVV2/CVC2') or contains(@content-desc,'CVV2/CVC2') or contains(@text,'Wibmo SDK') or contains(@content-desc,'remind')]"; // either CVV-(3DS or WebOverlay) or merchant home page status message or guest payment screen
		try 
		{
			new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));			
		} 
		catch (Exception e)
		{
			Assert.fail("CVV screen not found\n");			
		}
		
		Log.info("======== Verifying for mandatory occurence of CVV prompt ========");
		String cvvCheck=driver.findElement(By.xpath(xp)).getText()+driver.findElement(By.xpath(xp)).getAttribute("contentDescription");
		if(!Generic.containsIgnoreCase(cvvCheck, "CVV"))
			Assert.fail("CVV screen was not displayed\n");			
	}
	
	/**
	 *  Waits and Verifies the occurrence of 3DS page during Merchant Payment.
	 *  If 3DS page is not detected by autowebview capability it executes a normal tap to identify the 3DS page
	 *
	 */
	public void verifyMerchantPayment3DS()
	{
		i++;		
		Log.info("======== Verifying 3DS Page ========");			
		
		try 
		{
			new WebDriverWait(driver,20).until(ExpectedConditions.visibilityOf(pageCheck));
		} 
		catch (Exception e) 
		{
			Generic.tap3DS(driver);
			if(i>=3)
			{
				i=0;
				Assert.fail("3DS Page taking too much time to load \n " +e.getMessage());
			}
			verifyMerchantPayment3DS();
			return;
		}	
		String checker=Generic.getAttribute(pageCheck, "contentDescription")+pageCheck.getText();
		
		if(!checker.contains("XXXX"))
		{
			Log.info("==== 3DS page did not occur ====");
			i=0;
			return;
		}
		/*//--------------------------
		if(i==1) 
		{
			Log.info("======== Focussing on Textfield ========"); // Device Compatibility 
			Generic.tap3DS(driver);
		}
		//-------------------------
*/		i=0;
		try 
		{
			Assert.assertTrue(maskedCardNumber.isDisplayed());
			Log.info("======== Masked card No. : "+	Generic.getAttribute(maskedCardNumber, "contentDescription")+maskedCardNumber.getText()+" ========"); 
			Log.info("======== Verifying Secure Pin text field ========");
			Assert.assertTrue(securePinTextField.isDisplayed());
		} 
		catch (Exception e) 
		{
			Assert.fail("3DS Page not Displayed \n" +e.getMessage());e.printStackTrace();
		}
	}
	
	public void verifyITP()
	{
		String itpChecker=Generic.getAttribute(pageCheck, "contentDescription")+pageCheck.getText();	
		
		if(itpChecker.contains("XXXX")) 				// 3DS Page
			Assert.fail("ITP was not executed");
	}

	/**
	 * Verify 3DS authentication failure message.
	 */
	public void verifyFailureMsg()
	{
		Generic.wait(3);
		Generic.tap3DS(driver);		
		
		try 
		{
			new WebDriverWait(driver, 45).until(ExpectedConditions.visibilityOf(authErrMsg));
			Log.info("======== Verifying Authentication Error Message : "+authErrMsg.getAttribute("contentDescription")+"  ========");
			Assert.assertTrue(authErrMsg.isDisplayed());
		} catch (Exception e) 
		{
			Assert.fail("3DS Authentication Error Message not found"+e.getMessage());
		}
	}
	
	public void verifyCardBlockMessage()
	{
		Generic.wait(5); // wait for msg
		
		AppiumDriver adriver=(AppiumDriver)driver;
		
		int x=(int)(adriver.manage().window().getSize().getWidth()/2);
		int y=(int)(adriver.manage().window().getSize().getHeight()/2);
		
		System.out.println("Tap on x,y : "+x+','+y);
		new TouchAction(adriver).press(PointOption.point(x, y)).release().perform();

		
		try
		{
			new WebDriverWait(driver, 35).until(ExpectedConditions.visibilityOf(blockMsg));
			Log.info("======== Verifying message : "+blockMsg.getAttribute("contentDescription")+blockMsg.getText()+" ========");
		}
		catch(Exception e)
		{
			Assert.fail("Error message not found\n"+e.getMessage());
		}		
		
	}


	/**
	 * Wait for session timeout.
	 *
	 * @param timeout the timeout
	 */
	public void waitForSessionTimeout(int timeout) 
	{
		Log.info("======== Waiting for Session timeout ========");
		for(int i=timeout*2;i>=1;i--)
		{
			Log.info("========  "+ i/2.0 +" minutes left until expected session timeout ========");			
			try { Thread.sleep(30000);} catch (InterruptedException e) {}
			timeoutCheck.getText(); // To prevent Appium timeout			
		}		
	}
	
	/**
	 * Cancels 3DS transaction.
	 */
	public void cancel3DS()
	{
		verifyMerchantPayment3DS();
		Log.info("======== Clicking on Cancel button ========");
		for(WebElement b:buttons)
			if(b.getAttribute("contentDescription").toLowerCase().contains("cancel")) 
			{
				b.click();break;
			}		
		cancelConfirmOKBtn.click();
	}
}
