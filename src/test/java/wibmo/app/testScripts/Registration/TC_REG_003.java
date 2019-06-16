package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class TC_REG_003 Verification of Blank Mobile number in Register screen.
 */
public class TC_REG_003 extends BaseTest
{
	
	/** The tc. */
	public static String TC=getTestScenario1("TC_REG_003");
	
	/**
	 * Tc reg 003.
	 */
	@Test 
	public void TC_REG_003() // ==========Verification of Blank Mobile number in Register screen====//
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_003"); // Single Value							

		RegisterMobilePage rmp =new RegisterMobilePage(driver);
		
		gotoVerifyMobile();
		
		rmp.registerMobile(mobileNumber);
		rmp.verifyBlankNumberErrMsg();		
	}
}
