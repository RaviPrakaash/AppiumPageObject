package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRecharge3DSPage;
import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DTHR_026 Click Pay Now after 5 minutes.
 */
public class DTHR_026 extends BaseTest // ==== Click Pay Now after 5 minutes ==== // 
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_026");
	
	/**
	 * Dthr 026.
	 */
	@Test
	public void DTHR_026() 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_026");
		gotoDTHRecharge(data);	
		
		DTHRechargePage drp=new DTHRechargePage(driver);
		drp.enterValues(data);
		drp.waitFor5Mins();		// change to waitFor(data.split(",")[6])
		drp.clickPayNow();
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[5]);	
		
		// Verify login page , update code after receiving actual timeout 
		
		DTHRecharge3DSPage dr3p=new DTHRecharge3DSPage(driver);
		dr3p.verifyDTHRecharge3DS();		
	}
}
