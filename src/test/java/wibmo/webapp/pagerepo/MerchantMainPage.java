package wibmo.webapp.pagerepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import library.Generic;
//import com.libraries.Log;
import library.Log;
import wibmo.app.testScripts.IAP_Transaction.BaseTest;

/**
* The Class MerchantMainPage used to enter the user details for a transaction.
*/
public class MerchantMainPage {

	/** The driver. */
	WebDriver driver;
	
	/**
	 * Instantiates a new MerchantMainPage
	 *
	 * @param driver the driver
	 */
	public MerchantMainPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	/** The name text field. */
	@FindBy(xpath="//input[@name='cust_name']")
	private WebElement nameTextField;
	
	/** The mobile number text field. */
	@FindBy(xpath="//input[@name='cust_mobile_number']")
	private WebElement mobileTextField;
	
	/** The email text field. */
	@FindBy(xpath="//input[@name='cust_email_id']")
	private WebElement emailTextField;
	
	/** The merchant data text field. */
	@FindBy(xpath="//input[@name='mer_data_field']")
	private WebElement merchantDataTextField;
	
	/** The merchant Dynamic name text field. */
	@FindBy(xpath="//input[@name='mer_dynamic_name']")
	private WebElement merchantDNameTextField;
	
	/** The submit button. */
	@FindBy(xpath="//button[text()='Submit']")
	private WebElement submitButton;
	
	//====================== IAPV2 dropdowns =========================//
	
	@FindBy(name="mer_txnAmount_Known")
	private WebElement amountKnownSelector;
	
	@FindBy(name="mer_chargeOnDatapickup")
	private WebElement chargeOnPickupSelector;
	
	
	/**
	 * Enters basic user info like name, mobile, email and clicks on Submit button.  
	 *
	 * @param data the data
	 */
	public void basicInfoSubmit(String data)		//Fill the required details of  Merchant main page and click on the submit button
	{
		String str[] = data.split(",");
		Log.info("====Entering User Details====");
		
		Log.info("======== Entering Name : "+str[0]+" ========");
		nameTextField.sendKeys( str[0]);
		
		Log.info("======== Entering mobile  : "+str[1]+" ========");
		mobileTextField.sendKeys(str[1]);
		
		Log.info("======== Entering email : "+str[2]+" ========");
		emailTextField.sendKeys(str[2]);
		
		if(Generic.containsIgnoreCase(data, "IAPV1") || str.length<=5) // Select default IAPV1 value
		{
			Log.info("======== IAPV1 flow ========");
			new Select(chargeOnPickupSelector).selectByValue("false");
		}	
		
		if(Generic.containsIgnoreCase(BaseTest.getMerchantType(), "dynamic"))
		{
			merchantDNameTextField.clear();
			Log.info("======== Entering dynamic merchant name : Dynamic Script Merchant ========");
			merchantDNameTextField.sendKeys("Dynamic Script Merchant");
		}		
		
		submitButton.click();

	}
	
	/**
	 * For IAPV2, Enters registered mobile number and sets configuration for Amount Known & Charge on Datapickup
	 *
	 * @param data the data
	 */
	public void basicInfo(String data)
	{
		String values[]=data.split(",");
		
		String mobileNumber=values[0],
			   amountKnownStatus=values[5],
			   chargeOnPickupStatus=values[6];
		
		Log.info("======== Entering mobile number : "+mobileNumber+" ========");
		mobileTextField.sendKeys(mobileNumber);
		
		Log.info("======== Setting Amount Known Status : "+amountKnownStatus+"========");
		if(amountKnownStatus.toLowerCase().contains("true"))
			new Select(amountKnownSelector).selectByValue("true");
		else
			new Select(amountKnownSelector).selectByValue("false");
		
		Log.info("======== Setting Charge On Pickup Status "+chargeOnPickupStatus+"========");
		if(chargeOnPickupStatus.toLowerCase().contains("true"))
			new Select(chargeOnPickupSelector).selectByValue("true");
		else
			new Select(chargeOnPickupSelector).selectByValue("false");
		
		Log.info("======== Clicking on Submit ========");
		submitButton.click();		
	}
	
	/**
	 * Enters basic user info like name, mobile, email and clicks on Submit button for Dynamic Merchant
	 *
	 * @param data the data
	 */
	public void basicInfoSubmitForDynamic(String data)
	{
		String str[] = data.split(",");
		Log.info("====Merchant Home Page Details====");
		nameTextField.sendKeys( str[0]);
		mobileTextField.sendKeys(str[1]);
		emailTextField.sendKeys(str[2]);
		merchantDNameTextField.sendKeys("MVISA");
		submitButton.click();
	}
	
	public void basicInfoSubmitForHashFail(String data)
	{
		String str[] = data.split(",");
		Log.info("====Entering User Details====");
		
		Log.info("======== Entering Name : "+str[0]+" ========");
		nameTextField.sendKeys( str[0]);
		
		Log.info("======== Entering mobile  : "+str[1]+" ========");
		mobileTextField.sendKeys(str[1]);
		
		Log.info("======== Entering email : "+str[2]+" ========");
		emailTextField.sendKeys(str[2]);
		
		if(Generic.containsIgnoreCase(data, "IAPV1") || str.length<=5) // Select default IAPV1 value
		{
			Log.info("======== IAPV1 flow ========");
			new Select(chargeOnPickupSelector).selectByValue("false");
		}	
		
		merchantDNameTextField.clear(); // For static enter Dynamic Merchant name , for dynamic enter null as Dynamic Merchant name
		if(Generic.containsIgnoreCase(BaseTest.getMerchantType(), "static"))
		{			
			Log.info("======== Entering dynamic merchant name : Dynamic Script Merchant ========");
			merchantDNameTextField.sendKeys("Hash Trigger");
		}		
		
		submitButton.click();
	}

}
