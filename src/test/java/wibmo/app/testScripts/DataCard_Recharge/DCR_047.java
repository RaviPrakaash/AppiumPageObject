package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_047 Select valid number operator and amount and click on Cancel button, For Prepaid option.
 */
public class DCR_047 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_047");
	
	/**
	 * Dcr 047.
	 */
	@Test
	public void DCR_047()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_047");
		gotoDataCardRecharge(data);	

		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);

		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.enterValues(data);
		dcrp.clickCancel();
		drp.verifyContactPage();
	}
}
