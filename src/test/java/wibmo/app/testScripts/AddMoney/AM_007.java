package wibmo.app.testScripts.AddMoney;

import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.AddMoney.BaseTest;

/**
 * The Class AM_007 Load money using another user's wibmo card with insufficient balance.
 */
public class AM_007 extends BaseTest // ==== Load money using another user's wibmo card with insufficient balance ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_007");
	
	/**
	 * Am 007.
	 */
	@Test
	public void AM_007() 
 	{ 
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("AM_007");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++],amt=values[i++],cardDetails=values[i++],cardPin=values[i++]; 
		
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
		amscp.addMoney(cardDetails);
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);		
		am3p.addMoney3ds(cardPin);	
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyTransactionFailure();		
				
 	}

}
