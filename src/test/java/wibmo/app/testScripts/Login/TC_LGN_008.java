package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_LGN_008 Login with invalid mobile number greater than 10 digits.
 */
public class TC_LGN_008 extends BaseTest{
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_008");
	
	/**
	 * Tc lgn 008.
	 */
	@Test
	public void TC_LGN_008() // Login with invalid mobile number (greater than 10 digits)
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data= getTestData("TC_LGN_008");
		String[] values = data.split(",");
		String loginId=values[i++];
		

		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);
		wp.verifyErrMsg();
	}

}
