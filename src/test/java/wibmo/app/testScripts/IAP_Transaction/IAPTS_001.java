package wibmo.app.testScripts.IAP_Transaction;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import library.CSR;
import library.Generic;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantLoginPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WibmoSDKPage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class IAPTS_001 IAP transaction cancellation in login and Verify Balance for Static Merchant
 */
public class IAPTS_001 extends BaseTest // ==== IAP transaction cancellation in login and Verify Balance ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_001");	
	
	/**
	 * Iapts 001.
	 */
	@Test
	public void IAPTS_001() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_001");	
		
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);
		Generic.logout(driver);
		Generic.wait(4); // Wait for pageload
		
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName);	
		mhp.cancelSecurePin();		 
		
		Generic.switchToApp(driver);		
		
		double balanceAfterTransaction=Generic.checkWalletBalance(driver,data);
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);	
		
		//CSR.verifyAbortStatus(data);	
		CSR.verifyStatusOnTxnDesc("aborted");
	}	
}
