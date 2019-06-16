package wibmo.app.testScripts.IAP_Payment;

import org.testng.Reporter;
import org.testng.annotations.Test;

import library.Generic;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAP_008 Complete transaction using Wibmo card with wibmo app installed and User logged in
 */
public class IAP_008 extends BaseTest // ==== Complete transaction using Wibmo card with wibmo app installed and User logged in ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAP_008");	
	
	/**
	 * Iap 008.
	 */
	@Test
	public void IAP_008()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_008");
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++];
		String securePin =values[i++];
		String cardDetails=data.split(",")[3];
		
		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);		
		Generic.verifyLogin(driver,data);		
			
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config.xls file
		mhp.verifySecurePinAbsent(data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);	
		
		mhp.verifyMerchantSuccess();
	}
}
