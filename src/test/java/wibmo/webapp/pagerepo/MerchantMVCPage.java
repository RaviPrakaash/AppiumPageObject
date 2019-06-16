package wibmo.webapp.pagerepo;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import library.Generic;
import library.Log;

		
/**
* The Class MerchantMVCPage used to enter the MVC during registration
*/
public class MerchantMVCPage  {
	
	/** The driver. */
	WebDriver driver;
	
	/**
	 * Instantiates a new MerchantMVCPage.
	 *
	 * @param driver the driver
	 */
	public MerchantMVCPage(WebDriver driver) 
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	
	/** The MVC text field. */
	@FindBy(id="mbOtp")
	private WebElement mvcTextField;
	
	/** The resend MVC link. */
	@FindBy(id="otpLink")
	private WebElement resendMVCLink;
	
	/** The cancel button. */
	@FindBy(xpath="//button[@class='btn btn-default col-xs-4']")
	private WebElement cancelButton;
	
	/** The continue button. */
	@FindBy(id="registerButton")
	private WebElement continueButton;
	
		
	/**
	 * Enters the MVC into the textfield.
	 *
	 * @param str the str
	 */
	public void addMVCDetail(String mvc) 
	{
		Log.info("===Enter mvc number "+mvc+"===");
		mvcTextField.sendKeys(mvc);		
	}
	
	/**
	 * Clicks on continue button.
	 */
	public void clickoncontinue()
	{
		Log.info("====Clicking on Continue button====");
		continueButton.click();
	}
	
	/**
	 * Clicks on cancel button.
	 */
	public void clickoncancel() 
	{
		Log.info("====Cancelling Registration====");
		cancelButton.click();
		
		Log.info("====Clinking on cancel confirm====");
		Alert alert=driver.switchTo().alert();
		alert.accept();	
		
		Generic.checkAlert(driver);
	}
	
	/**
	 * Waits for page timeout.
	 *
	 * @param timeout the timeout in minutes.
	 */
	public void timeout(int timeout) 
	{
		Log.info("======== Waiting for Session timeout ========");
		for(int i=timeout*2;i>=1;i--)
		{
			Log.info("========  "+ i/2.0 +" minutes left until expected session timeout ========");			
			try { Thread.sleep(30000);} catch (InterruptedException e) {}
						
		}		
	}
}
