package wibmo.app.testScripts.Recent_Records;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.RecentRecordsPage;

// TODO: Auto-generated Javadoc
/**
 * The Class RR_015 Verify Pay Now button disable after clearing amount field.
 */
public class RR_015 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("RR_015");
	
	/**
	 * Rr 015.
	 */
	@Test
	public void RR_015() // ==== Pay Now button should be in a disabled state after erasing the amount  ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("RR_015");
		gotoRecentRecords(data);
		
		RecentRecordsPage rrp=new RecentRecordsPage(driver);
		rrp.verifyPayNowButtonDisable();		
	}
}
