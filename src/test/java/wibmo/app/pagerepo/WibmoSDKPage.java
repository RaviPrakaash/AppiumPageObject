package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
//import com.libraries.ExcelLibrary;
import library.Log;
import wibmo.app.testScripts.BaseTest1;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;


/**
 * The Class WibmoSDKPage represents the payment screen occuring during all transactions.  
 */
public class WibmoSDKPage extends BasePage 
{

	/** The driver. */
	private WebDriver driver;

	/** The recharge pay page. */
	@FindBy(id="layout_root")
	private WebElement rechargePayPage;
	
	/** The transaction amount. */
	@FindBy(id="txn_merchant_amount")
	private WebElement transactionAmount;
	
	/** The approve button. */
	@iOSXCUITFindBy(accessibility="Approve")
	@AndroidFindBy(xpath="//*[@text='Approve']") 
	private WebElement approveButton;

	/** The cancel button. */
	@iOSXCUITFindBy(accessibility="Cancel")
	@AndroidFindBy(id="cancel_textbutton")
	private WebElement cancelButton;
	
	@FindBy(xpath="(//*[contains(@resource-id,'text1')])[2]") 
	private WebElement cancelReason;
	
	@FindBy(xpath="//*[contains(@resource-id,'text1')]")
	private List<WebElement> cancelReasons;

