package wibmo.app.testScripts.IAP_Payment;

import org.testng.Reporter;
import org.testng.annotations.Test;

import library.Generic;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class IAP_023 Payment via IAP with  an HDFC wibmo ITP card with app installed but from registered device with a different SIM(check trust device)
 */
public class IAP_023 extends BaseTest // ==== Payment via IAP with  an HDFC wibmo ITP card with app installed but from registered device with a different SIM(check trust device) ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAP_023");	
	
	/**
	 * Iap 023.
	 */
	@Test
	public void IAP_023()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_023");
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[0],cardDetails=values[3];
		
		WelcomePage wp = new WelcomePage(driver);		
		wp.selectUser(loginId);	
		
		// Using different SIM , so no need to use preconditionITP() 
			
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
