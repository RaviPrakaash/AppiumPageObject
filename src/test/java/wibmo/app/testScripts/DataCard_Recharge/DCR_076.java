package wibmo.app.testScripts.DataCard_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;
import com.libraries.Log;
import library.CSR;
import library.Generic;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class DCR_076 Perform transaction with a card with balance less than transaction amount.
 */
public class DCR_076 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_076");
	
	/**
	 * Dcr 076.
	 */
	@Test
	public void DCR_076() // ==== Click on cancel on the payment screen ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DCR_076");
        String cardDetails=data.split(",")[7];

        gotoRecharge(data);		
		RechargePage rp = new RechargePage(driver);		
		
		if(Generic.containsIgnoreCase(cardDetails, programName)) // Calculate recharge amount automatically if using program card
		{
			// ==== Get User Balance ==== //
			Generic.wait(2);
			rp.gotoAddSend();
			
			AddSendPage asp= new AddSendPage(driver);
			int newBal=(int)asp.verifyBalance();
			
			if(newBal>1)			
				data=data.replace(data.split(",")[6],newBal+5+"");
			
			driver.navigate().back();
						
			/*try
			{
				Log.info("======== Updating testdata based on current user balance ========");
				int newBal=(int)CSR.verifyBalance(data.split(",")[0]);
				if(newBal>1)			
					data=data.replace(data.split(",")[6],newBal+5+"");			
			}
			catch(Exception e)
			{			
				Log.info("======== Unable to retreive balance from CSR ========");
			}*/
		}
		
		rp.gotoDataCardRecharge();
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);

		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.enterValues(data);
		dcrp.clickPayNow();
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.verifyInsufficientBalance(cardDetails);
	}
}
