package wibmo.app.testScripts.Recent_Records;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.RecentRecordsPage;

// TODO: Auto-generated Javadoc
/**
 * The Class RR_008 Check for editable amount field.
 */
public class RR_008 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("RR_008"); 
	
	/**
	 * Rr 008.
	 */
	@Test
	public void RR_008() // ==== Check amount field editable or not ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("RR_008");
		gotoRecentRecords(data);
		
		RecentRecordsPage rrp=new RecentRecordsPage(driver);
		rrp.verifyAmountField();		
	}
}
