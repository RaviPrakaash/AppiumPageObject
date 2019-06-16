package wibmo.app.testScripts.Recent_Records;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.RecentRecordsPage;

/**
 * The Class RR_021 Click Pay Now with valid amount.
 */
public class RR_021 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("RR_021");
	
	/**
	 * Rr 021.
	 */
	@Test
	public void RR_021() // ==== Clicking Pay Now should trigger to payment page with selected details  ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("RR_021");
		gotoRecentRecords(data);
		
		RecentRecordsPage rrp=new RecentRecordsPage(driver);	
		rrp.clickRepeat();	
		rrp.verifyRepeat();
	}

}
