package wibmo.app.testScripts.SmokeTest;


import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.MobileRecharge3DSPage;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.pagerepo.RechargeTransactionFinalPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class SMT_018 Recharge flow - Mobile.
 */
public class SMT_018 extends BaseTest // ==== Recharge flow - Mobile ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SMT_018");	
	
	/**
	 * Smt 018.
	 */
	@Test
	public void SMT_018() 
	{	
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_018");
		
		String cardDetails=data.split(",")[6];
		
		gotoMobileRecharge(data);		
	
		MobileRechargePage mrp = new MobileRechargePage(driver);
		mrp.enterMobileNumber(data);
	
		MobileRechargePayPage mrpp = new MobileRechargePayPage(driver);
		mrpp.enteringAmountToRecharge(data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MobileRecharge3DSPage mr3p=new MobileRecharge3DSPage(driver);
		mr3p.executeRecharge(cardDetails);
		
		RechargeTransactionFinalPage rtfp=new RechargeTransactionFinalPage(driver);
		rtfp.verifyRechargeSuccess(data);					
	}
}
