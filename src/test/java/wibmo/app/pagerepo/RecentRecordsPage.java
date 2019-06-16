package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
public class RecentRecordsPage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	
	/** The transaction amount text field. */
	@FindBy(className="android.widget.EditText")
	private WebElement transactionAmountTextField;
	
	/** The pay now button. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'Pay')]")
	private WebElement payButton;
	
	/** The view plans link. */
	@FindBy(id="label_plan") 		 // android.view.View[contains(@content-desc,'View Plans')] to be updated after checking prepaid transaction record which is currently unavailable
	private WebElement viewPlansLink;
	
	/** The cut button used to force clear a textfield. */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'cut')]")
	private WebElement cutButton;
	
	
	
	/** The recharge link. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Bill Pay') or contains(@text,'Recharge')]")
	private WebElement rechargeLink;
	
	/** The connection type(prepaid/postpaid). */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'text1')]") //*[contains(@text,'paid')]
	private WebElement connectionType;
	
	@FindBy(xpath="//*[contains(@resource-id,'rcOperatorDesc') and contains(@text,'Prepaid')]")
	private WebElement prepaidRepeatEntry;
	
	@FindBy(xpath="//*[contains(@resource-id,'rcOperatorDesc') and contains(@text,'Postpaid')]")
	private WebElement postpaidRepeatEntry;
	
	/** The view plans close link. */
	//@FindBy(xpath="//android.view.View[contains(@content-desc,'Amount') or contains(@content-desc,'Close')]") // This may change in the next build, clicking on close will not close the page
	@FindBy(xpath="//*[contains(@content-desc,'Go back')]")
	private WebElement viewPlansCloseLink;
	
	
	/** The view plans page text. */
	@FindBy(id="planDesc") // className=android.view.View
	private WebElement viewPlansLocationText;	 //planDesc
	
	@FindBy(id="rcRepeat")
	private WebElement repeatBtn;
	
	@FindBy(id="main_btn_pay") //android.widget.Button[contains(@text,'Pay') or contains(@resource-id,'main_btn_pay')]
	private WebElement rechargePayScreenBtn;
		
	
	/**
	 * Instantiates a new RecentRecordsPage.
	 *
	 * @param driver the driver
	 */
	public RecentRecordsPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Verifies whether Recent Records are present or not.
	 */
	public void verifyRecords()
	{		
		Log.info("======== Verifying presence of records ========");		
		try 
		{
			Assert.assertTrue(repeatBtn.isDisplayed());
		} 
		catch (Exception e) 
		{
			Assert.fail(" No recent records found \n"+e.getMessage());
		}
	}
	
	/**
	 * Verifies whether the amount text field is editable or not.
	 */
	public void verifyAmountField()
	{
		verifyRecords();
		
		transactionAmountTextField.click();			
		try { Thread.sleep(2000);} catch (InterruptedException e) {}		
					
		Log.info("======== Entering sample amount into amount field  ========" );		
		transactionAmountTextField.sendKeys("123");				
		
		Log.info("======== Verifying Editable amount field ========");		
		Assert.assertTrue(transactionAmountTextField.getAttribute("contentDescription").contains("123"),"Amount text field not editable\n");		
	}
	
	/**
	 * Verifies the absence of View Plans link for a given Postpaid number present in the records.
	 *
	 * @param data the data
	 */
	public void verifyViewPlansPostpaid(String data)
	{
		String postpaidNumber=data.split(",")[2];
		//String maskedNumber=postpaidNumber.substring(0,2)+"XX"+postpaidNumber.charAt(4)+"XX"+postpaidNumber.substring(7);
		
		//verifyRecords();
		
		try 
		{
			/*String postpaidNumberXp="//*[contains(@text,"+postpaidNumber+")]";
			Log.info("======== Clicking on masked postpaid number : "+postpaidNumber+" ========");
			driver.findElement(By.xpath(postpaidNumberXp)).click();	*/
			
			postpaidRepeatEntry.click();
		} 
		catch (Exception e1) 
		{
			Log.info("======== Searching for "+postpaidNumber+" ========");			
			try { Generic.scroll(driver, postpaidNumber).click(); } catch (Exception e) { Assert.fail(postpaidNumber+" Number not found, check Subscriber Id \n"+e.getMessage());}
		}
		
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Verifying connection type : "+connectionType.getText()+" ========");
		if(connectionType.getText().contains("Pre"))
			Assert.fail(postpaidNumber+" is prepaid number , Please provide postpaid no.\n");		
		
		Log.info("======== Verifying absence of View Plans link ========");		
		Assert.assertFalse(Generic.checkTextInPageSource(driver, "View Plans").contains("View"),"View Plans Link displayed for Postpaid number");
		
		Log.info("======== Navigating Back to Quick Repeat Page ========");
		navigateLink.click();
	}
	
	/**
	 * Verifies the presence of View Plans link for a given prepaid number.
	 *
	 * @param data the data
	 */
	public void verifyViewPlansPrepaid(String data)
	{
		String prepaidNumber=data.split(",")[2];
		//String maskedNumber=prepaidNumber.substring(0,2)+"XX"+prepaidNumber.charAt(4)+"XX"+prepaidNumber.substring(7);
		
		//verifyRecords();
		
		try 
		{
			//String prepaidNumberXp="//*[contains(@text,"+prepaidNumber+")]";
			//Log.info("======== Clicking on prepaid number : "+prepaidNumber+" ========");
			//driver.findElement(By.xpath(prepaidNumberXp)).click();			
			
			Log.info("======== Clicking on Prepaid Entry ========");
			prepaidRepeatEntry.click();
		} 
		catch (Exception e1) 
		{
			Log.info("======== Searching for "+prepaidNumber+" ========");			
			try { Generic.scroll(driver, prepaidNumber).click(); } catch (Exception e) { Assert.fail(" Number not found, check Subscriber Id \n"+e.getMessage());}
		}
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Verifying connection type : "+connectionType.getText()+" ========");
		
		if(connectionType.getText().contains("Post"))
			Assert.fail(prepaidNumber+" is a Postpaid number , Please provide prepaid no.\n");
		
				
		Log.info("======== Verifying presence of View Plans link ========");
		
		try
		{
			Assert.assertTrue(viewPlansLink.isDisplayed());
		}
		catch(Exception e)
		{
			Assert.fail("View Plans link is not displayed for the given prepaid number \n");
		}		
			
	}
	
	/**
	 * Verifies whether view plans page is displayed or not after clicking on the View Plans link for a Prepaid Entry.
	 *
	 * @param data the data
	 */
	public void verifyViewPlansPage(String data)
	{
		verifyViewPlansPrepaid(data);
		
		Log.info("======== Clicking on View Plans link ========");
		viewPlansLink.click();
		
		Log.info("======== Verifying View Plans page ========");		
		try 
		{
			Assert.assertTrue(viewPlansLocationText.isDisplayed(), " View Plans page not displayed correctly\n");
			Assert.assertTrue(pageTitle.getText().contains("View"), pageTitle.getText()+" displayed instead of View Plans");
		} 
		catch (Exception e) 
		{
			Assert.fail("View Plans page not displayed \n");
		}
		
		Log.info("======== Verifying Closing of View Plans page ========");	
		navigateLink.click();
		
		try{viewPlansLink.isDisplayed();}catch(Exception e){Assert.fail("Error in closing View plans page "+e.getMessage());}
		
		Log.info("======== Navigating Back to Quick Repeat page ========");
		navigateLink.click(); // Goto recent records page
	}
	
	/**
	 * Verify pay now button enable.
	 */
	public void verifyPayNowButtonEnable()
	{
		verifyRecords();
		
		Log.info("======== Clicking on transaction record ========");
		transactionAmountTextField.click();	
		Generic.hideKeyBoard(driver);
		payButton.click();
		
		Log.info("======== Verifying Pay Now button enable, Please wait ========");
		
		Generic.wait(4);
		try
		{			
			payButton.isDisplayed();
		}
		catch(Exception e)
		{
			Log.info("======== Pay now button Enabled ========");
			return;
		}		
		Assert.fail(" Pay Now button was disabled \n");		
	}
	
	/**
	 * Verify pay now button disable.
	 * @deprecated
	 */
	public void verifyPayNowButtonDisable()
	{
//		verifyRecords();
//		
//		Log.info("======== Clicking on transaction record ========");
//		transactionAmountTextField.click();
//		try { Thread.sleep(3000);} catch (InterruptedException e) {}		
//		
//		Log.info("======== Clearing text field ========");	
//		
//		TouchAction action = new TouchAction((AndroidDriver)driver);
//		action.longPress(transactionAmountTextField).perform();			
//		try { Thread.sleep(3000);} catch (InterruptedException e) {}
//			
//		cutButton.click();			
//		Generic.wait(3);
//		
//		payButton.click();
//		
//		Log.info("======== Verifying Pay Now button disable ========");		
//		try
//		{
//			Assert.assertTrue(payButton.isDisplayed(),"Pay Now button was enabled \n");
//		}
//		catch(Exception e)
//		{
//			Assert.fail("Pay now button was enabled \n"+e.getMessage());
//		}		
	}
	
	/**
	 * Only the first 5 digits should be accepted by the amount field when entering an amount greater than 5 digits.
	 *
	 * @param data the data
	 */
	public void verify5DigitAmt(String data)
	{
		String amt=data.split(",")[2];
		
		verifyRecords();
		
		transactionAmountTextField.click();		
		Generic.wait(2);
		
		Log.info("======== Entering amount :"+amt+" ========");
		transactionAmountTextField.clear();
		transactionAmountTextField.sendKeys(amt);
		
		Log.info("======== Verifying for 5 digits entered ========");
		Assert.assertEquals(transactionAmountTextField.getAttribute("contentDescription").length(), 5,groupExecuteFailMsg()+ " 5 digits not accepted correctly \n");		
	}
	
	/**
	 * Enters the recharge  amount into the existing record and clicks on pay now button.
	 *
	 * @param data the data
	 */
	public void enterAmountClickPayNow(String data)
	{
		String amt=data.split(",")[2];
		
		verifyRecords();
		
		transactionAmountTextField.click();		
		try { Thread.sleep(2000);} catch (InterruptedException e) {}
		
		Log.info("======== Entering amount :"+amt+" ========");
		transactionAmountTextField.sendKeys(amt);
		
		payButton.click();		
	}
	
	public void clickRepeat()
	{
		Log.info("======== Clicking on Repeat button ========");
		repeatBtn.click();		
	}
	
	public void verifyRepeat()
	{
		Log.info("======== Verifying Payment page ========");
		try
		{																														//if(true)return;
			Assert.assertTrue(rechargePayScreenBtn.isDisplayed(), "Recharge Pay Screen not displayed");						
		}
		catch(Exception e)				
		{
			Assert.fail("Recharge Pay Screen was not displayed"+e.getMessage());		
		}		
	}
	
	/**
	 * Navigates to Recharge Page.
	 */
	public void gotoRecharge()
	{
		Log.info("======== Navigating to Recharge page ========");
		navigateLink.click();
		Generic.wait(3);
		rechargeLink.click();		
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
