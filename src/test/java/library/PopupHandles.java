package library;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.libraries.Log;

/**
 * The Class PopupHandles Thread will be used to handle popups specific to the production app. 
 */
public class PopupHandles extends Thread
{
	
	/** The checker. */
	static WebElement checker;	
	
	/** The popup handling thread. */
	private static Thread pthread;
	
	/** The checker xpath. */
	private static String checkerXp;
	
	/** The driver. */
	private static WebDriver driver;
	
	/**
	 * Instantiates a new PopupHandles Thread.
	 *
	 * @param driver the driver
	 */
	public PopupHandles(WebDriver driver)
	{
		this.driver=driver;
	}
	
	/**
	 * run() method of PopupHandles thread. 
	 *
	 * @param driver the driver
	 */
	public void run()
	{			
		do
		{
			handleOffers();
		}
		while(!PopupHandles.checker.getText().contains("Home"));
		Log.info("======== Paralell execution complete ========");
	}
	
	/**
	 * Handles Offer popups , is called by the run() method.  
	 */
	public static void handleOffers()
	{
		checkerXp="//android.widget.ImageButton[contains(@resource-id,'close_button')] | //android.widget.TextView[contains(@resource-id,'title_text')]";	
		
		Generic.wait(3);
		//System.out.println("handleOffers() Executing in paralell\n");
		try
		{
			checker=driver.findElement(By.xpath(checkerXp));
			
			if(checker.getAttribute("resourceId").contains("close_button"))
			{
				Log.info("======== Handling offer popup ========");
				driver.findElement(By.id("close_button")).click();
				Generic.wait(2);
			}
			/*else
			{
				System.out.println("Paralell execution going through "+checker.getText());
			}*/
		}		
		catch(Exception e)
		{
			Log.info("==== No Offer popup or Page detected ===="/*+e.getMessage()*/);			
		}
	}
}



