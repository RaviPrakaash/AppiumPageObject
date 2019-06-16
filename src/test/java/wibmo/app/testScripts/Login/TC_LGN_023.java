package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import library.Generic;
import wibmo.app.pagerepo.DeleteAccountPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_LGN_023 Verify account delete.
 */
public class TC_LGN_023 extends BaseTest{
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_023");
	
	/**
	 * Tc lgn 023.
	 */
	@Test
	public void TC_LGN_023() // Verify account delete
	{
		Reporter.log(getTestScenario());
		String data= getTestData("TC_LGN_023");
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++];
		String securePin =values[i++];

		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);

		Generic.verifyLogin(driver,data);

		HomePage hp = new HomePage(driver);
		/*hp.lastLogin();*/
		hp.gotoSettings();

		SettingsPage sp = new SettingsPage(driver);
		sp.gotoDeleteAccount();

		DeleteAccountPage dap = new DeleteAccountPage(driver);
		dap.deleteAccount(securePin);
	}

}
