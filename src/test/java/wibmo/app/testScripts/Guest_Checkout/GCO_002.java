package wibmo.app.testScripts.Guest_Checkout;


import library.Generic;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wibmo.app.testScripts.BaseTest1;
import wibmo.webapp.pagerepo.*;

/**
 * The Class GCO_002 Unregistered user payment followed by registration
 */
public class GCO_002 extends BaseTest{

	/** The tc. */
	public String TC=getTestScenario("GCO_002");	
	
	@BeforeMethod
	public void launchApplication()
	{		   
	   launchAsWebDriver();
	}
	
	/**
	 * Gco 002.
	 */
	@Test
	public void GCO_002()		//unregistered user followed by registration
	{
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_002"); data=data.replace(data.split(",")[1], Generic.generateMobileNumber());
		String str[]=data.split(",");
		String securePin=str[7];
		String mobileNumber=str[1];
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcop=new MerchantCheckOutPage(driver);
		mcop.merchantCheckout(data);
		
		MerchantPaymentPage payment=new MerchantPaymentPage(driver);
		payment.enterCardDetails(data);
		
		Merchant3DSPage mdsp=new Merchant3DSPage(driver);
		mdsp.submitVisaVerification(securePin);
		
		MerchantTransactionFinalPage paymentstatus=new MerchantTransactionFinalPage(driver);
		paymentstatus.forFasterCheckout();
		
		MerchantRegistrationPage register=new MerchantRegistrationPage(driver);
		register.checkprepopulated(data);
		register.addRegistrationDetails(data);	
		
		String otp=Generic.getOTP(mobileNumber,bankCode, Generic.getPropValues("MVC", BaseTest1.configPath));
		
		MerchantMVCPage mvc= new MerchantMVCPage(driver);	
		mvc.addMVCDetail(otp);
		mvc.clickoncontinue();		
		
		IAPPostReportPage iapprp=new IAPPostReportPage(driver);
		iapprp.verifySuccess();
	}	
	
}
