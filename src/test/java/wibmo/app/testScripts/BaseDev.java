package wibmo.app.testScripts;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.libraries.Log;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileBy;
import io.appium.java_client.Setting;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import library.CSR;
import library.DB;
import library.ExcelLibrary;
import library.ExtentManager;
import library.ExtentTestManager;
import library.Generic;
import wibmo.app.pagerepo.BasePage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.WelcomePage;


public class BaseDev 	//imitates BaseClass for Script Development
{	

	private static String testScenario="TrialRun"; 
	protected String udid="ce12160cb2c964740c"; 					 														 // 	420085dad66ba291 310044cf61c7b2eb df25d0aa ce12160cb2c964740c SK55NZ7H99999999  YED7N16901000792 FA6A40312236 ZY223CPDLT ZX1F5225BT PLEGAR1761900048 5d8e9235
																																					// 	ce12160cb2c964740c 8853d65e1c599d42d52153a147a73c948b987a34  emulator-5554 30E0C8A5-13A8-4021-A85B-1CDAF887F199 602D2480-F531-4A44-8406-5C578C0BF8D1
	public static String env="staging"; 	//Generic.getPropValues("ENV", configPath); // env to be read from configXL Path 
	public String iosDeviceName="iPhone 8";  // To Be read from Devices.ini
	
	// ==== To be Commented during batch run for multiple programs ==== //
	/** The path. */
	public static String path = System.getProperty("user.dir").replace("\\", "/");  // MAC
	
	public static final String configXLPath="./config/config.xlsx";
	public static final String configPath="./config/"+env+"/config.properties"; // env= "staging" or "qa"
	public static  String packageName=Generic.getPropValues("PACKAGENAME", configPath);  // Staging : com.enstage.wibmo.staging.hdfc // QA :com.enstage.wibmo.qa // // Production app : com.enstage.wibmo.hdfc 
	public static  String appActivity="com.enstage.wibmo.main.MainActivity";
	protected WebDriver driver;
	public static String bankCode="6019",programName="PayZapp"; // programName case should be exactly as displayed in the app
	//	public static boolean qa=Boolean.parseBoolean(Generic.getPropValues("QA",configPath));
	
	public static boolean deviceTrialRun=true; 		// used to distinguish between Suite and Individual Run. .
	public static String appVersion="";
	public static String chromeDriverPath="./config/chromedriver.exe";
	public static HashMap<String, String> udidModel=new HashMap<String, String>();
	
	//public static String executionDeviceId="";
	
	public boolean isAppPresent=true;
	
	public static HashMap<String, Boolean> skipStatus=new HashMap<String, Boolean>();
	public static HashMap<String, Boolean> udidPermissionsGranted=new HashMap<String, Boolean>();
	public static HashMap<String, WebDriver> driverHolder=new HashMap<String, WebDriver>();
	
	private static String oldMvc="606606"; // Default value
	//`````
	
	// ==== ------------------------------ ==== // 

	private String testScenarioName;
	private String className,category="";
	private String model="";
	private String version="";
	private String resolution="";
	
	@Parameters("device-id")
	@BeforeClass
	public void launchApplication(@Optional("420085dad66ba291")String udid) // cannot remove String udid => impacts individual launchApplication()to match BaseClass <test> Parameter //
	{
		
		ExtentManager.generateFilePath();
		System.out.println("Declared Methods :"+this.getClass().getDeclaredMethods().length);
		System.out.println("Declared Annotations :"+this.getClass().getDeclaredAnnotations().length);
		System.out.println("Simple name: "+this.getClass().getSimpleName());
		
		if(!advancedLaunch(udid))
			launchApp(packageName,appActivity);		
		
		driverHolder.put(udid, driver);
		System.out.println("Driver Holder :"+driverHolder);
		
	    testScenarioName = this.getClass().getSimpleName();
	    className=this.getClass().getCanonicalName(); 
	    selectQaProgram();	 
	    BaseTest1.setWebOverlayStatus(driver, false);
	}
	
	
	
	@BeforeSuite // Match to BaseClass
	public void executeCleanUpFile()
	{
		setWebDriverProperty();
	}
	
	
	@AfterSuite
	public void closeAll()
	{
		DB.closeDB();
	}
	
	
	// Sets the Firefox Binary Path if the Firefox/exe is not in the default path.
	// 
	public void setFirefoxBinary()
	{
		String firefoxBinaryPath=ExcelLibrary.getExcelData("./config/config.xlsx", "Android_setup", 2, 5); // read from config.xls
		
		if(firefoxBinaryPath.toLowerCase().contains("fox"))
		{
			System.out.println("Setting Firefox Binary Path to "+firefoxBinaryPath);
			System.setProperty("webdriver.firefox.bin", firefoxBinaryPath);
		}		
	}
	
