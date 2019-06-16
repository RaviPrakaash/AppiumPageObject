package wibmo.app.testScripts.Recent_Records;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.RecentRecordsPage;

// TODO: Auto-generated Javadoc
/**
 * The Class RR_011 Verify Pay Now button enable.
 */
public class RR_011 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("RR_011");
	
	/**
	 * Rr 011.
	 */
	@Test
	public void RR_011() // ==== Pay Now button should be in enabled state below transaction  ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("RR_011");
		gotoRecentRecords(data);
		
		RecentRecordsPage rrp=new RecentRecordsPage(driver);
		rrp.verifyPayNowButtonEnable();
	}
}
