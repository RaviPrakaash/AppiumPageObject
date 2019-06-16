package wibmo.app.testScripts.SmokeTest;

import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.ForgotPinStep1Page;
import wibmo.app.pagerepo.ForgotPinStep2Page;
import wibmo.app.pagerepo.ForgotPinStep3Page;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SMT_013 Forgot PIN.
 */
public class SMT_013 extends BaseTest // ==== Forgot PIN === //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_013");
	
	/**
	 * Smt 013.
	 */
	@Test
	public void SMT_013() 
	{
		Reporter.log(getTestScenario());
		String data= getTestData("SMT_013");
		int i=0;
		String[] values = data.split(",");	

		String mobileEmail=values[i++],newPin=values[i++],ans=values[i++];
		
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
		
		Generic.verifyLogin(driver, data);
		
		HomePage hp=new HomePage(driver);
		hp.verifyPageTitle();
	}
}
