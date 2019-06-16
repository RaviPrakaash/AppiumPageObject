package wibmo.app.testScripts.IAP_Transaction_Dynamic;

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
import wibmo.app.testScripts.IAP_Transaction.IAPTS_024;
import wibmo.webapp.pagerepo.ConnectingPhonePage;
import wibmo.webapp.pagerepo.DeviceVerificationPage;
import wibmo.webapp.pagerepo.IAPPostReportPage;
import wibmo.webapp.pagerepo.Merchant3DSPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.OTPFetchPage;
import wibmo.webapp.pagerepo.PayZappLoginPage;
import wibmo.webapp.pagerepo.TestMerchantPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTD_024 Web-SDK IAP transaction status in cancellation 3ds screen. Dynamic Merchant.
 *
 */
public class IAPTD_024 extends IAPTS_024 
{
	/** The tc. */
	public String TC=getTestScenario("IAPTS_024")+" Dynamic Merchant";	
}
