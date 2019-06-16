package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class MR_020 Enter less than 10 Digit number.
 */
public class MR_020  extends BaseTest// ==== Enter less than 10 Digit number ==== //
{
	
	/** The tc field. */
	public String TC=getTestScenario("MR_020");
	
	/**
	 * Mr 020.
	 */
	@Test
	public void MR_020() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("MR_020");
		gotoMobileRecharge(data);
		
		MobileRechargePage mrp=new MobileRechargePage(driver);		
		mrp.enterMobileNumber(data);			
		mrp.verifyLessThan10DigitMobileNumber();		
	}
}
