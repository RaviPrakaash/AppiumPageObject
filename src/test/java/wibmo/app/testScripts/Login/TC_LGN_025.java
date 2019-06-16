package wibmo.app.testScripts.Login;

import java.lang.reflect.Method;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.libraries.Log;
import wibmo.app.pagerepo.WelcomePage;

/**
 * 
 * The Class TC_LGN_025 Login with invalid mobile numbers and blank 
 * TC_LGN_008	Login with invalid mobile number (greater than 10 digits)
 * TC_LGN_009	Login with invalid mobile number (less than 10 digits)
 * TC_LGN_020	Login with blank username 
 * 
 */
public class TC_LGN_025 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_025");
	
	public String tcId;
	public int prevTCStatus=5; // First TC random value
	public String loginId;
	
	@BeforeMethod
	public void setExecutionStatus(Method testMethod)
	{
		tcId=testMethod.getName();
		
		// ==== Set Execution Status ====//
		if(this.getClass().getSimpleName().equals("TC_LGN_025"))
		{	
			Log.info("================ Group execution "+tcId+" : "+getTestScenario(tcId)+" ================");
			groupExecute=true;		
		}		
	}
	
	
	@Test
	public void TC_LGN_008()
	{
		loginId=getTestData(tcId);
		
		WelcomePage wp=new WelcomePage(driver);
		
		wp.selectUser(loginId);
		wp.verifyErrMsg();
	}
	
	@Test(dependsOnMethods="TC_LGN_008")
	public void TC_LGN_009()
	{
		loginId=getTestData(tcId);
		
		WelcomePage wp=new WelcomePage(driver);
		wp.enterLoginId(loginId);
		wp.verifyErrMsg(); 		
	}
	
	@Test(dependsOnMethods="TC_LGN_009")
	public void TC_LGN_020()
	{
		loginId=getTestData(tcId);
		
		WelcomePage wp=new WelcomePage(driver);		
		wp.enterLoginId(loginId);
		wp.verifyErrMsg();
	}
	
	
	

}
