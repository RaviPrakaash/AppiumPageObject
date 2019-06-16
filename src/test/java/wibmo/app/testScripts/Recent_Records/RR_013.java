package wibmo.app.testScripts.Recent_Records;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.RecentRecordsPage;

// TODO: Auto-generated Javadoc
/**
 * The Class RR_013 Enter amount greater than 5 digits.
 */
public class RR_013 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("RR_013");
	
	/**
	 * Rr 013.
	 */
	@Test
	public void RR_013() // ==== Should not allow to enter more than 5 digits  ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("RR_013");
		gotoRecentRecords(data);
		
		RecentRecordsPage rrp=new RecentRecordsPage(driver);
		rrp.verify5DigitAmt(data);
	}
}
