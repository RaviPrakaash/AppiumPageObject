package wibmo.app.testScripts.IAPV2;

import java.util.ArrayList;
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

// TODO: Auto-generated Javadoc
/**
 * The Class IAPV2AS_009 Web-SDK V2-IAP with 3ds & CVV U
 */
public class IAPV2AS_009 extends BaseTest
{

	/** The tc. */
	public String TC=getTestScenario("IAPV2AS_009");	
	
	@BeforeMethod
	public void launchApplication()
	{		   
	   launchAsWebDriver();		
	}	
	
	/**
	 * IAPV2AS_009
	 */
	@Test
	public void IAPV2AS_009()
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("IAPV2AS_009");
		String str[]=data.split(",");		
		String mobileNumber=str[0];
		String loginPassword=str[1];
		String amt=str[2];
		String cardDetails=str[3];
		String securePin=str[4];
		boolean chargeStatus=str[7].toLowerCase().contains("true");
		String cvvStatus=Generic.parseCVVStatus(data);
		
		
		CSR.setCVVStatus(cardDetails,cvvStatus);
		
		MerchantMainPage main=new MerchantMainPage(driver);
		main.basicInfo(data);		
		
		MerchantCheckOutPage checkout=new MerchantCheckOutPage(driver); 
		checkout.merchantCheckout(amt,"IAPV2");		
		
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
		ds.verifyCVVOccurence(ds.submitVisaVerification(securePin), true); // CVV U means true
		
		IAPPostReportPage icpr=new IAPPostReportPage(driver);
		String transactionid=icpr.fetchwibmoid();		
		
		icpr.checkSUF(false,"\"pgStatusCode\" : null,\"authenticationSuccessful\" : true,\"chargeSuccessful\" : false");
		if(chargeStatus)
		icpr.checkSUF(chargeStatus,"\"pgStatusCode\" : \"50020\",\"authenticationSuccessful\" : true,\"chargeSuccessful\" : true");		
		
		CSR.verifyIAPTXNDetails(transactionid);	
	}	
}
