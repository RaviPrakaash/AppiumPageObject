package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class MR_030 Select any operator.
 */
public class MR_030 extends BaseTest // ==== Select any operator ==== // 
{
	
	/** The tc. */
	public String TC=getTestScenario("MR_030");
	
	/**
	 * Mr 030.
	 */
	@Test
	public void MR_030() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("MR_030");
		gotoMobileRecharge(data);
		
		MobileRechargePage mrp=new MobileRechargePage(driver);
		mrp.enterMobileNumber(data);
		
		MobileRechargePayPage mrpp=new MobileRechargePayPage(driver);		
		mrpp.verifyOperatorSelect();
		
		
		
		
	}
}
