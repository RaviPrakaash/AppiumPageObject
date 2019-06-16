package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;
import library.Generic;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class TC_LGN_003 Login with  4 digit valid secure PIN.
 */
public class TC_LGN_003 extends BaseTest {
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_003");
	
	/**
	 * Tc lgn 003.
	 */
	@Test
	public void TC_LGN_003()      // Login with  4 digits valid secure PIN
	{
		Reporter.log(getTestScenario());
		String data= getTestData("TC_LGN_003");
		String[] values = data.split(",");
		int i=0;
		String loginId=values[i++];
		String securePin = values[i++];

		WelcomePage wp = new WelcomePage(driver);		
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver, data);

		HomePage hp=new HomePage(driver);
		hp.verifyPageTitle();
	}
}
