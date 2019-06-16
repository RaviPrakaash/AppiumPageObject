package wibmo.webapp.pagerepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import library.Log;

/**
* The Class MerchantRegistrationPage is the Registration Step 2 page used to enter additional merchant details.
*/

public class MerchantRegistrationPage{
	
	/** The driver. */
	WebDriver driver;
	
	/**
	 * Instantiates a new MerchantRegistrationPage
	 *
	 * @param driver the driver
	 */
	public MerchantRegistrationPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	/** The email text field. */
	@FindBy(id="emailID")
	private WebElement emailTextField;
	
	/** The mobile number text field. */
	@FindBy(id="mobile")
	private WebElement mobileNumTextField;
	
	/** The DateOfBirth text field. */
	@FindBy(id="dob")
	private WebElement dobTextField;
	
	/** The secure pin text field. */
	@FindBy(id="passwordDisplay")
	private WebElement securePinTextField;
	
	/** The continue button. */
	@FindBy(id="registerButton")
	private WebElement continueButton;
	
	/** The cancel button. */
	@FindBy(xpath="//button[@class='btn btn-default col-xs-4']")
	private WebElement cancelButton;
	
	
	/**
	 * Cancels Registration.
	 */
	public void cancelReg()    
	{
		Log.info("====Clicking on Cancel button ====");
		cancelButton.click();
	}
	
	/**
	 * Enters additional Registration details like DateOfBirth,SecurePin and clicks on Continue button
	 *
	 * @param data the data
	 */
	public void addRegistrationDetails(String data)
	{
		Log.info("==== Additional Registraion Details DOB and Secure Pin====");
		String str[]=data.split(",");
		Log.info("==== Entering DOB :"+str[8]+"====");
		dobTextField.sendKeys(str[8]);
		Log.info("==== Entering Secure Pin ====");
		securePinTextField.sendKeys(str[9]);
		Log.info("==== Clicking on Continue button ====");
		continueButton.click();
	}
	
	/**
	 * Overrides the previously entered mobile number with an already registered mobile number
	 *
	 * @param regnum the registeredNumber
	 */
	public void changetoregisterednumber(String regnum)
	{
		mobileNumTextField.click();
		mobileNumTextField.clear();
		Log.info("====registered mobile number"+regnum+"====");
		mobileNumTextField.sendKeys(regnum);
	}
	
	/**
	 * Checks email and mobile text fields for prepopulated values.
	 *
	 * @param data the data
	 */
	public void checkprepopulated(String data)
	{
		String str[]=data.split(",");
		
		Log.info("==== Checking prepopulated field data====");
		Assert.assertTrue(emailTextField.getAttribute("value").equals(str[2]));		
		Assert.assertTrue(mobileNumTextField.getAttribute("value").equals(str[1]));	
		
	}
}
