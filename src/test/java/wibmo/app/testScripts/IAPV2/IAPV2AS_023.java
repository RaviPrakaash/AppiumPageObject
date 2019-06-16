package wibmo.app.testScripts.IAPV2;


import library.CSR;
import library.Generic;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;
import wibmo.webapp.pagerepo.IAPPostReportPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;

/**
 * The Class IAPV2AS_023 Web-SDK V2-IAP with 3ds & CVV U
 */
public class IAPV2AS_023 extends BaseTest
{

	/** The tc. */
	public String TC=getTestScenario("IAPV2AS_019");
	public WebDriver sdkDriver;
	
	
	
	/**
	 * IAPV2AS_019
	 */
	@Test
	public void IAPV2AS_019()
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("IAPV2AS_023");
		String str[]=data.split(",");		
		String mobileNumber=str[0];
		String loginPassword=str[1];
		String amt=str[2];
		String cardDetails=str[3];
		String securePin=str[4];
		boolean chargeStatus=str[7].toLowerCase().contains("true");
		String cvvStatus=Generic.parseCVVStatus(data);	
		
		
		//=================== Login ======================//
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(mobileNumber);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(loginPassword);
		
		Generic.verifyLogin(driver, data);
		
		//================= Initiate WebSDK ================================ //
		
		initiateWebSDK(data);
		
		//=================Open Notification & complete Transaction===================//
		
		Generic.wait(15);
		Generic.gotoIAPFromNotifications(driver);
		
		CSR.setCVVStatus(cardDetails, cvvStatus);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver); // can handle only 3DS flow 
		mp3p.executeMerchantPayment(cardDetails);
		
		driver=sdkDriver;		
		
		// ================ Verify Transaction in Web SDK ================ //
		
		IAPPostReportPage icpr=new IAPPostReportPage(driver);
		String transactionid=icpr.fetchwibmoid();		
		
		icpr.checkSUF(false,"\"pgStatusCode\" : null,\"authenticationSuccessful\" : true,\"chargeSuccessful\" : false");
		if(chargeStatus)
		icpr.checkSUF(chargeStatus,"\"pgStatusCode\" : \"50020\",\"authenticationSuccessful\" : true,\"chargeSuccessful\" : true");		
		
		CSR.verifyIAPTXNDetails(transactionid);	
	}	
	
	
	public void initiateWebSDK(String data)
	{
		String amt=data.split(",")[2];
		
		sdkDriver=launchWebDriver();		
		
		MerchantMainPage main=new MerchantMainPage(sdkDriver);
		main.basicInfo(data);		
		
		MerchantCheckOutPage checkout=new MerchantCheckOutPage(sdkDriver); 
		checkout.merchantCheckout(amt,"IAPV2");		// On AssertionError catch, equate to driver,then throw Assert if necessary
		
	}
	
	
}
