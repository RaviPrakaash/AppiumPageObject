package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import library.Generic;
import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
//import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import wibmo.app.testScripts.DataCard_Recharge.BaseTest;

/**
 * The Class DataCardRechargePage used to select connection type and enter/select the DataCard Number.
 */
public class DataCardRechargePage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	SoftAssert softAssert;
	
	/** The prepaid radio button. */
	@iOSXCUITFindBy(accessibility="Prepaid")
	@AndroidFindBy(id="rc_ph_type_pre")
	private WebElement prepaidRadioBtn;

	/** The postpaid radio button. */
	@iOSXCUITFindBy(accessibility="Postpaid")
	@AndroidFindBy(id="rc_ph_type_post")
	private WebElement postpaidRadioBtn;

	/** The my numbers dropdown button. */
	@FindBy(id="myNumberSpinner")
	private WebElement myNumbersButton;

	/** The my numbers List. */
	@FindBy(xpath="//*[contains(@resource-id,'text1')]") 
	private List<WebElement> myNumbersList;

	/** The select from contacts icon. */
	@FindBy(id="pick_from_contacts")
	private WebElement selectContactsIcon;

	/** The mobile number text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="main_mobile")
	private WebElement mobileNumberTextField;	

	/** The data card page title. */
	@FindBy(id="title_text")
	private WebElement dataCardPageTitle;

	/**
	 * Instantiates a new DataCardRechargePage.
	 *
	 * @param driver the driver
	 */
	public DataCardRechargePage(WebDriver driver)
	{
		// super(driver);
		this.driver=driver;
		softAssert=new SoftAssert();
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);	}

	/**
	 * Verifies whether the numbers present in the number list is same as the List of numbers obtained from Manage Mobile page.
	 *
	 * @param manageMobileNumbers the manage mobile numbers
	 */
	public void verifyMyNumbers(List manageMobileNumbers)
	{
		ArrayList<String> myNumbers=new ArrayList<String>();

		myNumbersButton.click();

		for(WebElement e:myNumbersList)
			myNumbers.add(e.getText());

		Log.info("======== Verifying My Numbers ========");
		Assert.assertTrue(myNumbers.containsAll(manageMobileNumbers));

		Log.info("======== "+myNumbers+" ========");
		Log.info("======== Manage Mobile Numbers List :" + manageMobileNumbers+" ========");		
	}

	/**
	 * Selects the connection type (prepaid/postpaid) and also the contact from Phone contacts after clicking on the Select contacts button.
	 *   
	 *
	 * @param data the data
	 */
	public void selectContacts(String data)
	{
		WebElement contact;
		String connectionType= data.split(",")[2];
		String contactName = data.split(",")[3];
		boolean found=true;
		
		Log.info("======== Selecting Connection type : "+connectionType+" ========");
		if(connectionType.equalsIgnoreCase("Prepaid"))		
			prepaidRadioBtn.click();
		else 		
			postpaidRadioBtn.click();		
		
		Log.info("======== Selecting Contact : " +contactName+" ========");
		
		selectContactsIcon.click();
		
		Generic.wait(2);
		try
		{
			contact=Generic.scroll(driver,contactName);
			Generic.wait(3); // Wait for Inertia A7
			contact.click();
		}
		catch(Exception e)
		{
			found=false;
			Assert.fail(contactName+" Contact not found, please add contact or check contact name \n");								
		}
		finally
		{
			if(!found)
				driver.navigate().back(); // Handle Activity Clash 
		}
	}

	/**
	 * Verifies the non acceptance of  DataCard number less than 10 digits.
	 */
	public void verifyLessThan10DigitDataCardNumber()
	{		
		Log.info("======== Verifying less than 10 digit Data Card number ========");;
		// Verify whether we are in the same page or not by checking for the Select Contacts icon
		try 
		{
			Assert.assertTrue(selectContactsIcon.isDisplayed(),groupExecuteFailMsg()+"Less than 10 digit contact accepted\n");
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+"Less than 10 digit contact was accepted\n"+e.getMessage());
		}		
	}	
	
	/**
	 * Selects the connection type (prepaid/postpaid) and enters  the DataCard Number.
	 *
	 * @param data the data
	 */
	public void enterMobileNumber(String data)
	{
		//String data =ExcelLibrary.getExcelData("./excel_lib/TestData.xls","MobileRecharge",rowNum,2);
		String[] values = data.split(",");
		int i=2;
		String connection=values[i++],mobileNumber=values[i++];

		Log.info("======== Selecting Connection : "+connection+" ========");
		if(connection.equalsIgnoreCase("Prepaid"))
		{
			prepaidRadioBtn.click();
		}
		else 
		{
			postpaidRadioBtn.click();
		}

		Log.info("======== Entering mobile no. "+mobileNumber+" ========");
		Generic.setValue(driver, mobileNumberTextField, mobileNumber);
		//mobileNumberTextField.sendKeys(mobileNumber);		
		
		if(Generic.isIos(driver)) Generic.hideKeyBoard(driver);
		
	}
	
	
	/**
	 * Selects the post paid button.
	 */
	public void selectPostPaidButton()
	{
		postpaidRadioBtn.click();
	}
	
	/**
	 * Verifies whether 'Select from Contacts' icon is displayed or not.
	 */
	public void verifyContactPage()
	{
		Log.info("======== Verifying Page With Contact Icon ========");
		
		try 
		{
			Assert.assertTrue(selectContactsIcon.isDisplayed());
		} catch (Exception e) 
		{
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * Checks whether the current page is DataCard Recharge Page
	 * 
	 * @return true if the current page in DataCard Recharge Page
	 */
	public boolean checkDataCardRechargePage()
	{
		return Generic.checkElementOccurence(driver,mobileNumberTextField,3,"Id");				
	}
	
	/**
	 * Waits for 5 mins while preventing the Appium Server from timing out.
	 */
	public void waitFor5Mins()
	{
		for(int i=10;i>=1;i--)
		{			
			Log.info("========  "+ i/2.0 +" minutes left until Pay Now click ========");			
			try { Thread.sleep(30000);} catch (InterruptedException e) {}	
			dataCardPageTitle.getText(); // To prevent Appium timeout
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

}
