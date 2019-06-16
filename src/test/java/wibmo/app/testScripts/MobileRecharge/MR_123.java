package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import library.Generic;
import wibmo.app.pagerepo.MobileRechargePage;

// TODO: Auto-generated Javadoc
/**
 * The Class MR_123 : Enter contact number & number less than 10 digits, contains the following scripts
 *  MR_011	Select contact number which has less than 10 digits
 *  MR_020	Enter Less than 10 Digit number
 *	
 *  
 */
public class MR_123  extends BaseTest
{
	
	/** The tc field. */
	public String TC=getTestScenario("MR_123");
	
	/**
	 * Mr 123.
	 */
	@Test
	public void MR_123() 
	{		
		Reporter.log(getTestScenario());
		groupExecute=true;
		
		String data=getTestData("MR_011");
		gotoMobileRecharge(data);
		
		MobileRechargePage mrp=new MobileRechargePage(driver);		
			
		
		// ---------------- MR_011	Select contact number which has less than 10 digits ---------------- //
		
		setGroupValue("MR_011");
		data=getTestData("MR_011");
		
		mrp.selectContact(data);		
		mrp.verifyLessThan10DigitContactNumber();
		
		// ---------------- MR_020	Enter Less than 10 Digit number ---------------- //
		
		setGroupValue("MR_020");
		data=getTestData("MR_020");
		mrp.enterMobileNumber(data);
		mrp.verifyLessThan10DigitMobileNumber();		
		
		
	}
	/**
	 * Indicates completion of Group execution
	 * 
	 */
	@AfterMethod
	public void completeGroupExecution()
	{
		groupExecute=false;
	}
}
