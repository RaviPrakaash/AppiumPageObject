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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
//import wibmo.app.testScripts.SendMoney.BaseTest;
import library.Generic;
import library.Log;

/**
 * The Class SendMoneyPage used to enter the recipient details like name, mobile/email, amount etc.
 */
public class SendMoneyConfirmPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeStaticText[2]")
	@AndroidFindBy(id="sendmoney_rec_name")
	private WebElement nameField;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeStaticText[4]")
	@AndroidFindBy(id="sendmoney_account_number")
	private WebElement accNoField;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeStaticText[6]")
	@AndroidFindBy(id="sendmoney_bankbranch_code")
	private WebElement ifscField;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeStaticText[8]")
	@AndroidFindBy(id="sendmoney_amount")
	private WebElement amtField;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeStaticText[10]")
	@AndroidFindBy(id="fees_amount")
	private WebElement feesAmtField;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeStaticText[12]")
	@AndroidFindBy(id="total_amount")
	private WebElement totalAmtField;
	
	@iOSXCUITFindBy(accessibility="Confirm")
	@AndroidFindBy(id="btnConfirm")
	private WebElement confirmBtn;
	
	private String footerText="Confirm"; 
	
	
	/**
	 * Instantiates a new SendMoneyConfirmPage.
	 *
	 * @param driver the driver
	 */
	public SendMoneyConfirmPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this); 
	}
	
	
	/**
	 * Confirms the Bank values previously entered and returns the updated amt+gst
	 * 
	 * @param data
	 * @return amt + gst 
	 * 
	 */
	public double confirmBankValues(String data)
	{
		String[] values = data.split(",");
		
		int i = 2;
		
		String name = values[i++],nameDisplayed;
		String accNo = values[i++],accNoDisplayed;
		String ifscCode=values[i++],ifscCodeDisplayed;
		String amount = values[i++],amountDisplayed,fees,totalAmt=amount;		
		
		Log.info("======== Confirming entered values ========");	
		try
		{
			new WebDriverWait(driver, 35).until(ExpectedConditions.visibilityOf(confirmBtn));
			
			Log.info("Account Holder Name : "+(nameDisplayed=nameField.getText()));
			Log.info("Account Number : "+(accNoDisplayed=accNoField.getText()));
			Log.info("IFSC Code : "+(ifscCodeDisplayed=ifscField.getText()));
			Log.info("Amount : "+(amountDisplayed=amtField.getText()));
			
			Log.info("Fees : "+(fees=feesAmtField.getText()));
			Log.info("Total Amount : "+(totalAmt=totalAmtField.getText()));			
			
			Assert.assertTrue(Generic.containsIgnoreCase(nameDisplayed, name), name+" not displayed correctly\n");
			Assert.assertTrue(Generic.containsIgnoreCase(accNoDisplayed, accNo), accNo+" not displayed correctly\n");
			Assert.assertTrue(Generic.containsIgnoreCase(ifscCodeDisplayed, ifscCode), ifscCode+" not displayed correctly\n");
			Assert.assertTrue(Generic.containsIgnoreCase(Generic.parseNumberString(amountDisplayed), amount), amount+" not displayed correctly\n");
			
		}		
		catch(Exception e)
		{	
			Assert.fail("Unable to confirm values "+e.getMessage());			
		}
		
		clickConfirm();
	
		return Generic.parseNumber(totalAmt);
		
	}
	
	public void clickConfirm()
	{
		Log.info("======== Clicking on Confirm button ========");
		
		Generic.scroll(driver, footerText);
		confirmBtn.click();		
		
	}
	
	
}
