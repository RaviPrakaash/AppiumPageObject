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
import wibmo.webapp.pagerepo.*;

/**
 * The Class IAPTS_020 Web-SDK IAP transaction cancellation in login. Static Merchant.
 */
public class IAPTS_020 extends BaseTest {

	/** The tc. */
	public String TC=getTestScenario("IAPTS_020");
	
	
	@BeforeMethod
	public void launchApplication() 
	{		   
	   launchAsWebDriver();
	}	
	
	/**
	 * Iapts 020.
	 */
	@Test
	public void IAPTS_020()	//"Web-SDK IAP transaction cancellation in login"
	{
		Reporter.log(getTestScenario());
		
		String data=getTestData("IAPTS_020");		
		
		String str[]=data.split(",");
		
		String balanceMobileNumber=str[1];
		double balanceBeforeTransaction=CSR.verifyBalance(driver, str[1]);
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcop=new MerchantCheckOutPage(driver); 
		mcop.merchantCheckout(data);
		
		ConnectingPhonePage cpp=new ConnectingPhonePage(driver);
		cpp.clickonskip();
		
		PayZappLoginPage pzlp=new PayZappLoginPage(driver);
		pzlp.cancellogin();
		
		IAPPostReportPage iprp=new IAPPostReportPage(driver);		
		String transactionid=iprp.fetchwibmoid();		
		
		double balanceAfterTransaction=CSR.verifyBalance(driver, balanceMobileNumber);	
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction); 		
		
		CSR.verifyStatusOnTxnId(transactionid,"204","User Aborted");
		
		iprp.testAgain();
		
		
		
		
		
		
		
		
		
	}
	
}
