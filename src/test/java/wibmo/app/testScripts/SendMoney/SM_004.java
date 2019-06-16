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
 * The Class SM_004 Send money to a contact from phone.
 */
public class SM_004 extends BaseTest // ==== Send money :  Verify a contact from phone ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_004");

	/**
	 * Sm 004.
	 */
	@Test
	public void SM_004()
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("SM_004");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++],contactName=values[i++],contactNo=values[i++];
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,data);	
		
		HomePage hp=new HomePage(driver);
		hp.addSend();
		
		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughMobile();
		
		SendMoneyContactsPage smcp=new SendMoneyContactsPage(driver);
		smcp.selectContact(contactName);
		
		SendMoneyContactDetailsPage smcdp=new SendMoneyContactDetailsPage(driver);
		smcdp.verifySelectedContact(contactName, contactNo);		
	}

}
