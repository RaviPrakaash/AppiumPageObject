package wibmo.app.testScripts.Guest_Checkout;

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
import wibmo.app.pagerepo.MerchantDetailsAdditionalPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantMVCPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.MerchantRegisterPage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class GCO_019 IAP for unregistered user from merchant app from a phone without app installed. Complete registration
 *
 */
public class GCO_019 extends BaseTest
{	
	@BeforeMethod
	public void launchApplication()
	{		
		//launchApp(Generic.getPropValues("MERCHANTPACKAGE",BaseTest1.configPath), Generic.getPropValues("MERCHANTACTIVITY",BaseTest1.configPath));
		clearApp();  
	}  
	
	public String TC=getTestScenario("GCO_019");
	
	/**
	 * Gco 019.
	 */
	@Test
	public void GCO_019()
	{			
		
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_019"); data=data.replaceAll(data.split(",")[0], Generic.generateMobileNumber());
		String transactionStatusMsg="";
		String cardDetails=data.split(",")[4];
		//Generic.unInstallApp(driver, packageName);	// uninstall is simulated by clearing app data 	
		
		MerchantHomePage mhp = new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectWithoutApp();
		
		MerchantRegisterPage mrp = new MerchantRegisterPage(driver);
		mrp.enterValues(data);
		
		MerchantPayment3DSPage mp3p = new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);
		//mp3p.submitPayment(data.split(",")[1]);
		mhp.verifyGuestPayment();				
		
		MerchantDetailsAdditionalPage mdap = new MerchantDetailsAdditionalPage(driver);
		mdap.enterDetails(data);
		
		MerchantMVCPage mmp = new MerchantMVCPage(driver);
		mmp.enterMVC(data,bankCode);
		mmp.verifyRegister();	
		
		transactionStatusMsg=mhp.verifyMerchantSuccess();	
		mhp.performDataPickup();
		CSR.verifyIAPTransactionDataPickupSuccess(driver, transactionStatusMsg);		
	}

}
