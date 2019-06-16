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
 * The Class SMT_034 IAP txn V2 - ITP. (Taxi) Amount Known False, Charge Later True, ChargeOnCheckTrue
 */
public class SMT_034 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_034");	
	
	/**
	 * Smt 034.
	 */
	@Test
	public void SMT_034() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_034");
		String values[]=data.split(",");
		
		double amt=Double.parseDouble(values[2]);		
		String cardDetails=values[3];		
		double balanceBeforeTransaction=0;
		String chargeStatus=values[8],chargeAmt=values[9];
		
		
		// ==== Check balance and Verify Phone ==== //
		balanceBeforeTransaction = Generic.checkWalletBalance(driver, data);
		Generic.preconditionITP(driver, data);
		driver.navigate().back();		
		
		SettingsPage sp = new SettingsPage(driver);
		sp.gotoAddSendPage();		
		
		Generic.wait(5); // Wait for Add/Send to load
		Generic.switchToMerchant(driver);
		
		
		// ==== Execute Merchant payment ==== //
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName initialised from config.xls file or property file		
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		mhp.verifyITP();
		transactionStatusMsg=mhp.verifyMerchantSuccess();
		
		mhp.clickCheckStatusDataPickup(chargeStatus);
		mhp.enterChargeAmount(chargeAmt);
		Generic.wait(2);
		
		mhp.checkSUF("authenticationSuccessful,chargeSuccessful");  
		
		CSR.verifyIAPTXNDetails(transactionStatusMsg);		
		
		if(Generic.containsIgnoreCase(cardDetails, programName) && !checkEnv("qa"))
		{
			Generic.switchToAppWithState(driver);	
			
			AddSendPage asp=new AddSendPage(driver);
			asp.refreshTransactionList();
			
			double balanceAfterTransaction=new AddSendPage(driver).verifyBalance();	 // ITP/Program Card will be used	
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,Double.parseDouble(chargeAmt)*0.01,balanceAfterTransaction);
		}
	}

}
