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
import wibmo.app.pagerepo.MerchantDetailsAdditionalPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantMVCPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.MerchantRegisterPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class IAP_009 Perform registration through merchant app without app installed
 */
public class IAP_009 extends BaseTest // ==== Perform registration through merchant app without app installed ==== //
{
	@BeforeMethod
	public void launchApplication()
	{		
		//launchApp(Generic.getPropValues("MERCHANTPACKAGE",BaseTest1.configPath), Generic.getPropValues("MERCHANTACTIVITY",BaseTest1.configPath));
		clearApp();  
	}
	
	/** The tc. */
	public String TC=getTestScenario("IAP_009");	
	
	/**
	 * Iap 009.
	 */
	@Test
	public void IAP_009()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_009");				
		
		//Generic.unInstallApp(driver,packageName);
		
		//Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp = new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectWithoutApp();
		
		MerchantRegisterPage mrp = new MerchantRegisterPage(driver);
		mrp.enterValues(data);
		
		MerchantPayment3DSPage mp3p = new MerchantPayment3DSPage(driver);
		mp3p.submitPayment(data.split(",")[1]);
		
		mhp.verifyGuestPayment();
		
		MerchantDetailsAdditionalPage mdap = new MerchantDetailsAdditionalPage(driver);
		mdap.enterDetails(data);
		
		MerchantMVCPage mmp = new MerchantMVCPage(driver);
		mmp.enterMVC(data, bankCode);
		mmp.verifyRegister();
		
		mhp.verifyMerchantSuccess();
	}
}
