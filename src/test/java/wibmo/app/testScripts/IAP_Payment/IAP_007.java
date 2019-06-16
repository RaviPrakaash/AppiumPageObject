package wibmo.app.testScripts.IAP_Payment;

import org.testng.Reporter;
import org.testng.annotations.Test;
import library.Generic;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class IAP_007 Complete transaction using any card with wibmo app installed and User logged in
 */
public class IAP_007 extends BaseTest // ==== Complete transaction using any card with wibmo app installed and User logged in ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAP_007");	
	
	/**
	 * Iap 007.
	 */
	@Test
	public void IAP_007() 
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("IAP_007");

		String[] values = data.split(",");
		String loginId=values[i++],securePin =values[i++],cardDetails=data.split(",")[3];
		
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
