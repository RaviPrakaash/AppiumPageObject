package wibmo.app.testScripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import library.Log;
import library.BaseClass;
import library.CSR;
import library.DB;
import library.ExcelLibrary;
import library.Generic;
import wibmo.api.repo.API;
import wibmo.app.pagerepo.AddCardPage;
import wibmo.app.pagerepo.AddMoney3DSPage;
import wibmo.app.pagerepo.AddMoneyFinalPage;
import wibmo.app.pagerepo.AddMoneyPage;
import wibmo.app.pagerepo.AddMoneySelectCardPage;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.ManageCardsPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.ProgramCardPage;
import wibmo.app.pagerepo.RechargePage;
import wibmo.app.pagerepo.RegisterMobilePage;
import wibmo.app.pagerepo.RegisterPage;
import wibmo.app.pagerepo.RegistrationSuccessfulPage;
import wibmo.app.pagerepo.SettingsPage;
import wibmo.app.pagerepo.SimVerificationPage;
import wibmo.app.pagerepo.VerifyDevicePage;
import wibmo.app.pagerepo.VerifyMobilePage;
import wibmo.app.pagerepo.VerifyPhonePage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.SendMoney.BaseTest;
import wibmo.app.pagerepo.BasePage;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * The Class BaseTest1.
 * 
 */
public class BaseTest1 extends BaseDev // BaseClass=Execution . BaseDev=Development
{

	/** The old mvc. */
	protected static String oldMvc = "606606"; 	// Default value

	/**
	 * The SDK WebDriver to corrersponding udid map. CSR.wdriver(driver) needs to
	 * access udid for non Android driver.
	 * 
	 */
	public static HashMap<Integer, String> sdkDriverUdid = new HashMap<Integer, String>();

	public static boolean appDataClear;
	public static boolean ITP = false, phoneVerification = false;
	public static String autVersion = "";

	/** Used to store as udid=newlyRegisteredMobileNumber */
	public static HashMap<String, PriorityQueue<String>> newlyRegisteredMobileNos = new HashMap<String, PriorityQueue<String>>();

	public static ChromeDriverService chromeService;
	private static boolean deviceRegression=true;

	/**
	 * The Map used to check if the device is currently executing Web Overlay flow.
	 * Set to false from BaseTest of Respective package. Set to true from the Test
	 * Script executing Web Overlay.
	 */
	public static HashMap<String, Boolean> webOverlayStatus = new HashMap<String, Boolean>();
	

	public String getTestData(String tcId) {
		return getTestVal(tcId, "data");
	}

	public String getTestScenario(String tcId) {
		return getTestVal(tcId, "scenario");
	}

	/**
	 * @author aravindanath
	 * @param gettitle
	 * @param getKey
	 * @return value of the specified key under the given title 
	 */

	public String getValByKey(String tcId, String key) {

		String environment = env.contains("staging") ? "staging" : "qa";

		String platform = Generic.isIos(driver) ? "ios" : "android",
				module = this.getClass().getCanonicalName().split("testScripts.")[1].split("\\.")[0],
				path = "./excel_lib/" + environment + '/' + platform + '/' + "TestData.ini";

		Ini ini = null;
		try {
				ini = new Ini(new File(path));
		} catch (IOException e) { Assert.fail("TestData.ini file not found");}
		
		String value = ini.get(tcId, key);
		if ((value == null)) 
			Assert.fail(key+" value not found for "+tcId);
		
		return value;
	}
	
	public String getVal(String csvData, String key)	
	{
		return  csvData.split(key+'=')[1].split(",")[0];
	}

	public String getPlatform() {
		String platform = "";

		if (Generic.isAndroid(driver))
			platform = "Android";
		if (Generic.isIos(driver))
			platform = "IOS";

		// if driver==null
		// set platform based on package name for non driver execution

		return platform;
	}

	public String getTestVal(String tcId, String val) {

		if(udid==null) 
		{
			System.err.println("Warning : udid not initialised "); return "";
		}
		
		int col = val.equals("data") ? 2 : 1; // TestData:TestScenario
		String environment = env.contains("staging") ? "staging" : "qa";

		String tcRowVal;

		String platform = Generic.isIos(udid) ? "ios" : "android",
				module = this.getClass().getCanonicalName().split("testScripts.")[1].split("\\.")[0],
				path = "./excel_lib/" + environment + '/' + platform + '/' + "TestData.xlsx";

		int rc = ExcelLibrary.getExcelRowCount(path, module);
		for (int i = 0; i <= rc; i++) {
			tcRowVal = ExcelLibrary.getExcelData(path, module, i, 0);
			if (tcRowVal.equals(tcId))
				return ExcelLibrary.getExcelData(path, module, i, col);
		}
		return "DefaultVal" + tcId + ':' + module;
	}

	/**
	 * Returns the Wibmo Device Id corresponding to the driver udid from config.xlsx
	 * Uses column offset
	 * 
	 * @see getWibmoSimId()
	 */
	public String getWibmoDeviceId() {

		String udid = getUdid(driver), sheet = "Modules", wibmoDeviceId = "", tcRowVal;

		int rc, udidColumn = 9, wDeviceIdColOffset = 4; // Column Offset from udid column

		try {
			rc = ExcelLibrary.getExcelRowCount(configXLPath, sheet);

			for (int i = 0; i <= rc; i++) {
				tcRowVal = ExcelLibrary.getExcelData(configXLPath, sheet, i, udidColumn);

				if (tcRowVal.contains(udid))
					wibmoDeviceId = ExcelLibrary.getExcelData(configXLPath, sheet, i, udidColumn + wDeviceIdColOffset);
			}
		} catch (Exception e) {
			System.err.println("Error retrieving Device model for " + udid + '\n');
			e.printStackTrace();
		}

		return wibmoDeviceId;

	}

