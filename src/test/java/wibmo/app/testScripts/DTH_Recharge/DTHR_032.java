package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRecharge3DSPage;
import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DTHR_032 Verify 3DS Authentication fail error message for External card.
 */
public class DTHR_032 extends BaseTest // ==== Verify 3DS Authentication failed error message for External card ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_032"); 
	
	/**
	 * Dthr 032.
	 */
	@Test
	public void DTHR_032()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_032");
		gotoDTHRecharge(data);	

		DTHRechargePage drp=new DTHRechargePage(driver);
		drp.enterValues(data);		
		drp.clickPayNow();

		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(data.split(",")[5]);

		DTHRecharge3DSPage dth3p = new DTHRecharge3DSPage(driver);
		dth3p.verifyDTHRecharge3DS();
		dth3p.submitDTHRecharge3DS(data.split(",")[5].split(":")[1]);
		dth3p.verifyAuthenticationErrMsg();
	}
}
