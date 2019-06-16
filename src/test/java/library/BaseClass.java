package library;

import io.appium.java_client.MobileBy;
import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import wibmo.app.pagerepo.BasePage;
import wibmo.app.testScripts.BaseTest1;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.commons.configuration.ConfigurationXMLReader;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
//import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import com.libraries.Log;
import com.relevantcodes.extentreports.LogStatus;

/**
 * The Class BaseClass used to start the appium server and launch the AUT.
 */

//@Listeners({MyTestListner.class})
public class BaseClass {
	
	/** The capabilities. */
	//public DesiredCapabilities capabilities= null;
	public static URL appiumURL; // Global Static
	
	/** The devicePort used to Map udid=port. */
	public static  HashMap<String, String> devicePort=new HashMap<String, String>();
	
	/** The deviceBootStrap used to Map udid=BootStrapPort. */
	public static  HashMap<String, String> deviceBootStrap=new HashMap<String, String>();	
	
	/** The deviceUiAuto2Port used to Map udid=UiAuto2Port. */
	public static  HashMap<String, String> deviceUiAuto2Port=new HashMap<String, String>();
	
	/** Maps the udid to the driver . Used under launchApplication */
	public static HashMap<String, WebDriver> driverHolder=new HashMap<String, WebDriver>();
	
	/** The skipStatus used to check whether a driver with a given udid has custom skipped an @Test
	 *  Used to add skip status to Report. 
	 *  Mapped as udid=true means a Generic.customSkip() was executed by the corresponding driver 
	 *    
	 *   */
	public static HashMap<String, Boolean> skipStatus=new HashMap<String, Boolean>();
	
	/** The udidModel used to Map udid=Device Model. */
	public static HashMap<String, String> udidModel=new HashMap<String, String>();
	
	/** The AppiumDriverLocalService map specific to each device to be set BeforeSuite . Safe option.*/
	public static HashMap<String, AppiumDriverLocalService> adls=new HashMap<String,AppiumDriverLocalService>();
	
	
	/** The udidPermissionsGranted used to Map udid=permissionGrantedStatus as true/false 
	 * @deprecated Use AutoGrantPermissions Capability instead
	 * */
	public static HashMap<String, Boolean> udidPermissionsGranted=new HashMap<String, Boolean>();	
	
	/** The br version. */
	public static  String brVersion=" ";
	
	/** The package name of AUT */
	public static  String packageName=" ";
	
	/** The driver non static . */
	public WebDriver driver;	
	
	/** The udid Unique Device Identifier non static used to represent the thread */ 
	public String udid;
	
	/** The path. */
	public static String path = System.getProperty("user.dir").replace("\\", "/");  	// MAC
	
	/** The test scenario name. */
	public String testScenarioName;
	
	/** The test scenario. */
	public String testScenario;
	
	/** The class name. */
	public String className;
	
	/** The path to config xlsx*/
	public static String configXLPath="./config/config.xlsx";
	
	/** The environment variable. */
	public static String env=ExcelLibrary.getExcelData(configXLPath,"Android_setup",8, 7);
	
	/** The environment config folder. */
	private static String envConfigFolder;
	
	/** The port. */
	public String port;
	
	public String executionDeviceId="";
	
	public String bootstrapPort;
	
	/** The address. */
	private static String address;
	
	/** The count. */
	static int count=0;
	
	/** Methods which uninstall  the app will set this to false*/
	static boolean appPresent=true; 
	
	/** The app activity. */
	public static String appActivity="";
	
	/** The bank name. */
	public static String bankName="";
	
	/** The bank code. */
	public static String bankCode="";
	
	/** The path to config folder. */
	public static String configPath;
	
	public static String chromeDriverPath;
	
	/** The qa variable used to previously check for qa environment. */
	//public static boolean qa=false;
	
	/** The program name. */
	public static String programName="";	
	
	/** The path to TestData excel File. */
	public static String excelPath; 
	
	public boolean isAppPresent=true;
	
	public static String model="";
	
	public static String version="";
	
	public static String resolution="";
	
	public static String appVersion="";
	
	public static String category="";
	
	public static boolean runtimePermissionsGranted=false; // Use static map in devicePermission	
	
	public static ChromeDriverService chromeService;
	
	// Used to match development codebase //
	public static boolean deviceTrialRun;
	
	public static int bootstrapNumber=4820;
	
	
	//public static String testDescription="";
	
	
	/**
	 * Returns the main driver
	 *
	 * @return the driver
	 */
	public  WebDriver getDriver() {
        return driver;
	}
	
	
	/**
	 * Gets the test Scenario name.
	 *
	 * @return the class name
	 */
	public String getClassName(){
		return testScenarioName;
	}
	
	/**
	 * Gets the test scenario.
	 *
	 * @return the test scenario
	 */
	public String getTestScenario() {
		return testScenario;
	}
	
	/**
	 * Sets the test scenario.
	 *
	 * @param testScenario the new test scenario
	 */
	public void setTestScenario(String testScenario) {
		this.testScenario = testScenario;
	}
	
	/**
	 * Gets the browser os information from driver Capabilities.
	 *
	 * @param driver the driver
	 * @return the browser os info
	 */	
	public void getBrowserOsInfo(WebDriver driver){
		 /*Capabilities cap = ((AndroidDriver) driver).getCapabilities();
		 String os = System.getProperty("os.name").toLowerCase();
		 brVersion=cap.getBrowserName()+","+cap.getVersion()+","+os;*/		 
		
	/*	if(!model.isEmpty())
			brVersion=model+","+version+","+resolution;
		//else
		{//brVersion=Generic.getDeviceModel()+","+Generic.getAndroidVersion()+","+Generic.getScreenResolution();}
		 
		 File file = new File(path+"\\basicinfo.txt");
		 FileWriter fw;
		try 
		{
			fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(BaseClass.brVersion);
			bw.close();
		} catch (IOException e) {
			Log.info("~~~ Unable to write device details ~~~");
			e.printStackTrace();			
		}		*/	
	}
	
