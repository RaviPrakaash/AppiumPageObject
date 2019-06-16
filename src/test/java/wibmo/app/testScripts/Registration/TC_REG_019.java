package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.pagerepo.VerifyMobilePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_REG_019 Verification of valid security answer.
 */
public class TC_REG_019 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_019");
	
	/**
	 * Tc reg 019.
	 */
	@Test
	public void TC_REG_019() // ==== Verification of field values in "Register for PayZapp" screen. Security Answer Positive ==== //
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_019").split(",")[0];		
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.enterValues(getTestData("TC_REG_019")); 
		
		VerifyMobilePage vmp=new VerifyMobilePage(driver);
		vmp.verifyTitle();
		
		setOldMvc(mobileNumber);
		
		// After this Verify whether next page is displayed
	}
}
