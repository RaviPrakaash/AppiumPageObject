package wibmo.app.testScripts.SmokeTest;

import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.ManageProfilePage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SMT_020 Update Profile.
 */
public class SMT_020 extends BaseTest // ==== Update Profile ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_020");
	
	/**
	 * Smt 020.
	 */
	@Test
	public void SMT_020()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_020");
		String mobileNo = data.split(",")[0],securePin=data.split(",")[1],name=data.split(",")[2];
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(mobileNo);
		
		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver, data);
		
		HomePage hp = new HomePage(driver);
		hp.gotoManageProfile();		
		
		ManageProfilePage mpp = new ManageProfilePage(driver);
		mpp.enterValuesToUpdateProfile(data);
		
		Generic.logout(driver);
		
		wp.selectUser(mobileNo);
		
		lnp.login(securePin);
		
		Generic.verifyLogin(driver, data);
		
		hp.gotoSettings();
		
		SettingsPage sp=new SettingsPage(driver);		
		sp.verifyUserName(name);
		sp.gotoManageProfile();
		
		mpp.verifyProfileUpdate(data);
		
	}
}
