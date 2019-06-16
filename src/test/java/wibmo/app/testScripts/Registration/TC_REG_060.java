package wibmo.app.testScripts.Registration;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.libraries.Log;

import library.Generic;
import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
/**
 *  The Class TC_REG_060 contains the following Test Scripts 
 * 	TC_REG_008 Verification of valid Mobile number in Register screen 
 *	TC_REG_009 Verification of fields in Register Page 
 *	TC_REG_010 minimum PIN length
 *	TC_REG_011 max PIN length
 *	TC_REG_012 invalid email (without '.com')
 *	TC_REG_013 invalid email (without '@')
 *	TC_REG_014 ph # with 11 digits
 *	TC_REG_015 <10 digits mobile number
 *	TC_REG_016 Alpha Numeric as mobile number
 *	TC_REG_017 Incorrect DOB
 *	TC_REG_018 Custom Security Question	
 *	TC_REG_021 Security Answer max length 
 *	TC_REG_022 Security Question & Answer min length, Error for security question too small
 */
public class TC_REG_060 extends BaseTest 
{
	public String TC=getTestScenario("TC_REG_060");
	
	public String tcId;
	public int prevTCStatus=5; // First TC random value
	public String mobileNumber;
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{
		tcId=testMethod.getName();
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("TC_REG_060"))
		{	
			Log.info("================ Group execution "+tcId+" : "+getTestScenario(tcId)+" ================");
			groupExecute=true;		
		}		
	}	
	
	public void navigate()
	{
		// ======== Initialize ======== //
		
		mobileNumber=getTestData(tcId).split(",")[0];		
		
		if(prevTCStatus==1) return; //1=SUCCESS
		
		// ======== Navigate ======== //
		
		if(prevTCStatus<5)// First TC
			 Generic.switchToApp(driver);
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);	
	}	
	
	
	@Test(priority=1)
	public void TC_REG_010() 
	{
		navigate();
		
		RegisterPage rp=new RegisterPage(driver);		
		
		rp.enterValues(getTestData("TC_REG_010"));	
		rp.verifyInvalidPinErrMsg();	
		
		Generic.swipeUp(driver);
	}
	
	@Test(enabled=false) 
	public void TC_REG_011() // -------- TC_REG_011 -------- // Functionality has changed, No error message is displayed. Instead the first 12 digits are accepted
	{
		  
		/*testScenario=getTestScenario("TC_REG_011");
		Log.info("======== TC_REG_011 : "+testScenario+" ========");
		
		rp.enterValues(getTestData("TC_REG_011"));	
		rp.verifyInvalidPinErrMsg();
		
		Generic.scroll(driver, pageResetText);*/
		//-------------------------------//				
	}
	
	@Test(priority=2) 
	public void TC_REG_012() // -------- TC_REG_012 : invalid email (without '.com') -------- //
	{
		navigate();
		
		RegisterPage rp=new RegisterPage(driver);		
		rp.enterValues(getTestData(tcId));	
		rp.verifyInvalidEmailErrMsg();
		
		Generic.swipeUp(driver);
	}
	
	@Test(priority=3)
	public void TC_REG_013() // -------- TC_REG_013 : invalid email (without '@') -------- //
	{
		navigate();
		
		RegisterPage rp=new RegisterPage(driver);
				
		rp.enterValues(getTestData(tcId));	
		rp.verifyInvalidEmailErrMsg();
		
		Generic.swipeUp(driver);		
	}
	
	@Test(priority=4)
	public void TC_REG_017() // -------- TC_REG_017 : Incorrect DOB-------- //
	{
		navigate();
		
		RegisterPage rp=new RegisterPage(driver);		
				
		rp.enterValues(getTestData(tcId));
		rp.verifyInvalidDob();
		
		Generic.swipeUp(driver);				
	}
	
	@Test(priority=5)
	public void TC_REG_021() // -------- TC_REG_021 : Security Answer max length ------- //
	{
		navigate();
		
		RegisterPage rp=new RegisterPage(driver);		
		rp.enterValues(getTestData(tcId));		
		rp.verifySecurityAnswerLongErrMsg();
		
		Generic.swipeUp(driver);
	}
	
	@Test(priority=6)
	public void TC_REG_022() // -------- TC_REG_022 : Security Question & Answer minimum length, Error for security question too small -------- //
	{
							 // -------- TC_REG_018 : Custom Security Question Displayed	-------- //		
		navigate();
		
		RegisterPage rp=new RegisterPage(driver);				
		rp.enterValues(getTestData(tcId));		
		rp.verifyCustomQuestionShortErrMsg();
				
		Generic.swipeUp(driver);
	}
	
	@Test(priority=7)
	public void TC_REG_015() // -------- TC_REG_015 : <10 digits mobile number -------- //
	{
		navigate();
		
		RegisterPage rp=new RegisterPage(driver);			
				
		rp.enterValues(getTestData(tcId));	
		rp.verifyInvalidNumberErrMsg();
				
		Generic.swipeUp(driver);
	}
	
	@Test(priority=8)
	public void TC_REG_014() // -------- TC_REG_014 : ph # with 11 digits -------- //
	{
		navigate();
		
		RegisterPage rp=new RegisterPage(driver);	
		rp.verify10DigitNumber(getTestData(tcId));	
	}
	
	@Test(priority=9)
	public void TC_REG_016() // -------- TC_REG_016 : Alpha Numeric as mobile number -------- // 
	{		
		navigate();	
		
		RegisterPage rp=new RegisterPage(driver);		
		rp.verifyAlphaNumericNumber(getTestData(tcId));
	}	
	
	@AfterMethod
	public void setStatus(ITestResult result)
	{
		prevTCStatus=result.getStatus();
	}


}
