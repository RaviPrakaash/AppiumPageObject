package wibmo.app.pagerepo;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import library.Generic;
//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
//import org.testng.asserts.SoftAssert;

//import wibmo.app.testScripts.SendMoney.BaseTest;

import library.Log;

/**
 * The Class SendMoneyPage used to enter the recipient details like name, mobile/email, amount etc.
 */
public class TransactionDetailPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	@FindBy(xpath="//android.widget.ImageView[contains(@resource-id,'history_txn_img')]")
	private WebElement txnIcon;
	
	@FindBy(id="am_hdetail_na_value")
	private WebElement txnNarration;
	
	@FindBy(id="am_hdetail_amt_value")
	private WebElement txnAmt;
	
	@FindBy(id="am_hdetail_ref_value")
	private WebElement txnIdField;
	
	@FindBy(id="am_hdetail_ref_text")
	private WebElement txnText;
	
	/** The OK button after the transaction completes. */
	@FindBy(id="am_hdetail_btnBack")
	private WebElement backBtn;
	
	
	/**
	 * Instantiates a new TransactionDetailPage.
	 *
	 * @param driver the driver
	 */
	public TransactionDetailPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	public void verifyTxnDetails()
	{
		Log.info("======== Verifying Transaction Details ========");
		try
		{
			txnIcon.isDisplayed();
			Log.info("Narration : "+txnNarration.getText());
			Log.info("Amt : "+txnAmt.getText());
			Log.info("Txn Id : "+txnIdField.getText());
			
			backBtn.click();
		}
		catch(Exception e)
		{
			Assert.fail("Error in validating Transaction details"+e.getMessage());
		}		
	}
	
	public void verifyTxnId(String txnId)
	{
		Generic.wait(2); // Wait text to populate , use text in element 
		Log.info("======== Verifying Transaction Id : "+txnId+" in transaction detail page ========");
		try
		{
			String txnIdDisplayed=txnIdField.getText();		
			Assert.assertEquals(txnIdDisplayed, txnId, "Transaction Id "+txnIdDisplayed+" does not match with "+txnId);
		}
		catch(Exception e)
		{
			Assert.fail("Transaction Id field not found \n"+e.toString());
		}
	}
	
	public void navigateBack()
	{
		Log.info("======== Clicking on Back button ========");
		backBtn.click();
	}
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
}
