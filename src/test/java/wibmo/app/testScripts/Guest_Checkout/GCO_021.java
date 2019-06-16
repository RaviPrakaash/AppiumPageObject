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
import wibmo.app.pagerepo.MerchantLoginPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.MerchantRegisterPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class GCO_021 Guest checkout IAP and canceling on the login screen.
 *
 */
public class GCO_021 extends BaseTest
{	
	
	@BeforeMethod
	public void launchApplication()
	{		
		//launchApp(Generic.getPropValues("MERCHANTPACKAGE",BaseTest1.configPath), Generic.getPropValues("MERCHANTACTIVITY",BaseTest1.configPath));
		clearApp(); 
	}  
	
	/** The tc. */
	String TC=getTestScenario("GCO_021");

	/**
	 * Gco 021.
	 */
	@Test
	public void GCO_021()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_021");

		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++],securePin =values[i++];

		//Generic.unInstallApp(driver, packageName);

		MerchantHomePage mhp = new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectWithoutApp();

		MerchantLoginPage mlp=new MerchantLoginPage(driver);
		mlp.verifyLoginPage();
		mlp.cancelLogin();
		
		mhp.verifyFailureMessage();
	}
}
