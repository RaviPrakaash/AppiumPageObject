package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRecharge3DSPage;
import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.DataCardRecharge3DSPage;
import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_072.
 */
public class DCR_072 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_071");
	
	/**
	 * Dcr 072.
	 */
	@Test
	public void DCR_072() // ==== Click Pay Now after 5 minutes ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_072");
		gotoDataCardRecharge(data);	
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.enterValues(data);
		drp.waitFor5Mins();
		dcrp.clickPayNow();
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[7]);	
		
		DataCardRecharge3DSPage dcr3ds = new DataCardRecharge3DSPage(driver);
		dcr3ds.verifyDataCardRecharge3DS();
	}
}
