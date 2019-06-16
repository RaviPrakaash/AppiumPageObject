package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.RechargePage;

// TODO: Auto-generated Javadoc
/**
 * The Class DTHR_024 Click on Cancel after entering required details.
 */
public class DTHR_024 extends BaseTest // ==== Click on Cancel after entering the required details  ==== // 
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_024"); 
	
	/**
	 * Dthr 024.
	 */
	@Test
	public void DTHR_024() 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_024");
		gotoDTHRecharge(data);	
		
		DTHRechargePage drp=new DTHRechargePage(driver);
		drp.enterValues(data);	
		drp.clickCancel();
		
		RechargePage rp=new RechargePage(driver);
		rp.verifyRechargePage();		
	}
}
