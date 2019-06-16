package wibmo.app.testScripts.IAP_Payment;

import org.testng.Reporter;
import org.testng.annotations.Test;

import library.Generic;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAP_016 Payment via ITP card with app installed
 */
public class IAP_016 extends BaseTest // ==== Payment via ITP card with app installed ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAP_016");	
	
	/**
	 * Iap 016.
	 */
	@Test
	public void IAP_016() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_016");
		double amt=Double.parseDouble(data.split(",")[2]);		
		String cardDetails=data.split(",")[3];			
		
		Generic.preconditionITP(driver,data);
		//Generic.logout(driver);
		
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); 	
		//mhp.enterSecurePin(data);
		
		//Generic.merchantVerifiedLogin(driver, data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		mhp.verifyITP();			
	}
}
