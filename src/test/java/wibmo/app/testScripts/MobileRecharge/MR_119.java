package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.MobileRecharge3DSPage;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class MR_119 Verify complete transaction with an External card.
 */
public class MR_119 extends BaseTest // ==== Verify complete transaction with an External card ==== //
{	
	
	/** The tc. */
	public String TC=getTestScenario("MR_119");	
	
	/**
	 * Mr 119.
	 */
	@Test
	public void MR_119() 
	{	
		Reporter.log(getTestScenario());
		String data=getTestData("MR_119");
		gotoMobileRecharge(data);
	
		MobileRechargePage mrp = new MobileRechargePage(driver);
		mrp.enterMobileNumber(data);
	
		MobileRechargePayPage mrpp = new MobileRechargePayPage(driver);
		mrpp.enteringAmountToRecharge(data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[6]);
		
		MobileRecharge3DSPage mr3p=new MobileRecharge3DSPage(driver);
		mr3p.executeRecharge(data.split(",")[6]);
		
		RechargeTransactionFinalPage rtfp=new RechargeTransactionFinalPage(driver);
		rtfp.verifyRechargeSuccess(data);		
	}
}
