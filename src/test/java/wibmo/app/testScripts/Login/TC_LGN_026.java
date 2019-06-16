package wibmo.app.testScripts.Login;

import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.libraries.Log;
import library.Generic;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;

/**
 * The Class TC_LGN_026 Login with unregistered mobile number,email id & unverified email address.
 * 
 * TC_LGN_007	Login with unregistered mobile number and PIN
 * TC_LGN_012	Login with unregistered email id
 * TC_LGN_017	Login with an unverified Email address and password
 *
 */
public class TC_LGN_026 extends BaseTest {
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_026");
	
	public String tcId;
	public int prevTCStatus=5; // First TC random value
	public String data;
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{
		tcId=testMethod.getName();		
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("TC_LGN_026"))
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
	}	
	
	@Test
	public void TC_LGN_007() // ---------------- TC_LGN_007	Login with unregistered mobile number and PIN ---------------- //
	{
		navigate();
		
		String loginId=data.split(",")[0],
			   securePin=data.split(",")[1];		

		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		lnp.loginIdValidation();
	}
	
	@Test
	public void TC_LGN_012() // ---------------- TC_LGN_012	Login with unregistered email id ---------------- //
	{
		TC_LGN_007();
	}
	
	@Test
	public void TC_LGN_017() // ---------------- TC_LGN_017	Login with an unverified Email address and password ---------------- //
	{
		TC_LGN_007();
	}
	
	@AfterMethod
	public void setStatus(ITestResult result)
	{
		prevTCStatus=result.getStatus();
	}
}
