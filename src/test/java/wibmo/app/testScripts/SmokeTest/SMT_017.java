package wibmo.app.testScripts.SmokeTest;

import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.ProgramCardPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SMT_017 Unlock Card.
 */
public class SMT_017 extends BaseTest // ==== Unlock Card ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_017");
	
	/**
	 * Smt 017.
	 */
	@Test
	public void SMT_017()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_017");
		String mobileNo = data.split(",")[0],securePin=data.split(",")[1];
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(mobileNo);
		
		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver, data);
		
		HomePage hp = new HomePage(driver);
		hp.goToProgramCard();
		
		ProgramCardPage pcp = new ProgramCardPage(driver);
		pcp.unlockCard();		
	}

}
