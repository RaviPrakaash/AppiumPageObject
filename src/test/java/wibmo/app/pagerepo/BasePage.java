package wibmo.app.pagerepo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import library.Generic;
import library.Log;
import wibmo.app.testScripts.BaseTest1;

/**
 * The Class BasePage consists of elements and actions common to all the pages. 
 * Can be extended by all the pages having common features.
 */
public class BasePage {

	/** The driver. */
	private WebDriver driver;
	
	static SoftAssert sAssert;
	
	static ArrayList<String> upperNav;
	
	static ArrayList<String> lowerNav;
	
	/** Used to store all the Navigation link texts */
	static ArrayList<String> allNav;
	
	/** Page title text .ios  to be used only if  the pagetitle has text value   */
	@iOSXCUITFindBy(iOSNsPredicate="value contains ' Verification' or value contains 'Hi '") 
	@AndroidFindBy(id="title_text")
	protected WebElement pageTitle;
 
	/** The more options Icon under the Navigation Panel.  */
	@iOSXCUITFindBy(accessibility="IconMenu")
	@AndroidFindBy(xpath="//*[@content-desc='More options']|(//*[contains(@resource-id,'title_text')]/../../..//android.widget.ImageView)[last()]") 
	protected WebElement moreOptions;
	
	/** The navigate link. */
	@iOSXCUITFindBy(iOSNsPredicate="(name endswith 'Back' or name='IconMenu') and visible=true") 
	@AndroidFindBy(xpath="//*[@content-desc='Navigate up']")  
	protected WebElement navigateLink;
	
	/** The about link under More Options  Menu */
	@iOSXCUITFindBy(accessibility="About")
	@AndroidFindBy(xpath="//*[@text='About']")
	protected WebElement aboutLink;
	
	/** The Change User link under More Options  Menu */
	@iOSXCUITFindBy(accessibility="Change User")
	protected WebElement changeUserLink;
	
	/** The app version displayed in the About page */ 
	@FindBy(id="fl_main_version")
	protected WebElement appVersion;
	
	/** The Navigation Elements displayed in the page. */
	@iOSXCUITFindBy(iOSNsPredicate="name contains [ci]'IconMenu'")   	//    // **/XCUIElementTypeTable/**/XCUIElementTypeStaticText[`name like "+'*'+"`]
	@AndroidFindBy(id="design_menu_item_text")
	protected List<WebElement> navElements;
	
	/** The logout link under Navigation Panel*/
	@iOSXCUITFindBy(accessibility="Logout")
	@AndroidFindBy(xpath="//*[@text='Logout']") 
	protected WebElement logoutLink; 
	
	/** The Program Card Link under Navigation Panel*/
	@iOSXCUITFindBy(accessibility="PayZapp Card")
	protected WebElement programCardLink;
	
	/** The MVISA Scan To Pay link under Navigation Panel*/
	@iOSXCUITFindBy(accessibility="Scan to Pay")
	protected WebElement scanToPayLink;
	
	/** The Recharge link under Navigation Panel*/
	@iOSXCUITFindBy(iOSNsPredicate="name beginswith 'Recharge'")
	protected WebElement rechargeLink;
	
	@iOSXCUITFindBy(iOSNsPredicate="name beginswith 'Pay / Send'")
	protected  WebElement addSendLink;
	
	/** The Check used to check for logout , whether the Logout Link occurred or not */
	@AndroidFindBy(xpath="//*[contains(@resource-id,'message') or contains(@resource-id,'login_button')]")
	protected WebElement logoutChk;
	
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeNavigationBar' or (type='XCUIElementTypeButton' and name='Login')")
	protected List<WebElement> iosLogoutChk;
	
	@iOSXCUITFindBy(iOSNsPredicate="type in {'XCUIElementTypeNavigationBar','XCUIElementTypeActivityIndicator'}")
	protected List<WebElement> iosPageloadChk;
	
	@FindBy(xpath="//*[contains(@resource-id,'skip')]") 
	protected WebElement logoutDontAskChkBox;
	
