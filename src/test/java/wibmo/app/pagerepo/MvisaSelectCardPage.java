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
import wibmo.app.testScripts.BaseTest1;
import wibmo.app.testScripts.Registration.BaseTest;

/**
 * The Class AddMoneySelectCardPage used to select a card with the given card name to perform add money transaction.
 */
public class MvisaSelectCardPage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	@iOSXCUITFindBy(accessibility="Wallet Card")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'Wallet Card')]")  //name="Wallet Cards" or name="Wallet Card"
	private WebElement walletCardsLink;
	
	@iOSXCUITFindBy(accessibility="Linked Cards")
	@AndroidFindBy(xpath="//*[@text='Linked Cards']")
	private WebElement linkedCardsTab;
	
	/** The new card link. */
	@iOSXCUITFindBy(accessibility="New Card")
	@AndroidFindBy(xpath="//*[@text='New Card']")
	private WebElement newCardTab; 
	
	/** The Pay button . */
	@iOSXCUITFindBy(accessibility="Pay")
	@AndroidFindBy(id="continue_btn")
	private WebElement payBtn;
	
	/** The text message displayed when no cards are present. */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'text_cardnum') or contains(@resource-id,'nocard_textview')]")
	private WebElement noCardsText;	
	
	/** The card name text. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeImage/XCUIElementTypeStaticText[-5]") // Handle intermittent imag
	@AndroidFindBy(id="text_alias")
	private WebElement cardNameText;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeImage/XCUIElementTypeStaticText[-1]")
	@AndroidFindBy(id="text_name_on_card")
	private WebElement nameOnCardText;
	
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
	
	/** The add money button . */
	@FindBy(id="continue_btn")
	private WebElement addMoneyButton;
	
	
	
	
	
	/**
	 * Instantiates a new AddMoneySelectCardPage.
	 *
	 * @param driver the driver
	 */
	public MvisaSelectCardPage(WebDriver driver)
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
	
	public void selectCardForPayment(String cardName)
	{
		//if()cardName contains : logic & New Card logic in the future - match to script
		
		if(Generic.containsIgnoreCase(cardName, BaseTest1.programName))
		{
			Log.info("======== Clicking on Wallet Cards Tab ========");
			walletCardsLink.click();			
		}
		else
		{
			Log.info("======== Clicking on Linked Cards Tab ========");
			linkedCardsTab.click();
			swipeToCard(cardName); 
		}
		Generic.wait(1); // PageBounce
		Log.info("======== Clicking on Pay Button ========");
		payBtn.click();
		
	}
	
	
	
	/**
	 * Swipes to the card with the given card name.
	 *
	 * @param cardname the cardname
	 * @return true, if card is found
	 */
	public boolean swipeToCard(String cardName)
	{			
		String previousCard="",currentCard=cardNameText.getText()+(nameOnCardText.getText()); // + nameOncardTest.getText();
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
	
	/**
	 * If cardDetails contains only card name , it swipes to the corresponding card and clicks on Add Money button
	 * If cardDetails contains new card details as cardName:cardNumber:expiryMonth:expiryYear then it adds a new card and clicks on Add Money button  .
	 *
	 * @param cardDetails the card details
	 */
	public void addMoney(String cardDetails)
	{
		String cardName=cardDetails.split(":")[0];		
		
		try{new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(addMoneyButton));}catch(Exception e){Log.info("==Delay due to Add Money button==");}
				
		if(!swipeToCard(cardName)) 			
		{
			Log.info("======== "+cardName+" card not found, trying to Add Money by adding "+cardName+" details ========");
			enterCardDetails(cardDetails);
		}
		Log.info("======== Clicking on Add money button ========");
		addMoneyButton.click();		
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
		newCardTab.click();
		
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
			invalidCardNumber=cardNameTextfield.getText();		
			Assert.assertTrue(cardNameTextfield.isDisplayed(),groupExecuteFailMsg()+ "Invalid card number "+invalidCardNumber+" was accepted\n");
		}
		catch(Exception e)
		{
			Assert.fail(groupExecuteFailMsg()+"Invalid Card Number "+invalidCardNumber+" was accepted\n"+e.getMessage());
		}		
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
			Assert.assertTrue(cardNameTextfield.isDisplayed(),groupExecuteFailMsg()+ "Blank card Alias was accepted\n");
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
			Assert.assertTrue(cardNameTextfield.isDisplayed(),groupExecuteFailMsg()+ "Blank card Number was accepted\n");
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
		Log.info("======== Clicking on Navigate button ========");
		navigateLink.click();
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
