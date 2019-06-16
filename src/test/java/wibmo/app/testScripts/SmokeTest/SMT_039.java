package wibmo.app.testScripts.SmokeTest;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import library.Generic;
import wibmo.app.pagerepo.DTHRecharge3DSPage;
import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.MobileRecharge3DSPage;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class SMT_039  DTH Recharge 
 */
public class SMT_039 extends BaseTest
{	
	
	/** The tc. */
	public String TC=getTestScenario("SMT_039");
	
	/**
	 * SMT 039.
	 */
	@Test
	public void SMT_039()  
	{	
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_039");				
		
		gotoDTHRecharge(data);

		DTHRechargePage drp=new DTHRechargePage(driver);
		drp.enterValues(data);		
		drp.clickPayNow();

		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[5]);

		DTHRecharge3DSPage dth3p = new DTHRecharge3DSPage(driver);
		dth3p.executeDTHRechargeGeneric(data.split(",")[5]);
		
		RechargeTransactionFinalPage rtfp=new RechargeTransactionFinalPage(driver);
		rtfp.verifyRechargeSuccess(data);
	}
}
