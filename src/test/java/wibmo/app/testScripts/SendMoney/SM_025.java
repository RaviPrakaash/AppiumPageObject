package wibmo.app.testScripts.SendMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.UnclaimedFundsPage;
import wibmo.app.pagerepo.WelcomePage;

// TODO: Auto-generated Javadoc
/**
 * The Class SM_025 Verify Blank claim code.
 */
public class SM_025 extends BaseTest // ==== Verify Blank ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_025");

	/**
	 * Sm 025.
	 */
	@Test
	public void SM_025()
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
		
		checkUnclaimed=true;
		Generic.verifyLogin(driver,data);
		
		UnclaimedFundsPage ufp=new UnclaimedFundsPage(driver);
		ufp.verifyUnclaimedFundsPage();	
		ufp.verifyBlankClaimCode();
	}
	

}
