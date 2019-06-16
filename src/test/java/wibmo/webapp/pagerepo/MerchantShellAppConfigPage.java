package wibmo.webapp.pagerepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.libraries.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class MerchantShellAppConfigPage used to set the Merchant type as Static/Dynamic and change hashkey value. 
 */
public class MerchantShellAppConfigPage
{
	
	/** The driver. */
	WebDriver driver;
	
	/**
	 * Instantiates a new MerchantShellAppConfigPage
	 *
	 * @param driver the driver
	 */
	public MerchantShellAppConfigPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	/** The merchant ID text field. */
	@FindBy(xpath="//input[@name='merId']")
	private WebElement merchantIDTextField;
	
	/** The merchant app ID text field. */
	@FindBy(xpath="//input[@name='merAppId']")
	private WebElement merchantAppIDTextField;
	
	/** The currency text field. */
	@FindBy(xpath="//input[@name='txnCurrency']")
	private WebElement currencyTextField;
	
	/** The Secret hash key text field. */
	@FindBy(xpath="//input[@name='hashkey']")
	private WebElement SecretHashKeyTextField;
	
	/** The domain text field. */
	@FindBy(xpath="//input[@name='domain']")
	private WebElement domainTextField;
	
	/** The set new value button. */
	@FindBy(xpath="//input[@value='Set new value']")
	private WebElement setNewValueButton;
	
	/**
	 * Changes the secret hash key value.
	 *
	 * @param data the data
	 */
	public void changeSecretHashKey(String data)
	{
		
		String str[]=data.split(",");
		SecretHashKeyTextField.clear();
		Log.info("==== Setting hashkey value : "+str[7]+" ====");
		SecretHashKeyTextField.sendKeys(str[7]);
		
		Log.info("==== Clicking on New value button ====");
		setNewValueButton.click();
		
		driver.get("https://wallet.pc.enstage-sas.com/testMerchant/wPay");
	}
	
	/**
	 *  Used to set the correct hashkey value
	 *
	 * @param data the data
	 */
	public void correctSecretHashKey(String data)
	{
		
		String str[]=data.split(",");
		SecretHashKeyTextField.clear();
		SecretHashKeyTextField.sendKeys(data);
		setNewValueButton.click();
		driver.get("https://wallet.pc.enstage-sas.com/testMerchant/wPay");		
	}
	
	/**
	 * Enters Dynamic merchant details.
	 */
	public void enterDynamicDetails()
	{
		
		merchantIDTextField.clear();
		merchantIDTextField.sendKeys("62571330538168734134");
		
		merchantAppIDTextField.clear();
		merchantAppIDTextField.sendKeys("4038");
		
		currencyTextField.sendKeys("");
		SecretHashKeyTextField.clear();
		SecretHashKeyTextField.sendKeys("65625713305384573483");
		
		domainTextField.sendKeys("");
		setNewValueButton.click();
	}
	
}
