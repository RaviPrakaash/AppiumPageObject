package wibmo.app.pagerepo;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import library.Generic;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
//import org.testng.asserts.SoftAssert;
//import wibmo.app.testScripts.SendMoney.BaseTest;
import library.Log;

/**
 * The Class SendMoneyPage used to enter the recipient details like name, mobile/email, amount etc.
 */
public class MvisaPage 
{
	
	/** The driver. */
	private WebDriver driver;	
	
	/** The send money email icon. */
	@iOSXCUITFindBy(accessibility="Type Merchant ID")
	@AndroidFindBy(xpath="//*[@text='Type Merchant ID']") 
	private WebElement mVisaIdTab;
	
	/** The merchant Id text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="mvisa_merchant_id")
	private WebElement merchantIdTxtField;	
	
	/** The mvisa id textfield */
	@FindBy(id="value_mvisa_id_manual")
	private WebElement mvisaIdField;
	
	/** The recipient phone or email txt field. */
	@iOSXCUITFindBy(accessibility="Continue")
	@AndroidFindBy(id="main_btnContinue")
	private WebElement continueBtn;
	
	/** The transaction status  field. */	
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@resource-id,'m_status_title') or contains(@resource-id,'footer_failed')]")
	private List<WebElement> transactionChecker;
	
	/** Used to check the status of the transaction	 */
	@iOSXCUITFindBy(iOSNsPredicate="name contains 'IconSeal'")
	private WebElement txnChk;
	
	/** Mvisa transaction fail message */
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'could not be'")
	private WebElement txnFailMsg;
	
	/** Transaction id field */
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith ': 201'")
	@AndroidFindBy(id="sm_status_ref_value")
	private WebElement txnIdField;	
	
	/** The OK button after the transaction completes. */
	@iOSXCUITFindBy(accessibility="Ok")
	@AndroidFindBy(xpath="//android.widget.Button[contains(@text,'Ok') or contains(@text,'OK') or contains(@text,'ok')] ")
	private WebElement okBtn;
	
	// --------------------------- mVisa Amount Page --------------------------- //
	/** The amount textfield. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="input_mvisa_amount")
	private WebElement amtTxtField;
	
	/** Merchant Reference Id field */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeStaticText[`name contains \":\"`][-1]") // ` is not same as single quote , Class Chain syntax 
	@AndroidFindBy(id="sm_status_mref_value")
	private WebElement merRefIdField;	
	
	@FindBy(id="input_mvisa_tip_amount")
	private WebElement tipField;
	
	@FindBy(id="value_mvisa_pid")
	private WebElement billIdField;
	
	@FindBy(id="value_mvisa_sid")
	private WebElement refIdField;	
	
	/** The more button which opens the More Options. */
	@FindBy(xpath="//android.widget.ImageView[contains(@content-desc,'More options')]")
	private WebElement moreButton;
	
	@FindBy(xpath="//*[@text='Add Tip']") 
	private WebElement addTipMoreLink;
	
	/*@FindBy(name="Add Bill ID")
	private WebElement addBillIdMoreLink;
	
	@FindBy(name="Add Ref ID")
	private WebElement addRefIdMoreLink;	*/
	
	@FindBy(className="android.widget.EditText")
	private WebElement dialogueTextField;
	
	@FindBy(xpath="//android.widget.Button[contains(@text,'Ok') or contains(@text,'OK')]")
	private WebElement dialogueOkBtn;

	/** The Select Card  button */
	@iOSXCUITFindBy(accessibility="Select Card")
	@AndroidFindBy(id="main_btnPay")
	private WebElement payBtn;
	
	/**
	 * Instantiates a new SendMoneyPage.
	 *
	 * @param driver the driver
	 */
	public MvisaPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 *  Navigates to Merchant ID tab
	 */
	public void gotoMvisaId()
	{
		Log.info("======== Clicking on mvisa ID tab ========");
		mVisaIdTab.click();
	}
	
	/**
	*	Enters the Merchant ID and clicks on Continue button		
	*
	* @param the merchantId
	*/
	public void enterMerchantId(String merchantId)
	{
		gotoMvisaId();
		
		Log.info("======== Entering Merchant Id : "+merchantId+" ========");
		
		if(merchantIdTxtField.getText().matches("\\d+"))
			merchantIdTxtField.clear();
		
		merchantIdTxtField.sendKeys(merchantId);	
		
		Log.info("======== Clicking on Continue ========");
		continueBtn.click();
	}
	
	/**
	 * Enters the Amount and clicks on Select Card button 
	 * 
	 * @param amt
	 */
	public void enterAmt(String amt)
	{
		Log.info("======== Entering amount : "+amt+" ========");
		
		//if(!amtTxtField.getText().isEmpty()) Code on Manual Observation
			//amtTxtField.clear();
		
		amtTxtField.sendKeys(amt);
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Clicking on Pay button ========");
		payBtn.click();		
	}
	
	/**
	 * Enters amt with billdetails
	 *
	 * 
	 * @param amt the amt
	 * @param merchantId the merchant id
	 * 
	 * @return updated amount as amount+tip
	 */
	public String enterAmtWithBilldetails(String amt,String merchantId)
	{
		
		Log.info("======== Entering amount : "+amt+" ========");		
		amtTxtField.sendKeys(amt);
		
		amt=verifyAdditionalDetails(merchantId)+Double.parseDouble(amt)+"";
		
		Log.info("======== Clicking on Pay button ========");
		Generic.hideKeyBoard(driver);
		payBtn.click();	
		
		Log.info("======== Amount value updated with tip, current amount value : "+amt+" ========");
		return amt;
	}
	
	/**
	 * 	 Verifies MVisa Transaction Success
	*/		
	public void verifyTransactionSuccess()
	{
		String txnResult;
		try
		{
			Log.info("======== Verifying Transaction Success ========");
			
			if(Generic.isAndroid(driver))
			{
				if(transactionChecker.size()>1)
					Assert.fail("Transaction failed\n");
			}
			else
				Assert.assertTrue((txnResult=txnChk.getAttribute("name")).contains("Success"), "Transaction failed . "+txnResult);
			
		}
		catch(Exception e)
		{
			Assert.fail("Transaction Status not found\n"+e.getMessage()); e.printStackTrace();
		}
		
		verifyTransactionDetails();
		
		okBtn.click();
	}

	/**
	 *  Verifies for MVisa Transaction Failure 
	 *  
	 */
	public void verifyTransactionFailure()
	{
		String failMsg,txnResult;
		
		try
		{
			Log.info("======== Verifying Transaction Failure ========");
			
			if(Generic.isAndroid(driver))	
			{
				if(transactionChecker.size()==1) 
					Assert.fail("Transaction Status not defined\n");
				
				failMsg=transactionChecker.get(1).getText();
			}
			else // ios
			{
				Assert.assertTrue((txnResult=txnChk.getAttribute("name")).contains("Failure"), "Transaction failed . "+txnResult);
				failMsg=txnFailMsg.getText();
			}	
		Log.info("======== Verifying Fail Indicator message : "+failMsg+" ========");	
		}
		catch(Exception e)
		{
			Assert.fail("Transaction Status not found\n");
		}
		
		verifyTransactionDetails();
		
		okBtn.click();
	}
	
	/** 	
	 * Verifies the transaction details displayed in the MVISA Transaction Final Page 
	*/
	public void verifyTransactionDetails()
	{
		try
		{
			Log.info("======== Verifying transaction Details ========");
			String txnId=txnIdField.getText(),merRefId=merRefIdField.getText();
			
			Log.info("======== Verifying Txn ID :"+txnId+"========");
			Log.info("======== Verifying Merchant Ref ID :"+merRefId+"========");
		}
		catch(Exception e)
		{
			Assert.fail("Error in verifying transaction details\n"+e.getMessage());e.printStackTrace();
		}

	}
	
	/**
	 * Verify additional details.
	 *
	 * @param merchantId the merchant id
	 * @return 
	 * 
	 */
	public double verifyAdditionalDetails(String merchantId)
	{
		double tipAdded;
		verifyMerchantId(merchantId);
		
		tipAdded=verifyTipPercent(addTipPercentage());
		
		verifyBillId(addBillId());
		
		verifyRefId(addRefId());
		
		return tipAdded;
		
	}
	
	public void verifyMerchantId(String merchantId)
	{
		String mVisaIdField=mvisaIdField.getText();
		
		Log.info("======== Verifying Merchant Id : "+mVisaIdField+" ========");
		Assert.assertTrue(mVisaIdField.contains(merchantId),merchantId+" not found in field "+mVisaIdField);
	}
	
	public double verifyTipPercent(String tipPercent)
	{
		double tipPercentDisplayed=Double.parseDouble(tipField.getText());
		double tipPercentExpected=Double.parseDouble(tipPercent);
		double amt=Double.parseDouble(amtTxtField.getText());		
		
		Log.info("======== Verifying Tip Percent : "+tipPercentDisplayed+" ========");
		Assert.assertEquals(tipPercentDisplayed, 0.01*tipPercentExpected*amt,0.001,"Tip Percent not displayed correctly ");	
		
		return tipPercentDisplayed;
	}
	
	public void verifyBillId(String billId)
	{	
		String billIdDisplayed=billIdField.getText();
		
		Log.info("======== Verifying Bill Id displayed : "+billIdDisplayed+" ========");
		Assert.assertEquals(billIdDisplayed, billId,"Bill Id entered does not match\n");		
	}
	
	public void verifyRefId(String refId)
	{	
		String refIdDisplayed=refIdField.getText();
		
		Log.info("======== Verfiying Ref Id displayed : "+refIdDisplayed+" ========");
		Assert.assertEquals(refIdDisplayed, refId,"Ref Id entered does not match\n");
	}
	
	public String addTipPercentage()
	{
		String tipPercent=new Random().nextInt(99)+"%";		
		if(tipPercent.charAt(0)=='0') tipPercent="1%";
		
		Log.info("======== Adding Tip Percent : "+tipPercent+" ========");
		moreButton.click();
		addTipMoreLink.click();
		dialogueTextField.sendKeys(tipPercent);
		clickDialogueOK();
		
		return tipPercent.substring(0,tipPercent.length()-1);// remove % symbol		
	}
	
	public String addBillId()
	{
		/*String billId=Generic.generateAlphaNumeric();
		
		Log.info("======== Entering BillId : "+billId+" ========");
		moreButton.click();
		addBillIdMoreLink.click();
		dialogueTextField.sendKeys(billId);
		clickDialogueOK();
		
		return billId;		*/
		return "";
	}
	
	public String addRefId()
	{
		String refId=Generic.generateAlphaNumeric();
		
		/*Log.info("======== Entering Ref Id  : "+refId+" ========");
		moreButton.click();
		addRefIdMoreLink.click();
		dialogueTextField.sendKeys(refId);
		clickDialogueOK();
		*/
		return refId;		
	}
	
	public void clickDialogueOK()
	{
		Generic.tap3DS(driver);
		Generic.tap3DS(driver);
		
		((AndroidDriver)driver).pressKeyCode(66); // KEYCODE_ENTER
	}
	
}
