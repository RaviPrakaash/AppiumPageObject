package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
//import com.libraries.ExcelLibrary;
import library.Log;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;
import wibmo.app.testScripts.MobileRecharge.BaseTest;

/**
 * The Class MobileRechargePayPage used to select the operator and enter the recharge amount.
 */
public class MobileRechargePayPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The phone number field. */
	@FindBy(id="value_phone_number")
	private WebElement phoneNumberField;
	
	/** The connection type list button. */
	@FindBy(id="phoneTypeSpinner")
	private WebElement connectionTypeList;
	
	/** The connection type previously selected. */
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'paid') and contains(@resource-id,'text1')]")
	private WebElement preSelectedConnection;
	
	/** The operator list button. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[2]")
	@AndroidFindBy(id="operatorSpinner")
	private WebElement operatorListButton;
	
	/** The operator list elements. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and visible=true")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'text1')]") 
	private List<WebElement> operatorListElements;
	
	/** The amount text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[-1]")
	@AndroidFindBy(id="input_amount")
	private WebElement amountTextField;		
	
	/** The pay button. */
	@iOSXCUITFindBy(accessibility="Pay")
	@AndroidFindBy(id="main_btn_pay")
	private WebElement payButton;
	
	/** The pre selected operator element. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[2]")
	@AndroidFindBy(xpath="(//android.widget.TextView[contains(@resource-id,'text1')])[2]") // second dropdown is operator dropdown
	private WebElement selectedOperatorElement;
	
	@iOSXCUITFindBy(iOSNsPredicate="name='Ok' or name contains 'Note:'")
	private List<WebElement> operatorAlertChk;
	
		
	/**
	 * Instantiates a new MobileRechargePayPage.
	 *
	 * @param driver the driver
	 */
	public MobileRechargePayPage(WebDriver driver)
	{
		//super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Verifies whether the number selected from contacts based on conatctName, matches the expected number present in testData.
	 *
	 * @param data the data
	 */
	public void verifyContact(String data)
	{
		String contactNo = data.split(",")[4];
		Log.info("======== Verifying contact number "+ contactNo+" ========" );
		
		try 
		{
			Assert.assertTrue(phoneNumberField.getText().equals(contactNo),"Contact number does not match");
		} catch (Exception e) 
		{
			Assert.fail("Contact number not selected correctly\n"+e.getMessage());
		}
	}
	
	/**
	 * Verifies the acceptance of 10 digit mobile number.
	 */
	public void verify10DigitMobileNumber()
	{
		Log.info("======== Verifying 10 digit mobile number acceptance ========");
		try 
		{
			Assert.assertTrue(payButton.isDisplayed());
		} catch (Exception e) 
		{
			Assert.fail("10 digit valid mobile number not accepted");
		}
	}
	
	/**
	 * Selects operator and enters recharge amount after verifying the connection type(prepaid/postpaid) previously selected .
	 *
	 * @param data the data
	 */
	public void enteringAmountToRecharge(String data)
	{
		//String data = ExcelLibrary.getExcelData("./excel_lib/TestData.xls","MobileRecharge",rowNum,2); 
		
		String[] values = data.split(",");
		int i=3;
		if(!values[3].toLowerCase().contains("paid")) i--;  // set index as per the test cases with 'select contact' and 'enter mobile number'
		
		String connectionType=values[i++],mobileNumber=values[i++],operator = values[i++], amt=values[i++];		
		
		if(Generic.isAndroid(driver))
		{	
			Log.info("======== Checking pre selected connection type : "+connectionType+" ========");
			String previouslySelectedConnectionType=preSelectedConnection.getText();
			Assert.assertTrue(previouslySelectedConnectionType.equalsIgnoreCase(connectionType),"Previously selected connection not selected by default . "+previouslySelectedConnectionType+" found\n");
			
			Log.info("======== Selecting Operator "+ operator+" ========"); 
			
			if(!selectedOperatorElement.getText().equals(operator))
			{
				selectedOperatorElement.click();			
				try 
				{
					Generic.scroll(driver,operator).click();				
				}	 
				catch (Exception e) 
				{
					Assert.fail(" Operator not found,Operator name and case should match exactly\n"+e.getMessage() );
				}			
			}
			
		}
		else // IOS
		{
			Log.info("======== Selecting Operator "+ operator+" ========"); 
			
			if(!handleOperatorAlert() && !selectedOperatorElement.getText().equals(operator)) // if operator is not selected already
			{
					Generic.tap(driver, selectedOperatorElement);
					if(operator.charAt(0)>='V') 
						Generic.swipeToBottom(driver);
					driver.findElement(MobileBy.AccessibilityId(operator)).click();
			}
			else	
			{
				Log.info("======== Operator "+operator +" already selected ========");
			}
			
			Generic.tap(driver, amountTextField); // Handle visible attribute 
		}				
		
		Log.info("======== Entering Amount for recharge : "+amt+" ========");
		amountTextField.sendKeys(amt);
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Clicking on Pay button ========");
		payButton.click();		
	}
	
	/**
	 * Verifies the operator list.
	 */
	public void verifyOperatorList()
	{
		ArrayList<String> fullOperatorList = new ArrayList<String>(Arrays.asList("--Select--","Airtel", "Uninor", "MTS", "Jio", "Vodafone", "Tata Docomo", "Aircel", "Tata Indicom", "Reliance CDMA", "Tata Indicom", "Reliance CDMA", "BSNL", "Tata CDMA", "Videocon", "MTNL", "Reliance GSM", "Idea","Cancel"));
		String listElement;	
		
		handleOperatorAlert();
		
		operatorListButton.click();		
		
		ArrayList<String> operatorList=new ArrayList<String>();
		for(WebElement e:operatorListElements)
		{
			listElement=Generic.isAndroid(driver)?e.getText():Generic.getAttribute(e, "name");
			operatorList.add(listElement);					
		}
		Log.info("======== Verifying default operators "+operatorList+" ========");
		
		Assert.assertTrue(fullOperatorList.containsAll(operatorList),groupExecuteFailMsg()+"One or more operators missing from list");			
	}
	
	public void closeOperatorList()
	{
		if(Generic.isAndroid(driver))  
			driver.navigate().back();
		else			
			((IOSDriver<WebElement>)driver).findElementByAccessibilityId("Cancel").click(); 
	}
	
	
	/**
	 * Verifies operator select.
	 */
	public void verifyOperatorSelect()
	{
		operatorListButton.click();	
		int index=new Random().nextInt(operatorListElements.size());
		
		Log.info("======== Selecting an Operator ========");
		
		String selectedOperator=operatorListElements.get(index).getText();
		operatorListElements.get(index).click();
		
		Generic.wait(3);
			
		Log.info("======== Verifying selected operator : "+selectedOperator+" ========");	
		Assert.assertTrue(selectedOperatorElement.getText().equals(selectedOperator), groupExecuteFailMsg()+"Operator not selected correctly"); // No need fro try/catch since we remain in the same page		
	}
	
	/**
	 *  IOS Only , Alert displayed equivalent of  Android Toast message
	 *  
	 */
	public boolean handleOperatorAlert()
	{
		for(int i=0;i<operatorAlertChk.size();i++)
			if(Generic.getAttribute(operatorAlertChk.get(i), "name").contains("Ok"))
				{
					Log.info("======== Handling operator alert ========");
					operatorAlertChk.get(i).click();
					return true;
				}
		return false;
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
