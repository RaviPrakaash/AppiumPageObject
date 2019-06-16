package wibmo.app.testScripts.SmokeTest;

import library.CSR;

import org.testng.Reporter;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_026 CSR - Wibmo User Login History – Fetch Report.
 */
public class SMT_026 extends BaseTest // ==== CSR - Wibmo User Login History – Fetch Report ==== //
{
	
	/*@Override
	public void launchApplication()  // Currently attempting to merge usage of CSR.wdriver with driver
	{
		CSR.launchWebDriver(driver);
	}
	*/
	/** The tc. */
	public String TC=getTestScenario("SMT_026");	
	
	/**
	 * Smt 026.
	 */
	@Test
	public void SMT_026()
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_026");		
		String mobileNo=data.split(",")[0],username=data.split(",")[1];
		CSR.verifyLoginReport(mobileNo,username);	
	}

}
