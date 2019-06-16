package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_071.
 */
public class DCR_071 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_071");
	
	/**
	 * Dcr 071.
	 */
	@Test
	public void DCR_071() // ==== Recharge Payment session timeout==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_071");
		gotoDataCardRecharge(data);
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.enterValues(data);
		dcrp.clickPayNow();
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.verifyPaymentPage();
		wsp.waitForSessionTimeout(5);		
		
		RechargeTransactionFinalPage rtfp = new RechargeTransactionFinalPage(driver);
		rtfp.verifyTransactionFailMsg();		
	}
}
