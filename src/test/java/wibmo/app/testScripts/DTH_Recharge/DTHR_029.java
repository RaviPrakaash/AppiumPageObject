package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;
import library.Generic;
import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class DTHR_029 Click Cancel on payment screen for Program card.
 */
public class DTHR_029 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_029");
	
	/**
	 * Dthr 029.
	 */
	@Test
	public void DTHR_029() // ==== Click on cancel on the payment screen ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_029");		
		
		Generic.loginUntrustedDevice(driver, data);
		
		HomePage hp=new HomePage(driver);
		hp.gotoRecharge();
		
		RechargePage rp=new RechargePage(driver);
		rp.gotoDthRecharge();
		
		DTHRechargePage drp=new DTHRechargePage(driver);
		drp.enterValues(data);	
		drp.clickPayNow();
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.cancelRecharge(data.split(",")[5]);	
		
		RechargeTransactionFinalPage rtfp=new RechargeTransactionFinalPage(driver);
		rtfp.verifyTransactionFailMsg();
	}
}
