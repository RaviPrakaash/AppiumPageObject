package wibmo.app.testScripts.Guest_Checkout;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import library.CSR;
import library.Generic;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.MerchantRegisterPage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class GCO_018 IAP for unregistered user from merchant app for a phone without app installed. Skip registration.
 *
 */
public class GCO_018 extends BaseTest
{	
	@BeforeMethod
	public void launchApplication() 
	{		
		//launchApp(Generic.getPropValues("MERCHANTPACKAGE",BaseTest1.configPath), Generic.getPropValues("MERCHANTACTIVITY",BaseTest1.configPath));
		clearApp(); 
	}  
	
	/** The tc. */
	public String TC=getTestScenario("GCO_018");
	
	/**
	 * Gco 018.
	 */
	@Test
	public void GCO_018()
	{				
		
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_018");
		String cardDetails=data.split(",")[4];
		
		//Generic.unInstallApp(driver, packageName);
		
		MerchantHomePage mhp = new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectWithoutApp();
		
		MerchantRegisterPage mrp = new MerchantRegisterPage(driver);
		mrp.enterValues(data);
		
		MerchantPayment3DSPage mp3p = new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);
		//mp3p.submitPayment(data.split(",")[1]);		
		
		mhp.verifyRemindMeLink();
		mhp.verifyMerchantSuccess();		
		
	}
}
