package wibmo.app.testScripts.Recent_Records;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.RecentRecordsPage;

/**
 * 
 * The Class RR_027 Verify Amount field
 * 
 * RR_008	Check amount field editable or not
 * RR_012	Should allow to enter upto 5 digits 
 * RR_013	Should not allow to enter more than 5 digits  
 * 
 */
public class RR_027 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("RR_027");
	
	/**
	 * Rr 027.
	 */
	@Test
	public void RR_027() // ==== Clicking Pay Now should trigger to payment page with selected details  ==== // 
	{		
		Reporter.log(getTestScenario());
		groupExecute=true;
		
		String data=getTestData("RR_012");
		gotoRecentRecords(data);
		
		RecentRecordsPage rrp=new RecentRecordsPage(driver);
		
		// ---------------- RR_012	Should allow to enter upto 5 digits ---------------- //		
		
		setGroupValue("RR_012");
		rrp.verify5DigitAmt(data);		
		
		// ---------------- RR_013 Should not allow to enter more than 5 digits  ---------------- //
		
		setGroupValue("RR_013");
		data=getTestData("RR_013");
		rrp.verify5DigitAmt(data);		
	}
	
	/**
	 * Indicates completion of Group execution	 * 
	 */
	@AfterMethod
	public void completeGroupExecution()
	{
		groupExecute=false;
	}

}
