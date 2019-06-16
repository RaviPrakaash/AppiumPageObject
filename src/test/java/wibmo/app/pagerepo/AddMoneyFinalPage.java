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

import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * The Class AddMoneyFinalPage used to ascertain the Final status of Add Money transaction.
 */
public class AddMoneyFinalPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The status msg. */
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'Funds have'")
	@AndroidFindBy(id="lm_status_title")
	private WebElement statusMsg;
	
	/** The from card msg. */
	@FindBy(id="lm_status_to")
	private WebElement fromCardMsg;
	
	/** The amount transferred. */
	@FindBy(id="lm_status_amt_value")
	private WebElement amountTransferred;
	
	/** The transaction status img. */
	@iOSXCUITFindBy(iOSNsPredicate="name contains 'IconSeal'") // IconSealSuccess 
	@AndroidFindBy(id="lm_status_image")
	private WebElement statusImg;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains ': 201'")
	@AndroidFindBy(id="lm_status_ref_value")
	private WebElement txnIdField;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'limit reached'")
	@AndroidFindBy(xpath="//*[contains(@text,'Description')]/../android.widget.TextView[last()]")
	private WebElement errDescMsg;
	
	/** The Ok button. */
	@iOSXCUITFindBy(accessibility="Ok")
	@AndroidFindBy(id="lm_status_btnOkay")
	private WebElement OkButton;
	
	/**  The page check element used to check for ITP flow. */
	@FindBy(xpath="//android.view.View[contains(@content-desc,'XXXX')] | //android.widget.Button[contains(@resource-id,'lm_status_btnOkay')]") // 3DS page or final page
	private WebElement pageCheck; // used for checking ITP
	
	/**  The final page check used to wait for the occurrence of transaction status page. */
	@iOSXCUITFindBy(accessibility="Ok")
	@AndroidFindBy(xpath="//android.widget.Button[contains(@content-desc,'OK') or contains(@content-desc,'Ok') or contains(@content-desc,'ok') or contains(@text,'OK') or contains(@text,'Ok') or contains(@text,'ok')]")
	private WebElement finalPageCheck;
	
	/** The i . */
	public static int i;	
	
	/**
	 * Instantiates AddMoneyFinalPage.
	 *
	 * @param driver the driver
	 */
	public AddMoneyFinalPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this); 
	}
	
	/**
	 * Waits and Verifies  for transaction success.
	 * @return txnId
	 */
	public String verifyTransactionSuccess()
	{
		Log.info("======== Verifying Transaction success ========");
		try 
		{
			new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOf(finalPageCheck));
		} 
		catch (Exception e1){Assert.fail("Transaction page taking too long to load or not found , stopping execution \n"+e1.getMessage());} 
		
		try 
		{
			String successMsg=statusMsg.getText(),txnId=txnIdField.getText();
			
			Log.info("======== Verifying Transaction success message : "+successMsg+" ========");
			Log.info("======== Verifying Txn ID :"+txnId+"========");
			Assert.assertTrue(Generic.containsIgnoreCase(successMsg, "success"), " Add money Transaction was not successful \n");			
			
			if(Generic.isIos(driver))  // IOS 
			{
				String verifySuccessImage;
				Log.info("======== Veryfying success icon "+(verifySuccessImage=statusImg.getAttribute("name"))+" ========");
				Assert.assertTrue(verifySuccessImage.contains("Success"), "Incorrect Success Image displayed");				
			}
			
			Generic.wait(5); // Ribbon Interference
			OkButton.click();
			
			return txnId;
			
		} 
		catch (Exception e) 
		{
			Assert.fail("Transaction was not completed \n"+e.getMessage()); 
			return "";
		}
	}
	
	/**
	 * Waits and Verifies for transaction failure.
	 * Clicks on ok button
	 */
	public void verifyTransactionFailure()
	{
		Log.info("======== Verifying Transaction Failure ========");
		try 
		{
			new WebDriverWait(driver,120).until(ExpectedConditions.visibilityOf(statusMsg));
		} 
		catch (Exception e1) 
		{
			Assert.fail("======== Transaction Report page taking too long to load or not found , stopping execution ========");
		}
		
		try 
		{
			String failMsg=statusMsg.getText();
			Log.info("======== Verifying Transaction failure message : "+failMsg+" ========");
			Assert.assertTrue(failMsg.contains("not"), " Add money Transaction failure message not found \n");			
			
			if(Generic.isIos(driver)) 
			{
				String verifyImage;
				Log.info("======== Verifying success icon "+(verifyImage=Generic.getAttribute(statusImg, "name"))+" ========");
				Assert.assertTrue(verifyImage.contains("Failure"), "Incorrect Failure Image displayed");				
			}
			
			OkButton.click(); 
		} 
		catch (Exception e) 
		{
			Assert.fail(" Transaction was not completed \n"+e.getMessage());
		}		
	}
	
	public void verifyLimitsMsg()
	{
		String descMsg;
		
		try
		{
			Log.info("======== Verifying Limits Error Message : "+(descMsg=errDescMsg.getText())+" ========");
			Assert.assertTrue(descMsg.contains("limit reached"), "Incorrect Limits error message");
			
			if(Generic.isIos(driver))
			{
				OkButton.click(); // Handle message Alert
				OkButton.click(); // Handle Cancel Alert
			}
		}
		catch(Exception e)
		{
			Assert.fail("Limits message not found\n"+e.getMessage());
		}
	}
	
	public void clickOk()
	{
		OkButton.click();
	}
	
	/**
	 * Verifies whether ITP flow has occurred or not.
	 */
	public void verifyITP()
	{
		Log.info("======== Verifying ITP ========");
		try
		{
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(pageCheck));
		}
		catch(Exception e)
		{
			Assert.fail(" ITP was not executed. \n Page taking too much time to load or Page not found, stopping execution \n"+e.getMessage());
		}		
		if(pageCheck.getAttribute("contentDescription").contains("XXXX"))
			Assert.fail("ITP was not executed\n");			
	}	
}