	@iOSXCUITFindBy(accessibility="Yes")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'button1')]") 
	protected WebElement logoutConfirmYesBtn;
	
	@iOSXCUITFindBy(accessibility="Settings")
	@AndroidFindBy(xpath="//*[@text='Settings']") 
	protected WebElement settingsLink; 
	
	@AndroidFindBy(id="image_manage_cards")
	@iOSXCUITFindBy(accessibility="Manage Cards")
	protected WebElement manageCardsLink;
	
	@AndroidFindBy(xpath="//*[contains(@resource-id,'message')]") 
	protected WebElement alertMessage;

	/** The ok or the yes button where id=button1. */
	@iOSXCUITFindBy(accessibility="Ok")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'button1')]") 
	protected WebElement okButton;	
	
	/** The ok or the yes button where id=button2.  @see okButton */
	@iOSXCUITFindBy(accessibility="Ok")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'button2')]") 
	protected WebElement okBtn;	
		
	@iOSXCUITFindBy(iOSNsPredicate="name=[ci]'submit'")
	@AndroidFindBy(xpath="//android.widget.Button[contains(@resource-id,'cmdSubmit')]") 
	private WebElement submitBtn3ds;
	
	@FindBy(id="login_button")
	private WebElement loginBtn;
	
	@FindBy(id="menu_close")
	private WebElement closeAuthBtn;
	
	@iOSXCUITFindBy(iOSNsPredicate="type='XCUIElementTypeButton' and visible=true")
	@AndroidFindBy(xpath="//*[contains(@resource-id,'text1')]")
	private List<WebElement> cancelReasons;
	
	/** Used to handle repeated Cancel reason List . Cick on CancelReason/PageTitle */
	@AndroidFindBy(xpath="//*[contains(@text,'Changed my mind') or contains(@resource-id,'title_text') or contains(@text,'Wibmo SDK')]")
	private WebElement cancelRedundantClick;
	
	@iOSXCUITFindBy(className="XCUIElementTypePickerWheel")
	protected List<WebElement> dobPicker;
	
	@iOSXCUITFindBy(className="XCUIElementTypePickerWheel")
	 protected List<WebElement> expiryPicker; 
	
	protected String prgBarTxtId="smoothprogressbar";	
	protected String logoutConfirmTxt="Are you sure";
	protected String navMenuIdTxt="slidmenu_header_backgound";
		
	//========================= Navigation Check elements ============================//
	
	@FindBy(id="label_mvisa_qr_help")
	protected WebElement scanElement;	
	
	@iOSXCUITFindBy(className="XCUIElementTypeNavigationBar")
	@AndroidFindBy(id="title_text") 
	protected WebElement linkElement;	
	
	@iOSXCUITFindBy(iOSNsPredicate="name in {'Notifications','Ok'} and visible=true")
	protected WebElement notificationsAlert;
	
	
	//================================================================================//
	
	// ================ IOS Logout Elements ================ //
	
	@iOSXCUITFindBy(className="XCUIElementTypeNavigationBar")
	protected WebElement navBarChk;
	
	@iOSXCUITFindBy(iOSClassChain="**/XCUIElementTypeNavigationBar/**/XCUIElementTypeButton")
	protected List<WebElement> navBarBtns;
	
	// ============================================ //
	
	
	

	
	
	
	
	
	
	
	
	

	/**
	 * Instantiates a new base page.
	 *
	 * @param driver the driver
	 */
	public BasePage(WebDriver driver)
	{		
		this.driver= driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this); 
	}
	
	// ================== Wrapper Methods ================ // 
	
	public String getText(WebElement element)
	{
		return Generic.getText(driver, element);
	}
	
	public void sendKeys(WebElement element, String text,String...customArgs)
	{
			element.sendKeys(text); 
	}
	
	public void click(WebElement element,String...customArgs)
	{
		element.click(); // FB should not be present in the phone.
	}
	
	public void clear(WebElement element,String...args)
	{
			element.clear(); // FB should not be present in the phone.
	}
	
	
	public void verifyNavigation(String data)
	{
		sAssert=new SoftAssert();			
				
		obtainNavigationLinks();		// Only Android
		
		verifyLink(data,"Offers","Offers");
		verifyLink(data,"Travel","Travel");
		verifyLink(data,"Shopping","Shopping");
		verifyLink(data,"Movies","Movies");
		//verifyLink(data,"Dining","Dining");
		//verifyLink(data,"Gifting","Gifting");
		verifyLink(data,"Groceries & Dining","Groceries & Dining");
		verifyLink(data,"Taxi","Taxi");
		verifyLink(data,"Request Money","Accepted");
		verifyLink(data,"Recent","Recent"); 
		verifyLink(data,"Notifications","Notifications");
		verifyLink(data,"Support","Support");
		verifyLink(data,"About","About");	
		
		sAssert.assertAll();	
		
	}	
	
	/**
	 * Reads all the Navigation Link text values in to the allNav ArrayList
	 * 
	 */
	public void obtainNavigationLinks()
	{
		String navigationTxt;
		
		if(Generic.isIos(driver)) return;
		
		allNav=new ArrayList<String>();	
		
		Log.info("======== Clicking on Navigation ========");
		navigateLink.click();
		
		System.out.println(" nav Elements "+navElements.size());
		
		for(WebElement e:navElements)
		{
			navigationTxt=Generic.isAndroid(driver)?e.getText():Generic.getAttribute(e, "name");
			allNav.add(navigationTxt.toLowerCase()); // For IOS allNav is populated without scroll
		}
		
		if(Generic.isAndroid(driver))
		{
			Generic.scroll(driver,"Logout");
		
			for(WebElement e:navElements)
				allNav.add(e.getText().toLowerCase());
			
			Generic.scroll(driver, "Home");
		}
		
		Log.info("======= Navigation Links found :"+allNav+" ========");
		
		
		Log.info("======== Navigating Back ========");
		Generic.navigateBack(driver);
		
	}	
	
	public void verifyLink(String data,String linkName,String verifyText)
	{
		String actualText;
				
		if(!Generic.containsIgnoreCase(data, linkName)) return; 		// Navigation Links which are present in the TestData will be checked
		
		// === IOS === //
		if(Generic.isIos(driver))
			{verifyLinkIos(linkName,verifyText);return;}
		// =========//
						
		if(checkLink(linkName))
		{
			Log.info("======== Clicking on Navigation ========");
			navigateLink.click();		
		
			Log.info("======= Verifying "+linkName+" navigation link ========");
			
			if(Generic.isAndroid(driver)) 		// Scroll to Navigation Link Android
			{
				Generic.scroll(driver, linkName).click();
				Generic.wait(2);
			}
			
			else 									// Scroll to Navigation Link IOS 
			{
				String navLinkClassChain="**/XCUIElementTypeTable/**/XCUIElementTypeStaticText[`name contains \"navLinkText\"`]".replace("navLinkText", linkName);
				IOSElement navLink=(IOSElement)driver.findElement(MobileBy.iOSClassChain((navLinkClassChain)));
				
				// === To be replaced with Generic.scroll() for ios === // 
				if(Generic.getAttribute(navLink, "visible").equals("false")) 	// Swipe To IOS Element if not visible
					if(Generic.getAttribute(logoutLink, "visible").equals("false"))  
						Generic.swipeToBottom(driver);     // if at top swipe to bottom
					else
						Generic.swipeUp(driver);  			// if at bottom swipe to top
				// =============================== //
				
				navLink.click();	 // Click on Navigation Element 				
				
				// === Handle Additional IOS Elements behaviour  for Recent Transactions  === //
				if(Generic.containsIgnoreCase(linkName, "Notifications"))
						notificationsAlert.click();
				if(Generic.containsIgnoreCase(linkName, "Recent"))
						Generic.wait(10); 		// Wait for Pageload
				
			}
			// === Validate Link === //
			try
			{	
				actualText=Generic.isAndroid(driver)?linkElement.getText():Generic.getAttribute(linkElement, "name");
				Assert.assertTrue(Generic.containsIgnoreCase(actualText, verifyText), linkName+" screen not displayed with "+verifyText+","+actualText+" displayed.");
			}
			catch(Exception e)
			{
				Assert.fail(linkName+" screen was not displayed with "+verifyText+" text\n"+e.getMessage());
			}
			
			
			if(Generic.isAndroid(driver))
			{
				Log.info("======== Navigating back ========");
				Generic.navigateBack(driver);
			}
			else
				navigateLink.click(); 			// NavigateLink=Back Button IOS	
					
			Generic.wait(2);
		}
		else 	// Link not present in Navigation . If link present then only validate link.
			Log.info("=="+linkName+" link not present in Navigation ==");
		
	}
	
	public void verifyLinkIos(String linkName,String verifyText)
	{
		String actualTxt;
		String navLinkClassChain="**/XCUIElementTypeTable/**/XCUIElementTypeStaticText[`name contains \"navLinkText\"`]".replace("navLinkText", linkName);
		IOSElement navLink=(IOSElement)driver.findElement(MobileBy.iOSClassChain((navLinkClassChain)));
		
		navigateLink.click();
		
		Log.info("======== Clicking on Navigation link : "+ linkName+" ========");
		// === To be replaced with Generic.scroll() for ios === // 
		if(Generic.getAttribute(navLink, "visible").equals("false")) 	// Swipe To IOS Element if not visible
			if(Generic.getAttribute(logoutLink, "visible").equals("false"))  
				Generic.swipeToBottom(driver);     // if at top swipe to bottom
			else
				Generic.swipeUp(driver);  			// if at bottom swipe to top
		// =============================== //
		
		navLink.click();	 	// Click on Navigation Element 				
		
		// === Handle Additional IOS Elements behaviour  for Recent Transactions  === //
		if(Generic.containsIgnoreCase(linkName, "Notifications"))
				notificationsAlert.click();
		// === Wait for recent transactions to load
		if(Generic.containsIgnoreCase(linkName, "Recent"))
				waitOnProgressBarId(30);
		
		// === Validate Link === //
		try
		{	
			actualTxt=Generic.getAttribute(linkElement, "name");
			Assert.assertTrue(Generic.containsIgnoreCase(actualTxt, verifyText), linkName+" screen not displayed with "+verifyText+","+actualTxt+" displayed.");			
		}
		catch(Exception e)
		{
			Assert.fail(linkName+" screen was not displayed with "+verifyText+" text\n"+e.getMessage());
		}
		
		Log.info("======== Navigating back ========");
		navigateLink.click();
		
		// 	Form nested element from xcuitable or attempt \ classchain join
		// 	click navigate
		// 	click element
		// 	verify element
		//		click back
		
		
	}
	
	
	/**
	 *  Checks whether the navigation linkname is present in the allNav String list
	 * 
	 * @param linkName
	 * @return
	 */
	protected boolean checkLink(String linkName)
	{
		for(String s:allNav)
			if(Generic.containsIgnoreCase(s, linkName))
			{	
				Log.info("======== "+linkName+" link found ========");
				return true;
			}
		return false;
	}
	
	/**
	 * 
	 * Selects the given date 
	 * 
	 * @param dateField
	 * @param date
	 */
	protected void selectDateIos(WebElement dateField,String date)
	{
		String dd=date.substring(0, 2),
				month=Generic.getMonthName(Integer.parseInt(date.substring(2,4))),
				yyyy=date.substring(4);
		
		dateField.click();		
		
		Log.info("======== Selecting "+dd+' '+month+' '+' '+yyyy+" ========");
    		Generic.setValue(driver, dobPicker.get(2), yyyy);  	if(true) return;
		
		//dobPicker.get(2).sendKeys(yyyy); // Recurring Alert Issue in RegisterPage
		//dobPicker.get(1).sendKeys(month);
		//dobPicker.get(0).sendKeys(dd);
		
		Generic.hideKeyBoard(driver);
	}
	
	private void selectCardExpiryIos(WebElement cardExpiryField,String expMonth,String expYear)
	{
		
		 expMonth="August"; //Generic.getMonthName(Integer.parseInt(expMonth));
		 
		 Log.info("======== Selecting Card Expiry Month & date from Picker : "+expMonth+' '+expYear+" ========");
		 //cardExpiryField.sendKeys("06 / 2025");
		 cardExpiryField.click();
		 
		 Generic.wait(1);
		// Generic.swipeToBottom(driver);
		// Generic.hideKeyBoard(driver);
		 
		//System.out.println("IOS PageSource : \n"+driver.getPageSource()); 
		 
		 expiryPicker.get(0).sendKeys(expMonth);
		 expiryPicker.get(1).sendKeys(expYear);
		 
	}
	
	private void pickerWK1()
	{
		// Store Existing Text of card User in Temp Var
		
		// Enter "07 / 2020 " into Card User TxtField
		
		// Long Press Field
		// Click on Cut
		// Get Done button Co-ordinate
		// Get Card Expiry co-ordinates
		
		// Chain Touch Actions as
			// 	Long Press Card Expiry Co-ordinates 
			
				
		
	}
	
	/**
	 *  autoacceptAlert capability does not work for XCUITest. Hence the workaround
	 * 
	 */
	private void acceptAllowAlertIos()
	{
		
	}
	/**
	 * Navigates to Add Send page.
	 */
	public void gotoAddSendPage() 
	{
		Log.info("======== Navigating to Pay / Send page ========");
		navigateLink.click();
		
		Log.info("======== Scrolling to PaySend navigation link ========");
		Generic.scroll(driver, "/ Send").click();		
	}
	
	/**
	 * Navigates to Program Card page.
	 */
	public void gotoProgramCard() 
	{
		String programName=Generic.getPropValues("PROGRAMNAME", BaseTest1.configPath);
		Log.info("======== Navigating to "+programName+" card page ========");
		navigateLink.click();
		
		Generic.scroll(driver, programName+" Card").click();		
	}
	
	/**
	 * Navigates to Recharge page.
	 */
	public void gotoRecharge()
	{
		Log.info("======== Navigating to Recharge Page ========");
		navigateLink.click();
		Generic.wait(2);
		Generic.scroll(driver, "Recharge").click();		
	}
	
	public void gotoSettings()
	{
		Log.info("======== Navigating to Settings Page ========");
		navigateLink.click();
		Generic.wait(1);
		Generic.scroll(driver, "Settings").click();		
	}
	
	/**
	 * Clicks 3DS submit button to be inherited by all 3DS methods.
	 */
	public void click3DSsubmit()
	{	
		
		if(Generic.isIos(driver))
		{
			submitBtn3ds.click();
			return;
		}
		
		AndroidDriver adriver=(AndroidDriver)driver;
		Generic.wait(2); // Wait for hidekeyboard bounce
		
		
		if(Generic.isUiAuto2(adriver))
		{
			Log.info("======== Clicking on 3DS Submit button ========");
			submitBtn3ds.click();
		}
		else
		{
			Log.info("======== Submitting 3DS Pin ========");
			adriver.pressKeyCode(66); 
		}
	}	
	
	public void cancel3DSAuthentication()
	{
		Log.info("======== Closing Authentication Page ========");
		closeAuthBtn.click();
		okButton.click();
		handleCancelReason();
	}
	
	/**
	 * Handles the Cancel Reason list that occurs after Payment Cancellation.
	 *
	 */
	public void handleCancelReason()
	{
		Generic.wait(2);
		Log.info("======== Handling Cancel reason ========");		
		try{cancelReasons.get(new Random().nextInt(cancelReasons.size()-1)).click();}catch(Exception e){Log.info("== Delay due to non occrence of Cancel reason list ==");}		
		if(Generic.isIos(driver)) return;
		
		// Handle redundancy in Cancel Reasons for some Android Versions
		Generic.wait(2);
		try {cancelRedundantClick.click();}catch(Exception e) {Log.info("== Cancel Reason Delay == ");}
		
	}
	
	
	

	/**
	 * Logs out from a given page if the logout option is present under More Options.
	 * Otherwise it logs out from Navigation 
	 */
	public void logOut()
	{
		String pageChk;
		
		if(driver==null) return;
		
		 if (Generic.isIos(driver)) {logOutIos(); return;}  // IOS Logout
		
		if(!Generic.checkAndroid(driver)) return; // No Logout for non Android driver
		
		//if(!Generic.getPackageName(driver).equals(BaseTest1.packageName)) return;		 May cause socket issues
		
		try{
			pageChk=Generic.checkTextInPageSource(driver,"Wibmo SDK","More options");
			
			if(pageChk.contains("SDK")) return ; // No logout for Merchant App  
			
			if(pageChk.contains("options")) //moreOptions.getAttribute("contentDescription")Use pagesource= if necessary
			{	
				moreOptions.click();
				Generic.wait(1);
				if(Generic.checkTextInPageSource(driver, "Logout").contains("out"))
				{
					System.out.println("Logging out from Menu");
					logoutLink.click();
					handleLogout();
					return;
				}
				else
				{
					driver.navigate().back();
					Generic.wait(1);
				}
			}			
			
			if(Generic.checkTextInPageSource(driver, "Navigate up").contains("up")) //navigateLink.getAttribute("contentDescription") Use pagesource= if necessary
			{
				navigateLink.click();
				Generic.wait(1);
				
				if(Generic.checkTextInPageSource(driver, navMenuIdTxt).contains(navMenuIdTxt))
				{
					System.out.println("Logging out from Navigation Panel");
					
				//	 Generic.scroll(driver, "Logout").click();
					 
					 Generic.tap(driver, Generic.scroll(driver, "Logout"));
					 
					handleLogout();
					return;
				}				
			}
			
		}
		catch(Exception e)
		{
			System.err.println("Unable to perform logout\n"+e.getMessage());
		}
		
		
		/*if(!Generic.checkTextInPageSource(driver, "login_button","textbutton_login"))
		{
			System.out.println("--- Performing Force Logout by restarting App ---");
			Generic.switchToApp(driver);
		}*/		
		
	} // Use startActivity under advancedLaunch()
	
	/**
	 *  Logs Out from any page .
	 *  
	 */
	public void logOutIos()
	{
		try 
		{
			 if(navBarChk.getAttribute("visible").equals("false")) {System.out.println("IOS Navigation disabled for logout");return;}  // Logout not possible 
			
			// ===  if XCUIElementTypeNavigationBar  button children contains Back then click back === //
				for(WebElement navBarBtn : navBarBtns)
						if(navBarBtn.getAttribute("name").contains("Back"))
							{ 
								System.out.println("Clicking on Back button in IOS");
								navBarBtn.click();  
								Generic.wait(1);
								break;
							}			
				
			// === If Login button present in Page , then logged out === //
			if(iosLogoutChk.size()>1) return ;  
			
			// ===Complete logout :  if XCUIElementTypeNavigationBar  button children contains IconMenu then click IconMenu === //			
			  for(WebElement navBarBtn : navBarBtns)
				  if(navBarBtn.getAttribute("name").contains("IconMenu"))
				  	{ 
					  	System.out.println("Logging out from Navigation Panel IOS");
					  	navBarBtn.click();
					  	Generic.swipeToBottom(driver);
					  	logoutLink.click();
						logoutConfirmYesBtn.click();
						waitOnProgressBarId(30);
					  	break; 
					  }	
		}
		catch(Exception e) {System.err.println("Unable to logout");e.printStackTrace();}
		
	}
	
	
	/**
	 *  Handles the Logout alert if it occurs and waits for Logout to complete.
	 *   
	 */
	public void handleLogout()
	{
		handleLogoutAlert();
		
		waitOnProgressBarId(30);
		
		try{
			new WebDriverWait(driver, 30)
			.pollingEvery(1, TimeUnit.SECONDS)
			.ignoring(Exception.class)
			.until(ExpectedConditions.visibilityOf(loginBtn));
		}
		catch(Exception e){System.err.println("Unable to logout"+e.getMessage());}

	}
	
	/** 
	 *  Handles logout confirmation Alert if it occurs
	 * 
	 */
	public void handleLogoutAlert()
	{
		Generic.wait(1);		
		
		if(Generic.checkTextInPageSource(driver, logoutConfirmTxt).contains(logoutConfirmTxt))
		{
			System.out.println("=== Handling Logout confirmation Alert ===");
			logoutDontAskChkBox.click();	
			Generic.wait(1);
			logoutConfirmYesBtn.click();
		}
		
	}

	
	/**
	 * Waits for the page to load completely based on the visibility of the progressbar. 
	 * prgBarTxtId String can be overridden
	 *
	 * @param driver the driver
	 * @param prgBarTxtId progressBa Text Identifier in the PageSource
	 */
	public void waitOnProgressBarId(int timeout)
	{
			
		int i=0,j;
		System.out.println("======== Waiting for Page Load to Complete ========");	
		
		for(i=0;i<timeout/2;i++)
		{
			Generic.wait(2);
			
			if(Generic.isAndroid(driver))  // Android 
				
				if(!driver.getPageSource().contains(prgBarTxtId))	//id=smoothprogressbar			
					break;		
				else
					System.out.println("Waiting for Pageload : "+i);
			
			else 										// IOS
				
				if((j=iosPageloadChk.size())==1)
					break;
				else
					System.out.println("Waiting for IOS Pageload : "+j);
				
		}
		
		if(i>=timeout-1)
			System.out.println("== PageLoad Delay ==");
	}
	
	
	/**
	 * Reads and initialises the current AUT Version to autVersion
	 */
	public String getAutVersion()
	{
		if(!BaseTest1.autVersion.isEmpty()) return BaseTest1.autVersion;
		
		try{
			
			moreOptions.click();
			aboutLink.click();
			
			BaseTest1.autVersion=appVersion.getText();
			navigateLink.click(); 
			
			}
		catch(Exception e){}
			
		return BaseTest1.autVersion; 		
	}
}
