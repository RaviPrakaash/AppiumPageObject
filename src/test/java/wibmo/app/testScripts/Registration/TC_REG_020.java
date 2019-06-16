package wibmo.app.testScripts.Registration;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.pagerepo.VerifyMobilePage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class TC_REG_020 Verification of maximum number of characters in security question text field.
 */
public class TC_REG_020 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_020");
	
	/**
	 * Tc reg 020.
	 */
	@Test 
	public void TC_REG_020()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("TC_REG_020");
		String mobileNumber=data.split(",")[0];		
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.enterValues(data);
		
		VerifyMobilePage vmp=new VerifyMobilePage(driver);
		vmp.verifyTitle();
	}
}
