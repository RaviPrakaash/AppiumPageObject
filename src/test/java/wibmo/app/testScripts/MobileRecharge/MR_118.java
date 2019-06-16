package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;
import library.Generic;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.MobileRecharge3DSPage;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class MR_118  Verify 3DS Authentication fail error message for Program Card.
 */
public class MR_118 extends BaseTest  
{
	
	/** The tc. */
	public String TC=getTestScenario("MR_118");	
	
	/**
	 * Mr 118.
	 */
	@Test
	public void MR_118() 
	{	
		Reporter.log(getTestScenario());
		String data=getTestData("MR_118");		
		
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
		
		if(Generic.checkWalletITPDirect(data.split(",")[6])) return;
		
		MobileRecharge3DSPage mr3p=new MobileRecharge3DSPage(driver);
		mr3p.verifyMobileRecharge3DS();
		mr3p.submitMobileRecharge3DS(data.split(",")[6].split(":")[1]);
		mr3p.verifyAuthErrMsg();			
	}
}