	/**
	 * Returns the Wibmo SIM Id corresponding to the driver udid from config.xlsx
	 * Uses column offset
	 * 
	 * @see getWibmoDeviceId()
	 */
	public String getWibmoSimId() {

		String udid = getUdid(driver), sheet = "Modules", wibmoDeviceId = "", tcRowVal;

		int rc, udidColumn = 9, wSimIdColOffset = 5; // Column Offset from udid column

		try {
			rc = ExcelLibrary.getExcelRowCount(configXLPath, sheet);

			for (int i = 0; i <= rc; i++) {
				tcRowVal = ExcelLibrary.getExcelData(configXLPath, sheet, i, udidColumn);

				if (tcRowVal.contains(udid))
					wibmoDeviceId = ExcelLibrary.getExcelData(configXLPath, sheet, i, udidColumn + wSimIdColOffset);
			}
		} catch (Exception e) {
			System.err.println("Error retrieving Device model for " + udid + '\n');
			e.printStackTrace();
		}

		return wibmoDeviceId;

	}

	public WebDriver createSDKDriver() {
		return Generic.getPropValues("SDKBROWSER", configPath).equalsIgnoreCase("Firefox")
				? Generic.createFirefoxDriver()
				: Generic.createChromeDriver();
	}

	/**
	 * Sets the ITP falg to true, to be used by VerifyPhonePage If ITP flag is true
	 * then Phone Verification is triggered under VerifyPhonePage
	 * 
	 */
	public static void setITP(boolean ITPStatus) {
		ITP = ITPStatus;
	}

	public static boolean getITP() {
		return ITP;
	}

	public static void setPhoneVerifyStatus(boolean phoneVerifyStatus) {
		phoneVerification = phoneVerifyStatus;
	}

	public static boolean getPhoneVerifyStatus() {
		return phoneVerification;
	}

	public static void setWebOverlayStatus(WebDriver driver, boolean status) {
		if (driver == null)
			return;
		if (!Generic.checkAndroid(driver))
			return;

		webOverlayStatus.put(Generic.getUdId(driver), status);
	}

	public static boolean getWebOverlayStatus(WebDriver driver) // Not Used currently
	{
		if (!Generic.checkAndroid(driver))
			return true;

		String udid = Generic.getUdId(driver);

		if (webOverlayStatus.containsKey(udid))
			return webOverlayStatus.get(udid);
		else
			return false;
	}

	public static boolean walletITPDirect() {

		try {
			return Generic.getPropValues("WALLETITPDIRECT", configPath).toLowerCase().contains("true");
		} catch (Exception e) {
			return false;
		} // Default WalletCardITP false (3DS flow)
	}

	public void switchToMerchant() {

		Log.info("======== Opening merchant app ========");
		if (Generic.isAndroid(driver)) 
		{
			switchToMerchantAdvanced();
			return;
		}
		else
		{
			Map<String, Object> params = new HashMap<String, Object>(); 
			params.put("bundleId", Generic.getPropValues("MERCHANTBUNDLEID", BaseTest1.configPath)); 
																										
			((IOSDriver) driver).executeScript("mobile: launchApp", params);
		}
	}

	/**
	 * Launches the Merchant App from the adb command line without restarting the
	 * app return value to be integrated
	 */
	public void switchToMerchantAdvanced() {
		String cmd = "adb -s udid shell monkey -p packageName -c android.intent.category.LAUNCHER 1"
				.replace("udid", getUdid(driver))
				.replace("packageName", Generic.getPropValues("MERCHANTPACKAGE", BaseTest1.configPath));

		Log.info("======== Opening merchant app from adb using " + cmd + " ========");
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			Assert.fail("Unable to launch Merchant App" + e.getMessage());
		}

		Generic.wait(2);
		Generic.swipeUp(driver);
		Generic.wait(1);