	/**
	 * Cleans the reports directory and initializes paths for TestData.xls and config.properties based on the environment. 
	 */		
	@BeforeSuite
	public void executeCleanUpFile()
	{
		setWebDriverProperty();
		
		// ==== E ==== //
		ExtentManager.generateFilePath();		
		// =========== //
		
		File file_to_delete = new File(path+"/target/surefire-reports");
		File screen_shot_dir = new File(path+"/screenshots");
		
	try
		{
			FileUtils.deleteDirectory(file_to_delete);
			try{Thread.sleep(1000);}catch(Exception e){}
			FileUtils.deleteDirectory(screen_shot_dir);
			try{Thread.sleep(1000);}catch(Exception e){}
			new File(path+"/screenshots").mkdir();
		}
		catch(Exception e){Log.info("Unable to delete surefire-reports and screenshots folder\n");e.printStackTrace();}

		initializeEnvPaths();	
		
		appium();
		
	}
	
	/**
	 * Initializes paths for TestData.xls and config.properties based on the environment set by SetTestData.jar
	 */
	public static void initializeEnvPaths()
	{
		//-------- Retrieve package name from config.xls  -------- //
		//BaseClass.packageName=ExcelLibrary.getExcelData(".\\config\\config.xls", "Android_setup", 8,5);
		
		packageName=ExcelLibrary.getExcelData(configXLPath, "Android_setup", 8,5);
		Log.info("Setting package name : "+packageName);
		
		env=packageName.split("\\.")[3];
		
		Log.info("Environment initialized to "+env);
		
		// --------------------------Jenkins Support -------------------------------------//
		if(ExcelLibrary.getExcelData(configXLPath, "Android_setup", 8,7).equalsIgnoreCase("Yes"))
			env="jenkins_"+env;		
		//------------------------------------------------------- //
		
		//-------- Set config folder and path--------/
		if(env.contains("_")) // Jenkins
			envConfigFolder=env.split("_")[1]; /*  Folders under config i.e qa and staging are same for jenkins , env=jenkins_staging */
		else 
			envConfigFolder=env;
		
		configPath="./config"+"/"+envConfigFolder+"/config.properties";
		Log.info("configPath set to "+configPath);				
		//-----------------------------------------------------//		
		
		//---------------- Set packagename  -------------------- //
		packageName=Generic.getPropValues("PACKAGENAME",configPath);
		Log.info("Setting package name : "+packageName);
		// ---- Read Activity name from respective config.properties file ---- //
		
		
	}
	
	/**
	 * Initializes logger as log4j.properties.
	 * Launches Appium for the <test>
	 * 
	 * @param deviceId passed as xml <test >parameter for which Appium server is launched
	 */
	@Parameters("device-id")
	@BeforeTest
	public void initializeLogger(String deviceId){
		PropertyConfigurator.configure("log4j.properties");		
		baseSetup();
		
	}
	
	
	
	/**
	 * Initializes the AUT configuration attributes for all Test Classes.
	 *
	 */
	public void baseSetup()
	{		
		System.out.println("--- Initializing Application values ---");
		
		bankName=packageName.split("\\.")[packageName.split("\\.").length-1];
		programName=Generic.getPropValues("PROGRAMNAME",configPath);
		String program=bankName;
		if(bankName.equalsIgnoreCase("qa"))
			switch(programName.toLowerCase())
			{
			case "payzapp" : 	program="hdfc";
			break;			
			case "payapt"	: 	program="idbi";
			break;
			case "bob":		    program="bob";
			break;
			case "kotak":		program="kotak";
			break;			
			case "safepay": 	program="safepay";
			break;				
			}			
		
		bankCode=Generic.getPropValues(program.toUpperCase(), configPath);		
		//testScenarioName1 = this.getClass().getSimpleName();
		//className=this.getClass().getCanonicalName();	
		
		CSR.setCSRStatus(false);
		BaseTest1.setITP(false);
		BaseTest1.setPhoneVerifyStatus(false);
		
	}
	
	//======================== @Test Extent=====================================//
	/**
	 * Used to startTest() of ExtentTestManager 
	 * Uses java.lang.reflect.Method.invoke to get the Test Scenario from TestData.xls
	 * 
	 * @param Method testMethod. The @Test to be executed 
	 */
	@BeforeMethod
	public void startMethod(Method  testMethod)
	{
		
		// setSkipStatus(driver,false); WebSDK driver not initialized for overloaded @BeforeMethod launchApplication()
		// Do not use any driver here. Only reflect class names
		
		//--------------------------------E------------------------------------------//
		
		String tcName=testMethod.getName();
		
		if(tcName.contains("navi"))
			{ System.out.println("navigate() method found "); return; }
		
		if(!tcName.contains("_"))
		{
			System.out.println("Invalid TC name in startMethod : "+tcName); return;
		}
		
		String testDescription="Default";		
		
		try
		{
			Method classMethod=this.getClass().getMethod("getTestScenario", String.class);
			testDescription=(String)classMethod.invoke(this, tcName);
		} 
		catch(Exception e){library.Log.info("== Error in invoking method ==");e.printStackTrace();}
		
		String category=this.getClass().getCanonicalName().split("testScripts.")[1].split("\\.")[0];// previously global category used		
	
		
		Log.info("------------------------------------- "+tcName+" : "+testDescription+" ---------------------------------------");
		try{	
			
			if(Generic.isIos(driver))
				category+=" IOS";
			
		ExtentTestManager.startTest(tcName,testDescription);		
		ExtentTestManager.getTest().assignCategory(category);
															
		} catch(Exception e){System.out.println("Warning : Unable to write to Extent");}		
		
				
		//---------------------------------------------------------------------------//		
	}
	
	//
	public String getTcId(Method testMethod)
	{
		String classPackage=this.getClass().getCanonicalName().split("testScripts.")[1].split("\\.")[0],
			   methodPackage=testMethod.getDeclaringClass().getCanonicalName().split("testScripts.")[1].split("\\.")[0];
		
		return (classPackage==methodPackage)?this.getClass().getSimpleName():testMethod.getName();		
	}
	
