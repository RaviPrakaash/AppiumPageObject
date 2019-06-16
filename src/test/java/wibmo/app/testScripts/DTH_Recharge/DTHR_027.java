package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRecharge3DSPage;
import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DTHR_027 Click Approve on the payment screen.
 */
public class DTHR_027 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_027");
	
	/**
	 * Dthr 027.
	 */
	@Test
	public void DTHR_027() // ==== Click on Approve on the payment screen ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_027");
		gotoDTHRecharge(data);	
		
		DTHRechargePage drp=new DTHRechargePage(driver);
		drp.enterValues(data);
		drp.clickPayNow();
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[5]);
		
		DTHRecharge3DSPage dr3p=new DTHRecharge3DSPage(driver);
		dr3p.verifyDTHRecharge3DS();
	}

}
