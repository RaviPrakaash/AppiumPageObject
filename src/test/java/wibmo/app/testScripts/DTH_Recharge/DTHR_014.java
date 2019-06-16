package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRechargePage;

/**
 * The Class DTHR_014 Select any DTH operator.
 */
public class DTHR_014 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_014");
	
	/**
	 * Dthr 014.
	 */
	@Test
	public void DTHR_014() // ==== Select any operator  ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_014");
		gotoDTHRecharge(data);	
		
		DTHRechargePage drp=new DTHRechargePage(driver);
		drp.verifyOperatorSelect();  		
	}
}
