package wibmo.app.testScripts.Registration;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;

import library.Log;

import library.ExcelLibrary;
import library.Generic;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.SendMoneyPage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;


/**
 * The Class BaseTest of Registration module.
 */
public class BaseTest extends BaseTest1 {
	
	/** The test data HashMap which consists of the TestCaseId and TestData as Key Value pairs . */
	private static HashMap<String, String> testData=new HashMap<String, String>();
	
	/** The test scenario HashMap which consists of the TestCaseId and TestScenario as Key Value pairs . */
	protected static HashMap<String, String> testScenario=new HashMap<String, String>();
	
	@BeforeTest
	public void generateTestData() //Inputs values into the HashMaps testData and testScenario from the Excel file.
	{
		int rc=ExcelLibrary.getExcelRowCount("./excel_lib/TestData.xls", "Registration");
		for (int i = 0; i <= rc; i++) 
		{
			if (!ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "Registration",i,0).equals(" ")) 
			{
				testData.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "Registration",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "Registration",i,2));
				testScenario.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "Registration",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "Registration",i,1));
			}
		}
	}
	
	/**
	 * Gets the test data.
	 *
	 * @param TC the TestCaseId
	 * @return the test data
	 */
	public String getTestData(String TC)
	{
		String regData=testData.get(TC);
		
		if(regData.split(",")[0].length()>9)
			regData=regData.replace(regData.split(",")[0], Generic.generateMobileNumber());
		
		return regData;
	}
	
	/**
	 * Gets the test scenario.
	 *
	 * @param TC the TestCaseId
	 * @return the test scenario
	 */
	public static String getTestScenario1(String TC)
	{
		return testScenario.get(TC);
	}
	
	/**
	 * Sets the old mvc value
	 *
	 * @param mobileEmail the new old mvc
	 */
	public void setOldMvc(String mobileEmail)
	{
		oldMvc=Generic.getOTP(mobileEmail,bankCode,Generic.getPropValues("MVC", BaseTest1.configPath));		
		Log.info("======== MVC : "+oldMvc+" ========");
	}	
	
	/**
	 * Gets the old mvc value
	 *
	 * @return the old mvc
	 */
	public String getOldMvc()
	{
		return oldMvc;
	}
	
	/**
	 * Creates a user with Unclaimed funds by sending money to a user yet to be registered.
	 * Used as a precondition for Unclaimed funds module
	 * 
	 */
	public void createUnclaimed()
	{
		
	try{			
		
		int i=0;
		String data=getTestData("CreateUnclaimed");
		String[] values=data.split(",");
		String loginId=values[i++],securePin=values[i++]; 
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,data);	
		
		HomePage hp=new HomePage(driver);
		hp.addSend();

		AddSendPage asp=new AddSendPage(driver);
		asp.sendMoneyThroughMobile();		
		
		SendMoneyPage smp = new SendMoneyPage(driver);
		smp.enterValues(data);
		smp.verifyFundTransferSuccessMsg();
		
		Generic.logout(driver);
		Generic.wait(3);
		
		}catch(Exception e){Log.info("Create Unclaimed unsuccessful\n"+e.getMessage());}
	}	
	
	//---------------------------------------------- Group Execution ------------------------------------//
	
	/** The groupExecute will be set as true by Test Classes which perform group execution */
	public static boolean groupExecute;
	/** Current Test Scenario executing under a group **/
	public static String groupTestScenario;
	/** Current Test Case ID executing under a group **/
	public static String groupTestID;
	
	/** Sets the values for Test case executing under a group i.e TestID & TestScenario
	 *  @param testCaseID
	 * **/
	public void setGroupValue(String testCaseID)
	{
		groupTestID=testCaseID;
		groupTestScenario=getTestScenario(testCaseID);	
		Log.info("======== "+groupTestID+" : "+groupTestScenario+" ========");
	}
	

}
