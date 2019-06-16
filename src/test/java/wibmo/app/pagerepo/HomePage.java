package wibmo.app.pagerepo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

/**
 * The Class HomePage represents the landing page after successful login.
 */
public class HomePage extends BasePage
{	
	/** The driver. */
	private WebDriver driver;

	/** The my wallet link/ Pay Send Page link. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and name contains '/'")
	@AndroidFindBy(id="image_pay_send")     	// Compatibility for QA & Staging image_pay_send
	private WebElement myWalletLink; 

	/** The send money link. */
	@FindBy(id="image_sendmoney")
	private WebElement sendMoneyLink;	
	
	@iOSXCUITFindBy(iOSNsPredicate="value beginswith 'Hi '")
	@AndroidFindBy(id="main_hi_name_value")
	private WebElement usrName;
	
	/** The Navigation Panel link button common to most pages. */
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeTable/XCUIElementTypeButton")
	@AndroidFindBy(id="edit_img")
	private WebElement editProfileIcon;
	
	/** The coachChk : either click on the coach Coach Got It button or on any neutral element or any alert message*/
	@FindBy(xpath="//*[contains(@resource-id,'main_hi_name_value') or contains(@resource-id,'got_it') or contains(@resource-id,'message') ]") 	
	private WebElement coachChk;
	
	@FindBy(xpath="//*[contains(@resource-id,'smoothprogressbar') or contains(@resource-id,'balance_va')]") //android.widget.RelativeLayout)[1]/*") // 4=progressBar, 3=noProgressBar
	private List<WebElement> prgBarCheck;
	
	/** The Update KYC msg */
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'Update KYC'")
	private WebElement kycAlertMsg;
	
	/** The PAN Al;ert message with Skip & Proceed buttons */
	@iOSXCUITFindBy(iOSNsPredicate="value contains 'RBI mandate'")
	private WebElement PANAlertMsg;
	
	@iOSXCUITFindBy(iOSNsPredicate="name='Travel' and visible=true")
	@AndroidFindBy(id="image_travel")
	private WebElement travelIcon;
	
	@iOSXCUITFindBy(iOSNsPredicate="name='Shopping' and visible=true")
	@AndroidFindBy(id="image_shopping")
	private WebElement shopIcon;
	
	@iOSXCUITFindBy(iOSNsPredicate="name='Movies' and visible=true")
	@AndroidFindBy(id="image_movies")
	private WebElement movieIcon;
	
	@iOSXCUITFindBy(iOSNsPredicate="name contains 'Dining' and visible=true")
	@AndroidFindBy(xpath="//android.widget.ImageView[contains(@resource-id,'image_dining') or contains(@resource-id,'image_grocery')]")
	private WebElement diningIcon;
	
