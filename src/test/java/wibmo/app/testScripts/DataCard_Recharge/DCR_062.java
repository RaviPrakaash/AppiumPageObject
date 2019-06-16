package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_062 Select valid number, operator and amount and click on Cancel button, for postpaid option.
 */
public class DCR_062 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_062");
	
	/**
	 * Dcr 062.
	 */
	@Test
	public void DCR_062()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_062");
		gotoDataCardRecharge(data);	

		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);

		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.enterValues(data);
		dcrp.clickCancel();
		drp.verifyContactPage();
	}
}
