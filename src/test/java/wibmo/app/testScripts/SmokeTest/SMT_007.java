package wibmo.app.testScripts.SmokeTest;

import org.testng.Reporter;
import org.testng.annotations.Test;

import library.Generic;
import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SMT_007 Load Money via 3DS.
 */
public class SMT_007 extends BaseTest // ==== Load Money via 3DS  ==== //	
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_007");
	
	/**
	 * Smt 007.
	 */
	@Test
	public void SMT_007() 
 	{ 
		Reporter.log(getTestScenario());
		int i=2;
		String data=getTestData("SMT_007");
		String[] values=data.split(",");
		String amt=values[i++],cardDetails=values[i++],cardPin=values[i++]; 
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver, data);
		
		AddSendPage asp=new AddSendPage(driver);
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
