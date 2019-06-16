package wibmo.app.testScripts.SmokeTest;

import library.CSR;

import org.testng.Reporter;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_023 CSR – Search Wibmo User – Fetch Report.
 */
public class SMT_023 extends BaseTest // ==== CSR – Search Wibmo User – Fetch Report ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_023");	
	
	/**
	 * Smt 023.
	 */
	@Test
	public void SMT_023()
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_023");		
		String mobileNo=data.split(",")[0],username=data.split(",")[1];
		CSR.searchWibmoUser(mobileNo,username);	
	}
}
