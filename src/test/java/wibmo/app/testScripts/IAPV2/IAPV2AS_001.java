package wibmo.app.testScripts.IAPV2;

import library.CSR;
import library.Generic;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class IAPV2AS_001 Group Execute 
 * 
 * IAPV2AS_002 IAP V1 3DS txn with CVV N
 * IAPV2AS_003 IAP V2 3DS txn with CVV U
 * IAPV2AS_004 IAP Taxi 3DS txn with CVV Y
 *
 */
public class IAPV2AS_001 extends BaseTest 
{	
	/** The tc. */
	public String TC=getTestScenario("IAPV2AS_001");
	public String data,cardDetails;
	public double balanceBeforeTransaction;	
	
	@BeforeMethod(dependsOnMethods="startMethod")
	public void navigate(Method  testMethod)
	{
		tcId=testMethod.getName();
		data=getTestData(tcId);
		
		cardDetails=data.split(",")[3];			
		
		if( Generic.containsIgnoreCase(cardDetails, programName) && !checkEnv("qa"))
		{	
			if(!checkUserSame()) Generic.logout(driver);
			
			if(!Generic.checkAddSend(driver)); 
			{
				Generic.switchToApp(driver); // replace this by integrating shortcut into checkAddSend
				WelcomePage wp=new WelcomePage(driver);
				wp.changeUserTemp(data);
				balanceBeforeTransaction=Generic.checkWalletBalance(driver, data);
			}			
		}
		else
		{
			if(prevTcId.isEmpty() || !checkUserSame()) // First TC or not same user then populate (check switchToApp())
			{
				Generic.switchToApp(driver);
				WelcomePage wp=new WelcomePage(driver); //check switchToApp() 
				wp.changeUserTemp(data);
				Generic.userNotLoggedInToApp(driver, data);
			}			
		}
	 }	
	
	
	
	@Test // IAP V1  txn – 3DS with CVV N
	public void IAPV2AS_002() 
	{
		double amt=Double.parseDouble(data.split(",")[2]);			
		
		CSR.setCVVStatus(cardDetails, "No");
		Generic.switchToMerchant(driver);		
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); // programName will be initialised from config.xls file		
		
		if( !Generic.containsIgnoreCase(cardDetails, programName))
		{
			mhp.enterSecurePin(data);		
			Generic.merchantVerifiedLogin(driver,data);
		}
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);	
		
		String transactionStatusMsg=mhp.verifyMerchantSuccess();			
		
		if(Generic.containsIgnoreCase(cardDetails, programName) && !checkEnv("qa"))
		{
			Generic.switchToAppWithState(driver);	
			
			AddSendPage asp=new AddSendPage(driver);
			asp.refreshTransactionList();			
			double balanceAfterTransaction=asp.verifyBalance();
			
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt*0.01,balanceAfterTransaction);		
			Generic.switchToMerchantWithState(driver);
		}		
		
		mhp.performDataPickup("SUCCESS,\"pgStatusCode\" : \"50020\"");
		
		CSR.verifyIAPTXNDetails(transactionStatusMsg);		
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
	
	
	@Test // IAP Taxi txn – 3DS with CVV Y
	public void IAPV2AS_004()
	{
		String chargeStatus=data.split(",")[8];
		CSR.setCVVStatus(cardDetails, "Yes");
		double amt=Double.parseDouble(data.split(",")[2]);
		String chargeAmt=data.split(",")[9];
		
		
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
		mhp.enterChargeAmount(((int)amt)+"");
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
		mhp.enterChargeAmount(chargeAmt);
		mhp.checkSUF("\"pgStatusCode\" : \"50020\",\"authenticationSuccessful\" : true,\"chargeSuccessful\" : true");
		
		if(Generic.containsIgnoreCase(cardDetails, programName))
		{
			Generic.switchToAppWithState(driver);	
			
			AddSendPage asp=new AddSendPage(driver);
			asp.refreshTransactionList();			
			double balanceAfterTransaction=asp.verifyBalance();
			
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,(Double.parseDouble(chargeAmt))*0.01,balanceAfterTransaction);		
			Generic.switchToMerchantWithState(driver);			 
		}	
		
		// ==== Verify Txn report in CSR ==== //
		
		CSR.verifyIAPTXNDetails(transactionStatusMsg);
		
	}	
	
	
	@AfterMethod
	public void setFlow(ITestResult iRes)
	{
		prevTcId=tcId;
		prevTcStatus=iRes.getStatus();		
	}
	
}
