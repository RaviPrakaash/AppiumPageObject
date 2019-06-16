package wibmo.app.testScripts.Recent_Records;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.RecentRecordsPage;

// TODO: Auto-generated Javadoc
/**
 * The Class RR_014 Verify View Plans page for Prepaid record.
 */
public class RR_014 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("RR_014");
	
	/**
	 * Rr 014.
	 */
	@Test
	public void RR_014() // ==== View plans page should  be displayed for Prepaid ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("RR_014");
		gotoRecentRecords(data);
		
		RecentRecordsPage rrp=new RecentRecordsPage(driver);
		rrp.verifyViewPlansPage(data);	
	}

}
