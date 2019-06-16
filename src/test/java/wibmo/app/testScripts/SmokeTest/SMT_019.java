package wibmo.app.testScripts.SmokeTest;

import library.Generic;
import library.Log;
import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.ChangeSecurePinPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SMT_019 Change PIN. Login with new PIN.
 */
public class SMT_019 extends BaseTest // ==== Change PIN. Login with new PIN. ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_019");
	
	/**
	 * Smt 019.
	 */
	@Test
	public void SMT_019()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_019");
		String mobileNo = data.split(",")[0],securePin=data.split(",")[1],newPin=data.split(",")[2];
		
		
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(mobileNo);
		
		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		
		//==== Check & Exchange pins to minimize test data errors ==== //  
		if(!lnp.checkPinEntered())
		{
			Log.info("======== Setting current secure pin to "+newPin+" and new pin to "+securePin+" and logging in ========");
			String temp=newPin;newPin=securePin;securePin=temp;
			lnp.login(securePin);			
		}
		
		Generic.verifyLogin(driver, data);
		
		HomePage hp = new HomePage(driver);
		hp.gotoSettings();
		
		SettingsPage sp = new SettingsPage(driver);
		sp.gotoChangeSecurePin();
		
		ChangeSecurePinPage cspp = new ChangeSecurePinPage(driver);
		cspp.changePin(securePin, newPin);
		Generic.logout(driver);
				
		wp.selectUser(mobileNo);		
		lnp.login(newPin);		
		Generic.verifyLogin(driver,data);
		
		hp.verifyPageTitle();
	}
}
