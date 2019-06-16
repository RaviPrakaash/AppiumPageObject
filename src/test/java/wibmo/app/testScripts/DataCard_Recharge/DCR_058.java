package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_058 Click on pay now button without entering amount.
 */
public class DCR_058 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_058");
	
	/**
	 * Dcr 058.
	 */
	@Test
	public void DCR_058()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_058");
		gotoDataCardRecharge(data);	
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.enterValues(data);
		dcrp.verifyPayNowButtonDisable();
	}
}
