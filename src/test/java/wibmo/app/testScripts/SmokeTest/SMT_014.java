package wibmo.app.testScripts.SmokeTest;

import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.ProgramCardPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SMT_014 Lock Card.
 */
public class SMT_014 extends BaseTest // ==== Lock Card ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_014");

	/**
	 * Smt 014.
	 */
	@Test
	public void SMT_014()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_014");
		String mobileNo = data.split(",")[0],securePin=data.split(",")[1];
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(mobileNo);
		
		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver, data);
		
		HomePage hp = new HomePage(driver);
		hp.goToProgramCard();
		
		ProgramCardPage pcp = new ProgramCardPage(driver);
		pcp.lockCard();		
	}
}
