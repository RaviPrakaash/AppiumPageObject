package wibmo.app.testScripts.MobileRecharge;

import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import library.Generic;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.ManageMobilePage;
import wibmo.app.pagerepo.MobileRechargePage;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class MR_003  Numbers which are stored in Manage mobile should show in My Numbers.
 */
public class MR_003 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("MR_003");	
	
	/**
	 * Mr 003.
	 */
	/*@BeforeMethod // Sets the precondition for recharge. 
	public void preCondition()
	{
		preconditionRecharge(getTestData("PreconditionRecharge"));
	}*/
	@Test
	public void MR_003() // ==== Numbers which are stored in Manage mobile should show in My Numbers ==== // 
	{
		Reporter.log(getTestScenario());
		String data=getTestData("MR_003");
		String[] values=data.split(",");
		String loginId=values[0],securePin=values[1];
		
		WelcomePage wp= new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,data);
		
		HomePage hp=new HomePage(driver);
		hp.gotoSettings();
		
		SettingsPage sp=new SettingsPage(driver);
		sp.gotoManageMobile();
		
		ManageMobilePage mmp=new ManageMobilePage(driver);
		List<String> manageMobileNumbers=mmp.returnMobileNumberList();
		mmp.closeManageMobile();
		
		sp.gotoRecharge();
		
		RechargePage rp=new RechargePage(driver);
		rp.gotoMobileRecharge();
		
		MobileRechargePage mpp=new MobileRechargePage(driver);
		mpp.verifyMyNumbers(manageMobileNumbers);		
	}
}
