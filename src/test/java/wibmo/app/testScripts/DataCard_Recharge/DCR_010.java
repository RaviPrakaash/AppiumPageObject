package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_010 Select Data card contact number with 0.
 */
public class DCR_010 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_010");
	
	/**
	 * Dcr 010.
	 */
	@Test
	public void DCR_010()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_010");
		gotoDataCardRecharge(data);

		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.selectContacts(data);

		DataCardRechargePayPage dcrp= new DataCardRechargePayPage(driver);
		dcrp.verifyDataCardNumber(data);
	}
}
