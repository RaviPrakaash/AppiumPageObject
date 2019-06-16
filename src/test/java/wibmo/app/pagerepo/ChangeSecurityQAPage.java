package wibmo.app.pagerepo;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.time.Duration;
import java.util.List;
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

/**
 * The Class ChangeSecurityQAPage.
 */
public class ChangeSecurityQAPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The security question dropdown. */
	
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="secret_question_spinner_v2")
	private WebElement securityQuestionDropdown;
	
	/** The selected question. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'text1')]") 
	private WebElement selectedQuestion;
	
	/** The security answer textfield. */
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'enter answer'")
	@AndroidFindBy(id="main_secret_question_aedit")
	private WebElement securityAnswerTextfield;
	
	/** The custom question textfield. */
	@FindBy(id="main_secret_question_qedit")
	private WebElement customQuestionTextfield;
	
	/** The secure pin textfield. */
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(id="main_pwd_edit")
	private WebElement securePinTextfield;
	
	/** The question list. */
	
	@FindBy(xpath="//*[contains(@resource-id,'text1')]") 
	private List<WebElement> questionList;
	
	@iOSXCUITFindBy(className="XCUIElementTypePickerWheel")
	private WebElement questionPicker;

	/** The update button. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeButton[-1]")
	@AndroidFindBy(id="main_btnSave")
	private WebElement updateButton;
	
	@FindBy(xpath="//*[@text='Settings']")
	private WebElement updateChk;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains [ci]'Success'")
	private WebElement updateSuccessMsgTitle;
	
	@iOSXCUITFindBy(accessibility="Ok")
	private WebElement updateOkBtn;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'updated'")
	private WebElement updateSuccessMsgTxt;
	
	/**
	 * Instantiates a new change security QA page.
	 *
	 * @param driver the driver
	 */
	public ChangeSecurityQAPage(WebDriver driver)
	{
		this.driver= driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Changes the security question based on the security index passed in the testData.
	 * If index is 9 it enters the custom question present at the end of testData csv.
	 * Verifies successful operation based on the occurrence of the Settings Page.
	 * Returns the new Security Question text.
	 * @param data the data
	 * @return the string
	 */
	public String changeSecurityQa(String data)	
	{	
		int i=1;
		String[] values=data.split(",");
		String securePin=values[i++],index=values[i++],ans=values[i++];
		String sQuestion, updateMsgSuccess="";
		
		if(index.equals("0")) index="1";			
		
		securityQuestionDropdown.click();
		
		Log.info("======== Selecting security question with index "+index+" ========");		
		
		Generic.wait(1); // Wait for Question List to pen.
		
		if(Generic.isIos(driver))
			questionPicker.sendKeys("With which company did you start your career?");
		else	
			questionList.get(Integer.parseInt(index)).click();
		
		Generic.wait(2);
		
		sQuestion=selectedQuestion.getText();
		Log.info("======== Security Question selected : "+sQuestion+" ========");
		
		if(index.equals("9") && values.length==5)
		{
			String customQuestion=values[i];
			Log.info("======== Entering Custom Question : "+customQuestion+" ========");
			customQuestionTextfield.sendKeys(customQuestion);	
			sQuestion=customQuestion;
		}
		
		Log.info("======== Entering Security Answer : "+ans+" ========");
		securityAnswerTextfield.sendKeys(ans);
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Entering Secure Pin ========");
		securePinTextfield.sendKeys(securePin);
		Generic.hideKeyBoard(driver);
		
		Generic.wait(1); // Wait for hidekeyboard
		Log.info("======== Clicking on Update button ========");
		updateButton.click();
		
		try
		{
			 if(Generic.isIos(driver)) // IOS
			 {
				 Log.info("======== Validating Update status : "+(updateMsgSuccess+=updateSuccessMsgTitle.getText())+' '+(updateMsgSuccess+=updateSuccessMsgTxt.getText()));
				 Assert.assertTrue(updateMsgSuccess.contains("Success"), "Update Status title incorrect");
				 Assert.assertTrue(updateMsgSuccess.contains("updated"), "Update Status message incorrect");
				 
				 Log.info("======== Clicking on Update OK Button ========");
				 updateOkBtn.click();				 
			 }
			 else	// Android
				 new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(updateChk));
		}
		catch(Exception e)
		{
			Assert.fail("Security QA not changed successfully\n"+e.getMessage());
		}	
		
		Generic.navigateBack(driver);
		return sQuestion;		
	}
	
	

}
