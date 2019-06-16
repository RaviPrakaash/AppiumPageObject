package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import library.Generic;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.VerifyPhonePage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_LGN_004 Verification of phone with number in  device.
 */
public class TC_LGN_004 extends BaseTest {
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_004");
	
	/**
	 * Tc lgn 004.
	 */
	@Test
	public void TC_LGN_004()    // Verification of phone with number in  device
	{
		Reporter.log(getTestScenario());
		
		String data= getTestData("TC_LGN_004");
		String[] values = data.split(",");
		int i=0;
		String loginId=values[i++],securePin = values[i++];		

		//Generic.setPassword(driver, loginId, ans, securePin);

		WelcomePage wp = new WelcomePage(driver);
		wp.changeUserTemp(data);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);

		VerifyPhonePage vpp = new VerifyPhonePage(driver);
		vpp.verifyPhonePage();
	}
	
}
