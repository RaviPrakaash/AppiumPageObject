package wibmo.app.testScripts.Recent_Records;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.RecentRecordsPage;

// TODO: Auto-generated Javadoc
/**
 * The Class RR_010 Verify absence of View plans link for Postpaid transaction.
 */
public class RR_010 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("RR_010");
	
	/**
	 * Rr 010.
	 */
	@Test
	public void RR_010() // ==== View plans link should not be displayed for Postpaid ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("RR_010");
		gotoRecentRecords(data);
		
		RecentRecordsPage rrp=new RecentRecordsPage(driver);
		rrp.verifyViewPlansPostpaid(data);	
	}
}
