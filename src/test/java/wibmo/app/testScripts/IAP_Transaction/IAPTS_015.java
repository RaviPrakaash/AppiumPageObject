package wibmo.app.testScripts.IAP_Transaction;

import library.CSR;
import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTS_015 	IAP transaction failure on 3ds authentication and Verify Balance.Static Merchant.
 */
public class IAPTS_015 extends BaseTest // ==== IAP transaction failure on 3ds authentication and Verify Balance ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_015");

	/**
	 * Iapts 015.
	 */
	@Test
	public void IAPTS_015()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_015");
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++],securePin =values[i++],cardDetails=values[3];

		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);		
		Generic.logout(driver);
		
		WelcomePage wp=new WelcomePage(driver);
		wp.changeUserTemp(data);
		wp.selectUser(loginId);				
		
		Generic.switchToMerchant(driver);			
		
		MerchantHomePage mhp = new MerchantHomePage(driver);		
		mhp.executePayment(data);
		mhp.selectApp(programName);
		mhp.enterSecurePin(data);
		
		Generic.merchantVerifiedLogin(driver, data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);		
		mp3p.submitPayment(cardDetails.split(":")[1]);
		mp3p.verifyFailureMsg();		
		
		Generic.switchToApp(driver);	
		double balanceAfterTransaction=Generic.checkWalletBalance(driver,data);
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		//CSR.verify3DSIntermediate(loginId); 
		CSR.verifyStatusOnTxnDesc("Intermediate");
	}

}
