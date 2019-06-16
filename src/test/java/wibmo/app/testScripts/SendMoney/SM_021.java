package wibmo.app.testScripts.SendMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.UnclaimedFundsPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SM_021 Verify Unclaimed funds page.
 */
public class SM_021 extends BaseTest // ==== Verify Unclaimed funds page ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_021");

	/**
	 * Sm 021.
	 */
	@Test
	public void SM_021()
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("SM_021");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++];
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		checkUnclaimed=true; // prevents Generic.verifyLogin method from handling Unclaimed funds page 
		Generic.verifyLogin(driver,data);
		
		UnclaimedFundsPage ufp=new UnclaimedFundsPage(driver);
		ufp.verifyUnclaimedFundsPage();			
	}
	
}
