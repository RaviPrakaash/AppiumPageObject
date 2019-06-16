package wibmo.app.testScripts.SendMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.SendMoneyRecentPage;

// TODO: Auto-generated Javadoc
/**
 * The Class SM_034 Verify receiver mobile number in recent list after Send Money.
 */
public class SM_034 extends BaseTest  // ==== Verify receiver mobile number in recent list after Send Money ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_034");

	/**
	 * Sm 034.
	 */
	@Test
	public void SM_034()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SM_034");
		
		String contactValue=data.split(",")[3];
		
		Generic.checkWalletBalance(driver, data);

		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughMobile();		
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();
		
		asp.sendMoneyThroughMobile();
		
		SendMoneyRecentPage smrp=new SendMoneyRecentPage(driver);
		smrp.verifyContactValue(contactValue);		
	}
}
