package wibmo.webapp.pagerepo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import library.Generic;
import library.Log;

import wibmo.app.testScripts.Guest_Checkout.BaseTest;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPPostReportPage used to verify the final status of a transaction.
 */
public class IAPPostReportPage {

/** The driver. */
public WebDriver driver;
	
	/**
	 * Instantiates a new IAP post report page.
	 *
	 * @param driver the driver
	 */
	public IAPPostReportPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	/** The wibmo txn ID. */
	@FindBy(xpath="//td[contains(text(),'Wibmo Txn ID :')]/../td[2]")
	private WebElement wibmoTxnID;
	
	/** The failure message. */
	@FindBy(xpath="//td[text()='FAILURE']")
	private WebElement failureMessage;
	
	/** The test again link. */
	@FindBy(xpath="//a[contains(text(),'Test again')]")
	private WebElement testAgainLink;
	
	/** The success message. */
	@FindBy(xpath="//td[text()='SUCCESS']")
	private WebElement successMessage;
	
	/** The User aborted message. */
	@FindBy(xpath="//td[text()='User Aborted' or text()='User cancelled']")
	private WebElement userAbortedMessage;
	
	/** The hash failed message. */
	@FindBy(xpath="//td[text()='MESSAGE HASH FAILED']")
	private WebElement hashFailedMessage;
	
	@FindBy(name="chargeUser")
	private WebElement chargeUserDropdown;
	
	@FindBy(xpath="(//input[@type='submit'])[last()]")
	private WebElement statusCheckSubmit;
	
	@FindBy(xpath="(//input[@type='submit'])[last()]")
	private WebElement dataPkpSubmit;
	
	@FindBy(tagName="body")
	private WebElement suf;
	
	
	/**
	 * Verifies Transaction failure status. 
	 */
	public void verifyFailure()	 //verify the 'failure' message by checking text displayed on the page
	{	
		waitForResult();
		try
		{
			Log.info("==== Verifying failure message : "+failureMessage.getText()+"====");
			Assert.assertTrue(failureMessage.isDisplayed(), "\n Failure message was not displayed \n");
			Log.info("==== Verifying Txn Id : "+wibmoTxnID.getText()+" ====");
		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Transaction Failure Message / Page is not displayed or taking too much time to load\n"+e.getMessage());
		}	
	}
	
	/**
	 * Verifies Transaction Success.
	 */
	public void verifySuccess()		//verify the 'success' message by checking text displayed on the page
	{		
		try
		{
			Log.info("==== Verifying success message : "+successMessage.getText()+"====");
			Assert.assertTrue(successMessage.isDisplayed(),"Success message was not displayed");
			Log.info("==== Verfiying Txn Id : "+wibmoTxnID.getText()+" ====");			
		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Success message not displayed\n"+e.getMessage());
		}	
	}
	
	/**
	 * Verifies user abort status.
	 */
	public void verifyUserAbort()		//verify the 'User Aborted' message by checking text displayed on the page
	{
		
		try
		{
			Log.info("==== Verifying User Abort message :"+userAbortedMessage.getText()+"====");
			Assert.assertTrue(userAbortedMessage.isDisplayed(),"User Aborted page was not displayed");	
		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"User abort message not found\n"+e.getMessage());
		}
	}
	
	/**
	 * Verifies hash failed status.
	 */
	public void verifyHashFailed()	
	{
		Log.info("==== Verifies Hash failure ====");
		try
		{
			Assert.assertTrue(hashFailedMessage.isDisplayed(),"Message Hash Failed page was not displayed");
		}
		catch(Exception e)
		{
			Assert.fail("Page is not displayed or taking too much time to load"+e.getMessage());
		}
	}
	
	
	/**
	 * Returns the Wibmo Id.
	 *
	 * @return the WibmoId String
	 */
	public String fetchwibmoid()
	{
		String txnId=wibmoTxnID.getText();
		Log.info("==== Verifying Txn Id : "+txnId+" ====");
		return txnId;
	}
	
	
	public void checkSUF(boolean chargeStatus,String checkList)
	{
		Log.info("==== Selecting Charge User Status : "+chargeStatus+" ====");		
		new Select(chargeUserDropdown).selectByValue(chargeStatus+"");
		
		checkSUF(checkList);
	}
	
	
	public void checkSUF(String checklist)
	{
		String parent= driver.getWindowHandle();
		
		Log.info("======== Clicking on Submit ========");
		statusCheckSubmit.click();
		
		for(String w:driver.getWindowHandles())
			if(!w.equals(parent))
				driver.switchTo().window(w);
		
		String sufContent=suf.getText();
		Log.info("======== Verifying SUF ========\n"+sufContent);
		
		Log.info("======== Verifying SUF fields ========");
		for(String f:checklist.split(","))
		{
			Log.info(f);
			Assert.assertTrue(Generic.containsIgnoreCase(sufContent, f), f+" not found in SUF\n");
		}	
		
		Log.info("======== Switching to main window ========");
		driver.switchTo().window(parent);
		
	}
	
	public void checkDataPickup(String checklist)
	{
		String parent= driver.getWindowHandle();
		
		Log.info("======== Clicking on Submit ========");
		dataPkpSubmit.click();
		
		for(String w:driver.getWindowHandles())
			if(!w.equals(parent))
				driver.switchTo().window(w);
		
		String sufContent=suf.getText();
		Log.info("======== Verifying SUF ========\n"+sufContent);
		
		if(checklist.contains(","))
			for(String check :checklist.split(","))
				Assert.assertTrue(sufContent.contains(check), check+" not found ");
		else
			Assert.assertTrue(sufContent.contains(checklist), checklist+" not found.");	
		
	}
	
	public void testAgain()
	{
		Log.info("======== Clicking on Test Again link ========");
		testAgainLink.click();
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
	
	public void waitForResult()
	{
		try
		{
			new WebDriverWait(driver, 120).until(ExpectedConditions.visibilityOf(dataPkpSubmit));
		}
		catch(Exception e)
		{
			Assert.fail("Page taking too much time to load\n"+e.getMessage());
		}
		
	}
	
}
