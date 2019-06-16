package library;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;






//import java.util.concurrent.TimeoutException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;






//import com.libraries.ExcelLibrary;
import io.appium.java_client.android.AndroidDriver;
import library.Log;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class CSR consists of all static methods used to interact with the CSR.
 * After execution of every method the CSR.wdriver must be quit() using the finally{} block 
 */
public class CSR 
{	
	
	/** The wdriver. */
	public static WebDriver wdriver;
	
	public static HashMap<String, Boolean> deviceCSR=new HashMap<String, Boolean>();
	
	/** The deviceDriver used to map udid of main driver to the CSR WebDriver instance , to support parallel execution */
	public static HashMap<String, WebDriver> deviceDriver=new HashMap<String, WebDriver>();
	
	/** The soft assert. */
	private static SoftAssert softAssert;
	
	/** The program code. */
	public static String programCode;
	
	/** The txn desc. */
	public static String txnDesc;
	
	private static boolean CSRStatus;
	
	public static boolean CSRExecute=BaseTest1.checkEnv("qa");	// Temp Staging CSR workaround
	
	/**
	 * Login.
	 */
	public static void login()
	{
		String program =BaseTest1.packageName.split("\\.")[BaseTest1.packageName.split("\\.").length-1];
		
		if(program.equals("qa"))
		{
			String programName=BaseTest1.programName.toLowerCase();
			
			switch(programName)
			{
			case "payzapp" : 	program="hdfc";
			break;			
			case "payapt"	: 	program="idbi";
			break;
			case "kotak":		program="kotak";
			break;			
			case "safepay": 	program="safepay";
			break;				
			}			
		}
		
		setCSRStatus(true);
		
		switch(program)
		{
		case "hdfc" : 	CSR_login("payzappuser","welcome111",3);
						programCode="6019";
						break;
						
		case "idbi"	: 	CSR_login("payaptuser","welcome111",2);
						programCode="6022";
						break;
			
		case "kotak":	CSR_login("apppayuser","welcome111",1);
						programCode="6023";
						break;
						
		case "safepay": CSR_login("safepayuser","welcome111",4);
						programCode="7070";
						break;						
		}	
		setCSRStatus(true); 
	}
	
	public static void login(WebDriver driver)
	{
        String program =BaseTest1.packageName.split("\\.")[BaseTest1.packageName.split("\\.").length-1];
		
		if(program.equals("qa"))
		{
			String programName=BaseTest1.programName.toLowerCase();
			
			switch(programName)
			{
			case "payzapp" : 	program="hdfc";
			break;			
			case "payapt"	: 	program="idbi";
			break;
			case "kotak":		program="kotak";
			break;			
			case "safepay": 	program="safepay";
			break;				
			}			
		}
		
		setCSRStatus(driver,true);
		
		switch(program)
		{
		case "hdfc" : 	CSR_login(driver,"payzappuser","welcome111",3);
						programCode="6019";
						break;
						
		case "idbi"	: 	CSR_login(driver,"payaptuser","welcome111",2);
						programCode="6022";
						break;
			
		case "kotak":	CSR_login(driver,"apppayuser","welcome111",1);
						programCode="6023";
						break;
						
		case "safepay": CSR_login(driver,"safepayuser","welcome111",4);
						programCode="7070";
						break;						
		}	
		setCSRStatus(driver,true); 
	}
	
	/**
	 * Launches the WebDriver.
	 *
	 * @param driver the driver
	 */
	/*public static void launchWebDriver(WebDriver driver)
	{
		wdriver=new FirefoxDriver();
		wdriver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		System.out.println(Generic.getPropValues("CSR",BaseTest1.configPath));
		wdriver.get(Generic.getPropValues("CSR",BaseTest1.configPath));
		wdriver.manage().window().maximize();		
	}*/
	
	/**
	 * Performs a CSR login based on the given userId , password and programIndex.
	 *
	 * @param uid the CSRUserId
	 * @param pwd the password
	 * @param prgIndex the prg index
	 */
	public static void CSR_login(String uid,String pwd,int prgIndex)
	{
		// ==== wdriver Initialized from setWebDriver() ==== // 	
		setWebDriverProperty();
	try{
		wdriver=new FirefoxDriver();
		
		//wdriver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
	    //ArrayList<String> tabs = new ArrayList<String> (wdriver.getWindowHandles());
	    //wdriver.switchTo().window(tabs.get(0));
	    
		wdriver.manage().timeouts().implicitlyWait(25,TimeUnit.SECONDS);
		wdriver.get(Generic.getPropValues("CSR",BaseTest1.configPath));
		wdriver.manage().window().maximize();	
		
		wdriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		
		// ==== Login ==== //
		
		Log.info("~~~~======== Logging in to CSR as "+uid+" ========~~~~");
		wdriver.findElement(By.name("txtUserId")).sendKeys(uid);
		
		Log.info("======== Entering password ========" );
		wdriver.findElement(By.name("txtPassword")).sendKeys(pwd);	
		
		Log.info("======== Selecting Program : "+BaseTest1.programName+" =======");
		//new Select(wdriver.findElement(By.id("txtPrgName"))).selectByIndex(prgIndex);	
		wdriver.findElement(By.name("txtPrgName")).sendKeys(BaseTest1.programName);
		
		Log.info("======== Clicking on Submit ========");
		wdriver.findElement(By.name("submit")).click();		
		
		Log.info("======== Clicking on WIBMO link ========");
		wdriver.findElement(By.linkText("WIBMO")).click();		
	}
	catch(Exception e)
	{
		Assert.fail("Error opening CSR\n"+e.getMessage()); // wdriver.close(); may impact screenshot 
	}
	
		
}
	/**
	 * Performs a CSR login based on the given driver, userId , password and programIndex.
	 * The CSR execution corresponds to that specific driver during parallel execution.
	 *
	 * @param driver 
	 * @param uid the CSRUserId
	 * @param pwd the password
	 * @param prgIndex the prg index
	 */
	public static void CSR_login(WebDriver driver,String uid,String pwd,int prgIndex)
	{
		// ==== wdriver Initialized from setWebDriver() ==== // 	
		setWebDriverProperty();
	try{
		//deviceDriver.get(wdriver(driver));
		System.out.println("Launching driver");
		deviceDriver.put(wdriver(driver), new FirefoxDriver());  // deviceDriver.get(wdriver(driver))
		
		System.out.println("Driver Used : "+deviceDriver.get(wdriver(driver)));
		
		//deviceDriver.get(wdriver(driver)).findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
	   // ArrayList<String> tabs = new ArrayList<String> (wdriver.getWindowHandles());
	   // wdriver.switchTo().window(tabs.get(0));
	    
		System.out.println("Setting waits");
		deviceDriver.get(wdriver(driver)).manage().timeouts().implicitlyWait(25,TimeUnit.SECONDS);
		deviceDriver.get(wdriver(driver)).get(Generic.getPropValues("CSR",BaseTest1.configPath));
		deviceDriver.get(wdriver(driver)).manage().window().maximize();
		
		// --- CSR Non Responsive Scenario --- //
		deviceDriver.get(wdriver(driver)).manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		// ----------------------------------- //
		
		// ==== Login ==== //
		
		Log.info("======== Logging in to CSR as "+uid+" ========" );
		deviceDriver.get(wdriver(driver)).findElement(By.name("txtUserId")).sendKeys(uid);
		
		Log.info("======== Entering password ========" );
		deviceDriver.get(wdriver(driver)).findElement(By.name("txtPassword")).sendKeys(pwd);	
		
		Log.info("======== Selecting Program : "+BaseTest1.programName+" =======");
		//new Select(wdriver.findElement(By.id("txtPrgName"))).selectByIndex(prgIndex);	
		deviceDriver.get(wdriver(driver)).findElement(By.name("txtPrgName")).sendKeys(BaseTest1.programName);
		
		Log.info("======== Clicking on Submit ========");
		deviceDriver.get(wdriver(driver)).findElement(By.name("submit")).click();		
		
		Log.info("======== Clicking on WIBMO link ========");
		deviceDriver.get(wdriver(driver)).findElement(By.linkText("WIBMO")).click();
	}
	catch(Exception e)
	{
		Assert.fail("Error opening CSR\n"+e.getMessage()); e.printStackTrace();		
	}
}
	
public static String wdriver(WebDriver driver)
{
	if(!Generic.containsIgnoreCase(driver.toString(), "Android"))
	{
		// return BaseTest1.getSDKUdid(driver); 
	}
			
	String deviceId=(String)((AndroidDriver)driver).getCapabilities().getCapability("deviceName");
	
	System.out.println("DeviceId : "+ deviceId);
	
	return deviceId;	
}
	public static void navigateToBin() // Used for single device execution 
	{
		login();
		
		Log.info("======== Clicking on Manage BIN link ========");
		wdriver.switchTo().frame(0);		
		wdriver.findElement(By.partialLinkText("Fetch BIN")).click();				
	}
	
	public static void navigateToBin(WebDriver driver)
	{
		login(driver);
		
		Log.info("======== Clicking on Manage BIN link ========");
		deviceDriver.get(wdriver(driver)).switchTo().frame(0);		
		deviceDriver.get(wdriver(driver)).findElement(By.partialLinkText("Fetch BIN")).click();		
	}
	
	/**
	 * Generates an invite.
	 *
	 * @param uid the uid
	 * @param pwd the pwd
	 * @param prgIndex the prg index
	 * @param loginId the login id
	 */
	public static void generateInvite(String uid,String pwd,int prgIndex,String loginId)
	{	
		if(!CSRExecute) return;
		
		login();
		
		// ==== Go to Invitations ==== //
		
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("Invitations")).click();
		
