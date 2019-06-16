package wibmo.webapp.pagerepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import library.Log;
import wibmo.app.testScripts.BaseTest1;
import library.Generic;

/**
 * The Class ConnectingPhonePage used to skip connection to the mobile device
 */
public class ConnectingPhonePage {

	/** The driver. */
	public WebDriver driver;
	
	/**
	 * Instantiates a new ConnectingPhonePage
	 *
	 * @param driver the driver
	 */
	public ConnectingPhonePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	/** The Skip link. */
	@FindBy(id="skipDeviceBtn")
	private WebElement skipLink;
	
	@FindBy(xpath="//title")
	private WebElement pagetitle;
	
	@FindBy(xpath="//*[@id='skipDeviceBtn' or @id='loginButton']")
	private WebElement checker;
	
	// ===== Optional Select Program screen ==== // 
	
	@FindBy(id="programID")
	private WebElement selectProgramList;
	
	@FindBy(name="rememberMe")
	private WebElement selectPrgCheckbox;
	
	@FindBy(id="wibmoPaymentButton")
	private WebElement selectPrgProceedBtn;
		
	// ======================================== //
	
	/**
	 * Clicks On the Skip link.
	 */
	public void clickonskip()
	{
		selectProgram();
		
		String checkSkip=checker.getText();
		Log.info("==== Verifying link for "+checkSkip+" ====");
		if(Generic.containsIgnoreCase(checkSkip, "Login"))
			return;
		try
		{
			if(skipLink.isDisplayed())			
				skipLink.click();			
		}
		catch(Exception e)
		{
			Log.info("==== Optional Skip link was did not occur ====\n"+e.getMessage());			
		}
	}
	
	public void selectProgram()
	{
		try
		{
			new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOf(selectProgramList));
		}
		catch(Exception e)
		{
			Log.info("==== Select Program screen did not occur ====");return;
		}
		
		Log.info("==== Selecting Program : "+BaseTest1.programName+" ====");
		new Select(selectProgramList).selectByVisibleText(BaseTest1.programName);
		
		Log.info("==== Clicking on Select Program Proceed ====");
		selectPrgCheckbox.click();		
		selectPrgProceedBtn.click();			
	}
}
