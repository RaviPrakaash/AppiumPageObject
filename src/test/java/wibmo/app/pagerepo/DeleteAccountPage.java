package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import library.Log;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * The Class DeleteAccountPage used to delete the current user account after login.
 */
public class DeleteAccountPage {

	/** The driver. */
	private WebDriver driver;

	/** The securepintext field. */
	@FindBy(id="main_pwd_edit")
	private WebElement securepinTextfield;

	/** The delete my account button. */
	@FindBy(id="main_btnSave")
	private WebElement deleteMyAccountButton;

	/**
	 * Instantiates a new delete account page.
	 *
	 * @param driver the driver
	 */
	public DeleteAccountPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}

	/**
	 * Deletes the current user account after entering the securePin.
	 *
	 * @param securePin the secure pin
	 */
	public void deleteAccount(String securePin)
	{
		Log.info("======== Enter Secure Pin ========");
		securepinTextfield.sendKeys(securePin);
		
		Log.info("======== Clicking on DeleteMyAccount ========");
		deleteMyAccountButton.click();		
	}
}
