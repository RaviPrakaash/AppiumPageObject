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
 * The Class IAPTS_010  App-SDK IAP transaction status in hold on 3ds screen.Static Merchant.
 */
public class IAPTS_010 extends BaseTest // ==== App-SDK IAP transaction status in hold on 3ds screen ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_010");	
	
	/**
	 * Iapts 010.
	 */
	@Test
	public void IAPTS_010() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_010");	
		
		String cardDetails=data.split(",")[3];
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);	
		Generic.logout(driver);
		
		WelcomePage wp=new WelcomePage(driver);
		wp.changeUserTemp(data);
		wp.selectUser(data.split(",")[0]); 
		
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config.xls file		
		
		mhp.enterSecurePin(data);		
		Generic.merchantVerifiedLogin(driver, data);
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.verifyMerchantPayment3DS();		
		
		Generic.switchToApp(driver);		
		
		double balanceAfterTransaction=Generic.checkWalletBalance(driver,data);
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);	
		
		//CSR.verify3DSIntermediate(data.split(",")[0]); 
		CSR.verifyStatusOnTxnDesc("Intermediate");
	}

}
