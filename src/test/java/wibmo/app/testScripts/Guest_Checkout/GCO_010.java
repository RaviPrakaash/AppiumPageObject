package wibmo.app.testScripts.Guest_Checkout;

import java.util.concurrent.TimeUnit;

import library.Generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wibmo.app.testScripts.BaseTest1;
import wibmo.webapp.pagerepo.IAPPostReportPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.MerchantPaymentPage;
import wibmo.webapp.pagerepo.PopUpsHandlingPage;
import wibmo.webapp.pagerepo.Merchant3DSPage;

// TODO: Auto-generated Javadoc
/**
 * The Class GCO_010 IAP for unregistered user using invalid card expiry
 *
 */
public class GCO_010 extends BaseTest
{	
	
	public String TC=getTestScenario("GCO_010");
	
	@BeforeMethod
	public void launchApplication()
	{
		launchAsWebDriver();
	}
	
	/**
	 * Gco 010.
	 */
	@Test
	public void GCO_010()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_010");
		String[] str = data.split(",");
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcp=new MerchantCheckOutPage(driver);
		mcp.merchantCheckout(data);
		
		
		
		MerchantPaymentPage mpp=new MerchantPaymentPage(driver);
		mpp.invalidCardExpiry(data);
		
		Merchant3DSPage m3dsp=new Merchant3DSPage(driver);
		m3dsp.submitVisaVerification(str[7]);
		
		IAPPostReportPage iapprp=new IAPPostReportPage(driver);
		iapprp.verifyFailure();		
	}	
	
}
