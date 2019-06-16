package wibmo.app.testScripts.IAP_Transaction;

import library.CSR;
import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTS_008 	App-SDK IAP transaction hold on card selection, verify balance  .Static Merchant
 */
public class IAPTS_008 extends BaseTest  // ====  App-SDK IAP transaction hold on card selection ==== //
{ 
	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_008");	
	
	/**
	 * Iapts 008.
	 */
	@Test
	public void IAPTS_008() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_008");	
		String loginId=data.split(",")[0];
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);	
		
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName initialised from config.xls file				
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver); // Card Selection Page
		wsp.verifyPaymentPage();			
		
		Generic.switchToAppWithState(driver);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.refreshTransactionList();
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		//CSR.verifyInitSuccess(loginId);
		CSR.verifyStatusOnTxnDesc("Init Success");
	}	
}
