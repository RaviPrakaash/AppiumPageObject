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
 * The Class IAPV2AS_007.	Taxi-IAP-weboverlay with 3ds & CVV Y
 */
public class IAPV2AS_007 extends BaseTest  
{		
	@BeforeMethod
	public void launchApplication() 
	{		
		//launchApp(Generic.getPropValues("MERCHANTPACKAGE",BaseTest1.configPath), Generic.getPropValues("MERCHANTACTIVITY",BaseTest1.configPath));
		clearApp();
	}
	
	public String TC=getTestScenario("IAPV2AS_007");	
	
	/**
	 * iapv2as 007.
	 */
	@Test
	public void IAPV2AS_007() // V2-IAP-weboverlay with 3ds & CVV U
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPV2AS_007");
		String loginId=data.split(",")[0],amt=data.split(",")[2],cardDetails=data.split(",")[3],transactionStatusMsg,chargeStatus=data.split(",")[8],chargeAmt=data.split(",")[9];		
		String cvvStatus=Generic.parseCVVStatus(data);
		
		// ==== Set CVV Status ==== //
		CSR.setCVVStatus(cardDetails,cvvStatus);
		
		// ==== Perform IAP Transaction ==== //
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
		mp3p.verifyMerchantPayment3DS();
		mp3p.submitPayment(cardDetails.split(":")[1]);
		mp3p.verifyCVV();
		mp3p.enterCvv(cardDetails.split(":")[2]);
		
		transactionStatusMsg=mhp.verifyMerchantSuccess();	
		
		// ==== Verify Transaction Success ==== //
		transactionStatusMsg=mhp.verifyMerchantSuccess();				
				
		// ==== Charge On Status Check False ==== // 
		mhp.clickCheckStatusDataPickup("chargeStatusFalse");
		mhp.enterChargeAmount(amt);
		mhp.checkSUF("\"pgStatusCode\" : null,\"authenticationSuccessful\" : true,\"chargeSuccessful\" : false");
		
		// ==== Charge On Status Check True and Verify Balance ==== //
		
		mhp.clickCheckStatusDataPickup(chargeStatus);
		mhp.enterChargeAmount(chargeAmt);
		mhp.checkSUF("\"pgStatusCode\" : \"50020\",\"authenticationSuccessful\" : true,\"chargeSuccessful\" : true");
		
		CSR.verifyIAPTXNDetails(transactionStatusMsg);
	}
}
