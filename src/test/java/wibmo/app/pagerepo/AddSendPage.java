package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;
import library.Log;

/**
 * The Class AddSendPage used to verify program card balance, got to Add Money/Send money.
 */
public class AddSendPage extends BasePage
{	
	/** The driver. */
	private WebDriver driver;
	
	/** The page title. */
	@FindBy(id="title_text")
	private WebElement pageTitle;
	
	@iOSXCUITFindBy(iOSNsPredicate="name='IconMenu'") 
	@AndroidFindBy(xpath="//*[@content-desc='Navigate up']")  
	private WebElement navigateLink;
	
	@iOSXCUITFindBy(accessibility="Back")
	private WebElement navigateBackLinkIos;

	/** The load money button. */
	@iOSXCUITFindBy(accessibility="IconAddMoney") 	
	@AndroidFindBy(id="ab_loadmoney_img")
	private WebElement loadMoneyButton;

	/** The balance amount. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeStaticText' and name contains '₹'")
	//@iOSXCUITFindBy(xpath="//XCUIElementTypeImage[@name='IconVaultLeft']/..//XCUIElementTypeStaticText") Too Slow
	@AndroidFindBy(id="ab_balance_vault")
	private WebElement balanceAmount;
	
	/** The vaultOpen button. */
	@FindBy(xpath="//android.widget.ImageView[contains(@resource-id,'ab_right_vault')]")
	private WebElement vaultOpenbutton;	
	
	/** The program card icon. */
	@iOSXCUITFindBy(accessibility="IconAddCard")
	@AndroidFindBy(id="ab_card_img")
	private WebElement programCardIcon;
	
	/** The send money to mobile icon. */
	@iOSXCUITFindBy(accessibility="Mobile")
	@AndroidFindBy(xpath="//*[@text='Mobile']") 
	private WebElement sendMoneyMobileIcon;
	
	/** The send money to email icon. */
	@iOSXCUITFindBy(accessibility="Email")
	@AndroidFindBy(xpath="//*[@text='Email']") 
	private WebElement sendMoneyEmailIcon;
	
	@iOSXCUITFindBy(accessibility="Bank")
	@AndroidFindBy(xpath="//*[@text='Bank']")  
	private WebElement sendMoneyBankIcon;
	
	/** The refresh transactions button. */
	@FindBy(id="ac_history_refresh")
	private WebElement refreshTxnBtn;
	
	/** The latest transaction date time. */
	@FindBy(id="history_txnrow_date")
	private WebElement latestTransactionDateTime;
	
	/** The latest transaction description. */
	@FindBy(id="history_txnrow_narration")
	private WebElement latestTransactionDesc;
	
	/** The latest transaction amt. */
	@FindBy(id="history_txnrow_amount")
	private WebElement latestTransactionAmt;
	
	/** The no transactions msg. */
	@FindBy(id="history_notxn_text")
	private WebElement noTransactionsMsg;
	
