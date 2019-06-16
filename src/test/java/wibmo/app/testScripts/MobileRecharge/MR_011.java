package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class MR_011 Select contact number which has less than 10 digits.
 */
public class MR_011 extends BaseTest// ==== Select contact number which has less than 10 digits ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("MR_011");
	
	/**
	 * Mr 011.
	 */
	@Test
	public void MR_011() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("MR_011");
		gotoMobileRecharge(getTestData("MR_011"));
		
		MobileRechargePage mrp=new MobileRechargePage(driver);
		mrp.selectContact(data);
		
		mrp.verifyLessThan10DigitContactNumber();
		
	}
}
