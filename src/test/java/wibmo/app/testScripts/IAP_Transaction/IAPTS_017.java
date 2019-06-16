package wibmo.app.testScripts.IAP_Transaction;

import library.CSR;
import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.MerchantRegisterPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTS_017 App-SDK IAP transaction and Successful data pick up, Verify Balance. Static Merchant.
 *
 */
public class IAPTS_017 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_017");
	
	/**
	 * Iapts 017.
	 */
	@Test
	public void IAPTS_017()
	{				
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_017"),transactionStatusMsg;
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++],securePin =values[i++],cardDetails=values[3];
		double amt=Double.parseDouble(values[i++]);

		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);
		Generic.switchToMerchant(driver);		
		
		MerchantHomePage mhp = new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);		
		
		MerchantPayment3DSPage mp3p = new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);
		
		transactionStatusMsg=mhp.verifyMerchantSuccess();	
		mhp.performDataPickup();		
		
		Generic.switchToAppWithState(driver);	
		
		AddSendPage asp=new AddSendPage(driver);
		asp.refreshTransactionList();
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt*0.01,balanceAfterTransaction);
		
		CSR.verifyIAPTransactionDataPickupSuccess(transactionStatusMsg);		
	}
	

}
