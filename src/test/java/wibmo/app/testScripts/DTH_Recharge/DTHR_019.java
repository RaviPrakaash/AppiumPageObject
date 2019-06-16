package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRechargePage;

// TODO: Auto-generated Javadoc
/**
 * The Class DTHR_019 Enter amount greater than 5 digits.
 */
public class DTHR_019 extends BaseTest // === Enter > 5 digits in amount field ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_019");
	
	/**
	 * Dthr 019.
	 */
	@Test
	public void DTHR_019() // ==== Try to enter > 5 digits in amount field ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_019");
		gotoDTHRecharge(data);	
		
		DTHRechargePage drp=new DTHRechargePage(driver);
		drp.verify5DigitAmount(data);		
	}
}
