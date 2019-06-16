package wibmo.app.testScripts.SendMoney;

import library.CSR;
import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SM_018 Verify Recipient balance after Sending Money to email.
 */
public class SM_018 extends BaseTest  // ==== Verify Recipient balance after Send Money to email ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_018");

	/**
	 * Sm 018.
	 */
	@Test
	public void SM_018()
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("SM_018");
		String[] values=data.split(",");
		String senderLoginId=values[i++],senderSecurePin=values[i++];i++;
		String recipientLoginId=values[i].split(":")[0],recipientSecurePin=values[i++].split(":")[1];
		double amt=Double.parseDouble(values[i++]);
		
		String recipientMobileNo=recipientLoginId.split(":").length>2?recipientLoginId.split(":")[2]:CSR.emailToMobile(recipientLoginId);
		
		// ---------------- Check Recipient Balance before transaction ---------------- //
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(recipientLoginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(recipientSecurePin);
		
		Generic.verifyLogin(driver,recipientMobileNo);	
		
		HomePage hp=new HomePage(driver);
		hp.addSend();

		AddSendPage asp=new AddSendPage(driver);
		double recipientBalanceBeforeTransaction=asp.verifyBalance();	
		Generic.logout(driver);
		
		// ---------------------- Send Money --------------------- //
		
		wp.selectUser(senderLoginId);		
		lnp.login(senderSecurePin);		
		Generic.verifyLogin(driver,data);		
		hp.addSend();
		asp.sendMoneyThroughEmail();
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();
		Generic.wait(3);
		Generic.logout(driver);
		
		// ---------------- Check Recipient Balance ---------------- //
		
		wp.selectUser(recipientLoginId);		
		lnp.login(recipientSecurePin);		
		Generic.verifyLogin(driver,recipientMobileNo);		
		hp.addSend();
		double recipientBalanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceAdded(recipientBalanceBeforeTransaction,amt,recipientBalanceAfterTransaction);		
	}

}
