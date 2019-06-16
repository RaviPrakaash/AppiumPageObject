package wibmo.app.testScripts.AddMoney;

import library.Generic;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;

// TODO: Auto-generated Javadoc
/**
 * 
 * The Class AM_034 Load money with Invalid card number, blank card number & blank alias, and click on back button
 * AM_024	Invalid card number while loading money
 * AM_026	Load money giving blank alias name
 * AM_027	Load money using blank card number
 * AM_031	Click on app's back button when on the load money details entry page
 *
 */
public class AM_034 extends BaseTest 
{
	
	/** The tc. */
	public String TC=getTestScenario("AM_034");
	
	/**
	 * Am 024.
	 */
	@Test
	public void AM_034() 
 	{ 
		Reporter.log(getTestScenario());
		groupExecute=true;
		
		int i=0;
		String data=getTestData("AM_024");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++],amt=values[i++],cardDetails=values[i++]; 
		
		double balanceBeforeTransaction=Generic.checkWalletBalance(driver, data);
		
		AddSendPage asp=new AddSendPage(driver);
		asp.clickLoadMoney();
		
		AddMoneyPage am=new AddMoneyPage(driver);
		am.enterAmount(amt);	
		
		// ---------------- AM_024	Invalid card number while loading money --------------- //
		
		setGroupValue("AM_024");		
		AddMoneySelectCardPage amscp=new AddMoneySelectCardPage(driver);
		amscp.addMoneyWithNewCard(cardDetails);
		amscp.verifyInvalidCardNumber(cardDetails);	
		
		// ---------------- AM_026	Load money giving blank alias name ---------------- //		
		
		setGroupValue("AM_026");		
		cardDetails=getTestData("AM_026").split(",")[3];
		amscp.addMoneyWithNewCard(cardDetails);
		amscp.verifyBlankCardAlias(cardDetails);
		
		// ---------------- AM_027	Load money using blank card number ---------------- //
		
		setGroupValue("AM_027");		
		cardDetails=getTestData("AM_027").split(",")[3];
		amscp.addMoneyWithNewCard(cardDetails);
		amscp.verifyBlankCardNumber(cardDetails);
		
		// ----------------- AM_031	Click on app's back button when on the load money detail entry page ---------------- //
		
		setGroupValue("AM_031");
		amscp.navigateBack();
		double balanceAfterTransaction=asp.verifyBalance();		
		Generic.verifyBalanceAdded(balanceBeforeTransaction,0,balanceAfterTransaction);	 // Check balance unchanged		
		
 	}
	/**
	 * Indicates completion of Group execution
	 * 
	 */
	@AfterMethod
	public void completeGroupExecution()
	{
		groupExecute=false;
	}
}
