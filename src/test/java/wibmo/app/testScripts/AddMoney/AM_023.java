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
import wibmo.app.pagerepo.ManageCardsPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class AM_023 Special characters in Alias field.
 */
public class AM_023 extends BaseTest // ==== Special characters in Alias field ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_023");
	
	/**
	 * Am 023.
	 */
	@Test
	public void AM_023() 
 	{ 
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("AM_023");
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
		amscp.addMoneyWithNewCard(cardDetails);
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);		
		am3p.addMoney3ds(cardPin);	
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyTransactionSuccess();	
		
		Generic.wait(5); 		  // load screen wait for swipe
		driver.navigate().back();// Navigate to home page
		
		// App flow changed : New Card with existing card details will override existing card so that duplicate cards are not present.
		hp.gotoManageCards();
		Generic.hideKeyBoard(driver);
		
		ManageCardsPage mcp=new ManageCardsPage(driver);
		mcp.verifyCard(cardDetails);
		//mcp.deleteCurrentCard(); Postcondition //  cannot delete card since card number will be lost
 	}

}
