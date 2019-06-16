package wibmo.app.testScripts.SendMoney;

import library.CSR;
import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.SendMoneyPage;

// TODO: Auto-generated Javadoc
/**
 * The Class SM_037 Send Money to email and check CSR tables.
 */
public class SM_037 extends BaseTest // ==== Send Money to email and check CSR tables ==== //
{	
	/** The tc. */
	public String TC=getTestScenario("SM_037");

	/**
	 * Sm 037.
	 */
	@Test
	public void SM_037()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SM_037");
		String loginId=data.split(",")[0];
		double amt = Double.parseDouble(data.split(",")[4]);		

		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);

		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughEmail();		
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();

		double balanceAfterTransaction=asp.verifyBalance();

		Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt,balanceAfterTransaction);
		
		//CSR.verifyInflowTransactionReport(loginId);
		//CSR.verifyOutflowTransaction(loginId);		 // verifyInflowOutflow(loginId)
	}

}
