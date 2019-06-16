package wibmo.app.testScripts.IAP_Transaction;

import library.CSR;
import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantLoginPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class IAPTS_012 	IAP 3DS Authentication failure, Verify Balance.Static Merchant.
 */
public class IAPTS_012 extends BaseTest // ==== IAP 3DS Authentication failure ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_012");
	
	/**
	 * Iapts 012.
	 */
	@Test
	public void IAPTS_012() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_012");
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++],cardDetails=values[3];
		
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
		Generic.merchantVerifiedLogin(driver,data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);	
		mp3p.verifyMerchantPayment3DS();
		mp3p.submitPayment(cardDetails.split(":")[1]);
		mp3p.verifyFailureMsg();		 
		
		Generic.switchToApp(driver);	
		double balanceAfterTransaction=Generic.checkWalletBalance(driver,data);
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		//CSR.verify3DSIntermediate(data.split(",")[0]);
		CSR.verifyStatusOnTxnDesc("Intermediate");		
	}

}
