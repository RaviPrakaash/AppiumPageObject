package wibmo.app.testScripts.Registration;

import java.lang.reflect.Method;

import org.testng.Reporter;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import library.CSR;
import library.Generic;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.pagerepo.RegistrationSuccessfulPage;
import wibmo.app.pagerepo.VerifyMobilePage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class TC_REG_052 Perform Complete Registration.
 */
public class TC_REG_052 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_052");
	public String data;
	
	/**
	 * Tc reg 052.
	 */
	@Test
	public void TC_REG_052() // ==== Verification of MVC and Complete Registration , Positive ==== //
 	{
		Reporter.log(getTestScenario());
		 data=getTestData("TC_REG_052");
		String mobileNumber=data.split(",")[0];		
		String securePin=data.split(",")[8];
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.enterValues(data);
		
		VerifyMobilePage vmp= new VerifyMobilePage(driver);
		vmp.completeRegistration(mobileNumber,bankCode);	
		
		RegistrationSuccessfulPage rsp=new RegistrationSuccessfulPage(driver);
		rsp.verifyRegistrationSuccess();
		rsp.gotoLogin();
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,data);
		
		HomePage hp =new HomePage(driver);  // No need to check for RateApp popup
		hp.verifyPageTitle();			
		
	}
	
	@Test(dependsOnMethods="TC_REG_052")
	public void TC_REG_057(Method testMethod)
	{		
		getTestScenario(testMethod.getName());
		CSR.verifyDBEntry(driver,data);
	}
	
}
