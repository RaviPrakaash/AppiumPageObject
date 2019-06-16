package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.WelcomePage;


/**
 * The Class TC_INS_002 Verify App installation.
 */
public class TC_INS_002 extends BaseTest
{
	
	/** The tc. */
	public static String TC=getTestScenario1("TC_INS_002");
	
	/**
	 * Tc ins 002.
	 */
	@Test
	public void TC_INS_002() // ====Verify App installation ====//
	{
		Reporter.log(getTestScenario());
		//createUnclaimed();		
		
		WelcomePage wp=new WelcomePage(driver);		
		wp.verifyApp();		
	}
}
