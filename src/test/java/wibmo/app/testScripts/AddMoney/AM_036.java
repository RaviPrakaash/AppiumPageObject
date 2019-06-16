package wibmo.app.testScripts.AddMoney;

import library.CSR;
import library.Generic;
import library.Log;
import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.ProgramCardPage;
import wibmo.app.pagerepo.SettingsPage;

/**
 * The Class AM_036 Load Money with ITP with different CVV status
 * AM_037 Load Money ITP CVVY
 * AM_038 Load Money ITP CVVU
 * AM_039 Load Money ITP CVVN
 * 
 */
public class AM_036 extends BaseTest
{	
	/** The tc. */
	public String TC=getTestScenario("AM_036");	
	public String  tcId,data,amt,cardDetails,ownCardDetails="",cvvStatus,pinCvv;
	public String values[];
	boolean groupExecute,singleExecute; 
	double balanceBeforeTransaction=0.0;
	int prevTCStatus=2; // First TC
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{		
		tcId=testMethod.getName();
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("AM_036"))
		{	
			Log.info("======== Group execution "+tcId+" : "+getTestScenario(tcId)+" ========");
			groupExecute=true;		
		}
		if(testMethod.getName().equals(this.getClass().getSimpleName()))
			singleExecute=true;	
		
	}
	public void navigate()
	{		
		if(groupExecute==false && singleExecute==false) // (!groupExecute && !singleExecute)
			throw new SkipException(tcId+" Precondition/Environment/Resources were not available");		
		
		// ==== Initialize ==== //
		int i=2;
		data=getTestData(tcId);
		values=data.split(",");				
		amt=values[i++];
		cardDetails=values[i++];
		pinCvv=values[i++];
		
		// ==== Navigate ==== //		
		
		if(prevTCStatus==1) return;
		
		Generic.switchToApp(driver);
		setITP(true);
		balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);	
		Generic.preconditionITP(driver,data);		
		driver.navigate().back(); // Go to Settings Page
		
		SettingsPage sp=new SettingsPage(driver);
		
		//-------------- Get Program Card Details  ------------------//		
		if(Generic.containsIgnoreCase(cardDetails, internalITPCard) && !checkEnv("qa"))
		{			
			sp.gotoProgramCard();
			
			ProgramCardPage pcp=new ProgramCardPage(driver);
			ownCardDetails=pcp.getCardDetails();		
			
			pcp.gotoAddSendPage();
		}	
		else
			sp.gotoAddSendPage();
		//----------------------------------------------------------//
		
		
	}	
	
	@Test
	public void AM_037() 
	{		
		navigate();
		
		Reporter.log(getTestScenario());		
		
		cvvStatus=Generic.parseCVVStatus(data);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.clickLoadMoney();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.enterAmount(amt);	
		
		CSR.setCVVStatus(driver,cardDetails, cvvStatus);
		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		if(amscp.swipeToCard(cardDetails))
			amscp.addMoney(cardDetails);
		else		
			amscp.addMoneyWithNewCard(internalITPCard+':'+ownCardDetails);	
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);
		if(cvvStatus.equals("Yes") || cvvStatus.equals("Unknown"))
			am3p.verifyCVVOccurence(am3p.enterCvv(pinCvv), true); // cvvStatus=true for Yes & Unknown		
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyITP();
		amfp.verifyTransactionSuccess();
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		// Check for External ITP , otherwise ITP Program card balance will not change
		// If using own Programcard as an external ITP card , save Program Card as "OwnCard" under Manage Cards
		
		if((!cardDetails.toLowerCase().contains(internalITPCard.toLowerCase()) && !ownCardDetails.isEmpty()) || checkEnv("qa"))  
			Generic.verifyBalanceAdded(balanceBeforeTransaction,Double.parseDouble(amt), balanceAfterTransaction);	
		
		balanceBeforeTransaction=balanceAfterTransaction; // Group
	}
	
	@Test
	public void AM_038()
	{
		AM_037();
	}
	
	@Test
	public void AM_039()
	{
		AM_037();
	}
	
	@AfterMethod
	public void setStatus(ITestResult result)
	{
		prevTCStatus=result.getStatus();
		groupExecute=singleExecute=false;
	}	
	
}
