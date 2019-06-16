package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_REG_025 Verification of Terms of Service link.
 */
public class TC_REG_025 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_025");//TC_REG_024 not present
		
		/**
		 * Tc reg 025.
		 */
		@Test
		public void TC_REG_025() // ==== Verification of field values in "Register for PayZapp" screen.Positive. Verify Terms of Service ==== //
		{
			Reporter.log(getTestScenario());
			String mobileNumber=getTestData("TC_REG_025");		
			
			gotoVerifyMobile();
			
			RegisterMobilePage rmp=new RegisterMobilePage(driver);
			rmp.registerMobile(mobileNumber);
			
			RegisterPage rp=new RegisterPage(driver);
			rp.verifyTos();	
		}
}
