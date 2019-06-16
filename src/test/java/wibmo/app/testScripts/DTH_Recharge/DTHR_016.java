package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRechargePage;

// TODO: Auto-generated Javadoc
/**
 * The Class DTHR_016 Enter valid amount and Check Pay Now button enabled.
 */
public class DTHR_016 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_016");
	
	/**
	 * Dthr 016.
	 */
	@Test
	public void DTHR_016() // ==== Enter Valid amount in amount Field I.e <=5 digits , Pay Now button should be enabled ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_016");
		gotoDTHRecharge(data);	
		
		DTHRechargePage drp=new DTHRechargePage(driver);
		drp.enterValues(data);
		drp.verifyPayNowButtonEnable();
	}
}
