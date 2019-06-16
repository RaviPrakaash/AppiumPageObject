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
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.MerchantPaymentPage;

// TODO: Auto-generated Javadoc
/**
 * The Class GCO_009 IAP for unregistered user using invalid card no.
 *
 */
public class GCO_009 extends BaseTest
{	
	public String TC=getTestScenario("GCO_009");
	
	@BeforeMethod
	public void launchApplication()
	{
		launchAsWebDriver();
	}
	
	/**
	 * Gco 009.
	 */
	@Test
	public void GCO_009()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_009");
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcp=new MerchantCheckOutPage(driver);
		mcp.merchantCheckout(data);
		
		MerchantPaymentPage mpp=new MerchantPaymentPage(driver);
		mpp.invalidCardNum(data);		
	}	
}
