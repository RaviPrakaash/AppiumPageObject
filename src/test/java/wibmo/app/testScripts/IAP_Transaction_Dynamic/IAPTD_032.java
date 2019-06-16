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
import wibmo.app.testScripts.IAP_Transaction.IAPTS_032;
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
 * The Class IAPTD_032 Web-SDK Successful data pick up. Static Merchant.
 */
public class IAPTD_032 extends IAPTS_032 
{
	/** The tc. */
	public String TC=getTestScenario("IAPTS_032")+" Dynamic Merchant";	
}
