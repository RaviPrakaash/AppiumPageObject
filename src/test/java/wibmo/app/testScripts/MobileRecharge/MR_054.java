package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;
import com.libraries.ExcelLibrary;
import library.Generic;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.MobileRecharge3DSPage;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class MR_054 Select correct postpaid number , operator and amount and click on Pay Now button , 
 * 					Complete transaction with primary Payzapp card.
 */
public class MR_054 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("MR_054");
	
	/**
	 * Mr 054.
	 */
	
	@Test
	public void MR_054()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("MR_054");
		
		Generic.loginUntrustedDevice(driver, data);
		
		HomePage hp=new HomePage(driver);
		hp.gotoRecharge();
		
		RechargePage rp=new RechargePage(driver);
		rp.gotoMobileRecharge();
	
		MobileRechargePage mrp = new MobileRechargePage(driver);
		mrp.selectContact(data);
	
		MobileRechargePayPage mrpp = new MobileRechargePayPage(driver);
		mrpp.enteringAmountToRecharge(data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[7]);
		
		MobileRecharge3DSPage mr3p=new MobileRecharge3DSPage(driver);
		mr3p.executeRecharge(data);
		
		RechargeTransactionFinalPage rtfp=new RechargeTransactionFinalPage(driver);
		rtfp.verifyRechargeSuccess(data);
	}
}