		wdriver.findElement(By.id("fromday")).clear();
		wdriver.findElement(By.id("fromday")).sendKeys("0");		// "0" To be removed when integrating with registrationInvite script	
		wdriver.findElement(By.name("submit")).click();
		
		// ==== Send Invite to the number ==== //
		String xp="//td[text()='"+loginId+"']/..//a[text()='Resend' or 'Send']"; 	//Click on Send or Resend if already sent
		
		try
		{
		wdriver.switchTo().frame(1);
		wdriver.findElement(By.xpath(xp)).click();
		wdriver.switchTo().alert().accept();	
		}
		catch(Exception e)
		{
			Assert.fail(e.getMessage());
		}
		 wdriver.quit();	
		setCSRStatus(false);
	}
	
	/**
	 * Verifies Init success status.
	 *
	 * @param loginId the login id
	 */
	public static void verifyInitSuccess(String loginId)
	{
		if(!CSRExecute) return;
		
		login();
		
		Log.info("======== Clicking on IAP Txn Report link ========");
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("IAP Txn Report")).click();
		
		Log.info("======== Clicking on Fetch Report ========");
		wdriver.findElement(By.name("submit")).click(); 
		
		wdriver.switchTo().frame(1);
		
		String initSuccessXp="//center[contains(text(),'"+txnDesc+"')]/../..//td[13]";		
		
		try
		{				
			String initMsg=wdriver.findElement(By.xpath(initSuccessXp)).getText();
			Log.info("======== Verifying Record with Transaction Description "+txnDesc+" : "+initMsg+"========");
			Assert.assertTrue(initMsg.toLowerCase().contains("init success"),"Init Success Description not found for "+txnDesc+'\n');
			Assert.assertTrue(initMsg.contains("010"),"Init Success Code not found for "+txnDesc+'\n');
		}
		catch(Exception e)
		{
			Log.info("======== Init Success Description not found for "+txnDesc+"\n trying to find the record with Acc. No. ======== ");
			verifyWithAccNo(loginId,"init success");
		}
		 wdriver.quit();	
		 setCSRStatus(false);
	}
	
	/**
	 * Verify status for a given transaction id.
	 *
	 * @param txnId the transaction id
	 * @param statusCode the status code
	 * @param statusDesc the status desc
	 */
	public static void verifyStatusOnTxnId1(String txnId,String statusCode,String statusDesc)
	{
		if(!CSRExecute) return;
		
		login();
		
		Log.info("======== Clicking on IAP Txn Report link ========");
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("IAP Txn Report")).click();
		
		Log.info("======== Clicking on Fetch Report ========");
		wdriver.findElement(By.name("submit")).click(); 
		
		wdriver.switchTo().frame(1);
		
		String xp="//center[contains(text(),'"+txnId+"')]/../..//td[13]";	
		
		try
		{
			Log.info("======== Verifying Record with Transaction Id "+txnId+" ========");
			String descCode=wdriver.findElement(By.xpath(xp)).getText(); 
			Log.info("======== Verifying Status code and description :"+descCode+" ========");
			Assert.assertTrue(descCode.toLowerCase().contains(statusCode.toLowerCase()),"Code "+statusCode+" not found for Txn Id : "+txnId);
			Assert.assertTrue(descCode.contains(statusDesc),statusDesc+ " not found for Txn Id : "+txnId);
		}
		catch(Exception e)
		{
			Assert.fail("Record not found for the Txn Id : "+txnId);
		}	
		 wdriver.quit();	
		 setCSRStatus(false);
	}
	
	public static void verifyStatusOnTxnId(WebDriver driver,String txnId,String statusCode,String statusDesc)
	{
		if(!CSRExecute) return;
		
		login(driver);
		
		Log.info("======== Clicking on IAP Txn Report link ========");
		deviceDriver.get(wdriver(driver)).switchTo().frame(0); 
		deviceDriver.get(wdriver(driver)).findElement(By.partialLinkText("IAP Txn Report")).click();
		
		Log.info("======== Clicking on Fetch Report ========");
		deviceDriver.get(wdriver(driver)).findElement(By.name("submit")).click(); 
		
		deviceDriver.get(wdriver(driver)).switchTo().frame(1);
		
		String xp="//center[contains(text(),'"+txnId+"')]/../..//td[13]";	
		
		try
		{
			Log.info("======== Verifying Record with Transaction Id "+txnId+" ========");
			String descCode=deviceDriver.get(wdriver(driver)).findElement(By.xpath(xp)).getText(); 
			Log.info("======== Verifying Status code and description :"+descCode+" ========");
			Assert.assertTrue(descCode.toLowerCase().contains(statusCode.toLowerCase()),"Code "+statusCode+" not found for Txn Id : "+txnId);
			Assert.assertTrue(descCode.contains(statusDesc),statusDesc+ " not found for Txn Id : "+txnId);
		}
		catch(Exception e)
		{
			Assert.fail("Record not found for the Txn Id : "+txnId);
		}	
		deviceDriver.get(wdriver(driver)).quit();	
		 setCSRStatus(driver,false);
	}
	
	
	public static void verifyStatusOnTxnDesc(WebDriver driver,String statusText) // latest only
	{
		if(!CSRExecute) return;
		
		boolean status=true;
		try
		{
			login(driver);
			
			Log.info("======== Clicking on IAP Txn Report link ========"); 
					
			deviceDriver.get(wdriver(driver)).switchTo().frame(0);
			deviceDriver.get(wdriver(driver)).findElement(By.partialLinkText("IAP Txn Report")).click();
			
			Log.info("======== Clicking on Fetch Report ========");
			deviceDriver.get(wdriver(driver)).findElement(By.name("submit")).click(); 
			
			deviceDriver.get(wdriver(driver)).switchTo().frame(1);
			
			String xp="//center[contains(text(),'"+txnDesc+"')]/../..//td[13]";		
			
			try
			{
				Log.info("======== Verifying Record with Transaction Description "+txnDesc+" :"+deviceDriver.get(wdriver(driver)).findElement(By.xpath(xp)).getText()+" ========");
				//Assert.assertTrue(deviceDriver.get(wdriver(driver)).findElement(By.xpath(xp)).getText().toLowerCase().contains(statusDesc.toLowerCase()),"User Aborted Description not found for txn description "+txnDesc+'\n');
				Assert.assertTrue(Generic.containsIgnoreCase(deviceDriver.get(wdriver(driver)).findElement(By.xpath(xp)).getText(), statusText),statusText+" Description not found for txn description "+txnDesc+'\n');
			}
			catch(Exception e)
			{
				status=false;
				//Log.info("======== User Aborted Description not found for "+txnDesc+"\n trying to find the record with Acc. No.======== ");
				//verifyWithAccNo(loginId,"aborted");
				Assert.fail(statusText+" Description not found for txn description "+txnDesc+'\n'+e.getMessage());			
			}	
	}
		catch(Exception e)
		{
			status=false;
			Assert.fail("Error in page\n"+e.getMessage());
		}
		
		deviceDriver.get(wdriver(driver)).quit();
		setCSRStatus(driver,false);
	}
	
	/**
	 * Verify 3DS intermediate status.
	 *
	 * @param loginId the login id
	 */
	public static void verify3DSIntermediate(String loginId)
	{
		if(!CSRExecute) return;
		
		login();
		
		Log.info("======== Clicking on IAP Txn Report link ========");
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("IAP Txn Report")).click();
		
		Log.info("======== Clicking on Fetch Report ========");
		wdriver.findElement(By.name("submit")).click(); 
		
		wdriver.switchTo().frame(1);
		
		String intermediateXp="//center[contains(text(),'"+txnDesc+"')]/../..//td[13]";		
		
		try
		{
			Log.info("======== Verifying Record with Transaction Description "+txnDesc+" :"+wdriver.findElement(By.xpath(intermediateXp)).getText()+" ========");
			Assert.assertTrue(wdriver.findElement(By.xpath(intermediateXp)).getText().toLowerCase().contains("intermediate"),"3DS Intermediate Description not found for "+txnDesc+'\n');
		}
		catch(Exception e)
		{
			Log.info("======== 3DS Intermediate Description not found for "+txnDesc+"\n trying to find the record with Acc. No. ======== ");
			//Assert.fail("Init Success Description not found for "+txnDesc+'\n'+e.getMessage());
			verifyWithAccNo(loginId,"intermediate");
		}	
		
		 wdriver.quit();	
		 setCSRStatus(false);
	}
	
	/**
	 * Verify 3DS fail status.
	 *
	 * @param loginId the login id
	 */
	public static void verify3DSfail(String loginId)
	{
		if(!CSRExecute) return;
		
		login();		
		
		Log.info("======== Clicking on IAP Txn Report link ========");
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("IAP Txn Report")).click();
		
		Log.info("======== Clicking on Fetch Report ========");
		wdriver.findElement(By.name("submit")).click(); 
		
		wdriver.switchTo().frame(1);
		
		String xp="//center[contains(text(),'"+txnDesc+"')]/../..//td[13]";		
		
		try
		{
			Log.info("======== Verifying Record with Transaction Description "+txnDesc+" :"+wdriver.findElement(By.xpath(xp)).getText()+" ========");
			Assert.assertTrue(wdriver.findElement(By.xpath(xp)).getText().toLowerCase().contains("3ds failed"),"3DS Failed Description not found for "+txnDesc+'\n');
		}
		catch(Exception e)
		{
			Log.info("======== 3DS Failed Description not found for "+txnDesc+", trying to find the record with Acc. No. ========");
			verifyWithAccNo(loginId,"3ds failed");
		}
		 wdriver.quit();	
		 setCSRStatus(false);
	}
	
	/**
	 * Verifies abort status.
	 *
	 * @param loginId the login id
	 */
	public static void verifyAbortStatus(String loginId)
	{
		if(!CSRExecute) return;
		
		login();
		
		Log.info("======== Clicking on IAP Txn Report link ========");
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("IAP Txn Report")).click();
		
		Log.info("======== Clicking on Fetch Report ========");
		wdriver.findElement(By.name("submit")).click(); 
		
		wdriver.switchTo().frame(1);
		
		String abortXp="//center[contains(text(),'"+txnDesc+"')]/../..//td[13]";		
		
		try
		{
			Log.info("======== Verifying Record with Transaction Description "+txnDesc+" :"+wdriver.findElement(By.xpath(abortXp)).getText()+" ========");
			Assert.assertTrue(wdriver.findElement(By.xpath(abortXp)).getText().toLowerCase().contains("aborted"),"User Aborted Description not found for "+txnDesc+'\n');
		}
		catch(Exception e)
		{
			Log.info("======== User Aborted Description not found for "+txnDesc+"\n trying to find the record with Acc. No.======== ");
			verifyWithAccNo(loginId,"aborted");
		}	
		 wdriver.quit();	
		 setCSRStatus(false);
	}
	
	/**
	 * Verifies hash failed status in CSR.
	 *
	 * @param loginId the login id
	 */
	public static void verifyHashFailed(String loginId)
	{	
		if(!CSRExecute) return;
	 try{			
		
		login();
		
		Log.info("======== Clicking on IAP Txn Report link ========");
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("IAP Txn Report")).click();
		
		Log.info("======== Clicking on Fetch Report ========");
		wdriver.findElement(By.name("submit")).click(); 
		
		wdriver.switchTo().frame(1);
		
		String hashMsgXp="//center[contains(text(),'"+txnDesc+"')]/../..//td[13]";	
		
		try
		{
			Log.info("======== Verifying Record with Transaction Description "+txnDesc+" :"+wdriver.findElement(By.xpath(hashMsgXp)).getText()+" ========");
			Assert.assertTrue(wdriver.findElement(By.xpath(hashMsgXp)).getText().toLowerCase().contains("hash failed"),"Hash failed Description not found for "+txnDesc+'\n');
		}
		catch(Exception e)
		{
			Log.info("======== Hash failed Description not found for "+txnDesc+"\n trying to find the record with Acc. No. ========");
			verifyWithAccNo(loginId,"hash failed");
		}
	}
	 catch(Exception e)
	{
		 Assert.fail("Error in page\n"+e.getMessage());
	}
		wdriver.quit();
		setCSRStatus(false);
	}
	
	/**
	 * Verifies a status based on account number which is retrieved from the loginId.
	 *
	 * @param loginId the login id
	 * @param statusDesc the status desc
	 */
	public static void verifyWithAccNo(String loginId,String statusDesc)
	{
		if(!CSRExecute) return;
		
		String accNo=retrieveAccNo(loginId);
		
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("IAP Txn Report")).click();
		
		Log.info("======== Entering Account No. : "+accNo+" ========");
		wdriver.findElement(By.id("accountNumber")).sendKeys(accNo);

		Log.info("======== Clicking on Fetch Report ========");
		wdriver.findElement(By.name("submit")).click();
		
		wdriver.switchTo().frame(1);
		
		String xp="//center[text()='1.']/../..//td[13]";		
		try
		{
		Assert.assertTrue(wdriver.findElement(By.xpath(xp)).getText().toLowerCase().contains(statusDesc.toLowerCase()),statusDesc+" description status not found for "+loginId);
		}
		catch(Exception e)
		{
			Assert.fail(statusDesc+" description status not found for "+loginId);
		}
		 wdriver.quit();	
		 setCSRStatus(false);
		
	}
	
	/**
	 * Verifies a status and status code based on account number which is retrieved from the loginId.
	 *
	 * @param loginId the login id
	 * @param statusCode the status code
	 * @param statusDesc the status desc
	 */
	public static void verifyWithAccNo(String loginId,String statusCode,String statusDesc)
	{
		if(!CSRExecute) return;
		login();
		
		String accNo=retrieveAccNo(loginId);
		String codeDesc;
		
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("IAP Txn Report")).click();
		
		Log.info("======== Entering Account No. : "+accNo+" ========");
		wdriver.findElement(By.id("accountNumber")).sendKeys(accNo);

		Log.info("======== Clicking on Fetch Report ========");
		wdriver.findElement(By.name("submit")).click();
		
		wdriver.switchTo().frame(1);
		
		String xp="//center[text()='1.']/../..//td[13]";		
		try
		{
		codeDesc=wdriver.findElement(By.xpath(xp)).getText();
		Assert.assertTrue(codeDesc.toLowerCase().contains(statusDesc.toLowerCase()),statusDesc+" description status not found for "+loginId);
		Assert.assertTrue(codeDesc.contains(statusCode),statusCode+ "for "+ loginId+ " incorrect\n");
		}
		catch(Exception e)
		{
			Assert.fail(statusDesc+" description status not found for "+loginId);
		}	
		
		
	}
	
	/**
	 * Verifies the  DB entry for a new user registration..
	 *
	 * @param driver the driver
	 * @param data the data
	 */
	public static void verifyDBEntry(WebDriver driver,String data)	
	{	
		if(!CSRExecute) return;
		
		String[] values=data.split(",");
		String loginId=values[0],registeredUser=values[1];	
		boolean status=true;
		
	  try{
		// ==== Login ==== //			
		login(driver);
		
		// ==== Go to Registration ==== // 		
				
		deviceDriver.get(wdriver(driver)).switchTo().frame(0);
		Log.info("======== Clicking on User Registration History ========");
		deviceDriver.get(wdriver(driver)).findElement(By.partialLinkText("Registration")).click();
		
		
		deviceDriver.get(wdriver(driver)).findElement(By.id("txtFromDateDay")).clear();
		deviceDriver.get(wdriver(driver)).findElement(By.id("txtFromDateDay")).sendKeys("1");
		new Select(deviceDriver.get(wdriver(driver)).findElement(By.id("userAccessStringType"))).selectByIndex(2);
		deviceDriver.get(wdriver(driver)).findElement(By.id("userAccessString")).sendKeys(loginId);
		deviceDriver.get(wdriver(driver)).findElement(By.name("submit")).click();
		
		// ==== Verify Entry generated ====// 
		
		Log.info("========== Verifying DB Registration Entry for "+registeredUser+" ========");
		
		String xpDbCode="//td[contains(text(),'"+registeredUser.split(" ")[0]+"')]/..//td[8]"; // Cannot search entry based on mobile Number, also Registered user's name's space is not identified
		String xpKYC="//td[contains(text(),'"+registeredUser.split(" ")[0]+"')]/..//td[7]";
		
		try 
		{
			deviceDriver.get(wdriver(driver)).switchTo().frame(1);
			String codeDB=deviceDriver.get(wdriver(driver)).findElement(By.xpath(xpDbCode)).getText();		
			Log.info("======== DB Code retrieved :" +codeDB+" ========");
			
			String KYCStatus=deviceDriver.get(wdriver(driver)).findElement(By.xpath(xpKYC)).getText();
			Log.info("======== KYC Status : "+KYCStatus+" ========");
			
			Assert.assertTrue(codeDB.contains(programCode),"Program code "+codeDB+" does not match with "+programCode+"\n");	
			Assert.assertTrue(KYCStatus.toLowerCase().contains("non"),"KYC status :"+KYCStatus+" is incorrect\n");
		} 
		catch (Exception e) 
		{
			status=false;
			Assert.fail("DB Registration Entry not found for the user "+registeredUser+"\n"+e.getMessage());
		}
	  }
	  catch(Exception e)
	  {
		  status=false;
		  Assert.fail("Error in page \n"+e.getMessage());
	  }
	  deviceDriver.get(wdriver(driver)).quit();	
	  setCSRStatus(driver,false);
	}
	
	public static String getNewUser() // @Deprecated - Obtaining Newly registered user from static value
	{		
		
		String newlyRegisteredMobileNo="";
		boolean status=true;
		
	 try{
			// ==== Login ==== //			
			login();
			
			// ==== Go to Registration List ==== // 		
					
			wdriver.switchTo().frame(0);
			Log.info("======== Clicking on User Registration History ========");
			wdriver.findElement(By.partialLinkText("Registration")).click();				
			wdriver.findElement(By.name("submit")).click();
			
			// ==== Open any newly registered user ==== //				
			try 
			{
				wdriver.switchTo().frame(1);
				Log.info("======== Clicking on View Profile ========");
				wdriver.findElement(By.xpath("//input[@value='View Profile']")).click();					
				
				String xp="//td[text()='Associated mobile numbers']/..//div";
				newlyRegisteredMobileNo=wdriver.findElement(By.xpath(xp)).getText();
				Log.info("======== Retreiving a newly registered mobileNo :"+newlyRegisteredMobileNo+" ========");					
			} 
			catch (Exception e) 
			{
				status=false;
				Log.info("Could not fetch newly registered user from CSR "+e.getMessage());
			}
		 }
		  catch(Exception e)
		  {
			  status=false;		
			  Log.info("Unable to fetch newly registered user from CSR "+e.getMessage());
		  }
		  wdriver.quit();	
		  setCSRStatus(false);
		  
		  return newlyRegisteredMobileNo;
	}		
		
		
	

	
	/**
	 * Verifies AM transaction report.
	 *
	 * @param loginId the login id
	 */
	public static void verifyAMTransactionReport(String loginId)
	{
		// ==== Login ==== //	
		if(!CSRExecute) return;
	
		login();
		
		// ==== Go to AM Transaction History ==== //
		
		wdriver.switchTo().frame(0);
		Log.info("======== Clicking on AM Trans History ========");
		wdriver.findElement(By.partialLinkText("AM Trans")).click();
		
		wdriver.findElement(By.id("fromday")).clear();
		wdriver.findElement(By.id("fromday")).sendKeys("1");
		new Select(wdriver.findElement(By.id("userAccessStringType"))).selectByIndex(2);
		wdriver.findElement(By.id("userAccessString")).sendKeys(loginId);
		wdriver.findElement(By.name("submit")).click();
		
		// ==== Verify AM Transaction Report ==== //
		
		try
		{
			Log.info("======== Verifying for AM transaction report ========");
			wdriver.switchTo().frame(1);
			Assert.assertTrue(wdriver.findElement(By.id("BORDER_TABLE")).isDisplayed(),"AM Transaction report not found for "+loginId+'\n');
		}
		catch(Exception e)
		{
			Assert.fail("AM transaction report not found for "+loginId+'\n');
		}
	 
	 wdriver.quit();	
	 setCSRStatus(false);	
	}
	
	/**
	 * Verifies inflow transaction report.
	 *
	 * @param loginId the login id
	 */
	public static void verifyInflowTransactionReport(WebDriver driver,String loginId)
	{
		// ==== Login ==== //	
		if(!CSRExecute) return;
		
		login(driver);
				
		// ==== Go to Inflow Transaction History ==== //
		
		deviceDriver.get(wdriver(driver)).switchTo().frame(0);
		Log.info("======== Clicking on AM Trans History ========");
		deviceDriver.get(wdriver(driver)).findElement(By.partialLinkText("Inflow Trans")).click();
		
		
		deviceDriver.get(wdriver(driver)).findElement(By.id("fromday")).clear();
		deviceDriver.get(wdriver(driver)).findElement(By.id("fromday")).sendKeys("1");
		new Select(deviceDriver.get(wdriver(driver)).findElement(By.id("userAccessStringType"))).selectByIndex(2);
		deviceDriver.get(wdriver(driver)).findElement(By.id("userAccessString")).sendKeys(loginId);
		deviceDriver.get(wdriver(driver)).findElement(By.name("submit")).click();
		
		// ==== Verify Inflow Transaction Report ==== //
		
		Log.info("======== Verifying for Inflow Transaction Report ========");
		try
		{
			deviceDriver.get(wdriver(driver)).switchTo().frame(1);
			String bankCode=BaseTest1.bankCode;
			Assert.assertTrue(deviceDriver.get(wdriver(driver)).findElement(By.id("BORDER_TABLE")).isDisplayed(),"Inflow Transaction report not found for "+loginId+'\n');
			Log.info("======== Verifying Bank code "+bankCode+" in inflow transaction table ========");
			String bankCodeXp="//*[@id='BORDER_TABLE']//tr[2]//td[4]",
					bankCodeValue=deviceDriver.get(wdriver(driver)).findElement(By.xpath(bankCodeXp)).getText();
			
			Assert.assertTrue(bankCodeValue.contains(bankCode), "Bank Code "+bankCode+" was not found in "+bankCodeValue);			
		}
		catch(Exception e)
		{
			Assert.fail("Inflow transaction report not found for "+loginId+'\n');
		}	
		
		deviceDriver.get(wdriver(driver)).quit();	
		setCSRStatus(driver,false);
	}
	
	/**
	 * Verifies outflow transaction report.
	 *
	 * @param loginId the login id
	 */
	public static void verifyOutflowTransaction(WebDriver driver,String loginId)
	{
		if(!CSRExecute) return;
		
		// ==== Login ==== //			
		login(driver);
		
		// ==== Go to Outflow Transaction History ==== //

		deviceDriver.get(wdriver(driver)).switchTo().frame(0);
		Log.info("======== Clicking on Outflow Trans History ========");
		deviceDriver.get(wdriver(driver)).findElement(By.partialLinkText("Outflow Trans")).click();
	
	
		deviceDriver.get(wdriver(driver)).findElement(By.id("fromday")).clear();
		deviceDriver.get(wdriver(driver)).findElement(By.id("fromday")).sendKeys("1");
	new Select(deviceDriver.get(wdriver(driver)).findElement(By.id("userAccessStringType"))).selectByIndex(2);
	deviceDriver.get(wdriver(driver)).findElement(By.id("userAccessString")).sendKeys(loginId);
	deviceDriver.get(wdriver(driver)).findElement(By.name("submit")).click();
	
	// ==== Verify Outflow Transaction Report ==== //
	
	Log.info("======== Verifying for Outflow Transaction Report ========");
	try
	{
		deviceDriver.get(wdriver(driver)).switchTo().frame(1);
		String bankCode=BaseTest1.bankCode;
		Assert.assertTrue(deviceDriver.get(wdriver(driver)).findElement(By.id("BORDER_TABLE")).isDisplayed(),"Outflow Transaction report not found for "+loginId+'\n');
		Log.info("======== Verifying Bank code "+bankCode+" in outflow transaction table ========");
		String bankCodeXp="//*[@id='BORDER_TABLE']//tr[2]//td[4]",
				bankCodeValue=deviceDriver.get(wdriver(driver)).findElement(By.xpath(bankCodeXp)).getText();
		
		Assert.assertTrue(bankCodeValue.contains(bankCode), "Bank Code "+bankCode+" was not found in "+bankCodeValue);		
	}
	catch(Exception e)
	{
		Assert.fail("Outflow transaction report not found for "+loginId+'\n');
	}
  
	deviceDriver.get(wdriver(driver)).quit();	
	setCSRStatus(driver,false);	
		
}
	
	/**
	 * Verifies unclaimed funds report.
	 *
	 * @param loginId the login id
	 */
	public static void verifyUnclaimedFundsReport(String loginId)
	{
		if(!CSRExecute) return;
		// ==== Login ==== //			
			login();
			
		// ==== Go to Unclaimed funds report ==== //

		wdriver.switchTo().frame(0);
		Log.info("======== Clicking on Unclaimed funds report ========");
		wdriver.findElement(By.partialLinkText("Unclaimed")).click();
		
		
		wdriver.findElement(By.id("txtFromDateDay")).clear();
		wdriver.findElement(By.id("txtFromDateDay")).sendKeys("1");
		new Select(wdriver.findElement(By.id("userAccessStringType"))).selectByIndex(3);
		Log.info("======== Entering Search String : "+loginId+" ========");
		
		wdriver.findElement(By.id("searchString")).sendKeys(loginId);
		wdriver.findElement(By.name("submit")).click();
		
		// ==== Verify Unclaimed funds report ==== //
		Log.info("======== Verifying for Unclaimed funds ========");
		try
		{
			wdriver.switchTo().frame(1);
			Assert.assertTrue(wdriver.findElement(By.id("BORDER_TABLE")).isDisplayed(),"Unclaimed funds report not found for "+loginId+'\n');
		}
		catch(Exception e)
		{
			Assert.fail("Unclaimed funds report not found for "+loginId+'\n');
		}
	  
	  wdriver.quit();	
	  setCSRStatus(false);
		
	}
	
	/**
	 * Verifies user error report.
	 *
	 * @param loginId the login id
	 */
	public static void verifyUserErrorReport(String loginId)
	{
		if(!CSRExecute) return;
		// ==== Login ==== //			
		login();

		// ==== Go to Error Reports ==== // 		
						
		wdriver.switchTo().frame(0);
		Log.info("======== Clicking on User Error Report ========");
		wdriver.findElement(By.partialLinkText("User Error")).click();
		
		
		wdriver.findElement(By.id("txtFromDateDay")).clear();
		wdriver.findElement(By.id("txtFromDateDay")).sendKeys("1");
		new Select(wdriver.findElement(By.id("userAccessStringType"))).selectByIndex(3); // mobile
		wdriver.findElement(By.id("searchString")).sendKeys(loginId);
		wdriver.findElement(By.name("submit")).click();
		
		Log.info("========== Verifying Error Report table  ========");
		try
		{
			Assert.assertTrue(wdriver.findElement(By.id("userErrorTable")).isDisplayed(), "Error Report table not displayed for "+loginId+'\n');
		}
		catch(Exception e)
		{
			Assert.fail("User Error report table not displayed for "+loginId+'\n'+e.getMessage());
		}
	
		 wdriver.quit();	
		 setCSRStatus(false);	
	}
	
	/**
	 * Blocks the user with the given loginId.
	 *
	 * @param loginId the login id
	 */
	public static void block(String loginId)
	{
		if(!CSRExecute) return;
		
		softAssert = new SoftAssert();
		int searchType=3; // mobileNo.
		
		if(loginId.contains("@")) searchType=2; // email
		if(loginId.length()==14) searchType=1;  // Wibmo acct no.
		
	 
		login();
		
		// ==== Go to Block Unblock user ==== //
		
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("Block")).click();
		
		new Select(wdriver.findElement(By.id("userSearchStringType"))).selectByIndex(searchType);
		Log.info("======== Entering Mobile Number "+loginId+" ========");
		wdriver.findElement(By.id("searchString")).sendKeys(loginId);
		wdriver.findElement(By.name("submit")).click();
		
		// ==== Go to user profile ==== //
		try
		{
			wdriver.switchTo().frame(1);		
			wdriver.findElement(By.name("submit")).click();
		}
		catch(Exception e)
		{
			Assert.fail("Record for user with id "+loginId+" not found"+e.getMessage());
		}
		
		// ==== Block User ==== //
		
		//wdriver.switchTo().frame(1);
		Log.info("======== Clicking on Block Account Button ========");
		try
		{
			wdriver.findElement(By.xpath("//input[@value='Block Account']")).click();
		}
		catch(Exception e)
		{
			Log.info("======== Account already blocked ========");
			wdriver.quit();
			return;
		}
		Log.info("======== Clicking on confirmation OK button =======");
		wdriver.switchTo().alert().accept();
		
		try {
			Alert blockSuccessfulMsg = wdriver.switchTo().alert();
			Log.info("======== Verifying Successful Block Message: "+blockSuccessfulMsg.getText()+" ========");
			softAssert.assertTrue(blockSuccessfulMsg.getText().contains("Inactive"),"Block not successful\n");			
			blockSuccessfulMsg.accept();
		} 
		catch (Exception e) 
		{
			softAssert.fail("Block was not successful\n");
		}
		softAssert.assertAll();		
		 wdriver.quit();	
		 setCSRStatus(false);
		
	}
	
	/**
	 * Unblocks the user with the given loginId.
	 *
	 * @param loginId the login id
	 */
	public static void unBlock(String loginId)
	{
		if(!CSRExecute) return;
		
		softAssert = new SoftAssert();
		int searchType=3; // mobileNo.
		
		if(loginId.contains("@")) searchType=2; // email
		if(loginId.length()==14) searchType=1;  // Wibmo acct no.
		
	try{	
		login();
		
		// ==== Go to Block Unblock user ==== //
		
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("Unblock")).click();
		
		new Select(wdriver.findElement(By.id("userSearchStringType"))).selectByIndex(searchType);
		Log.info("======== Entering Mobile Number "+loginId+" ========");
		wdriver.findElement(By.id("searchString")).sendKeys(loginId);
		wdriver.findElement(By.name("submit")).click();
		
		// ==== Go to user profile ==== //
		try
		{
			wdriver.switchTo().frame(1);		
			wdriver.findElement(By.name("submit")).click();
		}
		catch(Exception e)
		{
			Assert.fail("Record for user with id "+loginId+" not found"+e.getMessage());
		}
		
		// ==== Block User ==== //
		
		//wdriver.switchTo().frame(1);
		Log.info("======== Clicking on Unblock Account Button ========");
		
		try
		{
			wdriver.findElement(By.xpath("//input[@value='Unblock Account']")).click();
		}
		catch(Exception e)
		{
			Log.info("======== Account is unblocked ========");
			wdriver.quit();
			return;			
		}
		Log.info("======== Clicking on confirmation ========");
		wdriver.switchTo().alert().accept();
		
		try 
		{
			Alert blockSuccessfulMsg = wdriver.switchTo().alert();
			Log.info("======== Verifying Successfull Unblock Message: "+blockSuccessfulMsg.getText()+" ========");
			softAssert.assertTrue(blockSuccessfulMsg.getText().contains("Active"),"Unblock not successful\n");
			blockSuccessfulMsg.accept();
		}
		catch (Exception e) 
		{
			softAssert.fail("Account was not unblocked\n"+e.getMessage());
		}
	}
		finally
		{
			softAssert.assertAll();		
			
		}		
	wdriver.quit();
	setCSRStatus(false);
	}
	
	public static String setCVVStatus(WebDriver driver,String cardDetails,String cvvStatus)
	{
		String cardNumber="";
		if(cardDetails.contains(":") &&  cardDetails.split(":").length>3) // App SDK TestData
			 cardNumber=cardDetails.split(":")[3];
		else if(cardDetails.contains(":") &&  cardDetails.split(":")[1].length()>14) // Web SDK TestData 
			cardNumber=cardDetails.split(":")[1];
		else
			Assert.fail("Card Number not found in card details TestData "+cardDetails);
		
		// ==== Login & Navigate to BIN ==== //
		navigateToBin(driver);
		
		String bin=cardNumber.substring(0, 6);
		
		Log.info("======== Entering bin range : "+bin+" ========");
		deviceDriver.get(wdriver(driver)).findElement(By.id("bin")).sendKeys(bin);
		
		Log.info("======== Clicking on Fetch Report ========");
		deviceDriver.get(wdriver(driver)).findElement(By.name("submit")).click();			
		
		// ==== Modify CVV ==== //
		try
		{
			deviceDriver.get(wdriver(driver)).switchTo().frame(1);
			
			String currentCVVStatus=deviceDriver.get(wdriver(driver)).findElement(By.xpath("//td[3]//center")).getText();
			if(currentCVVStatus.contains(cvvStatus))  // cannot use Generic.containsIgnoreCase(currentCVVStatus, cvvStatus) Unknown contains no
			{
				Log.info("======== CVV status already set to "+currentCVVStatus+" ========");
				deviceDriver.get(wdriver(driver)).quit();
				setCSRStatus(false);
				return currentCVVStatus; // Check Unknown to "Resulting" flow
			}
			
			Log.info("======== Clicking on Modify ========");
			deviceDriver.get(wdriver(driver)).findElement(By.xpath("//button[contains(text(),'Modify')]")).click();
			
			Log.info("======== Switching to Bin window ========");
			String parent=deviceDriver.get(wdriver(driver)).getWindowHandle();
			Set<String> handles=deviceDriver.get(wdriver(driver)).getWindowHandles();
			for(String handle:handles)
				if(!handle.equals(parent))
					deviceDriver.get(wdriver(driver)).switchTo().window(handle);
			
			Log.info("======== Setting CVV Required status to "+cvvStatus+" ========");
			new Select(deviceDriver.get(wdriver(driver)).findElement(By.id("cvvRequired"))).selectByVisibleText(cvvStatus);
			
			Log.info("======== Clicking on Modify ========");
			deviceDriver.get(wdriver(driver)).findElement(By.name("submit")).click();
			
			String updSuccessMsg=deviceDriver.get(wdriver(driver)).findElement(By.xpath("//td[contains(text(),'Success')]")).getText();
			Log.info("======== Verifying Update Success message : "+updSuccessMsg+" ========");					
			
		}
		catch(Exception e)
		{
			Assert.fail("Error in setting "+cvvStatus+" CVV status for bin "+bin+'\n'+e.getMessage());
		}	
		
		deviceDriver.get(wdriver(driver)).quit();
		setCSRStatus(driver,false);
		return "";
		
	}
	
	/**
	 * Verifies IAP transaction data pickup success.
	 *
	 * @param transactionStatusMsg the transaction status msg
	 */
	public static void verifyIAPTransactionDataPickupSuccess(WebDriver driver,String transactionStatusMsg)
	{
		if(!CSRExecute) return;
		
		Log.info("======== Verifying transaction Data Pickup Success ========");
		String txnId; //String txnId="201602081020374630oF01kR7";
		
		if(transactionStatusMsg.contains(":"))
		 txnId=transactionStatusMsg.split(":")[2].trim();	
		else
		  txnId=transactionStatusMsg; // For Web transaction
		
		String dataPickupStatusMsg="",dataPickupStatusCode="";
				
		login(driver);		
		
		// ==== Open transaction report ==== //
		
		Log.info("======== Clicking on IAP Txn Report link ========");
		deviceDriver.get(wdriver(driver)).switchTo().frame(0);
		deviceDriver.get(wdriver(driver)).findElement(By.partialLinkText("IAP Txn Report")).click();
		
		Log.info("======== Entering transaction ID : "+txnId+" ========");
		deviceDriver.get(wdriver(driver)).findElement(By.id("txnID")).sendKeys(txnId);		
		
		Log.info("======== Clicking on Fetch Report ========");
		deviceDriver.get(wdriver(driver)).findElement(By.name("submit")).click(); 		
		
		deviceDriver.get(wdriver(driver)).switchTo().frame(1);
		
		String dataPickupMsgXp="(//center[contains(text(),'Data Pickup Status Msg')]/../../../../..//td[20])[2]";
		String dataPickupCodeXp="(//center[contains(text(),'Data Pickup Status Code')]/../../../../..//td[19])[2]";
		
		
		Log.info("======== Verifying Record for txnId : "+txnId+" ========");
		try
		{			
			dataPickupStatusMsg=deviceDriver.get(wdriver(driver)).findElement(By.xpath(dataPickupMsgXp)).getText();	
			dataPickupStatusCode=deviceDriver.get(wdriver(driver)).findElement(By.xpath(dataPickupCodeXp)).getText();
			Log.info("======== Verifying Data Pickup Status Msg and Status Code : "+dataPickupStatusMsg+'\t'+dataPickupStatusCode+" ========");
		}
		catch(Exception e)
		{		
			Assert.fail("No record found for Txn ID : "+txnId+"\n"+e.getMessage());
		}			
		Assert.assertTrue(dataPickupStatusMsg.toLowerCase().contains("done") || dataPickupStatusMsg.toLowerCase().contains("success"),"Data Pickup Status message does not match\n" );
		Assert.assertEquals(dataPickupStatusCode,"030","Data Pickup status code incorrect\n");
		
		deviceDriver.get(wdriver(driver)).quit();
		setCSRStatus(driver,false);
	}	
	
	/**
	 * Verifies IAP transaction data pickup failure.
	 *
	 * @param transactionStatusMsg the transaction status msg
	 */
	public static void verifyIAPTransactionDataPickupFailure(String transactionStatusMsg)
	{
		if(!CSRExecute) return;
		
		Log.info("======== Verifying for transaction Data Pickup Failure ========");
				 
				String dataPickupStatusMsg="",txnId;
				if(transactionStatusMsg.contains(":"))
					 txnId=transactionStatusMsg.split(":")[2].trim();	
					else
					  txnId=transactionStatusMsg; // For Web transaction
						
				login();		
				
				// ==== Open transaction report ==== //
				
				Log.info("======== Clicking on IAP Txn Report link ========");
				wdriver.switchTo().frame(0);
				wdriver.findElement(By.partialLinkText("IAP Txn Report")).click();
				
				Log.info("======== Entering transaction ID : "+txnId+" ========");
				wdriver.findElement(By.id("txnID")).sendKeys(txnId);		
				
				Log.info("======== Clicking on Fetch Report ========");
				wdriver.findElement(By.name("submit")).click(); 		
				
				wdriver.switchTo().frame(1);
				
				String dataPickupXp="(//center[contains(text(),'Data Pickup Status Msg')]/../../../../..//td[20])[2]";
				
				
				Log.info("======== Verifying Record for txnId : "+txnId+" ========");
				try
				{			
					dataPickupStatusMsg=wdriver.findElement(By.xpath(dataPickupXp)).getText();	
					Log.info("======== Verifying Data Pickup Status Msg : "+dataPickupStatusMsg+" ========");
				}
				catch(Exception e)
				{		
					Assert.fail("No record found for Txn ID : "+txnId+"\n"+e.getMessage());
				}				
				Assert.assertTrue(dataPickupStatusMsg.toLowerCase().contains("na") || !dataPickupStatusMsg.toLowerCase().contains("done") );
				
				wdriver.quit();
				setCSRStatus(false);
		
	}
	
	/**
	 * Changes the date Of Birth for a given user.
	 *
	 * @param data the data
	 */
	public static void changeDOB(String data)
	{
		if(!CSRExecute) return;
		
		String newDOB=data.split(",")[4],userId=data.split(",")[0];// To be updated
		String [] values=newDOB.split("/");
		String date=values[0],month=values[1],year=values[2];
		
		if(date.startsWith("0")) date=date.substring(1);
		
		login();
		
		// ==== Go to User Profile ==== //
		
		wdriver.switchTo().frame(0);
		Log.info("======== Clicking on Search Wibmo User link ========");
		wdriver.findElement(By.partialLinkText("Wibmo User")).click();
		
		Log.info("======== Selecting Search type : mobile ========");
		new Select(wdriver.findElement(By.id("userSearchStringType"))).selectByValue("0");
		
		Log.info("======== Entering Search String : "+userId+" ========");
		wdriver.findElement(By.id("searchString")).sendKeys(data.split(",")[0]);
		
		Log.info("======== Clicking on Fetch Report button ========");
		wdriver.findElement(By.name("submit")).click();
		
		Generic.wait(2);
		wdriver.switchTo().frame(1);
		Log.info("======== Clicking on View Profile button of "+userId+" record ========");
		try
			{ wdriver.findElement(By.xpath("//input[@value='View Profile']")).click();}
		catch(Exception e)
			{ Assert.fail("No Record found for "+userId+'\n'+e.getMessage());}				
		
		//wdriver.switchTo().frame(1); 
		Log.info("======== Clicking on Calendar icon ========");
		wdriver.findElement(By.tagName("img")).click();
		
		Log.info("======== Clicking on Select Date ========");
		wdriver.findElement(By.id("datepicker")).click();
		
		Log.info("======== Selecting Month : "+month+" ========");
		new Select(wdriver.findElement(By.xpath("//select[1]"))).selectByIndex(Integer.parseInt(month)-1);
		
		Log.info("======== Selecting Year : "+year+" ========");
		new Select(wdriver.findElement(By.xpath("//select[2]"))).selectByValue(year);
		
		Log.info("======== Selecting Date :"+date+" ========");
		wdriver.findElement(By.linkText(date)).click();	
		
		Log.info("======== Clicking on submit button ========");
		wdriver.findElement(By.xpath("//input[@value='submit']")).click();
		
		Alert confirm=wdriver.switchTo().alert();
		confirm.accept();	
		
		Alert dobStatus=wdriver.switchTo().alert();
		String updatedStatusMsg=dobStatus.getText();
		
		Log.info("======== Verifying DOB update status : "+updatedStatusMsg+" ========");
		Assert.assertTrue(updatedStatusMsg.toLowerCase().contains("successfully"),"User DOB not updated\n");
		
		dobStatus.accept();
		
		wdriver.quit();
		setCSRStatus(false);	
	}
	
	/**
	 * Verifies timeout status.
	 *
	 * @param data the data
	 */
	public static void verifyTimeoutStatus(String data)
	{
		if(!CSRExecute) return;
		
		login();
		
		String accNo=retrieveAccNo(data);
		
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("IAP Txn Report")).click();
		
		Log.info("======== Entering Account No. : "+accNo+" ========");
		wdriver.findElement(By.id("accountNumber")).sendKeys(accNo);

		Log.info("======== Clicking on Fetch Report ========");
		wdriver.findElement(By.name("submit")).click();		
				
		String timeoutXp="(//center[contains(text(),'Code/Desc')]/../../../../..//td[13])[2]";
		
		wdriver.switchTo().frame(1);
		try
		{
			Log.info("======== Verifying Status : "+wdriver.findElement(By.xpath(timeoutXp)).getText()+" ========");
			Assert.assertTrue(wdriver.findElement(By.xpath(timeoutXp)).getText().toLowerCase().contains("timeout"), "Timeout status not found for the given transaction\n");
		}
		catch(Exception e)
		{
			Assert.fail("Timeout status not found for the given transaction\n"+e.getMessage());
		}	
		wdriver.quit();
		setCSRStatus(false);
	}
	
	/**
	 * Retrieves the account number for the given user.
	 *
	 * @param data the data
	 * @return the string
	 */
	public static String retrieveAccNo(String data)
	{
		
		String mobileNo=data.split(",")[0],accNo;
		
		Log.info("======== Clicking on Search Wibmo User ========");
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("Wibmo User")).click();
		
		Log.info("======== Selecting Search type : mobile ========");
		new Select(wdriver.findElement(By.id("userSearchStringType"))).selectByValue("0");
		
		Log.info("======== Entering Search String : "+mobileNo+" ========");
		wdriver.findElement(By.id("searchString")).sendKeys(mobileNo);
		
		Log.info("======== Clicking on Fetch Report button ========");
		wdriver.findElement(By.name("submit")).click();
		
		wdriver.switchTo().frame(wdriver.findElement(By.xpath("(//iframe)[2]")));
		Log.info("======== Clicking on View Profile button of "+mobileNo+" record ========");
		try
			{ wdriver.findElement(By.xpath("//input[@value='View Profile']")).click();}
		catch(Exception e)
			{ Assert.fail("No Record found for "+mobileNo+'\n'+e.getMessage());}		
		
		Log.info("======== Clicking on Show More Info button ========");
		wdriver.findElement(By.xpath("//input[@value='Show more info']")).click();
		
		Log.info("==== Switching to User Account details window ========");
		String parent=wdriver.getWindowHandle();
		Set<String> handles=wdriver.getWindowHandles();
		for(String handle:handles)
			if(!handle.equals(parent))
				wdriver.switchTo().window(handle);
		
		
		accNo=wdriver.findElement(By.xpath("//div[contains(text(),'Account Number')]/../..//b")).getText();
		Log.info("======== Retrieving Acc No. : "+accNo);
		
		wdriver.close();
		wdriver.switchTo().window(parent);
		return accNo;
	}
	
	/**
	 * Verifies balance in the Wibmo account for a given user.
	 *
	 * @param loginId the login id
	 * @return the balance in double
	 */
	public static double verifyBalance(WebDriver driver,String loginId)
	{		
		String  bal="0.00";
		
		if(!CSRExecute) return 0.00;
		
		login(driver);
		
		Log.info("======== Clicking on Search Wibmo User ========"); 
		deviceDriver.get(wdriver(driver)).switchTo().frame(0);
		deviceDriver.get(wdriver(driver)).findElement(By.partialLinkText("Wibmo User")).click();
		
		Log.info("======== Selecting Search type : mobile ========");
		new Select(deviceDriver.get(wdriver(driver)).findElement(By.id("userSearchStringType"))).selectByValue("0");
		
		Log.info("======== Entering Search String : "+loginId+" ========");
		deviceDriver.get(wdriver(driver)).findElement(By.id("searchString")).sendKeys(loginId);
		
		Log.info("======== Clicking on Fetch Report button ========");
		deviceDriver.get(wdriver(driver)).findElement(By.name("submit")).click();
		
		deviceDriver.get(wdriver(driver)).switchTo().frame(deviceDriver.get(wdriver(driver)).findElement(By.xpath("(//iframe)[2]")));
		Log.info("======== Clicking on View Profile button of "+loginId+" record ========");
		try
			{ deviceDriver.get(wdriver(driver)).findElement(By.xpath("//input[@value='View Profile']")).click();}
		catch(Exception e)
			{ Assert.fail("No Record found for "+loginId+'\n'+e.getMessage());}		
		
		Log.info("======== Clicking on Show More Info button ========");
		deviceDriver.get(wdriver(driver)).findElement(By.xpath("//input[@value='Show more info']")).click();
		
		Log.info("======== Switching to User Account details window ========");
		String parent=deviceDriver.get(wdriver(driver)).getWindowHandle();
		Set<String> handles=deviceDriver.get(wdriver(driver)).getWindowHandles();
		for(String handle:handles)
			if(!handle.equals(parent))
				deviceDriver.get(wdriver(driver)).switchTo().window(handle);		
		try
		{
			if(errorInChildWindow(deviceDriver.get(wdriver(driver)))) deviceDriver.get(wdriver(driver)).navigate().refresh();
				
			bal=deviceDriver.get(wdriver(driver)).findElement(By.xpath("//div[contains(text(),'Balance')]/../..//b")).getText();
			Log.info("======== Retrieving Bal. : "+bal+" ========");			
		}
		catch(Exception e)
		{
			Log.info("======== Unable to retrieve balance , retrying ========");
			deviceDriver.get(wdriver(driver)).navigate().refresh();
			try{bal=deviceDriver.get(wdriver(driver)).findElement(By.xpath("//div[contains(text(),'Balance')]/../..//b")).getText();}
			catch(Exception e1){Assert.fail("Balance not found for "+loginId+'\n'+e.getMessage());}
		}	
	
		deviceDriver.get(wdriver(driver)).quit();
		setCSRStatus(driver,false);
		return Double.parseDouble(bal.substring(3).replaceAll(",",""));		
	}
	
	/**
	 *  Retrieves the Account Number from CSR 
	 * 
	 * @param driver
	 * @param loginId
	 * @return
	 */
	public static String getPcAcNumber(WebDriver driver,String loginId)
	{
		String  pcAcNumber="DefaultAccountNumber";
				
		if(!CSRExecute) return "";
		String loginIdTypeIndex=loginId.contains("@")?"1":"0";
		
		// ==== Login and set wdriver specific to Main driver ==== //
		login(driver);
		WebDriver wdriver=deviceDriver.get(wdriver(driver));
		// ===================================// 
		
		Log.info("======== Clicking on Search Wibmo User ========"); 
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("Wibmo User")).click();
		
		wdriver.switchTo().defaultContent();
		Log.info("======== Selecting Search type : mobile ========");
		new Select(wdriver.findElement(By.id("userSearchStringType"))).selectByValue(loginIdTypeIndex);
		
		Log.info("======== Entering Search String : "+loginId+" ========");
		wdriver.findElement(By.id("searchString")).sendKeys(loginId);
		
		Log.info("======== Clicking on Fetch Report button ========");
		wdriver.findElement(By.name("submit")).click();
		
		wdriver.switchTo().frame(deviceDriver.get(wdriver(driver)).findElement(By.xpath("(//iframe)[2]")));
		Log.info("======== Clicking on View Profile button of "+loginId+" record ========");
		try
			{ wdriver.findElement(By.xpath("//input[@value='View Profile']")).click();}
		catch(Exception e)
			{ Assert.fail("No Record found for "+loginId+'\n'+e.getMessage());}		
		
		Log.info("======== Clicking on Show More Info button ========");
		wdriver.findElement(By.xpath("//input[@value='Show more info']")).click();
		
		Log.info("======== Switching to User Account details window ========");
		String parent=wdriver.getWindowHandle();
		Set<String> handles=wdriver.getWindowHandles();
		for(String handle:handles)
			if(!handle.equals(parent))
				wdriver.switchTo().window(handle);
		
		try
		{
			if(errorInChildWindow(wdriver)) wdriver.navigate().refresh();   // Attempt 1 
				
			pcAcNumber=wdriver.findElement(By.xpath("//div[contains(text(),'Account Number')]/../..//b")).getText();
			Log.info("======== Retrieving Account Number. : "+pcAcNumber+" ========");			
		}
		catch(Exception e)
		{
			Log.info("======== Unable to retrieve balance , retrying ========");  // // Attempt 2
			wdriver.navigate().refresh();
			try{pcAcNumber=wdriver.findElement(By.xpath("//div[contains(text(),'Account Number')]/../..//b")).getText();}
			catch(Exception e1){Assert.fail("Account Number not found for "+loginId+'\n'+e.getMessage());}
		}	
	
		wdriver.quit();
		setCSRStatus(driver,false);
		return pcAcNumber;	
	}

	
	public static boolean errorInChildWindow(WebDriver driver)
	{		
		String errMsgXp="//*[contains(text(),'Error') or contains(text(),'ERROR') or contains(text(),'Balance') or contains(text(),'Success')]";
						//Use for both User Balance Window and BIN Modify Window
		try
		{
			//Generic.wait(2);
			String errTxt=driver.findElement(By.xpath(errMsgXp)).getText();
			
			if(Generic.containsIgnoreCase(errTxt, "Error"))	
			{
				Log.info("== Error message in Child Window : "+errTxt+" ==");
				return true;
			}
			else
				return false;
		}
		catch(Exception e){return true;}
	}
	
	
	public static boolean unblockLinkedCard(String loginId)
	{
		Log.info("========~ Attempting CSR Wallet Card Unlock ========");
		boolean unlockStatus=false;
		login();
		
		Log.info("======== Clicking on Search Wibmo User ========");
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("Wibmo User")).click();
		
		Log.info("======== Selecting Search type : mobile ========");
		new Select(wdriver.findElement(By.id("userSearchStringType"))).selectByValue("0");
		
		Log.info("======== Entering Search String : "+loginId+" ========");
		wdriver.findElement(By.id("searchString")).sendKeys(loginId);
		
		Log.info("======== Clicking on Fetch Report button ========");
		wdriver.findElement(By.name("submit")).click();
		
		wdriver.switchTo().frame(wdriver.findElement(By.xpath("(//iframe)[2]")));
		Log.info("======== Clicking on View Profile button of "+loginId+" record ========");
		try
			{ wdriver.findElement(By.xpath("//input[@value='View Profile']")).click();}
		catch(Exception e)
			{ Assert.fail("No Record found for "+loginId+'\n'+e.getMessage());}		
		
		Log.info("======== Clicking on Show More Info button ========");
		wdriver.findElement(By.xpath("//input[@value='Show more info']")).click();
		
		Log.info("======== Switching to User Account details window ========");
		String parent=wdriver.getWindowHandle();
		Set<String> handles=wdriver.getWindowHandles();
		for(String handle:handles)
			if(!handle.equals(parent))
				wdriver.switchTo().window(handle);	
		
		String unlockCheckText=wdriver.findElement(By.xpath("//a[contains(@href,'changeUserCardStatus')]")).getText();		
		if(unlockCheckText.contains("Unblock"))
		{
			Log.info("======== Clicking on Unblock Linked Card ========");
			wdriver.findElement(By.xpath("//a[contains(@href,'changeUserCardStatus')]")).click();	
			
			try
			{
				String successMsgXp="//td[contains(text(),'Successfully')]";
				new WebDriverWait(wdriver, 3).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(successMsgXp)));
				Log.info("======== Verifying success message : "+wdriver.findElement(By.xpath(successMsgXp)).getText()+" ========");
				unlockStatus=true;
			}
			catch(Exception e)
			{
				Log.info("======== Success message not found , attempting reclick ========");
				try{new WebDriverWait(wdriver, 3).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'Successfully')]")));}
					catch(Exception e1){Assert.fail("Unable to unlock linked card \n"+e1.getMessage());}
				unlockStatus=false;
			}				
		}
		else
		{
			Log.info("======== Card already unlocked . Verifying link : "+unlockCheckText+" ========");
			unlockStatus=true;
		}
		
		wdriver.quit();
		setCSRStatus(false);
		return unlockStatus;
	}
	
	
	
	
	
	/**
	 * Returns the mobile number corresponding to the given email.
	 *
	 * @param emailId the email id
	 * @return the string
	 */
	public static String emailToMobile(String emailId)
	{
		String mobileNo="";
		Log.info("======== Retreiving mobile no. corresponding to "+emailId+" for retrieving DVC ========");			
		
		login();
		
		Log.info("======== Clicking on Search Wibmo User ========");
		wdriver.switchTo().frame(0);
		wdriver.findElement(By.partialLinkText("Wibmo User")).click();
		
		Log.info("======== Selecting Search type : email ========");
		new Select(wdriver.findElement(By.id("userSearchStringType"))).selectByValue("1");
		
		Log.info("======== Entering Search String : "+emailId+" ========");
		wdriver.findElement(By.id("searchString")).sendKeys(emailId);
		
		Log.info("======== Clicking on Fetch Report button ========");
		wdriver.findElement(By.name("submit")).click();
		
		
		try
		{ 
			wdriver.switchTo().frame(wdriver.findElement(By.xpath("(//iframe)[2]")));
			Log.info("======== Clicking on View Profile button of "+emailId+" record ========");
			wdriver.findElement(By.xpath("//input[@value='View Profile']")).click();
		}
		catch(Exception e)
			{ Assert.fail("No Record found for "+emailId+'\n'+e.getMessage());}	
		
		String xp="//td[text()='Associated mobile numbers']/..//div";
		
		try
		{	
			mobileNo=wdriver.findElement(By.xpath(xp)).getText();
			Log.info("======== Retreiving mobileNo :"+mobileNo+" ========");
		}
		catch(Exception e)
		{
			Assert.fail("Mobile Number corresponding to "+emailId+" not found"+e.getMessage());
		}
	
		wdriver.quit();
		setCSRStatus(false);
		return mobileNo;		
	}
	
	
	/**
	 * Verifies IAP transaction details based on the transaction status message.
	 *
	 * @param transactionStatusMsg the transaction status msg
	 */
	public static void verifyIAPTXNDetails(WebDriver driver,String transactionStatusMsg)
	{
		if(!CSRExecute) return;
		
		if(transactionStatusMsg==null) 
			{Log.info("Transaction Status not updated \n");return;}
		softAssert = new SoftAssert();
		boolean status=true;
		
		String txnId=transactionStatusMsg.contains(":")?transactionStatusMsg.split(":")[2].trim():transactionStatusMsg; 
		WebElement statusCodeOrDesc,status3Ds,authStatus,pgTxnId,pgStatusOrDesc;		
		
	
		login(driver);		
		
		// ==== Open transaction report ==== //
		
		Log.info("======== Clicking on IAP Txn Report link ========");
		deviceDriver.get(wdriver(driver)).switchTo().frame(0);
		deviceDriver.get(wdriver(driver)).findElement(By.partialLinkText("IAP Txn Report")).click();
		
		Log.info("======== Entering transaction ID : "+txnId+" ========");
		deviceDriver.get(wdriver(driver)).findElement(By.id("txnID")).sendKeys(txnId);		
		
		Log.info("======== Clicking on Fetch Report ========");
		deviceDriver.get(wdriver(driver)).findElement(By.name("submit")).click(); 		
		
		String statusOrDescXP="(//center[contains(text(),'Status Code/Desc')]/../../../../..//td[13])[2]";
						
		Log.info("======== Verifying Record for txnId : "+txnId+" ========");
		try
		{	
			deviceDriver.get(wdriver(driver)).switchTo().frame(1);
			statusCodeOrDesc=deviceDriver.get(wdriver(driver)).findElement(By.xpath(statusOrDescXP));	
			Log.info("======== Verifying Status code Or Desc : "+statusCodeOrDesc.getText()+" ========");
			softAssert.assertTrue(statusCodeOrDesc.isDisplayed());
		}
		catch(Exception e)
		{	
			status=false;
			Assert.fail("No record found for Txn ID : "+txnId+"\n"+e.getMessage());			
		}
		
		String status3DsXP="(//center[contains(text(),'3DS Status')]/../../../../..//td[14])[2]";
		
		try
		{			
			status3Ds=deviceDriver.get(wdriver(driver)).findElement(By.xpath(statusOrDescXP));	
			Log.info("======== Verifying 3DS Status Msg : "+status3Ds.getText()+" ========");
			softAssert.assertTrue(status3Ds.isDisplayed());
			
			String authStatusXP="(//center[contains(text(),'Auth Status')]/../../../../..//td[15])[2]";
			authStatus=deviceDriver.get(wdriver(driver)).findElement(By.xpath(authStatusXP));	
			Log.info("======== Verifying Auth Status Msg : "+authStatus.getText()+" ========");
			softAssert.assertTrue(authStatus.isDisplayed());
			
			String pgTxnIdXP="(//center[contains(text(),'PG Txn ID')]/../../../../..//td[16])[2]";
			pgTxnId=deviceDriver.get(wdriver(driver)).findElement(By.xpath(pgTxnIdXP));	
			Log.info("======== Verifying Pg Txn Id Msg : "+pgTxnId.getText()+" ========");
			softAssert.assertTrue(pgTxnId.isDisplayed());
			
			String pgStatusOrDescXP="(//center[contains(text(),'PG Status/Desc')]/../../../../..//td[16])[2]";
			pgStatusOrDesc=deviceDriver.get(wdriver(driver)).findElement(By.xpath(pgStatusOrDescXP));	
			Log.info("======== Verifying Pg Status or Desc Msg : "+pgStatusOrDesc.getText()+" ========");
			softAssert.assertTrue(pgStatusOrDesc.isDisplayed());					
			
		}
		catch(Exception e)
		{	
			status=false;
			softAssert.fail("Error in record found for Txn ID : "+txnId+"\n"+e.getMessage());
		}
		
	deviceDriver.get(wdriver(driver)).quit();
	setCSRStatus(driver,false);			
		
	}	
	
	/**
	 * Searches for the wibmo user based on the mobile number.
	 *
	 * @param mobileNo the mobile no
	 * @param username the username
	 */
	public static void searchWibmoUser(String mobileNo,String username)
	{
		if(!CSRExecute) return;
		login();
		
		// ==== Go to User Profile ==== //
		
		wdriver.switchTo().frame(0);
		Log.info("======== Clicking on Search Wibmo User link ========");
		wdriver.findElement(By.partialLinkText("Wibmo User")).click();
		
		Log.info("======== Selecting Search type : mobile ========");
		new Select(wdriver.findElement(By.id("userSearchStringType"))).selectByValue("0");
		
		Log.info("======== Entering Search String : "+mobileNo+" ========");
		wdriver.findElement(By.id("searchString")).sendKeys(mobileNo);
		
		Log.info("======== Clicking on Fetch Report button ========");
		wdriver.findElement(By.name("submit")).click();
		
		
		try
		{
			wdriver.switchTo().frame(wdriver.findElement(By.xpath("(//iframe)[2]")));
			Log.info("======== Verifying record for registered user : "+username+" ========");
			String xp="//td[contains(text(),'"+username.split(" ")[0]+"')]";
			Assert.assertTrue(wdriver.findElement(By.xpath(xp)).isDisplayed(),"No entry found for username :"+username+'\n' );
		}
		catch(Exception e)
		{
			Assert.fail("No entry found for the username :"+username+'\n'+e.getMessage());
		}
		
		wdriver.quit();
		setCSRStatus(false);
	}
	
	/**
	 * Verifies user devices.
	 *
	 * @param loginId the login id
	 */
	public static void verifyUserDevices(String loginId)
	{	
		if(!CSRExecute) return;
		login();
		
		// ==== Go to User Profile ==== //
		
		wdriver.switchTo().frame(0);
		Log.info("======== Clicking on Search Wibmo User link ========");
		wdriver.findElement(By.partialLinkText("Wibmo User")).click();
		
		Log.info("======== Selecting Search type : mobile ========");
		new Select(wdriver.findElement(By.id("userSearchStringType"))).selectByValue("0");
		
		Log.info("======== Entering Search String : "+loginId+" ========");
		wdriver.findElement(By.id("searchString")).sendKeys(loginId);
		
		Log.info("======== Clicking on Fetch Report button ========");
		wdriver.findElement(By.name("submit")).click();
		
		wdriver.switchTo().frame(wdriver.findElement(By.xpath("(//iframe)[2]")));
		Log.info("======== Verifying Devices for  "+loginId+" ========");
		try
		{
			Assert.assertTrue(wdriver.findElement(By.id("BORDER_TABLE")).isDisplayed(),"No Devices found for "+loginId+'\n' );
		}
		catch(Exception e)
		{
			Assert.fail("No Devices were found for"+loginId+'\n'+e.getMessage());
		}
		
		wdriver.quit();
		setCSRStatus(false);
		
	}
	
	/**
	 * Verifies login report.
	 *
	 * @param loginId the login id
	 * @param username the username
	 */
	public static void verifyLoginReport(String loginId,String username)
	{
		if(!CSRExecute) return;
		login();
		
		// ==== Go to User Login Report ==== //
		
		wdriver.switchTo().frame(0);
		Log.info("======== Clicking on User Login Report link ========");
		wdriver.findElement(By.partialLinkText("User Login")).click();		
		
		wdriver.findElement(By.id("txtFromDateDay")).clear();
		wdriver.findElement(By.id("txtFromDateDay")).sendKeys("1");
		
		new Select(wdriver.findElement(By.id("userAccessStringType"))).selectByIndex(3);
		wdriver.findElement(By.id("searchString")).sendKeys(loginId);
		wdriver.findElement(By.name("submit")).click();
		
		// ==== Verifying User Login report ==== //
		
		wdriver.switchTo().frame(wdriver.findElement(By.xpath("(//iframe)[2]")));
		Log.info("======== Verifying Login record for user : "+username+" ========");	
		String xp="//td[contains(text(),'"+username.split(" ")[0]+"')]";
		try
		{
			Assert.assertTrue(wdriver.findElement(By.xpath(xp)).isDisplayed(),"No Login entry found for username :"+username+'\n' );
		}
		catch(Exception e)
		{
			Assert.fail("No Login entry found for the username :"+username+'\n'+e.getMessage());
		}
		wdriver.quit();
		setCSRStatus(false);		
	}

