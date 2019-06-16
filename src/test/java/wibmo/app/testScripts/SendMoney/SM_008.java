package wibmo.app.testScripts.SendMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.libraries.Log;

import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.WelcomePage;

// TODO: Auto-generated Javadoc
/**
 * The Class SM_008 Send money from with insufficient funds opt to add money.
 */
public class SM_008 extends BaseTest // ==== Send money from wibmo a/c with insufficient funds and user opting to add money ==== //
{
	
	/** The tc. */
	public String TC=getTestScenario("SM_008");

	/**
	 * Sm 008.
	 */
	@Test
	public void SM_008()
	{
		Reporter.log(getTestScenario());
		int i=0;
		
		String data=getTestData("SM_008");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++];
		i=4;
		String addAmt=values[i++],cardDetails=values[i++],cardPin=values[i++];
		double currentBalance;
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,data);	
		
		HomePage hp=new HomePage(driver);
		hp.addSend();

		AddSendPage asp=new AddSendPage(driver);
		currentBalance=asp.verifyBalance();
		amtGreaterThanBal=currentBalance+Double.parseDouble(addAmt);
		asp.sendMoneyThroughMobile();		
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);		
		smp.addMoneyOnInsufficient();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.enterAmount(addAmt);			
		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		amscp.addMoney(cardDetails);
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);		
		am3p.addMoney3ds(cardPin);	
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyTransactionSuccess();	
		
		smp.verifyBalAfterAdd();	
		smp.cancelSend(); // To Prevent accidental large money transfer	
	}
	
	/**
	 * Reset amount greater than bal.
	 */
	@AfterMethod
	public void resetAmountGreaterThanBal()
	{
		amtGreaterThanBal=0;
	}
}
