package wibmo.app.testScripts.SmokeTest;

import library.CSR;

import org.testng.Reporter;
import org.testng.annotations.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_012 CSR – IAP Report check for successful transaction.
 */
public class SMT_012 extends BaseTest // ==== CSR – IAP Report check for successful transaction ==== //
{	
	
	/** The tc. */
	public String TC=getTestScenario("SMT_012");	
	
	/**
	 * Smt 012.
	 */
	@Test
	public void SMT_012()
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_012");		
		CSR.verifyIAPTXNDetails(transactionStatusMsg); // During batch run transactionStatusMsg will be updated by SMT_010 AND SMT_011 
	}
}
