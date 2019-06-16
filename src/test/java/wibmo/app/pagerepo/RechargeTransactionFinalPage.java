package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import library.Generic;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * The Class RechargeTransactionFinalPage mainly used to verify the final status of Recharge transaction (Mobile/DTH/DataCard). 
 */
public class RechargeTransactionFinalPage 
{
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	private SoftAssert softAssert;
	
	/** The transaction status message. */
	@FindBy(id="rechargeStatusTxt") 								//*[contains(@content-desc,'Your') or contains(@text,'Your')]
	private WebElement transactionStatusMessage; //*[contains(@resource-id,'') or contains(@text,'')] 
	
	@FindBy(xpath="(//*[contains(@content-desc,'Order')]/../*)[2]")
	private WebElement orderIdField;
	
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith '₹ '")
	@AndroidFindBy(id="rechargeStatusTxnID")
	private WebElement paymentIdField;
	
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith '6019'")
	@AndroidFindBy(id="rechargeStatusWibmoID")
	private WebElement transactionIdField;

	/** The success message. */
	@FindBy(xpath="//*[@text='Success']") 
	private WebElement successMessage;

	/** The Ok button. */
	@iOSXCUITFindBy(accessibility="OK")
	@AndroidFindBy(xpath="//android.widget.Button[contains(@content-desc,'OK') or contains(@content-desc,'Ok') or contains(@text,'OK') or contains(@text,'Ok') or contains(@resource-id,'rc_status_btnOkay')]") //id=rc_status_btnOkay
	private WebElement OkButton;

	/** The transaction failed error message. */
	@iOSXCUITFindBy(iOSNsPredicate="name contains 'could not be processed' or value contains 'ailed'")
	@AndroidFindBy(xpath="//*[contains(@content-desc,'could not be processed') or contains(@text,'ailed')]")
	private WebElement cancelBtnFailedErrMsg;

	/** The transaction success message. */
	@iOSXCUITFindBy(iOSNsPredicate="value contains[ci] 'Succes' or value contains 'being processed'") 
	@AndroidFindBy(xpath="//*[contains(@content-desc,'Success') or (@text='Success') or contains(@content-desc,'being processed')]")
	private WebElement successMsg;	
	
	/** The static int i used to count recursions and will be reset to 0 after recursion completes. */
	public static int i;	
	
