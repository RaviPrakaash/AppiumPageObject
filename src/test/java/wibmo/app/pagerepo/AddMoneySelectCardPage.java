package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
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
//import library.HandleProceedPopup;
import wibmo.app.testScripts.Registration.BaseTest;

/**
 * The Class AddMoneySelectCardPage used to select a card with the given card name to perform add money transaction.
 */
public class AddMoneySelectCardPage extends BasePage
{	
	/** The driver. */
	private WebDriver driver;
	
	/** The linked cards link. */
	@FindBy(xpath="//*[@text='Linked Cards']") 
	private WebElement linkedCardsLink;
	
	/** The new card link. */
	@iOSXCUITFindBy(accessibility="New Card")
	@AndroidFindBy(xpath="//*[@text='New Card']") 
	private WebElement newCardLink;
	
	/** The text message displayed when no cards are present. */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'text_cardnum') or contains(@resource-id,'nocard_textview')]")
	private WebElement noCardsText;	
	
	/** The card name text. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeImage/XCUIElementTypeStaticText")
	@AndroidFindBy(id="text_alias")
	private WebElement cardNameText;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeImage/XCUIElementTypeStaticText[-1]")
	@AndroidFindBy(id="text_name_on_card")
	private WebElement nameOnCardText;
	
	/** The card name editable textfield for New Card. */
	@iOSXCUITFindBy(className="XCUIElementTypeTextField")
	@AndroidFindBy(id="edit_alias")
	private WebElement cardNameTextfield;
	
	/** The card number editable textfield for New Card. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[2]")
	@AndroidFindBy(id="edit_cardnum")
	private WebElement cardNumberTextfield;
	
	/** The expiry month editable field for New Card. */
	@FindBy(id="edit_exp_mm")
	private WebElement expMonthField;
	
	/** The expiry year editable field for New Card. */
	@FindBy(id="edit_exp_yyyy")
	private WebElement expYearField;
	
	/** The Expiry Month Year field for IOS */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[-2]")
	private WebElement expMonthYearField;
	
	/** The add money button . */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and name='Add Money'")
	@AndroidFindBy(id="continue_btn")
	private WebElement addMoneyButton;
	
	@FindBy(xpath="//*[contains(@resource-id,'got_it_button') or contains(@resource-id,'edit_alias')]") 
	private WebElement coachChk;
	
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypePageIndicator' or name='IconCardCamera'")
	private WebElement swipeChk;
	
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Please'")
	private WebElement alertMsg;
	
	@iOSXCUITFindBy(accessibility="IconClose")
	private WebElement closeIcon;
	
	/**
	 * Instantiates a new AddMoneySelectCardPage.
	 *
	 * @param driver the driver
	 */
	public AddMoneySelectCardPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Selects card with the given cardName in the list.
	 *
	 * @param cardName the card name
	 */
	public void selectCard(String cardName)
	{
		Log.info("======== Selecting Card :"+cardName+" ========");
		driver.findElement(By.name(cardName)).click();
	}
	
	/**
	 * Swipes to the card with the given card name.
	 *
	 * @param cardname the cardname
	 * @return true, if card is found
	 */
	public boolean swipeToCard(String cardName)
	{			
		
		if(Generic.isIos(driver))
			return swipeToCardIOS(cardName);
		
		String previousCard="",currentCard=cardNameText.getText()+nameOnCardText.getText(); // + nameOncardTest.getText();
		cardName=cardName.split(":")[0];
		
		Log.info("======== Swiping to "+cardName+" ========");
		while(!previousCard.equals(currentCard))
		{			
			if(Generic.containsIgnoreCase(currentCard, cardName)) // cardName.equalsIgnoreCase(currentCard)
			{
				Log.info("======== "+cardName+" card found ========");
				return true;
			}
			else
			{
				previousCard=currentCard; 
				Generic.swipeLeft(driver);				
			}
			currentCard=cardNameText.getText()+nameOnCardText.getText();	// + nameOnCard.getText();	

		}	
		Log.info("======== "+cardName+" card not found ========");
		return false;		
	}
	
	public boolean swipeToCardIOS(String cardName)
	{
		cardName=cardName.split(":")[0];
		String currentCard=cardNameText.getText()+nameOnCardText.getText();
		
		while(Generic.getAttribute(swipeChk, "type").contains("Indicator"))
		{
			System.out.println("Going through : "+currentCard);
			if(Generic.containsIgnoreCase(currentCard, cardName)) 
			{
				Log.info("======== "+cardName+" card found ========");
				return true;
			}
			else
			{
				Generic.swipeLeft(driver);			
				Generic.wait(1);
				currentCard=cardNameText.getText()+nameOnCardText.getText();
			}
		}
		
		Log.info("======== "+cardName+" card not found ========");
		return false;	
		
	}
	
	/**
	 * If cardDetails contains only card name , it swipes to the corresponding card and clicks on Add Money button
	 * If cardDetails contains new card details as cardName:cardNumber:expiryMonth:expiryYear then it adds a new card and clicks on Add Money button  .
	 *
	 * @param cardDetails the card details
	 */
	public void addMoney(String cardDetails)
	{
		String cardName=cardDetails.split(":")[0];		
		
		try{new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOf(addMoneyButton));}catch(Exception e){Log.info("==Delay due to Add Money button==");}
				
		if(!swipeToCard(cardName)) 			
		{
			Log.info("======== "+cardName+" card not found, trying to Add Money by adding "+cardName+" details ========");
			enterCardDetails(cardDetails);
		}
		Log.info("======== Clicking on Add money button ========");
		addMoneyButton.click();		
	}	
	
	/**
	 * Adds the money after entering the new card details as cardName:cardNumber:expiryMonth:expiryYear.
	 *
	 * @param cardDetails the card details
	 */
	public void addMoneyWithNewCard(String cardDetails)
	{			
		enterCardDetails(cardDetails);
		Log.info("======== Clicking on Add money button ========");
		addMoneyButton.click();		
	}	
	
	public void handleCoach()
	{
		if(Generic.isIos(driver)) return;
		
		Generic.wait(1);
		try
		{
			coachChk.click();		
		}
		catch(Exception e)
		{
			Log.info("== Coach delay ==");
		}
	}
	
	/**
	 * Enter card details for a new card. cardDetails to be given as cardName:cardNumber:expiryMonth:expiryYear
	 *
	 * @param cardDetails the card details
	 */
	public void enterCardDetails(String cardDetails)
	{		
		int i=0;
		if(!cardDetails.contains(":")) 
			Assert.fail("Card Details not found in TestData for "+cardDetails+'\n');
		String[] values =cardDetails.split(":");
		
		Log.info("======== Clicking on New card tab ========");
		newCardLink.click();
		handleCoach();
		
		String cardName=values[i++],cardNumber=values[i++],expMonth=values[i++],expYear=values[i++];
		if(expMonth.length()==1) expMonth="0"+expMonth;  // prefix 0 - if exp month is 3 enter 03 
		
		Log.info("======== Entering Card Name : "+cardName+" ========");
		cardNameTextfield.clear();
		cardNameTextfield.sendKeys(cardName);
		
		Log.info("======== Entering Card No. : "+cardNumber+" ========");
		cardNumberTextfield.clear();
		cardNumberTextfield.sendKeys(cardNumber);
		
		if(Generic.isAndroid(driver)) // Android
		{
			Log.info("======== Entering Exp Month : "+expMonth+" ========");
			if(!expMonthField.getText().equals(expMonth))
				expMonthField.sendKeys(expMonth);
			
			Log.info("======== Entering Exp Year : "+expYear+" ========");
			if(!expYearField.getText().equals(expYear))
				expYearField.sendKeys(expYear);
		}
		//else // IOS 
			// selectCardExpiryIos(expMonthYearField,expMonth,expYear);
		
		Generic.hideKeyBoard(driver);
	}
	
	/**
	 * Verifies the non acceptance of invalid card number used to add money.
	 *
	 * @param cardDetails the card details
	 */
	public void verifyInvalidCardNumber(String cardDetails) 
	{		
		if(!cardDetails.contains(":")) 
			Assert.fail("Card Details not found in TestData for "+cardDetails+'\n');
		String invalidCardNumber=cardDetails.split(":")[1];
		
		Log.info("======== Verifying non acceptance of Invalid Card Number : "+invalidCardNumber+" ========");
		
		try
		{
			if(Generic.isAndroid(driver))
			{		
				invalidCardNumber=cardNumberTextfield.getText();					
				Assert.assertTrue(cardNumberTextfield.isDisplayed(),groupExecuteFailMsg()+ "Invalid card number "+invalidCardNumber+" was accepted\n"); // Integrate Toast
			}
			else
				verifyAlertMsgIOS("enter a valid card number");

		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Invalid Card Number "+invalidCardNumber+" was accepted\n"+e.getMessage());
		}		
	}
	
	public void verifyAlertMsgIOS(String textToBeVerified)
	{
		String msg;
		
		okBtn.isDisplayed();
		Log.info("======== Verifying Alert message : "+(msg=alertMsg.getText())+" ========");
		Assert.assertTrue(msg.contains(textToBeVerified), "Incorrect Alert message displayed");
		okBtn.click();
		
	}
	
	/**
	 * Verifies the non acceptance of blank card name used to Add money.
	 *
	 * @param cardDetails the card details
	 */
	public void verifyBlankCardAlias(String cardDetails) 
	{
		if(!cardDetails.contains(":")) 
			Assert.fail("Card Details not found in TestData for "+cardDetails+'\n');
		
		Log.info("======== Verifying non acceptance of Blank Card Alias ========");
		
		try
		{
			if(Generic.isAndroid(driver))
				Assert.assertTrue(cardNameTextfield.isDisplayed(),groupExecuteFailMsg()+ "Blank card Alias was accepted\n");
			else
				verifyAlertMsgIOS("enter an alias");
		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Blank Card Alias was accepted\n"+e.getMessage());
		}
		
	}
	
	/**
	 * 	Verifies the non acceptance of blank card number used to Add money.
	 *
	 * @param cardDetails the card details
	 */
	public void verifyBlankCardNumber(String cardDetails) 
	{
		if(!cardDetails.contains(":")) 
			Assert.fail("Card Details not found in TestData for "+cardDetails+'\n');
		
		Log.info("======== Verifying acceptance of Blank Card Number ========");
		
		try
		{
			if(Generic.isAndroid(driver))
				Assert.assertTrue(cardNameTextfield.isDisplayed(),groupExecuteFailMsg()+ "Blank card Number was accepted\n");
			else
				verifyAlertMsgIOS("enter a valid card number");
		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Blank Card Number was accepted\n"+e.getMessage());
		}
		
	}	
	
	/**
	 * Navigates back from the page by clicking on the Navigate button
	 */
	public void navigateBack()
	{
		Log.info("======== Clicking on Back button ========");
		if(Generic.isAndroid(driver))
			navigateLink.click();
		else
			closeIcon.click();
			
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