	//@BeforeClass
	//@Parameters("device-id") Used in  BaseClass  , udid specific to Thread
	public void initUdid(String deviceId)
	{
		udid=deviceId;
	}
	
	
	@BeforeMethod
	public void startMethod()
	{
		System.out.println("*** Executing Report Initialization ***");		
	}
	
	//@BeforeMethod
	public void startMethod(Method  testMethod)
	{
		//--------------------------------E------------------------------------------//
		
		String tcName=testMethod.getName();
		
		String testDescription="Default";		
		
		try
		{
			Method classMethod=this.getClass().getMethod("getTestScenario", String.class);
			testDescription=(String)classMethod.invoke(this, tcName);
		} 
		catch(Exception e){library.Log.info("== Error in invoking method ==");e.printStackTrace();}
		
		category=this.getClass().getCanonicalName().split("testScripts.")[1].split("\\.")[0];		
		
		
		Log.info("------------------------------------- "+tcName+" : "+testDescription+" ---------------------------------------");
		try{		
		ExtentTestManager.startTest(tcName,testDescription);		
		ExtentTestManager.getTest().assignCategory(category); } catch(Exception e){System.out.println("Warning : Unable to write to Extent");}		
				
		//---------------------------------------------------------------------------//		
	}
	
	@AfterMethod
	public void updateResults(){} // Stub to match BaseClass
	
	
	
	//@AfterMethod
	public void updateResults(ITestResult iRes)
	{
 		int res=iRes.getStatus();
		String testName=iRes.getMethod().getMethodName();
		
		
		System.out.println("=== Updating Test Result ===");
		
		{
			
			WebDriver driver=this.driver;
			Log.info("Failure --- Capturing screenshot");	
			//---------------------------------------------------------------//			
			if(CSR.getCSRStatus())
				driver=CSR.getDriver();			
			//---------------------------------------------------------------//
			try{	
				if (driver != null)
		        {
		            File f = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		            try 
		            {	
		            	File tmpFile=new File("./temp/Screen.png");
		            	FileUtils.copyFile(f, tmpFile); // Extent
		                try{Thread.sleep(2000);}catch(Exception e){}
		            	
					} catch (IOException e) { Log.info("======== Error in obtaining Screenshot ========");
						e.printStackTrace(); }			
		        }
				}
			catch(Exception e){Log.info("======== Error in obtaining screenshot ========\n"+e.getMessage());}
			
			String extScreens="./"+testName+".jpg";		
			ExtentTestManager.getTest().log(LogStatus.FAIL,"HTML", "There is an error: <br/><br/> "+iRes.getThrowable().getMessage()+" <br/><br/> Error Snapshot : "+ ExtentTestManager.getTest().addScreenCapture(extScreens));
		}
		if(res==1)
		{
			ExtentTestManager.getTest().log(LogStatus.PASS,"========== Passed: "+testName+" ==========");
		}
		if(res==3)
		{
			//ExtentTestManager.getTest().log(LogStatus.SKIP,"========== Skipped: "+testName+" =========="); // can add depends on logic or not executed 
		}
		
		// ==== E ==== //
		try{
			if(!Generic.containsIgnoreCase(driver.toString(), "Firefox"))	// If AndroidDriver then get Device details
			{
				if(model=="" || version=="" || resolution=="")   
				{
					model=(model=="")? Generic.getDeviceModel(driver):model;
					version=(version=="")? Generic.getAndroidVersion(driver):version; 
					resolution=(resolution=="")? Generic.getScreenResolution(driver):resolution;
					appVersion=(appVersion=="")?Generic.getAppVersion(driver):appVersion;
							
					/*ExtentManager.getReporter().addSystemInfo("Device Model", model);
					ExtentManager.getReporter().addSystemInfo("Android Version", version);
					ExtentManager.getReporter().addSystemInfo("Screen Resolution", resolution);
					ExtentManager.getReporter().addSystemInfo("App Version", appVersion);*/
				}							
			}	
		}catch(Exception e){Log.info("== Unable to obtain device details ==\n"+e.getMessage());}
		
		ExtentManager.getReporter().endTest(ExtentTestManager.getTest());		
		ExtentManager.getReporter().flush(); 
		// =========== //		
	}
	
