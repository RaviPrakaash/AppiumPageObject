package wibmo.app.testScripts.IAP_Payment;


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

/**
 * The Class IAP_011 IAP experience [WebOverlay] without app installed, merchant user is wibmo user and using wibmo card and check balance
 */
public class IAP_011 extends BaseTest // ==== IAP experience without app installed, merchant user is wibmo user and using wibmo card and check balance ==== //
{	
	/** The tc. */
	public String TC=getTestScenario("IAP_011");	
	
	/**
	 * Iap 011.
	 */
	@Test
	public void IAP_011()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAP_011");
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++],securePin =values[i++],cardDetails=values[3];
		double amt=Double.parseDouble(values[i++]),	balanceBeforeTransaction=0.0;	
		
		/*if(!checkEnv("qa"))
			balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);	
		
		Generic.switchToMerchant(driver); 		
		clearApp();	*/	
		
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
		
		/*if(checkEnv("qa")) return;
		
		Generic.switchToApp(driver);		
		
		double balanceAfterTransaction=Generic.checkWalletBalance(driver,data);
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt*0.01,balanceAfterTransaction);	*/
	}

}
