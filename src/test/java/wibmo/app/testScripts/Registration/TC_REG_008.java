package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_REG_008 Verification of valid Mobile number in Register screen.
 */
public class TC_REG_008 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_008");
	
	/**
	 * Tc reg 008.
	 */
	@Test
	public void TC_REG_008() // ==== Verification of Mobile number in Register screen , Positive ==== //
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_008");	// Single Value		
		
		gotoVerifyMobile();		
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);	
		
		RegisterPage rp=new RegisterPage(driver);
		rp.verifyFields();		
	}
}
