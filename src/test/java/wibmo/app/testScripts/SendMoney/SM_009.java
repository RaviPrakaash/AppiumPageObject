package wibmo.app.testScripts.SendMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.SendMoneyPage;

// TODO: Auto-generated Javadoc
/**
 * The Class SM_009 Verify Sender balance after Send Money.
 */
public class SM_009 extends BaseTest //==== Verify Sender balance after Send Money ==== // 
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_009");

	/**
	 * Sm 009.
	 */
	@Test
	public void SM_009()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SM_009");
		double amt = Double.parseDouble(data.split(",")[4]);		

		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);

		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughMobile();		
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();

		double balanceAfterTransaction=asp.verifyBalance();

		Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt,balanceAfterTransaction);
	}
}
