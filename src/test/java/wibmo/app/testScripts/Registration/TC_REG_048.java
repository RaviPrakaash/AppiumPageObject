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
 * The Class TC_REG_048 Verification of Incorrect MVC.
 */
public class TC_REG_048 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_048");
	
	/**
	 * Tc reg 048.
	 */
	@Test
	public void TC_REG_048() // ==== Verification of MVC, Incorrect OTP ==== // 
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_048").split(",")[0];		
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.enterValues(getTestData("TC_REG_048"));
		
		VerifyMobilePage vmp= new VerifyMobilePage(driver);
		vmp.verifyIncorrectOTP(getTestData("TC_REG_048"));
	}
}
