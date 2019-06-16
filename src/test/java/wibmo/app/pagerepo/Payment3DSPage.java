package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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

/**
 * The Class Payment3DSPage primarily designed to consolidate and replace all 3DS pages i.e AddMoney,Mobile,DTH,DataCard and Merchant 3DS pages.
 * Implementation pending.
 */
public class Payment3DSPage 
{

/** The driver. */
private WebDriver driver;
	
	/** The card number displayedin masked format. */
	@FindBy(xpath="//android.view.View[contains(@content-desc,'XXXX XXXX XXXX')]")
	private WebElement maskedCardNumber;
	
	/** The secure pin text field. */
	@FindBy(className="android.widget.EditText")
	private WebElement securePinTextField;
	
	/** The submit button. */
	@FindBy(xpath="(//android.widget.Button)[2]")
	private WebElement submitButton;
	
	/** The cancel button. */
	@FindBy(xpath="//android.widget.Button[1]")
	private WebElement cancelButton;
	
	/** The List of all 3DS buttons. */
	@FindBy(className="android.widget.Button")
	private List<WebElement> buttons3DS;
	
	/** The cancel confirmation alert OK button. */
	@FindBy(xpath="//*[contains(@resource-id,'button1')]")
	private WebElement cancelConfirmOKBtn;
	
	/** The cvv text field. */
	@FindBy(className="android.widget.EditText")
	private WebElement cvvTextField;
	
	/** The cvv submit button. */
	@FindBy(id="approve_textbutton")
	private WebElement cvvSubmitButton;
	
	/** The 3DS authentication fail error message. */
	@FindBy(xpath="//android.view.View[contains(@content-desc,'Authentication failed')]")
	private WebElement authErrMsg;
	
	/** The page check used to check between 3DS and ITP flow. */
	@FindBy(xpath="//android.view.View[contains(@content-desc,'XXXX')] | //android.widget.TextView[contains(@resource-id,'status_text')]") // either 3DS or merchant home page
	private WebElement pageCheck;
	
	/**
	 * Instantiates a new Payment3DSPage.
	 *
	 * @param driver the driver
	 */
	public  Payment3DSPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Executes Payment by entering the 3DS pin and entering the cvv if it occurs.
	 *
	 * @param cardDetails the card details
	 */
	public void executeMerchantPayment(String cardDetails)
	{		
		if(!cardDetails.contains(":"))
		{
			Log.info("======== :: "+ cardDetails +" :: Card Details for 3DS transaction not found , executing ITP ========");
			return;
		}	
		
		String pin=cardDetails.split(":")[1],cvv=cardDetails.split(":")[2];
		
		try
		{
			new WebDriverWait(driver,45).until(ExpectedConditions.visibilityOf(pageCheck));
		}
		catch(TimeoutException e)
		{
			Assert.fail("Page taking too much time to load or Page not found, stopping execution \n"+e.getMessage());
		}
		if(pageCheck.getAttribute("resourceId").contains("status_text") )
		{
			Log.info("======== Transaction was executed without 3DS ========");
			return;
		}
		
		Log.info("======== Executing 3DS Transaction ========");
		
		submitPayment(pin);
		enterCvv(cvv);
		
		
	}	
	
	/**
	 * Enters the 3DS pin and simulates the Submit button click.
	 *
	 * @param pin the pin
	 */
	public void submitPayment(String pin)
	{
		Log.info("======= Entering Secure Pin : "+pin+" ========");
		securePinTextField.sendKeys(pin);
		Generic.hideKeyBoard(driver);
		
		((AndroidDriver)driver).pressKeyCode(66); // KEYCODE_ENTER
	}
	
	/**
	 * Enters cvv if the cvv prompt occurs.
	 *
	 * @param cvv the cvv
	 */
	public void enterCvv(String cvv)
	{
		String xp="//android.widget.TextView[contains(@text,'CVV2/CVC2') or contains(@resource-id,'status_text')]"; // either cvv or merchant home page status message
				  
		try 
		{
			new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));			
		} 
		catch (Exception e)
		{
			Assert.fail("Page not found or taking too much time to load. Stopping execution\n");
		}
		
		if(driver.findElement(By.xpath(xp)).getText().contains("CVV"))
		{
			Log.info("======== Entering CVV : "+cvv+"========");
			cvvTextField.sendKeys(cvv);
			Generic.hideKeyBoard(driver);
			cvvSubmitButton.click();
		}			
	}	
}
