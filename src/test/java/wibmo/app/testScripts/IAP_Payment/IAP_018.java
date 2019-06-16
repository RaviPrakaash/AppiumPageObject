package wibmo.app.testScripts.IAP_Payment;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import junit.framework.Assert;
import library.Generic;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.MerchantCardSelectionPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantLoginPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class IAP_018 IAP experience without app installed, merchant user is wibmo user and using ITP card and check balance
 */
public class IAP_018 extends BaseTest // ==== IAP experience without app installed, merchant user is wibmo user and using ITP card and check balance ==== //
{
	/** The tc. */
	public String TC=getTestScenario("IAP_018");	
	
	/**
	 * Iap 018.
	 */
	@Test
	public void IAP_018()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_018");
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++],securePin =values[i++],cardDetails=values[3];
		double amt=Double.parseDouble(values[i++]); 
		String pin=cardDetails.split(":")[1],cvv=cardDetails.split(":")[2];
		
		/*Generic.preconditionITP(driver,data);
		//Generic.logout(driver);
		
		driver.navigate().back();
		SettingsPage sp=new SettingsPage(driver);
		sp.gotoAddSendPage();
		
		AddSendPage asp=new AddSendPage(driver);
		double balanceBeforeTransaction=asp.verifyBalance();
		
		Generic.switchToMerchant(driver);		
		//Generic.unInstallApp(driver,packageName);	
		clearApp();*/
		
		Generic.switchToMerchant(driver);
		
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
		
		//if(checkEnv("qa")) return;
		
	/*	Generic.switchToApp(driver);
		
		double balanceAfterTransaction=Generic.checkWalletBalance(driver,data);
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt*0.01,balanceAfterTransaction);	*/
	}	
}
