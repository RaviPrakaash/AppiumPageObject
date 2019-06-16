package wibmo.app.testScripts.SendMoney;

import library.Generic;
import library.Log;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.ProgramCardPage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SM_046 Lock card & send money* 
 * 
 */
public class SM_046 extends BaseTest // ==== Verify Recipient balance after Send Money ==== //
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
	
	@AfterMethod
	public void postconditionUnlockCards()
	{
		try
		{	
			//if(!senderCardUnlockStatus)			
				// Generic.unlockUserWalletCard(driver,senderLoginId,senderSecurePin);			
			
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
