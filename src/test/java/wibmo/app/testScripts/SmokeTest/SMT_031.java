package wibmo.app.testScripts.SmokeTest;

import library.CSR;

import org.testng.Reporter;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_031 CSR - Outflow Transaction History – Fetch Report.
 */
public class SMT_031 extends BaseTest // ==== CSR - Outflow Transaction History – Fetch Report ==== //
{	
	/** The tc. */
	public String TC=getTestScenario("SMT_031");	
	
	/**
	 * Smt 031.
	 */
	@Test
	public void SMT_031()
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_031");		
		String loginId=data;
		//CSR.verifyOutflowTransaction(loginId);
	}

}
