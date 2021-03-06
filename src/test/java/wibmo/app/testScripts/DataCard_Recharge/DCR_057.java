package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_057 Select any operator.
 */
public class DCR_057 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_057");
	
	/**
	 * Dcr 057.
	 */
	@Test
	public void DCR_057() // ==== Select any operator  ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_057");
		gotoDataCardRecharge(data);

		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		
		DataCardRechargePayPage drcp = new DataCardRechargePayPage(driver);
		drcp.verifyOperatorSelect();
	}
}
