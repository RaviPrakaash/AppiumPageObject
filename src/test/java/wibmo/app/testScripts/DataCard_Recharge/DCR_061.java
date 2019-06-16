package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class DCR_061 Select vaild number, operator and amount and click on Pay now , for Postpaid option.
 */
public class DCR_061 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_061");
	
	/**
	 * Dcr 061.
	 */
	@Test
	public void DCR_061()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_061");
		gotoDataCardRecharge(data);	

		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);

		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.enterValues(data);
		dcrp.clickPayNow();

		WibmoSDKPage wsp = new WibmoSDKPage(driver);
		wsp.verifyPaymentPage();
	}
}
