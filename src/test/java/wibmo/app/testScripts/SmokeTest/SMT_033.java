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
 * The Class SMT_033 IAP txn V2 - ITP. Amount Known True, Charge Later True, ChargeOnCheckTrue
 */
public class SMT_033 extends BaseTest // ==== IAP txn V2 - ITP. Amount Known True, Charge Later True , ChargeOnStaus True  ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_033");	
	
	/**
	 * Smt 033.
	 */
	@Test
	public void SMT_033() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_033");
		double amt=Double.parseDouble(data.split(",")[2]);		
		String cardDetails=data.split(",")[3];		
		double balanceBeforeTransaction=0;
		String chargeStatus=data.split(",")[8];
		
		balanceBeforeTransaction = Generic.checkWalletBalance(driver, data);
		Generic.preconditionITP(driver, data);
		driver.navigate().back();
		
		SettingsPage sp = new SettingsPage(driver);
		sp.gotoAddSendPage();		
		
		Generic.wait(5); // Wait for Add/Send to load
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName initialised from config.xls file or property file		
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		mhp.verifyITP();
		transactionStatusMsg=mhp.verifyMerchantSuccess();
		
		mhp.clickCheckStatusDataPickup(chargeStatus);		
		Generic.wait(2);
		
		mhp.checkSUF("authenticationSuccessful,chargeSuccessful"); // may require further depth of validation using overloaded function 
		
		CSR.verifyIAPTXNDetails(transactionStatusMsg);		
		
		if(Generic.containsIgnoreCase(cardDetails, programName) && !checkEnv("qa"))
		{
			Generic.switchToAppWithState(driver);	
			
			AddSendPage asp=new AddSendPage(driver);
			asp.refreshTransactionList();
			
			double balanceAfterTransaction=new AddSendPage(driver).verifyBalance();	 // ITP/Program Card will be used	
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt*0.01,balanceAfterTransaction);
		}
	}

}
