package wibmo.app.testScripts.AddMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;

// TODO: Auto-generated Javadoc
/**
 * The Class AM_026 Load money giving blank alias name.
 */
public class AM_026 extends BaseTest // ==== Load money giving blank alias name ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_026");
	
	/**
	 * Am 026.
	 */
	@Test
	public void AM_026() 
 	{ 
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("AM_026");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++],amt=values[i++],cardDetails=values[i++]; 
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,data);
		
		HomePage hp=new HomePage(driver);
		hp.addSend();
		
		AddSendPage asp=new AddSendPage(driver);
		asp.clickLoadMoney();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.enterAmount(amt);			
		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		amscp.addMoneyWithNewCard(cardDetails);
		amscp.verifyBlankCardAlias(cardDetails);				
 	}
}
