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
 * The Class DCR_074 Click on Cancel button in the payment screen.
 */
public class DCR_074 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_074");
	
	/**
	 * Dcr 074.
	 */
	@Test
	public void DCR_074() // ==== Click on cancel on the payment screen ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_074");
		gotoDataCardRecharge(data);	
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.enterValues(data);
		dcrp.clickPayNow();
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.cancelRecharge(data.split(",")[7]);	
		
		RechargeTransactionFinalPage rtfp=new RechargeTransactionFinalPage(driver);
		rtfp.verifyTransactionFailMsg();
	}
}
