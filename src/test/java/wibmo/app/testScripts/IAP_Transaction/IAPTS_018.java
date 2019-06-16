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
 * The Class IAPTS_018 App-SDK Successful IAP txn but no data pick up done, Verify balance. Static Merchant.
 */
public class IAPTS_018 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_018");
	
	/**
	 * Iapts 018.
	 */
	@Test
	public void IAPTS_018()
	{				
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_018"),transactionStatusMsg;
		
		String[] values = data.split(",");
		String cardDetails=values[3];
		double amt=Double.parseDouble(values[2]);

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
		//mhp.performDataPickup();	No Data Pickup
		
		Generic.switchToAppWithState(driver);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.refreshTransactionList();
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt*0.01,balanceAfterTransaction); 
		
		CSR.verifyIAPTransactionDataPickupFailure(transactionStatusMsg);
	}

}
