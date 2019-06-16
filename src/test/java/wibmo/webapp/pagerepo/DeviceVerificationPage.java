package wibmo.webapp.pagerepo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import library.Log;

import library.Generic;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviceVerificationPage used to enter the DVC for the given mobile.
 */
public class DeviceVerificationPage {
	
	/** The driver. */
	public WebDriver driver;
	
	/**
	 * Instantiates a new DeviceVerificationPage
	 *
	 * @param driver the driver
	 */
	public DeviceVerificationPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	/** The dvc textfield. */
	@FindBy(xpath="//input[@name='otp']")
	private WebElement dvcTextfield;
	
	/** The login button. */
	@FindBy(xpath="//button[contains(text(),'Login')]")
	private WebElement loginButton;
	
	/** The cancel button. */
	@FindBy(xpath="//button[contains(text(),'Cancel')]")
	private WebElement cancelButton;
	
	/**
	 * Enters the OTP into the DVC textfield
	 *
	 * @param otp the otp
	 */
	public void  adddvcdetails(String otp)
	{
		String xp="//input[@name='otp']|//*[@id='sourceSelection']";
		
		if(driver.findElement(By.xpath(xp)).getAttribute("name").contains("otp"))
		{
			Log.info("====Enter dvc number of device : "+otp+"====");
			dvcTextfield.sendKeys(otp);		
			loginButton.click();
		}
	}	
	
	/**
	 * Cancels DVC by accepting the confirmation alert.
	 */
	public void dvccancel()
	{
		Log.info("====Cancelling dvc authentication====");
		String cancelXp="//input[@name='trustThisDevice']/../../..//button[contains(text(),'Cancel')]";
		//cancelButton.click(); stale
		driver.findElement(By.xpath(cancelXp)).click(); 
		Log.info("==== Accepting Alert ====");
		driver.switchTo().alert().accept();
		
		Generic.checkAlert(driver);
	}
}
