package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_REG_012 Verification of email field with invalid email (without '.com')
 */
public class TC_REG_012 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_012");
	
	/**
	 * Tc reg 012.
	 */
	@Test 
	public void TC_REG_012() // ==== Verification of email field with invalid email (without '.com') ==== //
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_012").split(",")[0];
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.enterValues(getTestData("TC_REG_012"));	
		rp.verifyInvalidEmailErrMsg();				
	}
}
