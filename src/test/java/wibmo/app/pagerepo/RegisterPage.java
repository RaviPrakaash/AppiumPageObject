package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import library.Log;
import io.appium.java_client.pagefactory.AndroidFindBy;
//import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;
import wibmo.app.testScripts.Registration.BaseTest;

/**
 * The Class RegisterPage used to enter the new user registration details. 
 */
public class RegisterPage extends BasePage
{
	
	/** The driver. */
	private WebDriver driver;
	
	/** The soft assert. */
	SoftAssert softAssert;
	
	/** The full name text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[1]")
	@FindBy(id="main_name_edit")
	private WebElement fullNameTextField;
	
	/** The mobile number text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[2]")
	@AndroidFindBy(id="main_mobile_edit")
	private WebElement mobileTextField;
	
	/** The email text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[3]")
	@AndroidFindBy(id="main_email_edit")
	private WebElement emailTextField;
	
	/** The date of birth text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[4]")
	@AndroidFindBy(id="main_dob_edit")
	private WebElement dobTextField;
	
	@AndroidFindBy(id="main_dob_icon")
	private WebElement dobIcon;
	
	@AndroidFindBy(xpath="(//android.widget.Button)[last()]")
	private WebElement dobOkBtn;
	
	@iOSXCUITFindBy(className="XCUIElementTypePickerWheel")
	private List<WebElement> dobPicker;
	
	/** The security question selector button. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[5]")
	@AndroidFindBy(id="secretQuestionSpinner")
	private WebElement securityQuestionButton;
	
	/** The security answer text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[-1]")
	@AndroidFindBy(id="main_secret_question_aedit")
	private WebElement securityAnswerTextField;
	
	/** The secure pin text field. */
	@iOSXCUITFindBy(className="XCUIElementTypeSecureTextField")
	@AndroidFindBy(id="main_pwd_edit")
	private WebElement pinTextField;
	
	/** The re enter secure pin text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeSecureTextField[2]")
	@AndroidFindBy(id="main_pwd_edit_2")
	private WebElement reEnterPinTextField;
	
	/** The proceed button. */
	@iOSXCUITFindBy(accessibility="Proceed")
	@AndroidFindBy(id="main_btnReg")
	private WebElement proceedButton;
	
	/** The List of Security Questions. */
	@iOSXCUITFindBy(className="XCUIElementTypePickerWheel")
	private WebElement securityQuestionWheel;
	
	@AndroidFindBy(xpath="//*[contains(@resource-id,'text1')]") 
	private List<WebElement> securityQuestionList;   // cannot use select class
	
	/** The custom security question text field. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTextField[6]")
	@AndroidFindBy(id="main_secret_question_qedit")
	private WebElement customQuestionTextField;
	
	/** The Terms Of Service link. */
	@iOSXCUITFindBy(iOSNsPredicate="name endswith 'Terms of Service'")
	@AndroidFindBy(id="main_tnc_view")
	private WebElement tosLink;
	
	/** The Terms Of Service Page. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeNavigationBar' and name='Terms of Service'")
	@AndroidFindBy(xpath="//*[@text='Terms of Service']") 
	private WebElement tosPage;
	
	/** The Privacy Policy link. */
	@iOSXCUITFindBy(iOSNsPredicate="name endswith 'Privacy Policy'")
	@AndroidFindBy(id="main_pp_view")
	private WebElement privacyLink;
	
	/** The Privacy Policy page. */
	@FindBy(xpath="//*[@text='Privacy Policy']")
	private WebElement privacyPage; 
	
	/** The page title text. */
	@FindBy(id="title_text")
	private WebElement titleText;
	
	/** The page sub title text. */
	@FindBy(id="subtitle_text")
	private WebElement subTitleText;
	
	/** The menu ok tick button */
	@iOSXCUITFindBy(accessibility="Back")
	@AndroidFindBy(id="menu_ok")
	private WebElement menuTickBtn;
	
