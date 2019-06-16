package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import library.Generic;
import wibmo.app.pagerepo.DTHRecharge3DSPage;
import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class DTHR_030 Verify 3DS Authentication failed error message for Program Card.
 */
public class DTHR_030 extends BaseTest // ==== Verify 3DS Authentication failed error message for Program Card ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_030"); 
	
	/**
	 * Dthr 030.
	 */
	@Test
	public void DTHR_030() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_030");		
		
		Generic.loginUntrustedDevice(driver,data);
		
		HomePage hp=new HomePage(driver);
		hp.gotoRecharge();
		
		RechargePage rp=new RechargePage(driver);
		rp.gotoDthRecharge();		
	
		DTHRechargePage drp = new DTHRechargePage(driver);
		drp.enterValues(data);
		drp.clickPayNow();		
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[5]);
		
		DTHRecharge3DSPage dth3p = new DTHRecharge3DSPage(driver);
		if(Generic.checkWalletITPDirect(data.split(",")[5])) return;
		
		dth3p.verifyDTHRecharge3DS();
		dth3p.submitDTHRecharge3DS(data.split(",")[5].split(":")[1]);
		dth3p.verifyAuthenticationErrMsg();
		
		
	}
}
