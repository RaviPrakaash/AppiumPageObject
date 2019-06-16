package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRecharge3DSPage;
import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DTHR_031 Verify successful transaction for External card.
 */
public class DTHR_031 extends BaseTest // ==== Verify successful transaction for External card ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_031"); 
	
	/**
	 * Dthr 031.
	 */
	@Test
	public void DTHR_031()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_031");
		gotoDTHRecharge(data);	

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
