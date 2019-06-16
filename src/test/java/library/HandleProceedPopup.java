package library;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

//import com.libraries.Log;
import library.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class HandleProceedPopup.
 */
public class HandleProceedPopup extends Thread 
{
	
	/** The checker. */
	static WebElement checker;	
	
	/** The checker xp. */
	private static String checkerXp;
	
	/** The driver. */
	private static WebDriver driver;
	
	/** The main thread. */
	public static Thread mainThread;
	
	/** The handled. */
	public static boolean handled;
	
	/** The count. */
	public static int count;
	
	/**
	 * Instantiates a new handle proceed popup.
	 *
	 * @param driver the driver
	 */
	public HandleProceedPopup(WebDriver driver)
	{
		this.driver=driver;				
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run()
	{	
		int count=0;
		do
		{			
			count++;
			handleProceed();
			if(handled) {library.Log.info("-------- Popup handled --------"); return;}
		}
		while(count<2);
		Log.info("======== Paralell execution complete ========");
	}
	
	/**
	 * Handle Proceed popup if it appears.
	 */
	public static void handleProceed()
	{
		checkerXp="//*[contains(@resource-id,'button1') or contains(@resource-id,'title_text') or contains(@resource-id,'approve_textbutton') or contains(@content-desc,'XXXX')]";// | //android.widget.TextView[contains(@resource-id,'title_text')]";	
		Generic.wait(1);
		
		try
		{
			//checker=driver.findElements(By.xpath(checkerXp)).get(0);
			WebElement checker=driver.findElement(By.xpath(checkerXp));
			if(checker.getAttribute("resourceId").contains("button1"))
			{
				if(Generic.containsIgnoreCase(checker.getText(), "yes"))
					return; // Ignore Card Authentication popup
					
				library.Log.info("======== Handling Proceed ========");				
				checker.click();
				//Generic.doubleTapEnter(driver);
				
				//driver.findElement(By.name("Proceed")).click();
				//driver.findElement(By.xpath("//android.widget.Button[2]"));
				//--------------------------------------------------------------------// // Handle default Cancel 
				/*String buttonXp="//android.widget.Button[contains(@resource-id,'button_load_money') or contains(@resource-id,'continue_btn')]";
				if(driver.findElement(By.xpath(buttonXp)).getAttribute("resourceId").contains("button_load_money"))
					driver.findElement(By.xpath(buttonXp)).click();*/
				//--------------------------------------------------------------------//
				//handled=true;
				Generic.wait(1);
			}			
		}		
		catch(Exception e)
		{
			//if(count%2==0)
			library.Log.info("-------- No popup or Page detected , Checker: "+checker+"--------\n"+e.getMessage());			
		}
		
	}
}
