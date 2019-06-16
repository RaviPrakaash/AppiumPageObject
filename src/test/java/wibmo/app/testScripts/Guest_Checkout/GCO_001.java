package wibmo.app.testScripts.Guest_Checkout;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import library.Generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wibmo.app.testScripts.BaseTest1;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.MerchantTransactionFinalPage;
import wibmo.webapp.pagerepo.PostPaymentScreenPage;
import wibmo.webapp.pagerepo.MerchantPaymentPage;
import wibmo.webapp.pagerepo.IAPPostReportPage;
import wibmo.webapp.pagerepo.Merchant3DSPage;


// TODO: Auto-generated Javadoc
/**
 * The Class GCO_001 Unregistered User Payment - Verify Payment and skip registration 
 */
public class GCO_001 extends BaseTest {
	
	/** The tc. */
	public String TC=getTestScenario("GCO_001");	
	
	@BeforeMethod
	public void launchApplication()
	{			   
		launchAsWebDriver();	    
	}
	
	/**
	 * Gco 001.
	 */
	@Test
	public void GCO_001()		//Unregistered User Payment
	{
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_001");
		String str[]=data.split(",");
		String securePin=str[7];
		
		MerchantMainPage main=new MerchantMainPage(driver);
		main.basicInfoSubmit(data);
		
		MerchantCheckOutPage checkout=new MerchantCheckOutPage(driver);
		checkout.merchantCheckout(data);
		
		MerchantPaymentPage payment=new MerchantPaymentPage(driver);
		payment.enterCardDetails(data);
		
		Merchant3DSPage mdsp=new Merchant3DSPage(driver);
		mdsp.submitVisaVerification(securePin);
		
		MerchantTransactionFinalPage paymentstatus=new MerchantTransactionFinalPage(driver);		
		paymentstatus.verifyPayment();
		paymentstatus.remindMeLater(); // GCO_006
		
		IAPPostReportPage iapprp=new IAPPostReportPage(driver);
		iapprp.verifySuccess();								
	}	
}
