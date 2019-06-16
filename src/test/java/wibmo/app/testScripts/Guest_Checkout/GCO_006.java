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
import wibmo.webapp.pagerepo.*;

// TODO: Auto-generated Javadoc
/**
 * The Class GCO_006 Unregistered user payment & skip registration
 */
public class GCO_006 extends BaseTest {	
	
	public String TC=getTestScenario("GCO_006");
	
	@BeforeMethod
	public void launchApplication()
	{				   
	   launchAsWebDriver(); 
	}
	
	/**
	 * Gco 006.
	 */
	@Test
	public void GCO_006()			//Unregistered user & skip registration
	{
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_006");
		String str[]=data.split(",");
		String securePin=str[7];
		
		MerchantMainPage main=new MerchantMainPage(driver);
		main.basicInfoSubmit(data);
		
		MerchantCheckOutPage checkout=new MerchantCheckOutPage(driver);
		checkout.merchantCheckout(data);
		
		MerchantPaymentPage payment=new MerchantPaymentPage(driver);
		payment.enterCardDetails(data);
		
		Merchant3DSPage securepin=new Merchant3DSPage(driver);
		securepin.submitVisaVerification(securePin);
		
		MerchantTransactionFinalPage paymentstatus=new MerchantTransactionFinalPage(driver);
		paymentstatus.remindMeLater();
		
		IAPPostReportPage iapprp=new IAPPostReportPage(driver);
		iapprp.verifySuccess();			
	}	
}
