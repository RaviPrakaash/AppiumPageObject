package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.VerifyPhonePage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class TC_LGN_005 Verification of phone with number not in device.
 */
public class TC_LGN_005 extends BaseTest {
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_005");
	
	/**
	 * Tc lgn 005.
	 */
	@Test
	public void TC_LGN_005() // Verification of phone with number not in  device
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data= getTestData("TC_LGN_005");	
		String[] values = data.split(",");
		String loginId=values[i++];
		String securePin =values[i++];		

		WelcomePage wp = new WelcomePage(driver);
		setPhoneVerifyStatus(true);
		wp.changeUserTemp(data);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);

		VerifyPhonePage vpp = new VerifyPhonePage(driver);
		vpp.loginWithPersonalDevice();
		vpp.deviceAndPhonVerificationErrMsg();
		vpp.verifySkip();
	}
}
