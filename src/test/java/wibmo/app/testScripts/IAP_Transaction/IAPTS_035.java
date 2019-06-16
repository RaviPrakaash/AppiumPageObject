package wibmo.app.testScripts.IAP_Transaction;

import library.CSR;
import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class IAPTS_035  App-SDK IAP Transaction hold & cancel at login,phone verification,card selection,3DS
 * 
 * IAPTS_006 App-SDK IAP transaction hold on login screen and Verify Balance
 * IAPTS_001 App-SDK IAP transaction cancellation in login and Verify Balance 
 * IAPTS_008 App-SDK IAP transaction hold on card selection and Verify Balance
 * IAPTS_003 App-SDK IAP transaction cancellation in card selection and Verify Balance
 * IAPTS_010 App-SDK IAP transaction status in hold on 3ds screen and Verify Balance 
 * IAPTS_012 App-SDK IAP transaction failure on 3ds authentication and Verify Balance
 * IAPTS_005 App-SDK IAP transaction status in cancellation 3ds screen 
 *  
 * */
 
public class IAPTS_035 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_035");	
	
	/**
	 * Iapts 006.
	 */
	@Test
	public void IAPTS_035() 
	{
		Reporter.log(getTestScenario());
		
		// ---------------- IAPTS_006 App-SDK IAP transaction hold on login screen and Verify Balance ---------------- //
		String data=getTestData("IAPTS_006");
		setGroupValue("IAPTS_006");		
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);
		Generic.logout(driver); // To get Secure Pin Screen in Merchant App
		
		WelcomePage wp=new WelcomePage(driver); 
		wp.changeUserTemp(data); 
		wp.selectUser(data.split(",")[0]);			
		
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialized from config.xls file		
				
		mhp.verifySecurePinPresent();
		Generic.hideKeyBoard(driver);
		
		Generic.switchToAppWithState(driver);		
		
		double balanceAfterTransaction=Generic.checkWalletBalance(driver, data);
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);	
		
		CSR.verifyStatusOnTxnDesc("Init Success");
		
		// ---------------- IAPTS_001 App-SDK IAP transaction cancellation in login and Verify Balance ---------------- //
		
		setGroupValue("IAPTS_001");
		
		Generic.switchToMerchantWithState(driver);
		
		//mhp.executePayment(data);
		//mhp.selectApp(programName);	
		mhp.cancelSecurePin();
		
		Generic.switchToAppWithState(driver);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.refreshTransactionList();
		balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);	
		
		CSR.verifyStatusOnTxnDesc("aborted");		
		
		// ---------------- IAPTS_008 App-SDK IAP transaction hold on card selection and Verify Balance ---------------- //
		
		Generic.switchToMerchant(driver);
		
		data=getTestData("IAPTS_008");
		setGroupValue("IAPTS_008");
		
		mhp.executePayment(data);
		mhp.selectApp(programName);
		//mhp.enterSecurePin(data);	
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver); // Card Selection Page
		wsp.verifyPaymentPage();			
		
		Generic.switchToAppWithState(driver);
		
		asp.refreshTransactionList();		
		balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		CSR.verifyStatusOnTxnDesc("Init Success");
		
		// ---------------- IAPTS_003 IAP transaction cancellation in card selection and Verify Balance  ---------------- //
		
		setGroupValue("IAPTS_003");
		
		Generic.switchToMerchantWithState(driver);		
		
		wsp.verifyCardSelectionWithCancel();
		mhp.verifyFailureMessage();		
		
		Generic.switchToAppWithState(driver);
		
		asp.refreshTransactionList();		
		balanceAfterTransaction=asp.verifyBalance();
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		CSR.verifyStatusOnTxnDesc("Aborted");
		
		
		// ---------------- IAPTS_010 App-SDK IAP transaction status in hold on 3ds screen and Verify Balance ---------------- //
	
		data=getTestData("IAPTS_010");	
		setGroupValue("IAPTS_010");		
		String cardDetails=data.split(",")[3];
		if(Generic.checkWalletITPDirect(cardDetails)) return;

		Generic.switchToMerchant(driver);
		
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config.xls file		
		
		//mhp.enterSecurePin(data);		
		//Generic.merchantVerifiedLogin(driver, data);
		
		
		wsp.approvePayment(cardDetails);		
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.verifyMerchantPayment3DS();
		
		Generic.switchToAppWithState(driver);
		
		asp.refreshTransactionList();		
		balanceAfterTransaction=asp.verifyBalance();
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		CSR.verifyStatusOnTxnDesc("Intermediate");
		
		// ---------------- IAPTS_012 App-SDK IAP transaction failure on 3ds authentication and Verify Balance ---------------- //
				
		data=getTestData("IAPTS_012");	
		setGroupValue("IAPTS_012");		
		cardDetails=data.split(",")[3];
		
		if(Generic.checkWalletITPDirect(cardDetails)) return;
		
		Generic.switchToMerchantWithState(driver);
		
		mp3p.verifyMerchantPayment3DS();
		mp3p.submitPayment(cardDetails.split(":")[1]);
		mp3p.verifyFailureMsg();
		
		Generic.switchToAppWithState(driver);
		
		asp.refreshTransactionList();		
		balanceAfterTransaction=asp.verifyBalance();
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction); if(checkEnv("qa")) {CSR.verifyStatusOnTxnDesc("3DS Failed"); return;}
		
		CSR.verifyStatusOnTxnDesc("Intermediate");
		
		// ---------------- IAPTS_005 App-SDK IAP transaction status in cancellation 3ds screen  ---------------- //
		
		setGroupValue("IAPTS_005");
		Generic.switchToMerchantWithState(driver);
		mp3p.cancel3DS();
		
		CSR.verifyStatusOnTxnDesc("3DS Failed");		
		
	}
	@AfterMethod
	public void completeGroupExecution()
	{
		groupExecute=false;
	}

}
