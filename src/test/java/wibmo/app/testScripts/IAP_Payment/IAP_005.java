package wibmo.app.testScripts.IAP_Payment;

import org.testng.Reporter;
import org.testng.annotations.Test;

import library.CSR;
import library.Generic;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class IAP_005 Verify CVV screen with app installed,user not logged in
 */
public class IAP_005 extends BaseTest // ==== Verify CVV screen with app installed,user not logged in ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAP_005");	
	
	/**
	 * Iap 005.
	 */
	@Test
	public void IAP_005() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_005");
		String amt=data.split(",")[2],cardDetails=data.split(",")[3];
		
		String cvvStatus=Generic.parseCVVStatus(data);
		CSR.setCVVStatus(driver, cardDetails, cvvStatus);
		
		Generic.userNotLoggedInToApp(driver,data);
				
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config.xls file
		mhp.enterSecurePin(data);
		
		Generic.merchantVerifiedLogin(driver, data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.submitPayment(cardDetails.split(":")[1]);
		mp3p.verifyCVV();		
	}
}
