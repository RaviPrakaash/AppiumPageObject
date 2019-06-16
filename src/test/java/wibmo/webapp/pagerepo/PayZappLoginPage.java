package wibmo.webapp.pagerepo;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.libraries.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class PayZappLoginPage displayed for a registered user to login.
 */
public class PayZappLoginPage {

	/** The driver. */
	public WebDriver driver;
	
	/**
	 * Instantiates a new PayZappLoginPage
	 *
	 * @param driver the driver
	 */
	public PayZappLoginPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	/** The login textbox. */
	@FindBy(id="unDisplay")
	private WebElement loginTextbox;
	
	/** The secure pin textbox. */
	@FindBy(id="passwordDisplay")
	private WebElement securepinTextbox;
	
	/** The login button. */
	@FindBy(id="loginButton")
	private WebElement loginButton;
	
	/** The cancel button. */
	@FindBy(xpath="//button[contains(text(),'Cancel')]")
	private WebElement cancelButton;
	
	/** The remember ID checkbox. */
	@FindBy(xpath="//input[@type='checkbox']")
	private WebElement remembercheckbox;
	
	/**
	 * Enters secure pin and clicks on Login button.
	 *
	 * @param securepin the securepin
	 */
	public void addlogindetails(String securepin)
	{
		Log.info("====Entering Secure pin=====");
		securepinTextbox.sendKeys(securepin);
				
		remembercheckbox.click();
		
		Log.info("====Clicking on Login button====");
		loginButton.click();
	}
	
	/**
	 * Cancels Login.
	 */
	public void cancellogin()
	{
		Log.info("==== Clicking on cancel button ====");
		cancelButton.click();
		driver.switchTo().alert().accept();	// Handle confirmation alert	
	}
}
