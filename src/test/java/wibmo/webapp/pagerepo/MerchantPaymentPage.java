package wibmo.webapp.pagerepo;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import library.Generic;
import library.Log;
import wibmo.app.testScripts.Guest_Checkout.BaseTest;

		// TODO: Auto-generated Javadoc
//Payzapp payment page (Guest checkout page or payzapp web SDK)

/**
* The Class MerchantPaymentPage used to enter Registration details for unregistered user.
*/
public class MerchantPaymentPage {
	
	/** The driver. */
	WebDriver driver;
	
	/**
	 * Instantiates a new MerchantPaymentPage.
	 *
	 * @param driver the driver
	 */
	public MerchantPaymentPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	/** The full name text field. */
	@FindBy(id="guestFullName")
	private WebElement fullNameTextField;
	
	/** The card number textfield. */
	@FindBy(id="cardNumber")
	private WebElement cardNumber;
	
	/** The expiry month select list. */
	@FindBy(id="expMM")
	private WebElement expMonthSelectList;
	
	/** The expiry year select list. */
	@FindBy(id="expYYYY")
	private WebElement expYearSelectList;
	
	/** The email text field. */
	@FindBy(id="guestEmail")
	private WebElement emailTextField;
	
	/** The mobile number text field. */
	@FindBy(id="guestMobile")
	private WebElement mobileNumTextField;
	
	/** The cancel button. */
	@FindBy(xpath="//button[contains(text(),'Cancel')]")
	private WebElement cancelButton;
	
	/** The continue button. */
	@FindBy(id="registerButton")
	private WebElement continueButton;
	
	/**
	 * Enters card details like CardNumber,Expiry month & year and clicks on Continue. 
	 *
	 * @param data the data
	 */
	public void enterCardDetails(String data)   //Enter the card details in the text fields of Guest checkout page and continue for the next step
	{
		String[] str = data.split(",");
		
		Log.info("====Entering Card Details : "+str[4]+"====");
		cardNumber.sendKeys(str[4]);
		
		Log.info("====Entering Card Expiry Month "+str[5]+"& Year "+str[6]+"====");
		Select selMonth=new Select(expMonthSelectList);
		selMonth.selectByValue(str[5]);		
		Select selYear=new Select(expYearSelectList);
		selYear.selectByValue(str[6]);
		
		Log.info("==== Clicking on Continue button ====");
		continueButton.click();		
	}
	
	/**
	 * Cancels Registration.
	 *
	 * @param data the data
	 */
	public void cancelCardDetails(String data)		//Click on cancel button	
	{
		Log.info("==== Clicking on Cancel button ====");
		cancelButton.click();		
	}
	
	/**
	 * Enters the invalid card number and handles the alerts generated.
	 *
	 * @param data the data
	 */
	public void invalidCardNum(String data)  //
	{
		String[] str = data.split(",");
	
		try{
			Log.info("==== Entering invalid card number "+str[4]+" ====");
			cardNumber.sendKeys(str[4]);
			
			Log.info("==== Selecting exp month : "+str[5]+"====");
			Select selMonth=new Select(expMonthSelectList);
			selMonth.selectByValue(str[5]);
			
			Alert alert1 = driver.switchTo().alert();
			alert1.accept();
			
			Log.info("==== Selecting exp year : "+str[6]+" ====");
			Select selYear=new Select(expYearSelectList);
			selYear.selectByValue(str[6]);
			
			cardNumber.clear();
			if(cardNumber.getText().isEmpty())
			{
				cardNumber.sendKeys(str[4]);
			}
			continueButton.click();
			
			Alert alert2 = driver.switchTo().alert();
			Log.info("==== Validating alert message : "+alert2.getText()+" ====");
			alert2.accept();
			Generic.wait(2);
			
			Alert alert3 = driver.switchTo().alert();
			Log.info("==== Validating alert message : "+alert3.getText()+" ====");
			if(alert3.getText().contains("Visa"))
			{
				alert3.accept();
			}
			Generic.wait(2);
			}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Invalid card no. accepted\n"+e.getMessage());
		}
	}
	
	/**
	 * Enters invalid expiry date and clicks on continue.
	 *
	 * @param data the data
	 */
	public void invalidCardExpiry(String data)  
	{
		String[] str = data.split(",");
		Log.info("==== Entering card number ====");
		cardNumber.clear();
		cardNumber.sendKeys(str[4]);
		
		Log.info("==== Selecting exp month : "+str[5]+"====");
		Select selMonth=new Select(expMonthSelectList);
		selMonth.selectByValue(str[5]);
		
		Log.info("==== Selecting exp year : "+str[6]+" ====");
		Select selYear=new Select(expYearSelectList);
		selYear.selectByValue(str[6]);
		continueButton.click();
		
		Generic.wait(3);
	}
	/**
	 * Returns the TestCaseID and Scenario if the current TestCase is under Group execution 
	 * 
	 * @return String TestCaseID and Scenario if under Group execution
	 */
	public String groupExecuteFailMsg()
	{
		if(BaseTest.groupExecute) 		
			return "\nFailed under Group Execution "+BaseTest.groupTestID+" : "+BaseTest.groupTestScenario+"\n";	
		else
			return "";
	}
}