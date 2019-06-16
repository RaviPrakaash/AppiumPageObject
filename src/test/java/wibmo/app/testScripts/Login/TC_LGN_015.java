package wibmo.app.testScripts.Login;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.libraries.ExcelLibrary;

import wibmo.app.pagerepo.ForgotPinStep1Page;
import wibmo.app.pagerepo.ForgotPinStep2Page;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;

// TODO: Auto-generated Javadoc
/**
 * The Class TC_LGN_015 Validation of Forgot PIN with blank Security answer.
 */
public class TC_LGN_015 extends BaseTest{
	
	/** The tc. */
	public String TC=getTestScenario("TC_LGN_015");
	
	/**
	 * Tc lgn 015.
	 */
	@Test
	public void TC_LGN_015() // Validation of Forgot PIN with blank Security answer provided for the Security Question
	{
		Reporter.log(getTestScenario());
		String data= getTestData("TC_LGN_015");
		int i=0;
		String[] values = data.split(",");
		String mobileEmail=values[i++];
		String ans=values[i++];
		/*String newPin="123456";*/

		WelcomePage wp= new WelcomePage(driver);
		wp.goToForgotPin();

		ForgotPinStep1Page fps1p=new ForgotPinStep1Page(driver);
		fps1p.changePin(mobileEmail,bankCode);

		ForgotPinStep2Page fps2p=new ForgotPinStep2Page(driver);
		fps2p.answerSecurityQuestion(ans);
		//fps2p.verifyBlankErrMsg(); App flow changed, Alert message to be verified for Blank message instead of toast.
		fps2p.wrongSecurityAnsErrMsg();
		
	}

}
