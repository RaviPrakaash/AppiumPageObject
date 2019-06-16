package wibmo.app.testScripts.Guest_Checkout;

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
import wibmo.webapp.pagerepo.MerchantPaymentPage;
import wibmo.webapp.pagerepo.PopUpsHandlingPage;

/**
 * The Class GCO_011 IAP transaction cancellation on step 1
 */
public class GCO_011 extends BaseTest
{
	public String TC=getTestScenario("GCO_011");
	
	@BeforeMethod
	public void launchApplication()
	{
		launchAsWebDriver();
	}
	
	/**
	 * Gco 011.
	 */
	@Test
	public void GCO_011()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_011");
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcp=new MerchantCheckOutPage(driver);
		mcp.merchantCheckout(data);
		
		MerchantPaymentPage mpp=new MerchantPaymentPage(driver);
		mpp.cancelCardDetails(data);
		
		PopUpsHandlingPage php=new PopUpsHandlingPage(driver);
		php.acceptAlert();
		
		IAPPostReportPage iapprp=new IAPPostReportPage(driver);
		iapprp.verifyUserAbort();
		
		IAPPostReportPage icpr=new IAPPostReportPage(driver);
		String txnId=icpr.fetchwibmoid();	//Fetch the transaction id from IAP Report
		
		CSR.verifyStatusOnTxnId(driver, txnId, "204", "User Aborted");	// 010 Init Success

	}	
	
}
