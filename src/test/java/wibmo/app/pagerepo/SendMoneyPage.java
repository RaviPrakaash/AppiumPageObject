package wibmo.app.pagerepo;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import library.Generic;

import org.hamcrest.text.IsEqualIgnoringWhiteSpace;
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
 * The Class SendMoneyPage used to enter the recipient details like name, mobile/email, amount etc.
 */
public class SendMoneyPage extends BasePage
{
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	private SoftAssert softAssert;
	
	@FindBy(id="title_text")
	private WebElement pageTitle;
	
	/** The send money mobile icon. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Mobile')]")
	private WebElement sendMoneyMobileIcon;
	
	/** The send money email icon. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Email')]")
	private WebElement sendMoneyEmailIcon;
	
	/** The 'New' tab used to navigate to recipient details page. */
	@iOSXCUITFindBy(iOSNsPredicate="name='New'")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'New')]")
	private WebElement newEntrySendMoney;
	
	/** The recipient name text field also acts as static field for Beneficiary name */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[1]")
	@AndroidFindBy(id="sendmoney_rec_name")  
	private WebElement nameTextField;
	
	/** The recipient phone or email txt field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[2]")
	@AndroidFindBy(id="sendmoney_phone_email")
	private WebElement phoneOrEmailTxtField;
	
	/** The Amount text field of send Money to Bank*/
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[3]")
	@AndroidFindBy(id="sendmoney_amount")
	private WebElement amtTxtField;
	
	/** The transaction description textField. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[4]")
	@AndroidFindBy(id="sendmoney_txtAmntDesc")
	private WebElement enterDescription;
	
	@FindBy(id="fee_discription")
	private WebElement feeApplicableTxt;
	
	/** The Continue Button. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and name in {'Send Money','Continue'}")
	@AndroidFindBy(id="button_send_money")
	private WebElement continueButton;
	
	/** The transaction status message. */
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'Funds have'")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@resource-id,'sm_status_value') or contains(@resource-id,'sm_status_title')]" )   // SendMoney to Mob/Email or Bank
	private WebElement statusMsg;
	
	/** The Ok button in Transaction Final page , not inherited from BasePage*/
	@iOSXCUITFindBy(accessibility="Ok")
	@AndroidFindBy(xpath="//*[@text='Ok' or @text='OK']") 
	private WebElement okBtn;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains ': 201'")
	@AndroidFindBy(id="sm_status_ref_value")
	private WebElement txnIdField;
	
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeImage' and (name contains 'IconSeal'  or name contains 'IconBank')'")  
	private WebElement statusImg; 
	
	@FindBy(xpath="//*[contains(@text,'Error Description')]/..//android.widget.TextView[last()]")
	private WebElement errDesc;
	
	/** The insufficient funds alert message checker. */
	@FindBy(id="alertTitle")
	private WebElement insufficientAlert;
	
	/** The insufficient funds message. */
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'not sufficient'")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'message')]")
	private WebElement insufficientMessage;
	
	/** The insufficient funds alert 'Add Money' button. */
	@iOSXCUITFindBy(accessibility="Add Money")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'button1')]") 
	private WebElement insuffAddMoneyBtn;
	
	/** The insufficient funds alert 'Cancel' button. */
	@iOSXCUITFindBy(accessibility="Cancel")
	@AndroidFindBy(xpath="//*[@text='Cancel']") 
	private WebElement insuffCancelBtn;	
	
	/** The invalid email/mobile error message. */
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Please'")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'message')]")
	private WebElement invalidMsg;
	
	/** The invalid email/mobile error message OK button. */
	@FindBy(xpath="//*[contains(@resource-id,'button2')]")
	private WebElement invalidMsgOkBtn;
	
	/** The Send Money Cancel button. */
	@iOSXCUITFindBy(accessibility="Cancel")
	@AndroidFindBy(id="button_cancel")
	private WebElement cancelButton;
	
	@iOSXCUITFindBy(iOSNsPredicate="(name beginswith 'Funds' or name='Close') and visible=true") //name='Close'
	private WebElement sendAlertChk;
	
	@iOSXCUITFindBy(iOSNsPredicate="(name beginswith 'Funds' or name='Close') and visible=true") //   accessibility='Close'
	private WebElement sendAlertCloseBtn;
	
	// ======== Bank Fields ======== //
	
	@AndroidFindBy(xpath="//*[@text='Add Beneficiary']")
	private WebElement addBeneficiaryTxt;
	
	@FindBy(id="beneficiary_account_no")
	private WebElement nomineeAccNoField;
	
	/** The account number TextField*/
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(id="bank_account_number")
	private WebElement accNoTxtField;
	
	/** The confirm account number TextField */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[2]")
	@AndroidFindBy(id="bank_confrim_account_number")
	private WebElement confirmAccNoTxtField;
	
	/** The IFSC TextField*/
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[3]")
	@AndroidFindBy(id="bank_ifsc_number")
	private WebElement ifscTxtField;
	
	/** Check between "no bank account" message and Beneficiary Account No. already present in Page */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'no_acc_added_text') or contains(@resource-id,'beneficiary_account_no')]")
	private List<WebElement> beneficiaryList;
	
	/** The Pagecheck used to check between Send Money Page / Send Money Page with Alert Title / Wallet Page with Balance displayed  */
	@AndroidFindBy(xpath="//*[contains(@resource-id,'sendmoney_amount') or contains(@resource-id,'alertTitle') or contains(@resource-id,'balance_va')]")
	@iOSXCUITFindBy(iOSNsPredicate="(name='IconAddMoney' or name beginswith 'Money sent' or name beginswith 'Insufficient') and visible=true")
	private WebElement pageChk;
	
	/** The beneficiary account number field used to reconfirm values*/
	@FindBy(id="sendmoney_account_number")
	private WebElement beneAccNumberField;
	
	/** The beneficiary name field*/
	@FindBy(id="sendmoney_rec_name")
	private WebElement beneNameField;
	
	/** The beneficiary ifsc field*/
	@FindBy(id="sendmoney_bankbranch_code")
	private WebElement beneIfscField;
	
	@FindBy(id="save_btn")
	private WebElement confirmBtn;
	
	
	
	/**
	 * Instantiates a new SendMoneyPage.
	 *
	 * @param driver the driver
	 */
	public SendMoneyPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this); 
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
	 * 
	 *  Enter values as name , recipientMobileEmail , amount .
	 *  Ini compatible
	 *  @see enterValues(data)
	 */
	public void enterValues(String...recipientDetails)
	{
		String data=",,"+String.join(",", recipientDetails);
		enterValues(data);		
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
		if(!(amtChk=amtTxtField.getText()).equals(amount))
		{
			if(!amtChk.isEmpty())amtTxtField.clear();
			amtTxtField.sendKeys(amount);
			Generic.hideKeyBoard(driver);
		}
		
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
		
		// === Send to Beneficiary flow if Beneficiary is present === //
		if(sendToBeneficiary(name,accNo,ifscCode,amount)) return; 
		// =================================== //
		
		Log.info("======== Clicking on Add Beneficiary ========");
		addBeneficiaryTxt.click();
		
		ManageBeneficiary mb=new ManageBeneficiary(driver);
		mb.enterBeneficiaryDetails(name,accNo,ifscCode);
		mb.clickConfirm();
		
		// --------------------------------------------- //
		
		
		//newEntrySendMoney.click(); 					// if(!sendBankFav(data)==data) return data; // fav flow returns modified data
		
//		Log.info("======== Account Holder Name: "+name+" ========"); // No need to click s
//		//if(!(nameChk=nameTextField.getText()).equals(name))
//		{
//			//if(!nameChk.isEmpty()) 	nameTextField.clear();
//			nameTextField.sendKeys(name);
//			//Generic.hideKeyBoard(driver);
//		}
//		
//		Log.info("======== Entering Account Number : "+accNo+" ========");
//		//if(!(accNoChk=accNoTxtField.getText()).equals(accNo))
//		{
//			//if(!accNoChk.isEmpty())accNoTxtField.clear();
//			accNoTxtField.sendKeys(accNo);
//			//Generic.hideKeyBoard(driver);
//		}
//		
//		Log.info("========Re - Entering Account Number : "+accNo+" ========");
//		confirmAccNoTxtField.sendKeys(accNo);		
//		Generic.hideKeyBoard(driver);
//		
//		Log.info("======== Entering IFSC Code : "+ifscCode+" ========");
//		//if(!(ifscCodeChk=ifscTxtField.getText()).equals(ifscCode))
//		{
//			//if(!ifscCodeChk.isEmpty())ifscTxtField.clear();
//			ifscTxtField.sendKeys(ifscCode);
//			Generic.hideKeyBoard(driver);
//		}
//		
//		Log.info("======== Entering Amount: "+amount+" ========");
//	//	if(!(amtChk=amntTextField.getText()).equals(amount))
//		{
//			//if(!amtChk.isEmpty())amntTextField.clear();
//			amtTxtField.sendKeys(amount);
//			Generic.hideKeyBoard(driver);
//		}
//		
//		//Generic.scroll(driver,"Continue");
//		//feeApplicableTxt.isDisplayed(); 	// GST T&C message
//		
//		Log.info("======== Cliking on Continue Button ========");
//		confirmBtn.click();	
//		
		//return data; fav flow
	}
	
	/**
	 * Clicks on the Add Beneficiary link
	 */
	public void addBeneficiary()
	{
		Log.info("======== Clicking on Add Beneficiary ========");
		addBeneficiaryTxt.click();
	}
	
	
	/**
	 *  Send to beneficiary if already present
	 * 
	 * @param values[] as beneficiaryName,beneficiaryAcctNo,ifscCode,amt
	 * @return false if beneficiary is not present in the benficiary list
	 */
	public boolean sendToBeneficiary(String...values) // Send to Beneficiary if available
	{
		int i=0;
		
		String name = values[i++],reChkBeneName="";
		String accNo = values[i++],reChkBeneAccNo;
		String ifscCode=values[i++],reChkBeneIfsc;
		String amount = values[i++];		
				
		boolean beneficiaryPresentInList=false;
		
		if(Generic.isIos(driver)) return false; // TBI
		
		waitOnProgressBarId(30);
		
		for(WebElement beneficiary : beneficiaryList)
			if(beneficiary.getText().contains(accNo))
			{
				Log.info("======== Clicking on Beneficiary with account number : "+accNo+" ========");
				beneficiary.click();
				beneficiaryPresentInList=true;
				break;
			}
		
		if(!beneficiaryPresentInList) 
			{
				Log.info("======== Beneficiary with account number "+accNo+" not found ========");
				return false;
			}
		
		// ==== Confirm Beneficiary Values ==== //
		Log.info(reChkBeneName=beneNameField.getText());
		Assert.assertEquals(reChkBeneName,name);		

		Log.info(reChkBeneAccNo=beneAccNumberField.getText());
		Assert.assertEquals(reChkBeneAccNo,accNo);		
	
		Log.info(reChkBeneIfsc=beneIfscField.getText());
		Assert.assertEquals(reChkBeneIfsc,ifscCode);		
		// ======================== //
		
		Log.info("======== Entering Amount : "+amount+" ========"); 		
		amtTxtField.sendKeys(amount);	
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Cliking on Continue Button ========");
		continueButton.click();	
		
		return true;
		
	}
	
	public void verifyNominee(String expectedNomineeAccNo)
	{
		String nomineeActual;
		
		Log.info("======== Verifying Nominee : "+(nomineeActual=nomineeAccNoField.getText())+" ========");
		Assert.assertEquals(nomineeActual, expectedNomineeAccNo, "Incorrect Nominee displayed");
	}
	
	/**
	 * 
	 *  
	 * @return number of Beneficiaries
	 */
	public int getBeneficiaryListSize()
	{
		int numberOfBeneficiaries;
		
		Log.info("======== Checking Number of Beneficiaries : "+(numberOfBeneficiaries=beneficiaryList.size())+" ========");
		return  numberOfBeneficiaries;
		
	}
	
	/**
	 *  Clicks on Navigate Back button if the current page is focused on Favorites Tab.
	 *  Used after W2A to Beneficiary.
	 */
	public void navigateBackOnFavorites()
	{
		if(Generic.isIos(driver)) return;
			
		Generic.wait(3); // Wait for Pageload
		
		if(Generic.checkTextInPageSource(driver, "Beneficiary").contains("Beneficiary"))
		{
			Log.info("======== Navigating back from Favorites ========");
			navigateLink.click();
		}
	}
	
	//public boolean chkBeneficiaryPresent(String name,String accNo,String ifsc) 
	 // Perform Detailed Check for multiple Beneficiaries in List
	{
		// if beneficiary chk then return ; // Pageload already done by previous method
		//for each Accno
			//if AccNo = accNo
				//for each BeneficiaryName
					//if(BeneficiaryName=name)
						// for each ifsc
							//if(ifsc==ifsc)
								//return true;
								
		//return false;
	}
	
	
	/**
	 * Verifies fund transfer successful message.
	 * 
	 * @return txnId
	 */
	public String verifyFundTransferSuccessMsg()
	{		 
		
		String txnId="";
		
		try
		{
			handleSendAlert();
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(okBtn));
			System.out.println("Ok Btn displayed ");
			
		}
		catch(Exception e)
		{
			Assert.fail("Fund transfer status message not found\n"+e.getMessage());
		}
		
