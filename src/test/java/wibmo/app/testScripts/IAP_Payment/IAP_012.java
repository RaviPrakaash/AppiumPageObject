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
import wibmo.app.pagerepo.MerchantCardSelectionPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantLoginPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class IAP_012 IAP experience without app installed, merchant user is wibmo user and using new (add) card
 */
public class IAP_012 extends BaseTest // ==== IAP experience without app installed, merchant user is wibmo user and using new (add) card ==== //
{
	
	/* (non-Javadoc)
	 * @see library.BaseClass#launchApplication()
	 */
	@BeforeMethod
	public void launchApplication()
	{
		//launchApp(Generic.getPropValues("MERCHANTPACKAGE",BaseTest1.configPath), Generic.getPropValues("MERCHANTACTIVITY",BaseTest1.configPath));
		clearApp();
	}
	
	/** The tc. */
	public String TC=getTestScenario("IAP_012");	
	
	/**
	 * Iap 012.
	 */
	@Test
	public void IAP_012()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_012");
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++],securePin =values[i++],cardDetails=values[3],cardDetails3DS=values[4];	
		
		//Generic.unInstallApp(driver,packageName);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectWithoutApp();
		
		MerchantLoginPage mlp=new MerchantLoginPage(driver);
		mlp.verifyLoginPage();
		mlp.login(data);
		mlp.handleDVCTrusted(data,bankCode);
		
		MerchantCardSelectionPage mcsp=new MerchantCardSelectionPage(driver);
		mcsp.addCard(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails3DS);
		
		mhp.verifyMerchantSuccess();		
	}

}