	public void selectQaProgram() 
	{	
	 	if(!checkEnv("qa")) return;
	 	Generic.wait(1);
		new WebDriverWait(driver,15).until(ExpectedConditions.presenceOfElementLocated(By.id("title_text")));
		if(driver.findElement(By.id("title_text")).getText().contains("Select Program"))
			driver.findElement(By.name(programName)).click();		
	}
	
	public void selectQaProgram(WebDriver driver)
	{
		if(!checkEnv("qa")) return;
		
		library.Log.info("======== Selecting Program based on "+env+" env ========");	 	
		
		String xp="//android.widget.TextView[contains(@resource-id,'title_text')]";
		String txt=driver.findElement(By.xpath(xp)).getText();
		System.out.println("Title text : "+txt);
		
		if(txt.contains("Select Program"))
		{
			String prgXp="//*[@text='programName']".replace("programName", programName);
			library.Log.info("======== Clicking "+programName+" based on "+env+" env in "+txt+" screen========");
			driver.findElement(By.xpath(prgXp)).click();
		}
	}
	
	public void launchApp(String packageName,String appActivity)
	{
		String chromeDriverExecutablePath;
		
		DesiredCapabilities capabilities = new DesiredCapabilities();		
	    
	    // ======== IOS Capabilities ======== //
	    if(Generic.isIos(udid))
	    {
	    	    capabilities.setCapability("platformName", "ios");
			capabilities.setCapability("platformVersion", "11.1"); 
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, iosDeviceName); 
			capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.hdfc.payzappdev");  // read from config.properties 
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);						
			capabilities.setCapability(IOSMobileCapabilityType.SIMPLE_ISVISIBLE_CHECK, false); // Imp			
			capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true); 		// Chk suite behaviour 
			
		//capabilities.setCapability("shouldWaitForQuiescence", true);  Chk Slows down Execution ?			
		//	capabilities.setCapability(MobileCapabilityType.FORCE_MJSONWP, true);

	    }
	    // ======== Android Capabilities ======== //
	    else
	    {
	    	capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);		
	    	capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, udid);
	    	
	    	if(Generic.getAPILevel(udid)>=21) //Select UiAuto2 for Android 6 and above.  21=>Android 5 and 23=>Android  6
	    	{
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);			
			capabilities.setCapability("skipUnlock", true);		
			//capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT,"5724"); Uses getUiAuto2Port(udid) in Baseclass only
			//capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true); // Use in BaseClass 	
		}
	    	else
		    	capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);
	    	
	    capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, packageName);
	 	capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,appActivity);	
	 	capabilities.setCapability(AndroidMobileCapabilityType.NO_SIGN,true);	
	    capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
	    capabilities.setCapability(AndroidMobileCapabilityType.DISABLE_ANDROID_WATCHERS, true);
	    //capabilities.setCapability(AndroidMobileCapabilityType.TAKES_SCREENSHOT, true);
	    capabilities.setCapability(AndroidMobileCapabilityType.NATIVE_WEB_SCREENSHOT, true);
	    capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_PACKAGE, packageName);
	    capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, "com.enstage.wibmo.main.MainActivity,com.enstage.wibmo.wallet.ShowWarningActivity");
	    capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION, 90000);	    
	    capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT,getUiAuto2Port(udid)); 
	    	    
	    if(!(chromeDriverExecutablePath=appiumChromeDriverExecutablePath(udid)).contains("builtin")) // Set ChromeDriverExecutable for Web Overlay
	    {
    			System.out.println("Setting ChromeDriverExecutable to "+chromeDriverExecutablePath);
	    		capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromeDriverExecutablePath);
	    		
	    }
