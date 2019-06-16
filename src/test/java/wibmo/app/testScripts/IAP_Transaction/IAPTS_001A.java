package wibmo.app.testScripts.IAP_Transaction;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import library.CSR;
import library.Generic;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantCardSelectionPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantLoginPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.MerchantSettingsPage;
import wibmo.app.pagerepo.WibmoSDKPage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class IAPTS_001A Verify IAP transaction report for a successful transaction
 *
 */
public class IAPTS_001A extends BaseTest 
{		
	
	/** The tc. */
	String TC=getTestScenario("IAPTS_001A");

	/**
	 * Iapts 001a.
	 */
	@Test
	public void IAPTS_001A()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_001A");
		String transactionStatusMsg="";
		int i=0;
		String[] values = data.split(",");
		String cardDetails=values[3];
		double amt=Double.parseDouble(values[2]),balanceBeforeTransaction=0.0;
		
		if(!checkEnv("qa"))
			balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);	
		else
			Generic.userNotLoggedInToApp(driver, data);
		
		Generic.wait(5); // Preceded by Merchant Static Dynamic check with navigate back
		Generic.switchToMerchant(driver);

		MerchantHomePage mhp = new MerchantHomePage(driver);						
		mhp.executePayment(data);
		mhp.selectApp(programName);	
		
		if(mhp.checkSecurePinOccurence())
		{
		   mhp.enterSecurePin(data);
		   Generic.merchantVerifiedLogin(driver, data);
		}		
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);

		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);

		transactionStatusMsg=mhp.verifyMerchantSuccess();
		
		
		if(!checkEnv("qa"))
		{
			Generic.switchToAppWithState(driver);
			
			AddSendPage asp=new AddSendPage(driver);
			asp.refreshTransactionList();
			
			double balanceAfterTransaction=asp.verifyBalance();
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt*0.01,balanceAfterTransaction);		
			
			CSR.verifyIAPTXNDetails(transactionStatusMsg);
			CSR.verifyIAPTransactionDataPickupFailure(transactionStatusMsg); //IAPTS_018
			
			Generic.switchToMerchantWithState(driver);
		}
		
		mhp.performDataPickup();
		
		CSR.verifyIAPTransactionDataPickupSuccess(transactionStatusMsg); //IAPTS_017	
	}
}