	/** The cancel confirmation yes button. */
	@iOSXCUITFindBy(accessibility="Yes")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'button1')]")
	private WebElement cancelYesButton;
	
	/** The previously selected card. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="txn_card_selected")
	private WebElement selectedCard;
	
	/** The card list button. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="txn_card_arrow")
	private WebElement cardListButton;
	
	/** The card list. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and (name contains 'XX' or name contains 'PayZapp')")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'text1')]") 
	private List<WebElement> cardList;
	
	/** The proceed button. */
	@iOSXCUITFindBy(accessibility="Ok")
	@AndroidFindBy(xpath="//*[@text='Proceed' or @text='PROCEED']") 
	private WebElement proceedButton;
	
	/** The insufficient balance message alert title. */
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Insufficient'")
	@AndroidFindBy(id="alertTitle")
	private WebElement insuffBalMsgTitle;
	
	/** The insufficient balance message cancel btn. */
	@iOSXCUITFindBy(iOSNsPredicate="name='Cancel' and visible=true")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'button2')]")
	private WebElement insuffBalMsgCancelBtn;
	
	/** The timeout check used to prevent the Appium server from timeout. */
	@FindBy(className="android.widget.TextView")
	private WebElement timeoutCheck;
	
	/** The program title. */
	@FindBy(id="txn_program_title")
	private WebElement program;
	
	/** The enter secure pin textfield. */
	@FindBy(xpath="//android.widget.EditText")
	private WebElement enterSecurePinTextfield;
	
	/** The submit button. */
	@FindBy(xpath="//*[@text='Submit']") 
	private WebElement submitButton;
	
	@FindBy(id="card_img_edit")
	private WebElement cardsEditIcon;
	
	/** The card number textfield. */
	@FindBy(id="main_cardnum_edit")
	private WebElement cardNoTextfield;
	
	/** The expiry month textfield. */
	@FindBy(id="main_cardexp_mm_edit")
	private WebElement expMonthTextfield;
	
	/** The expiry year textfield. */
	@FindBy(id="main_cardexp_yyyy_edit")
	private WebElement expYearTextfield;
	
	/** The cards check used to check the presence of cards. */
	@FindBy(xpath="//android.widget.ImageView[contains(@resource-id,'arrow') or contains(@resource-id,'scan')]")
	private WebElement cardsCheck;
	
	/** The got it button. */
	@FindBy(id="got_it_button")
	private WebElement gotItButton;
	
	/** The popup checker. */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'approve_textbutton')] | //android.widget.Button")
	private WebElement popupChecker;
	
	/** The yes button. */
	@FindBy(xpath="//android.widget.Button[contains(@resource-id,'button1')]")//xpath="//android.widget.Button[contains(@resource-id,'button1')]"
	private WebElement yesBtn;
	
	/** The card number textfield. */
	@FindBy(id="edit_cardnum")
	private WebElement cardNumberTextfield; // New User flow 
	
	/** The expiry month field. */
	@FindBy(id="edit_exp_mm")
	private WebElement expMonthField;
	
	/** The expiry year field. */
	@FindBy(id="edit_exp_yyyy")
	private WebElement expYearField;
	
	/** Used to Check whether the Proceed Alert occured or not */ 
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeStaticText' and visible=true")
	@AndroidFindBy(xpath="//*[@text='Proceed' or @text='Authentication']|//*[contains(@resource-id,'button1') or contains(@resource-id,'title_text') or contains(@resource-id,'txn_merchant_name') or contains(@content-desc,'XXXX') or contains(@text,'Wibmo SDK') or contains(@text,'Change Card')]")
	private WebElement proceedChk;
	
	// ================ Promo Code dialogue ================ //
	
	@iOSXCUITFindBy(iOSNsPredicate="name contains 'promo code'")
	@AndroidFindBy(xpath="//*[contains(@text,'promo')]")
	private WebElement promoCodeLink;
	
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(className="android.widget.EditText")
	private WebElement promoCodeTxtField;
	
	@iOSXCUITFindBy(accessibility="Apply")
	@AndroidFindBy(xpath="//*[@text='Apply']") 
	private WebElement promoApplyBtn;
	
	
	@AndroidFindBy(xpath="//*[@text='Back']") 
	private WebElement promoBackBtn;
	
	@iOSXCUITFindBy(iOSNsPredicate="name beginswith 'Promo' and name contains 'applied'")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'txn_txn_footer')]")
	private WebElement promoAppliedLink;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'successful'")
	private WebElement promoSuccessMsg; 
	
	@AndroidFindBy(xpath="//*[contains(@resource-id,'message')]")
	private WebElement promoErrMsg;		
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'limit reached'")
	private WebElement limitsErrMsg;
	
	@iOSXCUITFindBy(accessibility="Ok")
	@AndroidFindBy(xpath="//android.widget.Button[contains(@text,'OK')  or contains(@text,'Ok')]")
	private WebElement promoErrOKBtn;
	

	@iOSXCUITFindBy(accessibility="Ok")
	private WebElement promoSuccessOkBtn;
	
	
		
		// ===================================================== //

	/**
	 * Instantiates a new WibmoSDKPage.
	 *
	 * @param driver the driver
	 */
	public WibmoSDKPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
		//new HandleOverlayPopup(driver).start();
	}

	/**
	 * Verifies the presence of payment screen.
	 */
	public void verifyPaymentPage()
	{		
		Log.info("======== Verifying Payment screen with Approve and Cancel buttons ========");
		
		handleGotIt();
		try 
		{
			Assert.assertTrue(approveButton.isDisplayed());	
			Assert.assertTrue(cancelButton.isDisplayed());
		}
		catch (Exception e) 
		{
			Assert.fail("Payment Screen not displayed  \n  "+e.getMessage());
		}
	}
	
	/**
	 * Verifies the merchant transaction amount.
	 *
	 * @param amt the amt
	 */
	public void verifyMerchantTransactionAmount(String amt) // ==== To be used only by Merchant test cases ==== // 
	{		
		try
		{
			Log.info("======== Verifying merchant transaction amount after converting denomination from paisa to Rupee : "+transactionAmount.getText().substring(4)+" ========");
			Assert.assertEquals(Double.parseDouble(transactionAmount.getText().substring(4)), Double.parseDouble(amt)*0.01," Amount value does not match\n");
		}
		catch(Exception e)
		{
			Assert.fail("Merchant Transaction amount not displayed correctly\n"+e.getMessage());
		}
	}
	
	/**
	 * Handles the got it popup.
	 */
	public void handleGotIt()
	{
		//HandleOverlayPopup.handled=false;
		//new HandleOverlayPopup(driver).start();
		//previous xpath : *[contains(@resource-id,'got_it') or contains(@resource-id,'title_text') or contains(@resource-id,'txn_merchant_name')]";
		
		if(Generic.isIos(driver)) return;
				
		String xp="//*[contains(@resource-id,'got_it') or contains(@text,'Payment Method') or contains(@text,'Got It')]"; // contains(@text,'Rs') txn_txn_ => Payment Method : text
		
		try
		{
			driver.findElement(By.xpath(xp)).click(); 
			Generic.wait(2);
			Log.info("======== Handling optional coach ========");
			driver.findElement(By.xpath(xp)).click(); // Handle Non reponsive got_it
		}
		catch(Exception e)
		{
			/*String backXp="//*[contains(@resource-id,'title_text') or contains(@text,'Wibmo SDK')]";
			if(!Generic.checkElementOccurenceOnXpath(driver, backXp, 3));
			{
				//Log.info("-- Navigating back for handling coach based on Text : "+driver.findElement(By.xpath(backXp)).getText()+"--");
				//driver.navigate().back();
			}*/
			Log.info("== Delay due to coach handling ==\n"+e.getMessage());
		}
	}
	
	public void handleCoach()
	{
		String xp="//*[contains(@resource-id,'got_it') or contains(@text,'Payment Method') or contains(@text,'Got It')]"; // or Use Approve
		
		if(driver.findElement(By.xpath(xp)).getAttribute("resourceId").contains("got"))
		{
			Log.info("======== Handling Coach through navigation ========");
			driver.navigate().back();
		} // If necessary add Cancel Prompt through common click 
	}
	
	/**
	 * Cancels the Recharge transaction at the Payment screen.
	 *
	 * @param cardName the card name
	 */
	public void cancelRecharge(String cardName)
	{
		int noOfCards;
		boolean cardFound=false;
		String listCardName;
		
		verifyPaymentPage();
		
		if(cardName.contains(":")) cardName=cardName.split(":")[0];		
		
		try 
		{
			cardListButton.click();
		} 
		catch (Exception e)
		{				
			Assert.fail("No cards found , add cards and try again\n"+e.getMessage());
		}	
		
		try { Thread.sleep(1500);} catch (InterruptedException e1){}			
			
		noOfCards=cardList.size();
			
		Log.info("========  "+noOfCards+" cards found ========");			
			
		for (WebElement c : cardList)
			{
				listCardName=c.getText();
				if(listCardName.toLowerCase().contains(cardName.toLowerCase()+" ") || listCardName.toLowerCase().contains(cardName.toLowerCase()+"card")) // Program Card displayed as ProgramCard
				{
					Log.info("======== Selecting Card : "+c.getText()+" ========");
					c.click();
					cardFound=true;
					break;
				}
			}
		if(cardFound)
		{
			Log.info("======== Clicking on Cancel ========");
			try {Thread.sleep(2500);} catch (InterruptedException e) {}
	
			cancelButton.click();
			cancelYesButton.click();		
		}
		else
		{
			Assert.fail(cardName+" Card Not Found , please recheck card name\n");
		}				
		
		handleCancelReason();
	}
	
	/**
	 *  Used in Web Overlay cancellation 
	 */
	public void handleCancelAlert()
	{
		Log.info("======== Clicking on Cancel Alert Yes button ========");
		cancelYesButton.click();				
	}
	
	
	public void cancelCardSelection()
	{
		Log.info("======== Cancelling Card Selection ========");
		cancelButton.click();
		cancelYesButton.click();
		
		handleCancelReason();
	}
	
	
	
	/**
	 * Selects the card as specified in the cardDetails and clicks on the Approve button.
	 *
	 * @param cardDetails the card details or CardName 
	 */
	public void approvePayment(String cardDetails)
	{
		int noOfCards;
		boolean cardFound=false;
		String listCardName;		
		
		verifyPaymentPage();		
		
		cardDetails=cardDetails.split(":")[0];				
		
		String preSelectedCard=selectedCard.getText();
		if(Generic.containsIgnoreCase(preSelectedCard, cardDetails+" ") || Generic.containsIgnoreCase(preSelectedCard, cardDetails+"card"))
		{
			Log.info("======== Card Preselected : "+preSelectedCard+" ========");
			cardFound=true;
		}
	
	if(!cardFound)	// If card is not already selected select card
	{
		try 
		{
			cardListButton.click(); 
		} 
		catch (Exception e)
		{				
			Assert.fail("No cards found , add cards and try again\n"+e.getMessage());
		}	
		
		try { Thread.sleep(1500);} catch (InterruptedException e1){}			
			
		noOfCards=cardList.size();			
		Log.info("========  "+noOfCards+" cards found ========");			
			
		for (WebElement c : cardList) 
		{
			listCardName=Generic.isAndroid(driver)?c.getText():c.getAttribute("name");
			Log.info(listCardName);
			
			//if(Generic.containsIgnoreCase(listCardName, cardDetails+" ") || Generic.containsIgnoreCase(listCardName, cardDetails+"card"))
			if(Generic.containsIgnoreCase(listCardName, cardDetails) && (!Character.isAlphabetic(listCardName.charAt(cardDetails.length()))))	// Handle &nbsp TestCardCorp TestCard				
			{
				Log.info("======== Selecting Card : "+listCardName+" ========");
				c.click();
				cardFound=true;
				break;
			}
		}
	}		
		if(cardFound)
		{
			Log.info("======== Clicking on Approve Button ========");	
			approveButton.click();			
		}
		else
		{
			Assert.fail(cardDetails+" Card Not Found , please recheck card name\n");
		}	
		
		if(!Generic.containsIgnoreCase(cardDetails,BaseTest1.programName))
			handleProceed();	
	}
	
	
	
	
	/**
	 * Handles proceed alert message if it occurs. Needs to be simplified , since Proceed alert comes for External Card 
	 */
	public void handleProceed() 
	{
		Generic.wait(2);
		try {
		
			String 	proceedChk=Generic.isAndroid(driver)?this.proceedChk.getText():this.proceedChk.getAttribute("name");
			System.out.println("Handle Proceed based on "+proceedChk);
		
			if(proceedChk.toLowerCase().contains("proceed"))
				proceedButton.click();
		
		}catch(Exception e) {Log.info("== Proceed alert delay ===");e.printStackTrace();}
		
	}
	
	public void verifyCardLock()
	{
		Log.info("======== Verifying Card Lock ========");
		try
		{
			Assert.assertTrue(!cardList.isEmpty(), "Unable to verify Card Lock\n");
		}
		catch(Exception e)
		{
			Assert.fail("Unable to verify card lock\n");
		}
	}
	  
	
	public void verifyCardLimitErrMsg()
	{
		String msg;
		try 
		{
			
			okBtn.isDisplayed(); // okBtn='NO' button or create separate No btn for platform compatibility  //android.widget.Button[@text='No' or @text='NO']
		//	WebElement noBtn=okBtn;
			
			msg=Generic.isAndroid(driver)?alertMessage.getText():limitsErrMsg.getText();
			
			Log.info("======== Verifying Card Limits error message : "+msg+" ========");
			Assert.assertTrue(msg.contains("limit reached"),"Incorrect card limit error message");
			
			okBtn.click();
		//	noBtn.click(); 
		}
		catch(Exception e) {Assert.fail("Limits message not found"+e.getMessage());}
		
		if(Generic.isIos(driver))
			cancelCardSelection(); 	// Normalize Navigation 
		
	}

	
	/**
	 * Approves payment with or without the presence of the given card.
	 * If card is not present the cardDetails can be given as cardName:cardNumber:expiryMonth:expiryYear
	 *
	 * @param cardDetails the card details
	 */
	public void approvePaymentGeneric(String cardDetails)
	{
		try{Thread.sleep(2500);}catch(Exception e){}
		
		if(!cardDetails.contains(":"))
		{
			approvePayment(cardDetails);  // If only cardNumber is present execute approvePayment()
			return;
		}		
		
		if(cardsCheck.getAttribute("resourceId").contains("arrow"))
		{
			approvePayment(cardDetails.split(":")[0]);
			return;
		}
		else if(cardsCheck.getAttribute("resourceId").contains("scan"))
		{
			Log.info("======== Executing transaction through Card Number ========");
			String[] values=cardDetails.split(":");			
			String cardNo=values[1],expMonth=values[2],expYear=values[3];
			
			Log.info("======== Entering card no. : "+ cardNo+" ========" );
			cardNoTextfield.sendKeys(cardNo);
			
			Log.info("======== Entering exp month : "+expMonth+" ========" );
			expMonthTextfield.sendKeys(expMonth);
			
			Log.info("======== Entering exp year : "+expYear+"========");
			expYearTextfield.sendKeys(expYear);
			
			Log.info("======== Clicking on Approve button ========");
			approveButton.click();			
		}
		else
		{
			Assert.fail("Payment screen not loaded correctly \n");
		}		
	}
	
	/**
	 * Verifies insufficient balance error message.
	 *
	 * @param cardName the card name
	 */
	public void verifyInsufficientBalance(String cardName)
	{
		boolean cardFound=false; 	
		handleGotIt();
		
		cardListButton.click();	
		
		try {Thread.sleep(1500);} catch (InterruptedException e1){}		
			
		for (WebElement c : cardList) 			
			if(c.getText().toLowerCase().contains(cardName.toLowerCase()))
			{
				Log.info("======== Selecting Card : "+c.getText()+" ========");
				c.click();
				cardFound=true;
				break;
			}
			
		if(cardFound)
		{
			Log.info("======== Clicking on Approve Button ========");
			approveButton.click();			
		}
		else
		{
			Assert.fail("Card Not Found , please recheck card name");
		}
		Log.info("======== Verifying insufficient balance message ========");
		
		try 
		{
			Assert.assertTrue(insuffBalMsgTitle.getText().contains("Insufficient"));
			insuffBalMsgCancelBtn.click();
		} 
		catch (Exception e)
		{
			Assert.fail("Insufficient balance message not dispalyed \n"+e.getMessage());
		}
	}	
	
	/**
	 * Waits for session timeout.
	 *
	 * @param timeout the timeout
	 */
	public void waitForSessionTimeout(int timeout)
	{
		Log.info("======== Waiting for Session timeout ========");
		for(int i=timeout*2;i>=1;i--)
		{
			Log.info("========  "+ i/2.0 +" minutes left until expected session timeout ========");			
			try { Thread.sleep(30000);} catch (InterruptedException e) {}
			timeoutCheck.getText(); // To prevent Appium timeout			
		}		
	}

	/**
	 * Verifies the display of card details.
	 */
	public void verifyCardDetails() 
	{
		Log.info("======== Verifying Card Details ========");
		
		try 
		{
			cardListButton.click();
			Generic.wait(2);
		} 
		catch (Exception e)
		{				
			Assert.fail("No cards found , add cards and try again\n"+e.getMessage());
		}		
		try
		{
			Log.info("======== Cards Displayed ========");
			for(WebElement c:cardList) 
				Log.info("\t"+c.getText());
			
			Assert.assertTrue(cardList.size()>0, "Cards not displayed correctly \n");
		}
		catch(Exception e)
		{
			Assert.fail("Cards not displayed correctly \n"+e.getMessage());
		}		
	}	
	
	/**
	 * Verifies the presence of card selection before cliking on cancel button.
	 */
	public void verifyCardSelectionWithCancel()
	{
		Log.info("======= Verifying Card Selection Page ========");
		verifyPaymentPage();
		
		Log.info("======= Cancelling Card Selection ========");
		cancelButton.click();
		yesBtn.click();			
		handleCancelReason();
		
	}
	
	public void cancelIAPLogin()
	{
		Log.info("======= Verifying IAP secure pin screen ========");
		Log.info("======= Clicking on Cancel button ========");
		
		cancelButton.click();
		yesBtn.click();			
		handleCancelReason();
	}

	/**
	 * Approves payment with a new card.
	 * cardDetails to be given as cardNumber:expiryMonth:expiryYear
	 *
	 * @param cardDetails the card details
	 */
	public void approvePaymentNewCard(String cardDetails) 
	{
		handleGotIt();
		int i=0;
		String values[]=cardDetails.split(":");
		String cardNumber=values[i++],
				expMonth=values[i++],
				expYear=values[i++];
		
		Log.info("======== Entering Card No. : "+cardNumber+" ========");
		cardNumberTextfield.sendKeys(cardNumber);
		
		Log.info("======== Entering Exp Month : "+expMonth+" ========");
		expMonthField.sendKeys(expMonth);
		
		Log.info("======== Entering Exp Year : "+expYear+" ========");
		expYearField.sendKeys(expYear);		
		
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Clicking on Approve Button ========");
		Generic.wait(2);
		approveButton.click();
		
		handleProceed();
	}
	
	public void enterPromoCode(String promoCode)
	{
		Log.info("======== Clicking on Promo Code link ========");
		promoCodeLink.click();
		
		Log.info("======== Entering Promo Code : "+promoCode+" ========");		
		promoCodeTxtField.sendKeys(promoCode);
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Clicking on Apply button ========");
		promoApplyBtn.click();		
		
		if(Generic.isIos(driver))
		{
			Log.info("======== Verifying Promo Code Success Alert Message : "+promoSuccessMsg.getText()+"  ========");
			promoSuccessOkBtn.click();			
		}
			
	}
	
	public void verifyPromoCodeApplied(String promoCode)
	{
		try
		{
			String promoAppliedMsg=Generic.isAndroid(driver)?promoAppliedLink.getText():Generic.getAttribute(promoAppliedLink, "name");
			Log.info("======== Verifying applied promo code : "+promoAppliedMsg+" ========");
			Assert.assertTrue(Generic.containsIgnoreCase(promoAppliedMsg, promoCode), promoCode+" not found in "+promoAppliedMsg);
		}
		catch(Exception e)
		{
			Assert.fail(" Applied Promo Code message not found \n"+e.getMessage());
		}
	}
	
	public void verifyInvalidPromoCodeMsg()
	{
		try
		{		
			String msg=promoErrMsg.getText();		
			Log.info("======== Verifying Invalid Promo Code message : "+msg+" ========");
			Assert.assertTrue(Generic.containsIgnoreCase(msg, "enter valid"));
		}
		catch(Exception e)
		{
			Assert.fail("Promo code error message not found\n"+e.getMessage());
		}
		Log.info("======== Clicking on OK button ========");
		promoErrOKBtn.click();
	}
	
	public void closePromoDialogue()
	{
		Log.info("======== Closing Promo dialogue ========");
		promoBackBtn.click();
	}
	
	public void gotoManageCards()
	{
		Log.info("======== Clicking on Edit Cards icon ========");
		cardsEditIcon.click();		
	}
	
	
	
}
