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
import wibmo.webapp.pagerepo.IAPPostReportPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.MerchantShellAppConfigPage;
import wibmo.webapp.pagerepo.PopUpsHandlingPage;

/**
 * The Class IAPTS_025 Web-SDK  IAP transaction when message hash fails. Static Merchant.
 */
public class IAPTS_025 extends BaseTest
{		
	@BeforeMethod
	public void launchApplication()
	{
		launchAsWebDriver();
	}
	
	/**
	 * Iapts 025.
	 */
	@Test
	public void IAPTS_025()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_025");
		String str[] = data.split(",");
		String loginMobileNo=str[1];
		
		/*MerchantShellAppConfigPage msacp=new MerchantShellAppConfigPage(driver); 
		msacp.changeSecretHashKey(data);*/
		
		double balanceBeforeTransaction=CSR.verifyBalance(loginMobileNo);
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmitForHashFail(data);
		
		MerchantCheckOutPage mcp=new MerchantCheckOutPage(driver);
		mcp.merchantCheckout(data);
	
		IAPPostReportPage iapprp=new IAPPostReportPage(driver);
		iapprp.verifyHashFailed();
		String txnId=iapprp.fetchwibmoid();
		
		double balanceAfterTransaction=CSR.verifyBalance(loginMobileNo);
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		CSR.verifyStatusOnTxnId(txnId, "070", "Message Hash failed");
	}	
}
