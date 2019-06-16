package wibmo.webapp.pagerepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.libraries.Log;

/**
 * The Class MerchantRegistrationFinalPage used to check the final Registration status after the transaction.
 */
public class MerchantRegistrationFinalPage {

	/** The driver. */
	public WebDriver driver;
	
	/**
	 * Instantiates a new MerchantRegistrationFinalPage
	 *
	 * @param driver the driver
	 */
	public MerchantRegistrationFinalPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	/** The Registered Status text. */
	@FindBy(xpath="//div[@class='container']//h3[contains(text(),'Already registered')]")
	private WebElement registeredstatustext;
	
	/** The Done button. */
	@FindBy(xpath="//a[@class='btn btn-primary btn-lg ladda-button']")
	private WebElement doneButton;
	
	/**
	 * Checks the final registration status and clicks on Done button.
	 */
	public void checkregistraionstatus()
	{		
		Log.info("====Registration Status====");
		Assert.assertEquals(true,registeredstatustext.isDisplayed());
		Log.info("====Clicking on Done button ====");
		doneButton.click();
	}
	
}

