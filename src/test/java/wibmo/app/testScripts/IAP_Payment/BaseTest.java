package wibmo.app.testScripts.IAP_Payment;

import java.util.HashMap;

import org.testng.annotations.BeforeTest;

import library.ExcelLibrary;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.testScripts.BaseTest1;


// TODO: Auto-generated Javadoc
/**
 * The Class BaseTest.
 */
public class BaseTest extends BaseTest1 {
	
	/** The testData HashMap which consists of the TestCaseId and TestData as Key Value pairs . */
	private static HashMap<String, String> testData=new HashMap<String, String>();
	
	/** The testScenario HashMap which consists of the TestCaseId and TestScenario as Key Value pairs . */
	private static HashMap<String, String> testScenario=new HashMap<String, String>();
	
	static // Inputs values into the HashMaps testData and testScenario from the Excel file.
	{
		int rc=ExcelLibrary.getExcelRowCount("./excel_lib/TestData.xls", "IAP_Payment"); 
		for (int i = 0; i <= rc; i++) 
		{
			if (!ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAP_Payment",i,0).equals(" ")) 
			{
				testData.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAP_Payment",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAP_Payment",i,2));
				testScenario.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAP_Payment",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAP_Payment",i,1));
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
	
}
