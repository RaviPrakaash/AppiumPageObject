package wibmo.app.testScripts.IAP_Payment;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import library.ExtentManager;
import library.ExtentTestManager;
import library.Generic;
import library.Log;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class SampleIAP not to be included in the test suite. Mainly used to troubleshoot and verify code blocks.
 */
public class CodeChecker //extends BaseTest
{	
	
	//@BeforeMethod
	//public void launchApplication()
	{	
		//launchApp(Generic.getPropValues("MERCHANTPACKAGE",BaseTest1.configPath), Generic.getPropValues("MERCHANTACTIVITY",BaseTest1.configPath));
	}  

	/**
	 * Sample IAP.
	 */
	@Test
	public void SampleIAP()
	{
		//Reporter.log(getTestScenario());
		
		//System.out.println(a()); 
		
		Math.pow(2, 3);
		 
		if(true)return;
		
		//@BeforeSuite
		ExtentManager.generateFilePath();
		Generic.wait(3);
		
		//@BeforeMethod
		ExtentTestManager.startTest("TC_003","SampleTest");
		
		//@Test
		Log.info("== Sample Info ==");
		
		//@AfterMethod
		ExtentTestManager.getTest().log(LogStatus.PASS,"========== Passed: "+"TC_003"+" ==========");
		
		//Close Extent Test
		System.out.println("== Closing Test ==");
		ExtentManager.getReporter().endTest(ExtentTestManager.getTest());		
		ExtentManager.getReporter().flush();
		
		
		
		
		
	}

	public void a()
	{
		
		
	}
}
