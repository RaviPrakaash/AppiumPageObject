package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.MobileRecharge3DSPage;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class MR_120 Verify 3DS Authentication fail error message for External card.
 */
public class MR_120 extends BaseTest // ==== Verify 3DS Authentication fail error message for External card ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("MR_120");	
	
	/**
	 * Mr 120.
	 */
	@Test
	public void MR_120() 
	{	
		Reporter.log(getTestScenario());
		String data=getTestData("MR_120");
		gotoMobileRecharge(data);
	
		MobileRechargePage mrp = new MobileRechargePage(driver);
		mrp.enterMobileNumber(data);
	
		MobileRechargePayPage mrpp = new MobileRechargePayPage(driver);
		mrpp.enteringAmountToRecharge(data);		
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[6]);
		
		MobileRecharge3DSPage mr3p=new MobileRecharge3DSPage(driver);
		mr3p.verifyMobileRecharge3DS();
		mr3p.submitMobileRecharge3DS(data.split(",")[6].split(":")[1]);
		mr3p.verifyAuthErrMsg();			
	}

}
