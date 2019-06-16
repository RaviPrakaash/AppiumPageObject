package wibmo.app.testScripts.Registration;

import java.lang.reflect.Method;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.libraries.Log;
import library.Generic;
import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class TC_REG_059 Verification of Valid Mobile number in Register screen & Registration field values, contains the following test scripts
 * 
 * TC_REG_008	Verification of Valid Mobile number in Register screen
 * TC_REG_009	Verification of Registration field values
 * 
 */
public class TC_REG_059 extends BaseTest
{
	
	/** The tc field. */
	public String TC=getTestScenario("TC_REG_059");
	
	public String tcId;
	public int prevTCStatus=5; // First TC random value	
	
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{
		tcId=testMethod.getName();
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("TC_REG_059"))
		{	
			Log.info("================ Group execution "+tcId+" : "+getTestScenario(tcId)+" ================");
			groupExecute=true;		
		}		
	}
	
	/**
	 * Tc reg 008.
	 */
	@Test
	public void TC_REG_008() 
	{
		Reporter.log(getTestScenario());
		
		String mobileNumber=getTestData("TC_REG_008");		
		
		gotoVerifyMobile();		
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
	}			
	
	@Test(dependsOnMethods="TC_REG_008")
	public void TC_REG_009()
	{		
		RegisterPage rp=new RegisterPage(driver);
		rp.verifyFields();			
	}
}
