package wibmo.app.testScripts.AddMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.ManageCardsPage;

/**
 * The Class AM_001 Load Money.
 */
public class AM_001 extends BaseTest  // ==== Load Money ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_001");
	
	/**
	 * Am 001.
	 */
	@Test
	public void AM_001() 
 	{ 
		Reporter.log(getTestScenario());
		int i=2;
		String data=getTestData("AM_001");
		String[] values=data.split(",");
		String amt=values[i++],cardDetails=values[i++],cardPin=values[i++]; 
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);		
		
		AddSendPage asp=new AddSendPage(driver);	
		//asp.gotoManageCards();
		// ---- Precondition for Suite ---- //
		//ManageCardsPage mcp =new ManageCardsPage(driver);		
		//mcp.deleteDuplicateCards(); 
		//mcp.closeManagecards();
		// -------------------------------- //
		
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
		
		Generic.verifyBalanceAdded(balanceBeforeTransaction,Double.parseDouble(amt), balanceAfterTransaction);		
 	}

}
