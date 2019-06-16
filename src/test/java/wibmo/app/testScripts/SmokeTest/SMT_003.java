package wibmo.app.testScripts.SmokeTest;

import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.pagerepo.VerifyDevicePage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SMT_003 Login – Phone Verification – DVC.
 */
public class SMT_003 extends BaseTest // ==== Login – Phone Verification – DVC ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_003");
	
	/**
	 * Smt 003.
	 */
	@Test
	public void SMT_003()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_003");
		String mobileNo = data.split(",")[0],securePin=data.split(",")[1];
		
		WelcomePage wp=new WelcomePage(driver);
		setPhoneVerifyStatus(true);
		wp.changeUserTemp(data);
		wp.selectUser(mobileNo);
		
		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.waitForPhoneVerification(driver);
		
		VerifyDevicePage vdp = new VerifyDevicePage(driver);
		vdp.loginWithTrustedVerifyDevice(mobileNo,bankCode);
		
		Generic.verifyLogin(driver,data);
	
		HomePage hp=new HomePage(driver);
		hp.verifyPageTitle();		
	}
}
