package wibmo.app.testScripts.IAP_Transaction;


import library.CSR;
import library.Generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wibmo.app.testScripts.BaseTest1;
import wibmo.webapp.pagerepo.ConnectingPhonePage;
import wibmo.webapp.pagerepo.DeviceVerificationPage;
import wibmo.webapp.pagerepo.IAPPostReportPage;
import wibmo.webapp.pagerepo.Merchant3DSPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.OTPFetchPage;
import wibmo.webapp.pagerepo.PayZappLoginPage;
import wibmo.webapp.pagerepo.TestMerchantPage;

/**
 * The Class IAPTS_031 Web-SDK Successful data pick up. Static Merchant.
 */
public class IAPTS_031 extends BaseTest {

	/** The tc. */
	public String TC=getTestScenario("IAPTS_031");

	
	@BeforeMethod
	public void launchApplication()
	{		   
	  launchAsWebDriver();
	}	
	
	/**
	 * Iapts 031.
	 */
	@Test
	public void IAPTS_031()		//"Web-SDK Successful data pick up"
	{
		
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_031");
		String str[]=data.split(",");
		double amt=Double.parseDouble(str[3]),balanceBeforeTransaction=0.0;
		String loginPassword=str[4];
		String balanceMobileNumber=str[1];
		String cardType=str[5];
		String securePin=str[6];
		
		if(!checkEnv("qa") && Generic.containsIgnoreCase(cardType, programName))
			 balanceBeforeTransaction=CSR.verifyBalance(balanceMobileNumber);
		
		MerchantMainPage main=new MerchantMainPage(driver);
		main.basicInfoSubmit(data);
		
		MerchantCheckOutPage checkout=new MerchantCheckOutPage(driver); 
		checkout.merchantCheckout(data);
		
		ConnectingPhonePage cpp=new ConnectingPhonePage(driver);
		cpp.clickonskip();
		
		PayZappLoginPage login=new PayZappLoginPage(driver);
		login.addlogindetails(loginPassword);
		
		String otp=Generic.getOTP(balanceMobileNumber,bankCode,Generic.getPropValues("DVC", BaseTest1.configPath));
		
		DeviceVerificationPage dvc=new DeviceVerificationPage(driver);
		dvc.adddvcdetails(otp);
		
		TestMerchantPage merchant=new TestMerchantPage(driver);
		merchant.addtestmerchantdetails(cardType);
		
		Merchant3DSPage ds=new Merchant3DSPage(driver); // if (Generic.checkWalletITPDirect(cardType))
		if (!Generic.checkWalletITPDirect(cardType))
			ds.submitVisaVerification(securePin);
		else
			ds.enterCVV(securePin.split(":")[1]);
		
		IAPPostReportPage icpr=new IAPPostReportPage(driver);
		String transactionid=icpr.fetchwibmoid();	
		icpr.checkSUF( "\"pgStatusCode\" : \"50020\",\"authenticationSuccessful\" : true"); 
		
		if(!checkEnv("qa") && Generic.containsIgnoreCase(cardType, programName))
		{
			double balanceAfterTransaction=CSR.verifyBalance(balanceMobileNumber);	
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt,balanceAfterTransaction);
		} 	
		
		CSR.verifyIAPTransactionDataPickupSuccess(transactionid);	// hash fail for first submit button ,  using v2 submit => no data pickup in CSR 
	}
	
}
