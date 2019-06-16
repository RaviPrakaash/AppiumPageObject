package wibmo.app.testScripts.SendMoney;

import library.Generic;
import library.Log;
import java.lang.reflect.Method;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.pagerepo.RegistrationSuccessfulPage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.UnclaimedFundsPage;
import wibmo.app.pagerepo.VerifyMobilePage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class SM_044 Verify Unclaimed funds page,claim prompt,incorrect code & blank code, contains the following scripts
 * 
 * SM_021	Send money to unregistered mobile no. & sign up the beneficiary, Verify Unclaimed funds page 
 * SM_022	Verify claim code prompt
 * SM_024	Verify Incorrect claim code 
 * SM_025	Verify Blank Unclaimed code 
 * 
 */
public class SM_044 extends BaseTest  
{	
	/** The tc. */
	public String TC=getTestScenario("SM_044");
	public String tcId;
	public String data;
	public String unclaimedUserNumber; 
	
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{
		tcId=testMethod.getName();
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("SM_044"))
		{	
			Log.info("================ Group execution "+tcId+" : "+getTestScenario(tcId)+" ================");
			groupExecute=true;		
		}	
	}
	
	@Test	
	public void SM_021()
	{			
		int i=0;
		
		data=getTestData("SM_021").split("\\|")[0]; // TestData for SM_021 is different from other grouped TestCases
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++]; 
		 unclaimedUserNumber=Generic.generateMobileNumber();
		 data=data.replace(data.split(",")[3],unclaimedUserNumber);
		
		
		// ======== Send Money to Unregistered number ======== //
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,data);	
		
		HomePage hp=new HomePage(driver);
		hp.addSend();

		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughMobile();		
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();
		
		Generic.logout(driver);
		Generic.wait(2);
		
		// ======== Register No. ========= //
		
		data=getTestData("SM_021").split("\\|")[1]; 
		data=data.replace(data.split(",")[0],unclaimedUserNumber);
		System.out.println("Registration data : "+data);
		
		String mobileNumber=data.split(",")[0];		
				  securePin=data.split(",")[8];
		
		gotoVerifyMobile();
		
		RegisterMobilePage rmp=new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNumber);
		
		RegisterPage rp=new RegisterPage(driver);
		rp.enterValues(data);
		
		VerifyMobilePage vmp= new VerifyMobilePage(driver);
		vmp.completeRegistration(mobileNumber,bankCode);
		
		RegistrationSuccessfulPage rsp=new RegistrationSuccessfulPage(driver);
		rsp.verifyRegistrationSuccess();
		
		rsp.gotoLogin();
		
		lnp.login(securePin);
		
		checkUnclaimed=true;
		Generic.verifyLogin(driver,data);
		
		UnclaimedFundsPage ufp=new UnclaimedFundsPage(driver);
		ufp.verifyUnclaimedFundsPage();				
	}	
	
	@Test(dependsOnMethods="SM_021")
	public void SM_022() // ---------------- SM_022	Verify claim code prompt ---------------- //
	{		
		UnclaimedFundsPage ufp=new UnclaimedFundsPage(driver);
		ufp.verifyClaimCodePrompt();
		ufp.navigateBack();		
	}
	
	@Test(dependsOnMethods="SM_022")
	public void SM_025() // ---------------- SM_025	Verify Blank Unclaimed code ---------------- //
	{
		UnclaimedFundsPage ufp=new UnclaimedFundsPage(driver);		
		ufp.verifyBlankClaimCode();	
		ufp.navigateBack();		
	}
	
	@Test(dependsOnMethods="SM_025")
	public void SM_024() // ---------------- SM_024	Verify Incorrect claim code ---------------- //
	{		
		String claimCode=getTestData(tcId).split(",")[2];
		
		UnclaimedFundsPage ufp=new UnclaimedFundsPage(driver);	
		ufp.enterClaimCode(claimCode);
		ufp.verifyIncorrectCode();			
	}	
	
	
}
