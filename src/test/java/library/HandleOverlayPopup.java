package library;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.libraries.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class HandleOverlayPopup is a Thread which is executed in paralell and is used to handle the overlay popup(Got It) occurring in any page.
 * 
 */
public class HandleOverlayPopup extends Thread 
{
	
	/** The checker Element used to check whether a popup has occurred or not. */
	static WebElement checker;	
	
	/** The checker xpath. */
	private static String checkerXp;
	
	/** The driver. */
	private static WebDriver driver;	
	
	/** The handled. */
	public static boolean handled=false;
	
	/**
	 * Instantiates a new HandleOverlayPopup Thread.
	 *
	 * @param driver the driver
	 */
	public HandleOverlayPopup(WebDriver driver)
	{
		this.driver=driver;				
	}
	
	/**
	 * run() method of HandleOverlayPopup Thread. 
	 */
	public void run()
	{	
		int count=0;
		Log.info("======== Overlay handled status : "+handled+" ========");
		do
		{	
			if(handled) 
			{
				Log.info("-------- Stopping Overlay Thread --------");	
				return;
			}
			count++;
			handleGotIt();		// Cannot use static handled				
		}
		while(count<=2);
		Log.info("======== Got it Paralell execution complete ========");
	}
	
	/**
	 * handleGotIt() executed under the run() method of HandleOverlayPopup thread
	 * Handles any overlay popup with a Got it button.
	 * 
	 */
	public static void handleGotIt()
	{
		checkerXp="//*[contains(@resource-id,'got_it') or contains(@resource-id,'title_text') or contains(@resource-id,'approve_textbutton')]";	
		Generic.wait(1);
		
		try
		{
			checker=driver.findElements(By.xpath(checkerXp)).get(0);
			
			if(checker.getAttribute("resourceId").contains("got_it"))
			{	
				
					
				Log.info("======== Handling Got it popup ========");
				checker.click();
				//handled=true;				
				Generic.wait(1);
				return;
			}			
		}		
		catch(Exception e)
		{
			// Used to Communicate Thread Status in Logs. 
			//if(count%2==0)
			//Log.info("======== No popup or Page detected ====\n"/*+e.getMessage()*/);			
		}
		
	}

}
