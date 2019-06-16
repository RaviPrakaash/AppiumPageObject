package wibmo.webapp.pagerepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.libraries.Log;

import library.Generic;

	// TODO: Auto-generated Javadoc
//save for faster checkout button page with successful payment message (registration step 1)

/**
* The Class MerchantTransactionFinalPage used to verify a successful transaction and optionally proceed for full Registration.(Registration Step 1)
*/
	public class MerchantTransactionFinalPage {

	/** The driver. */
	WebDriver driver;
	
	/**
	 * Instantiates a new MerchantTransactionFinalPage
	 *
	 * @param driver the driver
	 */
	public MerchantTransactionFinalPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	/** The save for faster checkout button. */
	@FindBy(id="registerContinueBtn")
	private WebElement saveForFasterCheckoutButton;
	
	/** The remind me later link. */
	@FindBy(id="registerSkipBtn")
	private WebElement remindMeLaterLink;
	
	/** The payment successful msg. */
	@FindBy(xpath="//h3[contains(text(),'Payment successful')]")
	private WebElement paymentSuccessfulMsg;
	
	/**
	 * Clicks on Faster Checkout button.
	 */
	public void forFasterCheckout()   
	{
		
		Generic.wait(3);
		Log.info("=== Clicking on Faster Checkout ===");
		saveForFasterCheckoutButton.click();
	}
	
	/**
	 * Clicks on Remind Me later link skipping registration.
	 */
	public void remindMeLater()			
	{
		
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception e)
		{
			e.getMessage();
		}
		remindMeLaterLink.click();
	}
	
	/**
	 * Verifies for a successful transaction.
	 */
	public void verifyPayment()		  
	{
		try
		{	
			Log.info("==== Verifying for transaction success ====");
			Assert.assertTrue(paymentSuccessfulMsg.isDisplayed(), "Payment successful message was not displayed");
		}
		catch(Exception e)
		{
			Assert.fail("Page is not displayed or taking too much time to load"+e.getMessage());
		}
	}
}
