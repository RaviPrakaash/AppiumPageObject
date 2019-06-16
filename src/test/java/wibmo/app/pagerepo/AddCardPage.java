package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import library.Log;



import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import library.Generic;

/**
 * The Class AddCardPage used to add a new card.
 */
public class AddCardPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The card name text field. */
	@FindBy(id="edit_alias")
	private WebElement cardNameTextField;
	
	/** The card number text field. */
	@FindBy(id="edit_cardnum")
	private WebElement cardNumberTextField;
	
	/** The exp month text field. */
	@FindBy(id="edit_exp_mm")
	private WebElement expMonthTextField;
	
	/** The exp year text field. */
	@FindBy(id="edit_exp_yyyy")
	private WebElement expYearTextField;
	
	/** The add button. */
	@FindBy(id="continue_btn")
	private WebElement addButton;
	
	@FindBy(xpath="//*[contains(@resource_id,'got_it_button') or contains(@resource_id,'edit_alias')]") 
	private WebElement coachChk;
	
	
	/**
	 * Instantiates a new adds the card page.
	 *
	 * @param driver the driver
	 */
	public AddCardPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Adds the card details.
	 *
	 * @param cardName the card name
	 * @param cardNumber the card number
	 * @param expMonth the exp month
	 * @param expYear the exp year
	 */
	public void addCardDetails(String cardName,String cardNumber,String  expMonth,String expYear)  
	{
		
		
		Log.info("======== Entering card name :" +cardName+" ========");
		cardNameTextField.sendKeys(cardName);
		
		Log.info("======== Entering cardNumber :"+cardNumber+" ========");
		cardNumberTextField.sendKeys(cardNumber);
		
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Entering exp month :"+expMonth+" ========");
		expMonthTextField.sendKeys(expMonth);
		
		Log.info("======== Entering exp year :"+expYear+" ========");
		expYearTextField.sendKeys(expYear); 
		Generic.hideKeyBoard(driver);		
		
		Log.info("======== Clicking on Add button ========");
		addButton.click();		
	}
	

	public void handleCoach()
	{
		try
		{
			coachChk.click();		
		}
		catch(Exception e)
		{
			Log.info("== Coach delay ==");
		}
	}
}
