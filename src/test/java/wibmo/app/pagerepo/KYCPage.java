package wibmo.app.pagerepo;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import library.CSR;
import library.DB;
import library.Generic;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import library.Log;

/**
 * The Class ProgramCardPage mainly used to view the Program/App generated wallet card and perform lock/unlock actions.
 */
public class KYCPage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The PAN number text field */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="panText")
	private WebElement panTxtField;
	
	/** The Username text field */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[-2]")
	@AndroidFindBy(id="name")
	private WebElement nameTxtField;
	
	/** The dob text field */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[-1]")
	@AndroidFindBy(id="main_dob_edit")
	private WebElement dobTxtField;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeOther[`visible=true`][-1]")
	private WebElement tncChkBox;
	
	/** The Terms & Conditions Checkbox . Need to Click on Co-ordinates*/
	@FindBy(id="term_condition_text")
	private WebElement tncText;
	
	/** The expiry details. */
	@iOSXCUITFindBy(accessibility="Continue")
	@AndroidFindBy(id="main_btnSubmit")
	private WebElement continueBtn;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'received your PAN'")
	private WebElement panReceivedAlertTitle;
	
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Your current wallet limit'")
	private WebElement panReceivedAlertMsg;
	
	@AndroidFindBy(id="main_dob_icon")
	private WebElement dobIcon;
	
	@AndroidFindBy(xpath="(//android.widget.Button)[last()]")
	private WebElement dobOkBtn;
		
	
/** 
	 * Instantiates a new ProgramCardPage.
	 *
	 * @param driver the driver
	 */
	public KYCPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);	}
	/**
	 *  Enters the PAN Details and clicks on Continue button.
	 * 
	 * @param panNumber
	 * @param name
	 * @param dob
	 */
	public void enterPANDetails(String panNumber,String name,String dob)
	{
		Log.info("======== Entering PAN number : "+panNumber+" ========");
		
		Generic.setValue(driver, panTxtField, panNumber); 
		Generic.wait(1);
		if(panTxtField.getText().length()!=10)
		{
			panTxtField.clear();
			Generic.setValue(driver, panTxtField, panNumber); 
		}
		
		Log.info("======== Entering Name : "+name+" ========");
		nameTxtField.sendKeys(name);
		
		Log.info("======== Entering DOB  : "+dob+" ========");
		if(Generic.isAndroid(driver))
		{
			dobIcon.click();
			Generic.wait(1);
			dobOkBtn.click();			
		}
		else
			selectDateIos(dobTxtField, dob);
		
		Log.info("======== Clicking on T&C Checkbox  ========");
		if(Generic.isAndroid(driver)) // Android
		{
			nameTxtField.click();		
			Generic.wait(1);
			((AndroidDriver)driver).pressKeyCode(61); // KEYCODE_TAB
			Generic.wait(1);
			((AndroidDriver)driver).pressKeyCode(61); // KEYCODE_TAB   // Replace this with advanced co-ordinate within dimension tap/click
			Generic.wait(1);
			
			if(Generic.isEmulator(driver))
				((AndroidDriver)driver).pressKeyCode(61); 
			
			((AndroidDriver)driver).pressKeyCode(66); // KEYCODE_ENTER
			Generic.hideKeyBoard(driver);
		}
		else 		// IOS
			tncChkBox.click();				
		
		Generic.hideKeyBoard(driver);
		
		waitOnProgressBarId(45);
		
		Log.info("======== Clicking on Continue  button ========");
		continueBtn.click();		
	}
	
	
	public void verifyPANSuccessAlert()
	{
		String msg;
		
		try 
		{
			okBtn.isDisplayed();
			Log.info("======== Verifying PAN received Success message : "+(msg=Generic.isAndroid(driver)?getText(alertMessage):(getText(panReceivedAlertTitle)+getText(panReceivedAlertMsg)))+" ========");
			Assert.assertTrue(msg.contains("We've received your PAN!") && msg.contains("current wallet limit") , "Invalid Success message \n");			
			okBtn.click();			
			
			if(Generic.isIos(driver)) // Navigate Back from Settings Page to HomePage
				navigateLink.click();
		}
		catch(Exception e)
		{
			Assert.fail("PAN Success Alert not found "+e.getMessage());
		}
		
	}
	
	
	public void createPANFile(String loginId,String panNumber,String panFilePath,String validationStatus) 
	{
		 String pcAcNumber=DB.getPCACNumber(loginId);
		
		String panFileEntry=pcAcNumber+'|'+loginId+'|'+panNumber+"||"+validationStatus;
		
		Path path=new File(panFilePath).toPath();
		
		try {
		
			List<String> fileContent = new ArrayList<>(Files.readAllLines(path));	
			
			for (int i = 0; i < fileContent.size(); i++) 
				{
				    if (fileContent.get(i).startsWith(("IN")))  // Modify File Entry which has the user details
				    {
				        fileContent.set(i, panFileEntry);
				        break;
				    }
				}
			
			String newFileContent=String.join("\n", fileContent);
			
			Log.info("======== Writing "+newFileContent+" to "+panFilePath+" ========");		
			Files.write(path, newFileContent.getBytes(Charset.forName("UTF-8"))); 
		
		} catch(Exception e) {Assert.fail("Error in Creating PAN file with "+panFileEntry+'\n'+e.getMessage());e.printStackTrace();} 
		
	}
	
	
	
	
	public void selectDateIos(WebElement dateField,String date)
	{
		String dd=date.substring(0, 2),
				month=Generic.getMonthName(Integer.parseInt(date.substring(2,4))),
				yyyy=date.substring(4);
		
		dateField.click();
		
		Log.info("======== Selecting "+dd+' '+month+' '+' '+yyyy+" ========");
		
		dobPicker.get(2).sendKeys(yyyy); 
		dobPicker.get(1).sendKeys(month);
		dobPicker.get(0).sendKeys(dd);
		
		Generic.hideKeyBoard(driver);
	}
	
}
