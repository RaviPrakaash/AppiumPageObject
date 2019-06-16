package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.MobileRecharge3DSPage;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class MR_122 Verify complete 3DS transaction with any card.
 */
public class MR_122 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("MR_122");	
	
	/**
	 * Mr 122.
	 */
	@Test
	public void MR_122() 
	{	
		Reporter.log(getTestScenario());
		String data=getTestData("MR_122");
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
