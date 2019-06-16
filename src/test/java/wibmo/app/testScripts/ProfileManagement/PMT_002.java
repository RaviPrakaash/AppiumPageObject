package wibmo.app.testScripts.ProfileManagement;

import library.CSR;
import library.Generic;
import java.lang.reflect.Method;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.ManageProfilePage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class PMT_002 Change name to 31 char and do ITP Payment
 * 
 */
public class PMT_002 extends BaseTest 
{	
	/** The tc. */
	public String TC=getTestScenario("PMT_002");	
	
	public String data,cardDetails,chargeStatus;
	public double balanceBeforeTransaction;
	double amt;	
	

	public void navigate()
	{
		// ==== Initialize ==== //
		
		data=getTestData("PMT_002");		
		
		String[] values=data.split(",");
		int valueSize=values.length;
		
		amt=Double.parseDouble(data.split(",")[2]);
		cardDetails=data.split(",")[3];	
		chargeStatus=valueSize>6?values[8]:"chargeStatusTrue"; //match data for V1 and V2
		
		
		// ==== Navigate ====//		
		
		setITP(true);		
		balanceBeforeTransaction = Generic.checkWalletBalance(driver, data); // Flow generalised to both Env.
		Generic.preconditionITP(driver, data);
		driver.navigate().back();			
		
		// ==== Update User Profile ==== //
		String profileData=data.split(",")[0]+','+data.split(",")[1]+','+data.split("\\|")[1]; //userName,securePin,profileData
		
		SettingsPage sp = new SettingsPage(driver);
		sp.gotoManageProfile();
		
		ManageProfilePage mpp = new ManageProfilePage(driver);
		mpp.enterValuesToUpdateProfile(profileData);
		sp.gotoAddSendPage();		
	 }		
	
	@Test
	public void PMT_002() 
	{
		navigate();
		Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); 		
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);		
		
		mhp.verifyITP();
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
		
		if(Generic.containsIgnoreCase(chargeStatus, "True"))
		{	
			mhp.clickCheckStatusDataPickup(chargeStatus);
			mhp.checkSUF("\"pgStatusCode\" : \"50020\",\"authenticationSuccessful\" : true,\"chargeSuccessful\" : true");
		}
		if(Generic.containsIgnoreCase(cardDetails, programName))
		{
			Generic.switchToAppWithState(driver);	
			
			AddSendPage asp=new AddSendPage(driver);
			asp.refreshTransactionList();			
			double balanceAfterTransaction=asp.verifyBalance();
			
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,amt*0.01,balanceAfterTransaction);	
		}
		
		CSR.verifyIAPTXNDetails(transactionStatusMsg);		
	}
	
	

}
