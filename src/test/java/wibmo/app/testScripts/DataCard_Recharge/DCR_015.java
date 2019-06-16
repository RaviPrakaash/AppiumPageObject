package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_015 Enter 10 Digit number.
 */
public class DCR_015 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_015");
	
	/**
	 * Dcr 015.
	 */
	@Test
	public void DCR_015()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_015");

		gotoDataCardRecharge(data);

		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);

		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.verifyDataCardNumber(data);
		dcrp.verifyDataCardOperator(data);
	}
}
