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
import wibmo.webapp.pagerepo.Merchant3DSPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.MerchantPaymentPage;

// TODO: Auto-generated Javadoc
/**
 * The Class GCO_022  Group Execut : IAP for unregistered user using invalid card no. & invalid card expiry
 * 
 * GCO_009	IAP for unregistered user using invalid card no:
 * GCO_010	IAP for unregistered user using invalid card expiry
 *
 */
public class GCO_022 extends BaseTest
{	
	public String TC=getTestScenario("GCO_022");
	
	@BeforeMethod
	public void launchApplication()
	{
		launchAsWebDriver();
	}
	
	/**
	 * Gco 022.
	 */
	@Test
	public void GCO_022()
	{
		Reporter.log(getTestScenario());
		groupExecute=true;
		
		// -------------------------- GCO_009	IAP for unregistered user using invalid card no. --------------------//
		
		String data=getTestData("GCO_009");
		setGroupValue("GCO_009");
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcp=new MerchantCheckOutPage(driver);
		mcp.merchantCheckout(data);
		
		MerchantPaymentPage mpp=new MerchantPaymentPage(driver);
		mpp.invalidCardNum(data);
		
		// -------------------------- GCO_010	IAP for unregistered user using invalid card expiry ---------------------//
		
		data=getTestData("GCO_010");
		setGroupValue("GCO_010");
		
		mpp.invalidCardExpiry(data);
		
		Merchant3DSPage m3dsp=new Merchant3DSPage(driver);
		m3dsp.submitVisaVerification(data.split(",")[7]); //if(checkEnv("qa")) return;
		
		IAPPostReportPage iapprp=new IAPPostReportPage(driver);
		iapprp.verifyFailure();			
	}	
	@AfterMethod
	public void completeGroupExecution()
	{
		groupExecute=false;
	}
}
