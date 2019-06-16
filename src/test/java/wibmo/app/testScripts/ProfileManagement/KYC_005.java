package wibmo.app.testScripts.ProfileManagement;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.KYCPage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.SettingsPage;

/**
 * The Class KYC_005 Validate KYC permissions for min KYC level
 * 
 */
public class KYC_005 extends BaseTest 
{	
	String mobileNo;
	
	@Test	
	public void KYC_005()
	{	
		String data=getTestData("KYC_005"); 
		String initialMobileNo=data.split(",")[0];
		
		int i=0;
		String[] values=data.split(",");
		 mobileNo=values[i++];
		 String securePin=values[i++],
					panNumber=values[i++],name=values[i++],dob=values[i++],panFilePath=values[i++],
					amt=values[i++],cardDetails=values[i++],cardPin=values[i++];	
		
		String newNumber=getNewlyRegisteredMobileNumber();
		
		if(newNumber.isEmpty())
			data=data.replaceAll(initialMobileNo, registerMobileNumber(mobileNo));
		else
			data=data.replaceAll(initialMobileNo, newNumber);
		
		double balanceBeforeTransaction,balanceAfterTransaction;
				
		// ========= Script Code ======== //
		balanceBeforeTransaction=checkWalletBalance(data);		
		
		AddSendPage asp=new AddSendPage(driver);
		asp.clickLoadMoney();
		asp.verifyKYCAlert();
		
		HomePage hp=new HomePage(driver);
		hp.gotoSettings();
		
		SettingsPage sp=new SettingsPage(driver);
		sp.gotoKYC(); 
		
		KYCPage kp=new KYCPage(driver);
		kp.enterPANDetails(panNumber, name, dob);	
		kp.verifyPANSuccessAlert();
		
		hp.addSend();
		asp.clickLoadMoney();
		
		asp.clickLoadMoney();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.handleKYCNoteAlert();
		am.enterAmount(amt);			
		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		amscp.addMoneyWithNewCard(cardDetails);
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);	
		am3p.addMoney3ds(cardPin);	
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyTransactionSuccess();
		
		asp.sendMoneyToBank();
		
		// Verify W2A Update KYC Message
		
	

	}	
	
	@AfterMethod
	private void postConditionUnregister()
	{
		// UnRegister User from the DB . 	
		
	}
	
}
