package wibmo.app.testScripts.SendMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.UnclaimedFundsPage;
import wibmo.app.pagerepo.WelcomePage;

// TODO: Auto-generated Javadoc
/**
 * The Class SM_024 Verify Incorrect claim code.
 */
public class SM_024 extends BaseTest // ==== Verify Incorrect claim code ==== // 
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_024");

	/**
	 * Sm 024.
	 */
	@Test
	public void SM_024()
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("SM_024");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++],claimCode=values[i++];
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		checkUnclaimed=true;
		Generic.verifyLogin(driver,data);
		
		UnclaimedFundsPage ufp=new UnclaimedFundsPage(driver);
		ufp.enterClaimCode(claimCode);
		ufp.verifyIncorrectCode();		
	}
}
