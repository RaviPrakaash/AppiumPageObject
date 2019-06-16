package wibmo.app.testScripts.SmokeTest;

import library.CSR;

import org.testng.Reporter;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_028 CSR - Wibmo User Devices – Fetch Report.
 */
public class SMT_028 extends BaseTest // ==== CSR - Wibmo User Devices – Fetch Report ==== //
{	
	/** The tc. */
	public String TC=getTestScenario("SMT_028");	
	
	/**
	 * Smt 028.
	 */
	@Test
	public void SMT_028()
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_028");		
		String loginId=data;
		CSR.verifyUserDevices(loginId);
	}
}
