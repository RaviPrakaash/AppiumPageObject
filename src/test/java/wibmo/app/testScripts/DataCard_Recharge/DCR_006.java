package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.MobileRechargePage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_006 Click on "Select From Contacts" & Select respective Data card number and operator.
 */
public class DCR_006 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_006");
	
	/**
	 * Dcr 006.
	 */
	@Test
	public void DCR_006()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_006");
		gotoDataCardRecharge(data);
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.selectContacts(data);

		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.verifyDataCardNumber(data);
		dcrp.verifyDataCardOperator(data);
	}
}
