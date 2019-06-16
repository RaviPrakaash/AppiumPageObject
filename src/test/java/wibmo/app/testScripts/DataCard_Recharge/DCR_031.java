package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_031 Enter Valid amount in amount field with amount less than or equal to 5 digits.
 */
public class DCR_031 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_031");
	
	/**
	 * Dcr 031.
	 */
	@Test
	public void DCR_031()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_031");
		gotoDataCardRecharge(data);	
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.enterValues(data);
		dcrp.verifyPayNowButtonEnable();
	}
}