		if (Generic.checkTextInPageSource(driver, "Txn Desc", "summary").contains("notFound")) // Relaunch merchant app
		{
			Log.info("=== Restarting Merchant App ===");
			((AndroidDriver) driver)
					.startActivity(new Activity(Generic.getPropValues("MERCHANTPACKAGE", BaseTest1.configPath),
							Generic.getPropValues("MERCHANTACTIVITY", BaseTest1.configPath)));
		}

	}

	/**
	 * Starts / Restarts the App and switches to the App. Can also be used to
	 * perform force logout
	 * 
	 */
	public void switchToApp() {
		if (Generic.isAndroid(driver)) // Android
		{
			Activity appActivity = new Activity(BaseTest1.packageName, BaseTest1.appActivity);
			Log.info("======== Opening AUT ========");
			((AndroidDriver) driver).startActivity(appActivity);
		} else // IOS
		{
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("bundleId", Generic.getPropValues("BUNDLEID", BaseTest1.configPath));

			System.out.println("=== Restarting IOS App with " + params + " ===");
			((IOSDriver) driver).executeScript("mobile: terminateApp", params);
			Generic.wait(3);
			((IOSDriver) driver).executeScript("mobile: launchApp", params);
		}
	}

	public void switchToAppWithState() {
		
		Log.info("======== Switching to app with previous state =======");
		String appName = programName;
		if (checkEnv("qa"))
			appName = "QA";

		String xp = "//*[contains(@text,'" + appName
				+ "') or contains(@text,'Login') or contains(@text,'Wallet Home')]/.."; // Can Use Tap if necessary
		String checkerXp = "//android.widget.TextView[contains(@text,'Wibmo') or contains(@resource-id,'title_text')]";

		try {
			((AndroidDriver) driver).pressKeyCode(187); // KEYCODE_APP_SWITCH
			Generic.wait(1);
			driver.findElement(By.xpath(xp)).click();
			Generic.wait(1);

			String title = driver.findElement(By.xpath(checkerXp)).getText();
			Log.info("======== Verifying AUT Page Title : " + title + " ========");

		} catch (Exception e) {
			Log.info("Error during App Switch\n" + e.getMessage());
		}
	}

	/**
	 * Sets a newly registered mobile no. to be used by any TC which requires a
	 * newly registered number.
	 * 
	 */
	public void setNewlyRegisteredMobileNumber(String mobileNo) {
		String udid = getUdid(driver);

		if (newlyRegisteredMobileNos.get(udid) == null) {
			Log.info("======== Creating new queue for " + udid + '=' + mobileNo + " ========");
			PriorityQueue<String> pq = new PriorityQueue<String>();
			pq.add(mobileNo);

			newlyRegisteredMobileNos.put(udid, pq);
		} else {
			Log.info("======== Adding " + mobileNo + " to Map " + newlyRegisteredMobileNos + " ========");
			newlyRegisteredMobileNos.get(udid).add(mobileNo);
		}

	}

	/**
	 * Gets a newly registered mobile specific to the device. Can be called only
	 * once per TestCase
	 * 
	 * 
	 */
	public String getNewlyRegisteredMobileNumber() {
		String udid = getUdid(driver); // if(true) return "1234566016";

		if (newlyRegisteredMobileNos.get(udid) == null)
			return "";
		else
			return newlyRegisteredMobileNos.get(udid).peek() == null ? "" : newlyRegisteredMobileNos.get(udid).poll();
	}

	// ------- WebSDK Mapping to UDID ---------- //
	public static String getSDKUdid(WebDriver driver) // To Support Parallel WebSDK Executions
	{
		String udid = sdkDriverUdid.get(driver.hashCode()); // .get(driver)
		if (udid == null)
			udid = "NotFound";

		return udid;
	}

	// ----------------------------------------//
	/**
	 * Sets the mapping from a non AndroidDriver to the corresponding udid. To be
	 * set under the Test Class
	 * 
	 * @param driver nonAndroidDriver
	 * @param udid   corresponding to the device
	 * 
	 */
	public void setSDKUdid(WebDriver driver, String udid) {
		if (driver == null) {
			System.err.println("sdk Driver value was null");
			return;
		}
		try {
			
			sdkDriverUdid.put(driver.hashCode(), udid); // (driver,udid)
			CSR.setCSRStatus(driver, false);
		} catch (Exception e) {
			System.out.println("Unable to map sdk Driver to udid ");
			e.printStackTrace();
		}
	}
	// -----------------------------------------//

	/**
	 * Verifies whether the user is logged in or not. Handles all the Verification
	 * pages occurring between Login Page and Home Page including Unclaimed. Uses
	 * driver.getPageSource() to identify the Pages Can be granulated as
	 * verifyPhoneDevice()
	 *
	 * @param driver the driver
	 * @param data   the data
	 * @see merchantVerifiedLogin()
	 */
	public String verifyLogin(String data) {
		
		if (Generic.isIos(driver)) {
			verifyLoginIos(data);
			return "";
		}

		String homeIdentifier = "PayZapp Home", kycIdentifier = "RBI"; // Change to Skip or pagesource text=skip
		String mobileNo = data.split(",")[0];
		String checker = Generic.waitUntilTextInPage(driver, 45, "Request timed","Sorry,", "Verify Phone", "Verify Device",
				"got_it", "support!", kycIdentifier, homeIdentifier); // Integrate OK

		String loginFlow = ">";

		// ======== Request timed out error ======== //
		if (checker.contains("Request") ||checker.contains("Sorry") ) {
			new LoginNewPage(driver).handleRequestTimeOut(data);
			checker = Generic.waitUntilTextInPage(driver, 45, "Verify Phone", "Verify Device", "Unclaimed", "got_it",
					"support!", kycIdentifier, homeIdentifier); 	// Integrate OK
		}
		// =================== //

		if (checker.contains("Verify Phone")) {
			Log.info("======== Going through " + checker + " page ========");

			if (BaseTest1.getITP())
				new VerifyPhonePage(driver).loginWithPersonalDeviceWithSkip(data); // data added
			else
				new VerifyPhonePage(driver).loginWithoutPersonalDevice();// loginWithPersonalDeviceWithSkip();

			checker = Generic.waitUntilTextInPage(driver, 45, "Verify Device", "Unclaimed", "got_it", "support!",
					kycIdentifier, homeIdentifier); // Integrate OK

			loginFlow += ""; // Verify Phone Page >

		}

		if (checker.contains("Device")) {
			new VerifyDevicePage(driver).loginWithTrustedVerifyDevice(data.split(",")[0], BaseTest1.bankCode);

			checker = Generic.waitUntilTextInPage(driver, 60, "Unclaimed", "got_it", "support!", kycIdentifier,
					homeIdentifier);

			loginFlow += ""; // VerifyDevicePage >
		}

		if (checker.contains("Unclaimed")) {
			if (BaseTest.checkUnclaimed)
				return loginFlow; // BaseTest of SendMoney module - Do not handle Unclaimed page if executing
									// Unclaimed scenario

			Log.info("======== Handling Unclaimed ========");
			Generic.navigateBack(driver);
			Generic.wait(2);
			checker = Generic.waitUntilTextInPage(driver, 45, "got_it", "support!", kycIdentifier, homeIdentifier);
		}

		if (checker.contains("got_it")) {
			Log.info("==== Handling Home Coach ====");
			Generic.navigateBack(driver);

			checker = Generic.waitUntilTextInPage(driver, 45, "support!", kycIdentifier, homeIdentifier); // RBI alert
																											
		}

		if (checker.contains("support!") || checker.contains(kycIdentifier)) {
			try {
				Log.info("==== Handling Rate App/RBI Alert ====");
				Generic.wait(2); // Hande coach sync before and ater Alert

				if (checker.contains(kycIdentifier))
					Generic.navigateBack(driver);

				loginFlow += driver.findElement(By.xpath("//*[contains(@resource-id,'message')]")).getText();
				driver.findElement(By.xpath("//*[contains(@resource-id,'button2') or @text='Skip']")).click();
				Generic.wait(3); // Wait for button click
				Generic.navigateBack(driver); // Handle Coach if Any

				return loginFlow;
			} catch (Exception e) {
				Log.info("== Error in handling RateApp / RBI Alert == " + e.getMessage().substring(0, 50) + " ...");
			}
		}

		if (checker.contains(homeIdentifier)) {
			checker = Generic.waitUntilTextInPage(driver, 45, "got_it", "support!", homeIdentifier);
			if (checker.contains("got_it") || checker.contains("support!")) {
				Log.info("Warning : Navigating back to handle " + checker);
				Generic.navigateBack(driver);
			}
		}

		return loginFlow;
	}
	
	/**
	 *  INI Compatible . 
	 *  
	 * 
	 * @param loginDetails as loginId,securePin
	 * @return
	 */
	public String verifyLogin(String...loginDetails)
	{
		String loginId=loginDetails[0],securePin=loginDetails[1];
		return verifyLogin(loginId+','+securePin);
	}

	/**
	 * Performs a Login in the Merchant app and handles any popups by calling the
	 * paralell thread HandleOverlayPopup.
	 *
	 * @param driver the driver
	 * @param data   the data
	 */
	public void merchantVerifiedLogin(String data) {
		// ==== IOS ==== //
		if (Generic.isIos(driver)) {
			String predicate = "value beginswith 'Verify' or value beginswith 'Payment'";

			if (driver.findElement(MobileBy.iOSNsPredicateString(predicate)).getText().contains("Verify")) {
				Log.info("======== Entering DVC and clicking on Proceed ========");
				driver.findElement(By.className("XCUIElementTypeTextField")).sendKeys(Generic.getOTP(data.split(",")[0],
						bankCode, Generic.getPropValues("DVC", BaseTest1.configPath)));
				driver.findElement(MobileBy.AccessibilityId("Proceed")).click();
			}
			return;
		}

		// ==== Android ==== //
		String xp = "//android.widget.TextView[contains(@text,'Verify') or contains(@resource-id,'txn_merchant_name') or contains(@text,'Request')] | //android.widget.Button[contains(@resource-id,'got')]";

		WebDriverWait wait = new WebDriverWait(driver, 60);
		Generic.wait(2); // UiAuto2

		// ======== Request timed out error ======== //
		if (driver.findElement(By.xpath(xp)).getText().contains("Request")) {
			new MerchantHomePage(driver).handleRequestTimeOut(data);
			try {
				wait(2);
				wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath(xp))));
				wait(2);
			} catch (Exception e) {
				Assert.fail(" Page taking too much time to load, stopping execution\n");
			}
		}

		// ==========================================//

		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));
		} catch (Exception e) {
			Assert.fail("Page not found or taking too much time to load\n" + e.getMessage());
		}

		if (!driver.findElement(By.xpath(xp)).getText().contains("Verify"))
			return;

		if (driver.findElement(By.xpath(xp)).getText().contains("Phone")) {
			Log.info("======== Going through Verify Phone page ========");
			new VerifyPhonePage(driver).loginWithoutPersonalDevice();
			Generic.wait(3);
			new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));
		}
		if (driver.findElement(By.xpath(xp)).getText().contains("Device")) {
			Log.info("======== Going through Verify Device page ========");
			new VerifyDevicePage(driver).loginWithTrustedVerifyDevice(data.split(",")[0], BaseTest1.bankCode);
			Generic.wait(2);
		}
	}

	/**
	 * Sets the Bank Response simulator settings page to the given errCode & errDesc
	 * 
	 * 
	 * @param errCode
	 * @param errDesc
	 */
	public void setBankResponseSim(String errCode, String errDesc) {
		By errCodeTxtField = By.id("bankErrorCodeToBeSet"), errdescTxtField = By.id("bankErrorDescToBeSet"),
				currentConfigValueField = By.xpath("(//label)[last()]"),
				setValBtn = By.xpath("//input[@type='submit']");

		String currentConfig;
		WebDriver driver = null;
		try {

			driver = Generic.createFirefoxDriver();

			Log.info("==== Launching Bank Response Simulator URL :  ====");
			driver.get(Generic.getPropValues("BANKRESPONSESIM", configPath));

			// Preset Val Chk not reliable

			Log.info("==== Entering Error Code : " + errCode + " ====");
			driver.findElement(errCodeTxtField).sendKeys(errCode);

			Log.info("==== Entering Error Desc : " + errDesc + " ====");
			driver.findElement(errdescTxtField).sendKeys(errDesc);

			Log.info("==== Clicking Set New Value  ====");
			driver.findElement(setValBtn).click();

			currentConfig = driver.findElement(currentConfigValueField).getText();
			Log.info("==== Bank Simulator Config set to : " + currentConfig + "  ====");

		} catch (Exception e) {
			Assert.fail("Error in setting Bank Sim Configuration" + e.getMessage());
			e.printStackTrace();
		} finally {
			driver.quit();
		}

	}

	public void merchantVerifiedLoginIos(String data) {

	}

	/**
	 *  Ini compatible 
	 * 
	 * @param loginDetails
	 * @see checkWalletBalance(data)
	 */
	public double checkWalletBalance(String... loginDetails) {

		return checkWalletBalance(loginDetails[0] + ',' + loginDetails[1]);

	}

	public double checkWalletBalance(String data) {
		gotoAddSend(data);

		AddSendPage asp = new AddSendPage(driver);
		return asp.verifyBalance();
	}

	/**
	 * Logs in and navigates to the Add/Send page.
	 *
	 * @param driver the driver
	 * @param data   the data
	 */
	public void gotoAddSend(String data) {
		int i = 0;
		String[] values = data.split(",");
		String loginId = values[i++];
		String securePin = values[i++];

		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);

		verifyLogin(data);

		HomePage hp = new HomePage(driver);
		hp.addSend();
	}

	public void verifyLoginIos(String data) {
		// Use Predicate and retrieve text
		IOSDriver iDriver = (IOSDriver) driver;
		String pageChk = "default", checkerPredicate, homeCheckerPredicate, kycHomeAlertIdentifier = "RBI mandate";

		FluentWait<IOSDriver> wait = new FluentWait<IOSDriver>(iDriver);
		wait.pollingEvery(1, TimeUnit.SECONDS);
		wait.withTimeout(90, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);

		// checkerPredicate="name IN {'Verify Phone','Verify Device','PayZapp Home'}";
		checkerPredicate = "type='XCUIElementTypeStaticText'  and (value contains ' Verification' or value contains 'Hi 'or value contains 'kycHomeAlertIdentifier') and visible=true"
				.replace("kycHomeAlertIdentifier", kycHomeAlertIdentifier); // From Login Page navigate to Phone
																			// Verification/ Device Verification / Home
																			// Page with Hi , Use value attribute for
																			// getText()

		try {
			wait.until(ExpectedConditions
					.visibilityOf(iDriver.findElement(MobileBy.iOSNsPredicateString(checkerPredicate))));
			pageChk = iDriver.findElement(MobileBy.iOSNsPredicateString(checkerPredicate)).getText();
		} catch (Exception e) {
			Assert.fail("Page taking too much time to load \n" + e.getMessage());
		}

		if (pageChk.contains("Phone") || pageChk.contains("Device")) {
			Log.info("======== Going through " + pageChk + " Page ========");

			new VerifyPhonePage(driver).loginWithoutPersonalDevice();
			new VerifyDevicePage(driver).loginWithTrustedVerifyDevice(data.split(",")[0], BaseTest1.bankCode);

			checkerPredicate = "type='XCUIElementTypeStaticText'  and (value contains 'Hi 'or value contains 'kycHomeAlertIdentifier') and visible=true"
					.replace("kycHomeAlertIdentifier", kycHomeAlertIdentifier);

			pageChk = iDriver.findElement(MobileBy.iOSNsPredicateString(checkerPredicate)).getText();
		}

		if (pageChk.contains(kycHomeAlertIdentifier)) {
			Log.info("======== Validating KYC Alert in HomePage :  " + pageChk + " ========");
			driver.findElement(MobileBy.AccessibilityId("Skip")).click(); // Can use flag based Assert validation , flag
																			// set from TC
			return;
		}

		// Log.info("======== Waiting for Home Page to load ========");
		// iDriver.findElementByClassName("XCUIElementTypePageIndicator"); // Wait for
		// Home page to load

	}

	public void navigateBack() {
		System.out.println("Navigating Back");
		driver.navigate().back();
	}

	/**
	 * Post condition registration.
	 */
	public static void postConditionRegistration() {
		try {
			if (env.contains("jenkins")) // Generate TestData for next jenkins run
			{
				ExcelLibrary.writeExcelData("./excel_lib/" + env + "/TestData.xls", "Registration", 2, 2,
						Generic.generateMobileNumber());
				Generic.wait(3);
			}
		} catch (Exception e) {
			Log.info("Postcondition Registration : Unable to write to TestData for next jenkins run");
		}
	}

	/**
	 * Post condition smoke.
	 */
	public static void postConditionSmoke() {
		if (env.contains("jenkins")) // Generate TestData for next jenkins run
		{
			ExcelLibrary.writeExcelData("./excel_lib/" + env + "/TestData.xls", "SmokeTest", 1, 5,
					Generic.generateMobileNumber());
			Generic.wait(3);
		}
	}

	/**
	 * Logs in and navigates to Recharge Page
	 *
	 * @param data the data
	 */
	public void gotoRecharge(String data) {
		String[] values = data.split(",");
		String loginId = values[0], securePin = values[1];

		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);

		verifyLogin(data);

		HomePage hp = new HomePage(driver);
		hp.gotoRecharge();
	}

	public void gotoMobileRecharge(String data) {
		gotoRecharge(data);
		RechargePage rp = new RechargePage(driver);
		rp.gotoMobileRecharge();
	}

	/**
	 * Navigates to Verify Mobile page.
	 */
	public void gotoVerifyMobile() {
		new BaseTest1().selectQaProgram(driver); // Opening page may be QA Select Program page

		WelcomePage wp = new WelcomePage(driver);
		wp.gotoWelcome();
		wp.register();
	}

	/**
	 * Navigates to Manage Cards after logging in.
	 *
	 * @param data the data
	 */
	public void gotoManagecards(String data) {
		String[] values = data.split(",");
		String loginId = values[0], securePin = values[1];

		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);

		verifyLogin(data);

		HomePage hp = new HomePage(driver);
		hp.gotoManageCards();
	}

	/**
	 * Precondition recharge.
	 *
	 * @param data the data
	 */
	public void preconditionRecharge(String data) {
		Log.info("======== Checking for Recharge preconditions ========");
		gotoManagecards(data);

		ManageCardsPage mcp = new ManageCardsPage(driver);

		// ==== Check whether cards are present or not ==== //

		String xp = "//android.widget.TextView[contains(@resource-id,'card_alias') or contains (@resource-id,'nocard_text')]";
		if (driver.findElement(By.xpath(xp)).getAttribute("resourceId").contains("nocard_text")) {

			String[] values = data.split(",");
			String cardName = values[2], cardNumber = values[3], expMonth = values[4], expYear = values[5];

			Log.info("======== No cards found , adding card : " + cardName + " ========");
			mcp.gotoAddCard();

			AddCardPage acp = new AddCardPage(driver);
			acp.addCardDetails(cardName, cardNumber, expMonth, expYear);
		}

		try {
			mcp.closeManagecards();
		} catch (Exception e) {
			Log.info("======== Card was not added successfully, dependant testcases may fail ========");
		}

		HomePage hp = new HomePage(driver);
		hp.addSend();

		AddSendPage asp = new AddSendPage(driver);

		// ==== Check whether Program card is present or not ==== //

		if (asp.verifyBalance() == 0.00) {
			String[] values = data.split(",");
			String cardName = values[2], amt = values[6], cvv = values[7], pin = values[8];

			Log.info("======== Opening Program card by adding money ========");

			asp.clickLoadMoney();

			AddMoneySelectCardPage amscp = new AddMoneySelectCardPage(driver);
			amscp.selectCard(cardName);

			AddMoneyPage amp = new AddMoneyPage(driver);
			// amp.addMoney(amt,cvv);

			AddMoney3DSPage am3p = new AddMoney3DSPage(driver);
			am3p.addMoney3ds(pin);

			AddMoneyFinalPage amfp = new AddMoneyFinalPage(driver);
			amfp.verifyTransactionSuccess();
		}
		Generic.logout(driver);

		Log.info("======== Precondition set for Recharge module ========");
	}

	/**
	 * Used to Uninstall Program to Trigger Web Overlay.
	 *
	 * @param driver
	 * @see setWebOverlay() under MerchantSettingsPage
	 */
	public void uninstallProgram(WebDriver driver) {
		String uninstallCmd = "";

		// Set Map udid for BaseTest
		//

		/*
		 * if(!isAppPresent) { Log.info("--- App is already uninstalled ---"); return; }
		 */

		if (!packageName.toLowerCase().contains("qa"))
			return; // Method used only in QA

		if (!((AndroidDriver) driver).isAppInstalled(packageName)) {
			System.out.println(":::: App with bundleId " + BaseTest1.packageName + " is already uninstalled ::::");
			return;
		}

		String udid = Generic.getExecutionDeviceId(driver);

		uninstallCmd = "adb -s " + udid + " shell pm uninstall " + packageName; // -k

		try {
			Log.info("==== Uninstalling " + packageName + " app with " + uninstallCmd + " ===");
			Runtime.getRuntime().exec(uninstallCmd);
			isAppPresent = false;
		} catch (Exception e) {
			Log.info("Unable to uninstall app \n" + e.toString()); // Do not use library.Log
		}
	}

	/**
	 * Installs the AUT to the device with the given udid. The apkPath should have
	 * the same version of the AUT which was previously uninstalled. Used mainly as
	 * postcondition after triggerring WebOverlay in QA Environment.
	 *
	 * @param driver executing on the device
	 */
	public void installProgram(WebDriver driver) {
		String installCmd = "";

		if (!packageName.toLowerCase().contains("qa"))
			return; // Method used only in QA

		if (((AndroidDriver) driver).isAppInstalled(packageName)) {
			System.out.println(
					":::: App with bundleId " + BaseTest1.packageName + " is already present in the device ::::");
			return;
		}

		String udid = Generic.getExecutionDeviceId(driver);

		System.out.println("== Executing postcondition specific to QA environment ==");

		String appPath = Generic.getApkPath();

		installCmd = "adb -s " + udid + " install " + appPath; // -r

		try {
			System.out.println("==== " + packageName + " App not present , Installing app with " + installCmd
					+ " , Please wait ===");
			Runtime.getRuntime().exec(installCmd);
			Generic.wait(30); // On Java 8 use waitFor
			System.out.println("==== App Installed for udid " + udid + " ====");
		} catch (Exception e) {
			System.err.println("Unable to install app ... \n" + e.getMessage());
			e.printStackTrace();
			return;
		}

		// == Grant Permissions after install == //
		System.out.println("=== Re Granting App Runtime Permissions ===");
		udidPermissionsGranted.put(udid, false);
		grantAppRuntimePermissions(driver);

		isAppPresent = true;
	}

	public void clearApp() {
		if (checkEnv("staging"))
			return;

		if (appDataClear) {
			Log.info("======== App already cleared ========");
			return;
		}

		String packageName = Generic.getPropValues("PACKAGENAME", configPath);
		String executionDeviceId = Generic.getExecutionDeviceId(driver);

		Log.info("======== Clearing " + packageName + " from " + executionDeviceId + " ========");

		String adbClearAppCmd = "adb -s " + executionDeviceId + " shell pm clear " + packageName;

		try {
			Runtime.getRuntime().exec(adbClearAppCmd);
		} catch (Exception e) {
			Assert.fail("Unable to clear app " + packageName + "\n" + e.getMessage());
			return;
		}

		appDataClear = true;
	}

	/**
	 * Launches a WebSDK session
	 * 
	 * @return WebDriver with WebSDK session
	 * @see launchAsWebDriver(); to differentiate
	 */
	public WebDriver launchWebDriver() {

		WebDriver driver;
		setWebDriverProperty();
		if (Generic.getPropValues("SDKBROWSER", configPath).contains("Chrome"))
			driver = Generic.createChromeDriver();
		else
			driver = Generic.createFirefoxDriver();

		driver.get(Generic.getPropValues("MERCHANTAPPURL", BaseTest1.configPath));
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return driver;
	}

	/**
	 * Experimental code , uses Advanced Navigation shortcut using Program
	 * Activities
	 * 
	 * @deprecated
	 */
	public void jumpToWallet() // Pay / Send
	{
		if (Generic.isIos(driver))
			return;

		String jumpCmd = "adb -s " + Generic.getUdId(driver) + " shell am start " + BaseTest1.packageName
				+ "/com.enstage.wibmo.wallet.account.WalletActivityV2"; // Read Activity from config file

		try {
			System.out.println("Executing Jump Command : " + jumpCmd);
			Runtime.getRuntime().exec(jumpCmd);
		} catch (Exception e) {
			Log.info("== Unable to execute Home Jump ==" + e.getMessage());
		}

		AddSendPage asp = new AddSendPage(driver);
		asp.handleCoach();
	}

	public static void setAppDataPresent() {
		if (appDataClear)
			appDataClear = false;
	}

	/**
	 * Singleton Class . Returns a ChromeDriverService common to the entire suite
	 *
	 * @param driver executing on the device
	 */
	public static ChromeDriverService chromeService() // Intermittent. HttpHostConnectException . Cannot be used as of
														// Now .
	{
		if (chromeService != null)
			return chromeService;

		chromeService = new ChromeDriverService.Builder() // Concept does not apply for FirefoxDriver
				.usingDriverExecutable(new File(chromeDriverPath)).usingAnyFreePort().build();

		try {
			chromeService.start();
			System.out.println("Chrome Driver Service started on URL : " + chromeService.getUrl());
			Generic.wait(5);
		} catch (Exception e) {
			System.err.println("Unable to Start Chrome Service " + e.getMessage());
		}
		return chromeService;
	}

	public void validateApkPath() {
		if (autVersion.isEmpty())
			return;

		String autPath;

		if (!(autPath = Generic.getApkPath()).contains(autVersion))
			Assert.fail(autVersion + " not found at " + autPath);

		// Write to config path under WelcomePage.getAutVersion()

	}

	/**
	 * Logs in and unlocks the user wallet card. Used mainly as postcondition for
	 * lock card scenarios.
	 * 
	 *
	 * @param driver the driver
	 * @param String loginId
	 * @param String securePin
	 */
	public void unlockUserWalletCard(String loginId, String securePin) {
		if (CSR.unblockLinkedCard(loginId))
			return;
		if (Generic.isIos(driver))
			return; // TBI based on launch App

		switchToApp();
		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);

		verifyLogin(loginId + ',' + securePin);

		HomePage hp = new HomePage(driver);
		hp.goToProgramCard();

		ProgramCardPage pcp = new ProgramCardPage(driver);
		pcp.unlockCard();
	}

	/**
	 * Registers a number with default registration values. and secure pin as 1234
	 * 
	 * 
	 * @param numberCredentials
	 * @return
	 */
	public String registerMobileNumber(String... number) {
		String mobileNo = number.length == 1 ? number[0] : Generic.generateMobileNumber();
		String securePin = number.length == 2 ? number[1] : "1111";

		API.registration(mobileNo);
		if (true)
			return mobileNo;

		String[] defaultRegistrationValues = new String[] { mobileNo, "Wibmo User", "skip", "sampleEmail@xyz.com",
				"12121990", "1", "skip", "IBM", securePin, securePin };

		String defaultRegistrationData = String.join(",", defaultRegistrationValues);

		Log.info("==== Registering " + mobileNo + " with default values as " + defaultRegistrationData + " ====");

		gotoVerifyMobile();

		RegisterMobilePage rmp = new RegisterMobilePage(driver);
		rmp.registerMobile(mobileNo);

		RegisterPage rp = new RegisterPage(driver);
		rp.enterValues(defaultRegistrationData);

		VerifyMobilePage vmp = new VerifyMobilePage(driver);
		vmp.completeRegistration(mobileNo, bankCode);

		RegistrationSuccessfulPage rsp = new RegistrationSuccessfulPage(driver);
		rsp.verifyRegistrationSuccess();
		rsp.gotoLogin();

		return mobileNo;
	}

	/**
	 * Navigates to Verify SIM page from any page with/without login.
	 *
	 * @param driver the driver
	 * @param data   the data
	 */
	public void gotoVerifySIM(String data) {

		if (!checkLoggedIn()) // If not already logged in then Login
		{
			WelcomePage wp = new WelcomePage(driver);
			wp.selectUser(data.split(",")[0]);

			LoginNewPage lnp = new LoginNewPage(driver);
			lnp.login(data.split(",")[1]);

			verifyLogin(data);
		}

		new BasePage(driver).gotoSettings();
		new SettingsPage(driver).gotoVerifySim();

	}

	/**
	 * Performs a full Phone Verification either during Login or from Verify SIM
	 * Page after Login
	 * 
	 * @param data
	 */
	public void verifyPhone(String data) {
		setITP(true);

		gotoVerifySIM(data);

		SimVerificationPage svp = new SimVerificationPage(driver);
		svp.verifySim(data);

	}

	/**
	 * Checks whether the user is currently logged in or not Move to BasePage
	 * 
	 * @return true if the user is Logged In
	 */
	public boolean checkLoggedIn() {
		ArrayList<String> options = new ArrayList<String>(); // Use MobileBy objects when integrating IOS
		driver.findElement(By.name("More options")).click();
		Generic.wait(1);

		List<WebElement> optionList = driver.findElements(By.id("title"));
		for (WebElement o : optionList)
			options.add(o.getText());

		driver.navigate().back();

		return !options.contains("Change User");
	}

	/**
	 * Creates a 3 column file for EKYC Validation
	 * 
	 * @param loginId
	 * @param filepath
	 * @see .pagerepo KYCPage createPANFile()
	 */
	public void create3ColFile(String loginId, String filePath, String url) {
		String pcAcNumber = DB.getPCACNumber(loginId);

		String fileEntry = pcAcNumber + '|' + loginId + '|' + url + '\n';

		Path path = new File(filePath).toPath();

		Log.info("======== Writing " + fileEntry + " to " + path.toString() + " ========");

		try {
			Files.write(path, fileEntry.getBytes(Charset.forName("UTF-8")));
		} catch (Exception e) {
			Assert.fail("Error creating 3 Column file " + e.getMessage());
		}
	}

	/**
	 * Creates an 8 column file for EKYC
	 * 
	 * @param values[] as loginId,relativeFilePath
	 */
	public void create8ColFile(String... values) {
		String loginId = values[0];
		String filePath = values[1];

		int pcAcCol = 0, mobileNumberCol = 1;
		int rowNumber = 1;

		String pcAcNumber = DB.getPCACNumber(loginId);

		Generic.writeCSV(pcAcNumber, rowNumber, pcAcCol, filePath);
		Generic.writeCSV(loginId, rowNumber, mobileNumberCol, filePath);
	}

	/**
	 * Creates a 6 Column file for IKYC
	 * 
	 * @param values
	 */
	public void create6ColFile(String... values) {
		create8ColFile(values);
	}

	/**
	 * 
	 * 
	 * @param sourceRelativeFilePath, destinationAbsoluteDirPath
	 * 
	 */
	public void sftpSend(String sourceRelativeFilePath, String destinationAbsoluteDirPath) {
		String SFTPHOST = Generic.getPropValues("SFTPHOST", BaseTest1.configPath);
		int SFTPPORT = Integer.parseInt(Generic.getPropValues("SFTPPORT", BaseTest1.configPath));
		String SFTPUSER = Generic.getPropValues("SFTPUSER", BaseTest1.configPath);
		String SFTPPASS = Generic.getPropValues("SFTPPASS", BaseTest1.configPath);

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		Log.info("========SFTP : Transferring file  " + sourceRelativeFilePath + "  to " + destinationAbsoluteDirPath
				+ " ========");

		try {
			JSch jsch = new JSch();

			// ==== Set Session Configuration ==== //
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			System.out.println("Host connected."); // to Log.infos

			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("sftp channel opened and connected.");
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(destinationAbsoluteDirPath);
			File f = new File(sourceRelativeFilePath);
			channelSftp.put(new FileInputStream(f), f.getName());
			Log.info("======== File transferred ========");

		} catch (Exception ex) {
			Assert.fail("File Transfer failed : " + ex.getMessage());
		} finally {

			channelSftp.exit();
			System.out.println("sftp Channel exited.");
			channel.disconnect();
			System.out.println("Channel disconnected.");
			session.disconnect();
			System.out.println("Host Session disconnected.");
		}

		Generic.wait(2); // Network latency FTP
	}

	/**
	 * Verifies SMS content from Notifications and switches back to AUT. To prevent
	 * Activity Clash
	 * 
	 * @param notificationTxt
	 * @param smsContent
	 * @param waitTimeForNotification
	 */
	public void verifySMSContentFromNotifications(String notificationTxt, String smsContent,int waitTimeForNotification) 
	{
		if (!deviceRegression)
			return;

		try {
			Generic.verifySMSContentFromNotifications(driver, notificationTxt, smsContent, waitTimeForNotification);
		} catch (Exception e) {
			Assert.fail("Unable to verify SMS Content based on " + notificationTxt + '\n' + e.getMessage());
		} finally {
			System.out.println("Reverting to App ");
			switchToAppWithState();
		}

	}

	public String parsePromoCode(String data) {
		if (!data.contains("PC_"))
			Assert.fail("Promo Code not found in TestData : " + data);

		return data.split("PC_")[1].split(",")[0];
	}

	/**
	 * Logs out from any page
	 */
	public void logout() {
		Log.info("======== Logging Out ========");
		new BasePage(driver).logOut();
	}

}
