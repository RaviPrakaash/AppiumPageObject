package wibmo.app.testScripts.SmokeTest;

import library.CSR;
import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_010 IAP txn - ITP.
 */
public class SMT_010 extends BaseTest // ==== IAP txn - ITP ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_010");	
	
	/**
	 * Smt 010.
	 */
	@Test
	public void SMT_010() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_010");
		double amt=Double.parseDouble(data.split(",")[2]);		
		String cardDetails=data.split(",")[3];		
		double balanceBeforeTransaction=0;			
		
		balanceBeforeTransaction = Generic.checkWalletBalance(driver, data);
		Generic.preconditionITP(driver, data);
		driver.navigate().back();
		
		SettingsPage sp = new SettingsPage(driver);
		sp.gotoAddSendPage();		
		
		Generic.wait(5); // Wait for Add/Send to load
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config.xls file or property file		
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		mhp.verifyITP();
		transactionStatusMsg=mhp.verifyMerchantSuccess();		

		Generic.switchToAppWithState(driver);	
		
		AddSendPage asp=new AddSendPage(driver);
		asp.refreshTransactionList();
		
		double balanceAfterTransaction=new AddSendPage(driver).verifyBalance();		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt*0.01,balanceAfterTransaction);			
	}

}
