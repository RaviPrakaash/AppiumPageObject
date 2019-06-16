package wibmo.app.testScripts.SendMoney;

import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddMobilePage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.ManageMobilePage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SM_038 Add new mobile number and send money.
 */
public class SM_038 extends BaseTest // ==== Add new mobile number and send money ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_038");

	/**
	 * Sm 038.
	 */
	@Test
	public void SM_038()
	{
		Reporter.log(getTestScenario());
		int i=0;
		String data=getTestData("SM_038");
		String[] values=data.split(",");
		String senderLoginId=values[i++],senderSecurePin=values[i++];i++;
		String recipientLoginId=values[i].split(":")[0],recipientSecurePin=values[i].split(":")[1],newMobileNo=values[i++].split(":")[2];
		double amt=Double.parseDouble(values[i++]);
		
		// ---------------- Check Recipient Balance before transaction ---------------- //
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(recipientLoginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(recipientSecurePin);
		
		Generic.verifyLogin(driver,recipientLoginId);	
		
		HomePage hp=new HomePage(driver);
		hp.addSend();

		AddSendPage asp=new AddSendPage(driver);
		double recipientBalanceBeforeTransaction=asp.verifyBalance();	
		
		
		
		// ---------------- Add new Mobile number to recipient ---------------- //
		
		asp.gotoSettings();
		
		SettingsPage sp=new SettingsPage(driver);		
		sp.gotoManageMobile();
		
		ManageMobilePage mmp=new ManageMobilePage(driver);
		mmp.verifyNumberAbsent(newMobileNo);		
		mmp.add();
		
		AddMobilePage amp=new AddMobilePage(driver);
		amp.addMobile(newMobileNo);		
		mmp.verifyNumberPresent(newMobileNo);
		driver.navigate().back();
		Generic.logout(driver);
		
		
		// ---------------------- Send Money --------------------- //
		
		wp.selectUser(senderLoginId);		
		lnp.login(senderSecurePin);		
		Generic.verifyLogin(driver,data);		
		hp.addSend();
		asp.sendMoneyThroughMobile();
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();
		Generic.wait(3);
		Generic.logout(driver);
		
		// ---------------- Check Recipient Balance ---------------- //
		
		wp.selectUser(recipientLoginId);		
		lnp.login(recipientSecurePin);		
		Generic.verifyLogin(driver,recipientLoginId);		
		hp.addSend();
		double recipientBalanceAfterTransaction=asp.verifyBalance();
		
		Generic.verifyBalanceAdded(recipientBalanceBeforeTransaction,amt,recipientBalanceAfterTransaction);		
	}
}
