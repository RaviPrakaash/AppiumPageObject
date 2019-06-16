package wibmo.app.testScripts.SmokeTest;

import library.CSR;
import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class SMT_035 IAP txn V2 - 3DS. Amount Known True, Charge Later True, ChargeOnCheckTrue. No Balance Verification(3DS external card)
 */
public class SMT_035 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_035");	
	
	/**
	 * Smt 035.
	 */
	@Test
	public void SMT_035() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_035");
		String loginId=data.split(",")[0];
		String cardDetails=data.split(",")[3];		
		String chargeStatus=data.split(",")[8];
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		Generic.wait(3);
		
		Generic.switchToMerchant(driver);
		
		// ==== Execute Merchant payment ==== //
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName initialised from config
		mhp.enterSecurePin(data);
		
		//Generic.merchantUnverifiedLogin(driver,data);  //Use when any card is used for 3DS
		Generic.merchantVerifiedLogin(driver, data);    //Used when External card is used for 3DS
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);	
		
		// ==== Verify Transaction Success ==== //
		transactionStatusMsg=mhp.verifyMerchantSuccess();		
		
		// ==== Charge On Staus check true ==== //
		mhp.clickCheckStatusDataPickup(chargeStatus);		
		Generic.wait(2);
		
		mhp.checkSUF("authenticationSuccessful,chargeSuccessful");  
		
		CSR.verifyIAPTXNDetails(transactionStatusMsg);	
		
	}

}
