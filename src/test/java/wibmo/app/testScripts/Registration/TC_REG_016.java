package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_REG_016 Verification of Alpha Numeric mobile number.
 */
public class TC_REG_016 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_016");
	
	/**
	 * Tc reg 016.
	 */
	@Test 
	public void TC_REG_016() // ==== Verification of Field Values, Enter Alpha Numeric as mobile number ==== //
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_016").split(",")[0];		
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.verifyAlphaNumericNumber(getTestData("TC_REG_016"));		
	}
}
