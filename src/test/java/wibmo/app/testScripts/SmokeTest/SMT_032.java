package wibmo.app.testScripts.SmokeTest;

import library.CSR;

import org.testng.Reporter;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_032 CSR - Unclaimed funds report – Fetch Report.
 */
public class SMT_032 extends BaseTest // ==== CSR - Unclaimed funds report – Fetch Report ==== //
{	
	/** The tc. */
	public String TC=getTestScenario("SMT_032");	
	
	/**
	 * Smt 032.
	 */
	@Test
	public void SMT_032()
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_032");		
		String loginId=data;
		CSR.verifyUnclaimedFundsReport(loginId);
	}
}