/**
 * Logs out from CSR.
 */
public void CSR_logout()
{
	if(!CSRExecute) return;
	String xp = "//b[text()='Sign Out']";
	wdriver.findElement(By.xpath(xp)).click();
}

public static void setCSRStatus(boolean status)
{	
	CSRStatus=status;	
	com.libraries.Log.info("CSR status :"+status);	
}

public static void setCSRStatus(WebDriver driver,boolean status)
{
	String udid=wdriver(driver);
	
	deviceCSR.put(udid, status);
	com.libraries.Log.info("CSR status for "+udid+" :"+status);	
}

public static WebDriver getDriver()
{
	com.libraries.Log.info("CSR driver : "+wdriver);
	return wdriver;
}

public static WebDriver getDriver(WebDriver driver)
{
	return deviceDriver.get(wdriver(driver));
}

public static boolean getCSRStatus()
{
	return CSRStatus;
}

public static boolean getCSRStatus(WebDriver driver)
{	
	return deviceCSR.get(wdriver(driver));
}

public static void setCSRNull()
{
	wdriver=null;
}

public static void setCSRNull(WebDriver driver)
{
	deviceDriver.put(wdriver(driver), null);
}

public static void setWebDriverProperty()
{
	System.setProperty("webdriver.firefox.marionette","webdriver.gecko.driver"); // webdriver.gecko.driver
	//System.setProperty("webdriver.gecko.driver","./config/geckodriver.exe");
}

public static void verifyStatusOnTxnDesc(String string) {
	// TODO Auto-generated method stub
	
}

public static void verifyIAPTXNDetails(String transactionStatusMsg) {
	// TODO Auto-generated method stub
	
}

public static void verifyIAPTransactionDataPickupSuccess(
		String transactionStatusMsg) {
	// TODO Auto-generated method stub
	
}

public static void verifyStatusOnTxnId(String transactionid, String string,
		String string2) {
	// TODO Auto-generated method stub
	
}

public static double verifyBalance(String balanceMobileNumber) {
	// TODO Auto-generated method stub
	return 0;
}

public static void setCVVStatus(String cardDetails, String string) {
	// TODO Auto-generated method stub
	
}


}
