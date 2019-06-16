package wibmo.app.testScripts.SmokeTest;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.SendMoneyPage;

/**
 * The Class SMT_008 Send Money – Mobile.
 */
public class SMT_008 extends BaseTest // ==== Send Money – Mobile ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_008");

	/**
	 * Smt 008.
	 */
	@Test
	public void SMT_008()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_008");
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
