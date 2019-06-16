package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.MobileRecharge3DSPage;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class MR_079 Click on Approve in payment screen.
 */
public class MR_079 extends BaseTest // ==== Click on Approve on the payment screen ==== // 
{
	
	/** The tc. */
	public String TC=getTestScenario("MR_079");
	
	/**
	 * Mr 079.
	 */
	@Test
	public void MR_079()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("MR_079");
		gotoMobileRecharge(data);

		MobileRechargePage mrp = new MobileRechargePage(driver);
		mrp.enterMobileNumber(data);

		MobileRechargePayPage mrpp = new MobileRechargePayPage(driver);
		mrpp.enteringAmountToRecharge(data);

		WibmoSDKPage wsp = new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[6]);

		MobileRecharge3DSPage mr3p = new MobileRecharge3DSPage(driver);
		mr3p.verifyMobileRecharge3DS();	
	}
}
