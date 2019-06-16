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
 * The Class SM_033 Send Amount with decimal value.
 */
public class SM_033 extends BaseTest // ==== Send Amount with decimal value ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_033");

	/**
	 * Sm 033.
	 */
	@Test
	public void SM_033()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SM_033");
		
		Generic.checkWalletBalance(driver, data);

		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughMobile();		
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();		
	}
}
