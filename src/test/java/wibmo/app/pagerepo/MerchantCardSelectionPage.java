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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import library.Log;
import wibmo.webapp.pagerepo.TestMerchantPage;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import library.Generic;

/**
 * The Class MerchantCardSelectionPage used to select a card under the Merchant Web Overlay.
 */
public class MerchantCardSelectionPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The card selection button. */
	@FindBy(xpath="//*[contains(@content-desc,'Select Card') or (@name='selectCard')]") //android.widget.Spinner | //android.widget.Button className=android.widget.ListView
	private WebElement cardSelection;
	
	@FindBy(className="android.widget.EditText") 
	private WebElement cardNameTextField;	
	
	/** The card number text field . */
	@FindBy(xpath="(//android.widget.EditText)[2]")
	private WebElement cardNumberTextField;	
	
	@FindBy(xpath="//*[contains(@content-desc,'Add new')]") 
	private WebElement addNewSelection;
	
	/** The expiry month dropdown button. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'Expiry Month')] |//android.widget.Spinner[contains(@content-desc,'Expiry Month')]")  // (//android.widget.ListView)[1]
	private WebElement expMonthDropdown; 
	
	/** The expiry year dropdown button. */
	@FindBy( xpath="//android.widget.Button[contains(@content-desc,'Expiry Year')] |//android.widget.Spinner[contains(@content-desc,'Expiry Year')]")  //(//android.widget.ListView)[2]
 	private WebElement expYearDropdown;	
	
	/** The expiry year list. */
	@FindBy(xpath="//android.widget.CheckedTextView[contains(@resource-id,'text1')] | //android.widget.TextView[contains(@resource-id,'text1')]")
	private List<WebElement> expYearList;
	
	/** The approve button. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'Approve')]")
	private WebElement approveButton;
	
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'CONTINUE')]")
	private WebElement continueBtn;
	
	/** The cancel button. */
	@FindBy(xpath="//android.widget.Button[contains(@content-desc,'Cancel')]")
	private WebElement cancelButton;	
	
	/** The mobile merchant text. */
	@FindBy(xpath="//*[contains(@content-desc,'Merchant')]")
	private WebElement mobileMerchantText;
	

	/** The card list. */
	@FindBy(xpath="//android.widget.CheckedTextView[contains(@resource-id,'text1')] | //android.widget.TextView[contains(@resource-id,'text1')]")
	private List<WebElement> cardList;	
	
	/**
	 * Instantiates a new MerchantCardSelectionPage
	 *
	 * @param driver the driver
	 */
	public MerchantCardSelectionPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Selects a card from the Card dropdown.
	 *
	 * @param cardDetails the card details
	 */
	public void selectCard(String cardDetails)
	{
		boolean cardFound=false;
		String listCard;
		verifyCardSelection();
		if(cardDetails.contains(":")) cardDetails=cardDetails.split(":")[0];	
		
		if(Generic.switchToWebView(driver))
		{
			TestMerchantPage merchant=new TestMerchantPage(driver);
			merchant.addtestmerchantdetails(cardDetails);return;
		}
		
		
		Log.info("======== Searching Card : "+cardDetails+" ========"); // No need to preselect 
		
		System.out.println("Card Selection Dropdown Tag : "+cardSelection.getTagName());
		
		System.out.println("PreSelected Card : "+cardSelection.getAttribute("contentDescription"));
		
		/*if(cardSelection.getAttribute("name").contains(cardDetails))		
			Log.info("======== "+cardDetails+" preselected ========");
		else
			try
		{
			cardSelection.click(); 
			Generic.wait(2);
			//String cardXp="//*[contains(@content-desc,'"+cardDetails+"')]";
			//driver.findElement(By.xpath(cardXp)).click();
			Generic.scroll(driver,cardDetails).click();
			
		}
		catch(Exception e)
		{
			Assert.fail(cardDetails+" card not found , please check Card Name \n"+e.getMessage());
		}*/
		
			
		
		Log.info("======== Going through card list ========");
		cardSelection.click();
		Generic.wait(2);
		
		for(WebElement c:cardList)
		{			
			listCard=c.getText();
			Log.info(listCard);
			if(Generic.containsIgnoreCase(listCard, cardDetails))	// Handle &nbsp TestCardCorp TestCard && (!Character.isAlphabetic(listCard.charAt(cardDetails.length()) 
			{
				Log.info("======== Selecting Card : "+listCard+" ========");
				c.click();
				cardFound=true;
				break;
			}
		}
		if(!cardFound)		
			Assert.fail(cardDetails+" card not found");
		
		Log.info("======== Clicking on Continue Button ========");
		//approveButton.click();
		continueBtn.click();		
	}
	
	/**
	 *  Adds a new card by selecting the 'Add New' option from card selection dropdown.
	 *
	 * @param cardDetails the card details
	 */
	public void addCard(String cardDetails)
	{
		verifyCardSelection();
		
		boolean cardFound=false;
		if(!cardDetails.contains(":"))		
			Assert.fail("Card Details does not contain exp month and year, Please recheck Card Details\n");		
		
		int i=0;
		String[] values=cardDetails.split(":");
		String cardName=values[i++],cardNumber=values[i++],expMonth=values[i++],expYear=values[i++];		
		
		Log.info("======== Opening card Selection ========");
		cardSelection.click();
		Generic.wait(2);
		
		Log.info("======== Clicking on Add new Payment card ========");
		Generic.scroll(driver,"Add new").click();
		//addNewSelection.click(); //
		
		Log.info("======== Entering Card Name : "+cardName+" ========");
		cardNameTextField.sendKeys(cardName);
		
		Log.info("======== Entering Card Number : "+cardNumber+"========");
		cardNumberTextField.sendKeys(cardNumber);
		Generic.hideKeyBoard(driver);
		
		selectExpMonth(expMonth);
		selectExpYear(expYear);			
		
		Log.info("======== Clicking on Approve button ========");
		continueBtn.click(); //approveButton.click();		
	}
	
	/**
	 * Selects expiry month from the expiry month dropdown list.
	 *
	 * @param expMonth the exp month
	 */
	public void selectExpMonth(String expMonth)
	{
		if (expMonth.length()==1) expMonth="0"+expMonth;
		
		Log.info("======== Selecting Exp month : "+expMonth+" ========");
		//expMonthDropdown.sendKeys(expMonth);
		
		expMonthDropdown.click();
		Generic.wait(2);
		try{Generic.scroll(driver,expMonth).click();}catch(Exception e){Assert.fail("Card expiry Month not found\n"+e.getMessage());} //try catch}
			
	}	
	
	/**
	 * Selects expiry year from the expiry year dropdown list.
	 *
	 * @param expYear the exp year
	 */
	public void selectExpYear(String expYear)
	{
		ArrayList<String> yearList=new ArrayList<String>();
		Log.info("======== Selecting Exp Year : "+expYear+" ========");
		//expYearDropdown.sendKeys(expYear);
		
		expYearDropdown.click();
		
		for(WebElement e:expYearList)
			yearList.add(e.getText());
		
		if(yearList.contains(expYear))
			driver.findElement(By.name(expYear)).click();		
		else		
			try{Generic.scroll(driver,expYear).click();}catch(Exception e){Assert.fail("Card expiry Year not found\n"+e.getMessage());}			
	}
	
	/**
	 * Waits and verifies the occurence of card selection page based on the text displayed.
	 */
	public void verifyCardSelection()
	{
		Log.info("======= Verifying Card Selection Page ========");
		try
		{
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOf(cardSelection));  			
		}
		catch(Exception e)
		{
			Assert.fail("Card Selection Page taking too much time to load or not found\n"+e.getMessage());				
		}		
	}
	

}
