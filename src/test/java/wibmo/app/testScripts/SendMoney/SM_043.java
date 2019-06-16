package wibmo.app.testScripts.SendMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.WelcomePage;

// TODO: Auto-generated Javadoc
/**
 * The Class SM_043	Send Money to Invalid emailId & mobile number, contains the following scripts
 * 
 * SM_027	Send money to invalid e-mail ID
 * SM_028	Send money to invalid 9 digit mobile number 
 * 
 */
public class SM_043 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_043");

	/**
	 * Sm 043.
	 */
	@Test
	public void SM_043()
	{
		Reporter.log(getTestScenario());
		groupExecute=true;
		
		int i=0;
		String data=getTestData("SM_028");
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
		
		// ---------------- SM_028	Send money to invalid 9 digit mobile number ---------------- //
		
		setGroupValue("SM_028");
		smp.enterValues(data);
		smp.verifyInvalidMobile();
		
		// ---------------- SM_027	Send money to invalid e-mail ID ---------------- //
		
		setGroupValue("SM_027");
		data=getTestData("SM_027");
		smp.enterValues(data);
		smp.verifyInvalidEmail();	
		
	}	
	
	/**
	 * Indicates completion of Group execution
	 * 
	 */
	@AfterMethod
	public void completeGroupExecution()
	{
		groupExecute=false;
	}
}
