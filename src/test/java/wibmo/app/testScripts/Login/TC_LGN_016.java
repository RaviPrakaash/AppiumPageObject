package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import library.Generic;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_LGN_016 Login using valid mobile number and Secure PIN and verify balance displayed.
 */
public class TC_LGN_016 extends BaseTest{
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_016");
	
	/**
	 * Tc lgn 016.
	 */
	@Test
	public void TC_LGN_016() // Login using valid mobile number and Secure PIN
	{
		Reporter.log(getTestScenario());
		String data= getTestData("TC_LGN_016");
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++];
		String securePin =values[i++];
		

		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);

		Generic.verifyLogin(driver,data);
		
		HomePage hp=new HomePage(driver);
		hp.addSend();
		
		AddSendPage asp=new AddSendPage(driver);
		asp.verifyBalance();
		
	}
}
