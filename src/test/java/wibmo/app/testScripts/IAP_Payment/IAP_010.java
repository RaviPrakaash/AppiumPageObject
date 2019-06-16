package wibmo.app.testScripts.IAP_Payment;

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
import library.Generic;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.MerchantCardSelectionPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantLoginPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class IAP_010 IAP experience without app installed, merchant user is wibmo user and using any(external) card
 */
public class IAP_010 extends BaseTest // ==== IAP experience without app installed, merchant user is wibmo user and using any(external) card ==== //
{	
	@BeforeMethod
	public void launchApplication()
	{
		//launchApp(Generic.getPropValues("MERCHANTPACKAGE",BaseTest1.configPath), Generic.getPropValues("MERCHANTACTIVITY",BaseTest1.configPath));
		clearApp(); 
	}
	
	/** The tc. */
	public String TC=getTestScenario("IAP_010");	
	
	/**
	 * Iap 010.
	 */
	@Test
	public void IAP_010()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_010");		
		
		String cardDetails=data.split(",")[3];
		
		//Generic.unInstallApp(driver, packageName);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectWithoutApp();
		
		MerchantLoginPage mlp=new MerchantLoginPage(driver);
		mlp.verifyLoginPage();
		mlp.login(data);
		mlp.handleDVCTrusted(data, bankCode);
		
		MerchantCardSelectionPage mcsp=new MerchantCardSelectionPage(driver);
		mcsp.selectCard(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);
		
		mhp.verifyMerchantSuccess();		
	}
}
