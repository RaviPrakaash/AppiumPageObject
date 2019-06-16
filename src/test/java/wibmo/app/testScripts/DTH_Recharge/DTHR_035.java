package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.RechargePage;

/**
 * The Class DTHR_035 Enter amount greater than 5 digits & click on Cancel after entering valid details- contains the following scripts
 * DTHR_019	Enter > 5 digits in amount field
 * DTHR_024	Click on Cancel after entering the required details
 */
public class DTHR_035 extends BaseTest // === Enter > 5 digits in amount field ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_019");
	
	/**
	 * Dthr 019.
	 */
	@Test
	public void DTHR_035() // ==== Try to enter > 5 digits in amount field ==== // 
	{		
		Reporter.log(getTestScenario());
		groupExecute=true;
		
		String data=getTestData("DTHR_019");
		gotoDTHRecharge(data);	
		
		DTHRechargePage drp=new DTHRechargePage(driver);
		
		// ---------------- DTHR_019	Enter > 5 digits in amount field ---------------- //
		
		setGroupValue("DTHR_019");	
		drp.verify5DigitAmount(data);	
		
		// ---------------- DTHR_024	Click on Cancel after entering the required details ---------------- //
		
		setGroupValue("DTHR_024");
		data=getTestData("DTHR_024");
		drp.enterValues(data);	
		drp.clickCancel();
		
		RechargePage rp=new RechargePage(driver);
		rp.verifyRechargePage();		
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
