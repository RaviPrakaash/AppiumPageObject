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
 * The Class IAP_022 Payment via IAP with HDFC wibmo ITP card with app installed but from unverified device(uncheck trust device)
 */
public class IAP_022 extends BaseTest // ==== Payment via IAP with HDFC wibmo ITP card with app installed but from unverified device(uncheck trust device) ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAP_022");	
	
	/**
	 * Iap 022.
	 */
	@Test
	public void IAP_022()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_022");
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[0],cardDetails=values[3];
		
		WelcomePage wp = new WelcomePage(driver);
		wp.changeUserTemp(data);
		wp.selectUser(loginId);	
			
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config.xls file
		mhp.enterSecurePin(data);
		
		Generic.merchantUnverifiedLogin(driver, data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);
		
		mhp.verifyMerchantSuccess();
	}
}
