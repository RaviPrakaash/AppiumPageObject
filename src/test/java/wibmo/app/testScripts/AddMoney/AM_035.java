package wibmo.app.testScripts.AddMoney;

import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddSendPage;

/**
 * The Class AM_035 Load Money using blank amount and click Cancel- contains the following scripts
 * 
 * AM_028	Load money using blank amount
 * AM_030	Click on cancel on the load money details entry page
 *
 */
public class AM_035 extends BaseTest // ==== Load Money using blank amount and click Cancel ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_035");
	
	/**
	 * Add Money 035.
	 */
	@Test
	public void AM_035() 
 	{ 
		Reporter.log(getTestScenario());
		groupExecute=true;
		
		int i=0;
		String data=getTestData("AM_028");
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver, data);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.clickLoadMoney();	
		
		// ---------------- AM_028	Load money using blank amount ---------------- //
		
		setGroupValue("AM_028");
		AddMoneyPage am=new AddMoneyPage(driver);
		am.verifyBlankAmount();
		
		// ---------------- AM_030	Click on cancel on the load money details entry page ---------------- //
		
		setGroupValue("AM_030");
		String amt=getTestData("AM_030").split(",")[2];
		am.cancelAddMoney(amt);
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceAdded(balanceBeforeTransaction,0,balanceAfterTransaction);			
 	}
	/**
	 * Indicates completion of Group execution
	 * 
	 */
	@AfterMethod
	public void completeGroupExecution()
	{
		groupExecute=false;
	}

}
