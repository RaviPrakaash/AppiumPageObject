package wibmo.app.testScripts.IAP_Transaction;

import library.CSR;
import library.Generic;
import library.Log;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class IAPTS_011   App-SDK IAP transaction when message hash fails.Static merchant.
 */
public class IAPTS_011 extends BaseTest // ==== App-SDK IAP transaction when message hash fails ==== // 
{	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_011");		
	
	/**
	 * Iapts 011.
	 */
	@Test
	public void IAPTS_011() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_011");
		
		String loginId=data.split(",")[0],cardDetails=data.split(",")[3]; // necessary to maintain data index
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);	
		
		Generic.switchToMerchant(driver);
		//String correctMerchantAppId=Generic.setIncorrectMerchantAppId();		
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.setHashFail();
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialized from config.xls file		
		mhp.verifyFailureMessage();		
		
		//Generic.setCorrectMerchantAppId(correctMerchantAppId);		
		
		Generic.switchToAppWithState(driver);	
		
		AddSendPage asp=new AddSendPage(driver);
		asp.refreshTransactionList();			
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		CSR.verifyHashFailed(loginId); // 
	}
	
	@AfterMethod
	public void postcondition()
	{
		Log.info("== TC complete . Executing Postcondition : Setting correct Hash Value in Merchant App ==");
		Generic.setCorrectHashMerchantApp(driver);
	}
}
