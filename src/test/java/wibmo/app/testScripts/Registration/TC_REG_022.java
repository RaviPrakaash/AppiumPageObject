package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_REG_022 Verification of minimum number of characters for Custom Security question and Security answer textfield.
 */
public class TC_REG_022 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_022");
	
	/**
	 * Tc reg 022.
	 */
	@Test
	public void TC_REG_022() // ==== Verification of field values in "Register for PayZapp" screen. Security Question & Answer min length ==== // 
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_022").split(",")[0];		
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.enterValues(getTestData("TC_REG_022"));
		
		rp.verifyCustomQuestionShortErrMsg();
	}
}
