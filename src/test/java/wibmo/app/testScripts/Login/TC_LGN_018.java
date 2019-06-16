package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import library.CSR;
import library.Generic;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class TC_LGN_018 Login using registered Email address and password and view balance.
 */
public class TC_LGN_018 extends BaseTest
{
	/** The tc field. */
	public String TC=getTestScenario("TC_LGN_018");
	
	@Test
	public void TC_LGN_018() // Login using registered Email address and password and view balance
	{
	   Reporter.log(getTestScenario());
		String data= getTestData("TC_LGN_018");
		int i=0;
		String[] values = data.split(",");
		String loginId=values[i++];		
		String securePin =values[i++];
		
		loginId=loginId.contains(":")?loginId.split(":")[0]:loginId;		

		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);
		
		if(values[0].contains(":")) // replace emailId with associated number for DVC 
			data=data.split(":")[1]+","+securePin;
		else
			data=CSR.emailToMobile(loginId)+","+securePin;

		Generic.verifyLogin(driver,data);
		
		HomePage hp=new HomePage(driver);
		hp.addSend();
		
		AddSendPage asp=new AddSendPage(driver);
		asp.verifyBalance();
	}
}
