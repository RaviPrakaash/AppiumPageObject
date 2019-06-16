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

// TODO: Auto-generated Javadoc
/**
 * The Class AM_013 Load money with blank CVV.
 */
public class AM_013 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_013");
	
	/**
	 * Am 013.
	 */
	@Test
	public void AM_013() 
 	{ 
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("AM_013");
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
		am3p.verify3DS();
		am3p.addMoneySubmit(cardPin);
		am3p.verifyCVV();
		am3p.enterCvv(cardPin);
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyTransactionFailure();				
 	}
}
