package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import library.Log;
import edu.emory.mathcs.backport.java.util.Collections;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;
import wibmo.app.testScripts.DTH_Recharge.BaseTest;

/**
 * The Class DTHRechargePage used to enter values for DTH recharge.
 */
public class DTHRechargePage 
{
	
	/** The driver. */
	private WebDriver driver;

	/** The subscriber ID text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="edtTxtSubscriberId")
	private WebElement subscriberIDTextField; //*[contains(@resource-id,'edtTxtSubscriberId') or contains(@class,'android.widget.EditText')]|//android.widget.EditText[1]

	/** The operator drop down. */
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Select'")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'text1')]") 
	private WebElement operatorDropDown;
	
	/** The operator selected. */
	@FindBy(xpath="//*[contains(@resource-id,'text1')]")   // operator dropdown element changes from device to device
	private WebElement operatorSelected;

	/** The recharge amount text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[-1]")
	@AndroidFindBy(id="input_amount")
	private WebElement amountTextField;

	/** The pay now button. */
	@iOSXCUITFindBy(accessibility="Pay Now")
	@AndroidFindBy(id="main_btn_pay")
	private WebElement payBtn;	

	/** The cancel button. */
	@FindBy(xpath="//*[contains(@text,'Cancel')]")
	private WebElement cancelButton;

	/** The dth operator list. */
	@FindBy(id="android:id/text1")
	private List<WebElement> dthOperatorList;
	
	@iOSXCUITFindBy(className="XCUIElementTypePickerWheel")
	private WebElement dthOperatorlist;

	/** The web view element. */
	@FindBy(className="android.webkit.WebView")
	private WebElement webView;
	
	/** The page title. */
	@FindBy(id="title_text")
	private WebElement dthPageTitle;

