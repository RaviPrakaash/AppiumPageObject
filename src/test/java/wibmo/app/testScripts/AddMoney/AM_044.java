package wibmo.app.testScripts.AddMoney;

import library.Generic;
import library.Log;
import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;

/**
 * The Class AM_044 Load Money with different cards
 * 
 * AM_001 : Load Money
 * AM_006 : Load money using another wibmo user's wibmo card
 * AM_014 : Load money using other bank cards
 * AM_007 : Load money using another user's wibmo card with insufficient balance
 * 
 */
public class AM_044 extends BaseTest  // ==== Load Money ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_001");
	public String  tcId;
	public int prevTCStatus=2; // First TC
	public String data,amt,cardDetails,cardPin;
	double balanceBeforeTransaction;
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{		
		tcId=testMethod.getName();
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("AM_044"))
		{	
			Log.info("================ Group execution "+tcId+" : "+getTestScenario(tcId)+" ================");
			groupExecute=true;		
		}				
	}
	
	public void navigate()
	{			
		// ==== Initialize ==== //			
		int i=2;
		data=getTestData(tcId);
		String[] values=data.split(",");
		amt=values[i++];cardDetails=values[i++];cardPin=values[i++]; 		
		
		if(prevTCStatus==1) return; //1=SUCCESS
		
		// ==== Navigate ==== //
		Generic.switchToApp(driver);
		balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);				
	}	
	
	/**
	 * Am 001.
	 */
	@Test
	public void AM_001() // AM_001 : Load Money
 	{ 
		Reporter.log(getTestScenario());		
		
		navigate();
		
		AddSendPage asp=new AddSendPage(driver);	
		asp.clickLoadMoney();		
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.enterAmount(amt);			
		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		amscp.addMoney(cardDetails);
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);		
		am3p.addMoney3ds(cardPin);	
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		if(tcId.equals("AM_007")) {amfp.verifyTransactionFailure(); return;} // AM_007 : Insufficient Balance scenario
		amfp.verifyTransactionSuccess();
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceAdded(balanceBeforeTransaction,Double.parseDouble(amt), balanceAfterTransaction);	
		
		balanceBeforeTransaction=balanceAfterTransaction;		
 	}
	
	@Test
	public void AM_006() // AM_006 : Load money using another wibmo user's wibmo card
	{
		AM_001();
	}
	
	@Test
	public void AM_014() // AM_014 : Load money using other bank cards
	{
		AM_001();
	}
	
	@Test
	public void AM_007() // AM_007 : Load money using another user's wibmo card with insufficient balance
	{
		AM_001();
	}
	
	@AfterMethod
	public void setStatus(ITestResult result)
	{
		prevTCStatus=result.getStatus();
	}
}
