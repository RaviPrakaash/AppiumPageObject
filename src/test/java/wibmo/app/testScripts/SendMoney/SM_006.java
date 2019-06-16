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
 * The Class SM_006 Send Money to valid mobile number.
 */
public class SM_006 extends BaseTest // ==== Send Money to valid mobile number ==== // 
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_006");

	/**
	 * Sm 006.
	 */
	@Test
	public void SM_006()
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("SM_006");
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
		asp.sendMoneyThroughMobile();		
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();
	}
}
