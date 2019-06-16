package wibmo.app.testScripts.IAPV2;


import org.testng.annotations.Test;
import library.CSR;
import library.Generic;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class IAPV2AS_002 IAP V1  txn – 3DS with CVV N
 */
public class IAPV2AS_002 extends BaseTest // ==== Normal Merchant AmountKnownTrue ChargeLaterFalse ChargeOnCheckFalse ==== //
{		
	/** The tc. */
	public String TC=getTestScenario("IAPV2AS_002");
	public String data,cardDetails;
	public double balanceBeforeTransaction;	
	
	
	public void navigate()
	{
		tcId=this.getClass().getSimpleName();
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
	
	@Test // IAP V1  txn – 3DS with CVV N
	public void IAPV2AS_002() 
	{
		navigate();
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
}
