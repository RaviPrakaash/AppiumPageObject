package wibmo.app.pagerepo;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import library.Generic;
import library.Log;

/**
 * The Class SendMoneyContactDetailsPage mainly used to verify a previously selected contact and corresponding mobileNumber/email, for sending money.
 */
public class SendMoneyContactDetailsPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	private SoftAssert softAssert;
	
	/** The contact name displayed. */
	@iOSXCUITFindBy(className="XCUIElementTypeStaticText") // or use **/XCUIElementTypeScrollView/XCUIElementTypeStaticText[1]
	@AndroidFindBy(id="value_phone_name")
	private WebElement contactNameField;
	
	/** The contact number displayed , corresponding to the contact name. */ 
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeStaticText[2]") // or use **/XCUIElementTypeScrollView/XCUIElementTypeStaticText[2]
	@AndroidFindBy(id="value_phone_number")
	private WebElement contactValueField; 
	
	/** The amount text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="sendmoney_amount")
	private WebElement amntTextField;
	
	/** The Continue Button. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and name beginswith 'Send'")
	@AndroidFindBy(id="button_send_money")
	private WebElement sendMoneyBtn;
	
	/**
	 * Instantiates a new SendMoneyContactDetailsPage.
	 *
	 * @param driver the driver
	 */
	public SendMoneyContactDetailsPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
		softAssert=new SoftAssert();
	}
	
	/**
	 * Verifies a previously selected contactName and the corresponding value(mobile/email).
	 *
	 * @param contactName the contact name
	 * @param contactValue the contact value
	 */
	public void verifySelectedContact(String contactName,String contactValue)
	{
		Log.info("======== Verifying Contact Name :"+contactName+" ========");
		softAssert.assertEquals(contactNameField.getText().toLowerCase().trim(),contactName.toLowerCase());
		
		Log.info("======== Verifying Contact value :"+contactValue+" ========");
		softAssert.assertEquals(Generic.parseNumberString(contactValueField.getText()),contactValue.toLowerCase());
		
		softAssert.assertAll();		
	}
	
	public void enterValues(String data)
	{
		String[] values = data.split(",");
		
		String amt=values[4],amtChk;
		String description = "TestSendMoney"; // Will be parameterized if necessary, since amt index is used by multiple methods
		
		
		Log.info("======== Entering Amount: "+amt+" ========");
		if(!(amtChk=amntTextField.getText()).equals(amt))
		{
			if(!amtChk.isEmpty())amntTextField.clear();
			amntTextField.sendKeys(amt);
			Generic.hideKeyBoard(driver);
		}
		
		Log.info("======== Cliking on Send Money Button ========");
		sendMoneyBtn.click();
	}
	
	
}
