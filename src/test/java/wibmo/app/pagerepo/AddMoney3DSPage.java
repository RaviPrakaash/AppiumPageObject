package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;
import library.Log;

/**
 * The Class AddMoney3DSPage checks and performs 3DS related operations during Add Money.
 */
public class AddMoney3DSPage extends BasePage
{	
	/** The driver. */
	private WebDriver driver;
	
	/** The secure pin. */
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(className="android.widget.EditText")  //class="android.widget.EditText"  id="txtPasswordtoDisplay"
	private WebElement securePin;
	
	/** The buttons. */
	@FindBy(className="android.widget.Button")     // id="cmdSubmit"
	private List<WebElement> buttons;	
	
	/** The web view. */
	@FindBy(className="android.webkit.WebView")
	private WebElement webView;	
	
	/** The page check used to check whether a 3DS page has occurred or not. Check between 3DS Page / Transaction Final Page / CVV Page    */ 
	@iOSXCUITFindBy(iOSNsPredicate="name beginswith 'XXXX' or name endswith 'ID' or name contains 'CVV'") 
	@AndroidFindBy(xpath="//*[starts-with(@content-desc,'XXXX') or starts-with(@text,'XXXX') or contains(@resource-id,'m_status_btnOkay') or contains(@text,'CVV')]")  // m_status_btnOkay to make it generic to lm_status ans sm_status
	private WebElement pageCheck;	
	
