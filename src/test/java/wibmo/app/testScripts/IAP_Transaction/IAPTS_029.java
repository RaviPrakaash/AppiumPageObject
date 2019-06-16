package wibmo.app.testScripts.IAP_Transaction;


import library.CSR;
import library.Generic;

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
import wibmo.webapp.pagerepo.MerchantShellAppConfigPage;
import wibmo.webapp.pagerepo.OTPFetchPage;
import wibmo.webapp.pagerepo.PayZappLoginPage;
import wibmo.webapp.pagerepo.TestMerchantPage;

/**
 * The Class IAPTS_029 Web-SDK IAP transaction status when 3ds fails.Static Merchant.
 */
public class IAPTS_029 extends BaseTest
{	
	
	@BeforeMethod
	public void launchApplication()
	{
		launchAsWebDriver();
	}
	
	/**
	 * Iapts 029.
	 */
	@Test
	public void IAPTS_029()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_029");
		String str[]=data.split(",");
		
		double balanceBeforeTransaction=CSR.verifyBalance(str[1]);
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcp= new MerchantCheckOutPage(driver);
		mcp.merchantCheckout(data);
		
		ConnectingPhonePage cpp=new ConnectingPhonePage(driver);
		cpp.clickonskip();
		
		PayZappLoginPage pzlp=new PayZappLoginPage(driver);
		pzlp.addlogindetails(str[4]);
		
		 //fetch the otp from other link
		String getOtp=Generic.getOTP(str[1], bankCode, Generic.getPropValues("DVC", configPath));
		
		DeviceVerificationPage dvp=new DeviceVerificationPage(driver);
		dvp.adddvcdetails(getOtp);
		
		TestMerchantPage tmp=new TestMerchantPage(driver);
		tmp.addtestmerchantdetails(str[5]);
		
		Merchant3DSPage m3dsp=new Merchant3DSPage(driver);
		m3dsp.submitVisaVerification(str[6]);	 //submit invalid secure pin
		m3dsp.verifyAuthenticationFailed();
		m3dsp.cancelVisaVerification();
		//verify authentication failed message
		
		IAPPostReportPage icpr=new IAPPostReportPage(driver);
		String transactionid=icpr.fetchwibmoid();	//Fetch the transaction id from IAP Report
		
		double balanceAfterTransaction=CSR.verifyBalance(str[1]);	
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction); 
		
		CSR.verifyStatusOnTxnId(transactionid,"043","3DS Failed ");

	}	
}
