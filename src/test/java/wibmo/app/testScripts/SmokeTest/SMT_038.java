package wibmo.app.testScripts.SmokeTest;

import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import library.CSR;
import library.Generic;
import wibmo.app.pagerepo.MerchantCardSelectionPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantLoginPage;
import wibmo.app.pagerepo.MerchantPayment3DSPage;
import wibmo.app.pagerepo.MerchantRegisterPage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class SMT_038.	IAP V1 – Web Overlay – Guest User
 */
public class SMT_038 extends BaseTest  
{		
	@BeforeMethod
	public void launchApplication() 
	{		
		//launchApp(Generic.getPropValues("MERCHANTPACKAGE",BaseTest1.configPath), Generic.getPropValues("MERCHANTACTIVITY",BaseTest1.configPath));
		clearApp();
	}
	
	/**
	 * smt 038.
	 */
	@Test
	public void SMT_038()
	{
		Reporter.log(getTestScenario());
		String data=getTestData("SMT_038");
		String cardDetails=data.split(",")[4],cvv="";
		String cardPin=cardDetails.split(":")[1];		
		
		if(cardDetails.split(":").length>2) cvv=cardDetails.split(":")[2];
		
		//String loginId=data.split(",")[0],cardDetails=data.split(",")[3],transactionStatusMsg;		
		String transactionStatusMsg;
		
		//Generic.switchToMerchant(driver);
		
		MerchantHomePage mhp = new MerchantHomePage(driver);
		mhp.executePayment(data);
		mhp.selectWithoutApp();

		MerchantRegisterPage mrp = new MerchantRegisterPage(driver);
		mrp.enterValues(data);

		MerchantPayment3DSPage mp3p = new MerchantPayment3DSPage(driver);
		mp3p.submitPayment(cardPin);
		mp3p.enterCvv(cvv);
		
		mhp.verifyRemindMeLink();		
		
		transactionStatusMsg=mhp.verifyMerchantSuccess();		
		
		CSR.verifyIAPTXNDetails(transactionStatusMsg);
	}
}
