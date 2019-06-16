package wibmo.app.pagerepo;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import library.CSR;
import library.Generic;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import library.Log;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class ManageCardLimitsPage mainly used to set the card limits 
 */
public class ManageCardLimitsPage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The PAN number text field */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'text1')]")
	private WebElement selectedCard;
	
	@iOSXCUITFindBy(accessibility="▾") 
	@AndroidFindBy(id="spinner_card_list")
	private WebElement cardSelectBtn;
	
	/** IOS has only name attribute */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and visible=true")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'text1')]")
	private List<WebElement> cardList;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[2]") 
	@AndroidFindBy(id="day_limit_count_input")
	private WebElement txnCountTxtField;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[3]") 
	@AndroidFindBy(id="day_limit_value_input")
	private WebElement txnValueTxtField;
	
	@iOSXCUITFindBy(accessibility="Proceed")
	@AndroidFindBy(id="btn_update")
	private WebElement proceedBtn;
	
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(id="main_mbOtp_edit")
	private WebElement otpTxtField;
	
	@iOSXCUITFindBy(accessibility="Update")
	@AndroidFindBy(id="main_btnUpdate")
	private WebElement otpUpdateBtn;
	
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Your'")
	private WebElement updateSuccessMsg;
	
	
/** 
	 * Instantiates a new ManageCardLimitsPage.
	 *
	 * @param driver the driver
	 */
	public ManageCardLimitsPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this); 
	}
	
	public void selectCard(String cardName)
	{
		boolean cardFound=false;
		int noOfCards;
		String listCardName;
		
		waitOnProgressBarId(35);
		
		String preSelectedCard=selectedCard.getText();
		if(Generic.containsIgnoreCase(preSelectedCard, cardName))
		{
			Log.info("======== Card Preselected : "+preSelectedCard+" ========");
			return;
		}		
		else
		{
			
				cardSelectBtn.click(); 
			
			try { Thread.sleep(1500);} catch (InterruptedException e1){}			
				
			noOfCards=cardList.size();			
			Log.info("========  "+noOfCards+" cards found ========");			
				
			for (WebElement c : cardList) 
			{
				listCardName=Generic.isAndroid(driver)?c.getText():c.getAttribute("name");
				Log.info(listCardName);
				
				if(Generic.containsIgnoreCase(listCardName, cardName))				
				{
					Log.info("======== Selecting Card : "+listCardName+" ========");
					c.click();
					cardFound=true;
					break;
				}
			}
		}		
			if(!cardFound) {Assert.fail(cardName+" card not found\n");}
	}
	
	/**
	 *  Can be updated to randomized value
	 * @param txnCountLimit
	 */
	public void setTxnCountLimit(String txnCountLimit)
	{
		Log.info("======== Entering Transaction count limit per day "+txnCountLimit+" ========");
		if(!txnCountTxtField.getText().equals(txnCountLimit))
		{
			txnCountTxtField.clear();
			txnCountTxtField.sendKeys(txnCountLimit);
		}
	}
	
	public void setTxnValueLimit(String txnValueLimit)
	{
		Log.info("======== Entering Transaction value limit per day "+txnValueLimit+" ========");
		if(!txnValueTxtField.getText().equals(txnValueLimit))
		{
			txnValueTxtField.clear();
			txnValueTxtField.sendKeys(txnValueLimit);
		}
	}
	
	public void clickProceed()
	{
		Log.info("======== Clicking on Proceed ========");
		Generic.hideKeyBoard(driver);
		
		proceedBtn.click();
		
	}
	
	public void enterCardLimitsOtp(String mobileNo)
	{
			
		String  bankCode=Generic.getPropValues("BANKCODE", BaseTest1.configPath),
				event=Generic.getPropValues("CARDLIMIT",BaseTest1.configPath);
		
		Log.info("======== Entering OTP ========");
		otpTxtField.sendKeys(Generic.getOTP(mobileNo, bankCode, event));
		
		otpUpdateBtn.click();
	}
	
	public void navigateBack()
	{
		navigateLink.click();
	}
	
	public void verifyCardLimitsUpdateMsg()
	{
		Generic.verifyToastContent(driver, "Your preference has been updated");
		
		String msg;
		if(Generic.isIos(driver))
		{
			try {
				Log.info("======== Validating Update message : "+(msg=updateSuccessMsg.getText())+" ========");
				Assert.assertTrue(msg.toLowerCase().contains("preference has been updated"), "Incorrect update message");
				okBtn.click();
				}catch(Exception e) {Assert.fail("Update message not found");}
		}
	}
	
	public void verifyTxnCountLimit(String txnCountLimit)
	{
		waitOnProgressBarId(30);
		try {
		String limitVal=txnCountTxtField.getText();	
		
		Log.info("======== Verifying Transaction Limit Count : "+limitVal+" ========");
		Assert.assertEquals(limitVal, txnCountLimit,"Limit count previously entered does not match");
		
		}catch(Exception e) {Assert.fail("Unable to verify transaction count limit");}
	}
	
	public void verifyTxnValueLimit(String txnValueLimit)
	{
		try {
			String limitVal=txnValueTxtField.getText();	
			
			Log.info("======== Verifying Transaction Limit Count : "+limitVal+" ========");
			Assert.assertEquals(limitVal, txnValueLimit,"Limit count previously entered does not match");
			
			}catch(Exception e) {Assert.fail("Unable to verify transaction count limit");}
	}
	
	
	
	
	
	
}
