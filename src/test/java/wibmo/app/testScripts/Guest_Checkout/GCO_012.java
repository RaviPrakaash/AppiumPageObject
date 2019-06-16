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
import wibmo.webapp.pagerepo.Merchant3DSPage;

// TODO: Auto-generated Javadoc
/**
 * The Class GCO_012 IAP transaction cancellation on step 2 3DS 
 */
public class GCO_012 extends BaseTest
{	
	public String TC=getTestScenario("GCO_012");
	
 	@BeforeMethod
	 public void launchApplication()
	 {
		 launchAsWebDriver();
	 }
	 
	 /**
 	 * Gco 012.
 	 */
 	@Test
	 public void GCO_012()
	 {
		 Reporter.log(getTestScenario());
		 String data=getTestData("GCO_012");
		 String[] str = data.split(",");
		 
		 MerchantMainPage mmp=new MerchantMainPage(driver);
		 mmp.basicInfoSubmit(data);
		 
		 MerchantCheckOutPage mcp=new MerchantCheckOutPage(driver);
		 mcp.merchantCheckout(data);
		 
		 MerchantPaymentPage mpp=new MerchantPaymentPage(driver);
		 mpp.enterCardDetails(data);
		 
		 Merchant3DSPage m3dsp=new Merchant3DSPage(driver);
		 m3dsp.cancelVisaVerification();
		 
		 Generic.wait(2);
		 
		 IAPPostReportPage iprp=new IAPPostReportPage(driver);
		 iprp.verifyFailure();

		String transactionId=iprp.fetchwibmoid();	//Fetch the transaction id from IAP Report
			
		CSR.verifyStatusOnTxnId(driver, transactionId, "043", "3DS Failed"); 		 

	 }	
}
