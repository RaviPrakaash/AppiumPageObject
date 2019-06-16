package wibmo.app.testScripts.SmokeTest;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.ChangeSecurityQAPage;
import wibmo.app.pagerepo.ForgotPinStep1Page;
import wibmo.app.pagerepo.ForgotPinStep2Page;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SMT_021 Change Security Q/A.
 */
public class SMT_021 extends BaseTest // ==== Change Security Q/A ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_021");
	
	/**
	 * Smt 021.
	 */
	@Test
	public void SMT_021()
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("SMT_021");
		String mobileNo = data.split(",")[i++],securePin=data.split(",")[i++],newPin=data.split(",")[i++],ans=data.split(",")[i++];
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(mobileNo);
		
		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver, data);
		
		HomePage hp = new HomePage(driver);
		hp.gotoSettings();
		
		SettingsPage sp = new SettingsPage(driver);
		sp.gotoChangeSecurityQa();
		
		ChangeSecurityQAPage csqp=new ChangeSecurityQAPage(driver);
		String sQuestion=csqp.changeSecurityQa(data);
		
		Generic.logout(driver);
		
		wp.goToForgotPin();
		
		ForgotPinStep1Page fps1p=new ForgotPinStep1Page(driver);
		fps1p.changePin(mobileNo,bankCode);
		
		ForgotPinStep2Page fps2p=new ForgotPinStep2Page(driver);
		fps2p.verifySecurityQa(sQuestion,ans);
				
	}
}
