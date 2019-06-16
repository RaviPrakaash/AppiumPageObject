package wibmo.app.testScripts.SmokeTest;

import library.CSR;

import org.testng.Reporter;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_029 CSR - AM Transaction History – Fetch Report.
 */
public class SMT_029 extends BaseTest // ==== CSR - AM Transaction History – Fetch Report ==== //
{	
	/** The tc. */
	public String TC=getTestScenario("SMT_029");	
	
	/**
	 * Smt 029.
	 */
	@Test
	public void SMT_029()
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_029");		
		String loginId=data;
		CSR.verifyAMTransactionReport(loginId);
	}
}
