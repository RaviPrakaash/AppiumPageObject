package library;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import wibmo.app.testScripts.SendMoney.BaseTest;

import com.libraries.Log;

import io.appium.java_client.android.AndroidDriver;

/**
 * The Class HandleHomePopup is a thread which executes in parallel during login.
 * This thread will check and handle all system/program popups occurring between Login Page and Home Page 
 * It wont handle popups from other apps and push notifications. 
 *  
 */
public class HandleHomePopup extends Thread 
{
	/** The checker element used to check whether a popup has occurred or not . */
	static WebElement checker;	
	
	/** The checker xpath. */
	private static String checkerXp;
	
	/** The driver. */
	private WebDriver driver; // static
	
	/** The handled static variable used to communicate to the Thread to stop. */
	public static boolean handled=false;
	
	/** The deviceHandled used to map deviceUdid<=>boolean . If udid<=>true then popup is handled for the device with udid */
	public static HashMap<String , Boolean> deviceHandled=new HashMap<String, Boolean>();
	
	/**
	 * Instantiates a new HandleHomePopup used to handle all popups occuring in between LoginPage and HomePage.
	 *
	 * @param driver the driver
	 */
	public HandleHomePopup(WebDriver driver)
	{
		this.driver=driver;				
	}
	
	/**
	 * run() method of the HandleHomePopup Thread. 
	 */
	public void run()
	{	
		int count=0;
		do
		{
			if(count%10==0)
				{
					System.out.println("Home Handled Map : "+deviceHandled);
					Log.info("-------- Parallel Home Thread iteration at : "+count+" --------");
				}			
 			count++;
			handleHomePopups();
		}
		while(!getHandledStatus(driver)); //while(!handled);	// while(getHandledStatus(driver))	
		Log.info("-------- Parallel Home Thread iteration complete --------");		
	}
	
	/**
	 * handleHomePopups() executed under the run() method until the Home Page occurs.
	 * Handles the following popups : RateApp , PrepaidBalance , GotIt
	 * Thread stops executing when it encounters Home Page or Merchant Home Page   
	 */
	public void handleHomePopups()
	{
		String checkerText,checkerIdAttribute;
		String checkerXp="//android.widget.TextView[contains(@text,'Home') or contains(@text,'Dashboard') or contains(@text,'Unclaimed') or contains(@text,'Verify')] "
				+"| //android.widget.Button[contains(@text,'No') or contains(@text,'NO') "
				+ "or contains(@text,'OK') or contains(@text,'Ok') or contains(@resource-id,'got_it_button') "
				+ "or contains(@text,'Wibmo')]";	//or contains(@text,'ancel') or contains(@text,'ANCEL')			
		
		Generic.wait(3); // wait to prevent Home page from getting skipped
		
		try
		{
			//Generic.quicken(driver,5);
			
			//checker=driver.findElement(By.xpath(checkerXp));
			WebElement checker=driver.findElements(By.xpath(checkerXp)).get(0); 
			
			checkerText=checker.getText().toLowerCase();
			checkerIdAttribute=checker.getAttribute("resourceId").toLowerCase();	
			
			     // Verifiers // 
			//System.out.println("checkerText : "+checkerText);
			//System.out.println("checkerIdAttribute : "+checkerIdAttribute);			
			
			if (checkerText.contains("ok") && checkerIdAttribute.contains("button1")) 			
			{
				Log.info("======== Handling Pre paid popup ========");
				checker.click();	
			}
			
			/*if(checkerText.contains("cancel") && checkerIdAttribute.contains("button2")) 
			{
				Log.info("======== Handling Pre paid low balance popup ========");
				checker.click();
			}*/
			
			// Unclaimed funds page will be handled by Generic.verifyLogin();
			/*if(checkerText.contains("unclaimed"))
			{
				if(BaseTest.checkUnclaimed) // BaseTest of SendMoney module - Do not handle Unclaimed page if executing Unclaimed module
					{
						handled=true;
						return;  
					}
				Log.info("======== Handling Unclaimed ========");
				driver.findElement(By.name("Navigate up")).click();
				Generic.wait(2);
			}*/
			
			if(checkerIdAttribute.contains("got_it")) // Handle got it popup
			{
				Log.info("=== Handling Coach from Thread ===");
				driver.navigate().back();
				
				/*if(checkerText.contains("proceed"))
				{
					Log.info("======== Handling Proceed coach ========");
					driver.navigate().back();return;
				}
				
				Log.info("======== Handling Got it popup ========");				
				checker.click();*/
								//handled=true; 	// Got it and Rate App will  come together in QA 
				return;
			}
			
			if(checkerText.contains("no") && !checkerText.contains("now")) // Handle rate app popup, Dont click on Verify Now popup of Verify SIM
			{
				Log.info("======== Handling Rate App popup ========");				
				checker.click(); 
				handled=true;	setHandledStatus(driver, true);			
				return; 		// No Thanks is the last popup in Home Page 
			}	
			
			if(checkerText.contains("home") || checkerText.contains("wibmo") || checkerText.contains("dashboard") ) // Here Wibmo refers to Merchant App SDK
				if((checkerText.contains("home") || checkerText.contains("dashboard")) && checkerIdAttribute.contains("title")) // Home Page
				{
					
					Generic.wait(2); // Handle Delayed Coach , Handle Delayed Proceed 
					System.out.println(" -- Extra Coach Handling in Home Page--");
					String gotItXp="//*[contains(@text,'Got It') or contains(@resource-id,'title_text') or contains(@resource-id,'got_it_button')]";
					driver.findElement(By.xpath(gotItXp)).click();
					
					handled=true;
					setHandledStatus(driver, true);	
				}
				else
					driver.navigate().back(); // Ignore popup with 'Home' text
		}		
		catch(Exception e){}			
		finally
		{
			Generic.normalize(driver,30);
		}		
	}
	
	/**
	 * Returns the Alert/Popup handled status stored in the deviceHandled Map
	 * 
	 * @param driver 
	 */
	public boolean getHandledStatus(WebDriver driver)
	{		
		return deviceHandled.get(driverUdid(driver));
	}
	
	/**
	 * Sets the Alert/Popup handled status stored in the deviceHandled Map
	 * 
	 * @param driver 
	 */
	public static void setHandledStatus(WebDriver driver,boolean status)
	{
		if(!Generic.containsIgnoreCase(driver.toString(), "Android")) return;
		
		deviceHandled.put(driverUdid(driver),status);		
	}
	
	
	/**
	 * Returns the udid corresponding to the driver
	 * 
	 * @param driver 
	 * @return udid corresponding to the driver
	 */
	public static String driverUdid(WebDriver driver)
	{		
		if(!Generic.containsIgnoreCase(driver.toString(), "Android")) return "sdkDriver";
		
		return ((String)((AndroidDriver)driver).getCapabilities().getCapability("deviceName"));
	}

}
