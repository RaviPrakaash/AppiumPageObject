package wibmo.app.testScripts.SmokeTest;

import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * The Class SMT_004 Login – Phone Verification.
 */
public class SMT_004 extends BaseTest // === Login – Phone Verification ==== // 
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_004");
	
	/**
	 * Smt 004.
	 */
	@Test
	public void SMT_004()
	{
		Reporter.log(getTestScenario()); 
		String data=getTestData("SMT_004");
		
		Generic.preconditionITP(driver,data);
	}
}
