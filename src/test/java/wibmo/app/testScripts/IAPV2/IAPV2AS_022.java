package wibmo.app.testScripts.IAPV2;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import library.Generic;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.ManageCardsPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.pagerepo.WibmoSDKPage;

/**
 * The Class IAPV2AS_002 IAP V1  txn – 3DS with CVV N
 */
public class IAPV2AS_022 extends BaseTest // ==== Normal Merchant AmountKnownTrue ChargeLaterFalse ChargeOnCheckFalse ==== //
{		
	/** The tc. */
	public String TC=getTestScenario("IAPV2AS_002");
	public String data,cardDetails;
	public double balanceBeforeTransaction;	
	
	
	public void navigate()
	{
		data=getTestData("IAPV2AS_022");
		String loginId=data.split(",")[0],securePin=data.split(",")[1];
		
		cardDetails=data.split(",")[3];	
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,data);
		
		HomePage hp=new HomePage(driver);
		hp.verifyPageTitle();	
		
	 }	
	
	@Test // IAP V1  txn – 3DS with CVV N
	public void IAPV2AS_002() 
	{
		navigate();
		
		Generic.switchToMerchant(driver);		
		
		MerchantHomePage mhp=new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectApp(programName); 		
		
		WibmoSDKPage wsp=new WibmoSDKPage(driver);
		wsp.approvePaymentNewCard(cardDetails);
		
		// Adjust CardDetails data Ex :  4799470174910036:10:2021:1234:041 => 4799470174910036:1234:041:1234:041
		cardDetails=cardDetails.replace(cardDetails.split(":")[1], cardDetails.split(":")[3]).replace(cardDetails.split(":")[2], cardDetails.split(":")[4]);
						
		MerchantPayment3DSPage mp3p=new MerchantPayment3DSPage(driver);
		mp3p.executeMerchantPayment(cardDetails);	
		
		mhp.verifyMerchantSuccess();
		
	}
	
	@AfterMethod
	public void deleteCard()
	{
		try{
			MerchantHomePage mhp=new MerchantHomePage(driver);
			mhp.executePayment(data);
			mhp.selectApp(programName);
			
			WibmoSDKPage wsp=new WibmoSDKPage(driver);
			wsp.gotoManageCards();
			
			ManageCardsPage mcp=new ManageCardsPage(driver);
			mcp.deleteCurrentCard();
		}
		catch(Exception e){ System.out.println("Unable to delete card ");e.printStackTrace();}
			
		
	}
	
	
}
