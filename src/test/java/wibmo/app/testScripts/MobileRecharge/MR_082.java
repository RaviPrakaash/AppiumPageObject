package wibmo.app.testScripts.MobileRecharge;

import org.testng.Reporter;
import org.testng.annotations.Test;
import library.CSR;
import library.Generic;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.MobileRechargePayPage;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class MR_082  Perform recharge with Card having less balance than transaction amount.
 */
public class MR_082 extends BaseTest //==== Card having less balance than Txn amount ====//
{	
	/** The tc. */
	public String TC=getTestScenario("MR_082");
	
	/**
	 * Mr 082.
	 */
	@Test
	public void MR_082()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("MR_082");
		String cardDetails=data.split(",")[6];
		
		gotoRecharge(data);
		
		RechargePage rp = new RechargePage(driver);		
		
		// ==== Automatically creates an amount greater than the user balance 
		if(Generic.containsIgnoreCase(cardDetails, programName))
		{			
			// ==== Get User Balance ==== //
			Generic.wait(3);
			rp.gotoAddSend();
			
			AddSendPage asp= new AddSendPage(driver);
			int newBal=(int)asp.verifyBalance();
			
			if(newBal>1)			
				data=data.replace(data.split(",")[5],newBal+5+"");
			
			driver.navigate().back();
			
		/*	try
			{
				Log.info("======== Updating testdata based on current user balance ========");
				int newBal=(int)CSR.verifyBalance(data.split(",")[0]);
				if(newBal>1)			
					data=data.replace(data.split(",")[5],newBal+5+"");			
			}
			catch(Exception e)
			{			
				Log.info("======== Unable to retreive balance from CSR ========");
			}*/
		}	
		
		rp.gotoMobileRecharge();
		
		MobileRechargePage mrp=new MobileRechargePage(driver);		
		mrp.enterMobileNumber(data);

		MobileRechargePayPage mrpp = new MobileRechargePayPage(driver);
		mrpp.enteringAmountToRecharge(data);

		WibmoSDKPage wsp = new WibmoSDKPage(driver);
		wsp.verifyInsufficientBalance(cardDetails);
	}
}
