package wibmo.app.testScripts.IAP_Payment;

import org.testng.Reporter;
import org.testng.annotations.Test;

import library.Generic;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAP_017 Payment via ITP card with app installed and check balance deducted
 */
public class IAP_017 extends BaseTest // ==== Payment via ITP card with app installed and check balance deducted ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAP_017");	
	
	/**
	 * Iap 017.
	 */
	@Test
	public void IAP_017() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_017");
		double amt=Double.parseDouble(data.split(",")[2]);		
		String cardDetails=data.split(",")[3];	
		
		//double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);	
		Generic.preconditionITP(driver,data);
		//Generic.logout(driver);
		
		driver.navigate().back();
		SettingsPage sp=new SettingsPage(driver);
		sp.gotoAddSendPage();
		
		AddSendPage asp=new AddSendPage(driver);
		double balanceBeforeTransaction=asp.verifyBalance();
		
		Generic.switchToMerchant(driver);		
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config file		
		//mhp.enterSecurePin(data);
		
		//Generic.merchantVerifiedLogin(driver, data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		mhp.verifyITP();
		mhp.verifyMerchantSuccess();
		
		if(checkEnv("qa")) return;
		Generic.switchToAppWithState(driver);		
		
		//double balanceAfterTransaction=Generic.checkWalletBalance(driver,data);
		
		asp.refreshTransactionList();
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt*0.01,balanceAfterTransaction);			
	}

}
