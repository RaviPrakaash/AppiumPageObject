package wibmo.app.testScripts.Guest_Checkout;

import java.util.concurrent.TimeUnit;

import library.CSR;
import library.Generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wibmo.app.testScripts.BaseTest1;
import wibmo.webapp.pagerepo.IAPPostReportPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.MerchantPaymentPage;
import wibmo.webapp.pagerepo.PopUpsHandlingPage;
import wibmo.webapp.pagerepo.MerchantRegistrationPage;
import wibmo.webapp.pagerepo.MerchantMVCPage;
import wibmo.webapp.pagerepo.MerchantTransactionFinalPage;
import wibmo.webapp.pagerepo.Merchant3DSPage;

/**
 * The Class GCO_014 IAP transaction cancellation on step 4
 */
public class GCO_014 extends BaseTest
{	
	public String TC=getTestScenario("GCO_014");
	
	@BeforeMethod
	public void launchApplication()
	{
		launchAsWebDriver();
	}
	
	/**
	 * Gco 014.
	 */
	@Test
	public void GCO_014()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("GCO_014");
		String[] str = data.split(",");
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcp=new MerchantCheckOutPage(driver);
		mcp.merchantCheckout(data);
		
		MerchantPaymentPage pzp=new MerchantPaymentPage(driver);
		pzp.enterCardDetails(data);
		
		Merchant3DSPage vvp=new Merchant3DSPage(driver);
		vvp.submitVisaVerification(str[7]);
		
		MerchantTransactionFinalPage sfcp=new MerchantTransactionFinalPage(driver);
		sfcp.forFasterCheckout();
		
		MerchantRegistrationPage sfrsp2=new MerchantRegistrationPage(driver);
		sfrsp2.addRegistrationDetails(data);
		
		MerchantMVCPage sfrsp3=new MerchantMVCPage(driver);
		sfrsp3.clickoncancel();
		
		Generic.wait(2);
		
		IAPPostReportPage iapprp=new IAPPostReportPage(driver);
		iapprp.verifySuccess();
		
		String transactionId=iapprp.fetchwibmoid();	//Fetch the transaction id from IAP Report
		
		CSR.verifyStatusOnTxnId(driver, transactionId, "021", "Authorization Success");		
		
	}

}
