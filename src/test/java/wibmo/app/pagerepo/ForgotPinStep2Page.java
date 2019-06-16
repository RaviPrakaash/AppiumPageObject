package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;

/**
 * The Class ForgotPinStep2Page used to answer the security question.
 */
public class ForgotPinStep2Page extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	private SoftAssert softAssert;
	
	/** The security question text. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeStaticText' and name contains '?'")
	@AndroidFindBy(id="main_sq_value")
	private WebElement securityQuestionText;			

	/** The security ans text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="main_secret_question_aedit")
	private WebElement securityAnsTextField;

	/** The submit button. */
	@iOSXCUITFindBy(accessibility="Submit")
	@AndroidFindBy(id="fl_btnSubmit")
	private WebElement submitButton;

	/** The record does nt match err msg. */
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Answer to'")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'message')]")
	private WebElement recordDoesNtMatchErrMsg;
	
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Please'")
	private WebElement errMsg;
	
	/** The err msg ok btn. */
	@iOSXCUITFindBy(accessibility="Ok")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'button2')]")
	private WebElement errMsgOkBtn;
	
	/** The ans accept check. */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'message') or contains(@resource-id,'fl_main_helo')]")
	private WebElement ansAcceptCheck;
	
	

	/**
	 * Instantiates a new ForgotPinStep2Page.
	 *
	 * @param driver the driver
	 */
	public ForgotPinStep2Page(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);	}
	
	/**
	 * Enters the answer for the given security question and clicks on submit.
	 *
	 * @param ans the ans
	 */
	public void answerSecurityQuestion(String ans)
	{
		Log.info("======== Answering Security Question : "+ans+" ========");
		 if(ans.length()>1) // Validate single blank space 
		 {
			 if(!securityAnsTextField.getText().isEmpty())
				 securityAnsTextField.clear();
			 
			 securityAnsTextField.sendKeys(ans);
		 }
	//	Generic.hideKeyBoard(driver);		
		submitButton.click();
	}
	
	/**
	 * Verifies the Security question text. Mainly used to check for the updated security question.
	 *
	 * @param question the question
	 * @param ans the ans
	 */
	public void verifySecurityQa(String question,String ans)
	{
		Generic.wait(2);
		try
		{
			String securityQa=securityQuestionText.getText();
			Log.info("======== Verifying new Security question : "+securityQa+" ========");
			Assert.assertEquals(securityQa,question,"Security question not updated correctly\n");
		}
		catch(Exception e)
		{
			Assert.fail("Security Question field not found\n"+e.getMessage());
		}
		
		// ==== Security Answer takes at least 5 minutes to get updated in the Database hence cannot be verified ==== //
		
		/*Log.info("======== Verifying new Security answer : "+ans+" ========");
		securityAnsTextField.sendKeys(ans);
		//Generic.hideKeyBoard(driver);		
		submitButton.click();		
		
		try
		{
			new WebDriverWait(driver,45).until(ExpectedConditions.visibilityOf(ansAcceptCheck));	
		}
		catch(Exception e)
		{
			softAssert.fail("Unable to verify Security answer \n"+e.getMessage());
		}
		if(ansAcceptCheck.getAttribute("resourceId").contains("message"))
			softAssert.fail("Security answer not updated correctly\n");
		
		softAssert.assertAll();*/		
	}
	
	/**
	 * Verifies for Wrong security pin error message.
	 */
	public void wrongSecurityAnsErrMsg()
	{			
			try 
			{
				String msg=recordDoesNtMatchErrMsg.getText();
				Log.info("======== Verifying Error message : "+msg+" ========"); // Verifying Security answer error msg
				Assert.assertTrue(msg.toLowerCase().contains("does not match"));
				
			} catch (Exception e) 
			{
				Assert.fail("Error message not displayed\n");
			}
			okBtn.click();
	}
	
	/**
	 * Verifies non acceptance of blank security answer.
	 */
	public void verifyBlankErrMsg()
	{
		String msg;
		Log.info("======== Verifying non accepyance of blank answer ========");
		try
		{
			if(Generic.isAndroid(driver))
				Assert.assertTrue(securityAnsTextField.isDisplayed(), "Blank Security Answer was accepted");
			else
			{
				Log.info("======== Validating error message "+(msg=errMsg.getText())+"========");
				Assert.assertTrue(msg.contains("enter a valid answer"), "Wrong error message diplayed");
				okBtn.click();
			}
		}
		catch(Exception e)
		{
			Assert.fail("Blank security answer accepted"+e.getMessage());
		}
		
	}
}
