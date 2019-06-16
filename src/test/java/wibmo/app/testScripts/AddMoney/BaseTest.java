package wibmo.app.testScripts.AddMoney;

import java.util.HashMap;

import library.Log;

import library.ExcelLibrary;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.testScripts.BaseTest1;


// TODO: Auto-generated Javadoc
/**
 * The Class BaseTest of AddMoney module.
 */
public class BaseTest extends BaseTest1 {
	
	/** The testData HashMap which consists of the TestCaseId and TestData as Key Value pairs . */
	private static HashMap<String, String> testData=new HashMap<String, String>();
	
	/** The testScenario HashMap which consists of the TestCaseId and TestScenario as Key Value pairs . */
	private static HashMap<String, String> testScenario=new HashMap<String, String>();
	
	/** The transaction status mesasge. */
	public static String transactionStatusMsg="success; wPayTxnId: 201603091531262585jL86xE9; dataPickUpCode: 201603091531262585jL86xE9:2eO5oH1fH3"; // Sample transMsg, will be overriden by the Script  // Access transaction id of previous test cases
	
	/** The internal ITP card. */
	public static String internalITPCard="OwnCard";
	
	static // Inputs values into the HashMaps testData and testScenario from the Excel file.
	{
		int rc=ExcelLibrary.getExcelRowCount("./excel_lib/TestData.xls", "AddMoney");
		for (int i = 0; i <= rc; i++) 
		{
			if (!ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "AddMoney",i,0).equals(" ")) 
			{
				testData.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "AddMoney",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "AddMoney",i,2));
				testScenario.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "AddMoney",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "AddMoney",i,1));
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
		return testData.get(TC);
	}
	
	/**
	 * Gets the test scenario.
	 *
	 * @param TC the tc
	 * @return the test scenario
	 */
	public String getTestScenario(String TC)
	{
		return testScenario.get(TC);
	}
	
	/**
	 * Goto mobile recharge.
	 *
	 * @param data the data
	 */
	public void gotoMobileRecharge(String data)
	{
		gotoRecharge(data);
		RechargePage rp=new RechargePage(driver);
		rp.gotoMobileRecharge();		
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
