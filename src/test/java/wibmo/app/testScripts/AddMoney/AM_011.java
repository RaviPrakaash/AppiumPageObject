package wibmo.app.testScripts.AddMoney;

import library.CSR;
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
 * The Class AM_011 Load money giving invalid CVV.
 */
public class AM_011 extends BaseTest // ==== Load money giving invalid CVV ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_011");
	
	/**
	 * Am 011.
	 */
	@Test
	public void AM_011() 
 	{ 
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("AM_011");
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
		
		// ======== Set CVV Y  ======== //
		
		CSR.setCVVStatus(driver,cardDetails, Generic.parseCVVStatus(data));		
				
		//============================//
		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		amscp.addMoney(cardDetails);		
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);		
		am3p.addMoneySubmit(cardPin);
		am3p.verifyCVV();
		am3p.enterCvv(cardPin);
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyTransactionFailure();				
 	}

}
