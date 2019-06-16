package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_046  Select valid number operator and amount and click on Pay Now button, For Prepaid option.
 */
public class DCR_046 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_046");
	
	/**
	 * Dcr 046.
	 */
	@Test
	public void DCR_046()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_046");
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
