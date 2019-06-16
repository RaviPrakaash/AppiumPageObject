package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_009 Select Data card contact number with 91.
 */
public class DCR_009 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_009");
	
	/**
	 * Dcr 009.
	 */
	@Test
	public void DCR_009()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_009");
		gotoDataCardRecharge(data);

		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.selectContacts(data);

		DataCardRechargePayPage dcrp= new DataCardRechargePayPage(driver);
		dcrp.verifyDataCardNumber(data);
	}
}
