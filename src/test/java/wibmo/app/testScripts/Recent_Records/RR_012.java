package wibmo.app.testScripts.Recent_Records;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.RecentRecordsPage;

// TODO: Auto-generated Javadoc
/**
 * The Class RR_012 Enter valid amount with 5 digits.
 */
public class RR_012 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("RR_012");
	
	/**
	 * Rr 012.
	 */
	@Test
	public void RR_012() // ==== Should allow to enter enter upto 5 digits  ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("RR_012");
		gotoRecentRecords(data);
		
		RecentRecordsPage rrp=new RecentRecordsPage(driver);
		rrp.verify5DigitAmt(data);	
	}

}
