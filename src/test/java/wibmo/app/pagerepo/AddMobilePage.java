package wibmo.app.pagerepo;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import library.Generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import wibmo.app.testScripts.BaseTest1;

import library.Log;

/**
 * The Class AddMobilePage used to add new mobile number to the existing number.
 */
public class AddMobilePage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The manage mobile close button. */
	@FindBy(id="menu_close")
	private WebElement manageMobileCloseButton;
	
	/** The mobile no textfield. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="main_mobile_edit")
	private WebElement mobileNoTextfield;
	
	/** The ask mvc button. */
	@iOSXCUITFindBy(iOSNsPredicate="name='Add' and visible=true")
	@AndroidFindBy(id="main_askmvc_button")
	private WebElement askMvcButton;
	
	/** The mvc textfield. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="main_mbOtp_edit")
	private WebElement  mvcTextfield;
	
	/** The add verify button. */
	@iOSXCUITFindBy(accessibility="Continue")
	@AndroidFindBy(id="main_btnSave")
	private WebElement addVerifyButton;	
	
	/** The add checker. */
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'verified' or value contains 'already'")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@resource-id,'message') or contains(@text,'Manage')]")
	private WebElement addChecker;
	
	
	/**
	 * Instantiates a new AddMobile page.
	 *
	 * @param driver the driver
	 */
	public AddMobilePage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Adds a mobile number to the existing loginId after entering the MVC
	 * The new mobile number should not be registered or associated previously to any number.
	 * @param mobileNo the mobile no
	 */
	public void addMobile(String mobileNo)
	{
		Log.info("======== Entering mobile no. to be added : "+mobileNo+" ========");
		mobileNoTextfield.sendKeys(mobileNo);
		
		Log.info("======== Clicking on Ask MVC button ========");
		askMvcButton.click();
		
		Log.info("======== Retreiving MVC from DB and entering ========");
		mvcTextfield.sendKeys(Generic.getOTP(mobileNo, BaseTest1.bankCode, Generic.getPropValues("VERIFYNEWMOBILE",BaseTest1.configPath)));
		
		if(Generic.isAndroid(driver))
			Generic.hideKeyBoard(driver);
		
		Log.info("======== Clicking on Add button ========");
		addVerifyButton.click();
		
		new WebDriverWait(driver,15).until(ExpectedConditions.visibilityOf(addChecker));
		String checkText=addChecker.getText();
		
		if(checkText.contains("already"))
			Assert.fail(checkText+" , check new mobile number passed : "+mobileNo+'\n');
		
		if(Generic.isIos(driver))
			okBtn.click();			// Handle Success alert
		else
			Generic.wait(10); // wait for mobile number list to be updated
	}
	
	

}
