package wibmo.app.testScripts.ProfileManagement;

import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.KYCPage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.SettingsPage;

/**
 * The Class KYC_002 Update PAN/KYC and verify PAN Seeding Failed.
 * 
 */
public class KYC_002 extends BaseTest 
{	
	@Test	
	public void KYC_002()
	{	
		String data=getTestData("KYC_002"); 
		String initialMobileNo=data.split(",")[0];
		
		int i=0;
		String[] values=data.split(",");
		String mobileNo=values[i++],securePin=values[i++],
					panNumber=values[i++],name=values[i++],dob=values[i++],panFilePath=values[i++],
					amt=values[i++],cardDetails=values[i++],cardPin=values[i++];			
		
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
		// ======================= //
		
		
		// ======== Integration Code ======== // 
		
		//	kp.enterPANDetails(panNumber, name, dob);	
		//kp.verifyPANSuccessAlert();
		
		//kp.createPANFile(mobileNo, panNumber, panFilePath,panStatus);
		
		// SFTP.send(panFilePath);
		// API.runJob(String jobName); // read api uri from config & replace jobName // TBI when Job is up 
		
		// wait for Run Job // reconfirm behaviour
		logout();
		
		// =====================// 
		 
		 LoginNewPage lnp=new LoginNewPage(driver);
		 lnp.login(securePin);
		 
		hp.verifyPANAlert();
		
		hp.addSend();
		asp.clickLoadMoney();
		asp.verifyKYCAlert();			
		

	}	
	
}
