package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.MobileRecharge3DSPage;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class MR_121 Verify 3DS Cancel for Any Card.
 */
public class MR_121 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("MR_121");	
	
	/**
	 * Mr 121.
	 */
	@Test
	public void MR_121() 
	{	
		Reporter.log(getTestScenario());
		String data=getTestData("MR_121");
		gotoMobileRecharge(data);
	
		MobileRechargePage mrp = new MobileRechargePage(driver); 
		mrp.enterMobileNumber(data);
	
		MobileRechargePayPage mrpp = new MobileRechargePayPage(driver);
		mrpp.enteringAmountToRecharge(data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[6]);
		
		MobileRecharge3DSPage mr3p=new MobileRecharge3DSPage(driver);
		mr3p.cancel3DS();
		
		RechargeTransactionFinalPage rtfp=new RechargeTransactionFinalPage(driver);
		rtfp.verifyTransactionFailMsg();					
	}

}
