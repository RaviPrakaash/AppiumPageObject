package wibmo.app.testScripts.SmokeTest;

import java.util.HashMap;
import library.ExcelLibrary;
import library.Generic;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.testScripts.BaseTest1;


/**
 * The Class BaseTest.
 */
public class BaseTest extends BaseTest1 {
	
	/** The test data HashMap which consists of the TestCaseId and TestData as Key Value pairs . */
	private static HashMap<String, String> testData=new HashMap<String, String>();
	
	/** The test scenario HashMap which consists of the TestCaseId and TestScenario as Key Value pairs . */
	private static HashMap<String, String> testScenario=new HashMap<String, String>();
	
	/** The transaction status message. */
	public static String transactionStatusMsg;
	
	/** The internal ITP card named as OwnCard.Refer Comments under TestData.xls */
	public static String internalITPCard="OwnCard";
	
	static //Inputs values into the HashMaps testData and testScenario from the Excel file.
	{
		int rc=ExcelLibrary.getExcelRowCount("./excel_lib/TestData.xls", "SmokeTest");
		ExcelLibrary.writeExcelData("./excel_lib/TestData.xls", "SmokeTest", 0, 12, "AutoGenerate");
		
		for (int i = 0; i <= rc; i++) 
		{
			if (!ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "SmokeTest",i,0).equals(" "))
			{
				testData.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "SmokeTest",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "SmokeTest",i,2));
				testScenario.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "SmokeTest",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "SmokeTest",i,1));
			}
		}
	}

	/**
	 * Gets the test data.
	 *
	 * @param TC the tc
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
	 * Navigates to Mobile Recharge page by previously navigating to Recharge Page. 
	 *
	 * @param data the data
	 */
	public void gotoMobileRecharge(String data)
	{
		gotoRecharge(data);
		RechargePage rp=new RechargePage(driver);
		rp.gotoMobileRecharge();		
	}
	
	/**
	 * Navigates to DTH recharge page by navigating previously to Recharge Page.
	 *
	 * @param data the data
	 */
	public void gotoDTHRecharge(String data)
	{
		gotoRecharge(data);
		RechargePage rp=new RechargePage(driver);
		rp.gotoDthRecharge();		
	}
	
	/**
	 * Navigates to DataCardRecharge Page by navigating previously to Recharge page.
	 *
	 * @param data the data
	 */
	public void gotoDataCardRecharge(String data)
	{
		gotoRecharge(data);
		RechargePage rp =new RechargePage(driver);
		rp.gotoDataCardRecharge();
	}
	
	/**
	 * Generates a new Mobile Number for the next jenkins run and writes it to the Excel file.
	 */
	public static void postConditionSmoke()
	{
		if(env.contains("jenkins")) // Automatically update test data for the next jenkins run.
			ExcelLibrary.writeExcelData("./excel_lib/"+env+"/TestData.xls","SmokeTest", 1, 5, Generic.generateMobileNumber());
	}
	
	public void clearProgramApp()
	{
		
		
	}
}
