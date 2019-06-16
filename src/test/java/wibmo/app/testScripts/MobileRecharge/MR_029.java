package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class MR_029 Verify default operators displayed in the list.
 */
public class MR_029 extends BaseTest // ==== Default operators should be shown in the list ==== // 
{
	
	/** The tc. */
	public String TC=getTestScenario("MR_029");
	
	/**
	 * Mr 029.
	 */
	@Test
	public void MR_029() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("MR_029");
		gotoMobileRecharge(data);
		
		MobileRechargePage mrp=new MobileRechargePage(driver);
		mrp.enterMobileNumber(data);
		
		MobileRechargePayPage mrpp=new MobileRechargePayPage(driver);		
		mrpp.verifyOperatorList();		
	}

}
