package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.MobileRechargePage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_007 Select less than 10 digit contact present in the phone.
 */
public class DCR_007 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_007");
	
	/**
	 * Dcr 007.
	 */
	@Test
	public void DCR_007()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_007");
		gotoDataCardRecharge(data);

		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.selectContacts(data);
		drp.verifyLessThan10DigitDataCardNumber();

	}
}
