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
 * The Class GCO_015 IAP for unregistered mobile number but registered mail ID
 */
public class GCO_015 extends BaseTest {
	
	/** The tc. */
	public String TC=getTestScenario("GCO_015");
	
	
	@BeforeMethod
	public void launchApplication()
	{				   
	   launchAsWebDriver();
	}
	
	/**
	 * Gco 015.
	 */
	@Test
	public void GCO_015()			//Unregistered mobile number & Registered Mail ID
	{
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_015");
		String str[]=data.split(",");
		String loginPassword=str[4];
		String mobileNumber=str[5];
		String cardType=str[6];
		String securePin=str[7];
		
		MerchantMainPage main=new MerchantMainPage(driver);
		main.basicInfoSubmit(data);
		
		MerchantCheckOutPage checkout=new MerchantCheckOutPage(driver);
		checkout.merchantCheckout(data);
		
		ConnectingPhonePage cpp=new ConnectingPhonePage(driver);
		cpp.clickonskip();
		
		PayZappLoginPage login=new PayZappLoginPage(driver);
		login.addlogindetails(loginPassword);		
		
		String otp=Generic.getOTP(mobileNumber,bankCode,Generic.getPropValues("DVC", BaseTest1.configPath));
		
		DeviceVerificationPage dvc=new DeviceVerificationPage(driver);
		dvc.adddvcdetails(otp);
		
		TestMerchantPage merchant=new TestMerchantPage(driver);
		merchant.addtestmerchantdetails(cardType);	
		
		Merchant3DSPage securepin=new Merchant3DSPage(driver);
		if(!Generic.checkWalletITPDirect(cardType))
			securepin.submitVisaVerification(securePin);
		else
			securepin.enterCVV(securePin.split(":")[1]);
		
		PostPaymentScreenPage postscreen=new PostPaymentScreenPage(driver);
		postscreen.verifypostsuccess();			
	}
	
	
}
