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
import wibmo.app.testScripts.IAP_Transaction.IAPTS_025;
import wibmo.webapp.pagerepo.IAPPostReportPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.MerchantShellAppConfigPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTD_025 Web-SDK IAP transaction when message hash fails. Dynamic Merchant.

 */
public class IAPTD_025 extends IAPTS_025
{	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_025")+" Dynamic Merchant";
}
