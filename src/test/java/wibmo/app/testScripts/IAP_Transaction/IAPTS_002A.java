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

import wibmo.app.pagerepo.MerchantCardSelectionPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantLoginPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.MerchantSettingsPage;
import wibmo.app.pagerepo.WibmoSDKPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTS_002A Verify IAP transaction report for a failed transaction, Static Merchant
 *
 */
public class IAPTS_002A extends BaseTest
{	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_002A");

	/**
	 * Iapts 002a.
	 */
	@Test
	public void IAPTS_002A()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_002A");

		String[] values = data.split(",");
		String cardDetails=values[3];
		
		Generic.userNotLoggedInToApp(driver, data);

		Generic.switchToMerchant(driver);

		MerchantHomePage mhp = new MerchantHomePage(driver);							
		mhp.executePayment(data);
		mhp.selectApp(programName);
		mhp.enterSecurePin(data);		
		Generic.merchantVerifiedLogin(driver, data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);

		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.submitPayment(cardDetails.split(":")[1]); 
		mp3p.verifyFailureMsg();

		//CSR.verify3DSIntermediate(data);
		CSR.verifyStatusOnTxnDesc("Intermediate");
	}
}
