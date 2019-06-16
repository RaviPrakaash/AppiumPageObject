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
 * The Class AM_006 Load money using another wibmo user's wibmo card.
 */
public class AM_006 extends BaseTest // ==== Load money using another wibmo user's wibmo card ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_006");
	
	/**
	 * Am 006.
	 */
	@Test
	public void AM_006() 
 	{ 
		Reporter.log(getTestScenario());
		int i=2;
		String data=getTestData("AM_006");
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
