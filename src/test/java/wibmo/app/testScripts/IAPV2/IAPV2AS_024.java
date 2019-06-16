package wibmo.app.testScripts.IAPV2;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import library.Generic;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.MerchantSettingsPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class IAPV2AS_024 : 2 merchant of 1 pgm to use 2 different PG
 *
 */
public class IAPV2AS_024 extends BaseTest  
{		
	public String TC=getTestScenario("IAPV2AS_024");	
	
	
	@Test
	public void IAPV2AS_024() // V2-IAP-weboverlay with 3ds & CVV U
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPV2AS_024");
		String values[]=data.split(",");
		String loginId=values[0],securePin=values[1],cardDetails=values[3];		
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver, data);
				
		Generic.switchToMerchant(driver);
		
		// ==== Perform IAP Transaction : Merchant 1==== //
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);		
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);
		
		mhp.verifyMerchantSuccess();			
		
		// ==== Set configuration for Merchant 2 ==== //
		
		mhp.gotoSettings();
		
		MerchantSettingsPage msp=new MerchantSettingsPage(driver);
		msp.setMerchant( Generic.getPropValues("SSMERCHANTID",configPath), Generic.getPropValues("SSMERCHANTAPPID", configPath));
		driver.navigate().back();
		
		setPGMerchant();
		
		// ==== Perform IAP Transaction : Merchant 2 ==== //
		
		mhp.clickWibmoButton();
		mhp.selectApp(programName);
		wsp.approvePayment(cardDetails);
		mp3p.executeMerchantPayment(cardDetails);
		mhp.verifyMerchantSuccess();		
	}
	
	@AfterMethod
	public void setNormalMerchant()
	{
		try
		{
			wibmo.app.testScripts.IAP_Transaction.BaseTest.setMerchantShellNormal();
			
			MerchantHomePage mhp=new MerchantHomePage(driver);
			mhp.gotoSettings();
		
			MerchantSettingsPage msp=new MerchantSettingsPage(driver);
			msp.enterStaticOrDynamicId("static");
		}
		catch(Exception e)
		{
			System.err.println("Merchant not set to normal\n");e.printStackTrace();
		}
		catch(AssertionError ae)
		{
			System.err.println("Merchant could not be set to normal");ae.printStackTrace();
		}		
	}
}
