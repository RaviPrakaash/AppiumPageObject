package wibmo.app.testScripts.IAPV2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
 * The Class BaseTest of IAPV2 App SDK module.
 */
public class BaseTest extends BaseTest1 {
	
	/** The testData HashMap which consists of the TestCaseId and TestData as Key Value pairs . */
	private static HashMap<String, String> testData=new HashMap<String, String>();
	
	/** The testScenario HashMap which consists of the TestCaseId and TestScenario as Key Value pairs . */
	private static HashMap<String, String> testScenario=new HashMap<String, String>();
	
	public String tcId="";
	public static String prevTcId="";
	public static int prevTcStatus;
	
	/**
	 * Inputs values into the HashMaps testData and testScenario from the Excel file.
	 */
	@BeforeTest
	public void generateTestData()
	{
		int rc=ExcelLibrary.getExcelRowCount("./excel_lib/TestData.xls", "IAPV2");
		for (int i = 0; i <= rc; i++) 
		{
			if (!ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAPV2",i,0).equals(" ")) 
			{
				testData.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAPV2",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAPV2",i,2));
				testScenario.put(ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAPV2",i,0), ExcelLibrary.getExcelData("./excel_lib/TestData.xls", "IAPV2",i,1));
			}
		}
	}

	/** The variable latestTransactionTimestampMinutes used to check for the latest transaction based on the timestamp. */
	public static int latestTransactionTimestampMinutes; 
	
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
		setWebDriverProperty();
		driver = new ChromeDriver();
	    driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
	    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(0));
	    
	    driver.manage().window().maximize();
	    driver.get(Generic.getPropValues("MERCHANTAPPURL", BaseTest1.configPath));
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);	
	}
	
	public WebDriver launchWebDriver()
	{
		WebDriver driver;
		setWebDriverProperty();
		driver = new ChromeDriver();
	    driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
	    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(0));
	    
	    driver.manage().window().maximize();
	    driver.get(Generic.getPropValues("MERCHANTAPPURL", BaseTest1.configPath));
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);	
		
		return driver;
	}
	
	
	public void setPGMerchant()
	{
		setWebDriverProperty();
		
		Log.info("======== Setting merchant configuration values for second PG merchant ========");
		
		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get(Generic.getPropValues("MERCHANTCONFIGURL", BaseTest1.configPath));
		WebElement merID = driver.findElement(By.name("merId"));
		WebElement merAppId = driver.findElement(By.name("merAppId"));
		WebElement hashKey = driver.findElement(By.name("hashkey"));
		
		merID.clear();
		merAppId.clear();
		hashKey.clear();			
		
		merID.sendKeys(Generic.getPropValues("SSMERCHANTID", BaseTest1.configPath));			
		merAppId.sendKeys(Generic.getPropValues("SSMERCHANTAPPID", BaseTest1.configPath));			
		hashKey.sendKeys(Generic.getPropValues("SSHASHKEY", BaseTest1.configPath));			
	
		driver.findElement(By.xpath("//input[contains(@value,'Set new value')]")).click();		
		driver.quit();
		
		wibmo.app.testScripts.IAP_Transaction.BaseTest.setMerchantType("pgmerchant2");
		
	}
	
	
	public boolean checkUserSame()
	{
		if(prevTcId.isEmpty()) return true; // first tc
		//if(prevTcStatus==2) return false;   // prev tc fail so relaunch/app restart, selectUser() with user logged in from app will lead to error
		
		String currentUser=getTestData(tcId).split(",")[0],prevUser=getTestData(prevTcId).split(",")[0];
		
		System.out.println("Current User :"+currentUser+" Previous User : "+prevUser);
		
		return currentUser.equals(prevUser);				
	}
	
	
	
}
