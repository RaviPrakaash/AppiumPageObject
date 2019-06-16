package wibmo.app.testScripts.ProfileManagement;

import org.testng.annotations.Test;
import library.Generic;
import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.KYCPage;
import wibmo.app.pagerepo.SettingsPage;

/**
 * The Class KYC_001 Update PAN/KYC Number and load money 
 * 
 */
public class KYC_001 extends BaseTest 
{	
	@Test	
	public void KYC_001()
	{	
		String data=getTestData("KYC_001"); 
		String initialMobileNo=data.split(",")[0];
		
		String newNumber=getNewlyRegisteredMobileNumber();
		
		if(newNumber.isEmpty())
			data=data.replaceAll(initialMobileNo, registerMobileNumber());
		else
			data=data.replaceAll(initialMobileNo, newNumber);
		
		int i=0;
		String[] values=data.split(",");
		String mobileNo=values[i++],securePin=values[i++],
					panNumber=values[i++],name=values[i++],dob=values[i++],panFilePath=values[i++],
					amt=values[i++],cardDetails=values[i++],cardPin=values[i++];			
		
		double balanceBeforeTransaction,balanceAfterTransaction;
		
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
		
		//kp.createPANFile(mobileNo, panNumber, panFilePath);
		
		// SFTP.send(panFilePath);
		// API.runJob(String jobName); // read api uri from config & replace jobName
		
		// logout();
		// checkWalletBalance(data);
		
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
		
		balanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceAdded(balanceBeforeTransaction, Double.parseDouble(amt), balanceAfterTransaction);		
		
		//KYC_002
		//-- > File prepare with N --- > Upload -- > Asynchronous Run Job ---- > logout ---> Relogin after 2 mins or whenever ready --- > Verify PAN validation fail msg ---> Skip Alert --- > 	asp.verifyKYCAlert(); on load money click
		
	}	
	
}
