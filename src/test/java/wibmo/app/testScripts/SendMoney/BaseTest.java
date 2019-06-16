package wibmo.app.testScripts.SendMoney;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;

import library.Log;

import library.ExcelLibrary;
import wibmo.app.testScripts.BaseTest1;


// TODO: Auto-generated Javadoc
/**
 * The Class BaseTest of SendMoney module.
 */
public class BaseTest extends BaseTest1 {
	
	/** The test data HashMap which consists of the TestCaseId and TestData as Key Value pairs . */
	private static HashMap<String, String> testData=new HashMap<String, String>();
	
	/** The test scenario HashMap which consists of the TestCaseId and TestScenario as Key Value pairs . */
	private static HashMap<String, String> testScenario=new HashMap<String, String>();
	
	/** The amount greater than card Balance. */
	public static double amtGreaterThanBal;
	
	/** The checkUnclaimed variable used to communicate to verifyLogin() to ignore Unclaimed funds page . */
	public static boolean checkUnclaimed;	
	
	@BeforeTest
	public void generateTestData() //Inputs values into the HashMaps testData and testScenario from the Excel file.
	{
		int rc=ExcelLibrary.getExcelRowCount("./excel_lib/TestData.xls","SendMoney");
		for (int i = 0; i <= rc; i++) 
		 {
			if (!ExcelLibrary.getExcelData("./excel_lib/TestData.xls","SendMoney",i,0).equals(" ")) 
				{
				 	testData.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls","SendMoney",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls","SendMoney",i,2));
				 	testScenario.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls","SendMoney",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls","SendMoney",i,1));
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
	 * @param TC the TestCaseId
	 * @return the test scenario
	 */
	public String getTestScenario(String TC)
	{
		return testScenario.get(TC);
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
	 *  
	 * **/
	public void setGroupValue(String testCaseID)
	{
		groupTestID=testCaseID;
		groupTestScenario=getTestScenario(testCaseID);	
		Log.info("======== "+groupTestID+" : "+groupTestScenario+" ========");
	}

}