	/** The submit check. */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'title_text') or contains(@resource-id,'message')]")
	private WebElement submitCheck;
	
	/** The submit 2 button. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'Submit')] | //android.widget.TextView[contains(@resource-id,'title_text')]")
	private WebElement submit2Button;
	
	/** The popup cancel btn. */
	@FindBy(xpath="//*[@text='Cancel']") 
	private WebElement popupCancelBtn;	
	
	/** The cvv checker. */ // m_status_btnOkay instead of lm_ or sm_ , to make it compatible across AddMoney & mVisa
	@iOSXCUITFindBy(iOSNsPredicate="name contains 'CVV' or name contains 'ID'")
	@AndroidFindBy(xpath="//*[contains(@text,'CVV2/CVC2') or contains(@resource-id,'m_status_btnOkay') or contains(@content-desc,'Ok') or contains(@content-desc,'OK') or contains(@content-desc,'ok')]")
	private WebElement cvvChecker;
	
	/** The cvv text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(className="android.widget.EditText")
	private WebElement cvvTextField;

	/** The cvv submit button. */
	@iOSXCUITFindBy(accessibility="Approve")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'approve_textbutton') or contains(@text,'Continue')]")
	private WebElement cvvSubmitButton;
	
	/** The progress bar. */
	@FindBy(xpath="//*[contains(@resource-id,'smoothprogressbar')]")
	private WebElement progressBar; 
	
	@iOSXCUITFindBy(iOSNsPredicate="name=[ci]'submit'")
	@AndroidFindBy(xpath="//android.widget.Button[contains(@resource-id,'cmdSubmit')]") 
	private WebElement submitBtn3ds;
	
	/** The j. */
	public static int i,j;
	
	/**
	 * Instantiates a new AddMoney3DSPage.
	 *
	 * @param driver the driver
	 */
	public AddMoney3DSPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Waits and Verifies for a 3DS page. If 3DS page is not detected by autowebview capability it executes a normal tap
	 * to identify the 3DS page
	 */
	public void verify3DS()
	{	
		i++; // Attempt with non static i for parallel
		Log.info("======== Verifying 3DS page ========");
		try
		{
			new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOf(pageCheck));			
		}
		catch(Exception e)
		{
			Log.info("======== Executing tap 3DS ========");
			Generic.tap3DS(driver);
			if(i>=3)
			{
				i=0;
				Assert.fail("3DS page taking too much time to load or not found , stopping Add money\n"+e.getMessage());
			}			
			verify3DS();		
		}
		i=0;
	}
	
	/**
	 * Completes an Add Money 3DS transaction by entering the pin and optional cvv if the 3DS page occurs.
	 *  
	 *
	 * @param pinDetails the pin details
	 * @return true if cvv was entered
	 * 
	 */
	public boolean addMoney3ds(String pinDetails) 
	{		
		verify3DS();	
		String pin=pinDetails;
		boolean itpFlow;
		
		if(Generic.isIos(driver)) // IOS
			itpFlow=!Generic.getAttribute(pageCheck, "type").contains("Secure");
		else // Android
			itpFlow=(!Generic.getAttribute(pageCheck, "contentDescription").contains("XXXX")) || !(pageCheck.getText().contains("XXXX")); // 3DS page not found
				
		if(pinDetails.contains(":")) pin=pinDetails.split(":")[0];	
		
		addMoneySubmit(pin);
		return enterCvv(pinDetails);			
	}
	
	/**
	 * Enters the 3DS pin and double taps on the Submit button 
	 *
	 * @param pin the pin
	 */
	public void addMoney3dsDouble(String pin)
	{		
		verify3DS();
		if(pin.contains(":")) pin=pin.split(":")[0];
		Log.info("======== Entering Pin ========");
		sendKeys(securePin,pin);
		Generic.hideKeyBoard(driver);					
	
		Log.info("======== Double Tapping Submit Button ========");
		Generic.doubleTap(driver, submitBtn3ds);
		enterCvv(pin);		
	}
	
	/**
	 * Enters 3DS pin and clicks on Submit.
	 *
	 * @param pin the pin
	 */
	public void addMoneySubmit(String pin)
	{	
		verify3DS();
		if(pin.contains(":")) pin=pin.split(":")[0];
		Log.info("======== Entering Pin ========");
		sendKeys(securePin,pin);
		Generic.hideKeyBoard(driver);			
		
		Log.info("======== Clicking on Submit Button ========");		
		click3DSsubmit();		
	}
	
	
	/**
	 * Verifies the presence of  CVV prompt.
	 */
	public void verifyCVV()
	{
		try 
		{
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(cvvChecker));				
		} 
		catch (Exception e)
		{
			Assert.fail("CVV Screen, Page not found or taking too much time to load. Stopping execution\n");
		}
		if(!cvvChecker.getText().contains("CVV"))
			Assert.fail("CVV Screen not found\n");		
	}
	
	/**
	 * Enters cvv if the CVV prompt occurs.
	 *
	 * @param pinCvv the pin cvv
	 * @return true if cvv was entered 
	 */
	public boolean enterCvv(String pinCvv)
	{	
		try 
		{
			new WebDriverWait(driver,35).until(ExpectedConditions.visibilityOf(cvvChecker));				
		} 
		catch (Exception e)
		{
			Assert.fail("Page not found or taking too much time to load. Stopping execution\n");
			Generic.tap3DS(driver);
		}

		if(cvvChecker.getText().contains("CVV"))
		{
			String cvv=pinCvv.contains(":")?pinCvv.split(":")[1]:"";	// Blank cvv means direct transaction		
			
			if(!cvv.isEmpty())
			{
				Log.info("======== Entering CVV : "+cvv+"========");
				cvvTextField.sendKeys(cvv);
			}
			Generic.hideKeyBoard(driver);
			cvvSubmitButton.click();
			return true;			
		}			
		return false;
	}
	
	/**
	 * Generic method used to execute the transaction with or without 3DS, with or without CVV
	 * 
	 * @return String containing the status of the transaction. Ex : 3DSNotFoundCVVFound, 3DSFoundCVVNotFound
	 */
	public String executeTransaction(String pinDetails) // pindetails as 3DSPin:cvv . Ex 1234:123 
	{
		String flow="";		
		
		// ==== Handle 3DS flow ==== //
		verify3DS();
		String pin=pinDetails;		
		String checker=Generic.getAttribute(pageCheck, "contentDescription")+pageCheck.getText();
		
		if(!checker.contains("XXXX")) 
			flow+="3DSNotFound"; 		//	ITP flow without cvv
		else
		{
			pin=pinDetails.split(":")[0];
			addMoneySubmit(pin);
			flow+="3DSFound";
		}		
		// ==== Handle optional CVV ==== //
		
		String cvv=pinDetails.split(":").length>1?pinDetails.split(":")[1]:""; // if CVV not present in TestData enter "" as CVV
		
		if(enterCvv(cvv))
			return flow+"CVVFound";
		else
			return flow+"CVVNotFound";		
	}
	
	/**
	 * Validates CVV Occurrence status values. Actual vs Expected  
	 * 
	 * @return String containing the status of the transaction. Ex : 3DSNotFoundCVVFound, 3DSFoundCVVNotFound
	 */
	public void verifyCVVOccurence(boolean cvvStatusFound,boolean cvvStatusExpected) // Use for checking mandatory occurrence  on absence of CVV screen
	{
		Log.info("==== Verifying the "+(cvvStatusExpected?"":"non ")+"occurence of CVV screen ====");
		
		if(cvvStatusExpected)	//cvvStatusFound==cvvStatusExpected		
			Assert.assertTrue(cvvStatusFound, "CVV Screen not found\n"); 
		else
			Assert.assertFalse(cvvStatusFound, "CVV Screen occured\n");
	}
	
}
