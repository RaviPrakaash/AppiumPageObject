package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_REG_018 Verification of Custom security question.
 */
public class TC_REG_018 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_018");
	
	/**
	 * Tc reg 018.
	 */
	@Test
	public void TC_REG_018() // ==== Verification of field values in "Register for PayZapp" screen. Custom security Question , Positive ==== //
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_018"); // Single Value		
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.verifyCreateQuestion();		
	}
}
