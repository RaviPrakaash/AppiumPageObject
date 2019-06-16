package wibmo.app.pagerepo;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import library.Generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import wibmo.app.testScripts.SendMoney.BaseTest;

import library.Log;

/**
 * The Class UnclaimedFundsPage used to claim the amount sent to the current user before registration.
 */
public class UnclaimedFundsPage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The page title. */
	@FindBy(id="title_text")
	private WebElement pageTitle;
	
	@FindBy(id="history_txnrow_from_value")
	private WebElement senderNameField;
	
	/** The transaction description. */
	@FindBy(id="history_txnrow_from_value")
	private WebElement transactionDescription;
	
	/** The claim code textfield. */
	@FindBy(id="lm_mkqr_ccode_value")
	private WebElement claimCodeTextfield;
	
	/** The claim button. */
	@FindBy(id="lm_cfBtn")
	private WebElement claimButton;
	
	
	
	
	
	/**
	 * Instantiates a new UnclaimedFundsPage.
	 *
	 * @param driver the driver
	 */
	public UnclaimedFundsPage(WebDriver driver)
	{
		super(driver);
		this.driver= driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this); 
	}
	
	/**
	 * Verifies the presence of Unclaimed funds page. Sets static field SendMoney.BaseTest.checkUnclaimed to false after checking the page.
	 * 
	 * 
	 */
	public void verifyUnclaimedFundsPage()
	{
		Log.info("======== Verifying Unclaimed funds page ========");
		
		try
		{			
			new WebDriverWait(driver,45).until(ExpectedConditions.visibilityOf(senderNameField));
			Assert.assertTrue(pageTitle.getText().contains("Unclaimed"),groupExecuteFailMsg()+"Unclaimed Funds page not displayed correctly\n");
		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Unclaimed Funds page not found for the given mobile number \n"+e.getMessage());
		}
		finally
		{
			BaseTest.checkUnclaimed=false; 
		}
	}
	
	/**
	 * Verify the presence of claim code prompt.
	 */
	public void verifyClaimCodePrompt()
	{
			
		Log.info("======== Verifying Claim Code prompt ========");
		try
		{
			Assert.assertTrue(claimCodeTextfield.isDisplayed(),groupExecuteFailMsg()+"Claim code prompt not found \n");
		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Claim Code prompt not found \n"+e.getMessage());
		}		
	}
	
	public void gotoClaimFunds()
	{
		Log.info("======== Navigating to Claim Funds Page ========");
		transactionDescription.click();
	}
	
	
	
	/**
	 * Enters claim code and clicks on Claim button.
	 *
	 * @param code the code
	 */
	public void enterClaimCode(String code)
	{
		
		Log.info("======== Entering Claim Code : "+code+" ========");
		if(claimCodeTextfield.getText().matches("\\d{6}"))
			claimCodeTextfield.clear();
		claimCodeTextfield.sendKeys(code);
		
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Clicking on Claim button ========");
		claimButton.click();	
	}
	
	
	
	
	
	/**
	 * Verifies the non acceptance of blank claim code.
	 */
	public void verifyBlankClaimCode()
	{
				
		Log.info("======== Entering Blank Code ========"); // Not entering value
		claimCodeTextfield.clear();
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Clicking on Claim button ========");
		claimButton.click();
		Generic.wait(2);
		
		try
		{
			Assert.assertTrue(claimButton.isDisplayed(),groupExecuteFailMsg()+"Blank claim code accepted\n" );
		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Blank claim code was accepted\n"+e.getMessage());
		}		
	}

	/**
	 * Verifies the non acceptance of incorrect claim code.
	 */
	public void verifyIncorrectCode() 
	{
		Generic.wait(5); // Wait for the acceptance of claim code
		Log.info("======== Verifying Incorrect Claim code ========");
		Assert.assertTrue(pageTitle.getText().toLowerCase().contains("claim"), groupExecuteFailMsg()+"Incorrect Claim code was accepted\n");		
	}
	
	public void navigateBack()
	{
		Log.info("======== Navigating back to Unclaimed funds list ========");
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
