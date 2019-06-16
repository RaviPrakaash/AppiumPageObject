package wibmo.app.testScripts.SmokeTest;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import library.CSR;
import library.Generic;
import library.Log;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
 * The Class SMT_041 Web-SDK IAPV1 Amount Known True , Charge Later False. Registered User
 */
public class SMT_041 extends BaseTest {

	/** The tc. */
	public String TC=getTestScenario("SMT_041");	
	
	@BeforeMethod
	public void launchApplication()
	{	
		try{
		setWebDriverProperty();
	    driver = new ChromeDriver();
	    driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
	    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(0));
	    
	    driver.manage().window().maximize();
	    driver.get(Generic.getPropValues("MERCHANTAPPURL", BaseTest1.configPath));
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);	
		}
		catch(Exception e){Log.info("== Unable to launch Web SDK browser ==");}
	}	
	
	/**
	 * SMT_041
	 */
	@Test
	public void SMT_041()
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_041");
		String str[]=data.split(",");
		String amt=str[2];
		String mobileNumber=str[0];
		String loginPassword=str[1];		
		String cardName=str[3];
		String securePin=str[4];		
		
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
		merchant.addtestmerchantdetails(cardName);
		
		Merchant3DSPage ds=new Merchant3DSPage(driver);
		ds.submitVisaVerification(securePin);
		
		IAPPostReportPage icpr=new IAPPostReportPage(driver);
		String transactionid=icpr.fetchwibmoid();
		
		CSR.verifyIAPTXNDetails(transactionid);	
		
		icpr.checkSUF("authenticationSuccessful,chargeSuccessful");		
	}	
}