	/**
	 * Instantiates a new DTHRechargePage.
	 *
	 * @param driver the driver
	 */
	public DTHRechargePage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}

	/**
	 * Populates the fields for subscriberId and recharge amount after selecting the DTH operator.
	 * Only populates the values , does not click on Pay Now button.
	 *
	 * @param data the data
	 */
	public void enterValues(String  data)
	{
		//String data = ExcelLibrary.getExcelData("./excel_lib/TestData.xls","DTHRecharge",rowNum,2);
		
		String[] values = data.split(",");
		String subScriberId = values[2];
		String dthOperator = values[3];
		String dthRechargeAmnt= values[4];		
		boolean operatorFound = false;		
			
		//driver.findElement(By.xpath("//*[contains(@text,'Subscriber')]")).click();
		
		Log.info("======== Entering DTH SubscriberId :"+subScriberId+ " ========");
		
		subscriberIDTextField.sendKeys(subScriberId);
		Generic.hideKeyBoard(driver);
				
		operatorDropDown.click();
		Generic.wait(2);
		
		if(Generic.isIos(driver)) // IOS
		{
			dthOperatorlist.sendKeys(dthOperator);
			Generic.hideKeyBoard(driver);
			if(!dthRechargeAmnt.contains("_"))  // If Valid Amount then Enter 
			{
				amountTextField.clear();
				amountTextField.sendKeys(dthRechargeAmnt);	
				Generic.hideKeyBoard(driver);
			}	return;		
		}
			
		
		// ======== Adjust scroll view ======== //		
		if(Generic.containsIgnoreCase(dthOperator, "Tata"))
			for(int i=0;i<8;i++)
				((AndroidDriver)driver).pressKeyCode(20); // KEYCODE_DPAD_DOWN	
		// ==================================== //
		
		for (WebElement e : dthOperatorList) 		
			if (e.getText().toLowerCase().contains(dthOperator.toLowerCase()))
			{	
				Log.info("========= Selecting Operator: "+e.getText()+" ========");
				e.click();
				operatorFound=true;
				break;
			}	
		
		if (operatorFound)
		{	
			Log.info("======== Entering amount : " +dthRechargeAmnt+" ========");
			if(!dthRechargeAmnt.contains("_"))  // If Valid Amount then Enter 
			{
				amountTextField.clear();
				amountTextField.sendKeys(dthRechargeAmnt);	
				Generic.hideKeyBoard(driver);
			}
		}
		else 
		{
			try
			{	Log.info("======== Operator not found , searching operator in list ========");
				Generic.scroll(driver,dthOperator).click();
				
				amountTextField.clear();
				amountTextField.sendKeys(dthRechargeAmnt);	
				Generic.hideKeyBoard(driver);
			}
			catch(Exception e)
			{
				Assert.fail("Operator not found. Check operator name\n"+e.getMessage());
			}
		}
		
	}

	/**
	 * Clicks on Pay Now button.
	 */
	public void clickPayNow()
	{
		Log.info("======== Clicking on Pay Now button ========");
		payBtn.click();
	}
	
	/**
	 * Clicks on Cancel button.
	 */
	public void clickCancel()
	{
		Log.info("======== Clicking on Cancel button ========");
		//cancelButton.click();
		driver.navigate().back();
	}
	
	
	/**
	 * Verify pay now button disable.
	 */
	public void verifyPayNowButtonDisable()
	{
		payBtn.click();
		
		Log.info("======== Verifying Pay Now button disable ========");
		
		try
		{
			Assert.assertTrue(payBtn.isDisplayed(),"Pay Now button is not enabled");
		}
		catch(Exception e)
		{
			Assert.fail("Pay now button was enabled \n"+e.getMessage());
		}		
	}
	
	/**
	 * Verify pay now button enable.
	 */
	public void verifyPayNowButtonEnable()
	{
		payBtn.click();
		
		Log.info("======== Verifying Pay Now button enable, Please wait ========");
		
		try
		{
			new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(By.name("Pay Now")));
		}
		catch(TimeoutException e)
		{
			return;
		}		
		Assert.fail(" Pay Now button was disabled \n");		
	}
	
	/**
	 * Verifies DTH operator list and checks the alphabetical order of Operator names .
	 */
	public void verifyDTHOperatorList()
	{
		ArrayList<String> operators=new ArrayList<String>();
		ArrayList<String> sortedOperators=new ArrayList<String>();
		
		Log.info("======== Verifying DTH operator list in alphabetical order ========");		
		
		operatorDropDown.click();
		Generic.wait(2);
		
		for(int i=1;i<dthOperatorList.size();i++)		
			operators.add(dthOperatorList.get(i).getText());
		
		sortedOperators.addAll(operators);
		Collections.sort(sortedOperators);	
		
		Log.info("======== Operator list "+operators+" ========");
		
		if(!operators.equals(sortedOperators))
			Log.info("======== Operator List not in alphabetical order ========");
		//Assert.assertTrue(operators.equals(sortedOperators),groupExecuteFailMsg()+"Operator List not in alphabetical order \n");		
	}
	
	/**
	 * Verify operator select.
	 */
	public void verifyOperatorSelect()
	{
		operatorDropDown.click();
		Generic.wait(2);
		
		int index=new Random().nextInt(dthOperatorList.size());		
		if(index==0) index++;
		
		
		String selectedOperator=dthOperatorList.get(index).getText(); if(selectedOperator.contains("Tata")) return; // cannot validate border values
		Log.info("======== Selecting Operator : "+selectedOperator+"========");
		dthOperatorList.get(index).click();
		
		Generic.wait(2);
		String operatorDisplayed=operatorSelected.getText();
		Log.info("======== Verifying selected operator : "+operatorDisplayed+" ========");
		Assert.assertTrue(operatorDisplayed.equals(selectedOperator),groupExecuteFailMsg()+ "Operator not selected correctly"); // No need for try/catch since we remain in the same page		
	}
	
	/**
	 * Only the first 5 digits should be accepted when entering an amount greater than 5 digits.
	 *
	 * @param data the data
	 */
	public void verify5DigitAmount(String data)
	{
		String amt=data.split(",")[2];
		
		Log.info("======== Entering amount greater than 5 digits : "+amt+" ========");
		amountTextField.sendKeys(amt);
		
		Log.info("======== Verifying 5 digit amount entered : "+amountTextField.getAttribute("contentDescription") +" ========");
		
		try 
		{
			Assert.assertTrue(amountTextField.getText().equals((amt.substring(0, 5))),"Amount data mismatch \n");
		} 
		catch (Exception e) 
		{
			Assert.fail("Amount text field or value not found \n"+e.getMessage());
		}				
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
			dthPageTitle.getText(); // To prevent Appium timeout
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
