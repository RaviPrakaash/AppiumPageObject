package wibmo.app.testScripts.IAP_Transaction_Dynamic;

import library.Log;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantLoginPage;
import wibmo.app.pagerepo.MerchantSettingsPage;
import wibmo.app.testScripts.IAP_Transaction.IAPTS_001;

/**
 * The Class IAPTD_001 App-SDK IAP transaction cancellation in login and Verify Balance. Dynamic Merchant.
 */
public class IAPTD_001 extends IAPTS_001
{		
	/** The tc. */
	public String TC=getTestScenario("IAPTS_001")+" Dynamic Merchant";	
}
