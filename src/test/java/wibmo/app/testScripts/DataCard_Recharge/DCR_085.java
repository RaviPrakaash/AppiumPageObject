package wibmo.app.testScripts.DataCard_Recharge;

import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.libraries.Log;
import library.Generic;
import wibmo.app.pagerepo.DataCardRechargePage;
import wibmo.app.pagerepo.DataCardRechargePayPage;
import wibmo.app.pagerepo.RechargePage;

/**
 * The Class DCR_025 Click on Operator Drop down.
 * 
 * DCR_025	Click on Operator  Drop down (Prepaid)
 * DCR_026	Select any operator (Prepaid)
 * DCR_028	Without entering Amount, try to click on pay now button (Prepaid)
 * DCR_043	Try to enter > 5 digits in amount field
 * DCR_047	With Prepaid option, Select valid number, operator and amount and click on Cancel button
 * DCR_056	Click on Operator  Drop down (Postpaid)
 * DCR_057	Select any operator (Postpaid)
 * DCR_058	Without entering Amount, try to click on pay now button (Postpaid)
 * DCR_062	With Postpaid option, Select valid number, operator and amount and click on Cancel button
 *
 */
public class DCR_085 extends BaseTest
{
	
	/** The tc field. */
	public String TC=getTestScenario("DCR_085");
	public String tcId;
	public int prevTCStatus=5; // First TC random value
	public String data;
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{
		tcId=testMethod.getName();	
		
		
		
		if(this.getClass().getSimpleName().equals("DCR_085"))
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
	public void DCR_025() // ---------------- DCR_025	Click on Operator Drop down (Prepaid) ---------------- //
	{
		navigate();
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.enterMobileNumber(data);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);			
		dcrp.verifyDataCardOperatorList();		
		
		driver.navigate().back(); // Close List
	}
	
	@Test(dependsOnMethods="DCR_025")
	public void DCR_026() // ---------------- DCR_026	Select any operator (Prepaid) ---------------- //
	{
		data=getTestData(tcId);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);	
		dcrp.verifyOperatorSelect();
	}
	
	@Test(dependsOnMethods="DCR_026")
	public void DCR_028() // ---------------- DCR_028	Without entering Amount, try to click on pay now button (Prepaid) ---------------- //
	{	
		data=getTestData(tcId);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);	
		dcrp.enterValues(data);
		dcrp.verifyPayNowButtonDisable();
	}
	
	@Test(dependsOnMethods="DCR_028")
	public void DCR_043() // ---------------- DCR_043	Try to enter > 5 digits in amount field ---------------- //
	{
		
		data=getTestData(tcId);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);	
		dcrp.enterValues(data);
		dcrp.verifyGreaterThan5DigitAmt();
		
		dcrp.navigateBack(); // Navigate to Recharge Page
		
		RechargePage rp =new RechargePage(driver);
		rp.gotoDataCardRecharge(); // Navigate for next @Test DCR_056
	}
	
	@Test(enabled=false)
	public void DCR_047() // ---------------- DCR_047	With Prepaid option, Select valid number, operator and amount and click on Cancel button ---------------- //
	{
		data=getTestData(tcId);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);			
		dcrp.clickCancel();
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.verifyContactPage();		
	}
	
	@Test(dependsOnMethods="DCR_043")
	public void DCR_056() // ---------------- DCR_056	Click on Operator  Drop down (Postpaid) ---------------- //
	{		
		navigate();
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);	
		drp.enterMobileNumber(data);		
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);		
		dcrp.verifyDataCardOperatorList();
				
		driver.navigate().back(); // Close List			
	}
	
	@Test(priority=6,dependsOnMethods="DCR_056")
	public void DCR_057() // ---------------- DCR_057	Select any operator (Postpaid) ---------------- //
	{
		data=getTestData(tcId); 
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);	
		dcrp.verifyOperatorSelect();		
	}
	
	@Test(priority=7,dependsOnMethods="DCR_057")
	public void DCR_058() // ---------------- DCR_058	Without entering Amount, try to click on pay now button (Postpaid) ---------------- //
	{	
		data=getTestData(tcId);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);
		dcrp.enterValues(data);
		dcrp.verifyPayNowButtonDisable();

	}
	
	@Test(enabled=false)
	public void DCR_062() // ---------------- DCR_062	With Postpaid option, Select valid number, operator and amount and click on Cancel button ---------------- //
	{
		data=getTestData(tcId);
		
		DataCardRechargePayPage dcrp = new DataCardRechargePayPage(driver);		
		dcrp.clickCancel();
		
		DataCardRechargePage drp = new DataCardRechargePage(driver);
		drp.verifyContactPage();		
	}
	
	@AfterMethod
	public void setStatus(ITestResult result)
	{
		prevTCStatus=result.getStatus();
	}
}
