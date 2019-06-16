package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.ForgotPinStep1Page;
import wibmo.app.pagerepo.ForgotPinStep2Page;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_LGN_014 Enter Wrong Security Answer while performing  Forgot PIN.
 */
public class TC_LGN_014 extends BaseTest{
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_014");
	
	/**
	 * Tc lgn 014.
	 */
	@Test
	public void TC_LGN_014() // Wrong Security Answer entered during performing the Forgot PIN in Login Page
	{
		Reporter.log(getTestScenario());
		String data= getTestData("TC_LGN_014");
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
		fps2p.wrongSecurityAnsErrMsg();

		/*ForgotPinStep3Page fps3p=new ForgotPinStep3Page(driver);
		fps3p.enterNewPin(newPin);

		wp.selectUser(mobileEmail);

		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(newPin);

		VerifyPhonePage vpp = new VerifyPhonePage(driver);
		vpp.loginWithPersonalDevice();

		VerifyDevicePage vdp=new VerifyDevicePage(driver);
		vdp.loginWithVerifyDevice();*/

	}
}
