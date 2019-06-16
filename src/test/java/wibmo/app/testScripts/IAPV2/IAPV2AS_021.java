package wibmo.app.testScripts.IAPV2;

import library.CSR;
import library.Generic;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.snowtide.pdf.C;

import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.ProgramCardPage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class IAPV2AS_021 IAP txn V2 - 3DS. Amount Known True, Charge Later True, ChargeOnCheckTrue. No Balance Verification(3DS external card)
 */
public class IAPV2AS_021 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("IAPV2AS_021");
	public String loginId;
	
	/**
	 * IAPV2AS_021.
	 */
	@Test
	public void IAPV2AS_021() 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPV2AS_021");
		 loginId=data.split(",")[0]; String securePin=data.split(",")[1];
		String cardDetails=data.split(",")[3];
		
		blockUserCard(loginId,securePin);
		
		Generic.switchToMerchant(driver);		
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); 	
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePayment(cardDetails);
		
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.submitPayment(cardDetails.split(":")[1]);
		mp3p.verifyCardBlockMessage();		
	}
	
	public void blockUserCard(String loginId,String securePin)
	{
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,loginId);	
		
		HomePage hp=new HomePage(driver);
		hp.goToProgramCard();
		
		ProgramCardPage pcp=new ProgramCardPage(driver);
		pcp.lockCard();				
	}
	
	@AfterMethod
	public void postconditionUnlockCard()
	{
		try
		{
			if(CSR.unblockLinkedCard(loginId)) return;
			Generic.switchToAppWithState(driver);
			ProgramCardPage pcp=new ProgramCardPage(driver);
			pcp.flipCard();
			pcp.unlockCard();			
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
