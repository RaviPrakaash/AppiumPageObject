package wibmo.app.testScripts.SmokeTest;


import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.libraries.Log;

import wibmo.app.pagerepo.BasePage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SMT_042 Verify Phone Verification status after upgrade.
 * 1.Downgrade app. 2.Perform phone verification. 3.Upgrade app. 4.Verify phone verification status
 */
public class SMT_042 extends BaseTest {

	/** The tc. */
	public String TC=getTestScenario("SMT_042");
	
	/** The latest App version. */
	public String ver="";
	
	/**
	* SMT_042
	*/
	@Test
	public void SMT_042()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_042");
		
		ver=appVersion.isEmpty()?Generic.getAppVersion(driver):appVersion;			
		
		Generic.wait(10); // wait for App Launch				
		
		Generic.downgradeApp(driver);		
		
		Generic.switchToApp(driver);
		
		Generic.preconditionITP(driver, data);
		
		Generic.closeApp(driver);
		
		Generic.upgradeApp(driver,ver);
		
		Generic.switchToApp(driver);
		
		Generic.preconditionITP(driver, data);				
	}
	
	@AfterMethod
	public void resetApp()
	{
		if(!ver.equals(Generic.getAppVersion(driver)))
		{
			Log.warn("App Version Unstable");
			try
			{
				Generic.upgradeApp(driver,ver);
			}
			catch(Exception e)
			{
				Log.warn(" App Version unstable ");
			}	
		}
		Log.info("======== Latest App version "+Generic.getAppVersion(driver)+" present ========");
	}
	
	
}
