package wibmo.app.testScripts.SmokeTest;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.ProgramCardPage;
import wibmo.app.pagerepo.SettingsPage;
import library.Generic;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_005 Load Money - ITP.
 */
public class SMT_005 extends BaseTest // ==== Load Money - ITP ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_005");	
	
	/**
	 * Smt 005.
	 */
	@Test
	public void SMT_005() 
	{
		Reporter.log(getTestScenario());
		int i=2;
		String data=getTestData("SMT_005");
		String[] values=data.split(",");				
		String amt=values[i++],cardDetails=values[i++];
		boolean ownCardAdded=false;
		setITP(true);
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);	
		Generic.preconditionITP(driver,data);		
		driver.navigate().back();
		
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
		
		// Check for External ITP card, otherwise ITP Program card balance will not change
		// If using own Programcard as an external ITP card , save Program Card as "OwnCard" under Manage Cards
		
		if(!cardDetails.toLowerCase().contains(internalITPCard.toLowerCase()) && !ownCardAdded)  
			Generic.verifyBalanceAdded(balanceBeforeTransaction,Double.parseDouble(amt), balanceAfterTransaction);	
		
	}
	

}
