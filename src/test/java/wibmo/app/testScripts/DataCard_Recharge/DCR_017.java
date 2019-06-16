package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_017 Enter more than 10 Digits.
 */
public class DCR_017 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_017");
	
	/**
	 * Dcr 017.
	 */
	@Test
	public void DCR_017()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_017");

		gotoDataCardRecharge(data);

		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		DataCardRechargePayPage drcp = new DataCardRechargePayPage(driver);
		drcp.verifyGreaterThan10DigitNumber(data);
		
	}
}
