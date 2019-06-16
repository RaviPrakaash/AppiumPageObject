package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_056 Click on Operator Drop down.
 */
public class DCR_056 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_056");
	
	/**
	 * Dcr 056.
	 */
	@Test
	public void DCR_056()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_056");
		gotoDataCardRecharge(data);

		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.verifyDataCardOperatorList();
	}
}
