package wibmo.app.testScripts.SmokeTest;

import library.Generic;
import library.Log;
import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.SendMoneyConfirmPage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.WelcomePage;

/** * 
 * 
 * SMT_050	Group Execute : Send Money to Bank with success & decline response
 * SMT_051 :	Send Money to Bank with success response
 * SMT_052 :	Send Money to Bank with decline response
 *
 */

@Test
public class SMT_050 extends BaseTest // ==== Send Money to valid mobile number ==== // 
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_050");	
	public String tcId,data;
	double balanceBeforeTransaction,amt;
	
	public int prevTCStatus=5; // First TC
	
	
	@BeforeMethod
	private void setExecutionStatus(Method testMethod)
	{		
		tcId=testMethod.getName();	
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("SMT_050"))			
			Log.info("================ Group execution "+tcId+" : "+getTestScenario(tcId)+" ================");		
	}
	
	private void navigate()
	{
		// ======== Initialize ======== //
		
		data=getTestData(tcId);	
		amt=Double.parseDouble(data.split(",")[5]);
		
		if(prevTCStatus==1) return; //1=SUCCESS			
		
		// ======== Navigate ======== //
		
		if(prevTCStatus<5)
			if(Generic.isIos(driver))
			{
				navigateBack();
				logout();
			}
			else
				Generic.switchToApp(driver);
		
		balanceBeforeTransaction=checkWalletBalance(data);		
	}

	/**
	 * SMT_051
	 */
	//@Test
	public void SMT_051()
	{	
		navigate();
		
		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyToBank();		
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterBankValues(data);
		
		SendMoneyConfirmPage smcp=new SendMoneyConfirmPage(driver);
		amt=smcp.confirmBankValues(data);
		
		smp.verifyFundTransferSuccessMsg();
		
		smp.navigateBackOnFavorites();
		
		asp.refreshTransactionList(); // Auto refresh not happening after send money to bank 
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction, amt, balanceAfterTransaction);
		
		balanceBeforeTransaction=balanceAfterTransaction; // precondition for next @Test
	}
	
	//@Test  
	public void SMT_052()
	{
		navigate();
		
		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyToBank();		
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterBankValues(data);
		
		SendMoneyConfirmPage smcp=new SendMoneyConfirmPage(driver);
		amt=smcp.confirmBankValues(data);
		
		smp.verifyFundTransferFailureMsg();	
		
		//smp.navigateBackOnFavorites();
		
		//double balanceAfterTransaction=asp.verifyBalance();		Debit unpredictable
		//Generic.verifyBalanceDeduct(balanceBeforeTransaction, 0, balanceAfterTransaction);		
	}		
	
	@AfterMethod
	private void setStatus(ITestResult result)
	{
		prevTCStatus=result.getStatus();
	}
}
