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

// TODO: Auto-generated Javadoc
/**
 * The Class MR_010 Select valid phone number from Contacts.
 */
public class MR_010 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("MR_010");
	
	/**
	 * Mr 010.
	 */
	@Test
	public void MR_010() // ==== Select valid phone number from Contacts ==== //
	{
		Reporter.log(getTestScenario());
		String data=getTestData("MR_010");
		
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
		mr3p.executeRecharge(data.split(",")[7]);
		
		RechargeTransactionFinalPage rtfp=new RechargeTransactionFinalPage(driver);
		rtfp.verifyRechargeSuccess(data);
		
	}
}
