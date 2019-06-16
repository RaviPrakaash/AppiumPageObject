package wibmo.app.pagerepo;

import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
//import org.testng.asserts.SoftAssert;

import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;

/**
 * The Class ChangeSecurePinPage used to change the user password after the user has logged in.
 */
public class ChangeSecurePinPage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The current pin text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(id="main_pwd_edit")
	private WebElement currentPinTextField;
	
	/** The new pin text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeSecureTextField[2]")
	@AndroidFindBy(id="main_newpwd_edit")
	private WebElement newPinTextField;
	
	/** The re enter new pin text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeSecureTextField[3]")
	@AndroidFindBy(id="main_newpwd_edit2")
	private WebElement reEnterNewPinTxtField;
	
	/** The change pin button. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and name contains 'PIN'")
	@AndroidFindBy(id="main_btnSave")
	private WebElement changePinButton;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains [ci]'Success'")
	private WebElement updateSuccessMsgTitle;
	
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'updated'")
	private WebElement updateSuccessMsgTxt;
	
	@iOSXCUITFindBy(accessibility="Ok")
	private WebElement updateOkBtn;
	
	/**
	 * Instantiates a new ChangeSecurePinPage.
	 *
	 * @param driver the driver
	 */
	public ChangeSecurePinPage(WebDriver driver)
	{
		this.driver= driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Changes the pin by entering the current pin and new pin which is more than 12 digits
	 * and verifies operation success based on the occurrence of Settings Page.
	 * 
	 * @param securePin the secure pin
	 * @param newPin the new pin
	 * @param additionalChars to be added along with the 12 digit new pin 
	 */
	public void enterMoreThan12DigitPin(String securePin, String newPin,String additionalChars)
	{
		newPin+=additionalChars;
		Log.info("======== Entering additional chars with new Pin as "+newPin+" with "+newPin.length()+" digits ======== ");
		changePin(securePin,newPin);		
	}
	
	/**
	 * Changes the pin by entering the current pin and new pin 
	 * and verifies operation success based on the occurence of Settings Page.
	 * @param securePin the secure pin
	 * @param newPin the new pin
	 */
	public void changePin(String securePin, String newPin)
	{
		String updateMsgSuccess="";
		
		Log.info("======== Entering Current Secure Pin :"+securePin+" ========");
		currentPinTextField.sendKeys(securePin);
		
		Log.info("======== Entering New Secure Pin :"+newPin+" ========");
		newPinTextField.sendKeys(newPin);
		
		//Generic.hideKeyBoard(driver);
		
		Log.info("======== Re-entering new Secure Pin :"+newPin+" ========");
		reEnterNewPinTxtField.sendKeys(newPin);
		
		Log.info("======== Clicking on Change Pin button ========");
		Generic.hideKeyBoard(driver);
		changePinButton.click();	
		
		try
		{
			Log.info("======== Verifying Pin change success ========");
			 if(Generic.isIos(driver))
			 {
				 Log.info("======== Validating Update status : "+(updateMsgSuccess+=updateSuccessMsgTitle.getText())+' '+(updateMsgSuccess+=updateSuccessMsgTxt.getText()));
				 Assert.assertTrue(updateMsgSuccess.contains("Success"), "Update Status title incorrect");
				 Assert.assertTrue(updateMsgSuccess.contains("updated"), "Update Status message incorrect");
				 
				 Log.info("======== Clicking on Update OK Button ========");
				 updateOkBtn.click();				 
			 }
			 else
			 {
				new WebDriverWait(driver,60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='Settings']")));
				driver.navigate().back();
				Generic.wait(1);
				driver.navigate().back(); // To facilitate Logout()
			 }
		}
		catch(Exception e)
		{
			Assert.fail("Secure Pin not changed successfully\n"+e.getMessage());
		}
		Generic.wait(2);
	}
	
}
