package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_LGN_001 Login with invalid secure PIN.
 */
public class TC_LGN_001 extends BaseTest{
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_001");
	
	/**
	 * Tc lgn 001.
	 */
	@Test
	public void TC_LGN_001()             // Login with invalid secure PIN
	{
		Reporter.log(getTestScenario());
		String data= getTestData("TC_LGN_001");
		String[] values = data.split(",");
		int i=0;
		String loginId= values[i++];
		String securePin = values[i++];	
		

		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);

		//Generic.hideKeyBoard(driver);

		LoginNewPage lnp =new LoginNewPage(driver);
		lnp.login(securePin);	
		lnp.authenticatioFailedErrMsg();
	}
}
