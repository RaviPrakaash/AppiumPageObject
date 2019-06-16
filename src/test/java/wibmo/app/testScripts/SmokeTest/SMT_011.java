package wibmo.app.testScripts.SmokeTest;

import library.Generic;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class SMT_011 IAP txn - 3DS , Check Balance after transaction.
 */
public class SMT_011 extends BaseTest // ==== IAP txn - 3DS , Check Balance ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_011");	
	
	/**
	 * Smt 011.
	 */
	@Test
	public void SMT_011()
	{
		String data=getTestData("SMT_011");
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++],cardDetails=data.split(",")[3];	
		double amt=Double.parseDouble(data.split(",")[2]);
		double balanceBeforeTransaction=0.0;
		
		if(Generic.containsIgnoreCase(cardDetails, programName) && !checkEnv("qa"))
		{
			balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);
			Generic.logout(driver);
		}
		
		WelcomePage wp=new WelcomePage(driver);
		wp.changeUserTemp(data);
		wp.selectUser(loginId);
		Generic.wait(3);	
			
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config.xls file		
		mhp.enterSecurePin(data);
		
		Generic.merchantVerifiedLogin(driver,data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);	
		
		transactionStatusMsg=mhp.verifyMerchantSuccess();			
		
		if(Generic.containsIgnoreCase(cardDetails, programName) && !checkEnv("qa"))
		{
			Generic.switchToApp(driver);			
			double balanceAfterTransaction=Generic.checkWalletBalance(driver,data);
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt*0.01,balanceAfterTransaction);
		} 
		
		
	}
}
