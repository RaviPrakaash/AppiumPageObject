package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.MobileRechargePage;

/**
 * The Class DCR_016 Enter Less than 10 Digits.
 */
public class DCR_016 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_016");
	
	/**
	 * Dcr 016.
	 */
	@Test
	public void DCR_016()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_016");

		gotoDataCardRecharge(data);

		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		drp.verifyLessThan10DigitDataCardNumber();
		
	}
}
