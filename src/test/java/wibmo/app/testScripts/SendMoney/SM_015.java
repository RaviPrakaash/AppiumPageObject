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
 * The Class SM_015 Send Money to valid registered email.
 */
public class SM_015 extends BaseTest // ==== Send Money to valid registered email ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_015");

	/**
	 * Sm 015.
	 */
	@Test
	public void SM_015()
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("SM_015");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++]; 
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,data);	
		
		HomePage hp=new HomePage(driver);
		hp.addSend();

		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughEmail();		
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();
	}
}
