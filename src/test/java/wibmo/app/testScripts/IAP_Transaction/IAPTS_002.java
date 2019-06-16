package wibmo.app.testScripts.IAP_Transaction;

import library.CSR;
import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.WelcomePage;

// TODO: Auto-generated Javadoc
/**
 * The Class IAPTS_002 App-SDK IAP transaction cancellation in phone verification and Verify Balance, for Static Merchant 
 * 
 */
public class IAPTS_002 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("IAPTS_002");

	/**
	 * Iapts 002.
	 */
	@Test
	public void IAPTS_002()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_002");
		
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++];
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);
		Generic.logout(driver);		
			
		WelcomePage wp=new WelcomePage(driver);
		setPhoneVerifyStatus(true);
		wp.changeUserTemp(loginId);
		wp.selectUser(loginId);
		Generic.wait(2);
		
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp = new MerchantHomePage(driver);		
		mhp.executePayment(data);
		mhp.selectApp(programName);
		mhp.enterSecurePin(data);	
		
		Generic.abortPhoneVerification(driver);
		
		Generic.switchToApp(driver);		
		
		double balanceAfterTransaction=Generic.checkWalletBalance(driver,data);
		
		Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		//CSR.verifyInitSuccess(loginId);
		CSR.verifyStatusOnTxnDesc("Init Success");
	}
}
