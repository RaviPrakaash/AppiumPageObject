package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class MR_080 Click on cancel in payment screen.
 */
public class MR_080 extends BaseTest // ==== Click on cancel on the payment screen ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("MR_080");
	
	/**
	 * Mr 080.
	 */
	@Test
	public void MR_080()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("MR_080");
		gotoMobileRecharge(data);

		MobileRechargePage mrp = new MobileRechargePage(driver);
		mrp.enterMobileNumber(data);

		MobileRechargePayPage mrpp = new MobileRechargePayPage(driver);
		mrpp.enteringAmountToRecharge(data);
		
		WibmoSDKPage wsp = new WibmoSDKPage(driver);
		wsp.cancelRecharge(data.split(",")[6]);
		
		RechargeTransactionFinalPage rtfp = new RechargeTransactionFinalPage(driver);
		rtfp.verifyTransactionFailMsg();		
	}
}
