package wibmo.app.testScripts.SmokeTest;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.DataCardRecharge3DSPage;
import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class SMT_040 Click on Cancel button in the payment screen.
 */
public class SMT_040 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_040");
	
	/**
	 * SMT 040
	 */
	@Test
	public void SMT_040() 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_040");
		gotoDataCardRecharge(data);	
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.enterValues(data);
		dcrp.clickPayNow();
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[7]);	
		
		DataCardRecharge3DSPage dcr3p=new DataCardRecharge3DSPage(driver);
		dcr3p.executeDataCardRechargeGeneric(data.split(",")[7]);
		
		RechargeTransactionFinalPage rtfp=new RechargeTransactionFinalPage(driver);
		rtfp.verifyRechargeSuccess(data);
	}
}
