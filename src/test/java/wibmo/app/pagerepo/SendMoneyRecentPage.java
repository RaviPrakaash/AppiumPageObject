package wibmo.app.pagerepo;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import library.Generic;
import library.Log;

/**
 * The Class SendMoneyRecentPage used to verify contact from phone to which money was sent recently.
 */
public class SendMoneyRecentPage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	@iOSXCUITFindBy(accessibility="Recent")
	private WebElement recentTab;
	
	/** The recent contact values List. */
	@iOSXCUITFindBy(className="XCUIElementTypeStaticText")
	@AndroidFindBy(id="history_phone_email")
	private List<WebElement> recentList;
	
	@FindBy(xpath="//*[contains(@resource-id,'smoothprogressbar') or contains(@text,'Recent')]") //android.widget.RelativeLayout)[1]/*") // 4=progressBar, 3=noProgressBar
	private List<WebElement> prgBarCheck;
	
	
	/**
	 * Instantiates a new SendMoneyRecentPage.
	 *
	 * @param driver the driver
	 */
	public SendMoneyRecentPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Verifies the presence of the given Contact value in the contact values List.
	 *
	 * @param contactValue the contact value
	 */
	public void verifyContactValue(String contactValue)
	{
		ArrayList<String> recentValues=new ArrayList<String>();
		
		if(Generic.isIos(driver))
			recentTab.click();
		
		waitOnProgressBarId(30);
		
		for(WebElement e:recentList)
			recentValues.add(e.getText());
		
		Log.info("======== Searching for "+contactValue+" in "+recentValues);		
		Assert.assertTrue(recentValues.contains(contactValue), contactValue+" not found in the list "+recentValues);		
		
	}
	
	
}