	@iOSXCUITFindBy(iOSNsPredicate="(name='Register' or name='Ok') and visible=true")
	private WebElement ageAlertHandler;
	
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Please'")
	private WebElement errMsgAlert;
	
		
	/**
	 * Instantiates a new RegisterPage.
	 *
	 * @param driver the driver
	 */
	public RegisterPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		softAssert=new SoftAssert();
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}
	
	/**
	 * Enters all the values necessary for user registration and Clicks on Proceed button. 
	 *
	 * @param data the data
	 */
	public void enterValues(String data) 
	{
		String[] values=data.split(",");
		int i=1;
		
		String fullName=values[i++],
			   mobileNumber=values[i++],
			   email=values[i++],
			   dob=values[i++];
		
		int securityIndex=Integer.parseInt(values[i++]);
		
		String customQuestion=values[i++],
			   securityAnswer=values[i++],
			   securePin=values[i++],
			   reEnterPin=values[i++];	
		
		Log.info("======== Entering Name : "+fullName+"  ========");
		if(!fullNameTextField.getText().equals(fullName))
		{
			fullNameTextField.clear();
			fullNameTextField.sendKeys(fullName);
			Generic.hideKeyBoard(driver);
		}
		
		if(!mobileNumber.equals("skip") && !mobileNumber.equals(mobileTextField.getText()))
		{
			Log.info("======== Entering Mobile Number : "+mobileNumber+" ========");
			mobileTextField.clear();
			mobileTextField.sendKeys(mobileNumber);
			Generic.hideKeyBoard(driver);
		}
		
		
		Log.info("======== Entering email id : "+email+" ========");		
		if(!email.equals("skip") && !email.equals(emailTextField.getText()))
		{
			emailTextField.clear();
			emailTextField.sendKeys(email);
			Generic.hideKeyBoard(driver);
		}		
		
		Log.info("======== Entering dob :"+dob+" ========");
		if(!dob.equals("skip")  && !dob.equals(dobTextField.getText().replace("/", "")))
		{
			if(Generic.isIos(driver))
			{
				selectDateIos(dobTextField, dob);
				handleAgeAlertIos(2);
			}
			else
			{
				//dobTextField.clear();
				//dobTextField.sendKeys(dob);
				dobIcon.click();
				dobOkBtn.click();  // Use scroll logic / RnD Android datepicker 
				
				
				Generic.hideKeyBoard(driver);
			}
		}
		
		Log.info("======== Selecting security question ========");		
		securityQuestionButton.click();
		Generic.wait(1); //UiAuto2
		if(Generic.isIos(driver))
			if(securityIndex==9)
				securityQuestionWheel.sendKeys("Create Custom security Question");
			else
				securityQuestionWheel.sendKeys("With which company did you start your career?");  //default 
		else
			securityQuestionList.get(securityIndex).click();
		
		if(securityIndex==9)
		{
			if(!customQuestion.equals("skip") && !customQuestion.equals(customQuestionTextField.getText()))
			{
				Log.info("=========Entering Custom Question : "+customQuestion+"  ============");
				customQuestionTextField.sendKeys(customQuestion);
				Generic.hideKeyBoard(driver);
			}			
		}
		
		Log.info("======== Entering Security Answer : "+securityAnswer+" ========");
		if(!securityAnswer.equals(securityAnswerTextField.getText()))
		{	
			securityAnswerTextField.clear();
			securityAnswerTextField.sendKeys(securityAnswer);
			Generic.hideKeyBoard(driver);
		}	
		Generic.scroll(driver,"Proceed");
		
		Log.info("======== Entering Secure Pin : "+securePin+" ========");		
		if(!securePin.equals("skip"))
		{
			pinTextField.clear();
			pinTextField.sendKeys(securePin);
			Generic.hideKeyBoard(driver);
		}
		else
		{
			pinTextField.clear();
			Generic.hideKeyBoard(driver);
		}
			
		Log.info("======== Re-entering Secure Pin : "+reEnterPin+" ========");		
		if(!reEnterPin.equals("skip")){
			reEnterPinTextField.clear();
			reEnterPinTextField.sendKeys(reEnterPin);
			Generic.hideKeyBoard(driver);
		}
		else
		{
			reEnterPinTextField.clear();
			Generic.hideKeyBoard(driver);
		}
		
		Log.info("======== Clicking on Proceed ========");
		Generic.scroll(driver, "View"); //bottom of page
		proceedButton.click();		
	}
	
	/**
	 * 		Verifies "Sorry App had an error"	
	 * 
	 */
	public void verifyAppError()
	{
		String msg, expectedContent="Sorry";
		
		try {
			
		okBtn.isDisplayed();
		Log.info("======== Verifying message : "+(msg=alertMessage.getText())+" ========");
		Assert.assertTrue(msg.contains(expectedContent), "Invalid error message \n ");
		
		okBtn.click();
		
		}
		catch(Exception e){Assert.fail("Error message not found\n");}
		
	}
	
	/**
	 * 
	 *  Workaround for age alert  
	 * 
	 * @param  n , estimated number of times to confirm alert does not recur
	 */
	public void handleAgeAlertIos(int n)
	{
		int count=0;
				
		while(count<n)
		{
		//	Generic.wait(1);
			if(Generic.getAttribute(ageAlertHandler, "name").contains("Ok"))
				ageAlertHandler.click();
			else
				count++;
		}
		
	}
	
	
	
	/**
	 * Verifies whether all the registration fields are displayed or not.
	 */
	public void verifyFields()
	{
		
		Generic.wait(3);  // Wait for the entire page to load		
		
		//======= Verify all values using assertTrue(element.isDisplayed) under try catch============= // 		
		Log.info("======== Verifying all fields ========");
		try 
		{
			softAssert.assertTrue(fullNameTextField.isDisplayed());
			softAssert.assertTrue(mobileTextField.isDisplayed());
			softAssert.assertTrue(emailTextField.isDisplayed());
			Generic.hideKeyBoard(driver);
			
			softAssert.assertTrue(dobTextField.isDisplayed());
			softAssert.assertTrue(securityQuestionButton.isDisplayed());
			softAssert.assertTrue(securityAnswerTextField.isDisplayed());
			Generic.scroll(driver,"Proceed");
			
			softAssert.assertTrue(pinTextField.isDisplayed());
			softAssert.assertTrue(reEnterPinTextField.isDisplayed());
			softAssert.assertTrue(proceedButton.isDisplayed());	
			softAssert.assertAll();
		} 
		catch (Exception e) 
		{
			softAssert.fail(groupExecuteFailMsg()+"Some of the fields were not found \n"+e.getMessage());			
		}	
		softAssert.assertAll();
	}
	
	/**
	 * Verifies the non acceptance of invalid pin.
	 */
	public void verifyInvalidPinErrMsg()
	{
		// Verification of Toast Message "Bad Secure PIN! Check length passed!" for Android
		String msg;
		
		
		Log.info("======== Verifying invalid pin error message ========");
		
		try 
		{
			if(Generic.isAndroid(driver))
				{	Generic.verifyToastContent(driver, "PIN!");
					Assert.assertTrue(subTitleText.getText().contains("Step"));
				}
			else // IOS
			{
				Log.info("======== Verifying error message alert  :"+(msg=errMsgAlert.getText())+ "========");
				Assert.assertTrue(msg.contains("PIN"), "Incorect error message");		
				okBtn.click();
			}
			
			
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+e.getMessage());
		}		
	}
	
	/**
	 * Verifies the non acceptance of invalid email.
	 */
	public void verifyInvalidEmailErrMsg()
	{
		// Verification of Toast Message - "Please enter valid Email ID"  for Android 
		
		String msg;
		
		Log.info("======== Verifying Invalid email error message ========");
		try 
		{
			if(Generic.isAndroid(driver))
			{
				Generic.verifyToastContent(driver, "ID");
				Assert.assertTrue(titleText.getText().contains("Register"));
				Assert.assertTrue(subTitleText.getText().contains("Step 1 of 2"));
			}
			else
			{
				Log.info("======== Verifying Invalid email error message : "+(msg=errMsgAlert.getText())+" ========");
				Assert.assertTrue(msg.contains("enter a valid email"), "Incorrect Error message ");
				okBtn.click();
			}
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+e.getMessage()); 
		}
	}
	
	/**
	 * Only the first 10 digits should be accepted while entering a mobile number greater than 10 digits.
	 * Verify Alert message for IOS
	 * 
	 * @param data the data
	 */
	public void verify10DigitNumber(String data)
	{
		String mobileNumber=data.split(",")[1];
		String msg;
		
		Log.info("======== Entering more than 10-digit mobile number "+ mobileNumber +" ========" );
		mobileTextField.clear();
		mobileTextField.sendKeys(mobileNumber);
		
		if(Generic.isAndroid(driver))
		{
			Log.info("======== Verifying whether first 10 digits accepted : "+mobileTextField.getText()+" ========");		
			Assert.assertEquals(mobileTextField.getText(),mobileNumber.substring(0,10),groupExecuteFailMsg()+"Incorrect mobile number");
		}
		else
		{
			Generic.hideKeyBoard(driver);
			
			Log.info("======== Clicking on Proceed button ========");
			proceedButton.click();			
			
			Log.info("======== Verifying invalid phone number error message : "+(msg=errMsgAlert.getText())+" ========");
			Assert.assertTrue(msg.contains("enter a valid phone number"), "Incorrect error message ");
			okBtn.click();
			
			Generic.swipeUp(driver); // Normalize Navigation  
		}
		
	}
	
	/**
	 * Verifies the non-acceptance of invalid number
	 * 
	 */
	public void verifyInvalidNumberErrMsg()
	{
		String msg;
		Log.info("======== Verifying invalid number error message ========");
		
		// Verification of Toast Message -"Please enter valid Mobile number"
		
		try 
		{
			if(Generic.isAndroid(driver))
			{
				Generic.verifyToastContent(driver, "Please");
				Assert.assertTrue(titleText.getText().contains("Register"));
				Assert.assertTrue(subTitleText.getText().contains("Step 1 of 2"));
			}
			else
			{
				Log.info("======== Verifying invalid phone number error message : "+(msg=errMsgAlert.getText())+" ========");
				Assert.assertTrue(msg.contains("enter a valid phone number"), "Incorrect error message ");
				okBtn.click();
			}
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+e.getMessage());
		}
	}
	
	/**
	 * Verifies the non acceptance of alpha numeric number.
	 *
	 * @param data the data
	 */
	public void verifyAlphaNumericNumber(String data)
	{
		
		if(Generic.isIos(driver)) return; // NA
		
		String alphaNumericNumber=data.split(",")[1],mobileNumberAccepted;
		
		Log.info("======== Entering Alpha numeric number : "+alphaNumericNumber+" ========");
		
		mobileTextField.sendKeys(alphaNumericNumber);
		Generic.hideKeyBoard(driver);
		
		mobileNumberAccepted=mobileTextField.getText();	
		
		Log.info("Verifying accepted number :"+mobileNumberAccepted);
		for(int i=0;i<mobileNumberAccepted.length();i++)
			if(!Character.isDigit(mobileNumberAccepted.charAt(i)))
					softAssert.fail(groupExecuteFailMsg()+mobileNumberAccepted.charAt(i) +" was accepted \n");
		softAssert.assertAll();
	}
	
	/**
	 * Verifies invalid Date Of Birth.
	 */
	public void verifyInvalidDob()
	{
		// Verification of Toast Message - "Please enter date of birth!"
		Log.info("======== Verifiying Invalid Date of Birth ========");
		try 
		{
			Generic.verifyToastContent(driver, "birth!");
			Assert.assertTrue(titleText.getText().contains("Register"));
			Assert.assertTrue(subTitleText.getText().contains("Step 1 of 2"));
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+e.getMessage());
		}
	}
	
	/**
	 * Enter future DOB & also validates what is displayed
	 * @author Prathyush
	 */
	public void verifyFutureDateDob(String dob)
	{
		Log.info("======== Verifiying Date of Birth as future date========");
		Log.info("======== Entering DOB : "+dob+" ========");		
		dobTextField.clear();
		dobTextField.sendKeys(dob);
		String date=dob.substring(0, 2) ;
		String month=dob.substring(2, 4);
		int year=Integer.parseInt(dob.substring(4));
		
		Generic.hideKeyBoard(driver);
		// Verification of Toast Message - "Please enter date of birth!"
		String dateOfBirth=dobTextField.getText();
		try 
		{
			Log.info("======Date of birth displayed is :"+dateOfBirth+"======");
			Assert.assertEquals(dateOfBirth, date+"/"+month+"/"+(year-10));
			
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+e.getMessage());
		}
	}
	
	/**
	 * Verifies the option to create custom security question.
	 */
	public void verifyCreateQuestion()
	{
		Generic.wait(5);	// Wait for optional keyboard to appear		
		Generic.hideKeyBoard(driver);
		
		Log.info("======== Selecting custom security question ========");
		securityQuestionButton.click();
		securityQuestionList.get(securityQuestionList.size()-1).click();	
		
		Log.info("======== Verifying custom question text field ========");
		
		try 
		{
			Assert.assertTrue(customQuestionTextField.isDisplayed());
		} catch (Exception e) 
		{
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * Verifies the non acceptance of custom question that is too long.
	 */
	public void verifyCustomQuestionLongErrMsg()
	{
		// Verification of Toast Message - "Security question is too long!"
		
		try 
		{
			Generic.verifyToastContent(driver, "long!");
			Assert.assertTrue(titleText.getText().contains("Register"));
			Assert.assertTrue(subTitleText.getText().contains("Step 1 of 2"));
		} catch (Exception e) 
		{
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * Verifies the non acceptance of security answer that is too long.
	 */
	public void verifySecurityAnswerLongErrMsg()
	{
		// Verification of Toast Message - "Security answer is too long!"
		Log.info("======== Verifying error message : Security answer is too long! ========");
		
		try 
		{
			Generic.verifyToastContent(driver, "long!");
			Assert.assertTrue(titleText.getText().contains("Register"));
			Assert.assertTrue(subTitleText.getText().contains("Step 1 of 2"));
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+e.getMessage());
		}
	}
	
	/**
	 * This method tries to enter more than 50 characters & verifies only 50 characters are entered.
	 * @author Prathyush
	 * @param securityAnswer
	 */	
	public void enterAndVerifySecurityAnswerMaxLength(String securityAnswer)
	{
		String msg;
		
		Generic.wait(1); 
		Generic.hideKeyBoard(driver);
		Log.info("======== Entering more than 50 characters :"+securityAnswer+" ========");
		securityAnswerTextField.sendKeys(securityAnswer);	
		
		Log.info("======== Verifying for 50 character answer number entered ========");
		try 
		{
			if(Generic.isAndroid(driver))
			{
				String numberEntered=securityAnswerTextField.getText();
				softAssert.assertEquals(numberEntered.length(), 50);
			}
			else 		//		IOS	
			{
				Generic.hideKeyBoard(driver);
				proceedButton.click();
				
				Log.info("======== Verifying answer length error message : "+(msg=errMsgAlert.getText())+" ========");
				Assert.assertTrue(msg.contains("should have a minimum length of 2 and maximum length of 49 characters"), "Incorrect error message");
				okBtn.click();
				
			}
			
		} 
		catch (Exception e) 
		{
			softAssert.fail(groupExecuteFailMsg()+e.getMessage());
		}			
		softAssert.assertAll();
	}
	
	/**
	 * Verifies the whether security question is selected.
	 */
	public void verifySecurityQuestionNotSelected()
	{
		// Verification of Toast Message - "Security answer is too long!"
		Log.info("======== Verifying error message : Security question is not selected or is blank! ========");
		
		try 
		{
			Generic.verifyToastContent(driver, "selected");
			Assert.assertTrue(titleText.getText().contains("Register"));
			Assert.assertTrue(subTitleText.getText().contains("Step 1 of 2"));
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+e.getMessage());
		}
	}
	
	/**
	 * Verifies the whether security answer is blank.
	 * @author Prathyush
	 */
	public void verifyBlankSecuirtyAnswer()
	{
		// Verification of Toast Message - "Security answer can't be blank"
		Log.info("======== Verifying error message : Security answer can't be blank ========");
		
		try 
		{
			Generic.verifyToastContent(driver, "blank");
			Assert.assertTrue(titleText.getText().contains("Register"));
			Assert.assertTrue(subTitleText.getText().contains("Step 1 of 2"));
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+e.getMessage());
		}
	}
	
	/**
	 * Verifies the whether PIN & re-enter PIN field is blank.
	 * @author Prathyush
	 */
	public void verifyBlankPIN()
	{
		Log.info("======== Verifying error message : Secure PIN can't be blank ========");
		
		try 
		{
			Generic.verifyToastContent(driver, "blank");  					// Re-enter Secure PIN does not match with Secure PIN
			Assert.assertTrue(titleText.getText().contains("Register"));
			Assert.assertTrue(subTitleText.getText().contains("Step 1 of 2"));
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+e.getMessage());
		}
	}
	
	public void verifyBlankReEnterPIN()
	{
		Log.info("======== Verifying error message : Re-enter Secure Pin does not match ========");
		
		try 
		{
			Generic.verifyToastContent(driver, "Re-enter");
			Assert.assertTrue(titleText.getText().contains("Register"));
			Assert.assertTrue(subTitleText.getText().contains("Step 1 of 2"));
			
		} catch (Exception e) 
		{
			Assert.fail(groupExecuteFailMsg()+e.getMessage());
		}		
	}
	
	
	
	/**
	 * Verifies the non acceptance of custom question that is too short.
	 */
	public void verifyCustomQuestionShortErrMsg()
	{
		
		// Verification of Toast Message - "Security question is too small!"
		
		
		try 
		{
			if(Generic.isAndroid(driver))
			{	
				Log.info("======== Verifying error message : Security question is too small! ========");
				Generic.verifyToastContent(driver, "small!");
				Assert.assertTrue(titleText.getText().contains("Register"));
				Assert.assertTrue(subTitleText.getText().contains("Step 1 of 2"));
			}
			else
			{
				
			}
			
		} catch (Exception e) 
		{
			Assert.fail("Invalid Data accepted \n"+groupExecuteFailMsg()+e.getMessage());
		}
	}
	
	/**
	 * Verifies the non acceptance of security answer that is too short.
	 */
	public void verifySecurityAnswerShortErrMsg()
	{
		// Verification of Toast Message - "Security answer is too small!"
		Log.info("======== Verifying error message : Security answer is too small!");
		try 
		{
			Generic.verifyToastContent(driver, "small!");
			Assert.assertTrue(titleText.getText().contains("Register"));
			Assert.assertTrue(subTitleText.getText().contains("Step 1 of 2"));
		} catch (Exception e) 
		{
			Assert.fail("Invalid Data accepted \n"+e.getMessage());
		}
	}
	
	/**
	 * Clicks on Terms Of Service link and verifies whether the Terms Of Service page is displayed or not. 
	 */
	public void verifyTos()
	{
		Log.info("======== Verifying Terms of Service ========");	
		
		
		try 
		{
			if(Generic.isAndroid(driver))
			{	
				Generic.hideKeyBoard(driver);
				Generic.wait(1); 
				Generic.hideKeyBoard(driver);
				Generic.scroll(driver, "View Terms of Service").click();
				Generic.wait(2);
				Assert.assertTrue(titleText.getText().toLowerCase().contains("terms"),"Terms of Service page not displayed\n");
				
			}
			else
			{
				Generic.swipeToBottom(driver);
				tosLink.click();
				Assert.assertTrue(tosPage.isDisplayed(), "Terms of Service not displayed");
			}
			
			menuTickBtn.click();
			proceedButton.isDisplayed();
			//privacyLink.isDisplayed();
			
		} catch (Exception e) 
		{
			Assert.fail("Terms of service page not displayed\n"+ e.getMessage());
		}
	}
	
	/**
	 * Clicks on Privacy Policy link and verifies whether the Privacy Policy page is displayed or not.
	 */
	public void verifyPp()
	{
		try{new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(subTitleText));}catch(Exception e){Log.info("== Wait delay ==");}
		Log.info("======== Verifying Privacy Policy  ========");		
		Generic.hideKeyBoard(driver);		
		try 
		{
			Generic.scroll(driver, "Privacy");
			privacyLink.click();
			Assert.assertTrue(privacyPage.isDisplayed(),"Privacy Policy link\\page not found \n");
		} 
		catch (Exception e) 
		{
			Assert.fail("Privacy Policy link/page not found \n"+ e.getMessage());
		}		
	}
	
	/**
	 * Returns the TestCaseID and Scenario if the current TestCase is under Group execution 
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
