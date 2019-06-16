package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import library.Generic;
import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * The Class RechargePage mainly used to select and navigate to Mobile , DTH , DataCard recharges.
 */
public class RechargePage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The recent records/quick repeat link. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Quick')]")
	private WebElement recentLink;
	
	/** The mobile recharge icon. */
	@iOSXCUITFindBy(accessibility="Mobile")
	@AndroidFindBy(id="image_mobile")
	private WebElement mobileRechargeIcon;
	
	/** The datacard recharge icon. */
	@iOSXCUITFindBy(accessibility="Data Card")
	@AndroidFindBy(id="image_datacard")
	private WebElement datacardIcon;
	
	/** The dth recharge icon. */
	@iOSXCUITFindBy(accessibility="DTH")
	@AndroidFindBy(id="image_dth")
	private WebElement dthIcon;		
	
	@FindBy(xpath="//*[@text='New' or contains(@resource-id,'got_it_button')]")  
	private WebElement gotItChecker;
	
	
	/**
	 * Instantiates a new RechargePage.
	 *
	 * @param driver the driver
	 */
	public RechargePage(WebDriver driver)
	{
		super(driver); 
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}


	/**
	 * Navigates to Mobile Recharge page.
	 */
	public void gotoMobileRecharge()
	{
		handleGotItCoach();
		Log.info("======== Clicking on Mobile Recharge Icon ========");
		mobileRechargeIcon.click();
	}
	
	/**
	 * Navigates to data card Recharge page.
	 */
	public void gotoDataCardRecharge()
	{
		handleGotItCoach();
		Log.info("======== Clicking on DataCard Recharge Icon ========");
		datacardIcon.click();		
	}
	
	/**
	 * Navigates to DTH recharge page.
	 */
	public void gotoDthRecharge()
	{
		handleGotItCoach();
		Log.info("======== Clicking on DTH Recharge Icon ========");
		dthIcon.click();
	}
	
	/**
	 * Navigates to recent records/quick repeat page.
	 */
	public void gotoRecentRecords()
	{
		handleGotItCoach();
		Log.info("======== Clicking on Quick Repeat ========");
		recentLink.click();
	}
	
	public void gotoAddSend()
	{
		handleGotItCoach();
		Log.info("======== Navigating to Add/Send page ========");
		if(Generic.isAndroid(driver))
		{
			navigateLink.click();
			Generic.scroll(driver, "/ Send").click();
		}
		else
		{
			moreOptions.click();
			Generic.swipeToBottom(driver);
			addSendLink.click();			
		}
	}
	/**
	 * Verifies the presence of recharge page.
	 */
	public void verifyRechargePage()
	{
		handleGotItCoach();
		Log.info("======== Verifying Recharge Page ========");
		try 
		{
			Assert.assertTrue(dthIcon.isDisplayed());
		} 
		catch (Exception e) 
		{
			Assert.fail("Recharge Page not displayed \n"+e.getMessage());
		}
	}
	
	/**
	 * Handles the coach message.
	 */
	public void handleGotItCoach()
	{
		if(Generic.isIos(driver)) return;
		
		try {gotItChecker.click();}catch(Exception e){Log.info("== Coach delay in Recharge Page =="+e.getMessage());}		
	}
}
