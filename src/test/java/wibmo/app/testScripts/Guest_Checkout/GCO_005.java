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
 * The Class GCO_005 Unregistered user payment followed by registering with registered mobile number
 */
public class GCO_005 extends BaseTest{
	
	/** The tc. */
	public String TC=getTestScenario("GCO_005");	
	
	@BeforeMethod
	public void launchApplication()
	{		
		launchAsWebDriver(); 
	}
	
	/**
	 * Gco 005.
	 */
	@Test
	public void GCO_005()		//Unregistered user/unregistered mobile number followed by registering with registered mobile number 
	{

		Reporter.log(getTestScenario());
		String data=getTestData("GCO_005");
		String str[]=data.split(",");
		String securePin=str[7];
		String mobileNumber=str[1],registeredNumber=str[10];	
		
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
		register.changetoregisterednumber(registeredNumber);
		register.addRegistrationDetails(data);
		
	//	OTPFetchPage fetch=new OTPFetchPage(driver1);
		//String otp=fetch.fetchotp(str[10]);
		String otp=Generic.getOTP(mobileNumber,bankCode, Generic.getPropValues("MVC", BaseTest1.configPath));
	
		MerchantMVCPage mvc= new MerchantMVCPage(driver);
		mvc.addMVCDetail(otp);
		mvc.clickoncontinue();
		
		MerchantRegistrationFinalPage alregistered=new MerchantRegistrationFinalPage(driver);
		alregistered.checkregistraionstatus();
		
		IAPPostReportPage iapprp=new IAPPostReportPage(driver);
		iapprp.verifySuccess();	
	}	
	
}
