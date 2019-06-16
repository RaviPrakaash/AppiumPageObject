package wibmo.app.pagerepo;

import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;

/**
 * The Class ForgotPinStep3Page used to enter the new securePin.
 */
public class ForgotPinStep3Page extends BasePage {

	/** The driver. */
	private WebDriver driver;

	/** The soft assert. */
	private SoftAssert softAssert;

	/** The enter secure pin. */
	@iOSXCUITFindBy(className = "XCUIElementTypeSecureTextField")
	@AndroidFindBy(id = "fl_text_pwd")
	private WebElement enterSecurePinTextfield;

	/** The re enter secure pin. */
	@iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeSecureTextField[2]")
	@AndroidFindBy(id = "fl_text_pwd_2")
	private WebElement reEnterSecurePinTextfield;

	/** The submit button. */
	@iOSXCUITFindBy(accessibility = "Submit")
	@AndroidFindBy(id = "fl_btnSubmit")
	private WebElement submitButton;

	/** The secure pin change success message. */
	@iOSXCUITFindBy(iOSNsPredicate = "value contains 'Your Secure '")
	@AndroidFindBy(xpath = "//*[contains(@resource-id,'message')]")
	private WebElement changeSecurePinMessage;

	/** The confirmation ok button for changeSecurePinMessage. */
	@iOSXCUITFindBy(accessibility = "Ok")
	@AndroidFindBy(xpath = "//*[contains(@resource-id,'button2')]")
	private WebElement confOkButton;

	/**
	 * Instantiates a new ForgotPinStep3Page.
	 *
	 * @param driver the driver
	 */
	public ForgotPinStep3Page(WebDriver driver) {
		super(driver);
		this.driver = driver;
		softAssert = new SoftAssert();
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)), this);
	}

	/**
	 * Enters new pin and validates the Change Pin Success message.
	 *
	 * @param newPin the new pin
	 */
	public void enterNewPin(String newPin) {
		Log.info("======== Entering new Secure Pin : " + newPin + " ========");
		enterSecurePinTextfield.sendKeys(newPin);
		Log.info("======== Re entering new Secure Pin : " + newPin + " ========");
		reEnterSecurePinTextfield.sendKeys(newPin);
		Generic.hideKeyBoard(driver);
		submitButton.click();
		changePinSuccessfullMsg();

		normalizeIOSNavigation();
	}

	/**
	 * Waits for and accepts Change pin successful alert message.
	 */
	public void changePinSuccessfullMsg() {

		try {

			new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(confOkButton));
			Log.info("======== Verifying Secure Pin Change success message " + changeSecurePinMessage.getText()
					+ " ========");
			softAssert.assertTrue(changeSecurePinMessage.getText().contains("successful"));
			confOkButton.click();
		} catch (Exception e) {
			softAssert.fail("PIN changed success message not found. \n");
		}
		softAssert.assertAll();
	}

	/**
	 * Navigate to Welcome Page after successful change pin
	 */
	public void normalizeIOSNavigation() {
		if (!Generic.isIos(driver))
			return;

		Log.info("======== Navigating to WelCome Page ========");
		moreOptions.click();
		changeUserLink.click();
		Generic.wait(2); // Wait for PageLoad

		navigateLink.click();

	}

}
