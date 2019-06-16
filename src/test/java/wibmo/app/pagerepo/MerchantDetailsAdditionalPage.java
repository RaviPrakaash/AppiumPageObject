package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import library.Log;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import library.Generic;

/**
 * The Class MerchantDetailsAdditionalPage.
 */
public class MerchantDetailsAdditionalPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The email text field. */
	@FindBy(xpath="(//android.widget.EditText)[1]")
	private WebElement emailTextField;
	
	/** The mobile no text field. */
	@FindBy(xpath="(//android.widget.EditText)[2]")
	private WebElement mobileNoTextField;
	
	/** The dob text field. */
	@FindBy(xpath="(//android.widget.EditText)[3]")
	private WebElement dobTextField;
	
	/** The new secure pin text field. */
	@FindBy(xpath="(//android.widget.EditText)[4]")
	private WebElement newSecurePinTextField;
	
	/** The continue btn. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'Continue')]")
	private WebElement continueBtn;
	
	/**
	 * Instantiates a new merchant details additional page.
	 *
	 * @param driver the driver
	 */
	public MerchantDetailsAdditionalPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Verify merchant register page.
	 */
	public void verifyMerchantRegisterPage()
	{
		Log.info("======== Verifying registration Page ========");		
		try
		{
		new WebDriverWait(driver,45).until(ExpectedConditions.visibilityOf(continueBtn));
		}
		catch(Exception e)
		{
			Assert.fail("Register page not found \n"+e.getMessage());
		}
		
	}
	
	/**
	 * Enters additional details for WebOverlay Registration.
	 *
	 * @param data the data
	 */
	public void enterDetails(String data)
	{
		String[] values = data.split(",");
		/*String email=values[i++];
		String mobileNo=values[i++];*/
		String dob=values[8];
		String newSecurePin=values[1];
		
		// ---- Values will be pre populated ---- 
		/*Log.info("======== Enter email id :"+email+" ========");
		if(!emailTextField.getAttribute("name").equals(email))
		{
			Generic.forceClearAttribute(driver, emailTextField);
			emailTextField.sendKeys(email);
			Generic.hideKeyBoard(driver);
		}
		
		Log.info("======== Enter Mobile No :"+mobileNo+" ========" );
		if(!mobileNoTextField.getAttribute("name").equals(mobileNo))
		{
			Generic.forceClearAttribute(driver, emailTextField);
			mobileNoTextField.sendKeys(mobileNo);
			Generic.hideKeyBoard(driver);
		}*/
		
		Log.info("======== Enter DOB :"+dob+" ========" );
		if(!dobTextField.getAttribute("contentDescription").equals(dob))
		{
			Generic.forceClearAttribute(driver,dobTextField);
			dobTextField.sendKeys(dob);			
			Generic.hideKeyBoard(driver);
		}
		
		newSecurePinTextField.click();
		
		Log.info("======== Enter New Secure Pin :"+newSecurePin+" ========" );
		if(!newSecurePinTextField.getAttribute("contentDescription").equals(newSecurePin))
		{
			
			Generic.forceClearAttribute(driver,newSecurePinTextField);
			newSecurePinTextField.sendKeys(newSecurePin);
			Generic.hideKeyBoard(driver);
		}
		
		Log.info("======== Clicking on Continue Button ========");
		continueBtn.click();
	}
}
