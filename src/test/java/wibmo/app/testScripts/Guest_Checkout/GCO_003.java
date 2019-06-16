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
 * The Class GCO_003 Unregistered User payment followed by TimeOut
 */
public class GCO_003 extends BaseTest {
	
	/** The tc. */
	public String TC=getTestScenario("GCO_003");	
	
	@BeforeMethod
	public void launchApplication()
	{			   
		launchAsWebDriver();
	}
	
	/**
	 * Gco 003.
	 */
	@Test
	public void GCO_003()		//Unregistered User followed by TimeOut
	{
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_003");
		String str[]=data.split(",");
		String securePin=str[7];
		String mobileNumber=str[10];
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcop=new MerchantCheckOutPage(driver);
		mcop.merchantCheckout(data);
		
		MerchantPaymentPage mpp=new MerchantPaymentPage(driver);
		mpp.enterCardDetails(data);
		
		Merchant3DSPage mdsp=new Merchant3DSPage(driver);
		mdsp.submitVisaVerification(securePin);
		
		MerchantTransactionFinalPage mtfp=new MerchantTransactionFinalPage(driver);
		mtfp.forFasterCheckout();
		
		MerchantRegistrationPage register=new MerchantRegistrationPage(driver);
		register.addRegistrationDetails(data);
		
		String otp=Generic.getOTP(mobileNumber,bankCode, Generic.getPropValues("MVC", BaseTest1.configPath));
		
		MerchantMVCPage mvc= new MerchantMVCPage(driver);
		mvc.addMVCDetail(otp);
		mvc.timeout(5);
		
		IAPPostReportPage iapprp=new IAPPostReportPage(driver);
		iapprp.verifySuccess();
		
	}	
}
