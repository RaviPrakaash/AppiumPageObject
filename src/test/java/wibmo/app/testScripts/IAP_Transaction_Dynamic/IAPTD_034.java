package wibmo.app.testScripts.IAP_Transaction_Dynamic;

import org.testng.annotations.AfterMethod;

import wibmo.app.testScripts.IAP_Transaction.IAPTS_034;
// TODO: Auto-generated Javadoc
/**
 * The Class IAPTD_034 Web-SDK transaction using external card  & Successful data pick up

 */
public class IAPTD_034 extends IAPTS_034 {

	/** The tc. */
	public String TC=getTestScenario("IAPTS_034")+" Dynamic Merchant";
	
	@AfterMethod
	public void postconditionDynamicMerchant() // IAPTD_034 is the last TC in Dynamic Merchant suite
	{
		setMerchantShellNormal();
	}
	
}
