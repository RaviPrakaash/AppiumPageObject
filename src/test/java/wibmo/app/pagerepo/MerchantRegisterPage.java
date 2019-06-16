package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import library.Log;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import library.Generic;

/**
 * The Class MerchantRegisterPage.
 */
public class MerchantRegisterPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	SoftAssert softAssert;
	
	/** The login id text field. */
	@FindBy(xpath="//android.widget.EditText|//*[@id='input_0']") 
	private WebElement mobileNoTxtField; 

	/** The full name text field. */
	@FindBy(xpath="(//android.widget.EditText)[1]|//*[@id='input_2']") 
	private WebElement fullNameTextField;

	/** The card number text field. */
	@FindBy(xpath="(//android.widget.EditText)[2]|//*[@id='input_3']")
	private WebElement cardNumberTextField;

	/** The expiry month dropdown. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'Expiry Month')] |//android.widget.Spinner[contains(@content-desc,'Expiry Month')]|//*[@name='expriyDate']")  // (//android.widget.ListView)[1]
	private WebElement expMonthDropdown;

	/** The expiry year dropdown. */
	@FindBy( xpath="//android.widget.Button[contains(@content-desc,'Expiry Year')] |//android.widget.Spinner[contains(@content-desc,'Expiry Year')]|//*[@name='expriyYear']")  //(//android.widget.ListView)[2]
	private WebElement expYearDropdown;
	
	@FindBy( xpath="//android.widget.Button[contains(@content-desc,'DD')] |//android.widget.Spinner[contains(@content-desc,'DD')]")
	private WebElement ddSelect;
	
	@FindBy( xpath="//android.widget.Button[contains(@content-desc,'MM')] |//android.widget.Spinner[contains(@content-desc,'MM')]")
	private WebElement mmSelect;
	
	@FindBy( xpath="//android.widget.Button[contains(@content-desc,'YYYY')] |//android.widget.Spinner[contains(@content-desc,'YYYY')]")
	private WebElement yyyySelect;
	

	/** The email text field. */
	@FindBy(xpath="(//android.widget.EditText)[2]|//*[@id='input_1']") //  previously [3]
	private WebElement emailTextField;	

	/** The mobile number text field. */
	@FindBy(xpath="(//android.widget.EditText)[4]")
	private WebElement mobileNoTextField;

	
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'SKIP')]|//*[text()='Skip']") // Handle text() function in Android 
	private WebElement skipBtn;
	
	@FindBy(xpath="//*[contains(@content-desc,'Faster')]")
	private WebElement fasterCheckoutTxt;
	
	@FindBy(xpath="//*[contains(@content-desc,'Name') or @name='expriyDate']") 
	private WebElement regPageChk;
	
	@FindBy(xpath="(//android.widget.EditText)[3]")
	private WebElement wibmoPinPwdTxtField;

	/** The cancel button. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'Cancel')]")
	private WebElement cancelButton;

	/** The click here link used to navigate to Merchant Login Page. */
	@FindBy(xpath="//android.view.View[contains(@content-desc,'click here Link')]")
	private WebElement clickHere;

	/** The expiry year list. */
	@FindBy(xpath="//android.widget.CheckedTextView[contains(@resource-id,'text1')] | //android.widget.TextView[contains(@resource-id,'text1')]")
	private List<WebElement> expYearList;
	
	/** The dob text field. */
	@FindBy(xpath="//android.widget.EditText[contains(@content-desc,'Date of Birth (DD/MM/YYYY)')]")
	private WebElement dobTxtField;
	
	/** The secure pin text field. */
	@FindBy(xpath="//android.widget.EditText[contains(@resource-id,'passwordDisplay')]")
	private WebElement enterSecurePinTxtField;	
	
	/** The continue button. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'CONTINUE')]|//*[text()='Continue']")
	private WebElement continueBtn;
	
	/** The done button. */
	@FindBy(xpath="//android.widget.Button[@content-desc,'Done']")
	private WebElement doneBtn;

	/**
	 * Instantiates a new merchant register page.
	 *
	 * @param driver the driver
	 */
	public MerchantRegisterPage(WebDriver driver)
	{
		this.driver=driver;
		softAssert=new SoftAssert();
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Verifies merchant register page.
	 * Also enters Unregistered User and  Email values
	 * 
	 */
	public void verifyMerchantRegisterPage(String data)
	{		
		enterValuesInWelcomePage(data);		
		
		Log.info("======== Verifying registration Page ========");		
		try
		{
			new WebDriverWait(driver,45).until(ExpectedConditions.visibilityOf(regPageChk)); // continueButton
		}
		catch(Exception e)
		{
			Assert.fail("Register page not found \n"+e.getMessage());
		}
		
	}
	
	public void enterValuesInWelcomePage(String data) // Can be changed to specific values mobileNo,email for index compatibility.
	{
		String mobileNo=data.split(",")[0];
		String email=data.split(",")[7];		                             
		
		Generic.waitForElement(driver, continueBtn, 50);
		
		Log.info("======== Entering Mobile No. "+mobileNo+" in Welcome Page========");
		if(!mobileNoTxtField.getText().equals(mobileNo))
		{	
			Generic.forceClearText(driver, mobileNoTxtField);
			mobileNoTxtField.sendKeys(mobileNo);			
			Generic.hideKeyBoard(driver);
		}
		
		Log.info("======== Entering email : "+email+" ========");
		Generic.forceClearText(driver, emailTextField);		
		emailTextField.sendKeys(email);
		Generic.hideKeyBoard(driver);	
		
		
		Log.info("======== Clicking on Continue Button in Welcome Page ========");
		continueBtn.click();
	}

	/**
	 * Enter values.
	 *
	 * @param data the data
	 */
	public void enterValues(String data)
	{
		int i=3;
		String[] values = data.split(",");
		String fullName=values[i++];
		String cardNumber=values[i++];
		String expMnth=values[i++];
		String expYear=values[i++];
		String email=values[i++];
		String mobileNo=values[0];
		
		verifyMerchantRegisterPage(data);

		Log.info("======== Entering Full Name :"+fullName+" ========" );
		//if(!fullNameTextField.getAttribute("name").equals(fullName))
		{
			//Generic.forceClearAttribute(driver, fullNameTextField);
			fullNameTextField.sendKeys(fullName);
			//Generic.hideKeyBoard(driver);
		}
		
		if(cardNumber.contains(":")) cardNumber=cardNumber.split(":")[0];
		Log.info("======== Entering card Number :"+cardNumber+" ========");
		cardNumberTextField.sendKeys(cardNumber);
		Generic.hideKeyBoard(driver);
		
		selectExpMonth(expMnth);
		selectExpYear(expYear);
		
		
		/*Log.info("======== Entering email id :"+email+" ========");
		if(!emailTextField.getAttribute("name").equals(email))
		{
			Generic.forceClearAttribute(driver,emailTextField);
			emailTextField.sendKeys(email);
			Generic.hideKeyBoard(driver);
		}

		
		Log.info("======== Entering Mobile Number :"+mobileNo+" ========");
		if(!mobileNoTextField.getAttribute("name").equals(mobileNo))
		{
			Generic.forceClearAttribute(driver, mobileNoTextField);
			mobileNoTextField.sendKeys(mobileNo);
			Generic.hideKeyBoard(driver);
		}*/
		
		clickRegisterContinue();
		
	}
	
	

	/**
	 * Selects expiry month from the dropdown.
	 *
	 * @param expMonth the exp month
	 */
	public void selectExpMonth(String expMonth)
	{
		
		if (expMonth.length()==1) expMonth="0"+expMonth;
		
		if(Generic.switchToWebView(driver)) 
		{
			new Select(expMonthDropdown).selectByValue(expMonth);
			return;
		}
		Log.info("======== Selecting Exp month : "+expMonth+" ========");
		//expMonthDropdown.sendKeys(expMonth);
		
		expMonthDropdown.click();
		Generic.wait(2);
		
		if(Integer.parseInt(expMonth)<6)
		{
			driver.findElement(By.name(expMonth)).click();
			return;
		}
		try{Generic.scroll(driver,expMonth).click();}catch(Exception e){Assert.fail("Card expiry Month not found\n"+e.getMessage());} //try catch}
	}	
	
	/**
	 * Selects expiry year from the dropdown.
	 *
	 * @param expYear the exp year
	 */
	public void selectExpYear(String expYear)
	{	
		Log.info("======== Selecting Exp Year : "+expYear+" ========");
		if(Generic.switchToWebView(driver)) 
		{
			new Select(expYearDropdown).selectByValue(expYear);
			return;
		}
		
		ArrayList<String> yearList=new ArrayList<String>();
		
		expYearDropdown.click();
		Generic.wait(2);
		
		for(WebElement e:expYearList)
			yearList.add(e.getText());		

		if(yearList.contains(expYear))
			driver.findElement(By.name(expYear)).click();		
		else		
			try{Generic.scroll(driver,expYear).click();}catch(Exception e){Assert.fail("Card expiry Year "+expYear+" not found\n"+e.getMessage());}
		
	}
	
	public void clickRegisterContinue()
	{
		Log.info("======== Clicking on Continue Button ========");
		continueBtn.click();
		
		//mobileNoTextField.click();		
		//Generic.doubleTapEnter(driver);				
	}
	
	public void skipFasterCheckout()
	{
		Log.info("======== Skipping Full Registration ========");
		skipBtn.click();		
	}
	
	public void fullRegistration(String data)
	{		
		String pin=data.split(",")[1],
			   dob=data.split(",")[8];	
		
		waitForFasterCheckout();
		
		enterDob(dob);		
		
		Log.info("==== Entering Wibmo Pin : "+pin+" ====");
		wibmoPinPwdTxtField.sendKeys(pin);
		Generic.wait(2); // Handle Bounce
		Generic.hideKeyBoard(driver);	
		
		clickRegisterContinue();	
	}
	
	public void waitForFasterCheckout()
	{
		try
		{
			fasterCheckoutTxt.isDisplayed();
		}
		catch(Exception e)
		{
			Assert.fail("Faster Checkout fields not found"+e.getMessage());
		}
	}
	
	
	public void enterDob(String dob)
	{
		String dd=dob.split("/")[0],
			   mm=dob.split("/")[1],
			   yyyy=dob.split("/")[2];
		
		dd=dd.length()==1?'0'+dd:dd;
		mm=mm.length()==1?'0'+mm:mm;
		
		Log.info("======== Entering DOB : "+dob+" ========");
		//Generic.hideKeyBoard(driver);
		//Generic.wait(1);		
		
		Log.info("==== Selecting Date : "+dd+" ====");
		//ddSelect.sendKeys(dd);
		ddSelect.click();
		Generic.wait(2);
		Generic.scroll(driver, dd).click();
		
		Log.info("==== Selecting Month : "+mm+" ====");
		//mmSelect.sendKeys(mm);
		mmSelect.click();
		Generic.wait(2);
		Generic.scroll(driver, mm).click();
		
		Log.info("==== Selecting Year : "+yyyy+" ====");
		//yyyySelect.sendKeys(yyyy);
		yyyySelect.click();
		Generic.wait(2);
		Generic.scroll(driver, yyyy).click();
		
	}
	
	
	
	
	/**
	 * Clicks on Done button for an already registered user.
	 */
	public void clickDoneBtn()
	{
		Log.info("======== Clicking on Done Button ========");
		String xp="//android.view.View[contains(@content-desc,'Already registered')]";
		if(driver.findElement(By.xpath(xp)).getText().toLowerCase().contains("already"))
		{
			doneBtn.click();
		}
	}
	
	
}
