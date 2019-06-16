package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import library.Generic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * The Class ManageMobilePage used to add, delete, view and prioritize multiple numbers associated with a single user.
 */
public class ManageMobilePage extends BasePage
{	
	/** The driver. */
	private WebDriver driver;
	
	/** The list of mobile numbers in List<String> format will be instantiated from mobileNumberList. */
	private List<String> mobileNumbers;
	
	/** The list of mobile numbers in List<WebElement> format. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeCell/**/XCUIElementTypeStaticText")
	@AndroidFindBy(id="data")
	private List<WebElement> mobileNumberList;	
	
	/** The manage mobile close button. */
	@iOSXCUITFindBy(accessibility="Back")
	@AndroidFindBy(id="menu_close")
	private WebElement manageMobileCloseButton;
	
	/** The add mobile number button. */
	@iOSXCUITFindBy(accessibility="Add")
	@AndroidFindBy(id="menu_new")
	private WebElement addButton;
	
	/** The edit icon. */
	@FindBy(id="card_img_edit")
	private WebElement editIcon;
	
	/** The delete mobile button. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeButton[`name=\"IconDelete\"`][-1]")
	@AndroidFindBy(id="main_btnDelete")
	private WebElement deleteMobileButton;
	
	/** The mobile number delete confirmation yes button. */
	@iOSXCUITFindBy(accessibility="Yes")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'button1')]") 
	private WebElement deleteConfirmYesBtn;
	
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Success'")
	private WebElement deleteSuccessAlert;
	
	
	/**
	 * Instantiates a new ManageMobilePage.
	 *
	 * @param driver the driver
	 */
	public ManageMobilePage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Returns the mobile number list as ArrayList<String>.
	 *
	 * @return the array list
	 */
	public ArrayList<String> returnMobileNumberList()
	{
		Log.info("======== Reading mobile number list ========");
				
		ArrayList<String> mobileNumbers=new ArrayList<String>();		
		
		for(WebElement m :mobileNumberList)
			mobileNumbers.add(m.getText());		// This method used in MR_003 , update accordingly at script level		
		
		Log.info("======== Mobile Number list : "+mobileNumbers+" ========");
		return mobileNumbers;		
	}

	/**
	 * Navigates to the Add Mobile page to add a new mobile number.
	 */
	public void add()
	{
		Log.info("======== Clicking on Add button ========");
		addButton.click();		
	}
	
	/**
	 * Verifies the absence of the given number. 
	 * If the number is present , it deletes the number as an Add Mobile precondition.
	 *
	 * @param mobileNo the mobile no
	 */
	public void verifyNumberAbsent(String mobileNo)
	{
		ArrayList<String> mobileNumbers=returnMobileNumberList();
		
		Log.info("======== Verifying absence of "+mobileNo+" in the mobile number list :"+mobileNumbers+" ========");
		if(mobileNumbers.contains(mobileNo))
		{
			Log.info("======== Executing Precondition : Deleting "+mobileNo);
			deleteMobile(mobileNo);			
		}			
	}
	
	/**
	 * Verify number present.
	 *
	 * @param mobileNo the mobile no
	 */
	public void verifyNumberPresent(String mobileNo)
	{
		ArrayList<String> mobileNumbers=returnMobileNumberList();
		
		Log.info("======== Verifying presence of "+mobileNo+" in the mobile number list :"+mobileNumbers+" ========");
		if(!mobileNumbers.contains(mobileNo))
			Assert.fail(mobileNo+" was not successfully added \n");		
	}
	
	public void navigateBack()
	{
		if(Generic.isAndroid(driver))
			Generic.navigateBack(driver);
		else
			navigateLink.click();
	}
	
	/**
	 * Delete mobile.
	 *
	 * @param mobileNo the mobile no
	 */
	public void deleteMobile(String mobileNo)
	{
		String editXp="//*[contains(@text,'"+ mobileNo + "')]/..//*[contains(@resource-id,'card_img_edit')]";
		
		if(Generic.isAndroid(driver))
			driver.findElement(By.xpath(editXp)).click();
		
		deleteMobileButton.click();
		deleteConfirmYesBtn.click();	
		Generic.wait(2);
		
		if(Generic.isIos(driver))
		{
			try {
				Log.info("======== Verifying Number Delete Success : "+deleteSuccessAlert.getText()+" ========");
				okBtn.click();
			} catch(Exception e) {Assert.fail("Unalble to delete number");}
		}
		
	}

	/**
	 * Close manage mobile.
	 */
	public void closeManageMobile()
	{
		Log.info("======== Closing Manage mobile ========");
		manageMobileCloseButton.click();
	}	
	
}
