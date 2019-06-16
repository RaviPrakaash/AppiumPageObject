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
import junit.framework.Assert;
import library.Generic;
import wibmo.app.pagerepo.MerchantCardSelectionPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantLoginPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class IAP_015  ITP should not happen when app not installed 
 */
public class IAP_015 extends BaseTest // ITP should not happen when app uninstalled ==== //
{	
	/** The tc. */
	public String TC=getTestScenario("IAP_015");	
	
	/**
	 * Iap 015.
	 */
	@Test
	public void IAP_015()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_015");
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++],securePin =values[i++],cardDetails=values[3];	
		String pin=cardDetails.split(":")[1],cvv=cardDetails.split(":")[2];
		
		//Generic.installProgram(driver,packageName);
		
		/*driver.quit();			
		driver=Generic.setAppDriver(packageName,appActivity);
		
		if(driver==null) Assert.fail("Failed to launch program app");*/	
		
		//Generic.preconditionITP(driver, data);
		//Generic.logout(driver);
		
		Generic.switchToMerchant(driver); 
		
		//Generic.unInstallApp(driver, packageName);
		clearApp();
		
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
		//mp3p.executeMerchantPayment(cardDetails); 
		mp3p.verifyMerchantPayment3DS();
		mhp.verifyNonITP();
		mp3p.submitPayment(pin);
		mp3p.enterCvv(cvv);
		
		mhp.verifyMerchantSuccess();
				
	}

}
