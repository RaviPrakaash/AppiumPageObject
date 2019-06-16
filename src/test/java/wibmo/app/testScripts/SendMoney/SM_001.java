package wibmo.app.testScripts.SendMoney;

import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.Test;
import com.libraries.Log;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SM_001 Verify Send Money option.
 */
public class SM_001 extends BaseTest // ==== Verify Send Money option ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_001");

	/**
	 * Sm 001.
	 */
	@Test
	public void SM_001()
	{
		Reporter.log(getTestScenario());
		int i=0;
		
		
		String data=getTestData("SM_001");
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
		asp.verifySendMoneyOption();			
	}
}
