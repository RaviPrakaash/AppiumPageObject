package wibmo.app.testScripts.SmokeTest;

import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.ProgramCardPage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SMT_015 Lock Card – Send Money.
 */
public class SMT_015 extends BaseTest // ==== Lock Card – Send Money ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_015");

	/**
	 * Smt 015.
	 */
	@Test
	public void SMT_015()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_015");
		String mobileNo = data.split(",")[0],securePin=data.split(",")[1];
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(mobileNo);
		
		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver, data);
		
		HomePage hp = new HomePage(driver);
		hp.goToProgramCard();
		
		ProgramCardPage pcp = new ProgramCardPage(driver);
		pcp.lockCard();	
		pcp.gotoAddSendPage();	
		
		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughMobile();		
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();			
	}

}
