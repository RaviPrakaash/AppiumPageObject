package wibmo.app.testScripts.IAP_Transaction;

import java.util.concurrent.TimeUnit;

import library.CSR;
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
 * The Class IAPTS_024 Web-SDK IAP transaction status in cancellation 3ds screen. Static Merchant.
 */
public class IAPTS_024 extends BaseTest {

	/** The tc. */
	public String TC=getTestScenario("IAPTS_024");	
	
	@BeforeMethod
	public void launchApplication() 
	{		   
	    launchAsWebDriver();
	}	
	
	/**
	 * Iapts 024.
	 */
	@Test
	public void IAPTS_024()		//"Web-SDK IAP transaction status in cancellation 3ds screen"
	{
		
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_024");
		String str[]=data.split(",");
		String loginPassword=str[4];
		String balanceMobileNumber=str[1];
		//String mobileNumber=str[5];
		String cardType=str[5];
		String securePin=str[6];
		double balanceBeforeTransaction=CSR.verifyBalance(balanceMobileNumber);
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcop=new MerchantCheckOutPage(driver); 
		mcop.merchantCheckout(data);
		
		ConnectingPhonePage cpp=new ConnectingPhonePage(driver);
		cpp.clickonskip();
		
		PayZappLoginPage login=new PayZappLoginPage(driver);
		login.addlogindetails(loginPassword);
		
		String otp=Generic.getOTP(balanceMobileNumber,bankCode,Generic.getPropValues("DVC", BaseTest1.configPath));
		
		DeviceVerificationPage dvc=new DeviceVerificationPage(driver);
		dvc.adddvcdetails(otp);
		
		TestMerchantPage merchant=new TestMerchantPage(driver);
		merchant.addtestmerchantdetails(cardType);
		
		Merchant3DSPage mds=new Merchant3DSPage(driver);	
		mds.cancelVisaVerification();		
		
		IAPPostReportPage icpr=new IAPPostReportPage(driver);
		
		String transactionid=icpr.fetchwibmoid();		
		
		double balanceAfterTransaction=CSR.verifyBalance(balanceMobileNumber);	
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction); 
		
		CSR.verifyStatusOnTxnId(transactionid,"043","3DS Failed ");
		
	}
	
}
