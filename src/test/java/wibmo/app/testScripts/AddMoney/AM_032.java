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

/**
 * The Class AM_032 Double tap on 3ds authentication page.
 */
public class AM_032 extends BaseTest  // ==== Double tap on 3ds authentication page ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_032");
	
	/**
	 * Am 032.
	 */
	@Test
	public void AM_032() 
 	{ 
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("AM_032");
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
		am.enterAmountDouble(amt);	// AM_029 : Click on add money button more than once		
		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		amscp.addMoney(cardDetails);
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);		
		am3p.addMoney3dsDouble(cardPin);	
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyTransactionSuccess();			
 	}
}