	/**
	 * Returns the udid corresponding to the AndroidDriver
	 * If the driver is not AndroidDriver then udid is retrieved from sdkDriverUdid of BaseTest1
	 * 
	 * @return udid corresponding to the driver 
	 */
	public String getUdid(WebDriver driver)
	{
		try {
			if(driver==null) return "";
			
			if(Generic.isIos(driver))
				return ((String)((IOSDriver)driver).getCapabilities().getCapability("udid"));
			
			if(Generic.isAndroid(driver))				
				return ((String)((AndroidDriver)driver).getCapabilities().getCapability("udid")); 
			
								
			if(!Generic.containsIgnoreCase(driver.toString(), "Android"))
			{
				 return BaseTest1.getSDKUdid(driver); 
			}		
			return ((String)((AndroidDriver)driver).getCapabilities().getCapability("deviceName"));
		}catch(Exception e) {System.err.println("getUdid error");e.printStackTrace();return "";}
	}
	
	/**
	 * Updates the @Test result status to the Extent Report
	 * 
	 * 
	 * @return udid corresponding to the driver 
	 */
	@AfterMethod 
	public void updateResults(ITestResult iRes)
	{
 		int res=iRes.getStatus();
		String testName=iRes.getMethod().getMethodName();
		
		// if(testName.contains("IAPT")) testName=className; 
		
		// ---- Custom Skip Logic ---- //
		 if(getSkipStatus(driver))
		{
			 	ExtentManager.getReporter().endTest(ExtentTestManager.getTest());		
				ExtentManager.getReporter().flush(); 
				setSkipStatus(driver, false);
			 	return;
		}
		 
		 // ------------ //
		if(res==2)   // SUCCESS=1 , FAILURE=2, SKIP=3
		{	
			if(driver==null){System.out.println("No Screenshot for null driver ");return;}  // Support driverless execution 
			
			WebDriver driver=this.driver;
			Log.info("Failure --- Capturing screenshot");	
			//---------------------------------------------------------------//			
			//if(CSR.getCSRStatus())
				//driver=CSR.getDriver();	
			
			try{
				if(CSR.getCSRStatus(driver))
				{
					driver=CSR.getDriver(driver);
				    try{library.Log.info("-- C --");}catch(Exception e){}
				    
				    CSR.setCSRStatus(driver, false);
				}
			}
			catch(Exception e){System.err.println("-- Error in Mapping to CSR -- ");e.printStackTrace();}
			
			//---------------------------------------------------------------//
			try{	
				if (driver != null)
		        {
		            File f = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		            try 
		            {		  
		            	
		            	if(Generic.isIos(driver)) 
		            		testName+="_IOS";
		            	
		            	FileUtils.copyFile(f, new File(ExtentManager.screenPath+testName+".jpg")); 	// Extent
		                try{Thread.sleep(2000);}catch(Exception e){}
		            	
					} catch (IOException e) { Log.info("== Error in copying screenshot ==\n"+e.getMessage());e.printStackTrace(); }									
		         }
				}catch(Exception e){Log.info("======== Error in obtaining screenshot ========\n"); e.printStackTrace();}			
			
			String extScreens="./"+testName+".jpg";		
			ExtentTestManager.getTest().log(LogStatus.FAIL,"HTML", "There is an error: <br/><br/> "+iRes.getThrowable().getMessage()+" <br/><br/> Error Snapshot : "+ ExtentTestManager.getTest().addScreenCapture(extScreens));
		}
		if(res==1)
		{
			ExtentTestManager.getTest().log(LogStatus.PASS,"========== Passed: "+testName+" ==========");
		}
		if(res==3)
		{
			 // Refer Framework Skip Logic
			//  ExtentTestManager.getTest().log(LogStatus.SKIP,"========== Skipped: "+testName+" ==========\n"+iRes.getThrowable().getMessage()); // can add depends on logic or not executed
		}		
		
		try{
			
			if(Generic.containsIgnoreCase(driver.toString(), "android") || Generic.containsIgnoreCase(driver.toString(), "ios"))	// If Mobile Driver then get device details 
			{
				ExtentTestManager.getTest().assignAuthor(getDeviceModel(driver)); 
				
				if(!appVersion.isEmpty())   
					ExtentManager.getReporter().addSystemInfo("App Version", appVersion);
					
				}//HandleHomePopup.setHandledStatus(driver, true); 							
		}catch(Exception e){Log.info("== Unable to obtain device details ==\n"+e.getMessage());}		
		
		ExtentManager.getReporter().endTest(ExtentTestManager.getTest());		
		ExtentManager.getReporter().flush(); 
	}	
	
	
	//==========================Skip Status====================================//
	public void setSkipStatus(WebDriver driver,boolean status)
	{
		if(!Generic.containsIgnoreCase(driver.toString(), "Android")) return; //can add if(driver==null ) return ; (For overridden WebSDK launchApplication)
		skipStatus.put(getUdid(driver), status);							  // then use setSkipStatus under @BeforeMethod startTest
	}																		  // since currently it is set at BaseClass, to prevent skip leak onto other grouped test methods , set skip false from Generic.customSkip() after Extent logging
	
	public boolean getSkipStatus(WebDriver driver)
	{
		if(driver==null) return false;
		if(!Generic.isAndroid(driver)) return false;
		if(skipStatus.get(getUdid(driver))==null) return false;
		return skipStatus.get(getUdid(driver));
	}	
	//=========================================================================//
	
	//======================== Device Model for Extent ========================//
	
	/**
	 *  Retrieves Device Model details from config.xlsx
	 *  
	 * 
	 * @param driver
	 * @return Device Model with Android Version
	 * @see 	config/config.xlsx & BaseTest1.getWibmoDeviceId()
	 */
	public String getDeviceModel(WebDriver driver)
	{
		String udid=getUdid(driver),
					sheet="Modules",
				    deviceModel="",tcRowVal;
		
		int rc,udidColumn=9	,deviceModelColumn=10;
		
		 if (udidModel.get(udid)!=null) 
			 return  udidModel.get(udid);		 		
		 
		try 
		{
				rc=ExcelLibrary.getExcelRowCount(configXLPath, sheet);		
				
				for(int i=0;i<=rc;i++)
				{
					tcRowVal=ExcelLibrary.getExcelData(configXLPath, sheet, i, udidColumn); 
					
					if(tcRowVal.contains(udid))
					{
						deviceModel=ExcelLibrary.getExcelData(configXLPath, sheet, i, deviceModelColumn); 
						udidModel.put(udid, deviceModel);						
					}
				}
		}catch(Exception e) {System.err.println("Error retrieving Device model for "+udid+'\n');e.printStackTrace();}
		
		return deviceModel;
	}
	
	
	/**
	 *  Initializes the udid for the given class thread. 
	 * 
	 * @param deviceId
	 */
	@Parameters("device-id")
	@BeforeClass
	public void initUdid(String deviceId)
	{
		udid=deviceId;
	}
	