//	    ChromeOptions options = new ChromeOptions();
//		options.setCapability("androidPackage", "com.android.chrome");
//		capabilities.setCapability(AndroidMobileCapabilityType.CHROME_OPTIONS, options);
	    
	    if(!packageName.contains("hdfc"))
	    	    capabilities.setCapability(AndroidMobileCapabilityType.DONT_STOP_APP_ON_RESET, true); // Dont kill Merchant App 
	    
	    }
	    
	    // ======== Common Capabilities ======== //
	    // ==== UDID ==== //
	    if(checkUDIDConnected())
	    {
	    		capabilities.setCapability(MobileCapabilityType.UDID, udid);
	    }
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
    			   driver=new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    		   else
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);			
    		   
		} catch (MalformedURLException e) 
    	   {
			System.err.println("Incorrect URL ");e.printStackTrace();
    	   }	 
    	   
    	   handleColorOS(driver);
	    driver.manage().timeouts().implicitlyWait(45,TimeUnit.SECONDS); 	    

	    //capabilities.setCapability(AndroidMobileCapabilityType.IGNORE_UNIMPORTANT_VIEWS, true); Make sure Element Identification Matches : Leaf nodes will not be identified.
	    //capabilities.setCapability("--session-override", true); // Server Argument	    
	    //capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
	    //capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");		    
	    
	    //if(!(chromeDriverExecutablePath=appiumChromeDriverExecutable(udid)).contains("builtin"))
	    	//capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromeDriverExecutablePath);
	    
	    // === Opt Uiauto2 === //
	    //capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.0");
	    //capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true); Attempt with 1.6.5
	    //capabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);
	    
	    //noReset
	    //capabilities.setCapability("automationName", "uiautomator2");	    
	    //capabilities.setCapability("autoWebview",true);	    
	    //capabilities.setCapability("autoAcceptAlerts",true); // will not be integrated into execution
	    //capabilities.setCapability("command-timeout",180); @Deprecated    
	    //capabilities.setCapability("unicodeKeyboard",true);	// puts mobile keyboard setting to unicode. Does not work with certain Textfields
	    //capabilities.setCapability("resetKeyboard",true);		
	
	    driver.manage().timeouts().implicitlyWait(45,TimeUnit.SECONDS); 	    
	    
	}	
	
	
	// Stub method mainly used to match BaseTest2 with BaseClass
	public void launchApp(String packageName,String appActivity,String udid)
	{
		launchApp(packageName,appActivity);
	}
	
	
	
	/**
	 *  Returns the ChromeDriver Absolute Executable Path corresponding to the Chrome App Version present in the device 
	 *  If unable to map to a version "builtin" is returned , i.e use the ChromeDriver version that comes built in with Appium desktop
	 *  
	 *  Uses getChromeDriverExecutableVersion
	 *   Make sure corresponding version folders are present under  ./ChromeDriverExecutables  
	 *           
	 * @param udid
	 * @return
	 * @see Ex : ./ChromeDriverExecutables/2.18/chromedriver.exe 
	 */
	public String appiumChromeDriverExecutablePath(String udid)
	{
		try{
			//sString chromePkgName="com.android.chrome";
			String chromeExecutableVersionPath="./ChromeDriverExecutables/"
												+getChromeDriverExecutableVersion(udid)
												+"/chromedriver.exe"; // Ex : ./ChromeDriverExecutables/2.18/chromedriver.exe
						
			chromeExecutableVersionPath=System.getProperty("os.name").contains("Mac")?chromeExecutableVersionPath.replace(".exe",""):chromeExecutableVersionPath; // Trim .exe for Mac
						
			System.out.println("Relative Path for ChromeDriverExecutable : "+chromeExecutableVersionPath);
			
			if(chromeExecutableVersionPath.contains("builtin"))
			{
				System.out.println("ChromeDriverExecutable not found for "+udid+", using Appium builtin");
				return "builtin";
			}
			else
				return new File(chromeExecutableVersionPath).getAbsolutePath();							
			}
		catch(Exception e)
		{
			return "builtin"; 
		}
	}
	
	/**
	 * 
	 *	  Returns the chrome executable version corresponding to the chromeAppVersion of the Chrome Browser App in the mobile
	 *	 "builtin" version means use the chrome executable that is bundled with Appium Desktop.
	 *  
	 *  {@link https://appium.io/docs/en/writing-running-appium/web/chromedriver/}
	 *  
	 * 
	 * @param chromeAppVersion in the device
	 * @return ChromeDriver version corresponding to the chromeAppVersion
	 * 
	 * @see Upgrade to Appium 1.8.1 to use the chromedriverExecutableDir & chromedriverChromeMappingFile Android Capability
	 * @deprecated Dynamic mapping to chrome app version unreliable, Use chromeDriverExecutableVersion() 
	 */
	public String getChromeExecutableVersion(String chromeAppVersion)
	{
		
		if(true)return "2.28";
		
		try
		{
			double chromeAppVer=Double.parseDouble(chromeAppVersion.split("\\.")[0]);		
			
			System.out.println("Chrome App Version on Device : "+chromeAppVer);
		
			if(chromeAppVer<47) return "2.18"; 
			if(chromeAppVer<=54) return "2.28";		 // <=55
			if(chromeAppVer>58) return "2.38"; 
		}
		catch(Exception e)
		{
			System.err.println("-- Unable to get Chrome Executable version --"); e.printStackTrace();
		}
		   // ... Add based on future Chrome App versions 7 Check ... //
		
		return "2.28"; // builtin // Use BuiltIn ChromeDriver version bundled with Appium 
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
	 * An attempt to speed up AUT launch between testClasses by preserving driver sessions.
	 * To be used under launchApplication() for launching AUT only.
	 * Uses static driverHolder to map device udid to driver [Android:Appium or Android:UiAutomator]  
	 * 
	 * @param udid 
	 * 
	 */
	public boolean advancedLaunch(String udid) 
	{
		boolean advLaunchStatus;
		
		if(Generic.isIos(udid))
			return advancedLaunchIos(udid);
		
		try
		{
			if(driverHolder.get(udid)==null || Generic.getSessionId(driverHolder.get(udid))==null) return false;
			
			WebDriver hdriver=driverHolder.get(udid);	
			
			if(!Generic.checkAndroid(hdriver)) return false; // hdriver may point to WebSDK/Overlay instance 
			
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
			System.err.println("Advanced launch not possible\n");e.printStackTrace();
			advLaunchStatus=false;
		}
		
		  // Currently not closing session  
		 // Use session override instead of closing driver session 
		
		return advLaunchStatus;
		
	}
	
	/**
	 * 
	 * 
	 * Use Pagesource logic after upgrading to Appium 1.8.1
	 * 
	 * 
	 * @param udid
	 * @return
	 */
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
			// else hdriver.quit();
			
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
	
	
	
	public String getCompleteClassName(){
		return className;
	}
	
	public String getClassName(){
		return testScenarioName;
	}
	
	

	public void gotoVerifyMobile()
	{
		WelcomePage wp=new WelcomePage(driver);		
		Generic.gotoWelcome(driver);	// If Starting Page is Login Page , go to Welcome Page
		
		wp.register();	           // Click on Register button , If no register button it is Kotak		
	}
	
	public void gotoManagecards(String data)
	{
		String[] values=data.split(",");
		String loginId=values[0],securePin=values[1];		
		
		WelcomePage wp=new WelcomePage(driver);
		wp.selectUser(loginId);
		
		LoginNewPage lnp=new LoginNewPage(driver);
		lnp.login(securePin);
		
		Generic.verifyLogin(driver,data);
		
		HomePage hp=new HomePage(driver);
		hp.gotoManageCards();	
		
	}
	
	
	
	
	public static boolean checkEnv(String environment)
	{
		return env.contains(environment);
	}
	
	public AndroidDriver getDriver() 
	{
		return (AndroidDriver)driver;
	}
	
	//==========================Skip Status====================================//
	public void setSkipStatus(WebDriver driver,boolean status)
	{
		skipStatus.put(getUdid(driver), status);
	}
	
	public boolean getSkipStatus(WebDriver driver)
	{
		return skipStatus.get(getUdid(driver));
	}	
	//=========================================================================//
	
	public String getUdid(WebDriver driver)
	{
		if(!Generic.containsIgnoreCase(driver.toString(), "Android")) return "";
		
		return ((String)((AndroidDriver)driver).getCapabilities().getCapability(MobileCapabilityType.UDID));
		
		//return ((String)((AndroidDriver)driver).getCapabilities().getCapability("deviceName"));		
	}
		
	//@AfterMethod
	public void tear()
	{
		Generic.resetPopups();
		if(driver!=null)
			driver.quit();		
		
		/*try{
			Runtime.getRuntime().exec("adb shell am start com.enstage.wibmo.staging.hdfc/com.enstage.wibmo.main.MainActivity");
		}catch(Exception e){System.out.println("Runtime!");}*/
	}
	
	public String getTestScenario() {
		return testScenario;
	}
	
	public void setWebDriverProperty()
	{
		setFirefoxBinary();
		
		String geckoExecutablePath="./config/geckodriver"+(System.getProperty("os.name").contains("Mac")?"":".exe");
		String chromeExecutablePath="./config/chromedriver"+(System.getProperty("os.name").contains("Mac")?"":".exe");
		
		System.out.println("Setting WebDriver properties");
		//System.setProperty("webdriver.firefox.marionette",geckoExecutablePath);		
		System.setProperty("webdriver.gecko.driver",geckoExecutablePath);
		System.setProperty("webdriver.chrome.driver", chromeExecutablePath);		
	}
	
	public void installApp()
	{
		try
		{
			Generic.installProgram(driver);
		}
		catch(Exception e)
		{
			((AndroidDriver)driver).installApp(new File(Generic.getApkPath()).getAbsolutePath());
		}
		catch(AssertionError ae)
		{
			((AndroidDriver)driver).installApp(new File(Generic.getApkPath()).getAbsolutePath());
		}
		
	}
	
	public boolean checkUDIDConnected()
	{
		try
		{
			
			String chkCmd=Generic.isIos(udid)?"xcrun instruments -s devices":"adb devices";
			
			return Generic.execCmd(chkCmd).contains(udid);  
			
		}
		catch(Exception e)
		{
			System.err.println("Error while checking for connected UDID "+udid+'\n'+e.getMessage());
			return false;
		}
	}	
	
	public void installProgram()
	{
		String installCmd="";		
		
		if(isAppPresent) return;
		if(!packageName.toLowerCase().contains("qa")) return;
		
		//executionDeviceId to set after driver is launched previously
		String appPath=Generic.getApkPath();
		
		
		if(udid.isEmpty())
			installCmd="adb"+" install "+appPath;
		else
			installCmd="adb -s "+udid+" install -r "+appPath;
		
		try
		{
			Log.info("==== "+packageName+" App not present , Installing app with "+installCmd+" ===");
			Runtime.getRuntime().exec(installCmd).wait();							
		}
		catch(Exception e)
		{
			Log.info("Unable to install app \n"+e.getMessage());			
		}	
		
		isAppPresent=true;
	}
	
	public void uninstallProgram()
	{
		String uninstallCmd="";
		
		if(!isAppPresent) return;
		if(!packageName.toLowerCase().contains("qa")) return;		
		
		//executionDeviceId=setExecutiondeviceId();
		
		if(udid.isEmpty())
			uninstallCmd="adb shell pm uninstall -k "+packageName;
		else
			uninstallCmd="adb -s "+udid+" shell pm uninstall -k "+packageName;
		
		try
		{
			Log.info("==== Uninstalling "+packageName+"  app with "+uninstallCmd+" ===");
			Runtime.getRuntime().exec(uninstallCmd);							
		}
		catch(Exception e)
		{
			Log.info("Unable to uninstall app \n"+e.toString());			
		}	
		
		Log.info("==== App Uninstalled ===");
		isAppPresent=false;		
	}
	
	public String setExecutionDeviceId()
	{
		if(!udid.isEmpty()) return "";
		
		try
		{
			udid=(String)((AndroidDriver)driver).getCapabilities().getCapability("deviceName");
		}
		catch(Exception e)
		{ System.out.println("== Unable to set execution Id");}		
		
		return udid;			
	}
	
	public void handleColorOS(WebDriver driver)
	{
		if(getUdid(driver).equals("SK55NZ7H99999999")) // Oppo
		{
			System.out.println("Setting Idle timeout to 0 ms");			
			((AndroidDriver)driver).setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 0); 	// or 0			
		}
	}
	
	
	public static void grantAppRuntimePermissions(WebDriver driver) // Stub to match BaseClass
	{}
	
	
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
				if(tcRowVal.equals(udid))
				{
					deviceModel=ExcelLibrary.getExcelData(configXLPath, sheet, i, deviceModelColumn); 
					udidModel.put(udid, deviceModel);						
				}
			}
	}catch(Exception e) {System.err.println("Error retrieving Device model for "+udid+'\n');e.printStackTrace();}
	
	return deviceModel;
	}
	
	public synchronized int getUiAuto2Port(String udid)
	{
		long x = 1023;
		long y = 65535;		
		
		long uiAuto2Port;
		
		Random r = new Random();		
		long number = x+((long)(r.nextDouble()*(y-x)));	
		
		System.out.println("UIAutomator2 port for "+udid+" : "+number);
		return (int)number;	  
	}
	
	/**
	 * 	 Handle Alerts generated from previous TestCases 
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
	
	// @AfterClass Comment this for WebSDK development to prevent multisession
	public void quit() 
	{
		
		handleIOSDelayedAlert();
		
		System.out.println("== Logging Out ==");
		new BasePage(driver).logOut();
		
		// handleIOSDelayedAlert();
		
		DB.closeDB();
		
		if(Generic.isWebDriver(driver) && driver!=null)  
		{
			try { driver.close(); driver.quit(); } catch (Exception e) {System.err.println("WebDriver Close	Error");}
			return;
		}
	
	}
	
	
	
	
}