//		if(Generic.isIos(driver))  // Image visible=true inconsistent
//		{
//			String verifySuccessImage;
//			Log.info("======== Veryfying success icon "+(verifySuccessImage=statusImg.getAttribute("name"))+" ========");
//			Assert.assertTrue(verifySuccessImage.contains("Success"), "Send Money not successful");				
//		}
		
		try 
		{
			String successMsg=statusMsg.getText();txnId=txnIdField.getText();
			Log.info("======== Verifying Fund Transfer successFul Message : "+successMsg+" ========");
			Log.info("======== Verifying Txn ID :"+txnId+"========");			
			Assert.assertTrue(Generic.containsIgnoreCase(successMsg, "success"),"Transaction was not successful\n");		
			
		} catch (Exception e) 
		{
			Assert.fail("Fund transfer status message not found\n"+e.getMessage());
		}
		Log.info("======== Clicking on OK button ========");
		okBtn.click();
		
		return txnId;
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
	
	
	public void verifyErrDesc(String expectedContent)
	{
		String errDesc;
		okBtn.isDisplayed();
		
		Log.info("======== Verifying Error Description : "+(errDesc=this.errDesc.getText())+"========");
		Assert.assertTrue(Generic.containsIgnoreCase(errDesc, expectedContent), "Invalid error description");
	}
	
	/**
	 *  Verifies Sorry App had error 
	 * 
	 * @param expectedContent
	 */
	public void verifyAppError(String expectedContent)
	{
		Generic.verifyToastContent(driver, expectedContent);
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
			softAssert.assertTrue(amtTxtField.isDisplayed(), "Amount text field not displayed\n");
			
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
		String msg;
		
		try
		{	
			Log.info("======== Verifying Insufficient Balance message : "+(msg=Generic.isAndroid(driver)? alertMessage.getText() : insufficientMessage.getText())+" ========");
			Assert.assertTrue(msg.contains("not sufficient"), "Insufficient Balance message not displayed \n");
		}
		catch(Exception e)
		{
			Assert.fail("Insufficient Balance message not displayed , Check Amount \n"+e.getMessage());
		}
		
		Log.info("======== Clicking on Add Money ========");
		insuffAddMoneyBtn.click();
	}

	/**
	 * Verifies balance displayed in the amount field of Send Money page after 'Add money due to Insufficient Balance' transaction.
	 * 
	 * 
	 */
	public void verifyBalAfterAdd() 
	{
		
		if(Generic.isIos(driver))
			verifyBalanceAfterAddIOS();
			
		Generic.wait(2);
		
		String xp="";		
		
		if(pageChk.getAttribute("resourceId").contains("balance")) 	// Initial Balance 0  flow => Resulting in Wallet Page after Transaction
		{
			AddSendPage asp=new AddSendPage(driver);
			double balanceAfterAdd=asp.verifyBalance();
			Assert.assertEquals(balanceAfterAdd, BaseTest.amtGreaterThanBal);
			return; 
		}
		
		if(pageChk.getAttribute("resourceId").contains("alertTitle"))	// Initial Balance greater than 0 flow => Resulting in Send Money page with or without Insuff alert 
			insuffCancelBtn.click();
		
		String amtInField=amtTxtField.getText();
		Log.info("======== Verifying amount field : "+amtInField+" ========");
		Assert.assertEquals(Double.parseDouble(amtInField),BaseTest.amtGreaterThanBal,0.05,"Balance not updated correctly\n");		
	}
	
	/**
	 *  Verifies balance displayed in the amount field of Send Money page after 'Add money due to Insufficient Balance' transaction. IOS
	 */
	public void verifyBalanceAfterAddIOS()
	{
		String chk=Generic.getAttribute(pageChk, "name");
		
		if(chk.contains("Add")) 	// Initial Balance 0  flow => Resulting in Wallet Page after Transaction 
		{
			AddSendPage asp=new AddSendPage(driver);
			double balanceAfterAdd=asp.verifyBalance();
			Assert.assertEquals(balanceAfterAdd, BaseTest.amtGreaterThanBal);		 
			return; 
		}
		
		if(chk.contains("Insufficient"))  // Initial Balance greater than 0 flow => Resulting in Send Money page with or without Insuff alert
			insuffCancelBtn.click();
		
		String amtInField=amtTxtField.getText();
		Log.info("======== Verifying amount field : "+amtInField+" ========");
		Assert.assertEquals(amtInField,BaseTest.amtGreaterThanBal+"","Balance not updated correctly\n");	
			
	}
	
	
	/**
	 *  Verifies Invalid email error message.Team Login
	 */
	public void verifyInvalidEmail()
	{
		String errMsg;
		try
		{
			okBtn.isDisplayed();
			errMsg=Generic.isAndroid(driver)?alertMessage.getText():invalidMsg.getText();			
			Log.info("======== Verifying Invalid email error message :"+errMsg+" ========");
			Assert.assertTrue(errMsg.toLowerCase().contains("email"),groupExecuteFailMsg()+"Invalid email message not found\n");
			
			Log.info("======== Clicking on OK button ========");
			okBtn.click();
		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Invalid Email alert message not found\n");
		}		
	}
	
	/**
	 * Verifies invalid mobile number error message.
	 * 
	 */
	public void verifyInvalidMobile()
	{
		String errMsg;
		try
		{
			okBtn.isDisplayed();
			errMsg=Generic.isAndroid(driver)?alertMessage.getText():invalidMsg.getText();
			Log.info("======== Verifying Invalid mobile error message :"+errMsg+" ========");
			Assert.assertTrue(errMsg.toLowerCase().contains("mobile") || errMsg.contains("phone"),groupExecuteFailMsg()+"Invalid mobile message not found\n");
			okBtn.click();
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
	 * 
	 * Handles intermittent alert occurring in Transaction Final Page
	 *  IOS Only
	 */
	public void handleSendAlert()
	{
		if(!Generic.isIos(driver)) return;
		
		try {sendAlertCloseBtn.click();}catch(Exception e) {Log.info("== Deposit Alert Delay ==");}
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
