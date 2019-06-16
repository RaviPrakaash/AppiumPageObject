package wibmo.app.testScripts.SmokeTest;


import library.CSR;
import library.Generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.BasePage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;
import wibmo.webapp.pagerepo.ConnectingPhonePage;
import wibmo.webapp.pagerepo.DeviceVerificationPage;
import wibmo.webapp.pagerepo.IAPPostReportPage;
import wibmo.webapp.pagerepo.Merchant3DSPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.OTPFetchPage;
import wibmo.webapp.pagerepo.PayZappLoginPage;
import wibmo.webapp.pagerepo.TestMerchantPage;

/**
 * The Class SMT_044 Verify Navigation Links
 */
public class SMT_044 extends BaseTest {

	/** The tc. */
	public String TC=getTestScenario("SMT_044");
	
	/**
	* SMT_044
	*/
	@Test
	public void SMT_044()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_044");
		String mobileNo = data.split(",")[0],securePin=data.split(",")[1];
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(mobileNo);
		
		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver, data);
		
		HomePage hp = new HomePage(driver); 	
		hp.gotoSettings();
		
		BasePage bp=new BasePage(driver);
		bp.verifyNavigation(data);
		
			
	}
	
}
