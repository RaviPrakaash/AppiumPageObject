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
 * The Class TC_REG_027 Verification of MVC Code reception.
 */
public class TC_REG_027 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_REG_027");
	
	/**
	 * Tc reg 027.
	 */
	@Test
	public void TC_REG_027() // ==== Verify Mobile page will be displayed after entering all valid data and MVC code will be sent to the mobile.Positive ==== //
	{
		Reporter.log(getTestScenario());
		String mobileNumber=getTestData("TC_REG_027").split(",")[0];		
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.enterValues(getTestData("TC_REG_027"));
		
		VerifyMobilePage vmp= new VerifyMobilePage(driver);
		vmp.verifyTitle();		
	}
}
