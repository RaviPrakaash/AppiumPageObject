package wibmo.webapp.pagerepo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class DVCFetchPage used to fetch the DVC and MVC for the given mobile number.
 */
public class DVCFetchPage {

	
	/** The driver. */
	public WebDriver driver;
	
	/**
	 * Instantiates a new DVCFetchPage.
	 *
	 * @param driver2 the driver 2
	 */
	//public WebDriver driver2;
	public  DVCFetchPage (WebDriver driver2)
	{
		this.driver=driver2;
		PageFactory.initElements(driver2,this);		
	}	
	
	/**
	 * Fetches the MVC otp based on the mobile number.
	 *
	 * @param mobnumber the mobnumber
	 * @return the string
	 */
	public String fetchotp(String mobnumber)
	{
		driver=new ChromeDriver();
		driver.get("https://wallet.pc.enstage-sas.com/sampleMerchant/getOtp.jsp?accessData="+mobnumber+"&programId=6019&eventId=4");		
		String otp=driver.findElement(By.xpath("//body")).getText();	
		driver.close();		
		return (otp);
		
	}
	
	/**
	 * Fetch DVC otp based on the mobile number.
	 *
	 * @param mobnumber the mobnumber
	 * @return the string
	 */
	public String fetchdvcotp(String mobnumber)
	{
		//System.out.println(mobnumber);
		
		driver=new ChromeDriver();		
		driver.get("https://wallet.pc.enstage-sas.com/sampleMerchant/getOtp.jsp?accessData="+mobnumber+"&programId=6019&eventId=5");		
		String otp=driver.findElement(By.xpath("//body")).getText();	
		driver.quit();	
		return (otp);
	}
}
