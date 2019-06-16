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
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class DTHR_003 Enter valid data and click on Pay Now and verify successful transaction for Program card.
 */
public class DTHR_003 extends BaseTest
{	
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_003");
	
	/**
	 * Dthr 003.
	 */
	@Test
	public void DTHR_003() // ==== Enter valid data and click on Pay Now and verify successful transaction for Program card ==== // 
	{	
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_003");		
		
		Generic.loginUntrustedDevice(driver,data);
		
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
		dth3p.executeDTHRecharge(data.split(",")[5]);
		
		RechargeTransactionFinalPage rtfp=new RechargeTransactionFinalPage(driver);
		rtfp.verifyRechargeSuccess(data);
	}
}
