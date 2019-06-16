package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import library.Log;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import library.Generic;
import wibmo.app.testScripts.Recent_Records.BaseTest;

/**
 * The Class RecentRecordsPage also known as Quick Repeat page, used to view and access recent recharge transactions.
 */
public class RecentTransactionsPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	@FindBy(id="history_txn_img")
	private List<WebElement> txnIcons;
	
	@FindBy(id="history_txnrow_narration")
	private List<WebElement> txnDescs;
	
	@FindBy(id="history_txnrow_date")
	private List<WebElement> txnDates;
	
	/** The transaction amount text field. */
	@FindBy(id="history_txnrow_amount")
	private List<WebElement> transactionAmts;
	
	/**
	 * Instantiates a new RecentTransactionsPage.
	 *
	 * @param driver the driver
	 */
	public RecentTransactionsPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Verifies Recent Transactions to load.
	 */
	public void waitForRecentTxns()
	{
		try
		{		
			Log.info("======== Waiting for Recent transactins to load ========");
			new  WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOfAllElements(txnIcons));
		}
		catch(Exception e)
		{
			Assert.fail("No Recent transactions found\n"+e.getMessage());
		}
	}
	
	/**
	 * Verifies Recent Transactions.
	 */
	public void verifyRecentTxns()
	{
		waitForRecentTxns();
		
		Log.info("======== Verifying Recent Transactions ========");
		try{
				Assert.assertTrue(txnIcons.size()>1, "Error in validating Transaction Icons");
				Assert.assertTrue(txnDescs.size()>1, "Error in validating Transaction Descriptions");
				Assert.assertTrue(txnDates.size()>1, "Error in validating Transaction Times");
				Assert.assertTrue(transactionAmts.size()>1, "Error in validating Transaction Amounts");
			}
		catch(Exception e)
			{
				Assert.fail("Error in validating Recent transactions"+e.getMessage());
			}		
	}	
	
	public void clickRecentTxn()
	{
		Log.info("======== Clicking on Recent Transaction ========");
		txnIcons.get(0).click();
	}
}
	
	