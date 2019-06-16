package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class TC_REG_004 Verification of 11 digit Mobile number in Register screen.
 */
public class TC_REG_004 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_004");
	
	/**
	 * Tc reg 004.
	 */
	@Test
	public void TC_REG_004() // ==== Verification of 11 digit Mobile number in Register screen ==== //
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_004");	// Single Value			
		RegisterMobilePage rmp =new RegisterMobilePage(driver);
		
		gotoVerifyMobile();		
		new BaseTest1().selectQaProgram(driver); // Since registerMobile(mobileNumber) which checks for QA program is not used
		rmp.verify10DigitNumber(mobileNumber);	
	}
}
