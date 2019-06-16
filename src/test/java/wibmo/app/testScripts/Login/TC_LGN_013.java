package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;


import library.Generic;
import wibmo.app.pagerepo.ForgotPinStep1Page;
import wibmo.app.pagerepo.ForgotPinStep2Page;
import wibmo.app.pagerepo.ForgotPinStep3Page;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.VerifyDevicePage;
import wibmo.app.pagerepo.VerifyPhonePage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class TC_LGN_013 Perform Forgot PIN in Login Page.
 */
public class TC_LGN_013 extends BaseTest{
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_013");
	
	/**
	 * Tc lgn 013.
	 */
	@Test
	public void TC_LGN_013() // Perform Forgot PIN in Login Page
	{
		Reporter.log(getTestScenario());
		String data= getTestData("TC_LGN_013");
		int i=0;
		String[] values = data.split(",");
		
		String mobileEmail=values[i++];
		String newPin=values[i++];
		String ans=values[i++];		

		WelcomePage wp= new WelcomePage(driver);
		wp.goToForgotPin();

		ForgotPinStep1Page fps1p=new ForgotPinStep1Page(driver);
		fps1p.changePin(mobileEmail,bankCode);

		ForgotPinStep2Page fps2p=new ForgotPinStep2Page(driver);
		fps2p.answerSecurityQuestion(ans);

		ForgotPinStep3Page fps3p=new ForgotPinStep3Page(driver);
		fps3p.enterNewPin(newPin);
		
		wp.selectUser(mobileEmail);

		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(newPin);

		Generic.verifyLogin(driver,data);	
	}
}
