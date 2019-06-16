package wibmo.app.testScripts.AddMoney;

import library.Generic;
import library.Log;
import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;

/**
 * The Class  AM_045 :  Double debit of source card during load money - new card & fav card
 * 
 * AM_029 : Double Tap on add money button,Complete add with New Card
 * AM_032 : Double tap on 3ds authentication page, Choose linked card
 *
 *  
 */
public class AM_045 extends BaseTest  // ==== Double tap on 3ds authentication page ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_045");
	public String tcId;
	public int prevTCStatus=2; // First TC
	
	public String loginId,securePin;
	public String data,amt,cardDetails,cardPin;
	double balanceBeforeTransaction;
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{		
		tcId=testMethod.getName();
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("AM_045"))
		{	
			Log.info("================ Group execution "+tcId+" : "+getTestScenario(tcId)+" ================");
			groupExecute=true;		
		}				
	}
	
	public void navigate()
	{
		// ======== Initialize ======== //
		
		int i=2;
		String data=getTestData(tcId);
		String[] values=data.split(",");
		 amt=values[i++];cardDetails=values[i++];cardPin=values[i++]; 
		
		
		if(prevTCStatus==1) return; //1=SUCCESS
		
		// ======== Navigate ======== //
		
		Generic.switchToApp(driver);
		balanceBeforeTransaction=Generic.checkWalletBalance(driver, data);	
		
	}
	
	
	/**
	 * Am 032.
	 */
	@Test
	public void AM_029() 
 	{ 		
		navigate();
		
		AddSendPage asp=new AddSendPage(driver);
		asp.clickLoadMoney();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.enterAmountDouble(amt);			
		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		amscp.addMoneyWithNewCard(cardDetails); // Add money with new card
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);		
		am3p.addMoney3dsDouble(cardPin);	
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyTransactionSuccess();	
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		if(!Generic.containsIgnoreCase(cardDetails, internalITPCard))
			Generic.verifyBalanceAdded(balanceBeforeTransaction,Double.parseDouble(amt), balanceAfterTransaction);
		
		balanceBeforeTransaction=balanceAfterTransaction;
 	}
	
	@Test
	public void AM_032() 
 	{ 		
		navigate();
		
		AddSendPage asp=new AddSendPage(driver);
		asp.clickLoadMoney();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.enterAmountDouble(amt);	// AM_029 : Click on add money button more than once		
		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		amscp.addMoney(cardDetails);
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);		
		am3p.addMoney3dsDouble(cardPin);	
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyTransactionSuccess();	
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		if(!Generic.containsIgnoreCase(cardDetails, internalITPCard))
			Generic.verifyBalanceAdded(balanceBeforeTransaction,Double.parseDouble(amt), balanceAfterTransaction);		
 	}
	
	@AfterMethod
	public void setStatus(ITestResult result)
	{
		prevTCStatus=result.getStatus();
	}
	

	
}
