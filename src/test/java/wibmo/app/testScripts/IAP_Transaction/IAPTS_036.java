package wibmo.app.testScripts.IAP_Transaction;


import library.CSR;
import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wibmo.app.testScripts.BaseTest1;
import wibmo.webapp.pagerepo.*;

/**
 * The Class IAPTS_036 Web-SDK IAP transaction cancellation at login,phone verification,card selection,3DS fail,3DS Cancel
 * IAPTS_020 Web-SDK IAP transaction cancellation in login
 * IAPTS_021 Web-SDK IAP transaction cancellation in phone verification
 * IAPTS_022 Web-SDK IAP transaction cancellation in card selection
 * IAPTS_029 Web-SDK IAP transaction status when 3ds fails
 * IAPTS_024 Web-SDK IAP transaction status in cancellation 3ds screen
 *  
 */
public class IAPTS_036 extends BaseTest {

	/** The tc. */
	public String TC=getTestScenario("IAPTS_036");
	
	
	@BeforeMethod
	public void launchApplication() 
	{		   
	   launchAsWebDriver();
	}	
	
	/**
	 * Iapts 036.
	 */
	@Test
	public void IAPTS_036()	//"Web-SDK IAP transaction cancellation in login"
	{
		Reporter.log(getTestScenario());
		
		// ---------------- IAPTS_020 Web-SDK IAP transaction cancellation in login ---------------- //
		
		String data=getTestData("IAPTS_029"); // IAPTS_029 TestData common to all TCs
		setGroupValue("IAPTS_020");
		
		String str[]=data.split(",");
		
		String balanceMobileNumber=str[1];
		double balanceBeforeTransaction=0.0,balanceAfterTransaction=0.0;
		
		if(!checkEnv("qa"))
			 balanceBeforeTransaction=CSR.verifyBalance(str[1]);
		
		MerchantMainPage mmp=new MerchantMainPage(driver);
		mmp.basicInfoSubmit(data);
		
		MerchantCheckOutPage mcop=new MerchantCheckOutPage(driver); 
		mcop.merchantCheckout(data);
		
		ConnectingPhonePage cpp=new ConnectingPhonePage(driver);
		cpp.clickonskip();
		
		PayZappLoginPage pzlp=new PayZappLoginPage(driver);
		pzlp.cancellogin();
		
		IAPPostReportPage iprp=new IAPPostReportPage(driver);		
		String txnId=iprp.fetchwibmoid();			
		 	
		if(!checkEnv("qa"))
		{
			balanceAfterTransaction=CSR.verifyBalance(balanceMobileNumber);
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);		
		}
		
		CSR.verifyStatusOnTxnId(txnId,"204","User Aborted");
		
		iprp.testAgain();		
		
		// ---------------- IAPTS_021 Web-SDK IAP transaction cancellation in phone verification ---------------- //
		setGroupValue("IAPTS_021");
		
		String loginPassword=data.split(",")[4];
		mmp.basicInfoSubmit(data);
		
		mcop.merchantCheckout(data);
		
		cpp.clickonskip();
		
		pzlp.addlogindetails(loginPassword);
		
		DeviceVerificationPage dvp=new DeviceVerificationPage(driver); //+
		dvp.dvccancel();
		
		iprp=new IAPPostReportPage(driver);
		
		txnId=iprp.fetchwibmoid();
		
		balanceAfterTransaction=CSR.verifyBalance(balanceMobileNumber);	
		if(!checkEnv("qa"))
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction); 
		
		CSR.verifyStatusOnTxnId(txnId,"204","User Aborted");
		
		iprp.testAgain();	
		
		// ---------------- IAPTS_022 Web-SDK IAP transaction cancellation in card selection ---------------- //
		setGroupValue("IAPTS_022");
		
		mmp.basicInfoSubmit(data);
		
		mcop.merchantCheckout(data);
		
		cpp.clickonskip();
		
		pzlp.addlogindetails(loginPassword);		
		
		String dvcotp=Generic.getOTP(balanceMobileNumber,bankCode,Generic.getPropValues("DVC", BaseTest1.configPath)); // +
		dvp.adddvcdetails(dvcotp);
		
		TestMerchantPage tmp=new TestMerchantPage(driver); // +
		tmp.cancelcardselection();
		
		txnId=iprp.fetchwibmoid();		
		
		balanceAfterTransaction=CSR.verifyBalance(balanceMobileNumber);	
		if(!checkEnv("qa"))
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		
		CSR.verifyStatusOnTxnId(txnId,"204","User Aborted");
		
		iprp.testAgain();
		
		// ---------------- IAPTS_029 Web-SDK IAP transaction status when 3ds fails ---------------- //
		
		setGroupValue("IAPTS_029");
		
		mmp.basicInfoSubmit(data);
		
		mcop.merchantCheckout(data);
		
		cpp.clickonskip();
		
		pzlp.addlogindetails(loginPassword);		
		
		dvcotp=Generic.getOTP(balanceMobileNumber,bankCode,Generic.getPropValues("DVC", BaseTest1.configPath)); // +
		dvp.adddvcdetails(dvcotp);
		
		tmp.addtestmerchantdetails(str[5]);
		if(Generic.checkWalletITPDirect(str[5])) return;
		
		Merchant3DSPage m3dsp=new Merchant3DSPage(driver);
		if(!checkEnv("qa")) // QA TestCard may get blocked for wrong pin, wallet Card not recognised in QA Env
		{		
			
			m3dsp.submitVisaVerification(str[6]);
			m3dsp.verifyAuthenticationFailed();
			
		}
		// ---------------- IAPTS_024 Web-SDK IAP transaction status in cancellation 3ds screen ---------------- //
		setGroupValue("IAPTS_024");
		if(!Generic.checkWalletITPDirect(str[5]))
			m3dsp.cancelVisaVerification();
		
		txnId=iprp.fetchwibmoid();		
		
			
		if(!checkEnv("qa") && Generic.containsIgnoreCase(str[5], programName))
		{
			balanceAfterTransaction=CSR.verifyBalance(balanceMobileNumber);
			Generic.verifyBalanceDeduct(balanceBeforeTransaction,0,balanceAfterTransaction);
		}
		CSR.verifyStatusOnTxnId(txnId,"043","3DS Failed ");			
	}
	/**
	 * Indicates completion of Group execution
	 * 
	 */
	@AfterMethod
	public void completeGroupExecution()
	{
		groupExecute=false;
	}
	
}
