package wibmo.app.testScripts.Recent_Records;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.RecentRecordsPage;

/**
 * The Class RR_007 Checks the presence of Recent records.
 */
public class RR_007 extends BaseTest
{	
	
	/** The tc. */
	public String TC=getTestScenario("RR_007");
	
	/**
	 * Rr 007.
	 */
	@Test
	public void RR_007() // ==== Click on Recently used module ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("RR_007");
		gotoRecentRecords(data);
		
		RecentRecordsPage rrp=new RecentRecordsPage(driver);
		rrp.verifyRecords();	
	}
}
