package wibmo.app.testScripts.SendMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.SendMoneyContactDetailsPage;
import wibmo.app.pagerepo.SendMoneyContactsPage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.WelcomePage;

// TODO: Auto-generated Javadoc
/**
 * The Class SM_014 Send money  to an email contact from phone.
 */
public class SM_014 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_014");	

	/**
	 * Sm 014.
	 */
	@Test
	public void SM_014()
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("SM_014");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++],contactName=values[i++],email=values[i++];
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,data);	
		
		HomePage hp=new HomePage(driver);
		hp.addSend();
		
		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughEmail();
		
		SendMoneyContactsPage smcp=new SendMoneyContactsPage(driver);
		smcp.selectContact(contactName);
		
		SendMoneyContactDetailsPage smcdp=new SendMoneyContactDetailsPage(driver);
		smcdp.verifySelectedContact(contactName, email);		
	}
}
