package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import library.Log;
import wibmo.app.testScripts.BaseTest1;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;

/**
 * The Class SettingsPage mainly used to navigate to different user profile attributes.
 */
public class SettingsPage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	private SoftAssert softAssert;

	/** The change pin button. */
	@iOSXCUITFindBy(accessibility="Change Secure PIN")
	@AndroidFindBy(id="button_changepassword")
	private WebElement changePinButton;

	/** The change secure pin button. */
	@FindBy(id="button_changepassword")
	private WebElement changeSecurePin;
	
	/** The change security Question button. */
	@iOSXCUITFindBy(accessibility="Change Security Q/A")
	@AndroidFindBy(id="button_changesecretquestion")
	private WebElement changeSecurityQButton;
	
	/** The manage mobile icon. */
	@FindBy(id="button_manage_mobile")
	private WebElement manageMobileIcon;
	
	
	@AndroidFindBy(id="button_verify_sim")
	private WebElement verifySimBtn;
	
	/** The manage profile button. */
	@iOSXCUITFindBy(accessibility="Manage Profile")
	@AndroidFindBy(id="button_manageprofile")
	private WebElement manageProfile;
	
	@iOSXCUITFindBy(accessibility="Manage Card Limits")
	@AndroidFindBy(id="button_card_limits")
	private WebElement mngCardLimitBtn;
	
	@FindBy(id="button_manage_bank_acc")
	private WebElement mngBeneficiaryAcctBtn;
	
	@iOSXCUITFindBy(accessibility="Update KYC")
	@AndroidFindBy(id="button_upload_kyc")
	private WebElement kycBtn;
	
	/** The user name text displayed under user information. */
	@FindBy(id="username_value_text")
	private WebElement userName;

	/** The add send link under Navigation panel. */
	@FindBy(xpath="//*[contains(@text,'/ Send')]") 
	private WebElement addSendNavigationLink;
	
	/** The beneficiary icon in settings page. */
	@FindBy(id="button_manage_bank_acc")
	private WebElement manageBankBtn;
	
	/**
	 * Instantiates a new SettingsPage.
	 *
	 * @param driver the driver
	 */
	public SettingsPage(WebDriver driver)
	{
		super(driver);
		this.driver= driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}	
	
	/**
	 * Navigates to change secure pin page.
	 */
	public void gotoChangeSecurePin()
	{
		changePinButton.click();
	}
	
	/**
	 *  Navigates to change security qa.
	 */
	public void gotoChangeSecurityQa()
	{
		changeSecurityQButton.click();		
	}
	
	/**
	 * Logs out from the Navigation Panel.
	 */
	public void logout1()
	{
		navigateLink.click();
		Generic.scroll(driver,"Logout");
		logoutLink.click();	
		logoutConfirmYesBtn.click(); 
	}
	
	/**
	 *  Navigates to the KYC page 
	 */
	public void gotoKYC()
	{
		Log.info("======== Clicking on KYC button ========");
		kycBtn.click();		
	}

	/**
	 * Navigates to Delete Account page.
	 * 
	 * @deprecated Functionality does not exist.
	 */
	public void gotoDeleteAccount()
	{
		Log.info("======== Clicking on Settings More ========");
		moreOptions.click();
		
		Log.info("======== Clicking on Delete Option ========");
		
		try 
		{
			//deleteAccountDropDown.click();
		} catch (Exception e)
		{
			// Repeat all the steps if settingsMore is not clicked. 
			driver.navigate().back();
			Generic.scroll(driver, "Settings");
			driver.findElement(By.id("image_setting")).click();
			moreOptions.click();
			//deleteAccountDropDown.click();			
		}
	}
	
	/**
	 * Verifies the presence of  Change Secure pin button.
	 */
	public void verifyChangeSecurePin()
	{
		Log.info("======== Verifying Change Secure Pin Button is Displayed ========");
		softAssert.assertTrue(changeSecurePin.isDisplayed());
	}
	
	
	/**
	 * Navigates to Manage Mobile page.
	 */
	public void gotoManageMobile()
	{			
			Generic.scroll(driver,"Terms"); // Scroll to the bottom
			Log.info("======== Clicking on Manage Mobile Icon ========");
			manageMobileIcon.click();	
	}
	
	public void gotoManageCardLimits()
	{
		if(Generic.isAndroid(driver))
			Generic.scroll(driver,"Terms");
		else
			Generic.swipeToBottom(driver);
		
		Log.info("======== Clicking on Manage Card Limits button ========");
		mngCardLimitBtn.click();
	}
	
	
	public void gotoBeneficiaries()
	{
		Generic.scroll(driver,"Terms");
		
		Log.info("======== Clicking on Manage Beneficiary Account button ========");
		mngBeneficiaryAcctBtn.click();
		
	}
	
	/**
	 * Navigates to Recharge page.
	 */
	public void gotoRecharge()
	{
		Log.info("======== Navigating to Recharge Page ========");
		manageProfile.isDisplayed(); // Prevent StaleElement for NavigateLink
				
		if(Generic.isAndroid(driver))
		{	
			navigateLink.click();
			Log.info("======== Scrolling to AddSend navigation link ========");
			Generic.scroll(driver, "Recharge").click();			
		}
		else
		{
			moreOptions.click();
			rechargeLink.click();
		}
	}
	
	/**
	 * Navigates to Manage Profile page.
	 */
	public void gotoManageProfile()
	{
		Log.info("======== Cliking on Manage Profile Button ========");
		manageProfile.click();
	}
	
	public void gotoVerifySim()
	{
		Log.info("======== Clicking on Verify SIM ========");
		Generic.scroll(driver, "Verify SIM"); 
	}
	
	/**
	 * Verifies the current userName displayed against the given name.
	 *
	 * @param name the name
	 */
	public void verifyUserName(String name)
	{
		Log.info("======== Verify UserName: "+userName.getText()+" ========");
		Assert.assertEquals(userName.getText(), name,"UserName Incorrect\n");
	}
	
	/**
	 * Navigates to Add Send page.
	 * 
	 */
	public void gotoAddSendPage() 
	{
		manageProfile.isDisplayed();	 // Prevent Stale
		
		Log.info("======== Navigating to Pay / Send page ========");
		
		if(Generic.isAndroid(driver))
		{	
			navigateLink.click();
			Log.info("======== Scrolling to AddSend navigation link ========");
			Generic.scroll(driver, "/ Send").click();			
		}
		else
		{
			moreOptions.click();
			Generic.swipeToBottom(driver);
			addSendLink.click();
		}
			
	}

	public void gotoProgramCard() 
	{
		String programName=Generic.getPropValues("PROGRAMNAME", BaseTest1.configPath);
		Log.info("======== Navigating to "+programName+" card page ========");
		navigateLink.click();
		
		Generic.scroll(driver, programName+" Card").click();
		
		//Log.info("======== Clicking on Payzapp Card Link ========");
		//payzappCardLink.click();
	}

}
