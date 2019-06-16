package wibmo.app.testScripts.Registration;

import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import library.Generic;
import library.Log;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.pagerepo.RegistrationSuccessfulPage;
import wibmo.app.pagerepo.VerifyMobilePage;

/**
 * The Class TC_REG_062 Use Invalid Referral Code & Valid Referral Code to Register
 * 
 * TC_REG_063 : Use invalid Referral Code to register
 * TC_REG_064 : Use valid Referral Code to register 
 * 
 */
public class TC_REG_062 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_062");
	
	public String tcId;
	public int prevTCStatus=5; // First TC random value
	
	public String data,mobileNumber,securePin,referralCode;
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{
		tcId=testMethod.getName();
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("TC_REG_062"))
		{	
			Log.info("================ Group execution "+tcId+" : "+getTestScenario(tcId)+" ================");
			groupExecute=true;		
		}		
	}
	
	public void navigate()
	{
		// ======== Initialize ======== //
		
		 data=getTestData(tcId);
		 mobileNumber=data.split(",")[0];		
		 securePin=data.split(",").length>3?data.split(",")[8]:"1234"; 
		 referralCode=Generic.parseReferralCode(data);
		 
		 if(prevTCStatus==1) return; //1=SUCCESS
		 
		 // ======== Navigate ======== //
		 
		 if(prevTCStatus<5)// First TC
			 Generic.switchToApp(driver);
		 
		 gotoVerifyMobile();		
	}
	
	
	@Test // TC_REG_063 : Use invalid Referral Code to register
	public void TC_REG_063()
	{
		navigate();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber,referralCode);
		rmp.verifyReferralCodeErrMsg();
	}
	
	
	@Test // TC_REG_064 : Use valid Referral Code to register
	public void TC_REG_064() 
 	{
		Reporter.log(getTestScenario());
		
		navigate();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber,referralCode);		
		
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
	
	
	@AfterMethod
	public void setStatus(ITestResult result)
	{
		prevTCStatus=result.getStatus();
	}
	
}
