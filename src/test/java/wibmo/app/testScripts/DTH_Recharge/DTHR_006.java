package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class DTHR_006 click on pay now button without entering amount.
 */
public class DTHR_006 extends BaseTest // ==== Without entering the amount , try to click on pay now button ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_006");
	
	/**
	 * Dthr 006.
	 */
	@Test
	public void DTHR_006() // ==== Without entering the amount , try to click on pay now button ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_006");
		gotoDTHRecharge(data);	
		
		DTHRechargePage drp=new DTHRechargePage(driver);
		drp.enterValues(data);		
		drp.verifyPayNowButtonDisable();				
	}
}
