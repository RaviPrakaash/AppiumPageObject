package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;
import library.Generic;
import wibmo.app.pagerepo.DTHRecharge3DSPage;
import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class DTHR_033 Verify transaction Cancel for Any card.
 */
public class DTHR_033 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_033");	// ==== Verify transaction Cancel for Any card ==== //
	
	/**
	 * Dthr 033.
	 */
	@Test
	public void DTHR_033()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_033");		
		
		Generic.loginUntrustedDevice(driver, data);
		
		HomePage hp=new HomePage(driver);
		hp.gotoRecharge();
		
		RechargePage rp=new RechargePage(driver);
		rp.gotoDthRecharge();

		DTHRechargePage drp=new DTHRechargePage(driver);
		drp.enterValues(data);		
		drp.clickPayNow();

		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[5]);

		DTHRecharge3DSPage dth3p = new DTHRecharge3DSPage(driver);
		if(Generic.checkWalletITPDirect(data.split(",")[5])) return;
		dth3p.verifyDTHRecharge3DS();
		dth3p.cancelDTHRecharge3DS();
		
		RechargeTransactionFinalPage rtfp = new RechargeTransactionFinalPage(driver);
		rtfp.verifyDTHRechargeFailMsg();
	}
}
