package wibmo.app.testScripts.DTH_Recharge;

import org.testng.Reporter;
import org.testng.annotations.Test;

import wibmo.app.pagerepo.DTHRechargePage;

// TODO: Auto-generated Javadoc
/**
 * The Class DTHR_013 Check display of DTH operators.
 */
public class DTHR_013 extends BaseTest
{
	
	/** The tc. */
	public String TC=getTestScenario("DTHR_013");
	
	/**
	 * Dthr 013.
	 */
	@Test
	public void DTHR_013() // ==== DTH Operators should be displayed in alphabetical order ==== // 
	{		
		Reporter.log(getTestScenario());
		String data=getTestData("DTHR_013");
		gotoDTHRecharge(data);	
		
		DTHRechargePage drp=new DTHRechargePage(driver);
		drp.verifyDTHOperatorList();		
	}
}
