package wibmo.app.testScripts.IAPV2;

import library.CSR;
import library.Generic;
import library.Log;
import java.lang.reflect.Method;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class IAPV2AS_011 Group Execute : V1-IAP with ITPP & CVV Y,V2-IAP with ITPP & CVV N,Taxi-IAP with ITPP & CVV U
 *
 * IAPV2AS_012 : V1-IAP with ITPP & CVV Y
 * IAPV2AS_013 : V2-IAP with ITPP & CVV N
 * IAPV2AS_014 : Taxi-IAP with ITPP & CVV U
 * 
 * IAP txn - ITP.
 */
public class IAPV2AS_014 extends BaseTest // ==== IAP txn - ITP ==== //
{	
	/** The tc. */
	public String TC=getTestScenario("IAPV2AS_014");	
	public String data,cardDetails,cvvStatus="No",cvv,chargeStatus,chargeAmt;
	public double balanceBeforeTransaction;
	public int prevTcStatus=2;
	double amt;
	
	
	@BeforeMethod(dependsOnMethods="startMethod")
	public void navigate(Method  testMethod)
	{
		// ==== Initialize ==== //
		tcId=testMethod.getName();
		data=getTestData(tcId);
		
		Log.info("======== Group execution : "+tcId+" : "+getTestScenario(tcId)+" ========");
		
		String[] values=data.split(",");
		int valueSize=values.length;
		
		amt=Double.parseDouble(data.split(",")[2]);
		cardDetails=data.split(",")[3];	
		cvvStatus=Generic.parseCVVStatus(data);
		chargeStatus=valueSize>6?values[8]:"chargeStatusTrue"; //match data for V1 and V2
		chargeAmt=valueSize>9?values[9]:"200";
		
		// ==== Navigate ====//
		
		//if(prevTcStatus != 2) return; 
		
		setITP(true);
		
		Generic.switchToApp(driver);
		
		balanceBeforeTransaction = Generic.checkWalletBalance(driver, data); // Flow generalised to both Env.
		Generic.preconditionITP(driver, data);
		driver.navigate().back();
		
		SettingsPage sp = new SettingsPage(driver);
		sp.gotoAddSendPage();			
	 }		
	
	@Test
	public void IAPV2AS_014() 
	{
		boolean cvvStatusFound=false;
		CSR.setCVVStatus(cardDetails, cvvStatus);
		
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); 		
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		if(cvvStatus.equals("Yes") || cvvStatus.equals("Unknown"))					
			cvvStatusFound=mp3p.enterCvv(cardDetails.split(":")[2]);
		
		mp3p.verifyCVVOccurence(cvvStatusFound, cvvStatus.equals("Yes") || cvvStatus.equals("Unknown"));
		
		mhp.verifyITP();
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
		
		if(Generic.containsIgnoreCase(chargeStatus, "True"))
		{	
			mhp.clickCheckStatusDataPickup(chargeStatus);
			mhp.enterChargeAmount(chargeAmt);
			mhp.checkSUF("\"pgStatusCode\" : \"50020\",\"authenticationSuccessful\" : true,\"chargeSuccessful\" : true");
		}
		if(Generic.containsIgnoreCase(cardDetails, programName))
		{
			Generic.switchToAppWithState(driver);	
			
			AddSendPage asp=new AddSendPage(driver);
			asp.refreshTransactionList();			
			double balanceAfterTransaction=asp.verifyBalance();
			
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,(Double.parseDouble(chargeAmt))*0.01,balanceAfterTransaction);	
			balanceBeforeTransaction=balanceAfterTransaction; // Balance for next @Test			
		}
		
		CSR.verifyIAPTXNDetails(transactionStatusMsg);		
	}

}
