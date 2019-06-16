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
 * The Class GCO_004  Unregistered User payment followed by Registration Cancellation
 */
public class GCO_004 extends BaseTest {

	/** The tc. */
	public String TC=getTestScenario("GCO_004");	
	
	@BeforeMethod
	public void launchApplication()
	{		   
		launchAsWebDriver();
	}
	
	/**
	 * Gco 004.
	 */
	@Test
	public void GCO_004()		//Unregistered User followed by Registration Cancellation
	{
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_004");
		String str[]=data.split(",");
		String securePin=str[7];
		String mobileNumber=str[1];
		
		MerchantMainPage main=new MerchantMainPage(driver);
		main.basicInfoSubmit(data);
		
		MerchantCheckOutPage checkout=new MerchantCheckOutPage(driver);
		checkout.merchantCheckout(data);
		
		MerchantPaymentPage payment=new MerchantPaymentPage(driver);
		payment.enterCardDetails(data);
		
		Merchant3DSPage securepin=new Merchant3DSPage(driver);
		securepin.submitVisaVerification(securePin);
		
		MerchantTransactionFinalPage paymentstatus=new MerchantTransactionFinalPage(driver);
		paymentstatus.forFasterCheckout();
		
		MerchantRegistrationPage register=new MerchantRegistrationPage(driver);
		register.addRegistrationDetails(data);
		
		//OTPFetchPage fetch=new OTPFetchPage(driver1);
		//String otp=fetch.fetchotp(str[1]);
		String otp=Generic.getOTP(mobileNumber,bankCode, Generic.getPropValues("MVC", BaseTest1.configPath));
		MerchantMVCPage mvc= new MerchantMVCPage(driver);
		mvc.addMVCDetail(otp);
		mvc.clickoncancel();
		
		IAPPostReportPage iapprp=new IAPPostReportPage(driver);
		iapprp.verifySuccess();		
	}
	
}
