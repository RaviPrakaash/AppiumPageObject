package wibmo.app.testScripts.SmokeTest;

import library.CSR;

import org.testng.Reporter;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_030 CSR - Inflow Transaction History – Fetch Report.
 */
public class SMT_030 extends BaseTest // === CSR - Inflow Transaction History – Fetch Report ==== //
{	
	/** The tc. */
	public String TC=getTestScenario("SMT_030");	
	
	/**
	 * Smt 030.
	 */
	@Test
	public void SMT_030()
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_030");		
		String loginId=data;
		//CSR.verifyInflowTransactionReport(loginId);
	}
}
