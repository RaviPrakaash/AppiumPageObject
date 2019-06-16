package wibmo.app.testScripts.SmokeTest;

import library.ExcelLibrary;
import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.libraries.Log;
import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.pagerepo.RegistrationSuccessfulPage;
import wibmo.app.pagerepo.VerifyMobilePage;

/**
 * The Class SMT_001 Verify Successful Registration.
 */
public class SMT_001 extends BaseTest // ==== Verify Successful Registration  ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_001");
	
	/**
	 * Smt 001.
	 */
	@Test
	public void SMT_001() 
 	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_001");data=data.replaceAll(data.split(",")[0], Generic.generateMobileNumber());
		String mobileNumber=data.split(",")[0];		
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.enterValues(data);
		
		VerifyMobilePage vmp= new VerifyMobilePage(driver);
		vmp.completeRegistration(mobileNumber,bankCode);	
		
		RegistrationSuccessfulPage rsp=new RegistrationSuccessfulPage(driver);
		rsp.verifyRegistrationSuccess();
		
		// CSR.verifyDBEntry(driver, data); On confirmation integrate SMT_022 here		
		
		/*rsp.gotoLogin();
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,data);
		
		HomePage hp =new HomePage(driver);  
		hp.verifyPageTitle();*/// Login for regression				
	}
	
	@AfterMethod
	public void setNewNumber()
	{
		try
		{			
			ExcelLibrary.writeExcelData("./excel_lib/"+env+"/TestData.xls","SmokeTest", 1, 5, Generic.generateMobileNumber());
			Generic.wait(2);
		}
		catch(Exception e)
		{
			Log.info("Unable to write to TestData for next run");
		}
	}
	

}
