package wibmo.webapp.pagerepo;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.libraries.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class OTPFetchPage used to fetch the DVC and MVC for the given mobile number.
 */
public class OTPFetchPage {
	
	/** The driver. */
	public WebDriver driver;
	
	/** The driver 2. */
	public WebDriver driver2;
	
	/**
	 * Instantiates a new OTPFetchPage
	 *
	 * @param driver1 the driver 1
	 */
	public  OTPFetchPage (WebDriver driver1)
	{
		this.driver=driver1;
		PageFactory.initElements(driver1,this);		
	}
	
	/**
	 * Fetches the MVC otp based on the mobile number.
	 *
	 * @param mobnumber the mobnumber
	 * @return the OTP
	 */
	public String fetchotp(String mobnumber)
	{		
		Log.info("==== get mvc of mobile number "+ mobnumber + "====");
		driver=new ChromeDriver();
		driver.get("https://wallet.pc.enstage-sas.com/sampleMerchant/getOtp.jsp?accessData="+mobnumber+"&programId=6019&eventId=4");		
		String otp=driver.findElement(By.xpath("//body")).getText();	
		driver.close();		
		return (otp);		
	}
	
	/**
	 * Fetch DVC otp based on the mobile number..
	 *
	 * @param mobnumber the mobileNumber
	 * @return the OTP
	 */
	public String fetchdvcotp(String mobnumber)
	{	
		Log.info("==== get dvc of mobile number" + mobnumber + " ====");
		driver=new ChromeDriver();		
		driver.get("https://wallet.pc.enstage-sas.com/sampleMerchant/getOtp.jsp?accessData="+mobnumber+"&programId=6019&eventId=5");		
		String otp=driver.findElement(By.xpath("//body")).getText();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.quit();	
		return (otp);
	}

	
}
