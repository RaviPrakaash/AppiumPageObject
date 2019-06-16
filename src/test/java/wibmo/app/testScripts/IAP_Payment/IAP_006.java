package wibmo.app.testScripts.IAP_Payment;

import org.testng.Reporter;
import org.testng.annotations.Test;

import library.Generic;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WibmoSDKPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class IAP_006 Complete transaction using any card with wibmo app installed and User not logged in
 */
public class IAP_006 extends BaseTest // ==== Complete transaction using any card with wibmo app installed and User not logged in ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAP_006");	
		
		/**
		 * Iap 006.
		 */
		@Test
		public void IAP_006() 
		{
			Reporter.log(getTestScenario());
			String data=getTestData("IAP_006");
		
			Generic.userNotLoggedInToApp(driver, data);
			
			Generic.wait(2);
			double amt=Double.parseDouble(data.split(",")[2]);		
			String cardDetails=data.split(",")[3];
			
			Generic.switchToMerchant(driver);
			
			MerchantHomePage mhp=new MerchantHomePage(driver);
			mhp.executePayment(data);
			mhp.selectApp(programName); // programName will be initialised from config.xls file
			mhp.enterSecurePin(data);
			
			Generic.merchantVerifiedLogin(driver, data);
			
			WibmoSDKPage wsp=new WibmoSDKPage(driver);
			wsp.approvePayment(cardDetails);
			
			MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
			mp3p.executeMerchantPayment(cardDetails);	
			
			mhp.verifyMerchantSuccess();					
		}
}
