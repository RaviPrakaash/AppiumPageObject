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
 * The Class SM_027 Send money to invalid e-mail ID.
 */
public class SM_027 extends BaseTest // ==== Send money to invalid e-mail ID ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_027");

	/**
	 * Sm 027.
	 */
	@Test
	public void SM_027()
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("SM_027");
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
		smp.verifyInvalidEmail();		
	}	
}