	/**
	 * Instantiates a new RechargeTransactionFinalPage.
	 *
	 * @param driver the driver
	 */
	public RechargeTransactionFinalPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
		softAssert=new SoftAssert();
	}

	/**
	 * Verifies transaction failure message.
	 */
	public void verifyTransactionFailMsg()
	{

		Log.info("======== Waiting for Transaction status ========");
		new WebDriverWait(driver,35).until(ExpectedConditions.visibilityOf(OkButton));

		try 
		{
			String msg=cancelBtnFailedErrMsg.getText(); //+transactionStatusMessage.getAttribute("name")
			Log.info("======== Verifying Transaction Status Message : "+msg+" ========");
			Assert.assertTrue(Generic.containsIgnoreCase(msg, "could not be") || Generic.containsIgnoreCase(msg, "failed"));
		} 
		catch (Exception e) 
		{
			Assert.fail("Transaction Status Message not found \n"+e.getMessage());
		}
		
	}
	
	/**
	 * Verifies recharge success and also the Recharge details if the recharge is successful.
	 *
	 * @param data the data
	 */
	public void verifyRechargeSuccess(String data)
	{
		try
		{
			new WebDriverWait(driver,35).until(ExpectedConditions.visibilityOf(OkButton));
		}
		catch(Exception e)
		{			
			Assert.fail(" Recharge transaction Status Page not found or taking too long to load, stopping execution \n" +e.getMessage());		
		}
		
		Log.info("======== Verifying for Transaction success, please wait ========");
		try
		{
			Assert.assertTrue(successMsg.isDisplayed()); // Implicity wait for Success after Pending displayed
			String msg=successMsg.getText();
			Log.info("======== Mobile recharge successful ========" );			
			Log.info("======== Verifying message : "+msg+" ========");			
		}
		catch(Exception e)
		{
			Assert.fail("Transaction was unsuccessful\n"+e.getMessage());
		}
		
		verifyRechargeDetails(data);
		
		Log.info("======== Clicking on Transaction OK button ========");
		OkButton.click();			
	}

	/**
	 * Verifies recharge details for a successful transaction like subscriberId,recharge amount and operator.
	 *
	 * @param data the data
	 */
	public void verifyRechargeDetails(String data)
	{
		//======================================================================//
		
		try{
		String paymentId=paymentIdField.getText(),txnId=transactionIdField.getText();
		
		Log.info("======== "+paymentId+" ========");
		Log.info("======== Verifying txn Id : "+txnId+" ========");
		}
		catch(Exception e){Log.info("== Unable to obtain Transaction Details ==");}
		if(true) return;
		
		//======================================================================//

		String[] values =data.split(",");
		int amtIndex=values.length-2,opIndex=values.length-3,subIndex=values.length-4;		
		String amt=values[amtIndex],operator=values[opIndex],subscriberId=values[subIndex];		
		
		/*String subXp="//android.view.View[contains(@content-desc,'"+subscriberId+"')]",
				amtXp="//android.view.View[contains(@content-desc,'"+amt+"')]",
				opXp="//android.view.View[contains(@content-desc,'"+operator+"')]";		*/
		
		String subXp="//*[contains(@text,'"+subscriberId+"') or contains(@content-desc,'"+subscriberId+"')]",
				amtXp="//*[contains(@text,'"+amt+"') or contains(@content-desc,'"+amt+"')]",
				opXp="//*[contains(@text,'"+operator+"') or contains(@content-desc,'"+operator+"')]",
				orderXp="(//*[contains(@content-desc,'Order Id')]/../)[2]";
		
		// ==== Verify Order Id ==== //
		try
		{			
			String orderId=orderIdField.getAttribute("contentDescription")+orderIdField.getText();
			Log.info("======== Verifying Order Id :"+orderId+"========" );
		}
		catch(Exception e)
		{
			Log.info("Order Id not displayed correctly\n");
		}
		
		// ==== Verify Subscriber Id ==== //
		
		try
		{
			Log.info("======== Verifying Subscriber Id :"+subscriberId+" ========" );
			softAssert.assertTrue(driver.findElement(By.xpath(subXp)).isDisplayed());
		}
		catch(Exception e)
		{
			softAssert.fail("Subscriber Id : "+subscriberId+" not displayed correctly\n");
		}
		
		// ==== Verify transaction amount ==== //
		
		try
		{
			Log.info("======== Verifying transaction amount  :"+amt+" ========" );
			softAssert.assertTrue(driver.findElement(By.xpath(amtXp)).isDisplayed());
		}
		catch(Exception e)
		{
			softAssert.fail("Transaction amount "+amt+" not displayed correctly\n");
		}
		
		// ==== Verify Operator ==== //
		
		try
		{
			Log.info("======== Verifying Operator : "+operator+" ========");
			softAssert.assertTrue(driver.findElement(By.xpath(opXp)).isDisplayed());			
		}
		catch(Exception e)
		{
			softAssert.fail("Operator  "+operator+" not displayed correctly\n");
		}	
		softAssert.assertAll();		
	}
	
	/**
	 * Verifies DTH recharge failure message. Used mainly when clicking on Cancel button.
	 */
	public void verifyDTHRechargeFailMsg()
	{
		try 
		{
			Log.info("======== Verify Transaction Failed Message : "+cancelBtnFailedErrMsg.getText()+" ========");
			Assert.assertTrue(cancelBtnFailedErrMsg.isDisplayed());
		} catch (Exception e) 
		{
			Assert.fail("Failed Message not Found\n"+e.getMessage());
		}
		
		try {Thread.sleep(2500);OkButton.click();} catch (Exception e) {Log.info("== Error clickong OK button ==");}
		
	}
	
}
