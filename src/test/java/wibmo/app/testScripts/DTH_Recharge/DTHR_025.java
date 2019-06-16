package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRecharge3DSPage;
import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DTHR_025 Verify recharge Payment session timeout.
 */
public class DTHR_025 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_025");
	
	/**
	 * Dthr 025.
	 */
	@Test
	public void DTHR_025() // ==== Recharge Payment session timeout ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_025");
		gotoDTHRecharge(data);	
		
		DTHRechargePage drp=new DTHRechargePage(driver);
		drp.enterValues(data);
		drp.clickPayNow();
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.verifyPaymentPage();
		wsp.waitForSessionTimeout(4);		
		
		RechargeTransactionFinalPage rtfp = new RechargeTransactionFinalPage(driver);
		rtfp.verifyTransactionFailMsg();		
	}
}
