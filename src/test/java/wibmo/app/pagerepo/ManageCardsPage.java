package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import library.Generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;


/**
 * The Class ManageCardsPage used to Add and Delete cards.
 */
public class ManageCardsPage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The add card button. */
	@iOSXCUITFindBy(accessibility="New Card")
	@AndroidFindBy(xpath="//*[@text='Add Card']") 
	private WebElement addCardTab;
	
	/** The primary card icon */
	@FindBy(id="fragment_cards_img_primary")
	private WebElement primaryCardIcon;
	
	/** The card name editable textfield for New Card. */
	@FindBy(id="edit_alias")
	private WebElement cardNameTextfield;
	
	/** The card number editable textfield for New Card. */
	@FindBy(id="edit_cardnum")
	private WebElement cardNumberTextfield;
	
	/** The expiry month editable field for New Card. */
	@FindBy(id="edit_exp_mm")
	private WebElement expMonthField;
	
	/** The expiry year editable field for New Card. */
	@FindBy(id="edit_exp_yyyy")
	private WebElement expYearField;
	
	/** The add card button */
	@FindBy(id="continue_btn")
	private WebElement addCardBtn;
	
	/** The close button. */
	@FindBy(id="menu_close")
	private WebElement closeButton;
	
	/** The card name text. */
	@FindBy(id="text_alias")
	private WebElement cardNameText;
	
	/** The delete icon. */
	@FindBy(id="fragment_cards_img_delete")
	private WebElement deleteIcon;	
	
	@FindBy(xpath="//*[contains(@resource-id,'main_notice_value') or contains(@resource-id,'got_it')]")
	private WebElement coachChk;
	
	@FindBy(xpath="//*[contains(@resource-id,'button1')]")
	private WebElement alertYesBtn;
	
	
	
	
	
	/**
	 * Instantiates a new ManageCardsPage.
	 *
	 * @param driver the driver
	 */
	public ManageCardsPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}	
	
	
	/**
	 * Navigates to Add Card screen.
	 */
	public void gotoAddCard()
	{
		Log.info("======== Clicking on Add Card Tab ========");
		addCardTab.click();
		
		handleCoach();
	}
	
	/**
	 * Closes Manage Cards page.
	 */
	public void closeManagecards()
	{
		Log.info("======== Closing Manage cards ========");
		closeButton.click();
	}
	
	/**
	 * Swipes through the card list and verifies whether a card with a given cardName is present or not.
	 * Parameter cardDetails can be just cardName or cardName:cardNo:expMonth:expYear
	 * @param cardDetails the card details
	 */
	public void verifyCard(String cardDetails) 
	{
		if(cardDetails.contains(":")) 
			cardDetails=cardDetails.split(":")[0];
		Log.info("======== Verifying presence of "+cardDetails+" ========");
		Assert.assertTrue(swipeToCard(cardDetails),cardDetails+ " not found\n");	
	}
	
	/**
	 * Swipes to a card with the given cardName.
	 *
	 * @param cardname the cardname
	 * @return true, if card is found
	 */
	public boolean swipeToCard(String cardname)
	{			
		String previousCard="",currentCard=cardNameText.getText();
		
		Log.info("======== Swiping to "+cardname+" ========");
		while(!previousCard.equals(currentCard))
		{			
			if(cardname.equalsIgnoreCase(currentCard))
			{
				Log.info("======== "+cardname+" card found ========");
				return true;
			}
			else
			{
				previousCard=currentCard;
				Generic.swipeLeft(driver);				
			}
			currentCard=cardNameText.getText();			
		}	
		Log.info("======== "+cardname+" card not found ========");
		return false;		
	}
	
	/**
	 * Deletes the card currently being displayed.
	 */
	public void deleteCurrentCard()
	{
		// Executed as Postcondition , No Assertions
		Log.info("======== Deleting Card ========");
		deleteIcon.click();
		logoutConfirmYesBtn.click(); // same as alert Yes Btn
		waitOnProgressBarId(15);
	}
	/**
	 *  Clicks on the Delete Icon.
	 */
	public void clickDelete()
	{
		Log.info("======== Clicking on Delete Icon ========");
		deleteIcon.click();
	}
	
	/**
	 * Deletes duplicate cards. 
	 * Multiple cards can have same name , which means the wrong card might get selected during transaction.
	 */
	public void deleteDuplicateCards()
	{
		String previousCard="",currentCard=cardNameText.getText();
		
		while(!previousCard.equals(currentCard))
		{			
			if(currentCard.contains("_"))
				deleteCurrentCard();
			
			previousCard=currentCard;
			Generic.swipeLeft(driver);
			currentCard=cardNameText.getText();			
		}		
	}
	
	/**
	 *  Handles the coach message displayed in the 
	 */
	public void handleCoach()
	{
		if(Generic.isIos(driver)) return;
		
		Generic.wait(1);
		try{coachChk.click();}catch(Exception e){Log.info("== Delay due to Coach ==");}
	}
	
	/**
	 *  Adds a card with the given card details
	 * 
	 * @param cardDetails as cardName:cardNumber:expiryMonth:expiryYear
	 */
	public void addCard(String cardDetails)
	{
		gotoAddCard();
		
		enterCardDetails(cardDetails);	
		
		clickAddCardBtn();
	}
	
	/**
	 *  Enters the card details.
	 * 
	 * @param cardDetails as cardName:cardNumber:expiryMonth:expiryYear
	 */
	public void enterCardDetails(String cardDetails)
	{		
		int i=0;
		if(!cardDetails.contains(":")) 
			Assert.fail("Card Details not found in TestData for "+cardDetails+'\n');
		String[] values =cardDetails.split(":");
		
		String cardName=values[i++],cardNumber=values[i++],expMonth=values[i++],expYear=values[i++];
		if(expMonth.length()==1) expMonth="0"+expMonth;  // prefix 0 - if exp month is 3 enter 03 
		
		Log.info("======== Entering Card Name : "+cardName+" ========");
		cardNameTextfield.clear();
		cardNameTextfield.sendKeys(cardName);
		
		Log.info("======== Entering Card No. : "+cardNumber+" ========");
		cardNumberTextfield.clear();
		cardNumberTextfield.sendKeys(cardNumber);
		
		Log.info("======== Entering Exp Month : "+expMonth+" ========");
		if(!expMonthField.getText().equals(expMonth))
			expMonthField.sendKeys(expMonth);
		
		Log.info("======== Entering Exp Year : "+expYear+" ========");
		if(!expYearField.getText().equals(expYear))
			expYearField.sendKeys(expYear);		
		
		Generic.hideKeyBoard(driver);
	}
	
	/**
	 *  Clicks on Add Card button
	 */
	public void clickAddCardBtn()
	{
		Log.info("======== Clicking on Add Card button ========");
		addCardBtn.click();
	}
	
	/**
	 *   Clicks on Primary Card icon.
	 */
	public void toggleCurrentCardPrimary()
	{
		Log.info("======== Clicking on Primary Icon ======== ");
		primaryCardIcon.click();
		Generic.wait(4);
	}
	
	
}
