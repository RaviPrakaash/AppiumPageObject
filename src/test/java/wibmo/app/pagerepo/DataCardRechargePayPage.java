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
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;
import wibmo.app.testScripts.DataCard_Recharge.BaseTest;

/**
 * The Class DataCardRechargePayPage used to select the operator and enter the recharge amount.
 */
public class DataCardRechargePayPage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;

	/** The data card number field. */
	@iOSXCUITFindBy(iOSNsPredicate="value matches '[\\\\d]*'")
	@AndroidFindBy(id="value_phone_number")
	private WebElement dataCardNumberField;

	/** The operator . */
	@FindBy(xpath="(//*[contains(@resource-id,'text1')])[2]")  // operator dropdown changes from device to device
	private WebElement operatorDataCard;
	
	/** The recharge amount field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[-1]")
	@AndroidFindBy(id="input_amount")
	private WebElement amountDataCard;

	/** The pay now button. */
	@iOSXCUITFindBy(accessibility="Pay")
	@AndroidFindBy(id="main_btn_pay") 
	private WebElement payBtn;

	/** The cancel button. */
	@FindBy(xpath="//*[@text='Cancel']") 
	private WebElement cancelButton;
	
	/** The data card operator list. */
	@FindBy(id="android:id/text1")
	private List<WebElement> dataCardOperatorList;
	
	@iOSXCUITFindBy(iOSNsPredicate="name='Ok' or name='Pay'")
	private List<WebElement> operatorAlertChk;
	
	

	/**
	 * Instantiates a new DataCardRechargePayPage.
	 *
	 * @param driver the driver
	 */
	public DataCardRechargePayPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}

	/**
	 * Verifies whether the number selected from contacts based on conatctName, matches the expected number present in testData.
	 *
	 * @param data the data
	 */
	public void verifyDataCardNumber(String data)
	{
		String contactNo=data.split(",")[4];
		
		//Generic.waitForEmbeddedWebview(driver, 10);
		//Generic.tap3DS(driver);

		Log.info("======== Verifying Data Card number :"+contactNo+" ========" );
		try 
		{
			String dataCardNoDisplayed=dataCardNumberField.getText();
			Log.info("======== Verifying DataCard Number displayed :"+dataCardNoDisplayed+"========");
			Assert.assertTrue(dataCardNoDisplayed.equals(contactNo),groupExecuteFailMsg()+contactNo+" Contact number does not match");
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+"Contact No. does not match\n"+e.getMessage());
		}
	}

	/**
	 * Verifies the default data card operator in the operator list for the given number.
	 *
	 * @param data the data
	 */
	public void verifyDataCardOperator(String data)
	{
		String operator=data.split(",")[5];
		Log.info("======== Verifying Data Card operator :"+operator+" ========");

		try 
		{
			Assert.assertTrue(operatorDataCard.getText().equals(operator),groupExecuteFailMsg()+"Operator does not match");
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+"Operator does not match."+e.getMessage());
		}
	}

	/**
	 * Only the first ten digits should be accepted when entering a number greater than 10 digits.
	 *
	 * @param data the data
	 */
	public void verifyGreaterThan10DigitNumber(String data)
	{
		String mobileNumber = data.split(",")[3];

		Generic.wait(5);		
		Generic.tap3DS(driver);
		
		Log.info("======== Verifying for greater than 10 digit number : "+dataCardNumberField.getText()+ "========");
		try 
		{
			Assert.assertTrue(dataCardNumberField.getText().equals(mobileNumber.substring(0, 10)),"Mobile No Does not match");			
		}
		catch (Exception e) 
		{
			Assert.fail("Mobile No Does not match\n" +e.getMessage());
		}
	}
	
	/**
	 * Verifies the  data card operator list and checks the alphabetical order of Operator names.
	 */
	public void verifyDataCardOperatorList()
	{
		ArrayList<String> operators=new ArrayList<String>();
		ArrayList<String> sortedOperators=new ArrayList<String>();
		
		Log.info("======== Verifying Data Card operator list in alphabetical order ========");
		
		
		operatorDataCard.click();
		try {Thread.sleep(2000);} catch (InterruptedException e1){}
		
		for(int i=1;i<dataCardOperatorList.size();i++)		
			operators.add(dataCardOperatorList.get(i).getText());
		
		sortedOperators.addAll(operators);
		Collections.sort(sortedOperators);	
		
		Log.info("======== Operator list "+operators+" ========");
		
		if(operators.equals(sortedOperators))
			Log.info("Operator list in Alphabetical Order\n");		
		else
			Log.info("Operator list not in Alphabetical Order\n");		
		
			
	}
	
	/**
	 * Verifies Operator select.
	 */
	public void verifyOperatorSelect()
	{
		operatorDataCard.click();
		try { Thread.sleep(2000); } catch (InterruptedException e) {}
		int index=new Random().nextInt(dataCardOperatorList.size());
		if(index==0) index++;
		
		Log.info("======== Selecting an Operator ========");
		
		String selectedOperator=dataCardOperatorList.get(index).getText();
		dataCardOperatorList.get(index).click();
		
		try { Thread.sleep(2000); } catch (InterruptedException e) {}
			
		Log.info("======== Verifying selected operator : "+selectedOperator+" ========");
		Assert.assertTrue(operatorDataCard.getText().equals(selectedOperator), "Operator not selected correctly"); // No need fro try/catch since we remain in the same page		
	}
	
	/**
	 * Selects operator and enters recharge amount after verifying the DataCardNumber and Operator previously selected .
	 *
	 * @param data the data
	 */
	public void enterValues(String  data)
	{
		//String data = ExcelLibrary.getExcelData("./excel_lib/TestData.xls","DTHRecharge",rowNum,2);
		Generic.wait(2);
		
		String[] values = data.split(",");
		
		String dataCardNumber = values[4];
		String dataCardOperator = values[5];
		String dataCardRechargeAmnt= values[6];				
		String dataCardNumberDisplayed;
		
		if(Generic.isIos(driver)) // IOS
		{
			Log.info("======== Selecting Operator "+ dataCardOperator+" ========"); 
			if(!handleOperatorAlert() && !dataCardNumberField.getText().equals(dataCardOperator)) // if operator is not selected already
			{
				//	Generic.tap(driver, dataCardNumberField);
				dataCardNumberField.click();
					if(dataCardOperator.charAt(0)>='V') 
						Generic.swipeToBottom(driver);
					driver.findElement(MobileBy.AccessibilityId(dataCardOperator)).click();
			}
			else	
			{
				Log.info("======== Operator "+dataCardOperator +" already selected ========");
			}
			
			Log.info("======== Entering Amount for DataCard recharge : "+dataCardRechargeAmnt+" ========");
			
			Generic.tap(driver, amountDataCard); 			 // Handle visible attribute 
			if(!dataCardRechargeAmnt.contains("_")) 	// Change to Regex digit 
			{
				amountDataCard.sendKeys(dataCardRechargeAmnt);	
				Generic.hideKeyBoard(driver);
			}
		}
		// ================================================================ //
		else // Android
		{
		
		Log.info("======== Data Card Number displayed :"+(dataCardNumberDisplayed=dataCardNumberField.getText())+" ========");
		//dataCardNumberField.sendKeys(dataCardNumber);	// Number already present	
		Log.info("========  Verifying Data Card Number : "+dataCardNumber+" ========");
		Assert.assertTrue(dataCardNumberDisplayed.equals(dataCardNumber), "Data Card No. previously entered does not match\n");
		
			
		Generic.wait(1); //UiAuto2
		boolean operatorFound = operatorDataCard.getText().contains(dataCardOperator);
		Log.info("======== Checking for operator  "+dataCardOperator+" preselected? : "+operatorFound+ "========" );	
		
		if(!operatorFound)
		{
			Generic.wait(1); //UiAuto2
			operatorDataCard.click();
			Generic.wait(2);
			
			for (WebElement e : dataCardOperatorList) 		
				if (e.getText().toLowerCase().contains(dataCardOperator.toLowerCase()))
				{	
					Log.info("========= Selecting Operator: "+e.getText()+" ========");
					e.click();
					operatorFound=true;
					break;
				}
		}		
		if (operatorFound)
		{	
			Log.info("======== Entering amount : " +dataCardRechargeAmnt+" ========");
			if(!dataCardRechargeAmnt.contains("_")) // Change to Regex digit 
			{
				amountDataCard.sendKeys(dataCardRechargeAmnt);	
				Generic.hideKeyBoard(driver);
			}
		}
		else 
		{
			Log.info("======== Operator not visible , searching for Operator : "+dataCardOperator +" ========");
			
			try 
			{
				Generic.scroll(driver, dataCardOperator).click();
				Log.info("======== Entering amount : " +dataCardRechargeAmnt+" ========");
				amountDataCard.sendKeys(dataCardRechargeAmnt);
				Generic.hideKeyBoard(driver);
			} 
			catch (Exception e) 
			{
				Assert.fail("Operator not found check operator name \n"+e.getMessage());
			}			
		}
	}
	}
	
	/**
	 * Only the first 5 digits should be accepted when entering an amount greater than 5 digits.
	 */
	public void verifyGreaterThan5DigitAmt()
	{
		Log.info("======== Verifying 5 digit amount : "+amountDataCard.getText()+" ========");
		Assert.assertEquals(amountDataCard.getText().length(), 5,"Amount is not 5 digits\n");
	}

	/**
	 * Verify pay now button disable.
	 */
	public void verifyPayNowButtonDisable()
	{
		Generic.hideKeyBoard(driver);
		payBtn.click(); 		
		
		/*
		 *  payNowButton.isEnabled() returning true/false cannot be used 
		 *  since payNowButton attributes are inconsistent across different Android platforms		
		*/
		
		Log.info("======== Verifying Pay Now button disable ========");
		
		try
		{
			Generic.wait(4);
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
			new WebDriverWait(driver,15).until(ExpectedConditions.invisibilityOfElementLocated(By.name("Pay Now")));
		}
		catch(TimeoutException e)
		{
			Assert.fail(" Pay Now button was disabled \n");
		}
		
		Log.info("======== Pay Now button is enabled ========");
				
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
	 * Clicks on  Cancel button.
	 */
	public void clickCancel()
	{
		Log.info("======== Cancelling DataCard Recharge ========");
		//cancelButton.click(); 
		navigateLink.click();		
	}
	
	/**
	 * Navigates back from the page
	 */
	public void navigateBack()
	{
		Log.info("======== Navigating back from the page ========");
		navigateLink.click();
	}
	
	/**
	 *  IOS Only , Alert displayed equivalent of  Android Toast message
	 *  
	 */
	public boolean handleOperatorAlert()
	{
		for(int i=operatorAlertChk.size()-1;i>=0;i--)
			if(Generic.getAttribute(operatorAlertChk.get(i), "name").contains("Ok"))
				{
					Log.info("======== Handling operator alert ========");
					operatorAlertChk.get(i).click();
					return true;
				}
		return false;
	}
	
	/**
	 * Returns the TestCaseID and Scenario if the current TestCase is under Group execution .
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
