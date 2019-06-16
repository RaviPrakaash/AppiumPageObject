package wibmo.app.testScripts.IAP_Transaction_Dynamic;

import library.CSR;
import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;
import wibmo.app.testScripts.IAP_Transaction.IAPTS_012;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTD_012 IAP transaction failure on 3ds authentication.Dynamic Merchant.
 *
 */
public class IAPTD_012 extends IAPTS_012
{
	/** The tc. */
	public String TC=getTestScenario("IAPTS_012")+" Dynamic Merchant";
}
