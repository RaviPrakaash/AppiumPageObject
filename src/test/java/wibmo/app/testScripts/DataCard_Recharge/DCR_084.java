package wibmo.app.testScripts.DataCard_Recharge;

import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.libraries.Log;
import library.Generic;
import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.RechargePage;

/**
 * The Class DCR_084 Select different types of contacts from Phone , contains the following TestScripts
 * 
 * DCR_003	Click on “Select From Contacts”
 * DCR_006	Click on “Select From Contacts” & Select respective Data card number & Operator
 * DCR_007	Wrong Data card number
 * DCR_008	Data card contact number with +
 * DCR_009	Data card contact number with 91
 * DCR_010	Data card contact number with 0
 * DCR_016	Enter Less than 10 Digits
 * 
 */
public class DCR_084 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DCR_084");
	
	public String tcId;
	public int prevTCStatus=5; // First TC random value
	public String data;
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{
		tcId=testMethod.getName();
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("DCR_084"))
		{	
			Log.info("================ Group execution "+tcId+" : "+getTestScenario(tcId)+" ================");
			groupExecute=true;		
		}		
	}
	
	public void navigate()
	{
		// ======== Initialize ======== //
		data=getTestData(tcId);		
		
		if(prevTCStatus==1) return; //1=Prev TC SUCCESS
		
		// ======== Navigate ======== //
		
		if(prevTCStatus<5)// Not First TC
			 Generic.switchToApp(driver);		
		
		gotoDataCardRecharge(data);			
	}
	
	
	@Test(priority=1)
	public void DCR_003() // ---------------- DCR_003	Click on “Select From Contacts” ---------------- //
	{ 		
		navigate();		
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.selectContacts(data);

		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.verifyDataCardNumber(data);
		
		dcrp.navigateBack();	
		
		RechargePage rp =new RechargePage(driver);
		rp.gotoDataCardRecharge();		
	}
	
	@Test(priority=2)
	public void DCR_006() // ---------------- DCR_006	Click on “Select From Contacts” & Select respective Data card number & Operator ---------------- //
	{
		navigate();		
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.selectContacts(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.verifyDataCardNumber(data);
		dcrp.verifyDataCardOperator(data);
		
		dcrp.navigateBack();
		
		RechargePage rp =new RechargePage(driver);
		rp.gotoDataCardRecharge();	
	}
	
	@Test(priority=3)
	public void DCR_007() // ---------------- DCR_007 Select Wrong Data card number from contacts ---------------- //
	{
		navigate();			
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.selectContacts(data);
		drp.verifyLessThan10DigitDataCardNumber();
		
	}
	
	@Test(priority=4)
	public void DCR_008() // ---------------- DCR_008	Data card contact number with + ---------------- //
	{	
		navigate();
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);		
		drp.selectContacts(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.verifyDataCardNumber(data);
		dcrp.navigateBack();
		
		RechargePage rp =new RechargePage(driver);
		rp.gotoDataCardRecharge();	
	}
	
	@Test(priority=5)
	public void DCR_009() // ---------------- DCR_009	Data card contact number with 91 ---------------- //
	{	
		navigate();	
				
		DataCardRechargePage drp = new DataCardRechargePage(driver);		
		drp.selectContacts(data);
				
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);	
		dcrp.verifyDataCardNumber(data);
				
		dcrp.navigateBack();
		
		RechargePage rp =new RechargePage(driver);
		rp.gotoDataCardRecharge();
	}
	
	@Test(priority=6)
	public void DCR_010() // ---------------- DCR_010	Data card contact number with 0 ---------------- //
	{		
		navigate();	
	
		DataCardRechargePage drp = new DataCardRechargePage(driver);			
		drp.selectContacts(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.verifyDataCardNumber(data);
		
		dcrp.navigateBack();
		
		RechargePage rp =new RechargePage(driver);
		rp.gotoDataCardRecharge();
	}
	
	@Test(priority=7)
	public void DCR_016() // ---------------- DCR_016	Enter Less than 10 Digits ---------------- //
	{		
		navigate();		
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);				
		drp.enterMobileNumber(data);
		drp.verifyLessThan10DigitDataCardNumber();
	}
	
	@AfterMethod
	public void setStatus(ITestResult result)
	{
		prevTCStatus=result.getStatus();
	}
	
	

}
