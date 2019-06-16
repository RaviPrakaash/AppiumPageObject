package wibmo.app.testScripts.SmokeTest;

import library.Generic;
import library.Log;
import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MvisaPage;
import wibmo.app.pagerepo.WibmoSDKPage;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * The Class SMT_046 : Perform mVisa transaction and Verify immediate refunds 
 * 
 */
public class SMT_046 extends BaseTest 
{	
	/** The tc. */
	public String TC=getTestScenario("SMT_046");
	
	public String tcId;
	public String data;
	
	public int prevTCStatus=5;
	
	public String amt,cardDetails,pinDetails,merchantId;
	public double balanceBeforeTransaction,balanceAfterTransaction;
	
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{
		tcId=testMethod.getName();
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("SMT_046"))		
			Log.info("================ Group execution "+tcId+" : "+getTestScenario(tcId)+" ================");
				
	}
	
	public void navigate()
	{
		// ======== Initialize ======== //
		
		data=getTestData(tcId);
		
		amt=data.split(",")[2];
		cardDetails=data.split(",")[3];	
		pinDetails=data.split(",")[4];
		merchantId=data.split(",")[5];
		
		if(prevTCStatus==1) return; //1=SUCCESS
		 
		 // ======== Navigate ======== //
		 
		 if(prevTCStatus<5)// First TC
			 Generic.switchToApp(driver);
		
		balanceBeforeTransaction=Generic.checkWalletBalance(driver, data);		
		
	}	
	
	/**
	 * Smt 047.
	 */
	@Test
	public void SMT_047()
	{
		navigate();		
		
		AddSendPage asp=new AddSendPage(driver);
		asp.gotoMvisa();
		
		MvisaPage mvp=new MvisaPage(driver);
		mvp.enterMerchantId(merchantId);
		mvp.enterAmt(amt);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);
		am3p.executeTransaction(pinDetails);
		
		mvp.verifyTransactionSuccess();
		
		if(Generic.containsIgnoreCase(cardDetails, programName))
		{
			asp.refreshTransactionList();
			balanceAfterTransaction=asp.verifyBalance();
			Generic.verifyBalanceDeduct(balanceBeforeTransaction, Double.parseDouble(amt), balanceAfterTransaction);
		}
		
		balanceBeforeTransaction=balanceAfterTransaction;			
	}
	
	@Test
	public void SMT_048()
	{
		navigate();		
		
		AddSendPage asp=new AddSendPage(driver);
		asp.gotoMvisa();
		
		MvisaPage mvp=new MvisaPage(driver);
		mvp.enterMerchantId(merchantId);
		mvp.enterAmt(amt);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);
		am3p.executeTransaction(pinDetails);
		
		mvp.verifyTransactionFailure(); 
		
		asp.refreshTransactionList();
		balanceAfterTransaction=asp.verifyBalance();
		
		if(!Generic.containsIgnoreCase(cardDetails, programName))			
			Generic.verifyBalanceAdded(balanceBeforeTransaction, Double.parseDouble(amt), balanceAfterTransaction);		
		else
			Generic.verifyBalanceAdded(balanceBeforeTransaction, 0, balanceAfterTransaction); // amt rotated back to same wallet card			
		
	}
	
	@AfterMethod
	public void setStatus(ITestResult result)
	{
		prevTCStatus=result.getStatus();
	}
}
