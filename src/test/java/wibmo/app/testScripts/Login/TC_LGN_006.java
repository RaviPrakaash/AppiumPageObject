package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import library.Generic;
import wibmo.app.pagerepo.ForgotPinStep1Page;
import wibmo.app.pagerepo.ForgotPinStep2Page;
import wibmo.app.pagerepo.ForgotPinStep3Page;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.VerifyPhonePage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_LGN_006 Login with 12 digits valid secure PIN.
 */
public class TC_LGN_006 extends BaseTest{
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_006");
	
	/**
	 * Tc lgn 006.
	 */
	@Test
	public void TC_LGN_006() // Login with 12 digits valid secure PIN
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data= getTestData("TC_LGN_006");
		String[] values = data.split(",");
		String loginId=values[i++];
		String securePin =values[i++];		

		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		Generic.verifyLogin(driver,data);		
	}

}
