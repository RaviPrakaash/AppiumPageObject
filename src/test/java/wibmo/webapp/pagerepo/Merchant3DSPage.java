package wibmo.webapp.pagerepo;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import library.Log;


/**
 * The Class Merchant3DSPage used to enter the 3DS pin for a transaction.
 */
//Visa verification page (3D Secure page)
public class Merchant3DSPage {
	
	/** The driver. */
	WebDriver driver;
	
	/**
	 * Instantiates a new Merchant3DSPage.
	 *
	 * @param driver the driver
	 */
	public Merchant3DSPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	/** The secure pin text field. */
	@FindBy(id="txtPasswordtoDisplay")
	private WebElement securePinTextField;
	
	/** The submit button. */
	@FindBy(id="cmdSubmit")
	private WebElement submitButton;
	
	/** The cancel button. */
	@FindBy(id="cmdCancel")
	private WebElement cancelButton;
	
	/** The authentication failed message. */
	@FindBy(xpath="//font[contains(text(),'Authentication failed')]")
	private WebElement authenticationFailedMsg;	
	
	/** The password text field. */
	@FindBy(xpath="//input[@type='password']")
	private WebElement passwordTextField;
	
	@FindBy(xpath="//table|//label[@id='CVV2Title']|//a[@id='registerSkipBtn']") // Result Table | CVV Screen | Payment Successful Screen for Guest
	private WebElement cvvChecker;
	
	@FindBy(id="CVV2Display")
	private WebElement cvvTextField;
	
	@FindBy(id="additionalInputButton")
	private WebElement cvvContinueButton;
	
	/**
	 * Enters 3DS pin and clicks on submit.
	 *
	 * @param pin
	 */
	public boolean submitVisaVerification(String pin)   //Enter the secure pin and click on submit button
	{		
		String cvv=pin.contains(":")?pin.split(":")[1]:"";
		pin=pin.split(":")[0];
		
		waitFor3DS();
		
		Log.info("==== Entering Secure Pin ====");		
		passwordTextField.sendKeys(pin);
		Log.info("==== Clicking on Submit button ====");
		submitButton.click();		
		return enterCVV(cvv);
	}
	
	/**
	 * Enters CVV if the CVV screen occurs.
	 *
	 * @param cvv
	 * @return true if cvv screen occurs
	 */
	public boolean enterCVV(String cvv)
	{	
		
		Log.info("==== Checking for optional occurence of CVV screen ====");
		try
		{
			new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(cvvChecker));
		}
		catch(Exception e)
		{
			Assert.fail("Page taking too much time to load\n"+e.getMessage());
		}
		if(cvvChecker.getText().contains("CVV2"))
		{
			if(!cvv.isEmpty())
			{
				Log.info("==== Entering  CVV : "+cvv+" ====");
				cvvTextField.sendKeys(cvv);
			}
			Log.info("==== Clicking on CVV continue button ====");
			cvvContinueButton.click();
			return true;
		}
		return false;		
	}
	
	public void waitFor3DS()
	{
		//pageCheck="//input[@type='password']|//table|//label[@id='CVV2Title']|//a[@id='registerSkipBtn']"
		// if(pageCheck.getAttribute("type").contains("password"))
		try
		{
			new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(passwordTextField));
		}
		catch(Exception e)
		{
			Assert.fail("Page taking too much time to load\n"+e.getMessage());
		}
	}
	
	/**
	 * Cancels 3DS verification.
	 *
	 * @param data the data
	 */
	public void cancelVisaVerification()	//Enter the secure pin and click on cancel button
	{
		waitFor3DS();
		//String[] str = data.split(",");
		Log.info("===Cancelling 3DS===");
		//securePinTextField.sendKeys(data);
		cancelButton.click();
		driver.switchTo().alert().accept();
	}
	
	/**
	 * Verifies the Authentication failed error message.
	 */
	public void verifyAuthenticationFailed()
	{		
		try
		{
			String msg=authenticationFailedMsg.getText();
			Log.info("===Verifying Authentication failed message : "+msg+" ===");
			Assert.assertTrue( authenticationFailedMsg.isDisplayed()," Authentication message not displayed ");
		}
		catch(Exception e)
		{
			Assert.fail("Page is not displayed or taking too much time to load"+e.getMessage());
		}
	}	
	
	public void verifyCVVOccurence(boolean cvvStatusFound,boolean cvvStatusExpected) // Use for checking mandatory occurrence  on absence of CVV screen
	{
		Log.info("==== Verifying the "+(cvvStatusExpected?"":"non ")+"occurence of CVV screen ====");
		
		if(cvvStatusExpected)	//cvvStatusFound==cvvStatusExpected		
			Assert.assertTrue(cvvStatusFound, "CVV Screen not found\n"); 
		else
			Assert.assertFalse(cvvStatusFound, "CVV Screen occured\n");
	}
}
