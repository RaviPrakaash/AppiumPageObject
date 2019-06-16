package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import library.CSR;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class TC_REG_057 DB Entry verification for new user through CSR.
 */
public class TC_REG_057 extends BaseTest // ==== Verification of DB Entry through CSR ==== // 
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_057");
	
	/**
	 * Tc reg 057.
	 */
	@Test
	public void TC_REG_057() 
	{
		// Requires newly registered mobile number,If registered at most recent time that CSR entry will be there		
		Reporter.log(getTestScenario());
		
		CSR.verifyDBEntry(driver,getTestData("TC_REG_057"));	
	}
}
