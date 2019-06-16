package library;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.libraries.Log;

/**
 * The Class HandleLoginPopup is a Thread which is used to handle all popups occuring in the Login Page.
 */
public class HandleLoginPopup extends Thread 
{
	
	/** The checker Element used to check whether a popup has occurred or not. */
	static WebElement checker;	
	
	/** The checker xpath. */
	private static String checkerXp;
	
	/** The driver. */
	private static WebDriver driver; 	
	
	/** The handled static variable used to communicate to the Thread to stop. */
	public static boolean handled=false;
	
	/**
	 * Instantiates a new HandleLoginPopup used to handle all popups occuring in the Login Page.
	 *
	 * @param driver the driver
	 */
	public HandleLoginPopup(WebDriver driver)
	{
		this.driver=driver;			
			
	}
	
	/**
	 * run() method of the HandleLoginPopup Thread. 
	 */
	public void run()
	{	
		int count=0;
		do
		{
 			count++;
			handleLoginPopups();
		}
		while(count<=2 && !handled);		
		Log.info("-------- Login paralell execution complete --------");
	}
	
	/**
	 * handleLoginPopups executed under the run() method
	 * Handles any popup with an OK button which occurs before entering the secure pin.
	 */
	public static void handleLoginPopups()
	{
		checkerXp="//android.widget.Button[contains(@text,'OK') or contains(@text,'Ok') or contains(@text,'ok')]"
				+ "| //android.widget.TextView[contains(@resource-id,'title_text')]";				
		
		Generic.wait(1);
		
		try
		{
			Generic.quicken(driver,5);
			checker=driver.findElements(By.xpath(checkerXp)).get(0);
			
			if (checker.getText().toLowerCase().contains("ok") && checker.getAttribute("resourceId").contains("button2")) //prepaid popup			
				{
					checker.click();
					handled=true;
				}
		}		
		catch(Exception e){}			
		finally
		{
			Generic.normalize(driver,40);
		}
		
	}

}
