package wibmo.app.testScripts.AddMoney;

import library.CSR;
import library.Generic;
import library.Log;
import java.lang.reflect.Method;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;

/**
 * The Class AM_040 Group Execute : Load Money using 3DS with different CVV status
 * 
 * AM_041 Load money using using 3ds with CVV Y
 * AM_042 Load money using using 3ds with CVV U
 * AM_043 Load money using using 3ds with CVV N
 * 
 */
public class AM_040 extends BaseTest
{	
	/** The tc. */
	public String TC=getTestScenario("AM_036");	
	public String  tcId,data,amt,cardDetails,cvvStatus,pinCvv;
	public String values[];
	boolean groupExecute,singleExecute; 
	double balanceBeforeTransaction=0.0;
	int prevTCStatus=2; // First TC
	
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{		
		tcId=testMethod.getName();
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("AM_040"))
		{	
			Log.info("================ Group execution "+tcId+" : "+getTestScenario(tcId)+" ================");
			groupExecute=true;		
		}
		if(testMethod.getName().equals(this.getClass().getSimpleName()))
			singleExecute=true;			
	}
	
	public void navigate()
	{		
		if(groupExecute==false && singleExecute==false) // (!groupExecute && !singleExecute)
			throw new SkipException(tcId+" Not Selected");			
		
		// ==== Initialize ==== //
		
		int i=2;
		data=getTestData(tcId);
		values=data.split(",");				
		amt=values[i++];
		cardDetails=values[i++];
		pinCvv=values[i++];
		
		// ==== Navigate ==== //		
		
		if(prevTCStatus!=2) return;
		
		Generic.switchToApp(driver);
		balanceBeforeTransaction=Generic.checkWalletBalance(driver,data);				
	}	
	
	@Test
	public void AM_041() 
	{	
		navigate();
		Reporter.log(getTestScenario());		
		
		String cvvStatus=Generic.parseCVVStatus(data);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.clickLoadMoney();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.enterAmount(amt);	
		
		CSR.setCVVStatus(driver,cardDetails, cvvStatus);
		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		if(amscp.swipeToCard(cardDetails))
			amscp.addMoney(cardDetails);
		else		
			Assert.fail(cardDetails.split(":")[0]+" Card not found\n  ");	
		
		AddMoney3DSPage am3p=new AddMoney3DSPage(driver);		
		am3p.verifyCVVOccurence(am3p.addMoney3ds(pinCvv), cvvStatus.equals("Yes") || cvvStatus.equals("Unknown")); // cvvStatus=true for Yes & Unknown		
		
		AddMoneyFinalPage amfp=new AddMoneyFinalPage(driver);
		amfp.verifyTransactionSuccess();
		
		double balanceAfterTransaction=asp.verifyBalance();
		
		// Check for External ITP , otherwise ITP Program card balance will not change
		// If using own Programcard as an external ITP card , save Program Card as "OwnCard" under Manage Cards
		
		if(!Generic.containsIgnoreCase(cardDetails, programName) && !Generic.containsIgnoreCase(cardDetails, internalITPCard))  
			Generic.verifyBalanceAdded(balanceBeforeTransaction,Double.parseDouble(amt), balanceAfterTransaction);	
		
		balanceBeforeTransaction=balanceAfterTransaction; // Group 
	}
	
	@Test
	public void AM_042()
	{
		AM_041();
	}
	
	@Test
	public void AM_043()
	{
		AM_041();
	}
	
	@AfterMethod
	public void setStatus(ITestResult result)
	{
		prevTCStatus=result.getStatus();
		groupExecute=singleExecute=false;
	}
	
	@AfterClass
	public void logout()
	{
		com.libraries.Log.info("Logging out ");
		try{Generic.logout(driver);}catch(Exception e){com.libraries.Log.info("Warning : User not logged out ");}
	}

}
