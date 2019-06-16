package wibmo.app.testScripts.IAP_Transaction;

import library.CSR;
import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTS_007 IAP transaction hold on phone verification . Static Merchant
 */
public class IAPTS_007 extends BaseTest // ==== IAP transaction hold on phone verification ==== //
{

/** The tc. */
public String TC=getTestScenario("IAPTS_007");
	
	/**
	 * Iapts 007.
	 */
	@Test
	public void IAPTS_007()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_007");
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++],securePin =values[i++],cardDetails=values[3];
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);
		Generic.logout(driver);
				
		WelcomePage wp=new WelcomePage(driver);
		setPhoneVerifyStatus(true);
		wp.changeUserTemp(loginId);
		wp.selectUser(loginId);
		
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp = new MerchantHomePage(driver);		
		mhp.executePayment(data);
		mhp.selectApp(programName);
		mhp.enterSecurePin(data);	
		
		Generic.waitForPhoneVerification(driver);		
		
		Generic.switchToApp(driver);		
		
		double balanceAfterTransaction=Generic.checkWalletBalance(driver,data);
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		//CSR.verifyInitSuccess(data.split(",")[0]);
		CSR.verifyStatusOnTxnDesc("Init Success");
	}
}
