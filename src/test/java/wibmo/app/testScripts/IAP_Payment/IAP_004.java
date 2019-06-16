package wibmo.app.testScripts.IAP_Payment;

import org.testng.Reporter;
import org.testng.annotations.Test;

import library.Generic;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAP_004 Verify 3DS Page with app installed,user not logged in
 */
public class IAP_004 extends BaseTest  // ==== Verify 3DS Page with app installed,user not logged in ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAP_004");	
	
	/**
	 * Iap 004.
	 */
	@Test	
	public void IAP_004()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_004");	
		String cardDetails=data.split(",")[3];		
		
		Generic.userNotLoggedInToApp(driver, data);			
		
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config.xls file
		mhp.enterSecurePin(data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.verifyMerchantPayment3DS();
	}
}
