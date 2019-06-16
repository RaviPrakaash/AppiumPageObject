package library;

import java.io.File;
//import java.awt.event.KeyEvent;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.UUID;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.HashMap;
import javax.swing.event.SwingPropertyChangeSupport;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.WordUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.safari.SafariDriver;
//import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.support.GenericTypeAwareAutowireCandidateResolver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.paulhammant.ngwebdriver.NgWebDriver;
import edu.emory.mathcs.backport.java.util.Arrays;
//import com.thoughtworks.selenium.webdriven.commands.KeyEvent;
import library.Log;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
//import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import wibmo.app.pagerepo.AddSendPage;
import wibmo.app.pagerepo.ForgotPinStep1Page;
import wibmo.app.pagerepo.ForgotPinStep2Page;
import wibmo.app.pagerepo.ForgotPinStep3Page;
import wibmo.app.pagerepo.HomePage;
import wibmo.app.pagerepo.LoginNewPage;
import wibmo.app.pagerepo.MerchantHomePage;
import wibmo.app.pagerepo.MerchantSettingsPage;
import wibmo.app.pagerepo.ProgramCardPage;
import wibmo.app.pagerepo.VerifyDevicePage;
import wibmo.app.pagerepo.VerifyPhonePage;
import wibmo.app.pagerepo.WelcomePage;
import wibmo.app.testScripts.BaseTest1;
import wibmo.app.testScripts.SendMoney.BaseTest;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.time.Duration;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.ByteArrayOutputStream;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;

/**
 * The Class Generic.
 */
public class Generic {

	/** The soft assert. */
	SoftAssert softAssert;
	public static boolean multipleDevices;
	private String executionDeviceId = "";

	/**
	 * Check environment between qa and staging
	 *
	 * @param environment the environment
	 * @return true, if successful
	 */
	public static boolean checkEnv(String environment) {
		return BaseTest1.env.contains(environment);
	}

	/**
	 * Hides the Keyboard if the Keyboard is displayed.
	 *
	 * @param driver     the driver
	 * @param WebElement textField
	 * @param String     text to be entered
	 */
	public static void sendKeys(WebDriver driver, WebElement textField, String text) {
		int i = (Integer) ((AndroidDriver) driver).executeScript(
				"try{var el = document.arguments[0];el.value = 'arguments[1]';return 0;}catch{return 1;}", textField,
				text);

		if (i == 1)
			System.out.println("Unable to sendKeys");
	}

	/**
	 * Use Generic.hideKeyboard() instead
	 * 
	 * @param driver
	 */
	public static void iosKey_Go(WebDriver driver) {
		IOSDriver iDriver = (IOSDriver) driver;
		Log.info("======== Clicking on Go Key ========");
		iDriver.findElementByAccessibilityId("Go").click();
	}

	/**
	 * Hides the Keyboard if the Keyboard is displayed.
	 *
	 * @param driver the driver
	 */
	public static void hideKeyBoard(WebDriver driver) {
		if (checkWebView(driver))
			return;
		try {
			if (isIos(driver)) // IOS
			{
				List<WebElement> keyBoardChk = driver.findElements(MobileBy.iOSNsPredicateString(
						"type='XCUIElementTypeNavigationBar' or (type='XCUIElementTypeButton' && name IN {'Done','Next','Return'})"));
				for (WebElement e : keyBoardChk)
					if (e.getAttribute("type").contains("Button")) {
						driver.findElement(MobileBy.iOSNsPredicateString("name IN {'Done','Next','Return'}")).click();
						break;
					}
			} else // Android
				((AndroidDriver) driver).hideKeyboard();
		} catch (Exception e) {
			com.libraries.Log.info("== Hide Keyboard Delay ==");
		}

	}

	/**
	 * Ignores the alert popup by clicking on the Cancel.
	 *
	 * @param driver the driver
	 */
	public static void ignorePopup(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		try {
			driver.findElement(By.name("Cancel")).click();
		} catch (Exception e) {
		}
	}

	/**
	 * Accepts the alert popup by clicking on the Cancel. Used to handle permissions
	 * alert popup
	 *
	 * @param driver the driver
	 */
	public static void allowPopup(WebDriver driver) // D
	{
		try {
			driver.findElement(By.id("permission_allow_button")).click(); // Click Allow Button
		} catch (Exception e) {
		}
	}

	/**
	 * Scrolls to the given text. Cannot be used for ios
	 *
	 * @param driver the driver
	 * @param text   the text
	 * @return the web element
	 */
	public static WebElement scroll(WebDriver driver, String text) {
		if (isIos(driver))
			return null;

		String automatorString = "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textContains(\""
				+ text + "\"));";
		return ((AndroidDriver) driver).findElementByAndroidUIAutomator(automatorString);
	}

