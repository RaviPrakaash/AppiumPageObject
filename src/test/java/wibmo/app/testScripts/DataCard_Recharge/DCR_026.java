package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_026 Select any operator.
 */
public class DCR_026 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_026");
	
	/**
	 * Dcr 026.
	 */
	@Test
	public void DCR_026() // ==== Select any operator  ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_026");
		gotoDataCardRecharge(data);

		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.verifyOperatorSelect();
	}
}
