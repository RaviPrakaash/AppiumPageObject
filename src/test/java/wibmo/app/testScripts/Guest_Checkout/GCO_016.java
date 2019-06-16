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
 * The Class GCO_016 IAP for unregistered email ID number but registered mobile number
 */
public class GCO_016 extends BaseTest {
	
	
	@BeforeMethod
	public void launchApplication()
	{		 
	   launchAsWebDriver();
	}
	public String TC=getTestScenario("GCO_016");
	/**
	 * Gco 016.
	 */
	@Test
	public void GCO_016()			//Unregistered mail ID & Registered mobile number
	{
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_016");
		String str[]=data.split(",");
		String loginPassword=str[4];
		String mobileNumber=str[1];
		String cardType=str[5];
		String securePin=str[6];
		
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
		
		Merchant3DSPage m3p=new Merchant3DSPage(driver);
		
		if (!Generic.checkWalletITPDirect(cardType))
			m3p.submitVisaVerification(securePin);
		else
			m3p.enterCVV(securePin.split(":")[1]);		
		
		PostPaymentScreenPage postscreen=new PostPaymentScreenPage(driver);
		postscreen.verifypostsuccess();
	}
	
}
