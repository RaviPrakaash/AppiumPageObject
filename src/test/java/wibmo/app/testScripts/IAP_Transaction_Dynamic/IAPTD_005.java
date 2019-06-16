package wibmo.app.testScripts.IAP_Transaction_Dynamic;

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
import wibmo.app.pagerepo.WibmoSDKPage;
import wibmo.app.testScripts.BaseTest1;
import wibmo.app.testScripts.IAP_Transaction.IAPTS_005;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTD_005. App-SDK IAP transaction status in cancellation 3ds screen. Dynamic Merchant.
 */
public class IAPTD_005 extends IAPTS_005
{		
	/** The tc. */
	String TC=getTestScenario("IAPTS_005")+" Dynamic Merchant";
}
