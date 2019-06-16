package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class TC_REG_005 Verification of 9 digit Mobile number in Register screen.
 */
public class TC_REG_005 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_005");
	
	/**
	 * Tc reg 005.
	 */
	@Test
	public void TC_REG_005() // ==== Verification of 9 digit Mobile number in Register screen ==== //
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_005");	 // Single Value		
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);		
		rmp.registerMobile(mobileNumber);
		rmp.verifyInvalidNumberErrMsg();		
	}
}
