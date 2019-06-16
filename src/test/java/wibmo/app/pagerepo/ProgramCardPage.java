package wibmo.app.pagerepo;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
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

/**
 * The Class ProgramCardPage mainly used to view the Program/App generated wallet card and perform lock/unlock actions.
 */
public class ProgramCardPage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	private SoftAssert softAssert;
	
	/** The lock card icon. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and name contains 'ock Card'")
	@AndroidFindBy(id="cardback_hotlist_icon")
	private WebElement lockCardIcon;
	
	/** The program card front display. */
	@iOSXCUITFindBy(accessibility="MONTH/YEAR")
	@AndroidFindBy(id="carddisplay_name")
	private WebElement cardFrontDisplay;
	
	/** The lock status. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and name contains 'ock Card'")
	@AndroidFindBy(id="cardback_hotlist_text")
	private WebElement lockStatus;
	
	/** The card number. */
	@FindBy(id="carddisplay_card")
	private WebElement cardNumber;
	
	/** The expiry details. */
	@FindBy(id="carddisplay_exp")
	private WebElement expiryDetails;
	
	/** The navigate link overridden from BasePage */
	@iOSXCUITFindBy(iOSNsPredicate="name='IconMenu'")
	@AndroidFindBy(xpath="//android.widget.ImageButton[contains(@content-desc,'Navigate up')]")
	private WebElement navigateLink;
	
	/** The payzapp card link. */
	@FindBy(xpath="//android.widget.CheckedTextView[contains(@text,'PayZapp Card')]")
	private WebElement payzappCardLink;
	
	@iOSXCUITFindBy(accessibility="Pay / Send Money")
	private WebElement paySendLink;
	
	/** The unlock card. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Unlock Card')]")
	private WebElement unlockCard;
	
	@iOSXCUITFindBy(iOSNsPredicate="name contains 'now unlocked'")
	@AndroidFindBy(xpath="//android.widget.TextView[@text='Lock Card']")
	private WebElement cardUnlockChecker;
	
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'carddisplay_name') or contains(@resource-id,'cardback_hotlist_help')]") // Check front or back
	private WebElement flipChk;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains [ci]'Success'")
	private WebElement updateSuccessMsgTitle;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'is locked' or value contains 'now unlocked'")
	private WebElement updateSuccessMsgTxt;
	
	@iOSXCUITFindBy(accessibility="Ok")
	private WebElement updateOkBtn;
	
	@iOSXCUITFindBy(accessibility="Back")
	private WebElement navigateBackIcon;
	
	@iOSXCUITFindBy(className="XCUIElementTypeStaticText")
	private WebElement noWalletCardMsg;
	
	
	// ==== Additional UI Validation ==== //
	
	@FindBy(id="headerNote")	
	private WebElement headerNote;  
	
	@FindBy(id="balance_card")
	private WebElement balanceText; // Chk consistency
	
	@FindBy(id="visa_card")
	private WebElement visaLogo;	
	
	@FindBy(id="cardback_cvv2") // CardBack
	private WebElement cvvField;
	
	@FindBy(id="cardback_hotlist_help") // CardBack
	private WebElement lockHelpText;
	
	@FindBy(id="cvv2_help")
	private WebElement footerNote;	
	
	
	/**
	 * Instantiates a new ProgramCardPage.
	 *
	 * @param driver the driver
	 */
	public ProgramCardPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this); 
	}
	
	/**
	 * Locks the card if it is not already locked. 
	 */
	public void lockCard()
	{
		String lockText,updateMsgSuccess="";
		Generic.wait(3); // Wait for Card Flip Animation
		
		Log.info("======== Flipping Program Card ========");
		cardFrontDisplay.click();
		
		lockText=Generic.isAndroid(driver)?lockStatus.getText():lockStatus.getAttribute("name"); // ios 
		
		Log.info("======= Current lock status : "+lockText+" ========");
		if(lockText.contains("Unlock"))
		{
			Log.info("======== Card is locked ========");
		}
		else
		{
			Log.info("======== Clicking on Lock Icon ========");
			lockCardIcon.click();		
			if (Generic.isAndroid(driver)) Generic.wait(5);
			
			if(Generic.isIos(driver))
			{
				updateOkBtn.isDisplayed();
				
				 Log.info("======== Validating Update status : "+(updateMsgSuccess+=updateSuccessMsgTitle.getText())+' '+(updateMsgSuccess+=updateSuccessMsgTxt.getText()));
				 Assert.assertTrue(updateMsgSuccess.contains("Success"), "Update Status title incorrect");
				 Assert.assertTrue(updateMsgSuccess.contains("is locked"), "Update Status message incorrect");
				 
				 Log.info("======== Clicking on Update OK Button ========");
				 updateOkBtn.click();	
			}
			
		}			
	}
	
	/**
	 * Unlocks the card if it is not already unlocked.
	 */
	public boolean unlockCard() 
	{
		String lockText,updateMsgSuccess="";
		
		Log.info("======== Flipping Program Card ========");
		cardFrontDisplay.click();
		
		lockText=Generic.isAndroid(driver)?lockStatus.getText():lockStatus.getAttribute("name"); // ios 
		
		Log.info("======= Current lock status : "+lockText+" ========");
		if(lockText.contains("Lock"))
		{
			Log.info("======== Card is unlocked ========");
		}
		else
		{
			Log.info("======== Clicking on Unlock Icon ========");
			lockCardIcon.click();			
			
			try
			{				
				
				if(Generic.isIos(driver)) // IOS 
				{
					updateOkBtn.isDisplayed();
					
					 Log.info("======== Validating Update status : "+(updateMsgSuccess+=updateSuccessMsgTitle.getText())+' '+(updateMsgSuccess+=updateSuccessMsgTxt.getText()));
					 Assert.assertTrue(updateMsgSuccess.contains("Success"), "Update Status title incorrect");
					 Assert.assertTrue(updateMsgSuccess.contains("unlocked"), "Update Status message incorrect");
					 
					 Log.info("======== Clicking on Update OK Button ========");
					 updateOkBtn.click();	
				}
				else // Android
					new WebDriverWait(driver, 40).until(ExpectedConditions.visibilityOf(cardUnlockChecker));
			}
			catch(Exception e)
			{
				com.libraries.Log.info("Card was not unlocked\n"+e.getMessage());
			}
		}	
		Log.info("======== Card Unlocked ========");
		return true;
	}
	
	/**
	 * Navigates to AddSend/Wallet page.
	 */
	public void gotoAddSendPage()
	{
		Log.info("======== Navigating to Add/Send Page ========");		
		navigateLink.click();
		
		if(Generic.isAndroid(driver))
		{
			Generic.wait(2);		
			Generic.scroll(driver, "/ Send").click();
		}
		else
		{
			Generic.swipeToBottom(driver);
			paySendLink.click();
		}
	}
	
	/**
	 * 
	 * Verifies whether a user has a wallet or not
	 */
	public void verifyNoWalletCardMsg()
	{
		String msg;
		
		waitOnProgressBarId(25);
		
		try {
		Log.info("======== Verifying no wallet card message : "+(msg=noWalletCardMsg.getText())+" ========");
		Assert.assertTrue(msg.contains("do not have a card"), "Please select a user without wallet");
		}catch(Exception e) {Assert.fail("Unable to verify Wallet status");}
		
	}

	/**
	 * Gets the card details from Program Card page as CardNumber:ExpiryMonth:ExpiryYear
	 *
	 * @return the card details as CardNumber:ExpiryMonth:ExpiryYear
	 */
	public String getCardDetails() 
	{	
		Generic.wait(2); // wait for auto flip animation
		String cardNo=cardNumber.getText().replaceAll(" ", ""),
				expiry=expiryDetails.getText().replaceAll("/", ":20");
		
		String cardDetails=cardNo+':'+expiry;
		Log.info("======== Retreiving card details : "+cardDetails+" ========");
		
		return cardDetails;		
	}	
	
	
	/**
	 *  Opens the Navigation Panel
	 */
	public void openNavigationPanel()
	{
		Log.info("========  Opening Navigation Panel ========");		
		navigateLink.click();
		
	}
	
	public void flipCard()
	{		
		Log.info("======== Flipping Program Card ========");
		Generic.tap(driver, flipChk);		
	}
	
	
	public void navigateBack()
	{
		if(Generic.isAndroid(driver))
			Generic.navigateBack(driver);
		else
			navigateBackIcon.click();
	}
	
	
	public double verifyBalance()
	{
		if(Generic.isIos(driver)) return 0.0; 	// TBI
		String bal="0.0"; 
		
		try {
		
		bal=balanceText.getText();
		Log.info("======== Balance Amount in Wallet Card Page : "+bal+" ========");
		bal=bal.replace("Rs.", ""); 
		
		}
		catch(Exception e) {Assert.fail("Balance not displayed in Wallet Card Page ");}
		
		return Generic.parseNumber(bal);
	}
	
	public void walletUIChk()
	{
		if(Generic.isIos(driver)) return;
		
		Log.info("======== Performing Additional UI checks ========");
		
		try{
		Log.info("=== Checking Header : "+headerNote.getText()+" ===");
		Log.info("=== Checking Balance: "+balanceText.getText()+" ===");
		Log.info("=== Checking Footer : "+footerNote.getText()+" ===");			
		
		chkCardFrontBack();
		flipCard();
		chkCardFrontBack();	
		}
		catch(Exception e){Assert.fail("Error in Validating Wallet UI");}
	}
	
	public void chkCardFrontBack()
	{
		Generic.wait(2);
		
		if(flipChk.getAttribute("resourceId").contains("carddisplay")) // CardFront
		{
			String cardNumberText;
			Log.info("=== Checking CardNumber : "+(cardNumberText=cardNumber.getText())+" ===");
			Assert.assertEquals(cardNumberText.replace(" ","").length(),16,"Error in Card Number");
			
			String expiryText;
			Log.info("=== Checking Card Expiry : "+(expiryText=expiryDetails.getText())+" ===");
			Assert.assertTrue(expiryText.contains("/"), "Error in Expiry Details");
			
			String cardUserName;
			Log.info("=== Checking Card Username : "+(cardUserName=cardFrontDisplay.getText())+" ===");
			Assert.assertTrue(cardUserName.contains(" "), "Error in Card UserName");
			
			Assert.assertTrue(visaLogo.isDisplayed(), "Visa Logo not displayed");			
		}
		else   // CardBack
		{
			String cvvFieldText;
			Log.info("=== Checking CVV field : "+(cvvFieldText=cvvField.getText())+" ===");
			Assert.assertEquals(cvvFieldText.split(" ")[1].length(),3);
			
			String lockHlpTxt;
			Log.info("=== Checking CVV help text : "+(lockHlpTxt=lockHelpText.getText()));
			Assert.assertTrue(lockHlpTxt.contains("lock or unlock"), "Lock Help Message error");			
		}
				
	}
	
	
}
