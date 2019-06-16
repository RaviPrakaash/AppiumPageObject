package wibmo.app.testScripts.IAP_Payment;

import org.testng.Reporter;
import org.testng.annotations.Test;

import library.Generic;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class IAP_002 Complete transaction using Wibmo card with wibmo app installed and User not logged in
 */
public class IAP_002 extends BaseTest // ==== Complete transaction using Wibmo card with wibmo app installed and User not logged in ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAP_002");	
	
	/**
	 * Iap 002.
	 */
	@Test
	public void IAP_002() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_002");
	
		double amt=Double.parseDouble(data.split(",")[2]),balanceBeforeTransaction=0.0;		
		String cardDetails=data.split(",")[3];
		
		if(!checkEnv("qa"))
		{		
			balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);	
			Generic.logout(driver); 
			Generic.wait(5);
		}
		else
		{
			Generic.userNotLoggedInToApp(driver, data);
		}
		
		
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp = new MerchantHomePage(driver);		

		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config.xls file		
		mhp.enterSecurePin(data);
		
		Generic.merchantVerifiedLogin(driver, data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);	
		
		mhp.verifyMerchantSuccess();
		
		if(!checkEnv("qa"))
		{
			Generic.switchToApp(driver);		
			double balanceAfterTransaction=Generic.checkWalletBalance(driver,data);
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt*0.01,balanceAfterTransaction);	
		}
		
			
	}

}
