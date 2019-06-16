package wibmo.webapp.pagerepo;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.testng.Assert;

import library.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class PopUpsHandlingPage used to handle confirmation alerts.
 */
public class PopUpsHandlingPage {

	/** The driver. */
	WebDriver driver;
	
	/**
	 * Instantiates a new PopUpsHandlingPage
	 *
	 * @param driver the driver
	 */
	public PopUpsHandlingPage(WebDriver driver) 
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * Accepts alert.
	 */
	public void acceptAlert()   //click ok on alert pop up
	{		
		try
		{
			driver.switchTo().alert().accept();
		}
		catch(Exception e)
		{
			Log.info("=== Alert delay ===");
		}
	}
	
	/**
	 * Dismisses alert.
	 */
	public void dismissAlert()  //click cancel on alert pop up
	{		
		driver.switchTo().alert().dismiss();
	}	
	
}
