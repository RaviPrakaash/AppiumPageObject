package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * The Class RegistrationSuccessfulPage is the page displayed after a successful registration.
 */
public class RegistrationSuccessfulPage
{	
	/** The driver. */
	private WebDriver driver;
	
	/** The registration success message. */
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'success'")
	@AndroidFindBy(id="reg_done_msg")
	private WebElement successMsg;
	
	/** The login button. */
	@iOSXCUITFindBy(accessibility="Login")
	@AndroidFindBy(id="main_btnClose")
	private WebElement loginButton;
	
	/**
	 * Instantiates a new RegistrationSuccessfulPage.
	 *
	 * @param driver the driver
	 */
	public RegistrationSuccessfulPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Verifies successful registration.
	 */
	public void verifyRegistrationSuccess()
	{		
		try
		{
			String msg=successMsg.getText();
			Log.info("======== Verifying Successful Registration message : "+msg+"========");
			Assert.assertTrue(msg.contains("success"), "Registration Unsuccessful\n");
		}
		catch(Exception e)
		{
			Assert.fail("Registration not Successful \n"+e.getMessage());
		}		
	}	
	
	/**
	 * Clicks on login button and navigates to Login Page.
	 */
	public void gotoLogin()
	{
		Log.info("======== Clicking on login button ========");
		loginButton.click();
	}
	
	
	
	
	
	

}
