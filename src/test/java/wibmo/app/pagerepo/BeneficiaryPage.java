package wibmo.app.pagerepo;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

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

import wibmo.app.testScripts.SendMoney.BaseTest;

import library.Log;

/**
 * The Class BeneficiaryPage used to check an already added Beneficiary during send money
 * 
 */
public class BeneficiaryPage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	private SoftAssert softAssert;
	
	@FindBy(id="title_text")
	private WebElement pageTitle;
	
	/** Check between "no bank account" msessage and Acc. Number */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'no_acc_added_text') or contains(@resource-id,'beneficiary_account_no')]")
	private WebElement favAccNoChk;
	
	@FindBy(id="beneficiary_account_no")
	private WebElement favBeneficiaryName;
	
	@FindBy(id="beneficiary_ifsc")
	private WebElement favIfscCode;
	
	/** The send money mobile icon. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Mobile')]")
	private WebElement sendMoneyMobileIcon;
	
	/** The send money email icon. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Email')]")
	private WebElement sendMoneyEmailIcon;
	
	/** The 'New' tab used to navigate to recipient details page. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'New')]")
	private WebElement newEntrySendMoney;
	
	/** The recipient name text field. */
	@FindBy(id="sendmoney_rec_name")
	private WebElement nameTextField;
	
	/** The recipient phone or email txt field. */
	@FindBy(id="sendmoney_phone_email")
	private WebElement phoneOrEmailTxtField;
	
	/** The amount text field. */
	@FindBy(id="sendmoney_amount")
	private WebElement amntTextField;
	
	/** The transaction description textField. */
	@FindBy(id="sendmoney_txtAmntDesc")
	private WebElement enterDescription;
	
	/** The Continue Button. */
	@FindBy(id="button_send_money")
	private WebElement continueButton;
	
	/** The transaction status message. */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'sm_status_value') or contains(@resource-id,'sm_status_title')]" )   // SendMoney to Mob/Email or Bank
	private WebElement statusMsg;
	
	@FindBy(id="sm_status_ref_value")
	private WebElement txnIdField;
	
	/** The OK button after the transaction completes. */
	@FindBy(xpath="//android.widget.Button[contains(@text,'Ok') or contains(@text,'OK')] ")
	private WebElement okBtn;
	
	/** The insufficient funds alert message checker. */
	@FindBy(id="alertTitle")
	private WebElement insufficientAlert;
	
	/** The insufficient funds message. */
	@FindBy(xpath="//*[contains(@resource-id,'message')]")
	private WebElement insufficientMessage;
	
	/** The insufficient funds alert 'Add Money' button. */
	@FindBy(xpath="//*[contains(@resource-id,'button1')]") 
	private WebElement insuffAddMoneyBtn;
	
	/** The insufficient funds alert 'Cancel' button. */
	@FindBy(xpath="//*[@text='Cancel']") 
	private WebElement insuffCancelBtn;	
	
	/** The invalid email/mobile error message. */
	@FindBy(xpath="//*[contains(@resource-id,'message')]")
	private WebElement invalidMsg;
	
	/** The invalid email/mobile error message OK button. */
	@FindBy(xpath="//*[contains(@resource-id,'button2')]")
	private WebElement invalidMsgOkBtn;
	
	/** The Send Money Cancel button. */
	@FindBy(id="button_cancel")
	private WebElement cancelButton;
	
	// ======== Bank Fields ======== //
	
	@FindBy(id="sendmoney_account_number")
	private WebElement accNoTxtField;
	
	@FindBy(id="sendmoney_bankbranch_code")
	private WebElement ifscTxtField;
	
	/**
	 * Instantiates a new SendMoneyPage.
	 *
	 * @param driver the driver
	 */
	public BeneficiaryPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	
	public boolean verifyBeneficiaryDetails(String beneficiaryName,String beneficiaryAccNo,String beneficiaryifsc)
	{
		waitOnProgressBarId(45);
		
		String favAccNo,favName,favIfsc;		
		
		favAccNo=favAccNoChk.getText();
		Log.info("======== Checking Favorites :  "+favAccNo+"========");
		
		if(!favAccNo.contains(beneficiaryAccNo)) return false;
		
		System.out.println(favName=favBeneficiaryName.getText());		
		if(!favName.contains(beneficiaryName)) return false;
		
		System.out.println(favIfsc=favIfscCode.getText());
		if(!favIfsc.contains(beneficiaryifsc)) return false;
		
		return true;
	}
	
	public void enterBeneficiaryAmt(String beneficiaryName,String beneficiaryAccNo,String beneficiaryifsc,String amt)
	{
		String reChkFavName,reChkFavAccNo,reChkFavIfsc;
		
		Log.info("======== Reconfirming Values ========");
		Log.info(reChkFavName=nameTextField.getText());
		Assert.assertEquals(beneficiaryName, reChkFavName);		
		
		Log.info(reChkFavAccNo=accNoTxtField.getText());
		Assert.assertEquals(beneficiaryAccNo, reChkFavAccNo);		
		
		Log.info(reChkFavIfsc=ifscTxtField.getText());
		Assert.assertEquals(beneficiaryifsc,reChkFavIfsc);
		
		Log.info("======== Clicking on Continue button under Favorites ========");
		continueButton.click();
		
	}
	
	
	
	/**
	 * Send money through mobile.
	 */
	public void sendMoneyThroughMobile()
	{		
		Log.info("======== Clicking on New Link ========");
		newEntrySendMoney.click();
	}
	
	/**
	 * Send money through email.
	 */
	public void sendMoneyThroughEmail()
	{
		Log.info("======== Clicking on Send Money Email Icon ========");
		sendMoneyEmailIcon.click();
		
		Log.info("======== Clicking on New Link ========");
		newEntrySendMoney.click();
	}
	
	/**
	 * Enter recipient details like name,mobile/email and amount.
	 *
	 * @param data the data
	 */
	public void enterValues(String data)
	{
		String[] values = data.split(",");
		int i = 2;
		
		String name = values[i++],nameChk;
		String mobOrEmail = values[i++],mobEmailChk;
		String amount = values[i++],amtChk;
		String description = "TestSendMoney"; // Will be parameterized if necessary, since amt index is used by multiple methods
		
		
		
		if(BaseTest.amtGreaterThanBal>0.0) // Insufficient Funds scenario
		{
			amount=BaseTest.amtGreaterThanBal+"";
			Log.info("======== Amount greater than Balance :"+amount+" ,will be entered ========");
		}
		
		if(mobOrEmail.contains(":")) // Extract mobile/email if it is in a bundle  
			{				
				 if(mobOrEmail.split(":").length==3)
				 {
					 if(mobOrEmail.split(":")[0].contains("@"))
						mobOrEmail=mobOrEmail.split(":")[0];
					 else
						mobOrEmail=mobOrEmail.split(":")[2];
				 }					
				else
					mobOrEmail=mobOrEmail.split(":")[0];				
			}		
		
		Log.info("======== Clicking on New Link ========");
		newEntrySendMoney.click();
		
		Log.info("======== Entering Name: "+name+" ========"); // No need to click 
		if(!(nameChk=nameTextField.getText()).equals(name))
		{
			if(!nameChk.isEmpty())nameTextField.clear();
			nameTextField.sendKeys(name);
			Generic.hideKeyBoard(driver);
		}
		
		Log.info("======== Entering Phone No or Email: "+mobOrEmail+" ========");
		if(!(mobEmailChk=phoneOrEmailTxtField.getText()).equals(mobOrEmail))
		{
			if(!mobEmailChk.isEmpty())phoneOrEmailTxtField.clear();
			phoneOrEmailTxtField.sendKeys(mobOrEmail);
			Generic.hideKeyBoard(driver);
		}
		
		Log.info("======== Entering Amount: "+amount+" ========");
		if(!(amtChk=amntTextField.getText()).equals(amount))
		{
			if(!amtChk.isEmpty())amntTextField.clear();
			amntTextField.sendKeys(amount);
			Generic.hideKeyBoard(driver);
		}
		
		//Log.info("======== Entering Description: "+description+" ========");
		//enterDescription.sendKeys(description);		
		//Generic.hideKeyBoard(driver);
		
		Log.info("======== Cliking on Continue Button ========");
		continueButton.click();
	}
	
	public void enterBankValues(String data)
	{
		String[] values = data.split(",");
		int i = 2;
		
		String name = values[i++],nameChk;
		String accNo = values[i++],accNoChk;
		String ifscCode=values[i++],ifscCodeChk;
		String amount = values[i++],amtChk;
		String description = "TestSendMoneyBank"; // Will be parameterized if necessary, since amt index is used by multiple methods
		
		//if(!sendBankFav(data)==data) return data;
		if(sendBankFav(data)) return;
		
		Log.info("======== Clicking on New Tab ========");
		newEntrySendMoney.click(); // if(!sendBankFav(data)==data) return data; // fav flow returns modified data
		
		Log.info("======== Account Holder Name: "+name+" ========"); // No need to click s
		if(!(nameChk=nameTextField.getText()).equals(name))
		{
			if(!nameChk.isEmpty())nameTextField.clear();
			nameTextField.sendKeys(name);
			Generic.hideKeyBoard(driver);
		}
		
		Log.info("======== Entering Account Number : "+accNo+" ========");
		if(!(accNoChk=accNoTxtField.getText()).equals(accNo))
		{
			if(!accNoChk.isEmpty())accNoTxtField.clear();
			accNoTxtField.sendKeys(accNo);
			Generic.hideKeyBoard(driver);
		}
		
		Log.info("======== Entering IFSC Code : "+ifscCode+" ========");
		if(!(ifscCodeChk=ifscTxtField.getText()).equals(ifscCode))
		{
			if(!ifscCodeChk.isEmpty())ifscTxtField.clear();
			ifscTxtField.sendKeys(ifscCode);
			Generic.hideKeyBoard(driver);
		}
		
		Log.info("======== Entering Amount: "+amount+" ========");
		if(!(amtChk=amntTextField.getText()).equals(amount))
		{
			if(!amtChk.isEmpty())amntTextField.clear();
			amntTextField.sendKeys(amount);
			Generic.hideKeyBoard(driver);
		}
		
		Log.info("======== Cliking on Continue Button ========");
		continueButton.click();	
		
		//return data; fav flow
	}
	
	public boolean sendBankFav(String data)
	{
		String[] values = data.split(",");
		int i = 2;
		
		String name = values[i++],favName,reChkFavName;
		String accNo = values[i++],favAccNo,reChkFavAccNo;
		String ifscCode=values[i++],favIfsc,reChkFavIfsc;
		String amount = values[i++];		
		
		favAccNo=favAccNoChk.getText();
		if(!favAccNo.contains(accNo)) return false;
		
		favName=favBeneficiaryName.getText();
		if(!favName.contains(name)) return false;
		
		favIfsc=favIfscCode.getText();
		if(!favIfsc.contains(ifscCode)) return false;
		
		Log.info("======== Sending to Favorites. Clicking on Beneficiary name ========");
		favBeneficiaryName.click();
		
		Log.info("======== Re Checking Beneficiary values ========");
		
		Log.info(reChkFavName=nameTextField.getText());
		Assert.assertEquals(favName, reChkFavName);		
		
		Log.info(reChkFavAccNo=accNoTxtField.getText());
		Assert.assertEquals(favAccNo, reChkFavAccNo);		
		
		Log.info(reChkFavIfsc=ifscTxtField.getText());
		Assert.assertEquals(favIfsc,reChkFavIfsc);		
		
		Log.info("======== Entering Amount : "+amount+" ========"); 		
		amntTextField.sendKeys(amount);
		
		Log.info("======== Cliking on Continue Button ========");
		continueButton.click();	
		
		return true;
		
	}
	
	
	/**
	 * Verifies fund transfer successful message.
	 */
	public void verifyFundTransferSuccessMsg()
	{		 
		try
		{
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(okBtn));
		}
		catch(Exception e)
		{
			Assert.fail("Fund transfer status message not found\n"+e.getMessage());
		}
		
		try 
		{
			String successMsg=statusMsg.getText(),txnId=txnIdField.getText();
			Log.info("======== Verifying Fund Transfer successFul Message : "+successMsg+" ========");
			Log.info("======== Verifying Txn ID :"+txnId+"========");
			Assert.assertTrue(Generic.containsIgnoreCase(successMsg, "success"),"Transaction was not successful\n");
			
		} catch (Exception e) 
		{
			Assert.fail("Fund transfer status message not found\n"+e.getMessage());
		}
		Log.info("======== Clicking on OK button ========");
		okBtn.click();
	}
	
	public void verifyFundTransferFailureMsg()
	{
		try
		{
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(okBtn));
		}
		catch(Exception e)
		{
			Assert.fail("Fund transfer status message not found\n"+e.getMessage());			
		}
		
		try 
		{
			String failureMsg=statusMsg.getText(),txnId=txnIdField.getText();
			Log.info("======== Verifying Fund Transfer Failure Message : "+failureMsg+" ========");
			Log.info("======== Verifying Txn ID :"+txnId+"========");
			Assert.assertTrue(!Generic.containsIgnoreCase(failureMsg, "success"),"Expected Transaction failure did not occur\n");
			
		} catch (Exception e) 
		{
			Assert.fail("Fund transfer status message not found\n"+e.getMessage());
		}
		
		Log.info("======== Clicking on OK button ========");
		okBtn.click();		
	}

	/**
	 * Verifies Recipient fields for sending money.
	 */
	public void verifyFields() 
	{
		softAssert =new SoftAssert();
		
		Log.info("======== Clicking on New Link ========");
		newEntrySendMoney.click();
		
		Log.info("======== Verifying fields ========");		
		try 
		{
			Log.info("======== Verifying Name field ========");		
			softAssert.assertTrue(nameTextField.isDisplayed(), "Name text field not displayed\n");
			
			Generic.hideKeyBoard(driver);
			
			Log.info("======== Verifying Phone Email text field ========");
			softAssert.assertTrue(phoneOrEmailTxtField.isDisplayed(), "Phone / Email text field not displayed\n");		
			
			Log.info("======== Verifying Amount field  ========");
			softAssert.assertTrue(amntTextField.isDisplayed(), "Amount text field not displayed\n");
			
			Log.info("======== Verifying Description field  ========");
			softAssert.assertTrue(enterDescription.isDisplayed(), "Description field not displayed\n");
		}
		catch(Exception e)
		{
			softAssert.fail("Send Money fields not displayed correctly\n"+e.getMessage());
		}
		softAssert.assertAll();		
	}	

	/**
	 * Verifies the occurrence of Insufficient balance error message and clicks on Add Money button navigating to Add Money page.
	 */
	public void addMoneyOnInsufficient() 
	{		
		try
		{	
			Log.info("======== Verifying Insufficient Balance message : "+insufficientMessage.getText()+" ========");
			Assert.assertTrue(insufficientAlert.isDisplayed(), "Insufficient Balance message not displayed \n");
		}
		catch(Exception e)
		{
			Assert.fail("Insufficient Balance message not displayed , Check Amount \n"+e.getMessage());
		}
		
		Log.info("======== Clicking on Add Money ========");
		insuffAddMoneyBtn.click();
	}

	/**
	 * Verifies balance displayed in the amount field of Send Money page after add money transaction.
	 */
	public void verifyBalAfterAdd() 
	{
		
		Generic.wait(2);
		
		
		//Generic.hideKeyBoard(driver);
		
		String xp="//*[contains(@resource-id,'sendmoney_amount') or contains(@resource-id,'alertTitle') or contains(@resource-id,'balance_va')]";		
		
		if(driver.findElement(By.xpath(xp)).getAttribute("resourceId").contains("balance")) // Initial Balance 0  flow 
		{
			AddSendPage asp=new AddSendPage(driver);
			double balanceAfterAdd=asp.verifyBalance();
			Assert.assertEquals(balanceAfterAdd, BaseTest.amtGreaterThanBal);
			return; 
		}
		if(driver.findElement(By.xpath(xp)).getAttribute("resourceId").contains("alertTitle"))			
			insuffCancelBtn.click();
		
		String amtInField=amntTextField.getText();
		Log.info("======== Verifying amount field : "+amtInField+" ========");
		Assert.assertEquals(amtInField,BaseTest.amtGreaterThanBal+"","Balance not updated correctly\n");		
	}
	
	/**
	 *  Verifies Invalid email error message.
	 */
	public void verifyInvalidEmail()
	{
		try
		{
			String errMsg=invalidMsg.getText();
			Log.info("======== Verifying Invalid email error message :"+errMsg+" ========");
			Assert.assertTrue(errMsg.toLowerCase().contains("email"),groupExecuteFailMsg()+"Invalid email message not found\n");
			
			Log.info("======== Clicking on OK button ========");
			invalidMsgOkBtn.click();
		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Invalid Email alert message not found\n");
		}		
	}
	
	/**
	 * Verifies invalid mobile number error message.
	 */
	public void verifyInvalidMobile()
	{
		try
		{
			String errMsg=invalidMsg.getText();
			Log.info("======== Verifying Invalid mobile error message :"+errMsg+" ========");
			Assert.assertTrue(errMsg.toLowerCase().contains("mobile"),groupExecuteFailMsg()+"Invalid mobile message not found\n");
			invalidMsgOkBtn.click();
		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Invalid Mobile message not found\n");
		}
	}
	
	/**
	 * Cancels Send Money by clicking on cancel button.
	 */
	public void cancelSend()
	{
		Generic.hideKeyBoard(driver);
		Log.info("======== Clicking on Cancel button ========");
		cancelButton.click();
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