	@iOSXCUITFindBy(iOSNsPredicate="type in {'XCUIElementTypeActivityIndicator','XCUIElementTypeNavigationBar'}")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'balance_va') or contains(@resource-id,'smoothprogressbar')]") //android.widget.RelativeLayout)[1]/*") // 4=progressBar, 3=noProgressBar
	private List<WebElement> prgBarCheck;
	
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeImage' and name='history_icon_wallet_in'")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'history_title_text') or contains(@resource-id,'got_it')]")
	private WebElement coachChk;	
	
	/** The void narration for wallet card txn */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Sale/Reversal')]")
	WebElement voidNarrationtxt;
	
	/** The void amount for wallet card txn */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Sale/Reversal')]//following-sibling::android.widget.TextView[contains(@resource-id,'history_txnrow_amount')]")
	WebElement voidAmt;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'blocked due to no transactions'")
	WebElement inactivityBlockMsg;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'is blocked.'")
	WebElement creditBlockMsg;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'update KYC to send money'")
	WebElement kycSendMoneyAlert;

	/**
	 * 
	 * Instantiates a new adds the send page.
	 *
	 * @param driver the driver
	 */
	public  AddSendPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)), this);
	}		
	
	/**
	 * Clicks on Add/Load money icon.
	 */
	public void clickLoadMoney()
	{		
		handleCoach();
		
		Log.info("======== Clicking on Load Money Icon ========");
		loadMoneyButton.click();
	}

	/**
	 * Waits for balance to be displayed and returns the program card balance.
	 *
	 * @return the double
	 * 
	 */
	public double verifyBalance()
	{
		String bal;
		handleCoach();
		
		waitOnProgressBarId(40);		
		
		// == Wait on Loading Screen for IOS  , Upgrade to BasePage if necessary == //
//		if(Generic.isIos(driver))
//		{
//			System.out.println("Waiting for Loading Screen");
//			
////			int i,chk;
////			for(i=0;i<20;i++)
////			{
////				System.out.println(chk=prgBarCheck.size());
////				if(chk >1)
////					Generic.wait(1);
////				else
////					break;
////			}		
////			if(i>18) System.out.println("-- Loading Screen delay --");
//		}
		// == == == == == == == == == == == //
		
		
		bal=Generic.isIos(driver)?balanceAmount.getAttribute("name"):balanceAmount.getText();
		Log.info("======== Balance Amount in Wallet : "+bal+" ========");
		
		if(bal.contains("..."))
		{
			Log.info("======== "+bal+" displayed. Refreshing Balance ========");
			refreshTransactionList();
			bal=balanceAmount.getText(); // TBU
		}
		
		if(bal.contains(" ")) // Valid balance Ex : Rs. 3,300
			return Generic.parseNumber(bal);
		else
		{			
			Assert.fail("Balance amount : "+balanceAmount.getText()+" is incorrect \n");
			return 0.0;
		}		
	}
	
	
	
	/**
	 * Clicks on Send Money to mobile icon.
	 */
	public void sendMoneyThroughMobile()
	{
		handleCoach();
		waitOnProgressBarId(30);
		Log.info("======== Clicking on Send Money Mobile Icon ========");
		sendMoneyMobileIcon.click();	
		//reclickSendMoneyMobile();
	}
	
	public void sendMoneyToBank()
	{
		handleCoach();
		Log.info("======== Clicking on Bank Icon ========");
		sendMoneyBankIcon.click();		
	}
	
	/**
	 * Reclicks on send money mobile icon since sometimes the first click is not registered.
	 * works based on the Add/Send title only
	 */
	public void reclickSendMoneyMobile()
	{
		Generic.wait(2);
		if(pageTitle.getText().contains("/")) // Check whether still in Pay / Send page 
			sendMoneyMobileIcon.click();
	}
	
	/**
	 * Clicks on Send Money to mobile icon.
	 */
	public void sendMoneyThroughEmail()
	{
		handleCoach();
		Log.info("======== Clicking on Send Money Email Icon ========");
		sendMoneyEmailIcon.click();		
	}
	
	/**
	 * Handles coach message occurring in the Add/Send page
	 */
	public void handleCoach()
	{
		if(Generic.isIos(driver)) 	return; 
		
		try{			
			Generic.wait(1);
			coachChk.click();
			}
		catch(Exception e){Log.info("Coach Delay !");} if(true) return; 
	}
	
	/**
	 * Navigates to the program card page.
	 */
	public void gotoProgramCard() 
	{
		handleCoach();
		Log.info("======== Clicking on Wallet Card ========");
		programCardIcon.click();		
	}
	
	
	public void gotoMvisa()
	{
		handleCoach();
		Log.info("======== Navigating to mVisa Page ========");
		navigateLink.click();
		Generic.wait(2);
		if(Generic.isAndroid(driver))
			Generic.scroll(driver, "Scan").click();
		else
			scanToPayLink.click();
	}
	
	/**
	 * Navigates to Manage cards page.
	 */
	public void gotoManageCards() 
	{
		handleCoach();
		Log.info("======== Navigating to Manage Cards Page ========");
		navigateLink.click();
		Generic.wait(2);
		Generic.scroll(driver,"Manage").click();	
		Generic.wait(2);
		Generic.hideKeyBoard(driver); // Hide Keyboard for swiping
	}
	
	/**
	 * Navigates to Settings page.
	 */
	public void gotoSettings()
	{
		handleCoach();
		Log.info("======== Navigating to Settings page ========");
		navigateLink.click();
		Generic.wait(2);
		
		if(Generic.isAndroid(driver))
			Generic.scroll(driver,"Settings").click();
		else
		{
			Generic.swipeToBottom(driver);
			settingsLink.click();
		}
		
	}
	
	/**
	 *  Verifies Send money option in the page.
	 */
	public void verifySendMoneyOption()
	{
		handleCoach();
		Log.info("======== Verifying Send Money option ========");
		try 
		{
			Assert.assertTrue(sendMoneyMobileIcon.isDisplayed(), "Send Money to mobile not displayed\n");
			Assert.assertTrue(sendMoneyEmailIcon.isDisplayed(), "Send Money to email not displayed\n");
		} 
		catch (Exception e) 
		{	
			Assert.fail("Send Money Option not displayed\n"+e.getMessage());		
		}		
	}
	
	/**
	 * Verifies latest received transaction based on sender name and amount sent.
	 *
	 * @param senderName the sender name
	 * @param amt the amt
	 */
	public void verifyReceivedTransaction(String senderName,double amt)
	{
		String nameXp="//android.widget.TextView[contains(@text,'received') and contains(@text,'"+senderName+"')]";
		String amtXP=nameXp+"/..//android.widget.TextView[contains(@resource-id,'history_txnrow_amount')]";
		
		Log.info("======== Waiting for Transaction record update ========"); 	// since userName record of previous txn may already be present
		waitOnProgressBarId(35);

		try
		{
			String txnDesc=driver.findElement(By.xpath(nameXp)).getText();
			Log.info("======== Verifying Transaction Record for "+senderName+" :"+txnDesc+" ========");
			Assert.assertTrue(txnDesc.contains(senderName), "Transaction Record not found\n");			
		}
		catch(Exception e)
		{
			Assert.fail("Transaction record not found for "+senderName+'\n'+e.getMessage());			
		}
		try
		{
			double txnAmt=Double.parseDouble(driver.findElement(By.xpath(amtXP)).getText().substring(2).replaceAll(",",""));
			Log.info("======== Verifying Transaction Amount :"+txnAmt+" ========");
			Assert.assertEquals(txnAmt,amt,"Transaction amount does not match");
			
		}
		catch(Exception e)
		{
			Assert.fail("Transaction amount not found for "+senderName+'\n'+e.getMessage());
		}		
	}
	
	/**
	 * Verify latest paid transaction based on recipient name and amount received.
	 *
	 * @param recipientName the recipient name
	 * @param amt the amt
	 */
	public void verifyPaidTransaction(String recipientName,double amt)
	{
		String nameXp="//android.widget.TextView[contains(@text,'Paid') and contains(@text,'"+recipientName+"')]";
		String amtXP=nameXp+"/..//android.widget.TextView[contains(@resource-id,'history_txnrow_amount')]";
		
		Log.info("======== Waiting for Transaction record update ========"); // since userName record of previous txn may already be present
		waitOnProgressBarId(35);
		try
		{
			String txnDesc=driver.findElement(By.xpath(nameXp)).getText();
			Log.info("======== Verifying Transaction Record for "+recipientName+" :"+txnDesc+" ========");
			Assert.assertTrue(txnDesc.contains(recipientName), "Transaction Record not found\n");			
		}
		catch(Exception e)
		{
			Assert.fail("Transaction record not found for "+recipientName+'\n'+e.getMessage());			
		}
		try
		{
			double txnAmt=Double.parseDouble(driver.findElement(By.xpath(amtXP)).getText().substring(2).replaceAll(",",""));
			Log.info("======== Verifying Transaction Amount : "+txnAmt+" ========");
			Assert.assertEquals(txnAmt,amt,"Transaction amount does not match for "+recipientName);			
		}
		catch(Exception e)
		{
			Assert.fail("Transaction amount not found for "+recipientName+'\n'+e.getMessage());
		}	
		
	}

	/**
	 * Verifies the  latest transaction  in the list based on timestamp present in transactionId which is parsed from transaction Status message.
	 *
	 * @param transactionStatusMsg the transaction status msg
	 * @param amt the amt
	 */
	public void verifyLatestTxnOnTimeStamp(String transactionStatusMsg,double amt) 
	{
		Log.info("======== Refreshing Transaction List ========");
		refreshTxnBtn.click();		
		
		String txnId=transactionStatusMsg.split(";")[1].split(":")[1].trim();
		int txnTimestampMinutes=Integer.parseInt(txnId.substring(10,12));		
		
		waitOnProgressBarId(35);
		
		Log.info("======== Verifying transaction based on timestamp for : "+txnId+" ========");
		try
		{
			String txnDesc=latestTransactionDesc.getText(),
				   txnTime=latestTransactionDateTime.getText(),
				   txnAmt=latestTransactionAmt.getText();
			
			Log.info("======== Verifying latest transaction details : "+txnDesc+' '+txnTime+' '+txnAmt+" ========");
			Assert.assertTrue(Math.abs(Integer.parseInt(txnTime.split(":")[1].substring(0,2))-txnTimestampMinutes)<=2,"Transaction corresponding to txnId : "+txnId+ " was not found\n");
			
			Log.info("======== Verifying transaction amount : "+amt+" ========");
			Assert.assertEquals(Generic.parseNumber(txnAmt),amt,0.05,"Transaction Amount does not match\n");
		}
		catch(Exception e)
		{
			Assert.fail("Transaction details corresponding to txnId "+txnId+ "not found\n"+e.getMessage());		
		}			
	}

	/**
	 * Verify absence of a transaction .
	 *
	 * @param transactionStatusMsg the transaction status msg
	 */
	public void verifyTransactionAbsent(String transactionStatusMsg)
	{
		Log.info("======== Refreshing Transaction List ========");
		refreshTxnBtn.click();		
		
		String txnId=transactionStatusMsg.split(":")[2].trim();
		int txnTimestampMinutes=Integer.parseInt(txnId.substring(10,12));
		
		waitOnProgressBarId(35);	
		
		Log.info("======== Verifying transaction based on timestamp for : "+txnId+" ========");
		try
		{
			String txnDesc=latestTransactionDesc.getText(),txnTime=latestTransactionDateTime.getText(),txnAmt=latestTransactionAmt.getText();
			Log.info("======== Verifying latest transaction details : "+txnDesc+' '+txnTime+' '+txnAmt+" ========");
			Assert.assertNotEquals(Integer.parseInt(txnTime.split(":")[1].substring(0,2)),txnTimestampMinutes,"Transaction corresponding to txnId : "+txnId+ "was found\n");		
		}
		catch(Exception e)
		{
			Assert.fail("Error in Transaction details \n"+e.getMessage());		
		}		
	}

	/**
	 * Verify the absence of all transactions especially for a new user.
	 */
	public void verifyNoTransactions() 
	{
		handleCoach();
		try
		{
			String msg=noTransactionsMsg.getText();
			Log.info("======== Verifying absence of Transactions with message : "+msg+" ========");
			Assert.assertTrue(noTransactionsMsg.isDisplayed(), "Transactions found\n");			
		}
		catch(Exception e)
		{
			Assert.fail("Transactions are present\n"+e.getMessage());
		}		
	}

	/**
	 * Refreshes the transaction list by clicking on the refresh icon.
	 */
	public void refreshTransactionList() 
	{
		handleCoach();
		Log.info("======== Refreshing Transaction List ========");
		refreshTxnBtn.click();
		waitOnProgressBarId(45);	
	}
	
	/**
	 *  
	 * Note : This alert occurs in both AddSendPage & HomePage
	 */
	public void verifyKYCAlert()
	{
		String msg;
		
		try
		{
			okBtn.isDisplayed(); 
			Log.info("======== Verifying Alert Message : "+(msg=alertMessage.getText())+" ========");
			Assert.assertTrue(msg.contains("update KYC"), "Wrong KYC update message "+msg);
			okBtn.click();
		}
		catch(Exception e)
		{
			Assert.fail("Error in validating KYC Update Alert message \n"+e.getMessage());
		}		
	}
	
	
	public void verifyWalletInactivityBlockMsg()
	{
		String msg;
		
		try
		{
			okBtn.isDisplayed(); 
			Log.info("======== Verifying Alert Message : "+(msg=Generic.isAndroid(driver)?alertMessage.getText():inactivityBlockMsg.getText())+" ========");
			Assert.assertTrue(msg.contains("blocked due to no transactions"), "Wrong message displayed "+msg);
			okBtn.click();
		}
		catch(Exception e)
		{
			Assert.fail("Error in validating KYC Wallet block message \n"+e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * @param txndetails
	 */
	public void clickLatestTransaction()
	{
		Log.info("======== Clicking on latest Transaction in list ========");
		latestTransactionDesc.click();
		
	}
	
	
	public void verifyWalletCreditBlockMsg()
	{
		String msg;
		try
		{
			okBtn.isDisplayed(); 
			Log.info("======== Verifying Alert Message : "+(msg=Generic.isAndroid(driver)?alertMessage.getText():creditBlockMsg.getText())+" ========");
			Assert.assertTrue(msg.contains("yet to complete KYC"), "Wrong credit block message displayed "+msg);
			okBtn.click();
		}
		catch(Exception e)
		{
			Assert.fail("Error in validating KYC Wallet block message \n"+e.getMessage());			
		}		
		
	}
	
	public void verifyKYCSendAlertMsg()
	{
		String msg; 	// Full Message Validation 
		
		try
		{
			okBtn.isDisplayed(); 
			Log.info("======== Verifying KYC Alert for Send Money: "+(msg=Generic.isAndroid(driver)?alertMessage.getText():kycSendMoneyAlert.getText())+" ========");
			Assert.assertTrue(msg.contains("update KYC to send money"), "Wrong message displayed :"+msg);
			okBtn.click();
		}
		catch(Exception e)
		{
			Assert.fail("Error in validating KYC Wallet block message \n"+e.getMessage());
		}		
		
	}
	
	/**
	 * This method verifies the mVisa void narration & amt
	 */
	public void verifyMvisaVoidNarrationAndAmt(String amount){
		
		waitOnProgressBarId(20);
		refreshTransactionList();
		Log.info("======== Verifying void narration ========");
		Log.info("======== The narration is ========"+ voidNarrationtxt.getText());
		Assert.assertTrue(voidNarrationtxt.isDisplayed(), "======== Mvisa void narration is not displayed ========");
		System.out.println(voidAmt.getText());
		String amt = voidAmt.getText().substring(2).replace(",", "");
		System.out.println("Amount passed from test data is " +amount);
		System.out.println("Amount extracted txn details from mob app " +amt);
		Assert.assertTrue(Double.parseDouble(amt)==Double.parseDouble(amount), "======== Void amount doesn't match with the charged amount ========");

	}
	
	
	public void navigateBack()
	{
		if(Generic.isAndroid(driver))
			driver.navigate().back();
		else
			navigateBackLinkIos.click();
	}
		
	
	

}
