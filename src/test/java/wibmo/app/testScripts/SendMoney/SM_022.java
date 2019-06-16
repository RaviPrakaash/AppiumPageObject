package wibmo.app.testScripts.SendMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.UnclaimedFundsPage;
import wibmo.app.pagerepo.WelcomePage;

// TODO: Auto-generated Javadoc
/**
 * The Class SM_022 Verify claim code prompt.
 */
public class SM_022 extends BaseTest // ====  Verify claim code prompt ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_022");

	/**
	 * Sm 022.
	 */
	@Test
	public void SM_022()
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("SM_022");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++];
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		checkUnclaimed=true;
		Generic.verifyLogin(driver,data);
		
		UnclaimedFundsPage ufp=new UnclaimedFundsPage(driver);
		ufp.verifyUnclaimedFundsPage();	
		ufp.verifyClaimCodePrompt();		
	}

}