	/**
	 * 
	 * 
	 * @param driver
	 * @param text
	 * @deprecated Under development. Auto Clicks on Random elements in IOS
	 */
	public static void scrollClick(WebDriver driver, String text) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap scrollObject = new HashMap<>();
		scrollObject.put("predicateString", "value == '" + text + "'"); // or name ==' '
		js.executeScript("mobile: scroll", scrollObject);
	}

	/**
	 * Scrolls to the given text, case insensitive.(Under development)
	 *
	 * @param driver the driver
	 * @param text   the text
	 * @return the web element
	 */
	public static WebElement scroll1(WebDriver driver, String text) {
		text = regInsensitive(text);
		String automatorString1 = "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().textMatches(\""
				+ text + "\"));";
		System.out.println("automatorString1 : " + automatorString1);

		// return
		// ((AndroidDriver)driver).findElement(MobileBy.AndroidUIAutomator(automatorString));
		return ((AndroidDriver) driver).findElementByAndroidUIAutomator(automatorString1);
	}

	/**
	 * Scrolls to the end of the page.
	 *
	 * @param driver the driver
	 */
	public static void scrollToEnd(WebDriver driver) {
		String automatorString = "new UiScrollable(new UiSelector()).scrollToEnd(2);";
		((AndroidDriver) driver).findElementByAndroidUIAutomator(automatorString);
	}

	/**
	 * Scrolls to the given text.
	 *
	 * @param driver the driver
	 * @return void
	 * 
	 */
	public static void scrollToText(WebDriver driver, String text) {
		String automatorString = "new UiScrollable(new UiSelector()).scrollTextIntoView(\"Charge On Status Check\");";
		System.out.println(automatorString);
		((AndroidDriver) driver).findElementByAndroidUIAutomator(automatorString);
		MobileElement e = (MobileElement) ((AndroidDriver) driver).findElementByAndroidUIAutomator(automatorString);
	}

	/**
	 * Swipes from right to left. Mainly used in the Select Card Page. <---------||
	 * 
	 * @param driver the driver
	 */
	public static void swipeLeft(WebDriver driver) {
		System.out.println("=Performing New Swipe =");

		AppiumDriver adriver = (AppiumDriver) driver;
		TouchAction touchAction = new TouchAction(adriver);
		Dimension size = adriver.manage().window().getSize();
		int startx = (int) (size.width * 0.8);
		int endx = (int) (size.width * 0.15);
		int starty = size.height / 3; 
		
		

		try {
			if (isIos(driver))
				touchAction.press(PointOption.point(startx, starty)).moveTo(PointOption.point(endx - startx, 0))
						.release().perform(); // Relative final Co-ordinates
			else
				touchAction.press(PointOption.point(startx, starty))
						.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
						.moveTo(PointOption.point(10, starty)).release().perform(); // Absolute final Co-ordinates
		} catch (Exception e) {
			System.err.println("Scroll error ");
			e.printStackTrace();
		}

	}

	/**
	 * Simulates a tap on the element
	 *
	 * @param driver  the driver
	 * @param element
	 */
	public static void tap(WebDriver driver, WebElement element) {
		
		if (isAndroid(driver)) {
			
			AndroidDriver<AndroidElement> aDriver = (AndroidDriver<AndroidElement>) driver;
			TouchAction touchAction = new TouchAction(aDriver);
			
			AndroidElement aElement=(AndroidElement)element;
			int x = aElement.getCenter().x, y = aElement.getCenter().y;
			
			System.out.println("Executing tap on " + x + ',' + y);
			
			touchAction.press(PointOption.point(x, y)).release().perform(); 
			
			
		} else {
			IOSDriver<IOSElement> iDriver = (IOSDriver<IOSElement>) driver;
			TouchAction touchAction = new TouchAction(iDriver);

			IOSElement iElement = (IOSElement) element;
			int x = iElement.getCenter().x, y = iElement.getCenter().y;

			System.out.println("Executing tap on " + x + ',' + y);
			touchAction.press(PointOption.point(x, y)).release().perform(); // May Upgrade , if 'visible' attribute is
																			// true then syso and print "Clicking
																			// directly" under log
		}
	}

	/** TBI */
	public static void tap(WebDriver driver, int x, int y) {

	}

	/**
	 * Swipes from left to right. Mainly used to open Navigation Pane. ||--------->
	 * 
	 * @param driver the driver
	 */
	public static void swipeRight(WebDriver driver) {
		AppiumDriver adriver = (AppiumDriver) driver;
		TouchAction touchAction = new TouchAction(adriver);

		Dimension size = adriver.manage().window().getSize();
		int startx = 6;// (int) (size.width * 0.15);
		int endx = (int) (size.width * 0.8);
		int starty = size.height / 2; // or use /3

		touchAction.press(PointOption.point(startx, starty)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(PointOption.point(endx - startx, 0)).release().perform(); // Absolute final Co-ordinates

	}

	/**
	 * Moves to bottom of the page by swiping to top.
	 *
	 * @param driver the driver
	 */
	public static void swipeToBottom(WebDriver driver) {
		
		AppiumDriver aDriver = (AppiumDriver) driver;
		TouchAction touchAction = new TouchAction(aDriver);

		Dimension size = aDriver.manage().window().getSize();
		int startx = (int) (size.width / 2);
		int endy = (int) (size.height * 0.2);
		int starty = (int) (size.height * 0.8);
	//	endy = 10;
		
		try {
			
			if (isIos(driver)) // IOS
				touchAction.press(PointOption.point(startx, starty)).moveTo(PointOption.point(startx, endy - starty))
						.release().perform(); 		// Relative final Co-ordinates for IOS
			else // Android
				touchAction.press(PointOption.point(startx, starty))
						.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
						.moveTo(PointOption.point(startx, endy)).release().perform(); 	// Absolute final Co-ordinates 
																						
		} catch (Exception e) {
			System.err.println("Scroll error ");
			e.printStackTrace();
		}
	}

	/**
	 * Swipes to top of the page by swiping down.
	 *
	 * @param driver the driver
	 * @see swipeLeft() swipeRight() swipeToBottom()
	 * 
	 */
	public static void swipeUp(WebDriver driver) {
		AppiumDriver adriver = (AppiumDriver) driver;
		TouchAction touchAction = new TouchAction(adriver);

		Dimension size = adriver.manage().window().getSize();
		int startx = (int) (size.width / 2);
		int starty = (int) (size.height * 0.20);
		int endy = (int) (size.height * 0.80);

		// touchAction.press(startx, starty).moveTo(0, endy-starty).release().perform();
		// // move To Added values to co-ordinates

		try {
			if (isIos(driver))
				touchAction.press(PointOption.point(startx, starty)).moveTo(PointOption.point(startx, endy - starty))
						.release().perform(); // Relative final Co-ordinates for IOS
			else
				touchAction.press(PointOption.point(startx, starty))
						.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
						.moveTo(PointOption.point(startx, endy)).release().perform(); // Absolute final Co-ordinates for
																						// Android
		} catch (Exception e) {
			System.err.println("Scroll error ");
			e.printStackTrace();
		}

	}

	public static void swipeUpToText(String text) // TBI
	{

	}

	/**
	 * Simulates a generic tap on the screen when the page is not recognized by
	 * autowebview capability.
	 *
	 * @param driver the driver
	 */
	public static void tap3DS(WebDriver driver) {
		if (Generic.isIos(driver))
			return;
		// Log.info("======== Trying to identify 3DS page ========");
		((AndroidDriver) driver).pressKeyCode(61); // KEYCODE_TAB
	}

	/**
	 * Simulates a double tap on the screen since doubleTap() is not implemented in
	 * AppiumDriver
	 *
	 * @param driver the driver
	 */
	public static void doubleTapEnter(WebDriver driver) {
		((AndroidDriver) driver).pressKeyCode(61); // KEYCODE_TAB
		wait(1);
		((AndroidDriver) driver).pressKeyCode(61); // KEYCODE_TAB
		wait(1);
		((AndroidDriver) driver).pressKeyCode(66); // KEYCODE_ENTER
	}

	/**
	 * Double taps on the given element
	 *
	 * @param driver  the driver
	 * @param element
	 * 
	 */
	public static void doubleTap(WebDriver driver, WebElement element) {
		AppiumDriver adriver = Generic.isAndroid(driver) ? (AndroidDriver) driver : (IOSDriver) driver;
		TouchAction touchAction = new TouchAction(adriver);

		// int locX=element.getLocation().x,locY=element.getLocation().y;

		Log.info("======== Performing Double Tap ========");
		try {
			touchAction.tap(tapOptions().withElement(element(element)).withTapsCount(2)).perform();

			// touchAction.tap(element).perform().waitAction(Duration.ofMillis(100)).tap(element).perform();
			// touchAction.press(locX,locY).release().perform().waitAction(Duration.ofMillis(100)).press(locX,locY).release().perform();
		} catch (Exception e) {
			// Log.info("== Unable to Perform double tap == ");
		}
	}

	/**
	 * 
	 * Double taps on the given co-ordinates
	 * 
	 * @param driver
	 * @param x      co ordinate
	 * @param y      co ordinate
	 * @see doubleTap(WebDriver driver,WebElement element)
	 * 
	 */
	public static void doubleTap(WebDriver driver, int x, int y) {
	}

	/**
	 * Navigates back from the page. Android only.
	 * 
	 * @param driver
	 */
	public static void navigateBack(WebDriver driver) {
		if (isIos(driver))
			return;
		System.out.println("======== Navigating Back ========");
		driver.navigate().back();
	}

	/**
	 * Navigates to the Welcome page if the default landing Page is Login Page.
	 *
	 * @param driver the driver
	 */
	public static void gotoWelcome(WebDriver driver) {
		String xp = "//android.widget.TextView[contains(@text,'Logging') or contains(@text,'Register')]";

		WebElement homeCheck = driver.findElement(By.xpath(xp));

		if (homeCheck.getText().contains("Logging"))
			driver.findElement(By.id("home_button_login")).click();
	}

	/**
	 * Verifies whether the user is logged in or not. Handles all the Verification
	 * pages occurring between Login Page and Home Page including Unclaimed. Handles
	 * all popups by calling the parallel thread HandleHomePopup.
	 *
	 * @param driver the driver
	 * @param data   the data
	 * @see merchantVerifiedLogin()
	 * @deprecated Use BaseTest1 verifyLogin() instead
	 */
	public static void verifyLogin(WebDriver driver, String data) {
		String homeIdentifier = "PayZapp Home";
		String mobileNo = data.split(",")[0];
		String checker = Generic.waitUntilTextInPage(driver, 60, "Request timed", "Verify Phone", "Verify Device",
				"got_it", "support!", "PayZapp Home"); // Integrate OK

		// ======== Request timed out error ======== //
		if (checker.contains("Request")) {
			new LoginNewPage(driver).handleRequestTimeOut(data);
			checker = Generic.waitUntilTextInPage(driver, 60, "Verify Phone", "Verify Device", "Unclaimed", "got_it",
					"support!", homeIdentifier); // Integrate OK
		}
		// =================== //

		if (checker.contains("Verify Phone")) {
			Log.info("======== Going through " + checker + " page ========");

			if (BaseTest1.getITP())
				new VerifyPhonePage(driver).loginWithPersonalDeviceWithSkip(data); // data added
			else
				new VerifyPhonePage(driver).loginWithoutPersonalDevice();// loginWithPersonalDeviceWithSkip();

			checker = Generic.waitUntilTextInPage(driver, 60, "Verify Device", "Unclaimed", "got_it", "support!",
					homeIdentifier); // Integrate OK

		}

		if (checker.contains("Device")) {
			new VerifyDevicePage(driver).loginWithTrustedVerifyDevice(data.split(",")[0], BaseTest1.bankCode);

			checker = Generic.waitUntilTextInPage(driver, 60, "Unclaimed", "got_it", "support!", homeIdentifier);
		}

		if (checker.contains("Unclaimed")) {
			if (BaseTest.checkUnclaimed)
				return; // BaseTest of SendMoney module - Do not handle Unclaimed page if executing
						// Unclaimed scenario

			Log.info("======== Handling Unclaimed ========");
			navigateBack(driver);
			wait(2);
			checker = Generic.waitUntilTextInPage(driver, 60, "got_it", "support!", homeIdentifier);
		}

		if (checker.contains("got_it")) {
			Log.info("==== Handling Home Coach ====");
			navigateBack(driver);
			checker = Generic.waitUntilTextInPage(driver, 60, "support!", homeIdentifier);
		}

		if (checker.contains("support!")) {
			Log.info("==== Handling Rate App Alert ====");
			driver.findElement(By.xpath("//*[contains(@resource-id,'button2')]")).click();
		}

		if (checker.contains(homeIdentifier)) {
			Generic.wait(1);
			checker = Generic.waitUntilTextInPage(driver, 60, "got_it", "support!", homeIdentifier);
			if (checker.contains("got_it") || checker.contains("support!")) {
				Log.info("Warning : Navigating back to handle " + checker);
				navigateBack(driver);
			}
		}
	}

	/**
	 * Waits until any of the text in String... is displayed in the page
	 * 
	 */
	public static String waitUntilTextInPage(WebDriver driver, int timeoutSeconds, String... args) {
		String textFound = "notFound";

		for (int i = 0; i < timeoutSeconds; i++) {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
			}
			if (!(textFound = checkTextInPageSource(driver, args)).equals("notFound"))
				return textFound;
		}

		Assert.fail("Page taking toomuch time to load while waiting for " + Arrays.asList(args));
		return textFound;
	}

	/**
	 * Handles the Coach messages occuring in the Home Page The coach messages may
	 * appear after the Home Page is validated.
	 * 
	 * 
	 * @param driver the driver
	 * @deprecated Coach Message in HomePage handled from BaseTest1 verifyLogin
	 *             method
	 */
	public static void handleHomeCoach(WebDriver driver) {
		try {
			String gotItXp = "//*[contains(@text,'Got It') or contains(@resource-id,'title_text') or contains(@resource-id,'got_it')]"; // Exclude
																																		// later
																																		// on
																																		// Observation
			WebElement coachElement = driver.findElement(By.xpath(gotItXp));

			if (coachElement.getText().contains("Proceed")) {
				Log.info("== Handling Proceed Coach ==");
				driver.navigate().back();
			} else
				driver.findElement(By.xpath(gotItXp)).click(); // dont use coachElement
		} catch (Exception e) {
			Log.info("== Home Coach delay ==" + e.getMessage());
		}

	}

	/**
	 * Logs in and Navigates to the Add/Send page and returns the balance displayed.
	 *
	 * @param driver the driver
	 * @param data   the data
	 * @return the double
	 */
	public static double checkWalletBalance(WebDriver driver, String data) {
		gotoAddSend(driver, data);

		AddSendPage asp = new AddSendPage(driver);
		return asp.verifyBalance();
	}

	/**
	 * Logs in and navigates to the Add/Send page.
	 *
	 * @param driver the driver
	 * @param data   the data
	 */
	public static void gotoAddSend(WebDriver driver, String data) {
		int i = 0;
		String[] values = data.split(",");
		String loginId = values[i++];
		String securePin = values[i++];

		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);

		verifyLogin(driver, data);

		HomePage hp = new HomePage(driver);
		hp.addSend();
	}

	/**
	 * Changes the user to the given LoginId but does not log in.
	 *
	 * @param driver the driver
	 * @param data   the data
	 */
	public static void userNotLoggedInToApp(WebDriver driver, String data) {
		String loginId = data.split(",")[0];
		Log.info("======== User with loginId " + loginId + " will not log in to the App ========");
		WelcomePage wp = new WelcomePage(driver);
		wp.selectUser(loginId);
	}

	/**
	 * Launches and switches to the Merchant app. MerchantPackage and
	 * MerchantActivity is specified in the config.properties file. *
	 *
	 * @param driver the driver
	 * @see Use switchToMerchant() under BaseTest1
	 * 
	 */
	public static void switchToMerchant(WebDriver driver) {
		Log.info("======== Opening merchant app ========");
		((AndroidDriver) driver).startActivity(new Activity(getPropValues("MERCHANTPACKAGE", BaseTest1.configPath),
				getPropValues("MERCHANTACTIVITY", BaseTest1.configPath)));
		
				
	}

	/**
	 * Launches / Relaunches the main AUT.Used mainly in BaseClass
	 *
	 *
	 * @param driver the driver
	 * @see BaseTest1 switchToApp() for usage in TestClasses
	 */
	public static void switchToApp(WebDriver driver) {
		if (isAndroid(driver)) {
			Activity appActivity = new Activity(BaseTest1.packageName, BaseTest1.appActivity);
			System.out.println("==== Opening AUT ====");
			((AndroidDriver) driver).startActivity(appActivity);
		}
		if (isIos(driver)) {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("bundleId", Generic.getPropValues("BUNDLEID", BaseTest1.configPath));

			System.out.println("=== Restarting IOS App with " + params + " ===");
			((IOSDriver) driver).executeScript("mobile: terminateApp", params);
			Generic.wait(3);
			((IOSDriver) driver).executeScript("mobile: launchApp", params);
		}

	}

	/**
	 * Switches to the AUT which was previously launched by using Android Keycodes
	 * 
	 * Can be updated to use monkeyrunner if screens are consistent
	 *
	 * 
	 * @param driver the driver
	 * @see Basetest1.switchToAppWithState(driver)
	 * 
	 */
	public static void switchToAppWithState(WebDriver driver) 
	{
		// ((AndroidDriver) driver).startActivity(BaseTest1.packageName,activity);

		Log.info("======== Switching to app with previous state =======");
		String appName = BaseTest1.programName;
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
	 * Switches to the Merchant App which was previously launched by using Android
	 * Keycode KEYCODE_APP_SWITCH.
	 *
	 * @param driver the driver
	 */
	public static void switchToMerchantWithState(WebDriver driver) {
		Log.info("======== Switching to merchant app with previous state =======");
		String xp = "//*[contains(@text,'Wibmo')]/..";
		String checkerXp = "//android.widget.TextView[contains(@text,'Wibmo') or contains(@resource-id,'title_text') or contains(@resource-id,'approve_textbutton') or contains(@text,'Authentication')]";

		try {
			((AndroidDriver) driver).pressKeyCode(187); // KEYCODE_APP_SWITCH
			Generic.wait(2);
			driver.findElement(By.xpath(xp)).click();
			Generic.wait(2);

			String title = driver.findElement(By.xpath(checkerXp)).getText();
			if (title.contains("Add")) // Reswitch if not switched to Merchant App (''Wibmo' or 'Secure Pin' prompt)
			// if(!getFocusedAppPkg(driver).contains(getPropValues("MERCHANTPACKAGE",
			// BaseTest1.configPath)))
			{
				Log.info("==== Attempting reswitch ====");
				Runtime.getRuntime().exec("adb -s " + getUdId(driver) + " shell input keyevent KEYCODE_APP_SWITCH");
				Generic.wait(2);
				driver.findElement(By.xpath(xp)).click();
			} else
				Log.info("======== Successfully switched to Merchant App ========");

		} catch (Exception e) {
			Log.info("Error during App Switch\n" + e.getMessage());
		}
	}

	/**
	 * Verifies whether the current page is Pay / Send page
	 * 
	 *
	 * @param driver the driver
	 * @return true if current page is Pay / Send
	 */
	public static boolean checkAddSend(WebDriver driver) {
		String checkerXp = "//android.widget.TextView[contains(@resource-id,'title_text') or contains(@text,'SDK')]";

		// adb shortcut can be attempted with try{}
		try {
			return driver.findElement(By.xpath(checkerXp)).getText().contains("Pay");
		} catch (Exception e) {
			Log.info("==== Error in determining Pay Send Page ====");
			return false;
		}
	}

	/**
	 * Checks whether the card with given cardDetails will go through ITP flow or
	 * not
	 * 
	 *
	 * @param driver the driver
	 * @return true if ITP flow
	 */
	public static boolean checkWalletITPDirect(String cardDetails) {
		return containsIgnoreCase(cardDetails, BaseTest1.programName) && BaseTest1.walletITPDirect();
	}

	/**
	 * Uninstalls the app with the given package name.
	 *
	 * @param driver      the driver
	 * @param packageName the package name
	 */
	public static void unInstallApp(WebDriver driver, String packageName) {
		if (((AndroidDriver) driver).isAppInstalled(packageName)) {
			Log.info("======== Uninstalling App with package name : " + packageName + " ========");
			try {
				((AndroidDriver) driver).removeApp(packageName);
				Log.info("======== App uninstalled ========");
			} catch (Exception e) {
				Assert.fail("App with package name : " + packageName + " was not uninstalled\n" + e.getMessage());
			}
		} else
			Log.info("======== App with package " + packageName + " already uninstalled ========");
	}

	/**
	 * Installs the app with the given package name. The corresponding apk file
	 * should be placed under the 'apk' folder.
	 *
	 * @param driver      the driver
	 * @param packageName the package name
	 */
	public static void installProgram(WebDriver driver, String packageName) {
		if (((AndroidDriver) driver).isAppInstalled(packageName)) {
			Log.info("======== App already installed ========");
			return;
		}
		String bankFolder = packageName.split("\\.")[packageName.split("\\.").length - 1];
		File dir = new File("./apk/" + bankFolder);
		FileFilter fileFilter = new WildcardFileFilter("*.apk");
		File[] files = dir.listFiles(fileFilter);
		String appPath = files[0].getAbsolutePath();
		String apkName = getPropValues("HDFC_APK", BaseTest1.configPath);
		try {
			Log.info("======== Installing app from the path " + appPath + " ========");
			((AndroidDriver) driver).installApp(appPath);
			Log.info("======== App for " + bankFolder.toUpperCase() + " installed ========");
		} catch (Exception e) {
			Assert.fail("App for " + bankFolder.toUpperCase() + " not installed \n" + e.getMessage());
		}
	}

	/**
	 * Installs the AUT app.
	 *
	 * @param driver      the driver
	 * @param packageName the package name
	 */
	public static void installProgram(WebDriver driver) {
		if (((AndroidDriver) driver).isAppInstalled(BaseTest1.packageName))
			return;

		String appPath = getApkPath();

		String installCmd = "adb -s " + getExecutionDeviceId(driver) + " install " + appPath;
		try {
			// if(!Runtime.getRuntime().exec(installCmd).waitFor(2,TimeUnit.MINUTES))
			// Assert.fail("App Upgrade timeout");
		} catch (Exception e) {
			// Assert.fail("Unable to install app within the specified time
			// \n"+e.getMessage());
		}

		wait(5);
	}

	// To be used by Baseclass using adb commands only and static boolean
	// isAppInstalled & before the driver is launched
	public void installProgram() {
		// if(packageName does not contain QA) return;

	}

	public void uninstallProgram() {

	}

	/**
	 * Returns the file path of the AUT .apk file placed under the respective
	 * environment folder Only one .apk file should be placed under the given folder
	 *
	 * @param driver      the driver
	 * @param packageName the package name
	 */
	public static String getApkPath() {
		File dir = new File(getPropValues("APKPATH", BaseTest1.configPath)); // Get the apk inside this folder
		FileFilter fileFilter = new WildcardFileFilter("*.apk");
		File[] files = dir.listFiles(fileFilter);

		return files[0].getPath().replace('\\', '/'); // getPath
	}

	// Currently not used Install/Uninstall between versions causes unpredictable
	// behaviour.
	public static String getPrevApkPath() {
		File dir = new File(getPropValues("PREVAPKPATH", BaseTest1.configPath)); // Install the apk inside this folder
		FileFilter fileFilter = new WildcardFileFilter("*.apk");
		File[] files = dir.listFiles(fileFilter);

		return files[0].getAbsolutePath().replace('\\', '/');
	}

	/**
	 * Sets the current execution device Id after reading it from the driver
	 * capabilities
	 * 
	 *
	 * @param driver the driver
	 * 
	 */
	public void setExecutionDeviceId(WebDriver driver) {
		if (executionDeviceId.isEmpty()) {
			try {
				executionDeviceId = (String) ((AndroidDriver) driver).getCapabilities().getCapability("deviceName");
			} catch (Exception e) {
				System.out.println("=== Obtaining deviceId from adb ===");
				String deviceCmd = "adb devices";
				try {
					executionDeviceId = execCmd(deviceCmd).split("\\n")[1].split("\\s+")[0];
				} catch (Exception e1) {
					System.out.println("Warning : unable to execute cmd : " + deviceCmd);
				}
			}
		}
	}

	/**
	 * Gets the current execution device Id from driver capabilities.
	 * 
	 *
	 * @param driver the driver
	 * 
	 */
	public static String getExecutionDeviceId(WebDriver driver) {

		if (isAndroid(driver))
			return (String) ((AndroidDriver) driver).getCapabilities().getCapability("udid");

		if (isIos(driver))
			return (String) ((IOSDriver) driver).getCapabilities().getCapability("udid");

		System.err.println("Warning : Udid not present for the driver : " + driver.toString());
		return "--";

	}

	public static String getUdId(WebDriver driver) {
		return getExecutionDeviceId(driver);
	}

	public static String getPackageName(WebDriver driver) 
	{
		if(!isAndroid(driver)) return "WebDriverPackage";
		
		// String pkgName=((AndroidDriver<WebElement>)driver).getCurrentPackage(); Retrieving Session Details on session id is unstable		
		
		return parseDataVal(driver, driver.toString(), "appPackage=");	

	}

	public static String getAttribute(WebElement element, String attribute) {

		if (element.toString().toLowerCase().contains("ios") && attribute.toLowerCase().contains("content"))
			return "attributeNotFound";

		String attributeValue = element.getAttribute(attribute);
		System.out.println("Retrieving " + attribute + " value  as " + attributeValue + " for " + element.toString());

		return attributeValue == null ? "" : attributeValue;
	}

	public static String getText(WebDriver driver, WebElement element) {
		return element.getText();
	}

	/**
	 * Uninstalls AUT Installs the previous version of the App from apk/env/prev
	 * folder
	 * 
	 * @param driver
	 */
	public static void downgradeApp(WebDriver driver) {
		String packageName = BaseTest1.packageName;
		String bankFolder = packageName.split("\\.")[packageName.split("\\.").length - 1];

		/*
		 * //=== 1. Take Backup ===// bkpApp(driver,packageName);
		 */

		String prePath = getPrevApkPath();

		// === 1. Uninstall AUT === //
		Log.info("======== Uninstalling latest App : " + packageName + "========");
		String uninstallCmd = "adb -s " + getExecutionDeviceId(driver) + " shell pm uninstall " + packageName;
		try {
			// if(!Runtime.getRuntime().exec(uninstallCmd).waitFor(2,TimeUnit.MINUTES))
			// Log.info("Warning : App Uninstall Timeout");

			((AndroidDriver) driver).removeApp(packageName);

		} catch (Exception e) {
			Assert.fail("Unable to uninstall app :" + packageName + '\n' + e.getMessage());
		}

		Generic.wait(10);
		// === 2.Install previous version from apk/env/prev === //
		try {
			Log.info("======== Installing previous version app from the path " + prePath + " ========");
			String cmd = "adb -s " + getExecutionDeviceId(driver) + " install " + prePath;
			try {
				((AndroidDriver) driver).installApp(prePath);
				// Runtime.getRuntime().exec(cmd);//.waitFor();

				// if(!Runtime.getRuntime().exec(cmd).waitFor(2,TimeUnit.MINUTES))
				// Log.info("Warning : App Install Timeout");
			} catch (Exception e) {
				Assert.fail("Unable to install downgraded app from " + prePath + '\n' + e.getMessage());
				e.printStackTrace();
			}

			Log.info("======== Successfully downgraded ========");
		} catch (Exception e) {
			Assert.fail("App for " + bankFolder + " not installed from " + prePath + "\n" + e.getMessage());
		}
		wait(10);
	}

	public static void uninstallAppPreserveData(WebDriver driver, String packageName) {
		Log.info("======== Uninstalling  App : " + packageName + "========");
		String uninstallCmd = "adb -s " + getExecutionDeviceId(driver) + " shell pm uninstall -k " + packageName;
		try {
			if (!Runtime.getRuntime().exec(uninstallCmd).waitFor(2, TimeUnit.MINUTES))
				Assert.fail("App Uninstall Timeout");
		} catch (Exception e) {
			Log.info("======== Force removing App ========");
			try {
				((AndroidDriver) driver).removeApp(packageName);
			} catch (Exception e1) {
				Assert.fail("Unable to uninstall app :" + packageName + '\n' + e1.getMessage());
			}
		}
		Log.info("======== App successfully uninstalled ========");
	}

	/**
	 * Upgrades on the existing App by using adb commands
	 * 
	 * @param driver
	 * @param ver
	 */
	public static void upgradeApp(WebDriver driver, String ver) {
		String packageName = BaseTest1.packageName;
		String bankFolder = packageName.split("\\.")[packageName.split("\\.").length - 1];

		String appPath = getApkPath();

		Log.info("======== Upgrading App to latest version " + ver + " from " + appPath + "========");
		String upgradeCmd = "adb -s " + getExecutionDeviceId(driver) + " install -r " + appPath;
		try {
			if (!Runtime.getRuntime().exec(upgradeCmd).waitFor(2, TimeUnit.MINUTES))
				Assert.fail("App Upgrade timeout");
		} catch (Exception e) {
			Assert.fail("Unable to install app within the specified time :" + packageName + '\n' + e.getMessage());
		}

		wait(5);
	}

	public static void bkpApp(WebDriver driver, String packageName) {
		String cmd = "adb -s " + getExecutionDeviceId(driver) + " shell pm list packages -f -3", pkgOutput = "",
				pkgPath = "";
		Log.info("======== Creating App Backup ========");

		// === 1.Find app path in Phone === //
		try {
			pkgOutput = execCmd(cmd);
			String[] packages = pkgOutput.split("\n");
			for (String p : packages) {
				if (p.contains(packageName)) {
					pkgPath = p.split(":")[1].split("=")[0];
					Log.info("======== Package Path in Phone :" + pkgPath + " ========");
					break;
				}
			}
		} catch (Exception e) {
			Log.info("Error in retrieving package \n" + e.getMessage());
			return;
		}

		String bankFolder = packageName.split("\\.")[packageName.split("\\.").length - 1];

		// === 2. Backup from phone path to apk/env === //
		Log.info("======== Backing up from phone app path " + pkgPath + " as 1.apk ========");
		String bkpCmd = "adb -s " + getExecutionDeviceId(driver) + " pull " + pkgPath + " "
				+ getPropValues("APKPATH", BaseTest1.configPath) + "/1.apk";
		System.out.println("Backup Command : " + bkpCmd);
		try {
			if (!Runtime.getRuntime().exec(bkpCmd).waitFor(2, TimeUnit.MINUTES))
				Log.info("Unable to backup - Timeout");
			else
				Log.info("======== Backup created successfully ========");
		} catch (Exception e) {
			Log.info("Unable to backup\n" + e.getMessage());
			e.printStackTrace();
		}
		wait(5);
	}

	/**
	 * Closes the App display by pressing the Home button
	 * 
	 * @param driver
	 */
	public static void closeApp(WebDriver driver) {
		Log.info("======== Minimising App ========");
		((AndroidDriver) driver).pressKeyCode(3); // KEYCODE_HOME
		wait(2);
	}

	/**
	 * Handles 'prepaid balance deducted' popup or 'rate app' occurring while
	 * logging in.
	 *
	 * @param driver the driver
	 */
	public static void handlePrepaidOrRateApp(WebDriver driver) {
		String xp = "//android.widget.Button[contains(@text,'OK') or contains(@text,'No thanks')]";

		driver.findElement(By.xpath(xp)).click();

		// On rare possibility, Rateapp popup might still be present

		xp = "//android.widget.TextView[contains(@resource-id,'title_text')] | //android.widget.Button[contains(@text,'No thanks')]";
		if (driver.findElement(By.xpath(xp)).getText().contains("thanks"))
			driver.findElement(By.name("No thanks")).click();
	}

	/**
	 * Sets the given password to the given mobileEmail loginId.
	 *
	 * @param driver      the driver
	 * @param mobileEmail the mobile email
	 * @param ans         the ans
	 * @param newPin      the new pin
	 */
	public static void setPassword(WebDriver driver, String mobileEmail, String ans, String newPin) {
		WelcomePage wp = new WelcomePage(driver);
		wp.goToForgotPin();

		ForgotPinStep1Page fps1p = new ForgotPinStep1Page(driver);
		fps1p.changePin(mobileEmail, BaseTest1.bankCode);

		ForgotPinStep2Page fps2p = new ForgotPinStep2Page(driver);
		fps2p.answerSecurityQuestion(ans);

		ForgotPinStep3Page fps3p = new ForgotPinStep3Page(driver);
		fps3p.enterNewPin(newPin);
		fps3p.changePinSuccessfullMsg();
	}

	/**
	 * Logs out from any page within the app.by restarting the App
	 *
	 * @param driver the driver
	 * @see BasePage logOut()
	 */
	public static void logout(WebDriver driver) {
		Log.info("======== Performing Force Log out ========");

		if (Generic.isAndroid(driver)) {
			Activity appActivity = new Activity(BaseTest1.packageName, BaseTest1.appActivity);
			((AndroidDriver) driver).startActivity(appActivity);
			resetPopups(); // To handle popups in next Login
		} else

		if (true)
			return;

		// ------------------------------- Logout confirmation buttons unpredictable
		// behaviour ---------------------------------//
		// For BaseClass logout()
		// Use syso instead of Log // If CurrentActivity is not AUT then return

	}

	/**
	 * Gets the prop values corresponding to the Key from the config.properties
	 * file.
	 *
	 * @param key  the key
	 * @param path the path
	 * @return the prop values
	 */
	public static String getPropValues(String key, String path) {

		String value = "";
		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream(path);
			prop.load(input);
		} catch (Exception e) {
			System.err.println(
					"--------------------------------------------------------------------------------------------------");
			System.err.println("Please Check The path of Properties file: Provided Path is: " + path);
			System.err.println(
					"--------------------------------------------------------------------------------------------------");
			System.out.println(e.toString());
			System.exit(0);
		}

		value = prop.getProperty(key);
		return value;
	}

	/**
	 * Launches and returns a Firefox driver
	 * 
	 * @return FirefoxDriver
	 */
	public static FirefoxDriver createFirefoxDriver() {
		return new FirefoxDriver();
	}

	/**
	 * Launches and returns a Firefox driver with the given profile
	 * 
	 * @return FirefoxDriver
	 */
	public static FirefoxDriver createFirefoxDriver(FirefoxProfile profile) {
		return new FirefoxDriver(new FirefoxOptions().setProfile(profile));
	}

	/**
	 * Launches and returns a ChromeDriver
	 * 
	 * @return ChromeDriver
	 */
	public static ChromeDriver createChromeDriver() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setBinary("./ChromeBinary/chrome.exe"); // Version Control across end user systems

		if (System.getProperty("os.name").contains("Mac"))
			return new ChromeDriver();
		else
			return new ChromeDriver(chromeOptions);

	}

	public static WebDriver createCSRDriver() {
		WebDriver driver = getPropValues("CSRBROWSER", BaseTest1.configPath).contains("Fire") ? createFirefoxDriver()
				: createChromeDriver();
		return driver;
	}

	public static String getHTTPResponseString(String url) {
		String USER_AGENT = "Mozilla/5.0";

		StringBuffer result = new StringBuffer();
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = null;
		String OTP = "";

		System.out.println(url);
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", USER_AGENT);

		try {
			response = client.execute(request);
			int responseCode = response.getStatusLine().getStatusCode();
			if (responseCode == 200) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line = "";
				while ((line = reader.readLine()) != null)
					result.append(line);
			}
			return result.toString();
		} catch (Exception ex) {
			Log.info("== Get Response failed ==");
			return "null";
		}

	}

	public static synchronized String getOTP(String mobileNo, String bank, String event) {
		String USER_AGENT = "Mozilla/5.0";

		StringBuffer result = new StringBuffer();
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = null;
		String OTP = "";

		String url = getPropValues("OTPURL", BaseTest1.configPath).replace("AccessData", mobileNo)
				.replace("ProgramId", bank).replace("EventId", event);
		System.out.println(url);
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", USER_AGENT);

		try {
			response = client.execute(request);
			int responseCode = response.getStatusLine().getStatusCode();
			if (responseCode == 200) {
				// System.out.println("Get Response is Successfull");
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line = "";
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
			}
			OTP = result.toString();
		} catch (Exception ex) {
			Log.info("== OTP Get Response failed, Attempting OTP from Browser ==");
			OTP = getOTPBrowser(mobileNo, bank, event);
		}

		if (OTP.length() != 6) {
			Log.info("== Invalid OTP : " + OTP + "==");
			OTP = getOTPChrome(mobileNo, bank, event);
		}

		return OTP.split("\n")[0];
	}

	/**
	 * Gets the otp from DB corresponding to the given Event.
	 *
	 * @param mobileNo the mobile no
	 * @param bank     the bank
	 * @param event    the event
	 * @return the otp
	 */
	public static String getOTPBrowser(String mobileNo, String bank, String event) {
		String otp = "";
		WebDriver driver = null;

		try {

			driver = createFirefoxDriver();

			String URL = getPropValues("OTPURL", BaseTest1.configPath).replace("AccessData", mobileNo)
					.replace("ProgramId", bank).replace("EventId", event);
			driver.get(URL);
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			otp = driver.findElement(By.xpath("//body")).getText().trim();
		} catch (Exception e) {
			Log.info("== Error retreiving OTP ==");
		} finally {
			if (driver != null)
				driver.quit();
		}

		if (otp.isEmpty())
			otp = getOTPChrome(mobileNo, bank, event);
		return otp;
	}

	public static String getOTPChrome(String mobileNo, String bank, String event) {
		String otp = "";
		System.setProperty("webdriver.chrome.driver", "./config/chromedriver.exe");
		WebDriver driver = null;

		try {

			driver = createChromeDriver();
			String URL = getPropValues("OTPURL", BaseTest1.configPath).replace("AccessData", mobileNo)
					.replace("ProgramId", bank).replace("EventId", event);
			driver.get(URL);
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			otp = driver.findElement(By.xpath("//body")).getText().trim();
		} catch (Exception e) {
			Log.info("== Error retreiving OTP from Chrome ==");
		} finally {
			if (driver != null)
				driver.quit();
		}
		if (otp.isEmpty())
			Log.info("== Error retreiving OTP from Chrome ,OTP is blank ==");

		Log.info("== OTP " + otp + " was retrieved using Chrome driver ==");
		return otp;

	}

	/**
	 * Waits until the given text is visible inside the element.
	 *
	 * @param driver         the driver
	 * @param element        the element
	 * @param text           the text
	 * @param timeoutSeconds the timeout seconds
	 * @return true, if successful
	 */
	public static boolean waitUntilElementTextVisible(WebDriver driver, WebElement element, String text,
			int timeoutSeconds) {
		
		if(isIos(driver)) return false; // TBI 
		
		String content;
		for (int i = 0; i < timeoutSeconds; i++) // Need to create separate method for element Attribute visible, cannot
													// include getAttribute() here
		{
			wait(1);
			content = element.getText();
			if (!(content.toLowerCase().contains(text.toLowerCase()) || content.equals("")))
				return true;

		}
		return false;
	}

	/**
	 * Waits until an element is populated by text.
	 *
	 * @param driver
	 * @param element
	 * @param timeoutSeconds
	 */
	public static String waitUntilAnyTextVisible(WebDriver driver, WebElement element, int timeoutSeconds) {
		String content;
		for (int i = 0; i < timeoutSeconds; i++) // Need to create separate method for element Attribute visible, cannot
													// include getAttribute() here
		{
			wait(1);
			content = element.getText();
			if (!content.isEmpty()) {
				Log.info("======== Element text found : " + content + " ========");
				return content;
			}
		}
		return "No Text found";
	}

	/**
	 * Logs in by skipping Phone Verification and selecting unrtusted device under
	 * Verify device page.
	 *
	 * @param driver the driver
	 * @param data   the data
	 */
	public static void loginUntrustedDevice(WebDriver driver, String data) {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		String[] values = data.split(",");
		String loginId = values[0], securePin = values[1];

		WelcomePage wp = new WelcomePage(driver);
		wp.changeUserTemp(loginId);
		wp.selectUser(loginId);

		LoginNewPage lnp = new LoginNewPage(driver);
		lnp.login(securePin);

		resetPopups();
		new HandleHomePopup(driver).start();

		String xp = "//android.widget.TextView[contains(@text,'Verify') or contains(@text,'Home') or contains(@text,'Unclaimed')]"
				+ "|//*[contains(@resource-id,'text1')] ";

		try {
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath(xp))));
			wait(3);
		} catch (Exception e) {
			Assert.fail(" Page taking too much time to load, stopping execution\n");
		}

		WebElement pageTitle = driver.findElement(By.id("title_text"));

		Log.info("======== Going through " + pageTitle.getText() + " page ========");
		if (pageTitle.getText().contains("Phone") || pageTitle.getText().contains("SIM")) {
			new VerifyPhonePage(driver).loginWithoutPersonalDevice();
			wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath(xp))));
			wait(3);
		}
		if (pageTitle.getText().contains("Device")) {
			new VerifyDevicePage(driver).loginWithUntrustedVerifyDevice(data.split(",")[0], BaseTest1.bankCode);
			// wait(3);
			// verifyLogin(driver,data);
		}
	}

	/**
	 * Verifies the balance deducted by comparing the balances before and after
	 * transaction.
	 *
	 * @param balanceBeforeTransaction the balance before transaction
	 * @param amt                      the amt
	 * @param balanceAfterTransaction  the balance after transaction
	 */
	public static void verifyBalanceDeduct(double balanceBeforeTransaction, double amt,
			double balanceAfterTransaction) {
		if (checkEnv("qa"))
			return; // program cards currently not recognised in QA
		Log.info("======== Balance before transaction : " + balanceBeforeTransaction + " ========");
		Log.info("======== Balance after transaction : " + balanceAfterTransaction + " ========");
		Log.info("======== Verifying deducted balance :" + amt + " ========");
		Assert.assertEquals(balanceBeforeTransaction - balanceAfterTransaction, amt, 0.05,
				" Balance amount not deducted correctly \n ");
	}

	/**
	 * This method verifies balance is unchanged
	 * 
	 */
	public static void verifyBalanceUnchanged(double balanceBeforeTransaction, double balanceAfterTransaction) {
		Log.info("======== Balance before transaction : " + balanceBeforeTransaction + " ========");
		Log.info("======== Balance after transaction : " + balanceAfterTransaction + " ========");
		Assert.assertTrue(balanceAfterTransaction == balanceBeforeTransaction, "======== Balance changed ========");
	}

	/**
	 * Verifies the balance added by comparing the balances before and after
	 * transaction.
	 *
	 * @param balanceBeforeTransaction the balance before transaction
	 * @param amt                      the amt
	 * @param balanceAfterTransaction  the balance after transaction
	 */
	public static void verifyBalanceAdded(double balanceBeforeTransaction, double amt, double balanceAfterTransaction) {
		// if(checkEnv("qa")) return; // program cards currently not recognised in QA
		Log.info("======== Balance before transaction : " + balanceBeforeTransaction + " ========");
		Log.info("======== Balance after transaction : " + balanceAfterTransaction + " ========");
		Log.info("======== Verifying added balance :" + amt + " ========");
		Assert.assertEquals(balanceAfterTransaction - balanceBeforeTransaction, amt, 0.05,
				" Balance amount not added correctly \n ");
	}

	/**
	 * Returns the month name corresponding to the month index
	 * 
	 * @param monthIndex
	 * @return
	 */
	public static String getMonthName(int monthIndex) {
		if (monthIndex <= 12)
			return new DateFormatSymbols().getMonths()[monthIndex - 1].toString();
		else
			return "October";
	}

	/**
	 * 
	 * @return Integer value for current month
	 */
	public static int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 
	 * @return Integer value for current year
	 */
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * Performs a Login in the Merchant app and handles any popups by calling the
	 * paralell thread HandleOverlayPopup.
	 *
	 * @param driver the driver
	 * @param data   the data
	 */
	public static void merchantVerifiedLogin(WebDriver driver, String data) {

		String xp = "//android.widget.TextView[contains(@text,'Verify') or contains(@resource-id,'txn_merchant_name') or contains(@text,'Request')] | //android.widget.Button[contains(@resource-id,'got')]",
				predicate = "value beginswith 'Verify' or value beginswith 'Payment'";
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait(2); // UiAuto2

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
			wait(3);
			new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));
		}
		if (driver.findElement(By.xpath(xp)).getText().contains("Device")) {
			Log.info("======== Going through Verify Device page ========");
			new VerifyDevicePage(driver).loginWithTrustedVerifyDevice(data.split(",")[0], BaseTest1.bankCode);
			wait(2);
		}
	}

	/**
	 * Performs a Login in the Merchant app by skipping Phone Verification.
	 *
	 * @param driver the driver
	 * @param data   the data
	 */
	public static void merchantUnverifiedLogin(WebDriver driver, String data) {
		wait(3);
		String xp = "//android.widget.TextView[contains(@text,'Verify') or contains(@resource-id,'approve_textbutton') ] | //android.widget.Button";
		// got it popup
		try {
			new WebDriverWait(driver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));
			Generic.wait(5);
		} catch (Exception e) {
			Assert.fail("Page not found or taking too much time to load\n" + e.getMessage());
		}
		if (!driver.findElement(By.xpath(xp)).getText().contains("Verify"))
			Assert.fail("Verify Page not found \n");

		if (driver.findElement(By.xpath(xp)).getText().contains("Phone")) {
			Log.info("======== Going through Verify Phone page ========");
			new VerifyPhonePage(driver).loginWithoutPersonalDevice();
			wait(3);
			try {
				new WebDriverWait(driver, 45).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));
			} catch (Exception e) {
				Assert.fail("Page not found or taking too much time to load\n" + e.getMessage());
			}
		}
		if (driver.findElement(By.xpath(xp)).getText().contains("Device")) {
			Log.info("======== Going through Verify Device Page ========");
			new VerifyDevicePage(driver).loginWithUntrustedVerifyDevice(data.split(",")[0], BaseTest1.bankCode);
			wait(3);
		}
	}

	/**
	 * Performs a static wait for the given number of seconds.
	 *
	 * @param Seconds the seconds
	 */
	public static void wait(int Seconds) {
		try {
			Thread.sleep(Seconds * 1000);
		} catch (InterruptedException e) {
		}
	}

	public static void waitForEmbeddedWebview(WebDriver driver, int seconds) {
		if (!checkAndroid(driver))
			return;

		String xp = "//*[contains(@class,'android.view') or contains(@class,'android.webkit')]";

		try {
			new WebDriverWait(driver, seconds).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xp)));
			Log.info("======== Context currently in Embedded WebView ========");
		} catch (Exception e) {
			Log.info("== WebView Delay ==");
		}
	}

	/**
	 * Requires setWebContentsDebuggingEnabled=true by App developer Waits for
	 * embeddedWebView
	 * 
	 *
	 * @param driver the driver
	 * @return true, if successful
	 */
	public static boolean switchToWebView(WebDriver driver) {
		/*
		 * if(getAndroidVersion(driver).split("v")[1].split("\\.")[0].equals("4")) {
		 * System.out.println("Unable to Switch to WebView for Android"
		 * +getAndroidVersion(driver));return false; }
		 */

		waitForEmbeddedWebview(driver, 90);
		if (checkWebView(driver))
			return true;

		AndroidDriver adriver = (AndroidDriver) driver;

		Set<String> contexts = adriver.getContextHandles();

		Log.info("== Contexts : " + contexts + " ==");

		for (String context : contexts)
			if (context.contains("WEB") && !context.contains("chrome") && context.contains("test")) // skip
																									// WEBVIEW_chrome ,
																									// select
																									// WEBVIEW_com.enstage.wibmo.sdk.test
			{
				System.out.println("Switching to " + context);
				Generic.wait(1);
				adriver.context(context);
				Generic.wait(1);
			}
		// adriver.context("WEBVIEW_com.enstage.wibmo.sdk.test");

		Log.info("== Context After Switching to WebView : " + adriver.getContext() + " == ");
		if (!adriver.getContext().contains("WEB")) {
			Log.info("== Unable to Switch To WebView ==");
			return false;
		} else
			return true;
	}

	public static void switchToNative(WebDriver driver) {
		if (!checkWebView(driver) || !checkAndroid(driver))
			return;

		AndroidDriver adriver = (AndroidDriver) driver;

		Set<String> contexts = adriver.getContextHandles();

		System.out.println("Switching to Native");

		for (String context : contexts)
			if (context.contains("NATIVE"))
				adriver.context(context);

		System.out.println("Context After Switch : " + adriver.getContext());
	}

	/**
	 * Verifies the Toast message.
	 *
	 * @param driver       the driver
	 * @param toastContent to be verified
	 * @return Toast Message text
	 */
	public static String verifyToastContent(WebDriver driver, String toastContent) {
		if (!Generic.isUiAuto2(driver))
			return ""; // Check for version >= Android 6 & UiAuto2
		if (Generic.getAPILevel(getUdId(driver)) <= 22)
			return "";

		String toastTxt = "", txp = "//*[contains(@text,'toastContent')]".replace("toastContent", toastContent); // content
																													// not
																													// in
																													// content-desc

		try {
			new WebDriverWait(driver, 30).pollingEvery(1, TimeUnit.SECONDS)
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath(txp))); // polling necessary for Uiauto2

			toastTxt = driver.findElement(By.xpath(txp)).getText();

			Log.info("======== Verifying Toast message : " + toastTxt + " ========");
			return toastTxt;
		} catch (Exception e) {
			Assert.fail("Toast Message not found " + e.getMessage());
		}
		return toastTxt;
	}

	public static void angularWait(WebDriver driver) {

		try {
			driver.manage().timeouts().setScriptTimeout(25, TimeUnit.SECONDS);
			new NgWebDriver((JavascriptExecutor) driver).waitForAngularRequestsToFinish();
		} catch (Exception e) {
			Log.info("== JS Exception ==" + e.getMessage().split(":")[0]);
		}
	}

	public static void waitForElement(WebDriver driver, WebElement element, int timeOutInSeconds) {
		try {
			new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			Log.info("== Timed out waiting for element ==");
		}
	}

	/**
	 * Force clears the text in a given textfield if the text is represented as
	 * content-desc(name) Used when the clear() method is unable to clear the
	 * textfield.
	 *
	 * @param driver the driver
	 * @param field  the field
	 * @deprecated use webview instead
	 */
	public static void forceClearAttribute(WebDriver driver,WebElement field)
	{
//		if(checkWebView(driver)) 
//			{field.clear();return;}
//		
//		new TouchAction((AndroidDriver)driver).longPress(field).perform();
//		((AndroidDriver)driver).pressKeyCode(67); // Press delete
//		
//		int textLength=field.getAttribute("contentDescription").length();					
//		for(int i=0;i<=textLength;i++)		
//			((AndroidDriver)driver).pressKeyCode(22); // Press right			
//		for(int i=0;i<=textLength;i++)		
//			((AndroidDriver)driver).pressKeyCode(67); // Press delete			
	}

	public static boolean checkWebView(WebDriver driver) {
		if (!checkAndroid(driver))
			return false;

		return ((AndroidDriver) driver).getContext().contains("WEB");
	}

	public static boolean checkNativeApp(WebDriver driver) {
		if (!checkAndroid(driver))
			return false;

		AndroidDriver adriver = (AndroidDriver) driver;

		System.out.println("Checking Native App context : " + adriver.getContext());

		return adriver.getContextHandles().contains("NATIVE") && adriver.getContextHandles().size() == 1;
	}

	/**
	 * Checks whether any of the text arguments are present in the pageSource Any
	 * number of comma separated text arguments can be passed
	 * 
	 * @param driver the driver
	 * @return the String among the String args if pageSource contains any of the
	 *         String args. "notFound" if none of the args are found
	 * 
	 */
	public static String checkTextInPageSource(WebDriver driver, String... args) {
		if (isIos(driver))
			return "notFound"; // pageSource command not reliable for IOS

		String pageSource = driver.getPageSource();

		for (String text : args) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException ie) {
			} // Prevent target server exception
			if (pageSource.contains(text))
				return text;
		}
		return "notFound";
	}

	/**
	 * Force clears the text in a given textfield if the text is represented as
	 * text(name) Can only clear text which does not contain blank space (Method to
	 * be updated if necessary). Used when the clear() method is unable to clear the
	 * textfield. Currently configured for Secure Pin textfield and WebOverlay
	 *
	 * @param driver the driver
	 * @param field  the field to be cleared
	 * @deprecated use UiAuto2
	 */
	public static void forceClearText(WebDriver driver,WebElement field)
	{
//		if(checkWebView(driver)) 
//			{
//				field.clear();
//				return ;
//			}
//		
//		new TouchAction((AndroidDriver)driver).longPress(field).perform();
//		
//		for(int i=0;i<=11;i++)		
//			((AndroidDriver)driver).pressKeyCode(67); // Press delete 
	}

	/**
	 * Sets the user profile to perform an ITP by performing SIM Verification after
	 * logging in. SIM Verification can be verified only after relogin. To be moved
	 * to BaseTest1
	 *
	 * @param driver the driver
	 * @param data   the data
	 * @deprecated Moved to BaseTest1
	 */
	public static void preconditionITP(WebDriver driver, String data) {
		BaseTest1.setITP(true);

		gotoVerifySIM(driver, data);

		Generic.waitUntilAnyTextVisible(driver, driver.findElement(By.id("device_verification_text")), 30);

		String verificationStatusMessage = driver.findElement(By.id("device_verification_text")).getText();

		Log.info("======== Verifying SIM verification status :" + verificationStatusMessage + "========");
		if (verificationStatusMessage.contains("Sorry!"))
			Assert.fail("SIM card not detected \n");

		if (!verificationStatusMessage.contains("not")) {
			Log.info("======== Phone Verified ========");
			return;
		}
		// ==============//

		Log.info("======== Clicking on verify button ========");
		driver.findElement(By.id("verify_button")).click();

		for (WebElement e : driver.findElements(By.id("text1"))) // Select any/all Sim and Number dropdowns
		{
			e.click();
			driver.findElements(By.id("text1")).get(1).click();
		}
		wait(2);

		driver.findElement(By.id("main_btnContinue")).click(); // click on Continue button

		String xp = "//android.widget.Button[contains(@text,'OK')] | //android.widget.TextView[contains(@resource-id,'device_verification_text')]";
		try {
			new WebDriverWait(driver, 90).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));
			wait(2);
			if (driver.findElement(By.xpath(xp)).getText().contains("OK")) {
				driver.findElement(By.name("OK")).click();
				new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));
				driver.findElement(By.xpath(xp)).click(); // Handle multiple prepaid popups
			}
		} catch (Exception e) {
			Assert.fail("======== Phone verification taking too much time to load , stopping execution \n"
					+ e.getMessage());
		}

		// ==== Relogin ==== //

		logout(driver);
		gotoVerifySIM(driver, data);
		Generic.waitUntilAnyTextVisible(driver, driver.findElement(By.id("device_verification_text")), 30);

		if (driver.findElement(By.id("device_verification_text")).getText().contains("not"))
			Assert.fail("ITP precondition SIM verification not complete , Please check mobile number");
		else
			Log.info("======== Precondition set for ITP ========");
	}

	/**
	 * Navigates to Verify SIM page from any page with/without login.
	 *
	 * @param driver the driver
	 * @param data   the data
	 * @deprecated moved to BaseTest1
	 */
	public static void gotoVerifySIM(WebDriver driver, String data) {
		ArrayList<String> options = new ArrayList<String>();
		driver.findElement(By.name("More options")).click();
		wait(1);
		List<WebElement> optionList = driver.findElements(By.id("title"));
		for (WebElement o : optionList)
			options.add(o.getText());

		driver.navigate().back();

		if (options.contains("Change User")) // If user not already logged in then login
		{
			WelcomePage wp = new WelcomePage(driver);
			wp.selectUser(data.split(",")[0]);

			LoginNewPage lnp = new LoginNewPage(driver);
			lnp.login(data.split(",")[1]);

			verifyLogin(driver, data);
		}

		Log.info("======== Navigatng to Settings page ========");
		driver.findElement(By.name("Navigate up")).click();
		wait(2);
		scroll(driver, "Settings").click();
		wait(3);
		Log.info("======== Clicking on Verify SIM ========");
		scroll(driver, "Verify SIM").click();
	}

	/**
	 * Executes phone verification after login.
	 *
	 * @param driver the driver
	 * @param data   the data
	 */
	public static void executePhoneVerification(WebDriver driver, String data) {
		gotoVerifySIM(driver, data);

		Generic.waitUntilAnyTextVisible(driver, driver.findElement(By.id("device_verification_text")), 30);

		if (!driver.findElement(By.id("device_verification_text")).getText().contains("not")) {
			Log.info("======== Phone Verified ========");
			return;
		}

		Log.info("======== Clicking on verify button ========");
		driver.findElement(By.id("verify_button")).click();

		for (WebElement e : driver.findElements(By.id("text1"))) // Select any/all Sim and Number dropdowns
		{
			e.click();
			driver.findElements(By.id("text1")).get(1).click();
		}
		wait(2);

		Log.info("======== Clicking on Continue Button ========");
		driver.findElement(By.id("main_btnContinue")).click(); // click on Continue button

		String xp = "//android.widget.Button[contains(@text,'OK')] | //android.widget.TextView[contains(@resource-id,'device_verification_text')]"; // OK
																																					// represents
																																					// prepaid
																																					// popup
		try {
			new WebDriverWait(driver, 90).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));
			wait(2);
			if (driver.findElement(By.xpath(xp)).getText().contains("OK")) {
				driver.findElement(By.name("OK")).click();
				new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));
				driver.findElement(By.xpath(xp)).click(); // Handle multiple prepaid popups
			}
		} catch (Exception e) {
			Assert.fail("======== Phone verification status page taking too much time to load , stopping execution \n"
					+ e.getMessage());
		}
		// ==== Relogin ==== //

		logout(driver);
		gotoVerifySIM(driver, data);
		Generic.waitUntilAnyTextVisible(driver, driver.findElement(By.id("device_verification_text")), 30);

		if (driver.findElement(By.id("device_verification_text")).getText().contains("not"))
			Assert.fail("SIM verification not complete , Please check mobile number\n");
		else
			Log.info("======== Phone Verified ========");

		// ==== No Logout ==== //
	}

	/**
	 * Waits for Phone verification page to occur and then cancels the Phone
	 * verification.
	 *
	 * @param driver the driver
	 */
	public static void abortPhoneVerification(WebDriver driver) // BT1
	{
		waitForPhoneVerification(driver);
		if (driver.findElement(By.id("title_text")).getText().contains("Verify")) {
			Log.info("======== Aborting Phone verification ========");
			driver.navigate().back();
		}
	}

	/**
	 * Waits for phone verification page to occur.
	 *
	 * @param driver the driver
	 */
	public static void waitForPhoneVerification(WebDriver driver) // BT1
	{
		String xp = "//android.widget.TextView[contains(@text,'Verify')]";
		try {
			new WebDriverWait(driver, 40).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xp)));
		} catch (Exception e) {
			Assert.fail("Phone Verification page not found\n" + e.getMessage());
		}
	}

	/**
	 * Sets the merchant to Static or Dynamic type at the Web Merchant Shell
	 * Configuration URL.
	 *
	 * @param type the new merchant
	 */
	public static void setMerchant(String type) {

		type = Generic.containsIgnoreCase(type, "dynamic") ? "dynamic" : "static";

		if (checkPresetMerchant(type))
			return;

		System.out.println("======== Checking and setting Merchant Shell config settings ========");

		WebDriver driver = new ChromeDriver(/* BaseTest1.chromeService() */); // FirefoxDriver
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get(getPropValues("MERCHANTCONFIGURL", BaseTest1.configPath));
		WebElement merID = driver.findElement(By.name("merId"));
		WebElement merAppId = driver.findElement(By.name("merAppId"));
		WebElement hashKey = driver.findElement(By.name("hashkey"));

		switch (type.toLowerCase()) {
		case "static":
			if (merID.getAttribute("value").equals(getPropValues("SMERCHANTID", BaseTest1.configPath))) {
				try {
					Log.info("Normal merchant settings already set in Merchant Shell Config URL");
				} catch (Exception e) {
				}
				break;
			}
			merID.clear();
			merID.sendKeys(getPropValues("SMERCHANTID", BaseTest1.configPath));
			merAppId.clear();
			merAppId.sendKeys(getPropValues("SMERCHANTAPPID", BaseTest1.configPath));
			hashKey.clear();
			hashKey.sendKeys(getPropValues("SHASHKEY", BaseTest1.configPath));
			break;
		case "dynamic":
			if (merID.getAttribute("value").equals(getPropValues("DMERCHANTID", BaseTest1.configPath))) {
				try {
					Log.info("Dynamic merchant settings already set in Merchant Shell Config URL");
				} catch (Exception e) {
				}
				break;
			}
			merID.clear();
			merID.sendKeys(getPropValues("DMERCHANTID", BaseTest1.configPath));
			merAppId.clear();
			merAppId.sendKeys(getPropValues("DMERCHANTAPPID", BaseTest1.configPath));
			hashKey.clear();
			hashKey.sendKeys(getPropValues("DHASHKEY", BaseTest1.configPath));
			break;
		}
		driver.findElement(By.xpath("//input[contains(@value,'Set new value')]")).click();
		driver.quit();

		wibmo.app.testScripts.IAP_Transaction.BaseTest.setMerchantType(type);
	}

	public static boolean checkPresetMerchant(String merchantType) {
		String configUrl = getPropValues("MERCHANTCONFIGURL", BaseTest1.configPath),
				merchantCheckValue = (merchantType.contains("static"))
						? getPropValues("SMERCHANTID", BaseTest1.configPath)
						: getPropValues("DMERCHANTID", BaseTest1.configPath);

		if (getHTTPResponseString(configUrl).contains(merchantCheckValue)) {
			System.out
					.println("=== Merchant Value already set to " + merchantType + " : " + merchantCheckValue + " ===");
			return true;
		} else
			return false;
	}

	/**
	 * Opens the merchant configaration URL and sets an incorrect App Id.
	 *
	 * @return the string
	 */
	public static String setIncorrectMerchantAppId() {
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get(getPropValues("MERCHANTCONFIGURL", BaseTest1.configPath));

		WebElement merAppId = driver.findElement(By.name("merAppId"));

		String correctMerchantAppId = merAppId.getAttribute("value");
		Log.info("======== Retrieving correct Merchant App Id : " + correctMerchantAppId + " ========");

		Log.info("======== Setting Incorrect Merchant App Id in Merchant configuration ========");
		merAppId.clear();
		merAppId.sendKeys("1111");

		Log.info("======== Clicking on Set new value button ========");
		driver.findElement(By.xpath("//input[contains(@value,'Set new value')]")).click();

		driver.quit();

		return correctMerchantAppId;
	}

	/**
	 * Opens the merchant configaration URL and sets an incorrect App Id.
	 *
	 * @param correctMerchantAppId the new correct merchant app id
	 */
	public static void setCorrectMerchantAppId(String correctMerchantAppId) {
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get(getPropValues("MERCHANTCONFIGURL", BaseTest1.configPath));

		WebElement merAppId = driver.findElement(By.name("merAppId"));

		Log.info("======== Setting correct Merchant App Id in Merchant configuration : " + correctMerchantAppId
				+ " ========");
		merAppId.clear();
		merAppId.sendKeys(correctMerchantAppId);

		Log.info("======== Clicking on Set new value button ========");
		driver.findElement(By.xpath("//input[contains(@value,'Set new value')]")).click();

		driver.quit();
	}

	/**
	 * Used as postcondition to set the hash value to its original correct value.
	 * 
	 * @param driver
	 */
	public static void setCorrectHashMerchantApp(WebDriver driver) {
		try {
			if (!checkTextInPageSource(driver, "Wibmo SDK Test").contains("Test"))
				Generic.switchToMerchant(driver);

			MerchantHomePage mhp = new MerchantHomePage(driver);
			mhp.gotoSettings();

			MerchantSettingsPage msp = new MerchantSettingsPage(driver);
			msp.setHashCorrectValue();

			driver.navigate().back();
			wait(2);
		} catch (Exception e) {
			Log.info("Warning : Incorrect hash value may be set" + e.getMessage());
		}
	}

	/**
	 * Opens the merchant configuration URL and sets the incorrect hash key.
	 */
	public static void setIncorrectHashKey() {
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.get(getPropValues("MERCHANTCONFIGURL", BaseTest1.configPath));

		WebElement SecretHashKeyTextField = driver.findElement(By.xpath("//input[@name='hashkey']"));
		WebElement setNewValueButton = driver.findElement(By.xpath("//input[@value='Set new value']"));

		SecretHashKeyTextField.clear();
		SecretHashKeyTextField.sendKeys("1111");
		setNewValueButton.click();
		driver.quit();
	}
	
	/**
	 *  Launches the given package name and activity 
	 * 
	 * @param driver
	 * @param pkgName
	 * @param activityName
	 */
	public static void startAppActivity(WebDriver driver,String pkgName,String activityName)
	{
		((AndroidDriver) driver).startActivity(new Activity(pkgName,activityName));
	}

	/**
	 * Returns the the current activity of the Android driver.
	 *
	 * @param driver the driver
	 * @return the current activity
	 */
	public static String getCurrentActivity(WebDriver driver) {
		return ((AndroidDriver) driver).currentActivity();
	}

	public static SessionId getSessionId(WebDriver driver) {
		if (!checkAndroid(driver))
			return null;

		System.out.println("Returning Session Id");

		return ((AndroidDriver) driver).getSessionId();
	}

	public static void openNotifications(WebDriver driver) {
		((AndroidDriver) driver).openNotifications();
		Generic.wait(1); // Wait for Multiple Notifications to settle

	}

	public static void gotoIAPFromNotifications(WebDriver driver) {
		openNotifications(driver);

		String notificationXp = "//*[contains(@text,'InApp')]";

		Log.info("======== Clicking on Notification ========");
		try {
			driver.findElement(By.xpath(notificationXp)).click();
		} catch (Exception e) {
			Assert.fail("Required Notification not found\n");
		}
	}

	/**
	 * Opens Notifications . Clicks on Notifications & navigates to Messages to
	 * verify the SMS message
	 * 
	 * @param driver
	 * @param notificationTxt
	 * @param smsContent
	 * @param waitTimeForNotification
	 * 
	 */
	public static void verifySMSContentFromNotifications(WebDriver driver, String notificationTxt, String smsContent,
			int waitTimeForNotification) {
		
		boolean notificationFound=false;
		
		Generic.wait(waitTimeForNotification);

		openNotifications(driver);

		String notificationXp = "//*[contains(@text,'notificationTxt')]".replace("notificationTxt", notificationTxt),
				contentXp = "//*[contains(@text,'smsContent')]".replace("smsContent", smsContent);

		Log.info("======== Clicking on Notification with notification Text : " + notificationTxt + " ========");
		try {
			driver.findElement(By.xpath(notificationXp)).click();
			wait(1);
			driver.findElement(By.xpath(notificationXp)).click(); // Handle Selection from multiople notiifcations
			wait(1);
			driver.findElement(By.xpath(notificationXp)).click();
			
			notificationFound=true;
			
		} catch (Exception e) {
			try {
				Assert.fail("Notification with " + notificationTxt + " text not found ");
			} finally {
				
				if(!notificationFound)
				{
					Log.info("=== Navigating Back to close Notifications ===");
					switchToAppWithState(driver);
				}
				
			}
		}

		try {
			Log.info("======== Verifying SMS Content : " + (driver.findElement(By.xpath(contentXp)).getText())
					+ " ========");
		} catch (Exception e) {
			Assert.fail("SMS not found");
		} finally {
			Generic.wait(3);
			driver.navigate().back();
		} // Navigate Back to AppActivity

	}

	public static void verifyNotificationAbsent(WebDriver driver) {
		openNotifications(driver);

		String notificationXp = "//*[contains(@text,'InApp')]";
		Log.info("======== Verifying Absence of Notification ========");

		if (checkElementOccurenceOnXpath(driver, notificationXp, 3))
			Assert.fail("Notification message found");

	}

	/**
	 * Resets the Thread static fields as true i.e ready to handle popups.
	 */
	public static void resetPopups() {
		// HandleHomePopup.handled=HandleOverlayPopup.handled=HandleLoginPopup.handled=false;
	}

	public static void resetPopups(WebDriver driver) {
		// HandleHomePopup.handled=HandleOverlayPopup.handled=HandleLoginPopup.handled=false;
		// HandleHomePopup.setHandledStatus(driver, false);
	}

	/**
	 * Sets the driver implicit wait to a lower timeout value.
	 *
	 * @param driver       the driver
	 * @param shortTimeout the short timeout
	 */
	public static void quicken(WebDriver driver, int shortTimeout) {
		driver.manage().timeouts().implicitlyWait(shortTimeout, TimeUnit.SECONDS);
	}

	/**
	 * Sets the driver implicit wait to a normal timeout value..
	 *
	 * @param driver        the driver
	 * @param normalTimeout the normal timeout
	 */
	public static void normalize(WebDriver driver, int normalTimeout) {
		driver.manage().timeouts().implicitlyWait(normalTimeout, TimeUnit.SECONDS);
	}

	/**
	 * Checks whether the containedString is present within the containerString
	 * while ignoring the case.
	 *
	 * @param containerString the container string
	 * @param containedString the contained string
	 * @return true, if successful
	 */
	public static boolean containsIgnoreCase(String containerString, String containedString) {
		return containerString.toLowerCase().contains(containedString.toLowerCase());
	}

	/**
	 * Parses the number present in the given string.
	 *
	 * @param numberText the number text
	 * @return the number in double which is present within the text
	 * @see parseNumberString
	 */
	public static double parseNumber(String numberText) {
		return Double.parseDouble(parseNumberString(numberText));
	}

	public static String parseNumberString(String numberText) {
		String number = "";
		char c;
		for (int i = 0; i < numberText.length(); i++) {
			c = numberText.charAt(i);
			if (Character.isDigit(c) || c == '.')
				number += c;
		}
		return number;
	}

	/**
	 * Converts the given value to implied decimal
	 * 
	 * @param val
	 * @return implied decimal String
	 */
	public static String convertToImpliedDecimal(String val) {
		return (Integer.parseInt(val) * 100) + "";
	}

	// Deprecated after IAP parallel integration
	public static String parseCVVStatus(String data) {
		if (!data.contains("CVV"))
			Assert.fail(" 'CVV' Status value not found in TestData :" + data);

		String value = data.split("CVV")[1].substring(0, 1);

		Log.info("======== Parsing CVV value from testdata : " + value + " ========");

		if (containsIgnoreCase(value, "Y"))
			return "Yes";
		if (containsIgnoreCase(value, "N"))
			return "No";

		return "Unknown";
	}

	/**
	 * Retrieves the data value from TestData based on the dataLabel
	 * 
	 * Ex : For TestData 9066946245,1234,panNumber_ABCDE1234P Use dataLabel as
	 * panNumber_ or panNumber to retrieve ABCDE1234P
	 * 
	 * @param driver
	 * @param data
	 * @param dataLabel
	 * @return
	 * @see BaseTest1 getVal()
	 */
	public static String parseDataVal(WebDriver driver, String data, String dataLabel) {
		if (!data.contains(dataLabel)) {
			System.err.println(dataLabel + " label not found in Testdata : " + data);
			return "null";
		}

		dataLabel = data.contains(dataLabel + '_') ? dataLabel + '_' : dataLabel; // Parse label_ as well as label

		return data.split(dataLabel)[1].split(",")[0];
	}

	public static String parseReferralCode(String data) {
		if (!data.contains("RC_"))
			Assert.fail("Referral Code RC_referralCode not found in Test Data : " + data);

		return data.split("RC_")[1].substring(0, 6);
	}

	/**
	 * Generates a mobile number.
	 * 
	 * @return String
	 */
	public static String generateMobileNumber() {
		long x = 1000000000L;
		long y = 6999999999L;

		Random r = new Random();

		long number = x + ((long) (r.nextDouble() * (y - x)));

		System.out.println("Number generated " + number);

		return number + "";
	}

	public static char generateRandomChar() {
		Random r = new Random();
		return (char) (r.nextInt(26) + 'A');
	}

	public static String getBootStrapPort(String port) {
		long x = 1023;
		long y = 65535;

		String bootStrapPort = "";

		Random r = new Random();

		long number = x + ((long) (r.nextDouble() * (y - x)));

		System.out.println("Number generated " + number);

		// ----------------------//

		int separator = Integer.parseInt("" + (port.charAt(r.nextInt(port.length() - 1))));

		bootStrapPort = separator + number + "";

		// ---------------------//

		return separator + number + "";
	}

	public static String generateAlphaNumeric() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
	}

	/**
	 * Checks whether the given mobile no. can receive OTP.
	 * 
	 * @return true if mobile can receive OTP.
	 */
	public static boolean checkOTPMobile(String mobileNo) {
		return Integer.parseInt(mobileNo.substring(0, 1)) > 6;
	}

	public static void clearApp(WebDriver driver) {

		String packageName = Generic.getPropValues("PACKAGENAME", BaseTest1.configPath);
		Log.info("======== Clearing " + packageName + " ========");

		String adbClearApp = "adb -s " + getExecutionDeviceId(driver) + " shell pm clear " + packageName; // adb -s
																											// Generic.getExecutiondeviceId()
																											// to be
																											// added

		try {
			Runtime.getRuntime().exec(adbClearApp);
		} catch (Exception e) {
			Assert.fail("Unable to clear app " + packageName + "\n" + e.getMessage());
		}
	}

	public static boolean checkAndroid(WebDriver driver) {
		if (driver == null)
			return false;

		System.out.println("driver type : " + driver.toString());
		return Generic.containsIgnoreCase(driver.toString(), "Android");
	}

	/**
	 * Checks whether a given udid belongs to an IOS device
	 * 
	 * @param udid
	 * @return true if yhe udid is ios
	 * @see isIos(WebDriver driver)
	 */
	public static boolean isIos(String udid) {
		return udid.length() > 24;
	}

	/**
	 * Checks whether the device is an emulator or not
	 * 
	 * 
	 * @param udid
	 * @return true if device is an emulator
	 */
	public static boolean isEmulator(String udid) {
		return udid.contains("emu") || udid.contains("."); // Cloud emulator as 192.168.239.101:5555
	}

	public static boolean isEmulator(WebDriver driver) {
		return isEmulator(getUdId(driver));
	}

	public static boolean isIos(WebDriver driver) {
		
		if(driver==null) System.err.println("Warning : Driver not initialized");		
		
		return driver != null && driver.toString().toLowerCase().contains("ios");
	}

	/**
	 * Checks whether the driver is Android
	 * 
	 * @param driver
	 * @return true if driver is AndroidDriver
	 * 
	 */
	public static boolean isAndroid(WebDriver driver) {
		return driver != null && driver.toString().toLowerCase().contains("android");
	}

	/**
	 * Checks whether the given driver is WebDriver or not
	 * 
	 * @param driver
	 * @return true if driver is WebDriver
	 */
	public static boolean isWebDriver(WebDriver driver) {
		if (driver == null)
			return false;

		String driverChk = driver.toString().toLowerCase();
		return !driverChk.contains("android") && !driverChk.contains("ios"); // AndroidDriver contains ChromeDriver path

	}

	/**
	 * 
	 * 
	 * @param driver
	 * @return
	 */

	public static boolean isUiAuto2(WebDriver driver) {
		if (checkAndroid(driver))
			return ((AndroidDriver) driver).getAutomationName().contains("2"); // UiAuto2 driver
		else
			return false;
	}

	/**
	 * Retrieves the device model from adb shell.
	 *
	 * @deprecated Use getDeviceModel(WebDriver driver) instead
	 * @return the device model
	 */
	public static String getDeviceModel() {
		String model = "", cmdOutput = "", cmd = "adb devices -l", productCmd = "adb shell getprop ro.product.brand",
				product = "";

		// ---- Obtain model ---- //
		try {
			cmdOutput = execCmd(cmd);
			model = cmdOutput.split("\\n")[1].split("\\s+")[3].split(":")[1];
			Log.info("======== Obtaining device model : " + model + " ========");
		} catch (Exception e) {
			Log.info("==== Could not obtain Device Model ====" + e.getMessage());
		}

		try {
			product = execCmd(productCmd).trim();
		} catch (Exception e) {
			Log.info("==== Could not obtain Product Name ====" + e.getMessage());
		}
		if (Generic.containsIgnoreCase(model, product))
			product = "";

		return product + " " + model;
	}

	/**
	 * Retrieves the device Android Version from adb shell.
	 *
	 * @return the Version
	 */
	/*
	 * public static String getAndroidVersion() { String
	 * version="",cmd="adb shell getprop ro.build.version.release";
	 * 
	 * if(multipleDevices) cmd="adb -s "+getExecutionDeviceId(driver)
	 * +" shell getprop ro.build.version.release";
	 * 
	 * try { String cmdOutput=execCmd(cmd);
	 * 
	 * if(!cmdOutput.toLowerCase().contains("error:")) version=cmdOutput.trim();
	 * 
	 * Log.info("======== Obtaining Android version : "+version+" ========"); }
	 * catch(Exception e) {
	 * Log.info("==== Could not obtain Android version ===="+e.getMessage());
	 * version="default"; } return version; }
	 */

	/**
	 * Retrieves the Device model with Android Version
	 * 
	 * @param driver
	 * @return
	 */
	public static String getDeviceModel(WebDriver driver) {

		if (!checkAndroid(driver))
			return "WebDriverExecution";

		AndroidDriver adriver = (AndroidDriver) driver;
		String model = "", product = "";
		String modelCmd = "adb -s udid shell getprop ro.product.model".replace("udid", getUdId(driver)),
				productCmd = "adb -s udid shell getprop ro.product.brand".replace("udid", getUdId(driver));

		// System.out.println("Capabilities : "+adriver.getCapabilities());

		if (adriver.getAutomationName().contains("2")) // UiAuto2
		{
			model = execCmd(modelCmd).split("[A-Za-z0-9]*")[0];
			product = execCmd(productCmd).split("\\W+")[0];
		} else {
			model = (String) adriver.getCapabilities().getCapability("deviceModel");
			product = (String) adriver.getCapabilities().getCapability("deviceManufacturer");
		}

		Log.info(
				"======== Retreiving device product name : " + (product = WordUtils.capitalize(product)) + " ========");
		Log.info("======== Retreiving device model : " + model + " ========");

		if (Generic.containsIgnoreCase(model, product))
			product = "";

		return product + " " + model + getAndroidVersion(driver);
	}

	/**
	 * Retrieves the device Android Version from Capabilities.
	 *
	 * @return the Version
	 */
	public static String getAndroidVersion(WebDriver driver) {
		if (!checkAndroid(driver))
			return "";

		String versionCmd = "adb -s udid shell getprop ro.build.version.release".replace("udid", getUdId(driver));

		AndroidDriver adriver = (AndroidDriver) driver;

		String ver = "";
		try {
			ver = adriver.getAutomationName().contains("2") ? // UiAuto2
					execCmd(versionCmd) : (String) (adriver).getCapabilities().getCapability("platformVersion");
		} catch (Exception e) {
			Log.info("==== Unable to obtain Android Version ====\n" + e.getMessage());
		}
		return " v" + ver;
	}

	public static synchronized int getAPILevel(String udid) {
		String verCmd = "adb -s " + udid + " shell getprop ro.build.version.sdk";
		try {
			return Integer.parseInt(execCmd(verCmd).substring(0, 2));
		} catch (Exception e) {
			System.err.println("Unable to obtain API level for " + udid + e.getMessage());
			return 23;
		}
	}

	public static void grantRuntimePermissions(WebDriver driver) {
		if (!getAndroidVersion(driver).split("\\.")[0].contains("6"))
			return; // Check for Android M

		String permList = " android.permission.CAMERA android.permission.READ_CONTACTS"
				+ " android.permission.ACCESS_FINE_LOCATION" + " android.permission.READ_PHONE_STATE"
				+ " android.permission.SEND_SMS android.permission.RECEIVE_SMS";

		String permCmd = "adb -s " + getExecutionDeviceId(driver) + " shell pm grant " + BaseTest1.packageName
				+ permList;

		try {
			Runtime.getRuntime().exec(permCmd);
		} catch (Exception e) {
			com.libraries.Log.info("Warning : Runtime Permissions not granted");
		}
	}

	public static void androidM(WebDriver driver) // DeviceSpecific
	{
		try // Does not work . As soon as Update Service is stopped , It restarts again
			// resulting in unmanageable popup.
		{
			if (getExecutionDeviceId(driver).contains("ZY"))
				Runtime.getRuntime().exec("adb shell am force-stop com.motorola.ccc.ota");
		} catch (Exception e) {
			System.err.println("Unable to stop Moto update service ");
		} catch (Error e) {
			System.err.println("Unable to stop Moto update service ");
		}
	}

	/**
	 * Retrieves the device screen resolution from adb shell.
	 *
	 * @return String the ScreenResolution
	 */
	/*
	 * public static String getScreenResolution() { String
	 * res="default",cmd="adb shell dumpsys window";
	 * 
	 * if(multipleDevices) cmd="adb -s "+executionDeviceId+" shell dumpsys window";
	 * 
	 * try { String cmdOutput=execCmd(cmd);
	 * 
	 * if(!cmdOutput.toLowerCase().contains("error:"))
	 * res=execCmd(cmd).split("init=")[1].split("\\s")[0];
	 * 
	 * Log.info("======== Obtaining device screen resolution : "+res+" ========"); }
	 * catch(Exception e) {
	 * com.libraries.Log.info("==== Could not obtain screen resolution ===="+e.
	 * getMessage()); res="default"; } return res; }
	 */

	/**
	 * Retrieves the device screen resolution from driver
	 *
	 * @return String the ScreenResolution
	 */
	public static String getScreenResolution(WebDriver driver) {
		String res = "";
		try {
			res = driver.manage().window().getSize().width + "X" + driver.manage().window().getSize().height;
		} catch (Exception e) {
			com.libraries.Log.info("==== Unable to obtain Screen resolution ====\n" + e.getMessage());
		}
		return res;
	}

	/**
	 * Retrieves the App Version
	 *
	 * @return String the App Version
	 */
	public static String getAppVersion(WebDriver driver) {
		String ver = "";

		String cmd = "adb -s " + getExecutionDeviceId(driver) + " shell \"dumpsys package "
				+ getPropValues("PACKAGENAME", BaseTest1.configPath) + " | grep versionName\"";

		try {
			ver = execCmd(cmd).split("=")[1].split("\n")[0].substring(0, 9);
		} catch (Exception e) {
			com.libraries.Log.info("~ Unable to obtain App version ~\n" + e.getMessage());
		}

		return ver;
	}

	/**
	 * Retrieves the App Version from adb shell. Returns the Base Version if app
	 * updates are installed.
	 *
	 * @return String the App Version
	 */
	public static String getAppVersion(String udid, String pkgName) {
		String ver = "DefaultVersion" + udid;
		String cmd = "adb -s " + udid + " shell dumpsys package " + pkgName + " | grep versionName";
		String cmdOutput;

		try {
			cmdOutput = execProcess(cmd);
			ver = cmdOutput.split("=")[cmdOutput.split("=").length - 1]; // return the base version from output
		} catch (Exception e) {
			com.libraries.Log.info("~ Unable to obtain App version ~\n" + e.getMessage());
		}

		return ver;
	}

	/**
	 * Executes a command line command and returns the output as a String
	 *
	 * @return String the command output
	 */
	public static String execCmd(String cmd) // throws java.io.IOException
	{
		try {
			java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream())
					.useDelimiter("\\A");

			String cmdOutput = s.hasNext() ? s.next() : "";
			s.close();
			return cmdOutput; // Implement close logic by storing in separate string before returning
		} catch (Exception e) {
			System.err.println("-- Unable to execute command " + cmd + " --" + e.getMessage());
			return "";
		}

	}

	public static String execProcess(String processCmd) {
		String output = "noRes";

		try {
			ProcessBuilder pb = new ProcessBuilder(processCmd.split(" "));
			output = IOUtils.toString(pb.start().getInputStream());
		} catch (Exception e) {
		}
		return output;
	}

	/**
	 * Checks whether an Alert is present or not and handles the Alert. Extra alerts
	 * are generated while using ChromeDriver
	 *
	 */
	public static void checkAlert(WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			Log.info("==== Handlng optional alert : " + alert.getText() + "====");
			alert.accept();
		} catch (Exception e) {
			Log.info("==== Optional Alert not found ====");
		}
	}

	/**
	 * Executes a javascript click
	 * 
	 * @param driver
	 * @param element to be clicked
	 */
	public static void javascriptClick(WebDriver driver, WebElement element) {
		System.out.println("Executing JS Click ");
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click()", element);
	}

	/**
	 * Checks for the occurrence of the given element
	 * 
	 * @param driver
	 * @param element
	 * @param timeoutSeconds
	 * @return true if the element occurs
	 * @see driver.getPageSource()
	 */
	public static boolean checkElementOccurenceOverrideImplicit(WebDriver driver, WebElement element,
			int timeoutSeconds) {
		try {
			driver.manage().timeouts().implicitlyWait(timeoutSeconds, TimeUnit.SECONDS); // Use 0 on observation

			WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
			wait.until(ExpectedConditions.visibilityOf(element));

			return true;
		} catch (Exception e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}

	/**
	 * 
	 * @param driver
	 * @param xp
	 * @param timeoutSeconds
	 * @return
	 */
	public static boolean checkElementOccurenceOnXpath(WebDriver driver, String xp, int timeoutSeconds) {
		boolean occurence;
		try {
			driver.manage().timeouts().implicitlyWait(timeoutSeconds, TimeUnit.SECONDS); // Use 0 or 1 on observation

			occurence = driver.findElements(By.xpath(xp)).size() > 0;

			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			return occurence;
		} catch (Exception e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}

	public static boolean checkElementOccurence(WebDriver driver, WebElement element, int timeoutSeconds,
			String locatorAttribute) {
		boolean occurence = false;

		System.out.println("~--- Checking Element Occurence ---~");

		// locatorAttribute as "resourceId" or "name"
		driver.manage().timeouts().implicitlyWait(timeoutSeconds, TimeUnit.SECONDS);
		try {
			if (Generic.containsIgnoreCase(locatorAttribute, "Id"))
				occurence = driver.findElements(By.id(element.getAttribute("resourceId"))).size() > 0;

			if (Generic.containsIgnoreCase(locatorAttribute, "Name"))
				occurence = driver.findElements(By.name(element.getAttribute("contentDescription"))).size() > 0;

			return occurence;
		} catch (Exception e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}

	public static String regInsensitive(String text) {
		return "(?i:" + text + ")";
	}

	/**
	 * To be used wherever sendKeys cannot be used . UiAuto2 limited TxtField
	 * workaround for Android 5
	 * 
	 * @param driver
	 * @param element
	 * @param text
	 * @see ((AppiumDriver)driver).getKeyboard().sendKeys(keysToSend);
	 */
	public static void setValue(WebDriver driver, WebElement element, String text) {

		Log.info("======== Executing setValue : " + text + " ========");
		if (isIos(driver)) {
			System.out.println("Setting value attribute ");
			IOSElement iElement = (IOSElement) element;
			iElement.setValue(text);
		} else // Android
		{
			element.click();
			try {
				new ProcessBuilder(
						new String[] { "adb", "-s", getExecutionDeviceId(driver), "shell", "input", "text", text })
								.redirectErrorStream(true).start();
			} catch (Exception e) {
				e.printStackTrace();
			} // UiAuto2 for Android 5 workaround
		}

		if (true)
			return;

		// ===============================//

		text = text.replace(" ", "%s").trim(); // Handle adb whitespace
		// ((IOSDriver<WebElement>)driver).getKeyboard().sendKeys("38");

		try {
			element.click();
			new ProcessBuilder(
					new String[] { "adb", "-s", getExecutionDeviceId(driver), "shell", "input", "text", text })
							.redirectErrorStream(true).start();

		} catch (Exception e) {
			Log.info("== Set val delay ==" + e.toString());
			element.sendKeys(text);
		}

		// MobileElement setValue() + adb click n clear //

		// ((MobileElement)element).setValue(text);

		/*
		 * for(int i=1;i<=text.split(" ").length;i++) { if(i!=1)
		 * adriver.pressKeyCode(62); // KEYCODE_SPACE
		 * 
		 * try { Runtime.getRuntime().exec(sendKeysCmd).waitFor(); } catch(Exception e)
		 * { Log.info("== Unable to enter text using adb =="); } }
		 */

		if (true)
			return;

		if (text.contains(" ")) {
			element.sendKeys(text);
			return;
		}

		element.click();
		AndroidDriver adriver = (AndroidDriver) ((WrapsDriver) element).getWrappedDriver();

		String sendKeysCmd = "adb -s " + getExecutionDeviceId(adriver) + " shell input text " + text;

		System.out.println("Driver used : " + adriver);
		System.out.println("cmd : " + sendKeysCmd);

		try {
			Runtime.getRuntime().exec(sendKeysCmd).waitFor();
		} catch (Exception e) {
			Log.info("== Unable to enter text using adb ==");
		}

		System.out
				.println("Element attribute + text " + element.getAttribute("contentDescription") + element.getText());
		// if((element.getAttribute("name")+element.getText()).contains(text)) return;

		// Log.info("== Attempting to enter using Appium sendKeys ==");
		// element.sendKeys(text);
	}

	/**
	 * Currently used for IOS only. Clicks on the co-ordinates of the element
	 * 
	 * Can be updated to use MobilElement.getCenter().x & y;
	 */
	public static void coOrdinateClick(WebDriver driver, WebElement element) {
		if (!isIos(driver))
			return;

		int x = 0, y = 0;

		try {
			Point coOrdinates = element.getLocation();
			x = coOrdinates.x;
			y = coOrdinates.y;

			IOSDriver<WebElement> iDriver = (IOSDriver<WebElement>) driver;
			TouchAction touchAction = new TouchAction(iDriver);

			System.out.println("Clicking on Co-ordinates : " + x + ' ' + y);
			touchAction.press(PointOption.point(x, y)).release().perform();

		} catch (Exception e) {
			Log.info("Error in Co-ordinate click on " + x + " " + y + e.getMessage());
		}
	}
	
	/**
	 * 
	 * 
	 * @param appName for Android , bundleId for IOS
	 */
	public static void closeExternalApp(WebDriver driver,String appName)
	{
		Log.info("======== Closing "+appName +" ========");
		String ytCloseBtnXpath="//*[contains(@content-desc,'Dismiss appName')]"
													.replace("appName", appName);
		
		((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
		Generic.wait(1); // Wait for open recents
		
		driver.findElement(By.xpath(ytCloseBtnXpath)).click();
		Generic.wait(1); // Wait for close 
		
		Log.info("======== Switching to previously opened app ========");
		((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.APP_SWITCH)); 
		
	}

	/**
	 * executes a keyboard sendKeys on the previously focused element
	 * 
	 * @param driver
	 * @param text
	 */
	public static void keyboardSendKeys(WebDriver driver, String text) {

		Log.info("======== Executing Keyboard sendKeys : " + text + " ========");

		if (isAndroid(driver))
			((AndroidDriver<WebElement>) driver).getKeyboard().sendKeys(text);
		else
			((IOSDriver<WebElement>) driver).getKeyboard().sendKeys(text);

	}

	public static void clearVal(WebDriver driver, WebElement element) {
		AndroidDriver adriver = (AndroidDriver) driver;

		element.click();
		element.clear();

		// adriver.pressKeyCode(123); //KEYCODE_MOVE_END

		// for(int i=0;i<20;i++)
		// adriver.pressKeyCode(67); //KEYCODE_DEL
	}

	public static void customSkip(WebDriver driver, String skipMsg) {
		Log.skip(skipMsg);

		new BaseTest1().setSkipStatus(driver, true);
	}

	public static void groupExecutePass(String testName) {
		Log.groupPass(testName);
	}

	/**
	 * 
	 * Row & Column numbers start from 0
	 * 
	 * @param csvVal
	 * @param rowNo
	 * @param colNo
	 * @param filePath
	 * 
	 */
	public static void writeCSV(String csvVal, int rowNo, int colNo, String filePath) {
		Log.info("======== Writing " + csvVal + " to Row " + rowNo + " and Col " + colNo + " at " + filePath
				+ " ========");

		List<String[]> csvEntries;

		try {
			CSVReader reader = new CSVReader(new FileReader(filePath));
			csvEntries = reader.readAll();

			csvEntries.get(rowNo)[colNo] = csvVal;

			CSVWriter writer = new CSVWriter(new FileWriter(filePath));
			writer.writeAll(csvEntries, false);

			// for(String[] stringArray:csvEntries)
			// writer.writeNext(stringArray);

			reader.close();
			writer.close();

		} catch (Exception e) {
			Assert.fail("Error reading CSV from " + filePath + " \n" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Clears the given directory
	 * 
	 * @param Relative pathof the directory
	 */
	public static void cleanDir(String relativePath) {
		if (!relativePath.startsWith("/"))
			relativePath = '/' + relativePath;

		File dir_to_clean = new File(BaseTest1.path + relativePath);
		System.out.println("Absolute Path of directory : " + dir_to_clean);

		try {
			FileUtils.cleanDirectory(dir_to_clean);
		} catch (IOException e) {
			System.err.println("Unable to clear directory at " + dir_to_clean);
			e.printStackTrace();
		}

	}

	public static void waitForDownload(File f) {
		long length1, length2;
		int timeout = 60, count = 0;

		System.out.println("Waiting for Download .." + f.getAbsolutePath());

		do {
			length1 = f.length(); // check file size
			wait(5);
			length2 = f.length(); // check file size again

			if (count++ == timeout)
				break;

		} while (length2 != length1);

	}

	public static void Zip(String sourceDirPath, String zipFilePath) throws IOException {
		Path p = Files.createFile(Paths.get(zipFilePath));
		try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
			Path pp = Paths.get(sourceDirPath);

			Files.walk(pp).filter(path -> !Files.isDirectory(path)).forEach(path -> {
				ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
				try {
					zs.putNextEntry(zipEntry);
					Files.copy(path, zs);
					zs.closeEntry();
				} catch (IOException e) {
					System.err.println(e);
				}
			});
		}
	}

}
