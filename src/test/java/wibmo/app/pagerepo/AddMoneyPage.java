package wibmo.app.pagerepo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import library.Log;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;
//import library.HandleProceedPopup;
import wibmo.app.testScripts.AddMoney.BaseTest;

/**
 * The Class AddMoneyPage used to enter the amount and add money.
 */
public class AddMoneyPage extends BasePage
{	
	/** The driver. */
	private WebDriver driver;	
		
	/** The amount textfield. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="edit_amount")
	private WebElement amountTextfield;
	
	/** The add money button. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and name='Add Money'")
	@AndroidFindBy(xpath="//android.widget.Button[contains(@resource-id,'button_send_money') or contains(@resource-id,'button_load_money')]")
	private WebElement addMoneyButton;
	
	/** The cancel button. */
	@iOSXCUITFindBy(accessibility="Cancel")
	@AndroidFindBy(id="button_cancel")
	private WebElement cancelButton;	
	
	/** The invalid amountt error message. */
	
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Please'")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'message')]") 
	private WebElement invalidAmtErrMsg;
	
	/** The alert confirmation ok button. */
	@iOSXCUITFindBy(accessibility="Ok")
	@AndroidFindBy(className="android.widget.Button")
	private WebElement confirmationOkButton;
	
	
	/**
	 * Instantiates a new AddMoneyPage.
	 *
	 * @param driver the driver
	 */
	public AddMoneyPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Enters amount to be added into the field.
	 *
	 * @param amt the amt
	 */
	public void enterAmtVal(String amt)
	{		
		if(!amountTextfield.getText().contains("Enter"))
			 clear(amountTextfield);
		
		if(Arrays.asList("100","200","500","1000").contains(amt))
		{
			Log.info("======== Clicking on "+amt+" button ========");
			
			if(Generic.isAndroid(driver)) // Android 
			{
				String btnId="btn_"+amt;
				Generic.tap(driver, driver.findElement(By.id(btnId)));
			}
			else  // IOS 
			{
				String predicate="name endswith 'amt'".replace("amt", amt);
				driver.findElement(MobileBy.iOSNsPredicateString(predicate)).click();
			}
		}
		else
		{
			Log.info("======== Entering amount : "+amt+" ========");
			sendKeys(amountTextfield,amt);			
		}			
		Generic.hideKeyBoard(driver);		
	}
	
	/**
	 * Enters amount and clicks on Add Money button.
	 *
	 * @param amt the amt
	 */
	public void enterAmount(String amt)
	{
		enterAmtVal(amt);
		
		Log.info("======== Clicking on Add Money button ========");
		addMoneyButton.click();			
		
		handleProceed();
	}
	
	public void handleKYCNoteAlert()
	{
		String msg; 
		
		if(Generic.isIos(driver)) return; // No Alert message for IOS
		
		try 
		{
			okButton.isDisplayed();
			Log.info("======== Verifying KYC Note message : "+(msg=getText(alertMessage))+" ========");
			Assert.assertTrue(msg.contains("send money and bank transfer is not allowed"), "KYC Note message incorrect");
			okButton.click();
		}
		catch(Exception e)
		{
			Assert.fail("KYC Note Alert not found"+e.getMessage());
		}
		
	}
	
	
	/**
	 * Handles proceed alert message.
	 */
	public void handleProceed() 
	{
		if(Generic.isIos(driver)) return;
		
		Generic.wait(2);
		
		String proceedXp="//*[@text='Proceed']|"+
			     "//*[contains(@resource-id,'button1') or contains(@resource-id,'title_text')]";

		Log.info("======== Clicking on optional Proceed ========");
		try{driver.findElement(By.xpath(proceedXp)).click();}catch(Exception e){Log.info("==Delay due to Proceed==");}		
	}
	
	/**
	 * Enters a blank amount and verifies the corresponding error message.
	 */
	public void verifyBlankAmount() 
	{		
		
		amountTextfield.isDisplayed();
		Generic.hideKeyBoard(driver);
		clickAddMoneyBtn();
		
		try
		{
			String errMsg= Generic.isAndroid(driver)?alertMessage.getText():invalidAmtErrMsg.getText();
			Log.info("======== Verifying error message :"+errMsg+" ========");
			Assert.assertTrue(errMsg.toLowerCase().contains("valid amount"),groupExecuteFailMsg()+ "Error message for Blank amount not found\n");
			
			Log.info("======== Clicking on OK button ========");
			confirmationOkButton.click();
		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Error message for blank amount not found \n"+e.getMessage());
		}	
	}
	
	public void clickAddMoneyBtn()
	{
		Log.info("======== Clicking on Add Money button ========");
		addMoneyButton.click();			
	}
	
	public void verifyInvalidAmount(String amt){
		Log.info("=======Entering invalid amount :"+amt+"=======");
		
		enterAmtVal(amt);
		clickAddMoneyBtn();
		
		try
		{
			String errMsg=invalidAmtErrMsg.getText();
			Log.info("======== Verifying error message :"+errMsg+" ========");
			Assert.assertTrue(errMsg.contains("valid amount"),groupExecuteFailMsg()+ "Error message for Blank amount not found\n");
			
			Log.info("======== Clicking on OK button ========");
			confirmationOkButton.click();
			
			try {
				if(Generic.isAndroid(driver))
					confirmationOkButton.click();//typing again since the alert comes twice.
			}catch(Exception e) {System.err.println("Ok button not repeated ");}
			

		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Error message for blank amount not found \n"+e.getMessage());
		}	
		
			
	}
	
	/**
	 * Enters a decimal amount .
	 *
	 * @param amt the amt
	 */
	public void enterAmountDouble(String amt)
	{
		enterAmtVal(amt);
		
		String secondTapXp="//*[contains(@resource-id,'button_send_money') or contains(@resource-id,'button_load_money') or contains(@resource-id,'lmoney_detail_hello_text')]";
		Log.info("======== Double clicking on Add Money button ========");
		Generic.doubleTap(driver, addMoneyButton);
		
		//((AndroidDriver)driver).tap(2,addMoneyButton,1);
		//try{driver.findElement(By.xpath(secondTapXp)).click();}catch(Exception e){Log.info("==== Button not available for second tap ====");}		
		
	}
	
	/**
	 * Cancels add money after entering the amount.
	 *
	 * @param amt the amt
	 */
	public void cancelAddMoney(String amt) 
	{
		enterAmtVal(amt);
		
		Log.info("======== Clicking on Cancel button ========");
		cancelButton.click();		
	}

	/**
	 * Clicks on Navigate button after entering the amount.
	 *
	 * @param amt the amount
	 */
	public void verifyNavigate(String amt) 
	{
		enterAmtVal(amt);
		
		Log.info("======== Clicking on Navigate Back button ========");
		navigateLink.click();		
	}
	
	/**
	 * Returns the TestCaseID and Scenario if the current TestCase is under Group execution .
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
