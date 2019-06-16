package wibmo.app.testScripts.Registration;

import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.libraries.Log;
import library.Generic;
import wibmo.app.pagerepo.RegisterMobilePage;

/**
 * The Class TC_REG_058 contains the following Test Scripts
 *  TC_REG_003 Verification of Blank Mobile number in Register screen.
 *  TC_REG_004 Verification of 11 digit Mobile number
 *  TC_REG_005 Verification of 9 digit Mobile number
 *  
 */
public class TC_REG_058 extends BaseTest
{
	public String TC=getTestScenario("TC_REG_058");
	
	public String tcId;
	public int prevTCStatus=5; // First TC random value
	public String mobileNumber;
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{
		tcId=testMethod.getName();
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("TC_REG_058"))
		{	
			Log.info("================ Group execution "+tcId+" : "+getTestScenario(tcId)+" ================");
			groupExecute=true;		
		}		
	}
	
	public void navigate()
	{
		// ======== Initialize ======== //
		
		mobileNumber=getTestData(tcId);		
		
		if(prevTCStatus==1) return; //1=SUCCESS
		
		// ======== Navigate ======== //
		
		if(prevTCStatus<5)// First TC
			 Generic.switchToApp(driver);
		
		gotoVerifyMobile();		
	}	
	
	@Test(priority=1)
	public void TC_REG_003()
	{
		navigate();
		
		RegisterMobilePage rmp =new RegisterMobilePage(driver);		
		rmp.registerMobile(mobileNumber);
		rmp.verifyBlankNumberErrMsg();	
	}
	
	@Test(priority=2)
	public void TC_REG_004()
	{
		navigate();
		
		RegisterMobilePage rmp =new RegisterMobilePage(driver);	
		rmp.verify10DigitNumber(mobileNumber);
	}
	
	@Test(priority=3)
	public void TC_REG_005()
	{
		navigate();
		
		RegisterMobilePage rmp =new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		rmp.verifyInvalidNumberErrMsg();
	}	
	
	@AfterMethod
	public void setStatus(ITestResult result)
	{
		prevTCStatus=result.getStatus();
	}

}
