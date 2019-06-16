package wibmo.app.testScripts.IAP_Transaction;

import library.CSR;
import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTS_006 IAP transaction hold on login screen. Static merchant
 */
public class IAPTS_006 extends BaseTest // ==== IAP transaction hold on login screen ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_006");	
	
	/**
	 * Iapts 006.
	 */
	@Test
	public void IAPTS_006() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_006");	
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);
		Generic.logout(driver);
		Generic.wait(5); // Settings link not recognised
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config.xls file		
				
		mhp.verifySecurePinPresent();			
		
		Generic.switchToApp(driver);		
		
		double balanceAfterTransaction=Generic.checkWalletBalance(driver, data);
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);	
		
		//CSR.verifyInitSuccess(data.split(",")[0]);
		CSR.verifyStatusOnTxnDesc("Init Success");
	}

}
