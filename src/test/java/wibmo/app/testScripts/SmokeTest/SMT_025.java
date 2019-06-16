package wibmo.app.testScripts.SmokeTest;

import library.CSR;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.pagerepo.RegistrationSuccessfulPage;
import wibmo.app.pagerepo.VerifyMobilePage;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_025 Verify Block & Unblock in CSR.
 */
public class SMT_025 extends BaseTest // ==== Verify Block & Unblock    ==== //
{
	/** The tc. */
	public String TC=getTestScenario("SMT_025");
	
	/**
	 * Smt 025.
	 */
	@Test
	public void SMT_025() 
 	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_025");
		String loginId = data;
		
		CSR.block(loginId);
		//CSR.launchWebDriver(driver);
		CSR.unBlock(loginId);		
	}
}
