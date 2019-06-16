package wibmo.app.testScripts.SmokeTest;

import library.CSR;

import org.testng.Reporter;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_027 CSR - Wibmo User error report – Fetch Report.
 */
public class SMT_027 extends BaseTest // ==== CSR - Wibmo User error report – Fetch Report ==== //
{	
	/** The tc. */
	public String TC=getTestScenario("SMT_027");	
	
	/**
	 * Smt 027.
	 */
	@Test
	public void SMT_027()
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_027");		
		String loginId=data;
		CSR.verifyUserErrorReport(loginId);
	}

}
