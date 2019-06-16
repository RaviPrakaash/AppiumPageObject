package wibmo.app.testScripts.AddMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;

// TODO: Auto-generated Javadoc
/**
 * The Class AM_030 Cancel load money.
 */
public class AM_030 extends BaseTest // ==== Cancel load money ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_030");
	
	/**
	 * Am 030.
	 */
	@Test
	public void AM_030() 
 	{ 
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("AM_030");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++],amt=values[i++]; 
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver, data);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.clickLoadMoney();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.cancelAddMoney(amt);
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceAdded(balanceBeforeTransaction,0,balanceAfterTransaction);			
 	}

}
