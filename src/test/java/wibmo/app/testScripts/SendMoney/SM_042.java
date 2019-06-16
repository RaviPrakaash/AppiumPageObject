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

/**
 * The Class SM_042 Verify Send Money Option and fields for Mobile and Email, contains following scripts
 * SM_001	Verify Send Money option 
 * SM_003	Verify Send Money fields for Mobile
 * SM_012	Verify Send Money fields for Email
 * 
 */
public class SM_042 extends BaseTest // ==== Verify Send Money fields ==== //
{
	
	/** The tc field. */
	public String TC=getTestScenario("SM_042");

	/**
	 * Send Money 042.
	 */
	@Test
	public void SM_042()
	{
		Reporter.log(getTestScenario());
		groupExecute=true;
		
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
		
		// ---------------- SM_001	Verify Send Money option ---------------- //
		
		setGroupValue("SM_001");
		asp.verifySendMoneyOption();
		
		// ---------------- SM_003	Verify Send Money fields for Mobile ---------------- //
		
		setGroupValue("SM_003");
		asp.sendMoneyThroughMobile();
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.verifyFields();	
		smp.cancelSend();		
		
		// ---------------- SM_012	Verify Send Money fields for Email ---------------- //
		
		setGroupValue("SM_012");
		asp.sendMoneyThroughEmail();
		smp.verifyFields();
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
