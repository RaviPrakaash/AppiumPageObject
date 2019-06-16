package wibmo.app.testScripts.IAP_Payment;

import org.testng.Reporter;
import org.testng.annotations.Test;

import library.Generic;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAP_019 Payment via IAP with a different HDFC wibmo a/c ITP card with app installed
 */
public class IAP_019 extends BaseTest // ==== Payment via IAP with a different HDFC wibmo a/c ITP card with app installed ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAP_019");	
	
	/**
	 * Iap 019.
	 */
	@Test
	public void IAP_019() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_019");
		double amt=Double.parseDouble(data.split(",")[2]);		
		String cardDetails=data.split(",")[3];		
		
		Generic.userNotLoggedInToApp(driver, data);
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config.xls file		
		mhp.enterSecurePin(data);
		
		Generic.merchantVerifiedLogin(driver, data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.verifyMerchantPayment3DS();
		mp3p.executeMerchantPayment(cardDetails);	
		
		mhp.verifyMerchantSuccess();		
	}

}
