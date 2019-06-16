package wibmo.app.testScripts.IAPV2;

import library.CSR;
import library.Generic;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class IAPV2AS_003 IAP V2  txn – 3DS with CVV U
 */
public class IAPV2AS_003 extends BaseTest // ==== IAP V2  txn – 3DS with CVV U ==== //
{		

	public String TC=getTestScenario("IAPV2AS_003");
	public String data,cardDetails;
	public double balanceBeforeTransaction;	
	
	@BeforeMethod
	public void navigate(Method  testMethod)
	{
		tcId=testMethod.getName();
		data=getTestData(tcId);
		
		cardDetails=data.split(",")[3];	
		
		WelcomePage wp=new WelcomePage(driver);
		wp.changeUserTemp(data);
		
		if( Generic.containsIgnoreCase(cardDetails, programName) && !checkEnv("qa"))
		{			
				//Generic.switchToApp(driver); // replace this by integrating shortcut into checkAddSend				
				balanceBeforeTransaction=Generic.checkWalletBalance(driver, data);
		}			
		else
		{			
				//Generic.switchToApp(driver);				
				Generic.userNotLoggedInToApp(driver, data);					
		}
	 }
	
	@Test // IAP V2  txn – 3DS with CVV U
	public void IAPV2AS_003()
	{
		String chargeStatus=data.split(",")[8];
		CSR.setCVVStatus(cardDetails, "Unknown");
		double amt=Double.parseDouble(data.split(",")[2]);
		
		
		// ==== Perform IAP Transaction ==== //
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName initialised from config
		
		if(!Generic.containsIgnoreCase(cardDetails, programName) && mhp.checkSecurePinOccurence())
		{
			mhp.enterSecurePin(data);		
			Generic.merchantVerifiedLogin(driver, data);   
		}
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.verifyMerchantPayment3DS();
		mp3p.submitPayment(cardDetails.split(":")[1]);
		mp3p.verifyCVV();
		mp3p.enterCvv(cardDetails.split(":")[2]);
		
		// ==== Verify Transaction Success ==== //
		String transactionStatusMsg=mhp.verifyMerchantSuccess();
		
		
		// ==== Charge On Status Check False and Verify Balance ==== // 
		
		mhp.clickCheckStatusDataPickup("chargeStatusFalse");
		mhp.checkSUF("\"pgStatusCode\" : null,\"authenticationSuccessful\" : true,\"chargeSuccessful\" : false");
		
		if(Generic.containsIgnoreCase(cardDetails, programName))
		{
			Generic.switchToAppWithState(driver);	
			
			AddSendPage asp=new AddSendPage(driver);
			asp.refreshTransactionList();			
			double balanceAfterTransaction=asp.verifyBalance();
			
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);		
			Generic.switchToMerchantWithState(driver);			 
		}
		
		// ==== Charge On Status Check True and Verify Balance ==== //
		
		mhp.clickCheckStatusDataPickup(chargeStatus);
		mhp.checkSUF("\"pgStatusCode\" : \"50020\",\"authenticationSuccessful\" : true,\"chargeSuccessful\" : true");
		
		if(Generic.containsIgnoreCase(cardDetails, programName))
		{
			Generic.switchToAppWithState(driver);	
			
			AddSendPage asp=new AddSendPage(driver);
			asp.refreshTransactionList();			
			double balanceAfterTransaction=asp.verifyBalance();
			
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt*0.01,balanceAfterTransaction);		
			Generic.switchToMerchantWithState(driver);			 
		}	
		
		// ==== Verify Txn report in CSR ==== //
		
		CSR.verifyIAPTXNDetails(transactionStatusMsg);		
	}
	
}
