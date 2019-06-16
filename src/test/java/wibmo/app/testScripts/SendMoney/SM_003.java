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
 * The Class SM_003 Verify Send Money fields.
 */
public class SM_003 extends BaseTest // ==== Verify Send Money fields ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_003");

	/**
	 * Sm 003.
	 */
	@Test
	public void SM_003()
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("SM_003");
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
		smp.verifyFields();		
	}

}
