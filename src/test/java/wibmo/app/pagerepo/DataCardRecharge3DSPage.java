package wibmo.app.pagerepo;

import java.time.Duration;
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
 * The Class DataCardRecharge3DSPage checks and performs 3DS related operations during DataCard Recharge.
 */
public class DataCardRecharge3DSPage extends BasePage
{

	/** The driver. */
	private WebDriver driver;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'Order Id' or value contains 'XXXX' or value contains 'CVV'")
	@AndroidFindBy(xpath="//android.widget.EditText|//android.widget.Button")
	private WebElement pageChk; 
	
	/** The masked card number. */
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'XXXX'")
	@AndroidFindBy(xpath="//android.view.View[contains(@content-desc,'XXXX XXXX XXXX') or contains(@text,'XXXX')]")
	private WebElement maskedCardNumber;
	
	/** The secure pin text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(className="android.widget.EditText")
	private WebElement securePinTextField;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'Order Id' or value contains 'CVV'")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'CVV2/CVC2')] | //android.widget.Button[contains(@content-desc,'OK') or contains(@content-desc,'Ok') or contains(@text,'OK') or contains(@text,'Ok')]")
	private WebElement cvvChk;
	
	/** The cvv text field. */
	@FindBy(className="android.widget.EditText")
	private WebElement cvvTextField;
	
	/** The cvv submit button. */
	@FindBy(id="approve_textbutton")
	private WebElement cvvSubmitButton;
	
	/** The static i used to count recursions will be reset to 0 after recursion completes. */
	public static int i;
	
	/**
	 * Instantiates a new DataCardRecharge3DSPage.
	 *
	 * @param driver the driver
	 */
	public  DataCardRecharge3DSPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Executes  Datacard recharge by entering the 3DS pin and entering the cvv if it occurs.
	 * Can be used for both 3DS and ITP flows
	 *
	 * @param cardDetails the card details
	 */
	public void executeDataCardRechargeGeneric(String cardDetails)
	{
		if(!cardDetails.contains(":"))
		{
			Log.info("======== Card Details for 3DS transaction not found , trying to execute ITP ========");
			return;
		}		
		String pin=cardDetails.split(":")[1],cvv=cardDetails.split(":")[2];
		
		if(verifyDataCardRechargeGeneric()) // if 3DS then continue
		{
			submitDTHRecharge3DS(pin);
			enterCvv(cvv);
		}
				
	}
	
	/**
	 * Enters the 3DS pin and simulates the Submit button click. 
	 *
	 * @param pin the pin
	 */
	public void submitDTHRecharge3DS(String pin)
	{
		Log.info("======== Entering pin ========");
		securePinTextField.sendKeys(pin);
		Generic.hideKeyBoard(driver);
		click3DSsubmit();	
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
	 * Waits and Verifies the occurrence of 3DS page during Datacard Recharge. To be used for both 3DS & ITP flows.
	 * If 3DS page is not detected by autowebview capability it executes a normal tap to identify the 3DS page
	 */
	public boolean verifyDataCardRechargeGeneric()
	{
		i++;
		Log.info("======== Verifying 3DS Page ========");
		try 
		{			
			Generic.wait(5);  // Override previous button check
			//new WebDriverWait(driver,15).until(ExpectedConditions.presenceOfElementLocated(By.className("android.widget.EditText")));
			new WebDriverWait(driver,15).until(ExpectedConditions.visibilityOf(pageChk));
		} 
		catch (Exception e1) 
		{
			Generic.tap3DS(driver);
			if(i>=3)
			{
				i=0;
				Assert.fail("Page not Displayed \n" +e1.getMessage());
			}
			return verifyDataCardRechargeGeneric();				
		}		
		i=0;		
		//try 
		{
			Generic.wait(1);
			String flowChk=pageChk.getText()+Generic.getAttribute(pageChk,"contentDescription");
			
			if(Generic.containsIgnoreCase(flowChk, "Order")) // Check between 3DS & ITP flows
			{
				Log.info("== 3DS page not detected ==");
				return false;
			}
			
			Assert.assertTrue(maskedCardNumber.isDisplayed());
			Log.info("======== Masked card No. : "+	Generic.getAttribute(maskedCardNumber, "contentDescription") +maskedCardNumber.getText() +" ========"); 
			Log.info("======== Verifying Secure Pin text field ========");
			Assert.assertTrue(securePinTextField.isDisplayed());
		} 
		//catch (Exception e) 
		{
			//Assert.fail("3DS Page was not Displayed \n" +e.getMessage());
		}	
		return true;
	}
	
	/**
	 * Waits and Verifies the occurrence of 3DS page during DataCard Recharges.
	 * If 3DS page is not detected by autowebview capability it executes a normal tap to identify the 3DS page
	 * 
	 */
	public void verifyDataCardRecharge3DS()
	{
		i++;
		Log.info("======== Verifying 3DS Page ========");
		try 
		{
			new WebDriverWait(driver,20).until(ExpectedConditions.presenceOfElementLocated(By.className("android.widget.EditText")));
		} catch (Exception e1) 
		{
			Generic.tap3DS(driver);
			if(i>=3)
			{
				i=0;
				Assert.fail("3DS Page not Displayed \n" +e1.getMessage());
			}
			verifyDataCardRecharge3DS();			
			return;
		}		
		i=0;		
		try 
		{
			Assert.assertTrue(maskedCardNumber.isDisplayed());
			Log.info("======== Masked card No. : "+	maskedCardNumber.getAttribute("contentDescription")+" ========");
			Log.info("======== Verifying Secure Pin text field ========");
			Assert.assertTrue(securePinTextField.isDisplayed());
		} 
		catch (Exception e) 
		{
			Assert.fail("3DS Page not Displayed \n" +e.getMessage());
		}
		i=0;
	}
}
