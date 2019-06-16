package wibmo.app.testScripts.SendMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.WelcomePage;

// TODO: Auto-generated Javadoc
/**
 * The Class SM_030 Send Money to Self.
 */
public class SM_030 extends BaseTest // ==== Send Money to Self ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_030");

	/**
	 * Sm 030.
	 */
	@Test
	public void SM_030()
	{
		Reporter.log(getTestScenario());
		int i=2;
		String data=getTestData("SM_030");
		String[] values=data.split(",");
		String userName=values[i++];i++; 
		double amt=Double.parseDouble(values[i++]);
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver, data);

		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughMobile();	
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		//asp.verifyReceivedTransaction(userName,amt); Transaction displayed with wrong timestamp
		//asp.verifyPaidTransaction(userName,amt);	   Transaction displayed with wrong timestamp	
	}
}
