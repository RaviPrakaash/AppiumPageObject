package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Point;
//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import library.Log;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
//import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.offset.PointOption;
import library.Generic;
import wibmo.app.testScripts.MobileRecharge.BaseTest;

/**
 * The Class MobileRechargePage used to select connection type and enter/select the Mobile Number.
 */
public class MobileRechargePage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	SoftAssert softAssert;

	/** The prepaid  button. */
	@iOSXCUITFindBy(accessibility="Prepaid")
	@AndroidFindBy(id="rc_ph_type_pre")
	private WebElement prepaidRadioBtn;

	/** The postpaid  button. */
	@iOSXCUITFindBy(accessibility="Postpaid")
	@AndroidFindBy(id="rc_ph_type_post")
	private WebElement postpaidRadioBtn;

	/** The my numbers dropdown button. */
	@iOSXCUITFindBy(iOSNsPredicate="name beginswith 'My Number'")
	@AndroidFindBy(id="myNumberSpinner")
	private WebElement myNumbersButton;

	/** The my numbers list. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and visible=true")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'text1')]") 
	private List<WebElement> myNumbersList;

	/** The select contacts icon. */
	@iOSXCUITFindBy(accessibility="Select from Contacts")
	@AndroidFindBy(id="pick_from_contacts")
	private WebElement selectContactsIcon;

	/** The mobile number text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="main_mobile")
	private WebElement mobileNumberTextField;	
	
	@iOSXCUITFindBy(iOSNsPredicate="name beginswith 'Please enter'")
	private WebElement alertMsg;
	
	@iOSXCUITFindBy(className="XCUIElementTypeSearchField")
	private WebElement searchContactField;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeStaticText[-1]")
	private WebElement contactInList;

	/**
	 * Instantiates a new MobileRechargePage.
	 *
	 * @param driver the driver
	 */
	public MobileRechargePage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		softAssert=new SoftAssert();
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}

	/**
	 * Verifies whether the numbers present in the number list is same as the List of numbers obtained from Manage Mobile page.
	 *
	 * @param manageMobileNumbers the manage mobile numbers
	 */
	public void verifyMyNumbers(List manageMobileNumbers)
	{
		ArrayList<String> myNumbers=new ArrayList<String>();

		myNumbersButton.click();
		Generic.wait(1);
		
		for(WebElement e:myNumbersList)
			myNumbers.add(e.getText());

		Log.info("======== Verifying My Numbers ========");
		Log.info("======== "+myNumbers+" ========");
		Log.info("======== Manage Mobile Numbers List :" + manageMobileNumbers+" ========");
		
		Assert.assertTrue(myNumbers.containsAll(manageMobileNumbers));	
		
		if(Generic.isIos(driver)) // Close list 
			driver.findElement(MobileBy.AccessibilityId("Cancel")).click(); 
		else  
		{
			Generic.navigateBack(driver);
			Generic.navigateBack(driver); 
		}
			
	}
	
	

	/**
	 * Selects the connection type (prepaid/postpaid) and also the contact from Phone contacts after clicking on the Select contacts button.
	 *
	 * @param data the data
	 */
	public void selectContact(String data)
	{		
		String contactName = data.split(",")[2],connectionType=data.split(",")[3];
		boolean found=true;
		
		Log.info("======== Selecting connection type :"+connectionType+" ========");
		
		if(connectionType.equalsIgnoreCase("Prepaid"))		
			prepaidRadioBtn.click();
		else 		
			postpaidRadioBtn.click();
		
		Log.info("========= Selecting Contact : "+contactName+ "========");
		selectContactsIcon.click();		
		try{Thread.sleep(2000);}catch(InterruptedException e){}	
		try
		{
			if(Generic.isAndroid(driver))
				Generic.scroll(driver,contactName).click();
			else
			{
								
				Generic.coOrdinateClick(driver, searchContactField);				
				Generic.keyboardSendKeys(driver,contactName);
				
				Generic.coOrdinateClick(driver, contactInList);		
				
				Generic.wait(3); // Wait for Contact Page from Phone
				
				Generic.coOrdinateClick(driver, contactInList);		
		
			}
		}
		catch(Exception e)
		{
			found=false;
			softAssert.fail(contactName+" Contact not found, please add contact or check contact name \n"+e.getMessage());				
			softAssert.assertAll();			
		}
		finally
		{
			if(!found && Generic.isAndroid(driver))
				driver.navigate().back(); 			// Activity handling			
		}
	}

	/**
	 * Verifies the non acceptance of  mobile number less than 10 digits which is selected from contacts.
	 */
	public void verifyLessThan10DigitContactNumber()
	{
		String msg;
		Log.info("======== Verifying less than 10 digit contact number ========");;
		// Verify whether we are in the same page or not by checking for the Select Contacts icon
		try 
		{
			if(Generic.isAndroid(driver))
				Assert.assertTrue(selectContactsIcon.isDisplayed(),groupExecuteFailMsg()+"Contact number less than 10 digits may have been accepted\n");
			else
			{
				
				okBtn.isDisplayed();
				msg=alertMsg.getText();
				Log.info("======== Validating alert message : "+msg+ "========");
				Assert.assertTrue(msg.contains("valid number"), "Wrong message displayed \n");
				okBtn.click();
				
				navigateLink.click();
				new RechargePage(driver).gotoMobileRecharge();
				
			}
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+"Error during validation of less than 10 digit contact number\n"+e.getMessage());
		}		
	}	
	
	/**
	 * Selects the connection type (prepaid/postpaid) and enters the Mobile Number.
	 *
	 * @param data the data
	 */
	public void enterMobileNumber(String data)
	{
		//String data =ExcelLibrary.getExcelData("./excel_lib/TestData.xls","MobileRecharge",rowNum,2);
		String[] values = data.split(",");
		int i=2;
		String connectionType=values[i++],mobileNumber=values[i++];

		Log.info("======== Selecting Connection type  "+connectionType+" ========");
		if(connectionType.equalsIgnoreCase("Prepaid"))
			prepaidRadioBtn.click();
		else		
			postpaidRadioBtn.click(); 
		
		Log.info("======== Entering mobile no. "+mobileNumber+" ========");
		try
		{
			//mobileNumberTextField.sendKeys(mobileNumber);	 							// Goes to the next page , no need for hideKeyboard()
			Generic.setValue(driver, mobileNumberTextField, mobileNumber);	   // Handle UiAuto2 		
			if(Generic.isIos(driver)) Generic.hideKeyBoard(driver);
			
		}catch(Exception e){Generic.scroll(driver, "Enter mobile");mobileNumberTextField.sendKeys(mobileNumber);} // scroll and enter if field is not within the screen
	}
	
	/**
	 * Verifies the non acceptance of  mobile number less than 10 digits.
	 */
	public void verifyLessThan10DigitMobileNumber()
	{
		if(Generic.isIos(driver))
			{verifyLessThan10DigitContactNumber(); return;}
		
		Log.info("======== Verifying less than 10 digit number ========");
		try 
		{
			Assert.assertTrue(mobileNumberTextField.isDisplayed(),groupExecuteFailMsg()+"Mobile Number less than 10 digits may have been accepted\n");			
		}
		catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+"Mobile Number less than 10 digits may have been accepted"+e.getMessage());
		}
	}
	
	/**
	 * Returns the TestCaseID and Scenario if the current TestCase is under Group execution 
	 * 
	 * @return String TestCaseID and Scenario if under Group execution
	 */
	public String groupExecuteFailMsg()
	{
		if(BaseTest.groupExecute) 		
			return "\nFailed under Group Execution "+BaseTest.groupTestID+" : "+BaseTest.groupTestScenario+"\n";	
		else
			return "";
	}
	
	/**
	 *  IOS Only 
	 */
	public void closeList()
	{
		
	}


}
