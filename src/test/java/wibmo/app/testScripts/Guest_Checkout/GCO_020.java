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
import wibmo.app.pagerepo.MerchantCardSelectionPage;
import wibmo.app.pagerepo.MerchantDetailsAdditionalPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantLoginPage;
import wibmo.app.pagerepo.MerchantMVCPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.MerchantRegisterPage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class GCO_020 Guest checkout IAP through Login and Verify Data Pickup
 *
 */
public class GCO_020 extends BaseTest
{		
	@BeforeMethod
	public void launchApplication()
	{		
		//launchApp(Generic.getPropValues("MERCHANTPACKAGE",BaseTest1.configPath), Generic.getPropValues("MERCHANTACTIVITY",BaseTest1.configPath));
		clearApp();
	}  
	
	/** The tc. */
	public String TC=getTestScenario("GCO_020");
	
	/**
	 * Gco 020.
	 */
	@Test
	public void GCO_020()
	{				
		
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_020");
		String transactionStatusMsg="",cardDetails=data.split(",")[3];
			
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
			
		transactionStatusMsg=mhp.verifyMerchantSuccess();	
		mhp.performDataPickup();
		CSR.verifyIAPTransactionDataPickupSuccess(driver, transactionStatusMsg);		
	}
}
