package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import library.Log;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import library.Generic;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class MerchantMVCPage used to enter the MVC and verify Registration during Merchant Web Overlay registration.
 */
public class MerchantMVCPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	SoftAssert softAssert;
	
	/** The mvc text field. */
	@FindBy(className="android.widget.EditText")
	private WebElement mvcTextField;
	
	/** The resend mvc link. */
	@FindBy(xpath="//android.view.View[contains(@content-desc,'Resend MVC')]")
	private WebElement resendMvcLink;
	
	/** The continue button. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'Continue')]")
	private WebElement continueBtn;	
	
	
	/**
	 * Instantiates a new MerchantMVCPage
	 *
	 * @param driver the driver
	 */
	public MerchantMVCPage(WebDriver driver)
	{
		this.driver=driver;
		softAssert=new SoftAssert();
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Enters MVC from DB.
	 *
	 * @param data the data
	 * @param bankCode the bank code
	 */
	public void enterMVC(String data,String bankCode)
	{
		new WebDriverWait(driver, 45).until(ExpectedConditions.visibilityOf(resendMvcLink));
		String mobileNo = data.split(",")[0];
		Log.info("======== Retreiving MVC from DB ========");
		mvcTextField.sendKeys(Generic.getOTP(mobileNo,bankCode,Generic.getPropValues("MVC", BaseTest1.configPath)));
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Clicking on Continue Button ========");
		continueBtn.click();			
	}
	
	/**
	 * Verifies registration.
	 */
	public void verifyRegister()
	{
		String xp="//android.view.View[contains(@content-desc,'Already')] | //android.widget.TextView[contains(@resource-id,'status_text')]";
		new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));
		if(driver.findElement(By.xpath(xp)).getAttribute("contentDescription").contains("Already"))
			Generic.scroll(driver, "Done").click();
	}
}