	//=========================================================================//
	
	/**
	 * Launches the application. Will be overridden by specific testcases which use a non mobile driver
	 */
	@Parameters("device-id")
	@BeforeClass//(dependsOnMethods="baseSetup")
	public void launchApplication(String deviceId){	
		
		baseSetup();
		appActivity=ExcelLibrary.getExcelData(configXLPath, "Android_setup", 8, 4);
		//installProgram(); 
		
		// ==== Advanced Launch ==== //
		if(!advancedLaunch(deviceId))		
			launchApp(packageName, appActivity,deviceId);
		driverHolder.put(deviceId,driver);
		// ========================= //
		
		CSR.setCSRStatus(driver,false);
		setSkipStatus(driver,false);
		Generic.resetPopups(driver);
		selectQaProgram();		
		
	}
	
	
	
	
	
	
	
	/**
	 * An attempt to speed up AUT launch between testClasses by preserving driver sessions.
	 * To be used under launchApplication() for launching AUT only.
	 * Uses static driverHolder to map device udid to driver [Android:Appium or Android:UiAutomator]  thereby preventing Caps Launch
	 * 
	 * @param udid 
	 * @see advancedLaunchMerchantApp under BaseTest1
	 */
	public boolean advancedLaunch(String udid) 
	{
		boolean advLaunchStatus;
		
		//=== IOS === //
		if(Generic.isIos(udid))
			return advancedLaunchIos(udid);
		// ======== //
		
		try
		{
			if(driverHolder.get(udid)==null ) return false; //|| Generic.getSessionId(driverHolder.get(udid))==null) return false;
			
			WebDriver hdriver=driverHolder.get(udid);	
			
			if(!Generic.checkAndroid(hdriver)) return false; // hdriver may point to WebSDK/Overlay instance 
			
			//if(!Generic.getPackageName(hdriver).contains(Generic.getPropValues("PACKAGENAME", BaseTest1.configPath))) return false;
			
			if(Generic.checkTextInPageSource(hdriver, "login_button","textbutton_login").contains("login")) // Check for Login buttons either in WelcomePage or LoginPage
			{	
				driver=hdriver;
				advLaunchStatus= true;			
			}
			else
			{
				
				Generic.switchToApp(hdriver);
				new BasePage(hdriver).waitOnProgressBarId(45);	
				advLaunchStatus= Generic.checkTextInPageSource(hdriver, "login_button","textbutton_login").contains("login");				
			}
			
			if(advLaunchStatus)
				driver=hdriver;
			// else hdriver.quit();
			
		}			
		catch(Exception e)
		{
			System.err.println("Advanced launch not possible for "+udid+'\n');e.printStackTrace();
			advLaunchStatus=false;
		}
		
		  // Currently not closing session  
		 // Use session override instead of closing driver session 
		
		return advLaunchStatus;
		
	}
	
	public boolean advancedLaunchIos(String udid)
	{
		boolean advLaunchStatus=false;
		try
		{
			if(driverHolder.get(udid)==null) return false; 
			
			IOSDriver hdriver=(IOSDriver)driverHolder.get(udid);	
			
			String checkerPredicate="(name contains ' battery ' or name='Login') and visible=true";  // (Phone Indicator or LoginButton) and visible=true
			if(hdriver.findElementsByIosNsPredicate(checkerPredicate).size()>1)
			{
				System.out.println(">> Continuing previous IOS Driver Session "); 
				driver=hdriver;
				advLaunchStatus= true;			
			}
			else
			{
				Generic.switchToApp(hdriver);
				new BasePage(hdriver).waitOnProgressBarId(45);	
				advLaunchStatus=hdriver.findElementsByIosNsPredicate(checkerPredicate).size()>1;
			}
			
			if(advLaunchStatus)
				driver=hdriver;
			// else hdriver.quit(); Needs Impact Assessment
			
		}			
		catch(Exception e)
		{
			System.err.println("Advanced launch IOS not possible\n");e.printStackTrace();
			advLaunchStatus=false;
		}
		
		  // Currently not closing session  
		 // Use session override instead of closing driver session 
		
		return advLaunchStatus;
	}
	
	/**
	 * Closes the application. Closes the CSR window specific to the driver if it is open.
	 */
	@AfterClass(alwaysRun=true)
	public void quit(){
		
	// ==== E ==== //  System/Device Info can be added only before flush().
	
	Log.endTestCase(this.getClass().getSimpleName());	
	
	//---------------------CSR---------------------------//		
		try
		{		
			if(CSR.getDriver(driver)!=null && !CSR.getDriver(driver).toString().contains("null")) 
			{
				CSR.getDriver(driver).quit();	
				//CSR.setCSRNull();
				CSR.setCSRStatus(driver,false);				
			}
			CSR.setCSRNull(driver);
		}
		catch(Exception e)
		{
			Log.info("== CSR driver delay ==\n"+e.getMessage());
		}
	//---------------------------------------------//
		
	try{
		BaseTest1.setWebOverlayStatus(driver, false);
		
		handleIOSDelayedAlert();
		
		//======== Advanced Launch ========//
		//if(driver!=null) driver.quit(); Toggle for advancedLaunch()		
		
		if(Generic.isWebDriver(driver))  
			{
				System.out.println("Closing WebDriver : "+driver.toString());
				
				try { driver.close(); driver.quit(); } catch (Exception e) {System.err.println("WebDriver Close	Error");}
				return;
			}
		
		new BasePage(driver).logOut();		
		//=================================//
		
		}
	catch(Exception e){System.err.println("Unable to close driver/logout under @AfterClass");e.printStackTrace();/*driver=null*/;}		
		
	}
	
