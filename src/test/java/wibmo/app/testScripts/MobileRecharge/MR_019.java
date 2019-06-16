package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;
import library.Generic;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.MobileRecharge3DSPage;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class MR_019 Enter 10 Digit number.
 */
public class MR_019 extends BaseTest // ==== Enter 10 Digit number, Positive ==== //
{
	
	/** The tc. */
	String TC=getTestScenario("MR_019");
	
	/**
	 * Mr 019.
	 */
	@Test
	public void MR_019() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("MR_019");
		
		Generic.loginUntrustedDevice(driver, data);
		
		HomePage hp=new HomePage(driver);
		hp.gotoRecharge();
		
		RechargePage rp=new RechargePage(driver);
		rp.gotoMobileRecharge();
	
		MobileRechargePage mrp = new MobileRechargePage(driver);
		mrp.enterMobileNumber(data);
	
		MobileRechargePayPage mrpp = new MobileRechargePayPage(driver);
		mrpp.enteringAmountToRecharge(data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[6]);
		
		MobileRecharge3DSPage mr3p=new MobileRecharge3DSPage(driver);
		mr3p.executeRecharge(data);		
		
		RechargeTransactionFinalPage rtfp=new RechargeTransactionFinalPage(driver);
		rtfp.verifyRechargeSuccess(data);
	}
}
