package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_REG_015 Verification of mobile number less than 10 digits.
 */
public class TC_REG_015 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_015");
	
	/**
	 * Tc reg 015.
	 */
	@Test
	public void TC_REG_015() // ==== Verification of field values in "Register for PayZapp" screen. ( Verification of <10 digits mobile number ) ==== //
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_015").split(",")[0];		
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.enterValues(getTestData("TC_REG_015"));	
		rp.verifyInvalidNumberErrMsg();		
	}
}
