package wibmo.app.testScripts.IAP_Payment;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAP_016A CVV prompt should not be displayed for ITP card
 */
public class IAP_016A extends BaseTest // ==== CVV prompt should not be displayed for ITP card ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAP_016A");	
	
	/**
	 * Iap 016a.
	 */
	@Test
	public void IAP_016A() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_016A");
		double amt=Double.parseDouble(data.split(",")[2]);		
		String cardDetails=data.split(",")[3];			
		
		Generic.preconditionITP(driver,data);
		
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); 			
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		mhp.verifyITP();			
	}

}
