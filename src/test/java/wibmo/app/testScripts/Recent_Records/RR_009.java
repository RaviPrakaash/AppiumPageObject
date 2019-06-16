package wibmo.app.testScripts.Recent_Records;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.RecentRecordsPage;

// TODO: Auto-generated Javadoc
/**
 * The Class RR_009 Verify presence of View plans link for Prepaid transaction.
 */
public class RR_009 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("RR_009");
	
	/**
	 * Rr 009.
	 */
	@Test
	public void RR_009() // ==== View plans link should  be displayed for Prepaid ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("RR_009");
		gotoRecentRecords(data);
		
		RecentRecordsPage rrp=new RecentRecordsPage(driver);
		rrp.verifyViewPlansPrepaid(data);	
	}
}
