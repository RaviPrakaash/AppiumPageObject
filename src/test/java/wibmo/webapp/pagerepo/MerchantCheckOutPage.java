package wibmo.webapp.pagerepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import library.Log;


// TODO: Auto-generated Javadoc
/**
 * The Class MerchantCheckOutPage used to enter the transaction amount and  card details 
 */
public class MerchantCheckOutPage {

	/** The driver. */
	WebDriver driver;
	
	/**
	 * Instantiates a new MerchantCheckOutPage.
	 *
	 * @param driver the driver
	 */
	public MerchantCheckOutPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	/** The sample alert button. */
	@FindBy(xpath="//button[text()='Sample alert']")
	private WebElement sampleAlertButton;
	
	/** The sample toast button. */
	@FindBy(xpath="//button[text()='Sample toast']")
	private WebElement sampleToastButton;
	
	/** The transaction amount text field. */
	@FindBy(xpath="//input[@name='amount']")
	private WebElement txnAmountTextField;
	
	@FindBy(name="version")
	private WebElement versionSelector;
	
	/** The checkout button. */
	@FindBy(xpath="//input[@value='Checkout']")
	private WebElement checkoutButton;
	
	/**
	 * Enters the transaction amount and clicks on Checkout button.
	 *
	 * @param data the data
	 */
	public void merchantCheckout(String data)
	{
		String[] str = data.split(",");
		
		Log.info("====Entering Transaction Amount : "+str[3]+"====");
		txnAmountTextField.clear();
		txnAmountTextField.sendKeys(str[3]);
		
		Log.info("==== Clicking on Checkout button ====");
		checkoutButton.click();
	}
	
	public void merchantCheckout(String amt,String iapVersion)
	{
		Log.info("========Entering Transaction Amount : "+amt+"========");
		if(!txnAmountTextField.getText().equals(amt))
		{
			txnAmountTextField.clear();
			txnAmountTextField.sendKeys(amt);
		}
		
		Log.info("======== Selecting IAP version "+iapVersion+"========");
		if(iapVersion.contains("2"))
			new Select(versionSelector).selectByValue("2");
		
		Log.info("==== Clicking on Checkout button ====");
		checkoutButton.click();		
	}
	
}
