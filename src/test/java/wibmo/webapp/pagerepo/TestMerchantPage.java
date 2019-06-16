package wibmo.webapp.pagerepo;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import library.Log;
import library.Generic;

/**
 * The Class TestMerchantPage used to select a card and approve payment for a registered user.
 */
public class TestMerchantPage {
	
	/** The driver. */
	public WebDriver driver;
	
	/**
	 * Instantiates a new TestMerchantPage.
	 *
	 * @param driver the driver
	 */
	public TestMerchantPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	/** The card drop down list. */
	@FindBy(id="sourceSelection")
	private WebElement cardDropDown;
	
	/** The approve button. */
	@FindBy(id="wibmoPaymentButton")
	private WebElement approveButton;
	
	/** The cancel button. */
	@FindBy(xpath="//button[contains(text(),'Cancel')]")
	private WebElement cancelButton;
	
	/**
	 * Selects Card and clicks on Approve button.
	 *
	 * @param data the data
	 */
	public void addtestmerchantdetails(String cardDetails)
	{
		cardDetails=cardDetails.split(":")[0];
		boolean cardFound=false;
		Select selCard=new Select(cardDropDown);
		String card;
		
		List<WebElement> cards=selCard.getOptions();
		
		Log.info("==== Going through card list ====");
		for(WebElement c:cards)
		{
			card=c.getText();
			Log.info(card);
			if( Generic.containsIgnoreCase(card, cardDetails) && (!Character.isAlphabetic(card.charAt(cardDetails.length()))))	// Handle &nbsp TestCardCorp TestCard 
			{
				Log.info("======== Selecting Card : "+card+"========");
				//selCard.selectByVisibleText(card);
				selCard.selectByValue(c.getAttribute("value"));
				cardFound=true;
				break;
			}
			
		}
		
		if(cardFound)
		{
			Log.info("========Clicking on approve button========");
			approveButton.click();
		}
		else
			Assert.fail("Card not found : "+cardDetails);
	}	
	
	/**
	 * Cancels card selection.
	 */
	public void cancelcardselection()
	{
		Log.info("====Cancel Card Selection====");
		cancelButton.click();
		driver.switchTo().alert().accept();
	}
	
}