	@iOSXCUITFindBy(iOSNsPredicate="(name='Taxi' or name contains 'Gift') and visible=true")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'image_taxi') or contains(@resource-id,'image_gifting')]")		
	private WebElement taxiIcon;
	
	@iOSXCUITFindBy(iOSNsPredicate="name='Taxi' or name contains 'Gift' and visible=true")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'image_taxi') or contains(@resource-id,'image_gifting')]")						
	private WebElement giftIcon;
	
	/** Grocery & Dining Icon */
	@iOSXCUITFindBy(iOSNsPredicate="name endswith 'Dining' and visible=true")
	@AndroidFindBy(xpath="//android.widget.ImageView[contains(@resource-id,'image_dining') or contains(@resource-id,'image_grocery')]")
	private WebElement groceryIcon;
	
	@FindBy(id="image_offers")
	private WebElement offersIcon;
	
	@iOSXCUITFindBy(className="XCUIElementTypeNavigationBar")
	@AndroidFindBy(id="title_text")
	private WebElement iconElement; 

	

	/** The home page title. */
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeNavigationBar' and name contains 'Home'")
	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text,'Home')]")
	private WebElement homePageTitle;
	
	@FindBy(id="title_text")
	private WebElement pageTitle;

	/** The last login time display. */
	@FindBy(id="main_lastlogin_value")
	private WebElement lastLoginTime;
	
	/**
	 *  The balance field displayed in the home page. IOS field does not have value attribute.
	 */
	@iOSXCUITFindBy(iOSNsPredicate="name beginswith 'Balance'")
	@AndroidFindBy(id="balance_value")
	private WebElement balanceField;

	/** The No Thanks button under rate app popup. */
	@FindBy(xpath="//*[contains(@resource-id,'button2')]")
	private WebElement noThanksBtn;
	
	/** The Rate App popup checker. */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'alertTitle') or contains(@resource-id,'title_text')]")
	private WebElement rateAppChk;
	
	/** The Recharge icon. */
	@iOSXCUITFindBy(iOSNsPredicate="name contains 'Recharge /' and visible=TRUE")
	@AndroidFindBy(id="image_bill_pay")                                                  
	private WebElement rechargeIcon; 
	
	/** The Program card link. */
	@FindBy(xpath="//android.widget.CheckedTextView[contains(@text,'PayZapp Card')]")
	private WebElement payzappCardLink;
	
	// ======== Additional UI ======== //
	@FindBy(id="indicator")
	private WebElement slideIndicator;
	
	@FindBy(id="viewpager")
	private WebElement bannerAd;

	/**
	 * Instantiates a new HomePage.
	 *
	 * @param driver the driver
	 */
	public HomePage(WebDriver driver)
		{	
			super(driver);
			this.driver=driver;
			 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(35)),this);		}

	/**
	 * Navigates to Add/Send (Wallet) page.
	 */
	public void addSend()
	{
		waitOnProgressBarId(10);
		Log.info("======== Clicking on Pay / Send Money ========");
		myWalletLink.click();         
	}
	
	/**
	 * Navigates to Settings Page.
	 */
	public void gotoSettings()
	{			
			openNavigationPanel();			
			
			Log.info("======== Scroll Click on Settings ========");
			if(Generic.isIos(driver))
			{
				Generic.swipeToBottom(driver);
				settingsLink.click();
			}
			else
			{
				Generic.scroll(driver,"Settings").click();				
			}
	}
	
	/**
	 *  Opens the Navigation Panel
	 */
	public void openNavigationPanel()
	{
		Log.info("========  Opening Navigation Panel ========");		
		navigateLink.click();
		
	}
	
	/**
	 *  Navigates to recent transactions through the Navigation Panel
	 */
	public void gotoRecentTxns()
	{
		Log.info("======== Clicking on Navigate Link ========");
		navigateLink.click();
		
		Log.info("======== Navigating to Recent Transactions ========");
		Generic.scroll(driver,"Recent").click();
	}
	
	/**
	 * Navigates to Manage Profile Page.
	 */
	public void gotoManageProfile()
	{
		Generic.wait(3);
		
		Log.info("======== Swipe Open Navigation Panel ========");
		navigateLink.click(); 			//Generic.swipeRight(driver);
		
		Log.info("======== Clicking on Edit Profile Icon ========");
		editProfileIcon.click();		
	}
	

	/**
	 * Navigates to Manage Cards page.
	 */
	public void gotoManageCards()
	{	
		Log.info("======== Opening Navigation Panel ========");
		navigateLink.click();
		try{Thread.sleep(1500);}catch(InterruptedException e){}		
		
		Log.info("======== Clicking on Manage Cards ========");
		
		if(Generic.isAndroid(driver))
			Generic.scroll(driver, "Manage Cards").click();
		else
			manageCardsLink.click();
			
	}
	
	/**
	 * Handles manage cards got it popup.
	 */
	public void handleManageCardsGotIt()
	{
		String xp="//android.widget.TextView[contains(@text,'Manage Cards')]"+
				  "| //android.widget.Button[contains(@resource-id,'got_it_button')]";
		
		try{Thread.sleep(2500);}catch(InterruptedException e){}
		
		if(driver.findElement(By.xpath(xp)).getText().toLowerCase().contains("got"))
			driver.findElement(By.id("got_it_button")).click();		
	}
	
	public void verifyPANAlert()
	{
		String msg;
		
		try
		{
			Log.info("======== Waiting for PAN Submit Alert ========");
			new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(okBtn));
			//okBtn.isDisplayed(); // okBtn=id button2 = Skip button	
			Log.info("======== Verifying PAN Submit Alert Message : "+(msg=Generic.isAndroid(driver)?alertMessage.getText():PANAlertMsg.getText())+" ========");
			Assert.assertTrue(msg.contains("RBI mandate") && msg.contains("submit your PAN details to avail Rs.10,000 PayZapp wallet"), "Wrong PAN Submit message : "+msg);
			
			Log.info("======== Clicking on Skip button ========");
			okBtn.click(); 		
		}
		catch(Exception e)
		{
			Assert.fail("Submit PAN Alert not found"+e.getMessage());
		}
		
		handleCoach();
		
	}
	
	/**
	 *  Handles Coach message in the HomePage by clicking back 
	 * 
	 *  @see verifyLogin() which handles all coach messages during login
	 */
	public void handleCoach()
	{
		if(Generic.isIos(driver)) return;
		
		try
		{
			coachChk.isDisplayed();
			
			Generic.wait(2); // Wait for Pageload 
			
			if(Generic.getAttribute(coachChk, "resourceId").contains("got_it"))
				Generic.navigateBack(driver);
			
		}
		catch(Exception e)
		{
			Log.info("== Coach delay ==");
		}		
	}
	
	/**
	 * Waits and verifies the Home Page.
	 */
	public void verifyHome()
	{		
		try
		{
			new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOf(homePageTitle));
		}catch(Exception e){Log.info("== Unable to verify HomePage ==");return;}
	
	}

	/**
	 * Verifies the Home page title.
	 */
	public void verifyPageTitle()
	{
		//HandleHomePopup.handled=false;
		//new HandleHomePopup(driver).start();
		if(Generic.isIos(driver)) return;
		
		Log.info("======== Verifying Home Page ========");
		try
		{
			Assert.assertTrue(homePageTitle.isDisplayed(),"Home Page not displayed\n");
		}
		catch(Exception e)
		{
			driver.navigate().back();
			if(!Generic.containsIgnoreCase(pageTitle.getText(), "Home"))
				Assert.fail("Home page not displayed \n" +e.toString());
		}
	}

	/**
	 * Verifies Last Login time.
	 */
	public void lastLogin()
	{		
		try
		{
			Log.info("======== Verifying last login time : "+lastLoginTime.getText());
			Assert.assertTrue(lastLoginTime.isDisplayed(),"Last Login time not displayed \n");
		}
		catch(Exception e)
		{
			Assert.fail("Last Login time was not displayed \n"+e.getMessage());
		}
	}
	
	/**
	 * Verifies the Balance field displayed in the home page.
	 */
	public double verifyBalanceField()
	{
		String balance="0.0";
		
		try
		{
			balance=Generic.isAndroid(driver)?balanceField.getText():Generic.getAttribute(balanceField, "name");
			Log.info("======== Verifying Balance Field in HomePage : "+balance+" ========");
			Assert.assertTrue(balance.contains("₹"),"Balance Field not displayed \n");
		}
		catch(Exception e){Assert.fail("Balance was not displayed in Home Page \n"+e.getMessage());}
		
		return Generic.parseNumber(balance);	
	}
	
	public void clickBalanceField()
	{
		Log.info("======== Clicking on Balance Field in Home Page ========");
		click(balanceField);
	}
	
	/**
	 *  Verifies balance displayed in the home page.
	 *   
	 * 
	 * @param amt
	 * @return amount displayed in HomePage
	 */
	public double verifyBalance(String amt)
	{
		String balanceDisplayed;
		
		Log.info("======== Verifying Balance in HomePage : "+(balanceDisplayed=balanceField.getText())+" ========");
		Assert.assertTrue(balanceDisplayed.contains(amt), "Incorrect Balance displayed");
		
		return Generic.parseNumber(balanceDisplayed);
		
	}

	/**
	 * Gets the system login in the format displayed under last login.
	 *
	 * @return the system login
	 */
	public String getSystemLogin()
	{
		DateFormat dateFormat = new SimpleDateFormat("hh:mm a',' dd MMM yyyy");
		//get current date time with Date()
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		String lastLoginRecorded = dateFormat.format(date);

		//get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));
		return lastLoginRecorded;

	}
	
	/**
	 * Verifies the absence of Wallet card
	 *
	 * 
	 */
	public void verifyNoWallet() 
	{
		goToProgramCard();
		Log.info("======== Verifying absence of Wallet ========");
		
		if(Generic.isAndroid(driver)) // Android
		{
			Generic.wait(3);
			Generic.navigateBack(driver); // Handle Coach in HomePage
			Generic.wait(1);		
			String title=pageTitle.getText();		
			Assert.assertTrue(title.contains("Home"),"Please provide a user without wallet, "+title+" found ");
		}
		else 	// IOS
		{
			ProgramCardPage pcp=new ProgramCardPage(driver);
			pcp.verifyNoWalletCardMsg();
			pcp.navigateBack();
			
		}
	}
	
	public void verifyKYCAlert()
	{
		String msg;
		
		try
		{
			okBtn.isDisplayed(); 
			
			Log.info("======== Verifying Alert Message : "+(msg=Generic.isAndroid(driver)?alertMessage.getText():kycAlertMsg.getText())+" ========");
			Assert.assertTrue(msg.toLowerCase().contains("update kyc"), "Wrong KYC update message "+msg);
			
			okBtn.click();
		}
		catch(Exception e)
		{
			Assert.fail("Error in validating KYC Update Alert message \n"+e.getMessage());
		}		
	}
	
	public void verifyPanValidationSuccessMsg()
	{
		String msg;
		
		try
		{
			handleCoach();
			
			okBtn.isDisplayed(); 
			Log.info("======== Verifying Success Message : "+(msg=Generic.isAndroid(driver)?alertMessage.getText():kycAlertMsg.getText())+" ========");
			Assert.assertTrue(msg.contains("PAN is validated successfully"), "Incorrect PAN Validation success message:  "+msg);
			
			okBtn.click();
		}
		catch(Exception e)
		{
			Assert.fail("Error in validating PAN Validation success message \n"+e.getMessage());
		}
		
		Generic.wait(1);
		Generic.navigateBack(driver); // Handle Coach
		
	}

	/**
	 * Verifies the last login time against a given time with a delta of 2 mins.
	 *
	 * @param lastLoginRecorded the last login recorded
	 */
	public void verifyLastLogin(String lastLoginRecorded )
	{
		boolean flag,flag1,flag2; 
		
		flag1=lastLoginRecorded.substring(10).equals(lastLoginTime.getText().substring(10));
				
		double res = Math.abs(Double.parseDouble(lastLoginRecorded.substring(0,5).replace(':','.')) - 
				Double.parseDouble(lastLoginTime.getText().substring(0,5).replace(':','.')) ) ;
		
		flag2=res<0.02 || res==0.41;

		flag=flag1 && flag2;
		
		System.out.println(lastLoginRecorded);
		System.out.println(lastLoginTime.getText());
		

		if(flag) // Insert Assert instead of if(flag)
			System.out.println("Last Login Verified");
		else
			System.out.println("Last Login not Verified");
	}
	
	/**
	 * Handles Rate app popup. Rate App popup is currently handled by HandleHomePopup Thread
	 */
	public void handleRateApp()
	{
		Generic.wait(2);
		if(rateAppChk.getText().contains("Rate"))
			noThanksBtn.click();			
	}	
	
	/**
	 * Navigates to Recharge Page.
	 */
	public void gotoRecharge()
	{
		Log.info("======== Clicking on Recharge icon ========");
		rechargeIcon.click();
	}
	
	/**
	 * Navigates to Program Card page.
	 */
	public void goToProgramCard()
	{
		
		openNavigationPanel();
		
		Log.info("======== Clicking on Program Card Link ========");
		
		if(Generic.isAndroid(driver))
			Generic.scroll(driver, "PayZapp Card").click();		
		else
		{
			Generic.swipeToBottom(driver);
			programCardLink.click();
		}
	}
	
	/**
	 * Clicks on Travel Icon to navigate to travel page.
	 */
	public void gotoTravel() 
	{
		Log.info("======== Clicking on Travel Icon ========");
		travelIcon.click();		
		Generic.wait(2);
	}
	
	/**
	 * Clicks on Shopping Icon to navigate to Shopping page.
	 */
	public void gotoShopping()
	{
		Log.info("======== Clicking on Shopping Icon ========");
		shopIcon.click();	
		Generic.wait(2);
	}
	
	/**
	 * Clicks on Movies Icon to navigate to Movies page.
	 */
	public void gotoMovies()
	{
		Log.info("======== Clicking on Movie Icon ========");
		movieIcon.click();
		Generic.wait(2);
	}
	
	/**
	 * Clicks on Dining Icon to navigate to Dining page.
	 */
	public void gotoDining()
	{
		Log.info("======== Clicking on Dining Icon ========");
		diningIcon.click();
		Generic.wait(2);
	}
	
	/**
	 * Clicks on Taxi Icon to navigate to Taxi page.
	 */
	public void gotoTaxi()
	{	
		Log.info("======== Clicking on Taxi Icon ========");	
		taxiIcon.click();
		Generic.wait(2);
	}
	
	/**
	 * Clicks on Gifting Icon to navigate to Gifting page.
	 */
	public void gotoGifting()
	{
		Log.info("======== Clicking on Gifting Icon ========");
		giftIcon.click();
		Generic.wait(2);
	}
	
	/**
	 * Clicks on Grocery Icon to navigate to Grocery page.
	 */
	public void gotoGrocery()
	{
		Log.info("======== Clicking on Grocery Icon ========");
		Generic.scroll(driver,"Grocer");
		Generic.wait(1);
		//Generic.swipeDown(driver);
		groceryIcon.click();
		Generic.wait(2);
	}
	
	public void gotoOffers()
	{
		Log.info("======== Clicking on Offers Icon ========");
		Generic.scroll(driver, "Offers");
		offersIcon.click();
		Generic.wait(2);
	}
	

	public void verifyIcons() 
	{
		String verText;	
		
		waitOnProgressBarId(30);
		
		gotoTaxi();
		verText=Generic.isAndroid(driver)?iconElement.getText():Generic.getAttribute(iconElement,"name");
		Assert.assertTrue(Generic.containsIgnoreCase(verText, "Taxi"), verText+" found instead of Taxi");
		navigateBack();
		
		gotoMovies();
		verText=Generic.isAndroid(driver)?iconElement.getText():Generic.getAttribute(iconElement,"name");
		Assert.assertTrue(Generic.containsIgnoreCase(verText, "Movies"), verText+" found instead of Movies");
		navigateBack();
		
		gotoDining();
		verText=Generic.isAndroid(driver)?iconElement.getText():Generic.getAttribute(iconElement,"name");
		Assert.assertTrue(Generic.containsIgnoreCase(verText, "Dining"), verText+" found instead of Dining");
		navigateBack();
		
		gotoTravel();		
		verText=Generic.isAndroid(driver)?iconElement.getText():Generic.getAttribute(iconElement,"name");
		Assert.assertTrue(Generic.containsIgnoreCase(verText, "Travel"), verText+" found instead of Travel");
		navigateBack();
		
		gotoShopping();
		verText=Generic.isAndroid(driver)?iconElement.getText():Generic.getAttribute(iconElement,"name");
		Assert.assertTrue(Generic.containsIgnoreCase(verText, "Shopping"), verText+" found instead of Shopping");
		navigateBack();		
		
	
	//	if(taxiIcon.getAttribute("resourceId").contains("taxi"))
		//{
	
		//}
	//	else
		//	Log.info("== Taxi Icon not found ==");
//		
//		if(giftIcon.getAttribute("resourceId").contains("gift"))			
//		{
//			gotoGifting();
//			verText=iconElement.getText();
//			Assert.assertTrue(Generic.containsIgnoreCase(verText, "Gift"), verText+" found instead of Gifting");
//			driver.navigate().back();
//		}
//		else
//			Log.info("== Gifting Icon not found ==");		
		
//		gotoGrocery(); //  Grocery and Dining Merged
//		verText=iconElement.getText();
//		Assert.assertTrue(Generic.containsIgnoreCase(verText, "Groceries"), verText+" found instead of Groceries");
//		driver.navigate().back();
		
		/*gotoOffers();
		verText=iconElement.getText();
		Assert.assertTrue(Generic.containsIgnoreCase(verText, "Offers"), verText+" found instead of Offers");
		driver.navigate().back();*/
		
	}
	
	public void navigateBack()
	{
		if(Generic.isAndroid(driver)) // Android
			Generic.navigateBack(driver);
		else
			navigateLink.click(); // IOS
	}
	
	/**
	 * Additional UI check : Home Page Banner
	 */
	public void verifyHomeBanner() 
	{
		
		if(Generic.isIos(driver)) return; // TBI 
		
		try
		{
			Generic.swipeUp(driver);			
			
			Log.info("======== Verifying Additional UI ========");
			slideIndicator.isDisplayed();
			bannerAd.isDisplayed(); 			// Offers pagetitle is specific to the offers 
		}
		catch(Exception e)
		{
			Assert.fail("Error in Home Page Banner Ad"+e.getMessage()); e.printStackTrace();
		}
		
	}
	
	public void gotoDemo()
	{
		openNavigationPanel();
		Log.info("======== Opening Demo ========");
		Generic.scroll(driver, "Demo").click();
	}

	/**
	 * Verifies the User Name displayed in the HomePage.
	 */
	public void verifyUsername(String name) 
	{
		Log.info("======== Verifying User Name "+name+" in HomePage ========");
		Assert.assertTrue(usrName.getText().contains(name),name +" Username does not match");
	}
}
