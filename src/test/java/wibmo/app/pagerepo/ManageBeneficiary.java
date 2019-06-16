package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import library.Generic;
import library.Log;

public class ManageBeneficiary extends BasePage {
	
	/** The driver. */
	private WebDriver driver;
	
	/** The add symbol on page title to add new beneficiary */
	@FindBy(id="menu_new")
	private WebElement addNewBeneficiaryBtn;
	
	@FindBy(id="beneficiary_account_no")
	private WebElement nomineeAccNoField;
	
	/** Check between "no bank account" message and Beneficiary Account No. already present in Page */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'no_acc_added_text') or contains(@resource-id,'beneficiary_account_no')]")
	private List<WebElement> beneficiaryList;
	
	/** The field to input beneficiary name*/
	@FindBy(id="beneficiary_name")
	private WebElement beneficiaryNameTxtField;
	
	/** The field to input bank account no: */
	@FindBy(id="bank_account_number")
	private WebElement bankAccNoTxtField;
	
	/** The field to confirm bank account no: */
	@FindBy(id="bank_confrim_account_number")
	private WebElement confirmBankAccNoTxtField;
	
	/** The field to input IFSC code */
	@FindBy(id="bank_ifsc_number")
	private WebElement ifscCodeTxtField;
	
	@FindBy(id="checkbox_mark_nominee")
	private WebElement nomineeChkbox;
	
	/** The button to save beneficiary details */
	@FindBy(id="save_btn")
	private WebElement saveBtn;
	
	@FindBy(xpath="(//*[contains(@resource-id,'delete_bank_details')])[last()]")
	private WebElement lastBeneficiaryDelBtn;
	
	private By navigateLink=By.xpath("//*[@content-desc='Navigate up']");

	
	public ManageBeneficiary(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this); 
	}

	public void verifyKYCSuccessMessage(String message)
	{
		Log.info("======== Verifying message during login : "+message+" ========");
		Assert.assertTrue(message.contains("KYC is successfully updated"), "KYC Success message was not found during login");		
	}
	
	public void clickBeneficiaryEdit(String benefiicaryNo)
	{
		String benNoEditBtnXp= "//*[contains(@text,'benefiicaryNo')]/../../../..//*[contains(@resource-id,'edit_bank_details')]".replace("benefiicaryNo", benefiicaryNo);
		
		Log.info("======== Clicking on edit button for "+benefiicaryNo+" ========");
		driver.findElement(By.xpath(benNoEditBtnXp)).click();
		
	}
	
	/**
	 * Beneficiary values as Beneficiary Name, Beneficiary Acc Number , ifsc 
	 * 
	 * @param beneficiaryValues
	 */
	public void enterBeneficiaryDetails(String...beneficiaryValues)
	{
		int i =0;
		String beneName=beneficiaryValues[i++],beneAccNo=beneficiaryValues[i++],ifsc=beneficiaryValues[i++];
		
		Log.info("======== Entering Beneficiary Name: "+beneName+" ========"); 		
		beneficiaryNameTxtField.sendKeys(beneName);
		
		Log.info("======== Entering Beneficiary AccNo : "+beneAccNo+" ========"); 		
		bankAccNoTxtField.sendKeys(beneAccNo);
		
		Log.info("======== Confirming Beneficiary AccNo : "+beneAccNo+" ========");
		confirmBankAccNoTxtField.sendKeys(beneAccNo);
		
		Log.info("======== Entering IFSC : "+ifsc+" ========"); 
		ifscCodeTxtField.sendKeys(ifsc);
		
	}
	
	public void clickNomineeCheckbox()
	{
		Log.info("======== Selecting nominee checkbox ========");
		nomineeChkbox.click();
	}
	
	public void clickConfirm()
	{
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Clicking on Confirm button ========");
		saveBtn.click();
	}
	
	public void verifyAddBeneficiarySuccess()
	{
		String msg;
		
		try {
			okBtn.isDisplayed();
			Log.info("======== Verifying Success message : "+(msg=alertMessage.getText())+" ========");
			Assert.assertTrue(msg.contains("added successfully"), "Incorrect success message");
			okBtn.click();
		}
		catch(Exception e) {Assert.fail("Success message not found "+e.getMessage());}		
	}
	
	public void verifyBeneficiaryUpdate()
	{
		String msg;
		
		try {
				okBtn.isDisplayed();
				Log.info("======== Verify Alert message : "+(msg=alertMessage.getText())+" ========");
				Assert.assertTrue(msg.contains("updated successfully"), "Incorrect Update message : "+msg);
				okBtn.click();
		}
		catch(Exception e){Assert.fail("Limits message not found");}
	}
	
	/**
	 *  Verifies nominee under Beneficiary details list
	 * 
	 */
	public void verifyNominee(String expectedNomineeAccNo)
	{
		String nomineeActual;
		
		Log.info("======== Verifying Nominee : "+(nomineeActual=nomineeAccNoField.getText())+" ========");
		Assert.assertEquals(nomineeActual, expectedNomineeAccNo, "Incorrect Nominee displayed");
	}
	
	
	public void verifyBeneficiaryLimitMsg()
	{
		String msg;
		
		try {
				okBtn.isDisplayed();
				Log.info("======== Verify Alert message : "+(msg=alertMessage.getText())+" ========");
				Assert.assertTrue(msg.contains("limit reached"), "Incorrect Alert message : "+msg);
				okBtn.click();
		}
		catch(Exception e){Assert.fail("Limits message not found");}
	
	}
	
	
	
	
	
	/**
	 * 
	 * 		Deletes the beneficiary with the given accNo from the BeneficiaryList. Currently used as Postcondition.
	 * 		
	 * 
	 * @param accNo
	 * @see deleteLastBeneficiary()
	 */
	public void deleteBeneficiary(String accNo)
	{
		String accNoDelBtnXp= "//*[contains(@text,'accNo')]/../../../..//*[contains(@resource-id,'delete_bank_details')]".replace("accNo", accNo);
		
		Log.info("======== Clicking on delete button for "+accNo+" ========");
		driver.findElement(By.xpath(accNoDelBtnXp)).click();
		okButton.click(); // Confirmation Yes button = Ok button1
		
		okBtn.isDisplayed();
		Log.info("======== Verifying  delete confirmation message : "+alertMessage.getText()+" ========");
		okBtn.click();	
		
		Generic.wait(2); 	// Wait for Pageload
		
	}
	
	
	
	/**
	 *  
	 *  
	 * 
	 */
	public void deleteLastBeneficiary()
	{
		
		Log.info("======== Deleting Beneficiary at the end of the list ========");
		lastBeneficiaryDelBtn.click();
		okButton.click(); // Confirmation Yes button = Ok button1
		
		okBtn.isDisplayed();
		Log.info("======== Verifying  delete confirmation message : "+alertMessage.getText()+" ========");
		okBtn.click();	
	}
	
	/**
	 *  
	 * @return number of Beneficiaries
	 */
	public int getBeneficiaryListSize()
	{
		int numberOfBeneficiaries;
		
		Log.info("======== Checking Number of Beneficiaries : "+(numberOfBeneficiaries=beneficiaryList.size())+" ========");
		return  numberOfBeneficiaries;
		
	}
	
	
	public void navigateBack()
	{
		
		Log.info("======== Navigating back from Benficiary Page ========");
		driver.findElement(navigateLink).click();
		
	}
	
	
	
	
}
