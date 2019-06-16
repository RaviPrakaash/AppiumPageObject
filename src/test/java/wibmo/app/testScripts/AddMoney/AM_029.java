package wibmo.app.testScripts.AddMoney;

import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;

/**
 * The Class AM_029 Click on add money more than once.
 */
public class AM_029 extends BaseTest // ==== Click on add money more than once ==== //

{
	
	/** The tc. */
	public String TC=getTestScenario("AM_029");
	
	/**
	 * Am 029.
	 */
	@Test
	public void AM_029() 
 	{ 
		Reporter.log(getTestScenario());
		int i=2;
		String data=getTestData("AM_029");
		String[] values=data.split(",");
		String amt=values[i++],cardDetails=values[i++],cardPin=values[i++]; 
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver, data);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.clickLoadMoney();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.enterAmountDouble(amt);		
		
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
