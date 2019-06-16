package wibmo.app.testScripts.Registration;

import java.lang.reflect.Method;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.libraries.Log;

import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.pagerepo.VerifyMobilePage;
/**
 * The Class TC_REG_061 - Verification of Old MVC and Incorrect OTP - contains the following Test Scripts 
 * 
 *  TC_REG_027 Verify Mobile page should be displayed after entering all valid data
 *  TC_REG_046 Verification of MVC Old MVC
 *  TC_REG_048 Verification of MVC, Incorrect OTP
 *  
 */
public class TC_REG_061 extends BaseTest
{
	public String TC=getTestScenario("TC_REG_061");	
	
	public String tcId;
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{
		tcId=testMethod.getName();
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("TC_REG_061"))
		{	
			Log.info("================ Group execution "+tcId+" : "+getTestScenario(tcId)+" ================");
			groupExecute=true;		
		}		
	}
	
	
	
	@Test 
	public void TC_REG_027() // ---------------- TC_REG_027 : Verify Mobile page should be displayed after entering all valid data ---------------- //
	{	
		Reporter.log(getTestScenario());
		groupExecute=true;	
		
		setGroupValue("TC_REG_027");		
		String mobileNumber=getTestData("TC_REG_027").split(",")[0];	
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.enterValues(getTestData("TC_REG_027"));	
		
		VerifyMobilePage vmp= new VerifyMobilePage(driver);
		vmp.verifyTitle();
		
	}
	
	@Test(dependsOnMethods="TC_REG_027")
	public void TC_REG_046() // --------------- TC_REG_046 : Verification of MVC- Old MVC ---------------- //
	{
		VerifyMobilePage vmp= new VerifyMobilePage(driver);
		vmp.verifyOldMvc(getOldMvc());		
	}
	
	@Test(dependsOnMethods="TC_REG_027")
	public void TC_REG_048() // ---------------- TC_REG_048 : Verification of MVC - Incorrect MVC ---------------- //
	{
		VerifyMobilePage vmp= new VerifyMobilePage(driver);
		vmp.verifyIncorrectOTP(getTestData("TC_REG_048"));		
	}
	
	

}
