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

// TODO: Auto-generated Javadoc
/**
 * The Class AM_025 Add a card with the same details as an already existing card, Check Balance.
 */
public class AM_025 extends BaseTest // ==== Add a card with the same details as an already existing card, Check Balance  ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_025");
	
	/**
	 * Am 025.
	 */
	@Test
	public void AM_025() 
 	{ 
		Reporter.log(getTestScenario());
		int i=2;
		String data=getTestData("AM_025");
		String[] values=data.split(",");
		String amt=values[i++],cardDetails=values[i++],cardPin=values[i++]; 
		String cardName=cardDetails.split(":")[0],cardNameExtension="_"+cardDetails.split(":")[1].substring(12);
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver, data);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.clickLoadMoney();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.enterAmount(amt);			
		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		amscp.addMoneyWithNewCard(cardDetails);
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);	
		am3p.verify3DS();
		am3p.addMoney3ds(cardPin);	
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyTransactionSuccess();
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceAdded(balanceBeforeTransaction,Double.parseDouble(amt),balanceAfterTransaction);	
		
		// App flow changed, New Card with Card Extension is not created
		
		/*asp.gotoManageCards();		
		ManageCardsPage mcp=new ManageCardsPage(driver);
		mcp.verifyCard(cardName+cardNameExtension);	
		mcp.deleteCurrentCard(); // PostCondition		
*/ 	}

}
