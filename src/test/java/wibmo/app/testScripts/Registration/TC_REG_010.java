package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;


// TODO: Auto-generated Javadoc
/**
 * The Class TC_REG_010 Verification of minimum PIN length in "Register for PayZapp" screen.
 */
public class TC_REG_010 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_010");
	
	/**
	 * Tc reg 010.
	 */
	@Test
	public void TC_REG_010() // ==== Verification of minimum PIN length in "Register for PayZapp" screen. ==== //
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_010").split(",")[0];				   
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.enterValues(getTestData("TC_REG_010"));	
		rp.verifyInvalidPinErrMsg();		
	}
}
