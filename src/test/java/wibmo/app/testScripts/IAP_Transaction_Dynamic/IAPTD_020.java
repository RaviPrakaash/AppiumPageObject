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
import wibmo.app.testScripts.IAP_Transaction.IAPTS_020;
import wibmo.webapp.pagerepo.ConnectingPhonePage;
import wibmo.webapp.pagerepo.IAPPostReportPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.PayZappLoginPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTD_020 Web-SDK IAP transaction cancellation in login. Dynamic Merchant.
 *
 */
public class IAPTD_020 extends IAPTS_020 
{
	/** The tc. */
	public String TC=getTestScenario("IAPTS_020")+" Dynamic Merchant";		
}
