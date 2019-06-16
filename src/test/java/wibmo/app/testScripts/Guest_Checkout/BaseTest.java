package wibmo.app.testScripts.Guest_Checkout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;

import library.ExcelLibrary;
import library.Generic;
import library.Log;
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
		int rc=ExcelLibrary.getExcelRowCount("./excel_lib/TestData.xls", "Guest_Checkout");
		for (int i = 0; i <= rc; i++) 
		{
			if (!ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "Guest_Checkout",i,0).equals(" ")) 
			{
				testData.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "Guest_Checkout",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "Guest_Checkout",i,2));
				testScenario.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "Guest_Checkout",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "Guest_Checkout",i,1));
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
	
	public void launchAsWebDriver()
	{
		driver = new ChromeDriver();
	    driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
	    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(0));
	    
	    driver.manage().window().maximize();
	    driver.get(Generic.getPropValues("MERCHANTAPPURL", BaseTest1.configPath));
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);	
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
