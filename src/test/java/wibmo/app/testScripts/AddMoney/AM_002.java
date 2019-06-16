package wibmo.app.testScripts.AddMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.ProgramCardPage;
import wibmo.app.pagerepo.SettingsPage;

/**
 * The Class AM_002 Load Money with ITP.
 */
public class AM_002 extends BaseTest // ==== Load Money with ITP ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_002");	
	
	/**
	 * Am 002.
	 */
	@Test
	public void AM_002() 
	{
		Reporter.log(getTestScenario());
		int i=2;
		String data=getTestData("AM_002");
		String[] values=data.split(",");				
		String amt=values[i++],cardDetails=values[i++];
		boolean ownCardAdded=false;
		
		setITP(true);
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);	
		Generic.preconditionITP(driver,data);		
		driver.navigate().back(); // Go to Settings Page
		
		SettingsPage sp=new SettingsPage(driver);
		sp.gotoProgramCard();
		
		//-------------- Get Program Card Details  ------------------//		
		
		ProgramCardPage pcp=new ProgramCardPage(driver);
		String ownCardDetails=pcp.getCardDetails();		
		pcp.gotoAddSendPage();		
		//----------------------------------------------------------//
		
		AddSendPage asp=new AddSendPage(driver);
		asp.clickLoadMoney();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.enterAmount(amt);			
		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		if(amscp.swipeToCard(cardDetails))
			amscp.addMoney(cardDetails);
		else
		{
			amscp.addMoneyWithNewCard(internalITPCard+':'+ownCardDetails);
			ownCardAdded=true;			
		}
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyITP();
		amfp.verifyTransactionSuccess();
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		// Check for External ITP , otherwise ITP Program card balance will not change
		// If using own Programcard as an external ITP card , save Program Card as "OwnCard" under Manage Cards
		
		if((!cardDetails.toLowerCase().contains(internalITPCard.toLowerCase()) && !ownCardAdded) || checkEnv("qa"))  
			Generic.verifyBalanceAdded(balanceBeforeTransaction,Double.parseDouble(amt), balanceAfterTransaction);		
	}

}