	/**
	 * Launches the app specific to the device udid with the given package name , appActivity and Capabilities.
	 *
	 * @param packageName the package name
	 * @param appActivity the app activity
	 */
	public  synchronized void launchApp(String packageName,String appActivity,String udid)
	{
		String chromeDriverExecutablePath;		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
	    // ======== IOS Capabilities ======== //
	    if(Generic.isIos(udid))
	    {
	    	    capabilities.setCapability("platformName", "ios");
			capabilities.setCapability("platformVersion", "11.1.1"); 
			capabilities.setCapability("deviceName", "iPhone 8"); 
			capabilities.setCapability("bundleId", "com.hdfc.payzappdev");  // read from config.properties or config.xlsx
			capabilities.setCapability("automationName", "XCUITest");
			capabilities.setCapability("simpleIsVisibleCheck", false);			
			capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true); // Check suite behaviour
	    }
	    
	    // ======== Android Capabilities ======== //
	    else
	    {
	    	
	    	capabilities.setCapability(MobileCapabilityType.PLATFORM, MobilePlatform.ANDROID);		
	    	capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, udid);
	    	
		    	if(Generic.getAPILevel(udid)>=21) 		//  Select UiAuto2 for Android 5 and above.  21=Android 5 and 23=Android  6
		    	{
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);			
				capabilities.setCapability("skipUnlock", true);		
				capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT,getUiAuto2Port(udid));
				//capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true); // Use in BaseClass Only 
			}
		    	else
			    	capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
	    	
	    capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, packageName);
	 	capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,appActivity);	
	 	capabilities.setCapability(AndroidMobileCapabilityType.NO_SIGN,true);	
	    capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
	    //capabilities.setCapability(AndroidMobileCapabilityType.DISABLE_ANDROID_WATCHERS, true);
	    capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_PACKAGE, packageName);
	    capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, appActivity); //Chk behaviour with emu 
	    capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION, 90000);
	    capabilities.setCapability(AndroidMobileCapabilityType.NATIVE_WEB_SCREENSHOT, true); 	// WebOverlay
	    
	    //capabilities.setCapability(AndroidMobileCapabilityType.TAKES_SCREENSHOT, true);	    	
	    if(!(chromeDriverExecutablePath=appiumChromeDriverExecutable(udid)).contains("builtin")) // Set ChromeDriverExecutable
	    {
	    		System.out.println("Setting CHROMEDRIVER_EXECUTABLE for "+udid+" to "+chromeDriverExecutablePath);
    			capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromeDriverExecutablePath);
	    }
	    else
	    		System.out.println("Using builtin ChromeDriverExecutable path for  "+udid);
	    
	    }
	    
	    // ======== Common Capabilities ======== //
	    // ==== UDID ==== //	    
	    if(checkUDIDConnected(udid))
	    		capabilities.setCapability(MobileCapabilityType.UDID, udid); 
	    else
	    {
	    		System.err.println("Device "+udid+" not found!!");
	    		System.exit(0);
	    }	    
	    //============//
	    capabilities.setCapability(MobileCapabilityType.NO_RESET, true); 
	    capabilities.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES, true);
	    capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 330);	    
	    // ======== Launch driver ======== //
	    
    	   try 
	    {	    	
    		   if(Generic.isIos(udid))
    			   driver=new IOSDriver<WebElement>(adls.get(udid), capabilities);
    		   else
			driver = new AndroidDriver<WebElement>(adls.get(udid), capabilities);
    		   
    		   handleColorOS(driver); 
    		   
    		   driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    		       		   
		}
		catch(Exception e)
		{
			if(count<=33)
			{
				e.printStackTrace();
			
				count++;
				System.out.println(e.getMessage().substring(0,30));
				System.out.println("========== Appium didn't start properly since too many applications are open ==========");
				System.out.println("========== Re-Starting Appium, Please wait... ==========");
				
				//appium(); 
				try {Thread.sleep(5000);} catch (InterruptedException e1) {}
				launchApp(packageName, appActivity,udid);
			}
			else{
			e.printStackTrace();
			System.err.println("--------------------------------------------------------------------------------------------------");
			System.err.println("Please Check The Configuration Excel file under config folder and make sure to fill all the fields");
			System.err.println("--------------------------------------------------------------------------------------------------");
			System.exit(0);}		
		}
	}
	
	/**
	 * Starts Appium server.
	 * 
	 */
	public void appium()
	{		
		String appiumNodeExecutablePath=ExcelLibrary.getExcelData(configXLPath, "Android_setup", 2, 1);        
        
        System.out.println("Appium node executable at "+appiumNodeExecutablePath);
        
        LinkedHashSet<String> devices=Driver.devices();
     //   cleanupHelperApps(devices);        
        
        for(String device:devices)
        {
        
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
        .withIPAddress("127.0.0.1")
        .usingAnyFreePort()
        .withAppiumJS(new File(appiumNodeExecutablePath))
       .withArgument(GeneralServerFlag.SESSION_OVERRIDE) 
        .withArgument(GeneralServerFlag.LOG_LEVEL,"info") //debug
        .withArgument(GeneralServerFlag.LOG_TIMESTAMP) 
        .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, getPort());
        
        //.withArgument(AndroidServerFlag.CHROME_DRIVER_PORT, "6666");
        
        AppiumDriverLocalService service=AppiumDriverLocalService.buildService(builder);
        service.start();
        
        if(service.isRunning())
        		{System.out.println("Appium started at  "+service.getUrl()+" for "+device);adls.put(device, service);}     
        else
            {System.out.println("Unable to start Appium for "+device);System.exit(1);}
        
        }
	}
	
	/**
	 * Checks for the environment - Staging or QA.
	 *
	 * @param environment the environment
	 * @return true if environment string (in lowercase) matches the static env variable.
	 */
	public static boolean checkEnv(String environment)
	{
		return env.contains(environment.toLowerCase());		
	}
	
	/**
	 * Closes Appium server.
	 */
	@AfterSuite(alwaysRun=true)
	public void close()
	{		
		try
		{
			//if(BaseTest1.chromeService!=null)BaseTest1.chromeService.stop();
			
			// === Stop All Appium Servers === //
			for(String device:adls.keySet())
			{
				System.out.println(" ~ Closing Appium Server at "+adls.get(device).getUrl()+" for "+device+" ~");
				adls.get(device).stop();
			}
			// ===================== //
			
			DB.closeDB();
			
			
			wibmo.app.testScripts.IAP_Transaction.BaseTest.setMerchantShellNormal(); // Set Common MerchantID
			installProgram();
			//Runtime.getRuntime().exec(".\\killNode.bat"); // Not suitable for parallel execution
			//Log.info("=========== Shutting Down Appium Server Session. Please Wait ===========");
			Thread.sleep(5000);	
			
			//======== E ========== //			
			ExtentManager.createExtZip();			
			//=================== //
			
		}
		catch (Exception e) 
		{
			Log.info("Error in closing After Suite process  "+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gets the complete class name for the given Test Class.
	 *
	 * @return the complete class name
	 */
	public String getCompleteClassName(){return className;}
	
	/**
	 * Returns an available Port.
	 *
	 * @return the port
	 */
	private String getPort()
	{
		try
		{
			ServerSocket socket=new ServerSocket(0);
			socket.setReuseAddress(true);
			String port="";
			if(socket.getLocalPort()!= -1)
			{
				port=Integer.toString(socket.getLocalPort());
				socket.close();
				return port;
			}
			else
			{
				socket.close();
				return getPort();
			}
		}catch(Exception e){return getPort();}
		 catch(Error e){return getPort();} // ECONNRESET Error
	}
	
	public synchronized String getPort(String udid)
	{
		
		// ==== 1.7.1 ==== //
		if(true) return getCommonPort(udid);		
		// =========== //
		
		System.out.println("=== Selecting port for "+udid+" ===");
		
		int portSeparator=((int)udid.charAt(new Random().nextInt(udid.length()-1))); 	
		
		// initialize port beforeTest();
		//System.out.println("Selecting port : "+port);
		 //return port; 
		
		try
		{
			ServerSocket socket=new ServerSocket(0);
			socket.setReuseAddress(true);
			String port="";
			if(socket.getLocalPort()!= -1)
			{
				port=Integer.toString(socket.getLocalPort()+portSeparator);
				
				 if(devicePort.containsValue(port) || deviceBootStrap.containsValue(port) || deviceUiAuto2Port.containsValue(port))
				 {
					 System.out.println("Main Port Conflict . Current devicePort :"+devicePort);
					 port=Integer.toString(socket.getLocalPort()+15);
				 }
				socket.close();
				System.out.println("=== Returning port "+port+" for "+udid); devicePort.put(udid, port);
				return port;
			}
			else
			{
				socket.close();
				return getPort(udid);
			}
		}catch(Exception e){return getPort(udid);}
		 catch(Error e){return getPort(udid);} // ECONNRESET Error
	}
	
	/**
	 * 	 Port Selection optimized for Appium 1.7.1
	 * 
	 *  Assign to static port and retrieve under launchApp()
	 *  
	 * @param udid
	 * @return available port
	 */
	public String getCommonPort(String udid)
	{
		try
		{
			ServerSocket socket=new ServerSocket(0);
			socket.setReuseAddress(true);
			String port="";	
			
			if(socket.getLocalPort()!= -1)
			{
				port=Integer.toString(socket.getLocalPort());			
				socket.close();
				System.out.println("=== Returning port "+port+" for "+udid); devicePort.put(udid, port);
				return port;
			}
			else
			{
				socket.close();
				return getPort(udid);
			}
	}catch(Exception e){return getPort(udid);}
	 catch(Error e){return getPort(udid);} 		// ECONNRESET Error
	}
	
	public synchronized String getBootStrapPort(String udid)
	{
		long x = 1023;
		long y = 65535;		
		
		long bootStrapPort;
		
		Random r = new Random();		
		
		long number = x+((long)(r.nextDouble()*(y-x)));	
		
		System.out.println("Bootstrap Port Number generated : "+ number);
		
		//----------------------//
		
		int portSeparator=((int)udid.charAt(new Random().nextInt(udid.length()-1))); 
		
		bootStrapPort=number+portSeparator;
		
		 if(devicePort.containsValue(bootStrapPort+"") || deviceBootStrap.containsValue(bootStrapPort+"") || deviceUiAuto2Port.containsValue(bootStrapPort+""))
		 {
			 System.out.println("Port Conflict . Current deviceBootStrapPort : "+deviceBootStrap);
			 bootStrapPort+=15;
		 }
		
		 deviceBootStrap.put(udid, bootStrapPort+"");
		//---------------------//
		
		return bootStrapPort+"";	  
	}
	
	
	
	public synchronized int getUiAuto2Port(String udid)
	{
		long x = 1023;
		long y = 65535;		
		
		long uiAuto2Port;
		
		Random r = new Random();		
		
		long number = x+((long)(r.nextDouble()*(y-x)));	
		
		//System.out.println("Number generated "+ number);
		
		//----------------------//
		
		int portSeparator=((int)udid.charAt(new Random().nextInt(udid.length()-1))); 
		
		uiAuto2Port=number+portSeparator;
		
		 if(devicePort.containsValue(uiAuto2Port+"") || deviceBootStrap.containsValue(uiAuto2Port+"") || deviceUiAuto2Port.containsValue(uiAuto2Port+""))
		 {
			 System.err.println("Port Conflict . Current deviceBootStrapPort : "+deviceBootStrap);
			 uiAuto2Port+=15;
		 }
		
		 deviceUiAuto2Port.put(udid, uiAuto2Port+"");
		//---------------------//
		 
		System.out.println("UIAutomator2 port for "+udid+" : "+uiAuto2Port);
		return (int)uiAuto2Port;	  
	}
	
	

		
	
	/**
	 * Gets the IP address.
	 *
	 * @return the address
	 */
	private String getAddress()
	{
		try
		{
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostAddress();
		}
		catch(Exception e){ return "127.0.0.1";}
	}
	
	/**
	 * Selects the qa program within the QA app.
	 */
	public void selectQaProgram() 
	{	
	 	if(!checkEnv("qa")) return;
	 	Log.info("======== Selecting Program based on :"+env+"========"); // use com.libraries.Log - Extent not yet initialised
	 	
	 	try
	 	{
	 		new WebDriverWait(driver,15).until(ExpectedConditions.presenceOfElementLocated(By.id("title_text")));
	 		if(driver.findElement(By.id("title_text")).getText().contains("Select Program"))
	 			driver.findElement(By.name(programName)).click();
	 	}
	 	catch(Exception e)
	 	{
	 		Log.info("Unable to find the QA Pragram in list \n");
	 	}
	}
	
	/**
	 * Selects the qa program within the QA app after creating a new object of BaseClass.
	 *
	 * @param driver the driver
	 */
	public void selectQaProgram(WebDriver driver)
	{
		if(!checkEnv("qa")) return;			 	
		
		String xp="//android.widget.TextView[contains(@resource-id,'title_text')]";
		String txt=driver.findElement(By.xpath(xp)).getText();
		
		if(Generic.containsIgnoreCase(txt, "QA")) 
		{
			library.Log.info("======== Waiting for  QA screen pageload ========");
			Generic.wait(3); 						// Wait for loading screen to complete
			txt=driver.findElement(By.xpath(xp)).getText();
		}
		
		library.Log.info("======== Selecting Program based on "+env+" env from "+txt+" screen ========");
		
		if(txt.contains("Select Program"))
		{
			library.Log.info("======== Clicking "+programName+" based on "+env+" env in "+txt+" screen========");
			driver.findElement(By.name(programName)).click();
		}
	}
	
	public static boolean checkUDIDConnected(String udid)
	{
		try
		{
			String chkCmd=Generic.isIos(udid)?"xcrun instruments -s devices":"adb devices";
				
			return Generic.execCmd(chkCmd).contains(udid);  
		}
		catch(Exception e)
		{
			System.err.println("Error while checking for connected UDID "+udid+"\n"+e.getMessage());
			return false;
		}
	}
	
	public void getExecutionDeviceId()
	{
		if(executionDeviceId.isEmpty())
		{
			try
			{
				executionDeviceId=(String)((AndroidDriver)driver).getCapabilities().getCapability("deviceName");
			}
			catch(Exception e)
			{
				System.err.println("=== Unable to obtain execution device Id ===");
			}
		}		
	}
	
	/**
	 * Workaround for UiAuto2 
	 * 
	 * @param devices
	 */
	public void cleanupHelperApps(LinkedHashSet<String> devices)
	{
		
		String uninstallHelperCmd;
		String[] helperApps= {"io.appium.settings","io.appium.uiautomator2.server","io.appium.uiautomator2.server.test","io.appium.unlock"};
		
		for (String device:devices)
		{
			for(String helperApp:helperApps)
			{
				uninstallHelperCmd="adb -s udid shell pm uninstall helperApp".replace("udid", device).replace("helperApp", helperApp);		
				System.out.println("Appium Helper App cleanup  : "+uninstallHelperCmd);
				try{Runtime.getRuntime().exec(uninstallHelperCmd).waitFor();}catch(Exception e) {System.err.println("Error cleaning up "+helperApp+" for "+device);e.printStackTrace();}
			}
		}
		
		Generic.wait(30); // Wait for Completions
	}
	
	public void installProgram()
	{
		String installCmd="";		
		
		/*if(isAppPresent) // Cannot synchronize static values with actual presence of the app 
			{
				System.out.println(":::: App is present in the device ::::");
				return;
			}*/
		
		if(!packageName.toLowerCase().contains("qa")) return; // Method used only in QA
		
		System.out.println("== Executing postcondition specific to QA environment ==");
		
		//executionDeviceId is set after driver is launched previously
		String appPath=Generic.getApkPath();
		
		if(executionDeviceId.isEmpty())
			installCmd="adb install -r "+appPath;
		else
			installCmd="adb -s "+executionDeviceId+" install -r "+appPath;
		
		try
		{
			System.out.println("==== "+packageName+" App not present , Installing app with "+installCmd+" ===");
			Runtime.getRuntime().exec(installCmd); Generic.wait(25);							
		}
		catch(Exception e)
		{
			System.err.println("Unable to install app ... \n"+e.getMessage());	
			e.printStackTrace(); return;
		}	
		
		isAppPresent=true;
	}
	
	public void uninstallProgram()
	{
		String uninstallCmd="";
		
		/*if(!isAppPresent) 
			{
				Log.info("--- App is already uninstalled ---");
				return;
			}*/
		
		if(!packageName.toLowerCase().contains("qa")) return; // Method used only in QA		
		
		if(executionDeviceId.isEmpty())
			uninstallCmd="adb shell pm uninstall -k "+packageName;
		else
			uninstallCmd="adb -s "+executionDeviceId+" shell pm uninstall "+packageName; //-k
		
		try
		{
			Log.info("==== Uninstalling "+packageName+" app with "+uninstallCmd+" ===");
			Runtime.getRuntime().exec(uninstallCmd);
			isAppPresent=false;	
		}
		catch(Exception e)
		{
			Log.info("Unable to uninstall app \n"+e.toString());	// Do not use library.Log	
		}				
	}
	
	public void setWebDriverProperty()
	{
		//setFirefoxBinary();
		
		String geckoExecutablePath="./config/geckodriver"+(System.getProperty("os.name").contains("Mac")?"":".exe");
		String chromeExecutablePath="./config/chromedriver"+(System.getProperty("os.name").contains("Mac")?"":".exe");
		
		System.out.println("Setting WebDriver properties \n "+geckoExecutablePath+'\n'+chromeExecutablePath);
		//System.setProperty("webdriver.firefox.marionette",geckoExecutablePath);		
		System.setProperty("webdriver.gecko.driver",geckoExecutablePath);
		System.setProperty("webdriver.chrome.driver", chromeExecutablePath);		
	
	}
	
	public void setFirefoxBinary()
	{
		String firefoxBinaryPath=ExcelLibrary.getExcelData(configXLPath, "Android_setup", 2,5);
		try{
			if(firefoxBinaryPath.length()>3) // " "
			{
				if(!new File(firefoxBinaryPath).exists()) return;
				
				System.out.println("Setting Firefox Binary Path to "+firefoxBinaryPath);
				System.setProperty("webdriver.firefox.bin", firefoxBinaryPath);
			}		
		}
		catch(Exception e){System.err.println("Error in Setting Firefox Binary Path . Check config.xlsx file");}
	}
	
	/**
	 * 
	 * @deprecated Use Auto Grant Permissions Capability instead 
	 * @param driver
	 */
	public static void grantAppRuntimePermissions(WebDriver driver)
	{
		if(true) return ; // Using AUTOGRANTPERMISSIONS Capability instead 
		
		String udid=Generic.getExecutionDeviceId(driver);
		
		if(udidPermissionsGranted.containsKey(udid) && udidPermissionsGranted.get(udid)) return;
		//if(runtimePermissionsGranted) return;
		
		
		String ver=Generic.getAndroidVersion(driver),permCmd; // If necessary check for WebDriver or AndroidDriver 
		//if(!ver.split("\\.")[0].contains("6")) return; 		 // Check for Android M Check for other versions
		
		System.out.println("== Android Version "+ver+" detected, Setting runtime permissions ==");
		
		String permList=" android.permission.CAMERA, android.permission.READ_CONTACTS, android.permission.WRITE_CONTACTS, android.permission.GET_ACCOUNTS,"+
						" android.permission.ACCESS_FINE_LOCATION,"+
						" android.permission.READ_PHONE_STATE,"+
						" android.permission.SEND_SMS, android.permission.RECEIVE_SMS,"+
						" android.permission.READ_EXTERNAL_STORAGE, android.permission.WRITE_EXTERNAL_STORAGE";
		
		String permissions[]=permList.split(",");
		
		for(String permission:permissions)
		{			
			 permCmd="adb -s "+Generic.getExecutionDeviceId(driver)+" shell pm grant "+BaseTest1.packageName+permission;
			 System.out.println("Permission Command : "+permCmd);			 
			 try { Runtime.getRuntime().exec(permCmd).wait(); }
			 catch(Exception e){}				 
		}
		
		//runtimePermissionsGranted=true;
		
		udidPermissionsGranted.put(udid, true);		
	}
	
	public String appiumChromeDriverExecutable(String udid)
	{
		try{
			String chromePkgName="com.android.chrome";
			String chromeExecutableVersionPath="./ChromeDriverExecutables/"
												+getChromeDriverExecutableVersion(udid)
												+"/chromedriver.exe"; // Ex : ./ChromeDriverExecutables/2.18/chromedriver.exe
			
			chromeExecutableVersionPath=System.getProperty("os.name").contains("Mac")?chromeExecutableVersionPath.replace(".exe", ""):chromeExecutableVersionPath; // OS Adjust
			System.out.println("Relative Path for ChromeDriverExecutable : "+chromeExecutableVersionPath);
			
			if(chromeExecutableVersionPath.contains("builtin"))
				return "builtin";
			else
				return new File(chromeExecutableVersionPath).getAbsolutePath();			
			}
		catch(Exception e)
		{
			return "builtin";
		}
	}
	
	/**
	 *  Retrieves the ChromeDriverExecutableVersion from config.xlsx 
	 *  
	 * 
	 * @param udid
	 * @return chromeDriverExecutableVersion corresponding to the udid
	 */
	public String getChromeDriverExecutableVersion(String udid)
	{
		// 	Get version corresponding to the udid from config file
		// 	If no version found return "chromeDriverExecutableVersionNotFound" , Print Warning at Capabilities & Launch
		
		String sheet="Modules",
			    version="",tcRowVal;
	
	int rc,udidColumn=9,versionColumn=11;
	 
	try 
	{
			rc=ExcelLibrary.getExcelRowCount(configXLPath, sheet);		
			
			for(int i=0;i<=rc;i++)
			{
				tcRowVal=ExcelLibrary.getExcelData(configXLPath, sheet, i, udidColumn); 
				if(tcRowVal.equals(udid))
				{
					version=ExcelLibrary.getExcelData(configXLPath, sheet, i, versionColumn);break; 
				}
			}
	}catch(Exception e) {System.err.println("Error retrieving Device model for "+udid+'\n');e.printStackTrace();}
	
	return version.length()>2?version:"builtin";
			
	}
	
	/**
	 * 	 Handle delayed Alerts generated from previous TestCases 
	 *  To be executed @AfterClass 
	 *  IOS Only
	 *  
	 */
	public void handleIOSDelayedAlert()
	{
		
		if(!Generic.isIos(driver)) return;
		
		try {
			
			String alertChkPredicate="(name contains 'battery' or name='Ok') and visible=true";
			
			if(driver.findElements(MobileBy.iOSNsPredicateString(alertChkPredicate)).size()>1)
			{
				System.out.println("Handling delayed alert");
				driver.findElement(MobileBy.AccessibilityId("Ok")).click();
			}	
			
		}catch(Exception e) {System.err.println("Error in handling delayed alert " );e.printStackTrace();}
		
	}
	
	/**
	 * 
	 * @param chromeAppVersion
	 * @return
	 * @deprecated
	 */
	public String getChromeExecutableVersion(String chromeAppVersion)
	{
		try
		{
			double chromeAppVer=Double.parseDouble(chromeAppVersion.split("\\.")[0]);		
		
			if(chromeAppVer<47) return "2.18"; 
			if(chromeAppVer>58) return "builtin"; 
		}
		catch(Exception e)
		{
			System.err.println("-- Unable to get Chrome Executable version --");e.printStackTrace();
		}
		   // ... Add based on future Chrome App versions 7 Check ... //
		
		return "builtin"; // Use BuiltIn ChromeDriver version bundled with Appium
	}
	
	
	public void handleColorOS(WebDriver driver)
	{
		if(getUdid(driver).equals("SK55NZ7H99999999")) // Oppo WK. WK for ColorOS=Android 6 & above would involve using System UI Tuner Third Party App  to hide hide status bar and thereby hiding Developer Options blink
		{
			System.out.println("Setting Idle timeout to 0 ms");			
			((AndroidDriver)driver).setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 0); 	
		}
	}
	
}
