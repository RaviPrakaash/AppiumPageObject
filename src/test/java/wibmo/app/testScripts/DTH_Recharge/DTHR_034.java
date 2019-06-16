package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.DTHRechargePage;

/**
 * The Class DTHR_034 Select Operator from the operators displayed, contains the following scripts
 * DTHR_013	DTH Operators should be displayed in alphabetical order
 * DTHR_014	Select any operator
 *  
 */
public class DTHR_034 extends BaseTest
{	
	/** The tc. */
	public String TC=getTestScenario("DTHR_034");
	
	/**
	 * Dthr 034.
	 */
	@Test
	public void DTHR_034() // ==== DTH Operators should be displayed in alphabetical order ==== // 
	{		
		Reporter.log(getTestScenario());
		groupExecute=true;
		
		String data=getTestData("DTHR_013");
		gotoDTHRecharge(data);	
		
		DTHRechargePage drp=new DTHRechargePage(driver);
		
		// ---------------- DTHR_013	DTH Operators should be displayed in alphabetical order ---------------- //
		
		setGroupValue("DTHR_013");
		drp.verifyDTHOperatorList();
		driver.navigate().back();
		
		// ---------------- DTHR_014	Select any operator ---------------- //
		
		setGroupValue("DTHR_014");
		drp.verifyOperatorSelect();  			
	}
	/**
	 * Indicates completion of Group execution	 
	 */
	@AfterMethod
	public void completeGroupExecution()
	{
		groupExecute=false;
	}
}
