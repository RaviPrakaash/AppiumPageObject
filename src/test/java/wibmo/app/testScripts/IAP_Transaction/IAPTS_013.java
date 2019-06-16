package wibmo.app.testScripts.IAP_Transaction;

import library.CSR;
import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTS_013 App-SDK IAP transaction when phone verification fails & insufficient funds transaction. Static Merchant.
 */
public class IAPTS_013 extends BaseTest // ==== App-SDK IAP transaction when phone verification fails & insufficient funds transaction ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_013");
	
	/**
	 * Iapts 013.
	 */
	@Test
	public void IAPTS_013()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_013");
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++],cardDetails=values[3];
		
		setPhoneVerifyStatus(true);
		WelcomePage wp=new WelcomePage(driver);
		wp.changeUserTemp(loginId);
		wp.selectUser(loginId);
		
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp = new MerchantHomePage(driver);		
		mhp.executePayment(data);
		mhp.selectApp(programName);
		mhp.enterSecurePin(data);	
		
		Generic.abortPhoneVerification(driver);			
		
		Generic.switchToApp(driver);		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);
		
		//CSR.verifyInitSuccess(data.split(",")[0]);
		CSR.verifyStatusOnTxnDesc("Init Success");
		
		Generic.switchToMerchant(driver);
		
		//---------------- Modify data for insufficient balance -----------------//
		
		data=data.replace(data.split(",")[2],((int)balanceBeforeTransaction)+5+"00");		
		
		//---------------------------------------------------------//
		
		
		mhp.executePayment(data);
		mhp.selectApp(programName);		
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.verifyInsufficientBalance(cardDetails);			
		
		Generic.switchToAppWithState(driver);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.refreshTransactionList();
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		//CSR.verifyInitSuccess(data.split(",")[0]);
		CSR.verifyStatusOnTxnDesc("Init Success");
	}

}
