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
import wibmo.webapp.pagerepo.Merchant3DSPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMVCPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.MerchantPaymentPage;
import wibmo.webapp.pagerepo.MerchantRegistrationPage;
import wibmo.webapp.pagerepo.MerchantTransactionFinalPage;
import wibmo.webapp.pagerepo.PopUpsHandlingPage;

/**
 * The Class GCO_023 IAP transaction cancellation at consecutive steps
 * GCO_011	IAP transaction cancellation on step 1 card selection 
 * GCO_012	IAP transaction cancellation on step 2 3DS 
 * GCO_013	IAP transaction cancellation on step 3 Reg step1
 * GCO_014	IAP transaction cancellation on step 4 MVC
 */
public class GCO_023 extends BaseTest
{
	
	@BeforeMethod
	public void launchApplication()
	{
		launchAsWebDriver();
	}
	
	public String TC=getTestScenario("GCO_023");
	
	/**
	 * Gco 023.
	 */
	@Test
	public void GCO_023()
	{			
		Reporter.log(getTestScenario());		
		
		//---------------- GCO_011	IAP transaction cancellation on step 1 card selection ---------------- //
		
		String data=getTestData("GCO_011");
		setGroupValue("GCO_011");
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcp=new MerchantCheckOutPage(driver);
		mcp.merchantCheckout(data);
		
		MerchantPaymentPage mpp=new MerchantPaymentPage(driver);
		mpp.cancelCardDetails(data);
		
		PopUpsHandlingPage php=new PopUpsHandlingPage(driver);
		php.acceptAlert();
		
		IAPPostReportPage iprp=new IAPPostReportPage(driver);
		iprp.verifyUserAbort();
		
		String txnId=iprp.fetchwibmoid();	//Fetch the transaction id from IAP Report
		
		CSR.verifyStatusOnTxnId(driver, txnId, "204", "User Aborted");	// 010 Init Success
		
		iprp.testAgain();		
		
		//---------------- GCO_012	IAP transaction cancellation on step 2 3DS ---------------- //
		
		data=getTestData("GCO_012");
		setGroupValue("GCO_012");
		String[] str = data.split(",");
		 
		mmp.basicInfoSubmit(data);
		 
		mcp.merchantCheckout(data);
		 
		mpp.enterCardDetails(data);
		 
		Merchant3DSPage m3dsp=new Merchant3DSPage(driver);
		m3dsp.cancelVisaVerification();
		 
		Generic.wait(2);
		 
		iprp.verifyFailure();
	
		txnId=iprp.fetchwibmoid();	// Fetch the transaction id from IAP Report
			
		CSR.verifyStatusOnTxnId(driver, txnId, "043", "3DS Failed");
		
		iprp.testAgain();
		
		// ---------------- GCO_013	IAP transaction cancellation on step 3 Reg step1 ---------------- //
		
		data = getTestData("GCO_013");
		setGroupValue("GCO_013");
		
		str = data.split(",");
		
		mmp.basicInfoSubmit(data);
		
		mcp.merchantCheckout(data);
		
		mpp.enterCardDetails(data);
		
		m3dsp.submitVisaVerification(str[7]);
		
		MerchantTransactionFinalPage mtfp=new MerchantTransactionFinalPage(driver);
		mtfp.forFasterCheckout();
		
		MerchantRegistrationPage mrp=new MerchantRegistrationPage(driver);
		mrp.cancelReg();
		
		php.acceptAlert();
		
		iprp.verifySuccess();
		
		txnId=iprp.fetchwibmoid();	//Fetch the transaction id from IAP Report
		
		CSR.verifyStatusOnTxnId(driver, txnId, "021", "Authorization Success");
		
		iprp.testAgain();
		
		// ---------------- GCO_014	IAP transaction cancellation on step 4 MVC ---------------- //
		
		data=getTestData("GCO_014");
		setGroupValue("GCO_014");
		 
		str = data.split(",");
		
		mmp.basicInfoSubmit(data);
		
		mcp.merchantCheckout(data);
		
		mpp.enterCardDetails(data);
		
		m3dsp.submitVisaVerification(str[7]);
		
		mtfp.forFasterCheckout();
		
		mrp.addRegistrationDetails(data);
		
		MerchantMVCPage sfrsp3=new MerchantMVCPage(driver);
		sfrsp3.clickoncancel();
		
		Generic.wait(2);
		
		iprp.verifySuccess();
		
		txnId=iprp.fetchwibmoid();	//Fetch the transaction id from IAP Report
		
		CSR.verifyStatusOnTxnId(driver, txnId, "021", "Authorization Success");			

	}	
	
	@AfterMethod
	public void completeGroupExecution()
	{
		groupExecute=false;
	}
	
}
