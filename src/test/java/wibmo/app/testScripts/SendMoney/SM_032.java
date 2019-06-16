package wibmo.app.testScripts.SendMoney;
import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.SendMoneyPage;

// TODO: Auto-generated Javadoc
/**
 * The Class SM_032 Send Money to Self with country code prefix.
 */
public class SM_032 extends BaseTest // ==== Send Money to Self with country code prefix ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_032");

	/**
	 * Sm 032.
	 */
	@Test
	public void SM_032()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SM_032");
		
		Generic.checkWalletBalance(driver,data);

		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughMobile();	
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();			
	}
}
