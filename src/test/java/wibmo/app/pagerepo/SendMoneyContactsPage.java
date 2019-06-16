package wibmo.app.pagerepo;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import library.Generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import library.Log;

/**
 * The Class SendMoneyContactsPage displays the phone contacts for sending money.
 */
public class SendMoneyContactsPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The search contact button. */
	@FindBy(xpath="//*[contains(@resource-id,'search_button')]")
	private WebElement searchButton;
	
	/** The search contact textfield. */
	@iOSXCUITFindBy(className="XCUIElementTypeSearchField")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'search_src_text')]")
	private WebElement searchTextfield;
	
	/** The contact entry found based on the search. */
	@iOSXCUITFindBy(accessibility="ic_account_circle")
	@AndroidFindBy(id="txtContactTxt")
	private WebElement contactEntryFound;	
	
	/** The Contacts tab used to navigate to the Phone contacts. */
	@iOSXCUITFindBy(accessibility="Contacts")
	@AndroidFindBy(xpath="//*[@text='Contacts']") 
	private WebElement contactsTab;
	
	
	/**
	 * Instantiates a new SendMoneyContactsPage.
	 *
	 * @param driver the driver
	 */
	public SendMoneyContactsPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Searches for the given contact and selects by clicking on the contact.
	 *
	 * @param contactName the contact name
	 */
	public void selectContact(String contactName)
	{
		Log.info("======== Clicking on Contacts tab ========");
		contactsTab.click();		
		
		Log.info("======== Searching for Contact name : "+contactName+" ========");
		
		if(Generic.isAndroid(driver))
			searchButton.click();
		else	 // IOS
			Generic.swipeUp(driver);
		
		searchTextfield.sendKeys(contactName.toLowerCase());	
		Generic.hideKeyBoard(driver);
		
		try
		{			
			Generic.tap(driver, contactEntryFound);
			//contactEntryFound.click();	
			Log.info("======== Clicking on Contact entry found ========");
		}
		catch(Exception e)
		{	
			Assert.fail("Contact not found :"+contactName+'\n'+e.getMessage());
		}		
	}
	
	
	
	

}
