package wibmo.app.testScripts.SendMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.SendMoneyPage;

// TODO: Auto-generated Javadoc
/**
 * The Class SM_017 Verify Sender balance after Sending Money to email.
 */
public class SM_017 extends BaseTest // ==== Verify Sender balance after Send Money to email ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_017");

	/**
	 * Sm 017.
	 */
	@Test
	public void SM_017()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SM_017");
		double amt = Double.parseDouble(data.split(",")[4]);		

		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);

		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughEmail();	
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();

		double balanceAfterTransaction=asp.verifyBalance();

		Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt,balanceAfterTransaction);
	}
}
