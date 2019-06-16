package wibmo.app.pagerepo;

import io.appium.java_client.MobileBy;
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
import org.testng.Assert;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import library.Log;

/**
 * The Class ManageProfilePage used to modify the user profile.
 */
public class ManageProfilePage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	private SoftAssert softAssert;

	/** The first name text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="main_fname_edit")
	private WebElement firstNameTextField;

	/** The middle name text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[2]")
	@AndroidFindBy(id="main_mname_edit")
	private WebElement middleNameTextField;

	/** The last name text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[3]")
	@AndroidFindBy(id="main_lname_edit")
	private WebElement lastNameTextField;

	/**  The select gender dropdown. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[4]")
	@AndroidFindBy(id="main_gender_spinner")
	private WebElement selectGender;
	
	/** The pre selected gender. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[4]")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'text1')]") 
	private WebElement selectedGender;

	/** The date of Birth text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[5]")
	@AndroidFindBy(id="main_dob_edit")
	private WebElement dobTextField;

	/** The profile update button. */
	@iOSXCUITFindBy(accessibility="Update")
	@AndroidFindBy(id="main_btnSave")
	private WebElement updateButton;
	
	@FindBy(xpath="//*[contains(@resource-id,'design_menu_item_text') or contains(@resource-id,'got_it') or contains(@resource-id,'main_hi_name_value')]")
	private WebElement updateBtnValidation;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains [ci]'Success'")
	private WebElement updateSuccessMsgTitle;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'updated'")
	private WebElement updateSuccessMsgTxt;
	
	@iOSXCUITFindBy(accessibility="Ok")
	private WebElement updateOkBtn;
	
	/**
	 * Instantiates a new ManageProfilePage.
	 *
	 * @param driver the driver
	 */
	public ManageProfilePage(WebDriver driver)
	{
		super(driver);
		this.driver= driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}

	/**
	 * Updates user profile by entering firstName, middleName, lastName, gender, dateOfBirth.
	 *
	 * @param data the data
	 */
	public void enterValuesToUpdateProfile(String data)
	{
		String[] values = data.split(",");

		int i = 2;
		String firstName = values[i++];
		String middleName = values[i++];
		String lastName = values[i++];
		String gender = values[i++];
		String dob = values[i++];
		
		String updateMsgSuccess="";

		Log.info("======== Enter First Name: "+firstName+" ========");
		if(!firstNameTextField.getText().equals(firstName))
		{
			firstNameTextField.clear();
			firstNameTextField.sendKeys(firstName);
		}

		Log.info("======== Enter Middle Name: "+middleName+" ========");
		if(!middleNameTextField.getText().equals(middleName))
		{
			middleNameTextField.clear();
			middleNameTextField.sendKeys(middleName);			
		}
		Generic.hideKeyBoard(driver);
		

		Log.info("======== Enter Last Name: "+lastName+" ========");
		if(!lastNameTextField.getText().equals(lastName))
		{
			lastNameTextField.clear();
			lastNameTextField.sendKeys(lastName);			
		}
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Select Gender: "+gender+" ========");
		if(!selectedGender.getText().equals(gender))
			{ 
				selectGender.click();				
				driver.findElement(MobileBy.AccessibilityId(gender)).click();
			}

		Log.info("======== Enter DOB: "+dob+" ========");
		if(Generic.isIos(driver))
			selectDateIos(dobTextField, dob);
		else
			dobTextField.sendKeys(dob);
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Clicking on Update button ========");
		updateButton.click();
		
		try
		{
			 Log.info("======== Validating Update ========"); 		
			 
			 if(Generic.isIos(driver))
			 {
				 
				 Log.info("======== Validating Update status : "+(updateMsgSuccess+=updateSuccessMsgTitle.getText())+' '+(updateMsgSuccess+=updateSuccessMsgTxt.getText()));
				 Assert.assertTrue(updateMsgSuccess.contains("Success"), "Update Status title incorrect");
				 Assert.assertTrue(updateMsgSuccess.contains("updated"), "Update Status message incorrect");
				 
				 Log.info("======== Clicking on Update OK Button ========");
				 updateOkBtn.click();				 
			 }
			 else // Android
			 {
				 waitOnProgressBarId(15);  
				 if(Generic.checkTextInPageSource(driver, "profile_img").contains("img"))
					 Assert.fail("Update Profile failed.");
				 Generic.navigateBack(driver);				 
			 }			
		}
		catch(Exception e)
		{
			Assert.fail("User Profile not updated successfully\n"+e.getMessage());
		}
	}
	
	/**
	 * Verifies the field values displayed in the Profile page against the given set of corresponding values obtained from TestData.
	 *
	 * @param data the data
	 */
	public void verifyProfileUpdate(String data)
	{
		softAssert = new SoftAssert();
		String[] values = data.split(",");

		int i = 2;
		String firstName = values[i++],fnameDisplayed;
		String middleName = values[i++],mnameDisplayed;
		String lastName = values[i++],lnameDisplayed;
		String gender = values[i++],gndrDisplayed;
		String dob = values[i++],dobDisplayed;
		
		waitOnProgressBarId(30);
		
		Log.info("======== Verify FirstName: "+(fnameDisplayed=firstNameTextField.getText())+" ========");
		softAssert.assertEquals(fnameDisplayed, firstName,"FirstName not updated correctly\n");
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Verify MiddleName: "+(mnameDisplayed=middleNameTextField.getText())+" ========");
		softAssert.assertEquals(mnameDisplayed, middleName,"MiddleName not updated correctly\n");		
		
		Log.info("======== Verify LastName: "+(lnameDisplayed=lastNameTextField.getText())+" ========");
		softAssert.assertEquals(lnameDisplayed, lastName,"LastName not updated correctly\n");
		
		Log.info("======== Verify Gender: "+(gndrDisplayed=selectedGender.getText())+" ========");
		softAssert.assertEquals(gndrDisplayed, gender,"Gender not updated correctly\n");
		
		Log.info("======== Verify DOB: "+(dobDisplayed=dobTextField.getText())+" ========");
		softAssert.assertEquals(dobDisplayed.replaceAll("/",""),dob,"Date Of Birth not updated correctly\n");
		
		softAssert.assertAll();
		
		if(Generic.isIos(driver))
			navigateLink.click(); // Navigate back Normalize IOS Navigation
	}
}
