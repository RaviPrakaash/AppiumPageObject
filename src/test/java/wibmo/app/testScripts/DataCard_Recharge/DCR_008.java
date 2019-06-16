package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_008 Select Data card contact number with +.
 */
public class DCR_008 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_008");
	
	/**
	 * Dcr 008.
	 */
	@Test
	public void DCR_008()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_008");
		gotoDataCardRecharge(data);
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.selectContacts(data);

		DataCardRechargePayPage dcrp= new DataCardRechargePayPage(driver);
		dcrp.verifyDataCardNumber(data);
	}
}
