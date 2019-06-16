package wibmo.app.testScripts.IAP_Transaction;

import library.CSR;
import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTS_003 IAP transaction cancellation in card selection . Static Merchant
 */
public class IAPTS_003 extends BaseTest // ==== IAP transaction cancellation in card selection ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_003");

	/**
	 * Iapts 003.
	 */
	@Test
	public void IAPTS_003()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_003");		

		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);
		//Generic.logout(driver);
		
		Generic.switchToMerchant(driver);

		MerchantHomePage mhp = new MerchantHomePage(driver);		
		mhp.executePayment(data);
		mhp.selectApp(programName);
		//mhp.enterSecurePin(data);	
		
		WibmoSDKPage wsp = new WibmoSDKPage(driver);
		wsp.verifyCardSelectionWithCancel();
		mhp.verifyFailureMessage();		
		
		Generic.switchToAppWithState(driver);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.refreshTransactionList();
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		//CSR.verifyAbortStatus(data);
		CSR.verifyStatusOnTxnDesc("Aborted");
	}
}
