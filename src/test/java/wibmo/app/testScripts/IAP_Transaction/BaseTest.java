package wibmo.app.testScripts.IAP_Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;
import com.libraries.Log;
import library.ExcelLibrary;
import library.Generic;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantSettingsPage;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.testScripts.BaseTest1;


/**
 * The Class BaseTest of IAP Transaction module.(Static Merchant)
 */
public class BaseTest extends BaseTest1 {
	
	/** The testData HashMap which consists of the TestCaseId and TestData as Key Value pairs . */
	private static HashMap<String, String> testData=new HashMap<String, String>();
	
	/** The testScenario HashMap which consists of the TestCaseId and TestScenario as Key Value pairs . */
	private static HashMap<String, String> testScenario=new HashMap<String, String>();
	
	public static String merchantType="static"; //static = normal merchant, dynamic=dynamic merchant 
	
	static // Inputs values into the HashMaps testData and testScenario from the Excel file.
	{
		int rc=ExcelLibrary.getExcelRowCount("./excel_lib/TestData.xls", "IAP_Transaction");
		
		for (int i = 0; i <= rc; i++) 
		{
			if (!ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAP_Transaction",i,0).equals(" ")) 
			{
				testData.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAP_Transaction",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAP_Transaction",i,2));
				testScenario.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAP_Transaction",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAP_Transaction",i,1));
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
	
	
	@BeforeMethod//(dependsOnMethods="launchApplication")
	public void setMerchant()
	{		
		String className=this.getClass().getName();
		System.out.println("Parsing ClassName : "+className);
		String type=Generic.containsIgnoreCase(className, "dynamic")?"dynamic":"static";
		
		
		 if(Generic.containsIgnoreCase(merchantType,type)) 
		{
			Log.info("======== Configuration already set to merchant type "+type+" ========");
			return;
		}
			 
	  try{ 
			
			WebDriver driver=new ChromeDriver();
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(0));
			    
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.get(Generic.getPropValues("MERCHANTCONFIGURL", BaseTest1.configPath));
			WebElement merID = driver.findElement(By.name("merId"));
			WebElement merAppId = driver.findElement(By.name("merAppId"));
			WebElement hashKey = driver.findElement(By.name("hashkey"));		
		
			switch(type.toLowerCase())
			{
			case "static": 
				merID.clear();
				merID.sendKeys(Generic.getPropValues("SMERCHANTID", BaseTest1.configPath));			
				merAppId.clear();
				merAppId.sendKeys(Generic.getPropValues("SMERCHANTAPPID", BaseTest1.configPath));			
				hashKey.clear();
				hashKey.sendKeys(Generic.getPropValues("SHASHKEY", BaseTest1.configPath));			
				break;
			case "dynamic":
				merID.clear();
				merID.sendKeys(Generic.getPropValues("DMERCHANTID", BaseTest1.configPath));
				merAppId.clear();
				merAppId.sendKeys(Generic.getPropValues("DMERCHANTAPPID", BaseTest1.configPath));
				hashKey.clear();
				hashKey.sendKeys(Generic.getPropValues("DHASHKEY", BaseTest1.configPath));
				break;
			}
			driver.findElement(By.xpath("//input[contains(@value,'Set new value')]")).click();		
			driver.quit();
			
			
			//----------------------------    Merchant setting    ----------------------------------------------//
			if(this.driver==null || Generic.containsIgnoreCase(this.driver.toString(), "Firefox") || Generic.containsIgnoreCase(this.driver.toString(), "Chrome"))
			{
				Log.info("======== WebDriver execution in progress, skipping Merchant App settings for "+type+" merchant========");
				merchantType=type;
				return;			
			}
			
			Generic.switchToMerchant(this.driver);
			
			MerchantHomePage mhp = new MerchantHomePage(this.driver);
			mhp.gotoSettings();
			
			MerchantSettingsPage msp=new MerchantSettingsPage(this.driver);		
			msp.enterStaticOrDynamicId(type);	
			
			merchantType=type;
			Generic.switchToApp(this.driver);
			
		}catch(Exception e)
		{
			System.err.println("Unable to set merchant to "+type);e.printStackTrace();
		}
		
	}
	
	public static void setMerchantType(String type)
	{
		merchantType=type;
	}
	
	public static String getMerchantType()
	{
		return merchantType;
	}
	
	public static void setMerchantShellNormal() // set correct normal values after suite execution
	{
		try{			
		
		
		
		WebDriver driver=new ChromeDriver();
		//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
		//ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		//driver.switchTo().window(tabs.get(0));
		    
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get(Generic.getPropValues("MERCHANTCONFIGURL", BaseTest1.configPath));
		WebElement merID = driver.findElement(By.name("merId"));
		WebElement merAppId = driver.findElement(By.name("merAppId"));
		WebElement hashKey = driver.findElement(By.name("hashkey"));
		
		if(merID.getAttribute("value").equals(Generic.getPropValues("COMMONMERCHANTID", BaseTest1.configPath))) 
			{System.out.println("Normal merchant settings already set in Merchant Shell Config URL");return;}
		
		merID.clear();
		merID.sendKeys(Generic.getPropValues("COMMONMERCHANTID", BaseTest1.configPath));			
		merAppId.clear();
		merAppId.sendKeys(Generic.getPropValues("COMMONMERCHANTAPPID", BaseTest1.configPath));			
		hashKey.clear();
		hashKey.sendKeys(Generic.getPropValues("COMMONSHASHKEY", BaseTest1.configPath));
		
		driver.findElement(By.xpath("//input[contains(@value,'Set new value')]")).click();		
		driver.quit();
		
		}
		catch(Exception e)
		{
			Log.info("== Warning : Unable to set Merchant Shell config to normal merchant values ==\n"+e.getMessage());
		}
		
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
		library.Log.info("======== "+groupTestID+" : "+groupTestScenario+" ========");
	}

	
	
}
