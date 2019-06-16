package wibmo.webapp.pagerepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import library.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class PostPaymentScreenPage used to verify successful payment
 */
public class PostPaymentScreenPage {

	/** The driver. */
	public WebDriver driver;
	
	/**
	 * Instantiates a new PostPaymentScreenPage
	 *
	 * @param driver the driver
	 */
	public PostPaymentScreenPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	/** The payment success message. */
	@FindBy(xpath="//tbody//tr//td[contains(text(),'Res Desc : ')]/..//td[contains(text(),'SUCCESS')]")
	private WebElement postsuccessmessage;
	
	/**
	 * Verifies payment success.
	 */
	public void verifypostsuccess()
	{			
		try
		{
			Log.info("====Verifying success message : "+postsuccessmessage.getText()+"====");
			Assert.assertTrue(postsuccessmessage.isDisplayed(),"Success message not found");
		}
		catch(Exception e)
		{
			Assert.fail("Success message not shown"+e.getMessage());
		}				
	}
	
}
