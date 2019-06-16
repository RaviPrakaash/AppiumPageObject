package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_REG_014 Verification of ph # with 11 digits in "Register for PayZapp" screen.
 */
public class TC_REG_014 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_014");
	
	/**
	 * Tc reg 014.
	 */
	@Test
	public void TC_REG_014() // ==== Verification of ph # with 11 digits in "Register for PayZapp" screen ==== //
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_014").split(",")[0];				
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.verify10DigitNumber(getTestData("TC_REG_014")); // Not accepting more than ten, No need to enter all the values
	}
}
