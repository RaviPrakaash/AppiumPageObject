package wibmo.app.testScripts.SmokeTest;

import library.CSR;

import org.testng.Reporter;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_022 CSR - User Registration History – Fetch Report.
 */
public class SMT_022 extends BaseTest // ==== CSR - User Registration History – Fetch Report ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_022");	
	
	/**
	 * Smt 022.
	 */
	@Test
	public void SMT_022()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_022");		
		CSR.verifyDBEntry(driver, data);		
	}

}
