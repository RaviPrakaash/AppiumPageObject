package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_LGN_002 Login with 3 digit Secure PIN.
 */
public class TC_LGN_002 extends BaseTest {
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_002");
	
	/**
	 * Tc lgn 002.
	 */
	@Test
	public void TC_LGN_002()            // Login with 3 digit Secure PIN
	{
		Reporter.log(getTestScenario());
		String data= getTestData("TC_LGN_002");
		String[] values = data.split(",");
		int i=0;
		String loginId=values[i++];
		String securePin =values[i++];	
		

		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);

		//Generic.hideKeyBoard(driver);

		LoginNewPage lnp =new LoginNewPage(driver);
		lnp.login(securePin);	
		lnp.verifyEnterValidPinErrMsg(data);
	}
}
