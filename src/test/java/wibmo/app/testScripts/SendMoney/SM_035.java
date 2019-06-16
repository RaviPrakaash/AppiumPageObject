package wibmo.app.testScripts.SendMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.SendMoneyRecentPage;
import wibmo.app.pagerepo.WelcomePage;

// TODO: Auto-generated Javadoc
/**
 * The Class SM_035 Verify receiver email contact in recent list after Send Money.
 */
public class SM_035 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_035");

	/**
	 * Sm 035.
	 */
	@Test
	public void SM_035()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SM_035");
		
		String contactValue=data.split(",")[3];
		
		Generic.checkWalletBalance(driver, data);

		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughEmail();		
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();
		
		asp.sendMoneyThroughEmail();
		
		SendMoneyRecentPage smrp=new SendMoneyRecentPage(driver);
		smrp.verifyContactValue(contactValue);		
	}
}
