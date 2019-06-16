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
 * The Class DCR_073 Click on Approve button in the payment screen.
 */
public class DCR_073 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_073");
	
	/**
	 * Dcr 073.
	 */
	@Test
	public void DCR_073() // ==== Click on Approve on the payment screen ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_073"); 
		gotoDataCardRecharge(data);
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.enterValues(data);
		dcrp.clickPayNow();
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[7]);	
		
		DataCardRecharge3DSPage dcr3ds = new DataCardRecharge3DSPage(driver);
		dcr3ds.verifyDataCardRecharge3DS();
	}
}
