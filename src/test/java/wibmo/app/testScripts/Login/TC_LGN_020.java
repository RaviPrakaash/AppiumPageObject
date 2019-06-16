package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_LGN_020 Login with blank username.
 */
public class TC_LGN_020 extends BaseTest{
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_020");
	
	/**
	 * Tc lgn 020.
	 */
	@Test
	public void TC_LGN_020() // Login with blank username
	{
		Reporter.log(getTestScenario());
		String loginId= getTestData("TC_LGN_020");
		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);
		wp.verifyErrMsg();
	}
}
