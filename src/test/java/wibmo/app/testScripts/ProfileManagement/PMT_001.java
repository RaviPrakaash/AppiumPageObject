package wibmo.app.testScripts.ProfileManagement;

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
 * The Class PMT_001 
 *  Change PIN without wallet
 *  Change Pin to 13 digit
 *  Change Pin to 12 digit
 *  
 */
public class PMT_001 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("PMT_001");
	
	/**
	 * pmt 001.
	 */
	@Test
	public void PMT_001()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("PMT_001");
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
		
		//==== Verify absence of Wallet ==== //
		hp.verifyNoWallet();		
		hp.gotoSettings();		
		
		SettingsPage sp = new SettingsPage(driver);
		sp.gotoChangeSecurePin();
		
		//==== Enter 13 -digit pin ==== //
		ChangeSecurePinPage cspp = new ChangeSecurePinPage(driver);		
		cspp.enterMoreThan12DigitPin(securePin, newPin, "4"); // can be parameterized if required
		Generic.logout(driver);				
		
		wp.selectUser(mobileNo);		
		lnp.login(newPin);		
		Generic.verifyLogin(driver,data);
		
		//==== Verify Acceptance of 12 digit pin ==== //
		hp.verifyPageTitle();
	}
}
