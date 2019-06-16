package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HaveInvitePage {

	private WebDriver driver;
	private SoftAssert softAssert;

	@FindBy(id="main_code_edit")
	private WebElement inviteCodeTextField;

	@FindBy(id="main_email_edit")
	private WebElement inviteMobileNumberTextField;

	@FindBy(id="main_btnSubmitInvite")
	private WebElement submitButton;

	public HaveInvitePage(WebDriver driver)
	{
		this.driver= driver;
		softAssert = new SoftAssert();
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	
	
	
}
