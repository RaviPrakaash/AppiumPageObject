package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRechargePage;
import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class DCR_003 Click on "Select From Contacts" and select contact.
 */
public class DCR_003 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_003");	
	
	/**
	 * Dcr 003.
	 */
	@Test
	public void DCR_003() // ==== Click on Data Card Recharge icon, choose contact after clicking on select contact icon ==== // 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_003");
		gotoDataCardRecharge(getTestData("DCR_003"));
		this.getClass().getSimpleName();
		DCR_003.class.getSimpleName();
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.selectContacts(data);

		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.verifyDataCardNumber(data);			
	}
}
