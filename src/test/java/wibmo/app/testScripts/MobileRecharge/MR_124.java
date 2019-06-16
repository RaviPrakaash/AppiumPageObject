package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import library.Generic;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class MR_124 Select an operator from the default operators displayed , contains the following scripts
 * 
 *  MR_029	Default operators should show in the operator drop down list
 *  MR_030	Select any operator and check selected
 * 
 */
public class MR_124 extends BaseTest // ==== Default operators should be shown in the list ==== // 
{
	
	/** The tc field . */
	public String TC=getTestScenario("MR_124");
	
	/**
	 * Mr 124.
	 */
	@Test
	public void MR_124() 
	{
		Reporter.log(getTestScenario());
		groupExecute=true;
		
		String data=getTestData("MR_029");
		gotoMobileRecharge(data);
		
		MobileRechargePage mrp=new MobileRechargePage(driver);
		mrp.enterMobileNumber(data);
		
		MobileRechargePayPage mrpp=new MobileRechargePayPage(driver);	
		
		// ---------------- MR_029	Default operators should show in the operator drop down list ---------------- //
		setGroupValue("MR_029");
		mrpp.verifyOperatorList();		
		driver.navigate().back();
		
		// ---------------- MR_030	Select any operator and check selected ---------------- //
		setGroupValue("MR_030");		
		mrpp.verifyOperatorSelect();
		
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
