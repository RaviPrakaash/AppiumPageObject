package wibmo.app.testScripts.SmokeTest;

import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import library.CSR;
import library.Generic;
import wibmo.app.pagerepo.MerchantCardSelectionPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantLoginPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_037.	IAP V1 – Web Overlay – Registered User
 */
public class SMT_037 extends BaseTest  
{		
	@BeforeMethod
	public void launchApplication() 
	{		
		//launchApp(Generic.getPropValues("MERCHANTPACKAGE",BaseTest1.configPath), Generic.getPropValues("MERCHANTACTIVITY",BaseTest1.configPath));
		clearApp();
	}
	
	/**
	 * smt 037.
	 */
	@Test
	public void SMT_037()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_037");
		String loginId=data.split(",")[0],cardDetails=data.split(",")[3],transactionStatusMsg;			
		
		//Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectWithoutApp();
		
		MerchantLoginPage mlp=new MerchantLoginPage(driver);
		mlp.verifyLoginPage();
		mlp.login(data);
		mlp.handleDVCTrusted(data,bankCode);
		
		MerchantCardSelectionPage mcsp=new MerchantCardSelectionPage(driver);
		mcsp.selectCard(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);
		
		transactionStatusMsg=mhp.verifyMerchantSuccess();		
		
		CSR.verifyIAPTXNDetails(transactionStatusMsg);
	}
}
