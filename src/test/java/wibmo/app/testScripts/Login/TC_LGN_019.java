package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import library.Generic;
import library.Log;
import wibmo.app.pagerepo.ForgotPinStep1Page;
import wibmo.app.pagerepo.ForgotPinStep2Page;
import wibmo.app.pagerepo.ForgotPinStep3Page;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_LGN_019 Login using old password after changing to new password.
 */
public class TC_LGN_019 extends BaseTest{
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_019");
	
	/**
	 * Tc lgn 019.
	 */
	@Test
	public void TC_LGN_019() // Login using old password after changing to new password
	{
		Reporter.log(getTestScenario());
		String data= getTestData("TC_LGN_019");
		int i=0;
		String[] values = data.split(",");
		String mobileEmail=values[i++];
		String newPin=values[i++];
		String ans=values[i++];
		String oldPin=values[i++];	
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(mobileEmail);
		
		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(oldPin);
		
		//==== Check & Exchange pins to minimize test data errors ==== //  
		if(!lnp.checkPinEntered())
		{
			Log.info("======== Setting old pin to "+newPin+" and new pin to "+oldPin+" and logging in ========");
			String temp=newPin;newPin=oldPin;oldPin=temp;					
		}
		else
		{
			Log.info("======== Old Pin : "+oldPin+", New Pin: "+newPin+" ========");
			Generic.logout(driver);
		}	

		wp.goToForgotPin();

		ForgotPinStep1Page fps1p=new ForgotPinStep1Page(driver);
		fps1p.changePin(mobileEmail,bankCode);

		ForgotPinStep2Page fps2p=new ForgotPinStep2Page(driver);
		fps2p.answerSecurityQuestion(ans);

		ForgotPinStep3Page fps3p=new ForgotPinStep3Page(driver);
		fps3p.enterNewPin(newPin);

		wp.selectUser(mobileEmail);
		
		lnp.login(oldPin);
		lnp.verifyEnterValidPinErrMsg(data);
	}
}
