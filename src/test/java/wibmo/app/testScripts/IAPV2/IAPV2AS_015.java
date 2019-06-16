package wibmo.app.testScripts.IAPV2;

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

/**
 * The Class IAPV2AS_015.	IAP V1 – Web Overlay – Registered User
 */
public class IAPV2AS_015 extends BaseTest  
{		
	@BeforeMethod
	public void launchApplication() 
	{		
		//launchApp(Generic.getPropValues("MERCHANTPACKAGE",BaseTest1.configPath), Generic.getPropValues("MERCHANTACTIVITY",BaseTest1.configPath));
		clearApp();
	}
	
	public String TC=getTestScenario("IAPV2AS_015");	
	
	/**
	 * iapv2as 015.
	 */
	@Test
	public void IAPV2AS_015() // V1-IAP-weboverlay with 3ds & CVV N
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPV2AS_015");
		String loginId=data.split(",")[0],cardDetails=data.split(",")[3],transactionStatusMsg;	
		String cvvStatus=Generic.parseCVVStatus(data);
		
		
		
		// ==== Perform IAP Transaction ==== //
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectWithoutApp(); 
		
		MerchantLoginPage mlp=new MerchantLoginPage(driver);
		mlp.verifyLoginPage();
		mlp.login(data);
		mlp.handleDVCTrusted(data,bankCode);
		
		// ==== Set CVV Status ==== //
		CSR.setCVVStatus(cardDetails,cvvStatus);
		//========================= //
		
		MerchantCardSelectionPage mcsp=new MerchantCardSelectionPage(driver);
		mcsp.selectCard(cardDetails);		
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		if(!Generic.checkWalletITPDirect(cardDetails))
		{
			mp3p.verifyMerchantPayment3DS();
			mp3p.submitPayment(cardDetails.split(":")[1]);
		}
		mp3p.verifyCVVOccurence(mp3p.enterCvv(cardDetails.split(":")[2]), (cvvStatus.contains("Yes") || cvvStatus.contains("Unknown")));		
		
		transactionStatusMsg=mhp.verifyMerchantSuccess();	
		
		mhp.performDataPickup("SUCCESS,\"pgStatusCode\" : \"50020\"");
		
		CSR.verifyIAPTXNDetails(transactionStatusMsg);
	}
}
