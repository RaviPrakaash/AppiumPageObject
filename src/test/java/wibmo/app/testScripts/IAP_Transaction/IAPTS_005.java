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
 * The Class IAPTS_005 IAP transaction cancellation in 3ds screen . Static Merchant
 */
public class IAPTS_005 extends BaseTest // ==== IAP transaction cancellation in 3ds screen ==== //
{
	
	/** The tc. */
	String TC=getTestScenario("IAPTS_005");

	/**
	 * Iapts 005.
	 */
	@Test
	public void IAPTS_005()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_005");
		String[] values = data.split(",");
		String cardDetails=values[3];
		
		Generic.userNotLoggedInToApp(driver, data);

		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp = new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName);
		mhp.enterSecurePin(data);
		Generic.merchantVerifiedLogin(driver,data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);		

		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.cancel3DS();
		
		//CSR.verify3DSfail(loginId); //3ds failed
		CSR.verifyStatusOnTxnDesc("3DS Failed");
	}
}
