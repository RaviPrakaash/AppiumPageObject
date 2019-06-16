package wibmo.app.testScripts.SendMoney;

import library.CSR;
import library.Generic;
import library.Log;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.ProgramCardPage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SM_045 Group Execute : Lock Sender & Beneficiary card and send money
 * 
 * SM_046 Lock card & send money
 * SM_047 Block beneficiary card and send money 
 * 
 */
public class SM_045 extends BaseTest // ==== Verify Recipient balance after Send Money ==== //
{	
	/** The tc. */
	public String TC=getTestScenario("SM_045");
	String senderLoginId,senderSecurePin,recipientLoginId,recipientSecurePin;
	double recipientBalanceBeforeTransaction,recipientBalanceAfterTransaction;
	double senderBalanceBeforeTransaction,senderBalanceAfterTransaction;
	boolean senderCardUnlockStatus,recipientCardUnlockStatus;
	String data;
	double amt;

	public double blockUserCardAndVerifyBalance(String loginId,String securePin)
	{
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,loginId);	
		
		HomePage hp=new HomePage(driver);
		hp.addSend();

		AddSendPage asp=new AddSendPage(driver);
		double walletBalance=asp.verifyBalance();	
		
		asp.gotoProgramCard();
		ProgramCardPage pcp=new ProgramCardPage(driver);
		pcp.lockCard();
		
		driver.navigate().back();
		
		return walletBalance;			
	}
	
	@Test
	public void SM_046()
	{
		Reporter.log(getTestScenario());
		int i=0;
		data=getTestData("SM_046");
		String[] values=data.split(",");
		 senderLoginId=values[i++];
		 senderSecurePin=values[i++];i++;
		 recipientLoginId=values[i].split(":")[0];
		 recipientSecurePin=values[i++].split(":")[1];
		 amt=Double.parseDouble(values[i++]);
		
		// ---------------- Check Recipient Balance before transaction ---------------- //
		
		recipientBalanceBeforeTransaction=blockUserCardAndVerifyBalance(recipientLoginId,recipientSecurePin);
		
		Generic.logout(driver);		
		
		// ---------------------- Send Money --------------------- //
		
		senderBalanceBeforeTransaction=blockUserCardAndVerifyBalance(senderLoginId,senderSecurePin);
		
		AddSendPage asp=new AddSendPage(driver);		
		asp.sendMoneyThroughMobile();
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		
		smp.verifyFundTransferSuccessMsg();
		senderBalanceAfterTransaction=asp.verifyBalance();
		
		asp.gotoProgramCard();
		ProgramCardPage pcp=new ProgramCardPage(driver);
		senderCardUnlockStatus=pcp.unlockCard();
		
		Generic.verifyBalanceDeduct(senderBalanceBeforeTransaction, amt, senderBalanceAfterTransaction);
		Generic.logout(driver);
		
	}
	
	@Test(dependsOnMethods="SM_046")
	public void SM_047()
	{
		// ---------------- Check blocked Recipient Balance ---------------- //
		Log.info("================ Under group execution , continued from  TC SM_046 ===============");
		recipientBalanceAfterTransaction=Generic.checkWalletBalance(driver,recipientLoginId+','+recipientSecurePin);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.gotoProgramCard();
		
		ProgramCardPage pcp=new ProgramCardPage(driver);
		recipientCardUnlockStatus=pcp.unlockCard();
		
		Generic.verifyBalanceAdded(recipientBalanceBeforeTransaction, amt, recipientBalanceAfterTransaction);		
	}
	
	@AfterClass
	public void postconditionUnlockCards()
	{
		try
		{	
			if(!senderCardUnlockStatus)
			{
				/* if(!CSR.unblockLinkedCard(senderLoginId))
					Generic.unlockUserWalletCard(driver,senderLoginId,senderSecurePin);*/			
			}
			if(!recipientCardUnlockStatus)
			{
				/*if(!CSR.unblockLinkedCard(recipientLoginId))
					Generic.unlockUserWalletCard(driver,recipientLoginId,recipientSecurePin);*/
			}
		}
		catch(Exception e)
		{
			System.out.println("WARNING : User card was not unlocked\n"+e.getMessage());
		}
		catch(AssertionError ae)
		{
			System.out.println("WARNING : User card was not unlocked\n"+ae.getMessage());
		}
	}
	

}
