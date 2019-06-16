package wibmo.app.testScripts.IAPV2;

import java.util.concurrent.TimeUnit;

import library.CSR;
import library.Generic;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wibmo.app.testScripts.BaseTest1;
import wibmo.webapp.pagerepo.ConnectingPhonePage;
import wibmo.webapp.pagerepo.DeviceVerificationPage;
import wibmo.webapp.pagerepo.IAPPostReportPage;
import wibmo.webapp.pagerepo.Merchant3DSPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.OTPFetchPage;
import wibmo.webapp.pagerepo.PayZappLoginPage;
import wibmo.webapp.pagerepo.TestMerchantPage;

/**
 * The Class IAPV2AS_008 Web-SDK IAPV1 Amount Known True , Charge Later False. Registered User . CVV N
 */
public class IAPV2AS_008 extends BaseTest
{

	/** The tc. */
	public String TC=getTestScenario("IAPV2AS_008");	
	
	@BeforeMethod
	public void launchApplication()
	{		   
	   launchAsWebDriver();		
	}	
	
	/**
	 * IAPV2AS_008
	 */
	@Test
	public void IAPV2AS_008()
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("IAPV2AS_008");
		String str[]=data.split(",");
		String amt=str[2];
		String mobileNumber=str[0];
		String loginPassword=str[1];		
		String cardDetails=str[3];
		String securePin=str[4];
		String cvvStatus=Generic.parseCVVStatus(data);
		
		CSR.setCVVStatus(cardDetails,cvvStatus);
		
		MerchantMainPage main=new MerchantMainPage(driver);
		main.basicInfo(data);		
		
		MerchantCheckOutPage checkout=new MerchantCheckOutPage(driver); 
		checkout.merchantCheckout(amt,"IAPV1");		
		
		ConnectingPhonePage cpp=new ConnectingPhonePage(driver);
		cpp.clickonskip();
		
		PayZappLoginPage login=new PayZappLoginPage(driver);
		login.addlogindetails(loginPassword);
		
		String otp=Generic.getOTP(mobileNumber,bankCode,Generic.getPropValues("DVC", BaseTest1.configPath));
		
		DeviceVerificationPage dvc=new DeviceVerificationPage(driver);
		dvc.adddvcdetails(otp);
		
		TestMerchantPage merchant=new TestMerchantPage(driver);
		merchant.addtestmerchantdetails(cardDetails);
		
		Merchant3DSPage ds=new Merchant3DSPage(driver);
		ds.verifyCVVOccurence(ds.submitVisaVerification(securePin), false); // CVV N means false
		
		IAPPostReportPage icpr=new IAPPostReportPage(driver);
		String transactionid=icpr.fetchwibmoid();		
		
		icpr.checkSUF("\"pgStatusCode\" : \"50020\",\"authenticationSuccessful\" : true,\"chargeSuccessful\" : true");		
		
		CSR.verifyIAPTXNDetails(transactionid);	
	}	
}
