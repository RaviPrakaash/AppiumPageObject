package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_025 Click on Operator  Drop down.
 */
public class DCR_025 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_025");
	
	/**
	 * Dcr 025.
	 */
	@Test
	public void DCR_025()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_025");
		gotoDataCardRecharge(data);

		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.verifyDataCardOperatorList();
	}
}
