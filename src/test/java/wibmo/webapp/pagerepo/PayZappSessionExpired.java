package wibmo.webapp.pagerepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

// TODO: Auto-generated Javadoc
/**
 * The Class PayZappSessionExpired.
 */
public class PayZappSessionExpired {

	/** The driver. */
	public WebDriver driver;
	
	/**
	 * Instantiates a new pay zapp session expired.
	 *
	 * @param driver the driver
	 */
	public PayZappSessionExpired(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	/** The sessionexpiredalert. */
	@FindBy(xpath="//div[contains(text(),'Session expired, please try again')]")
	private WebElement sessionexpiredalert;
	
	/** The fullname textfield. */
	@FindBy(id="fullName")
	private WebElement fullnameTextfield;
	
	/** The cardnumber textfield. */
	@FindBy(id="cardNumber")
	private WebElement cardnumberTextfield;
	
	/** The year textfield. */
	@FindBy(id="expYYYY")
	private WebElement yearTextfield;
	
	/** The month textfield. */
	@FindBy(id="expMM")
	private WebElement monthTextfield;
	
	/** The cancel button. */
	@FindBy(xpath="//button[contains(text(),'Cancel')]")
	private WebElement cancelButton;
	
	/** The continue button. */
	@FindBy(id="registerButton")
	private WebElement continueButton;
	
	/**
	 * Verifies Session expire
	 */
	public void checksessionexpired()
	{
		try
		{
			Assert.assertTrue(sessionexpiredalert.isDisplayed(), "Session alert message not displayed correctly");
		}
		catch(Exception e)
		{
			Assert.fail("Session Alert message not displayed correctly\n"+e.getMessage());
		}		
	}
	

}
