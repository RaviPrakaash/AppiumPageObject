package wibmo.app.testScripts.IAP_Payment;

import org.testng.Reporter;
import org.testng.annotations.Test;

import library.Generic;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAP_003 Verify Payment screen with Amount,Card Details,Approve and Cancel buttons, app installed and user not logged in
 */
public class IAP_003 extends BaseTest // ==== Verify Payment screen with Amount,Card Details,Approve and Cancel buttons, app installed and user not logged in ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAP_003");	
	
	/**
	 * Iap 003.
	 */
	@Test
	public void IAP_003() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_003");
		String loginId=data.split(",")[0],amt=data.split(",")[2];
		
		Generic.userNotLoggedInToApp(driver, data);
				
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config.xls file
		mhp.enterSecurePin(data);
		
		Generic.merchantVerifiedLogin(driver, data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.verifyPaymentPage();
		wsp.verifyMerchantTransactionAmount(amt);
		wsp.verifyCardDetails();		
	}	

}
