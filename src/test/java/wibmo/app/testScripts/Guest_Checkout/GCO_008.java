package wibmo.app.testScripts.Guest_Checkout;

import java.util.concurrent.TimeUnit;

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
import wibmo.webapp.pagerepo.MerchantTransactionFinalPage;
import wibmo.webapp.pagerepo.Merchant3DSPage;

// TODO: Auto-generated Javadoc
/**
 * The Class GCO_008  SMS generation for IAP for unregistered user & skip registration
 *
 */
public class GCO_008 extends BaseTest
{	
	public String TC=getTestScenario("GCO_008");
	
	@BeforeMethod
	public void launchApplication()
	{
		launchAsWebDriver();
	}	
	
	/**
	 * Gco 008.
	 */
	@Test
	public void GCO_008()
	{
		
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_008");
		String[] str = data.split(",");
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcp=new MerchantCheckOutPage(driver);
		mcp.merchantCheckout(data);
		
		MerchantPaymentPage mpp=new MerchantPaymentPage(driver);
		mpp.enterCardDetails(data);
		
		Merchant3DSPage m3dsp=new Merchant3DSPage(driver);
		m3dsp.submitVisaVerification(str[7]);
		
		MerchantTransactionFinalPage mtfp=new MerchantTransactionFinalPage(driver);
		mtfp.verifyPayment();
		mtfp.remindMeLater();
		
		IAPPostReportPage iapprp=new IAPPostReportPage(driver);
		iapprp.verifySuccess();			
	}
	
	/**
	 * Tear down.
	 */
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
}