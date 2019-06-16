package wibmo.app.testScripts.IAP_Transaction;

import java.util.concurrent.TimeUnit;

import library.CSR;
import library.Generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wibmo.app.testScripts.BaseTest1;
import wibmo.webapp.pagerepo.ConnectingPhonePage;
import wibmo.webapp.pagerepo.DeviceVerificationPage;
import wibmo.webapp.pagerepo.IAPPostReportPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.PayZappLoginPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTS_021  Web-SDK	IAP transaction cancellation in phone verification.Static Merchant.
 */
public class IAPTS_021 extends BaseTest {

	/** The tc. */
	public String TC=getTestScenario("IAPTS_021");
	

	
	@BeforeMethod
	public void launchApplication()
	{		   
	   launchAsWebDriver();	
	}	
	
	/**
	 * Iapts 021.
	 */
	@Test
	public void IAPTS_021()		//"Web-SDK	IAP transaction cancellation in phone verification"

	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_021");
		String str[]=data.split(",");
		String loginPassword=str[4];
		String balanceMobileNumber=str[1];
		
		double balanceBeforeTransaction=CSR.verifyBalance(balanceMobileNumber);
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcop=new MerchantCheckOutPage(driver); 
		mcop.merchantCheckout(data);
		
		ConnectingPhonePage cpp=new ConnectingPhonePage(driver);
		cpp.clickonskip();
		
		PayZappLoginPage pzlp=new PayZappLoginPage(driver);
		pzlp.addlogindetails(loginPassword);
		
		DeviceVerificationPage dvp=new DeviceVerificationPage(driver);
		dvp.dvccancel();
		
		IAPPostReportPage icpr=new IAPPostReportPage(driver);
		
		String transactionid=icpr.fetchwibmoid();
		
		double balanceAfterTransaction=CSR.verifyBalance(balanceMobileNumber);	
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction); 
		
		//CSR.verifyStatusOnTxnId(transactionid,"010","Init Success");
		CSR.verifyStatusOnTxnId(transactionid,"204","User Aborted");
	}	
	
}
