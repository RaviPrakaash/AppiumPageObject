package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_043 Enter greater than 5 digits in amount field.
 */
public class DCR_043 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_043");
	
	/**
	 * Dcr 043.
	 */
	@Test
	public void DCR_043()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_043");
		gotoDataCardRecharge(data);	
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.enterValues(data);
		dcrp.verifyGreaterThan5DigitAmt();
	}
}
