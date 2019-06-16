package wibmo.app.testScripts.AddMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;

// TODO: Auto-generated Javadoc
/**
 * The Class AM_031 Click on app's back button on load money details entry page.
 */
public class AM_031 extends BaseTest // ==== Click on app's back button on load money details entry page ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_031");
	
	/**
	 * Am 031.
	 */
	@Test
	public void AM_031() 
 	{ 
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("AM_031");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++],amt=values[i++]; 
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver, data);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.clickLoadMoney();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.verifyNavigate(amt);
		
		HomePage hp=new HomePage(driver);
		hp.verifyPageTitle();
		Generic.wait(5); // Wait out VerifySim popup & Other popups if any
		hp.addSend();
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceAdded(balanceBeforeTransaction,0,balanceAfterTransaction);			
 	}
	

}
