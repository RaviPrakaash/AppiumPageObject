package wibmo.app.testScripts.AddMoney;

import library.CSR;
import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.libraries.Log;
import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.ProgramCardPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class AM_008 Lock Card – Load Money ,Check Balance.
 */
public class AM_008 extends BaseTest // ==== Lock Card – Load Money ,Check Balance ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_008");

	/**
	 * Am 008.
	 */
	@Test
	public void AM_008()
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("AM_008");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++],amt=values[i++],cardDetails=values[i++],cardPin=values[i++];
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver, data);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.gotoProgramCard();		
		
		ProgramCardPage pcp = new ProgramCardPage(driver);
		pcp.lockCard();	
		pcp.gotoAddSendPage();	
		
		asp.clickLoadMoney();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.enterAmount(amt);			
		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		amscp.addMoney(cardDetails);
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);
		am3p.addMoney3ds(cardPin);	
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyTransactionSuccess();	
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceAdded(balanceBeforeTransaction,Double.parseDouble(amt),balanceAfterTransaction);	
		
		// Unlock card - Postcondition 
		asp.gotoProgramCard();
		pcp.unlockCard();
		Generic.wait(5);
	}
	
	@AfterMethod // Postcondition to unlock the locked card
	public void unlockcard()
	{
		int i=0;
		String data=getTestData("AM_008");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++];
		
		Log.info("== Executing Post Condition : Unlock Card == ");
		
		try
		{			
			
		if(CSR.unblockLinkedCard(loginId)) return;
			
		Generic.logout(driver);
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver, data);
		
		HomePage hp = new HomePage(driver);
		hp.goToProgramCard();
		
		ProgramCardPage pcp = new ProgramCardPage(driver);
		pcp.unlockCard();	
		
		}
		catch(Exception e)
		{
			Log.info("Exception in unlockCard() postcondition , Card was not unlocked\n"+e.getMessage()); 
		}
		catch(AssertionError ae)
		{
			Log.info("Exception in unlockCard() postcondition , Card was not unlocked\n"+ae.getMessage());
		}
	}
	
	

}
