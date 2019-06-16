package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_LGN_007 Login with unregistered mobile number and PIN.
 */
public class TC_LGN_007 extends BaseTest {
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_007");
	
	/**
	 * Tc lgn 007.
	 */
	@Test
	public void TC_LGN_007() // Login with unregistered mobile number and PIN
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data= getTestData("TC_LGN_007");
		String[] values = data.split(",");
		String loginId=values[i++];
		String securePin = values[i++];
		

		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		lnp.loginIdValidation();
		
	}

}
