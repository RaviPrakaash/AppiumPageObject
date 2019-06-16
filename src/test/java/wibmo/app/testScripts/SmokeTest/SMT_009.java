package wibmo.app.testScripts.SmokeTest;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.SendMoneyPage;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_009 Send Money – Email.
 */
public class SMT_009 extends BaseTest // ==== Send Money – Email ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_009");
	
	/**
	 * Smt 009.
	 */
	@Test
	public void SMT_009()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_009");
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
