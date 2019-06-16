package wibmo.app.pagerepo;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import library.Generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import wibmo.app.testScripts.BaseTest1;
import wibmo.app.testScripts.IAP_Transaction.BaseTest;
import library.Log;

/**
 * The Class MerchantSettingsPage used to set the configuration for different Environments (Staging or QA) and also set for Merchant type (Static or Dynamic) under the merchant app.
 * 
 */
public class MerchantSettingsPage 
{
	
	/** The driver. */
	private WebDriver driver;
	
	@FindBy(xpath="//*[contains(@resource-id,'summary')]")
	private WebElement appPkgName;	
	
	/** The dynamic merchant checkbox. */
	@FindBy(xpath="(//android.widget.CheckBox)[2]")
	private WebElement dynamicMerchantCheckbox;
	
	/** The OK button. */
	@FindBy(xpath="//android.widget.Button[contains(@text,'OK')]") 
	private WebElement okBtn;
	
	/** The app package name. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'App Package Name')]")
	private WebElement appPackageName;
	
	/** The domain to post. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'IAP Domain')]")
	private WebElement domainToPost;

	/** The iap merchant id. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Wibmo IAP Merchant ID')]")
	private WebElement iapMerchantId;
	
	/** The iap merchant name. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Wibmo IAP Merchant Name')]")
	private WebElement iapMerchantName;
	
	/** The iap merchant app id. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Wibmo IAP Merchant App ID')]")
	private WebElement iapMerchantAppId;
	
	/** The hash gen url. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'iap Merchant hash Gen PostUrl')]")
	private WebElement hashGenUrl;	

	/** The More options button. */
	@FindBy(xpath="//android.widget.ImageView[contains(@content-desc,'More options')]")
	private WebElement clickMore;

	/** The settings link under More Options. */
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Settings')]")
	private WebElement settingsLink;
	
	/** The generic settings text field used to represents any field . */
	@FindBy(className="android.widget.EditText")
	private WebElement genericSettingsTextField;
	
	/** The field that is currently being edited. */
	@FindBy(xpath="//android.widget.EditText[contains(@resource-id,'edit')]")
	private WebElement currentField;
	
	/** The default package name value used to check between Staging and QA settings. */
	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'summary')]")
	private WebElement defaultPackageNameValue;
	
	@FindBy(xpath="//android.widget.TextView[contains(@text,'Merchant ID')]/..//android.widget.TextView[contains(@resource-id,'summary')]")
	private WebElement iapMerchantIDValueField;	
	
	@FindBy(xpath="//*[@text='iap Customer Mobile')]")
	private WebElement merchantPassingCustomerMobNo;
	
	@FindBy(xpath="//*[@text='iap Customer Email')]")
	private WebElement merchantPassingCustomerEmail;
	
	/**
	 * Instantiates a new MerchantSettingsPage.
	 *
	 * @param driver the driver
	 */
	public MerchantSettingsPage(WebDriver driver)
	{
		this.driver=driver;
		 PageFactory.initElements(new AppiumFieldDecorator(this.driver, Duration.ofSeconds(30)),this);
	}

 /**
  * Enters the settings for static or dynamic merchant after checking for the present settings.
  * Navigates back after checking or setting the values
  *
  * @param merchantType the merchant type
  */
	public void enterStaticOrDynamicId(String merchantType)
	 {
		Generic.setMerchant(merchantType);	
		
		 if(checkMerchantType(merchantType))
		 {
			 Log.info("======== Merchant already set to "+merchantType+" ========");
			 driver.navigate().back();
			 return;
		 }
			 
		 String merchantId,dynamicMerchantCheck,merchantName,hashUrl,merchantAppId,packageName,domainPost;
		 //hashUrl=Generic.getPropValues("MERCHANTHASHURL", BaseTest1.configPath); //
		// packageName=Generic.getPropValues("MERCHANTPACKAGENAME", BaseTest1.configPath); //
		 //domainPost=Generic.getPropValues("MERCHANTDOMAINTOPOST", BaseTest1.configPath); //	 
		 
			
			if(merchantType.toLowerCase().contains("static"))
			{
				dynamicMerchantCheck="false";
				merchantId=Generic.getPropValues("SMERCHANTID", BaseTest1.configPath);
				//merchantName=Generic.getPropValues("SMERCHANTAPPNAME", BaseTest1.configPath);			
				merchantAppId=Generic.getPropValues("SMERCHANTAPPID", BaseTest1.configPath);		
			}
			else
			{
				dynamicMerchantCheck="true";
				merchantId=Generic.getPropValues("DMERCHANTID", BaseTest1.configPath);    //"44281392116282340588"; // 62571330538168734134
				//merchantName=Generic.getPropValues("DMERCHANTAPPNAME", BaseTest1.configPath);					//"MasterPass";	MVISA
				merchantAppId=Generic.getPropValues("DMERCHANTAPPID", BaseTest1.configPath); 					//"9839";	4038		
			}			
			
			/*appPackageName.click();
			Log.info("======== Entering App Package name  : "+packageName+" ========");
			if(!currentField.getText().equals(packageName))
				appPackageName.sendKeys(packageName);
			okBtn.click();
			
			domainToPost.click();
			Log.info("======== Entering Domain To Post : "+domainPost+" ========");
			if(!currentField.getText().equals(domainPost))
				domainToPost.sendKeys(domainPost);
			okBtn.click();		*/
			
			iapMerchantId.click();
			Log.info("======== Entering Wibmo IAP Merchant ID : "+merchantId+" ========" );
			if(!currentField.getText().equals(merchantId))
				iapMerchantId.sendKeys(merchantId);
			Generic.hideKeyBoard(driver);
			okBtn.click();
			
			/*iapMerchantName.click();
			Log.info("======== Entering Wibmo IAP Merchant Name : "+merchantName+" ========" );
			if(!currentField.getText().equals(merchantName))
				iapMerchantName.sendKeys(merchantName);
			okBtn.click();*/
			
			Log.info("======= Selecting Dynamic Merchant checkbox ========");
			//Generic.scroll(driver,"Dynamic Merchant");
			if(!dynamicMerchantCheckbox.getAttribute("checked").equals(dynamicMerchantCheck))		
				dynamicMerchantCheckbox.click();
			
			Log.info("======== Entering Wibmo IAP Merchant App ID :"+merchantAppId+" ========" );
			Generic.scroll(driver,"Wibmo IAP Merchant App ID").click();
			if(!currentField.getText().equals(merchantAppId))
				iapMerchantAppId.sendKeys(merchantAppId);
			Generic.hideKeyBoard(driver);
			okBtn.click();
			
		/*	Log.info("======== Entering hash Gen URL :"+hashUrl+" ========");
			Generic.scroll(driver,"hash Gen PostUrl").click();
			if(!currentField.getText().equals(hashUrl))
				hashGenUrl.sendKeys(hashUrl);	
			okBtn.click();*/
			
			driver.navigate().back();			
					
			
}
	
	public boolean checkMerchantType(String merchantType)
	 {
		 switch(merchantType.toLowerCase())
			{
			case "static": 
				if(iapMerchantIDValueField.getText().contains(Generic.getPropValues("SMERCHANTID", BaseTest1.configPath)))
					return true;
				else 
					return false;
			case "dynamic":
				if(iapMerchantIDValueField.getText().contains(Generic.getPropValues("DMERCHANTID", BaseTest1.configPath)))
					return true;
				else 
					return false;
			}
		 return false; 
	 }

/**
 * Sets the QA environment settings.
 */
public void setQASettings() 
{
	if(checkQASettings()) 
		{
			enterStaticOrDynamicId(BaseTest.getMerchantType());	
			return;
		}
	
	String appPackageNameValue=Generic.getPropValues("QA_APP_PACKAGE_NAME", BaseTest1.configPath),
			domainToPostValue=Generic.getPropValues("QA_DOMAINTOPOST", BaseTest1.configPath),
			defaultHashUrlValue=Generic.getPropValues("QA_DEFAULTHASHURL", BaseTest1.configPath),
			merchantWebShellUrlValue=Generic.getPropValues("QA_MERCHANTWEBSHELL", BaseTest1.configPath);
	
	Log.info("======== Setting Merchant App for QA ========");
	
	Log.info("======== Entering App Package Name :"+appPackageNameValue+" ========");
	appPackageName.click();
	currentField.clear();
	currentField.sendKeys(appPackageNameValue);
	okBtn.click();
	
	Log.info("======== Entering IAP Domain To Post : "+domainToPostValue+" ========");
	domainToPost.click();
	currentField.clear();
	currentField.sendKeys(domainToPostValue);
	okBtn.click();
	
	Log.info("======== Entering default Hash Gen URL : "+defaultHashUrlValue+" =========");
	Generic.scroll(driver, "hash Gen").click();
	currentField.clear();
	currentField.sendKeys(defaultHashUrlValue);
	okBtn.click();
	
	// Merchant Web Shell same for staging and qa
	/*Log.info("======== Entering Web Shell URL : "+merchantWebShellUrlValue+" ========");
	Generic.scroll(driver, "Web Shell").click();
	currentField.clear();
	currentField.sendKeys(merchantWebShellUrlValue);
	okBtn.click();*/	
	
	Generic.swipeUp(driver);
	enterStaticOrDynamicId(BaseTest.getMerchantType());
	//driver.navigate().back();	
} 

/**
 * Checks whether the current settings are for QA environment or not.
 *
 * @return true, if current settings are already for QA environment.
 */
public boolean checkQASettings()
{
	String qaChecker=defaultPackageNameValue.getText();
	
	Log.info("======== Checking Merchant package name : "+qaChecker+" ========");
	if(qaChecker.contains("qa") && qaChecker.length()<60)
	{
		Log.info("======== Merchant QA settings already set : "+qaChecker+" ========");
		return true;
	}
	else
		return false;
	
}

/**
 * Sets the Staging environment settings for Merchant App
 */
public void setStagingSettings()
{
	setWebOverlay();
	if(checkStagingSettings()) 
	{
		enterStaticOrDynamicId(BaseTest.getMerchantType()); 		
		//driver.navigate().back();
		return;
	}
	
	/*Log.info("======== Setting default staging settings by clearing Merchant app data ========");
	
	String merchantPackage=Generic.getPropValues("MERCHANTPACKAGE",BaseTest1.configPath);
	
	String adbClearMerchant="adb shell pm clear "+merchantPackage;
	
	try	{ Runtime.getRuntime().exec(adbClearMerchant); } catch (IOException e){}
	
	Generic.wait(5);
	
	Log.info("======== Relaunching merchant app ========");
	String adbLaunchMerchant="adb shell monkey -p "+merchantPackage+" -c android.intent.category.LAUNCHER 1";
	
	try { Runtime.getRuntime().exec(adbLaunchMerchant); } catch (IOException e){}
	
	Generic.wait(5);*/
	
	String appPackageNameValue=Generic.getPropValues("STAGING_APP_PACKAGE_NAME", BaseTest1.configPath),
			domainToPostValue=Generic.getPropValues("STAGING_DOMAINTOPOST", BaseTest1.configPath),
			defaultHashUrlValue=Generic.getPropValues("STAGING_DEFAULTHASHURL", BaseTest1.configPath),
			merchantWebShellUrlValue=Generic.getPropValues("STAGING_MERCHANTWEBSHELL", BaseTest1.configPath);
	
	Log.info("======== Setting Merchant App for Staging ========");
	
	Log.info("======== Entering App Package Name :"+appPackageNameValue+" ========");
	appPackageName.click();
	currentField.clear();
	currentField.sendKeys(appPackageNameValue);
	okBtn.click();
	
	Log.info("======== Entering IAP Domain To Post : "+domainToPostValue+" ========");
	domainToPost.click();
	currentField.clear();
	currentField.sendKeys(domainToPostValue);
	okBtn.click();
	
	Log.info("======== Entering default Hash Gen URL : "+defaultHashUrlValue+" =========");
	Generic.scroll(driver, "hash Gen").click();
	currentField.clear();
	currentField.sendKeys(defaultHashUrlValue);
	okBtn.click();
	
	Log.info("======== Entering Web Shell URL : "+merchantWebShellUrlValue+" ========");
	Generic.scroll(driver, "Web Shell").click();
	currentField.clear();
	currentField.sendKeys(merchantWebShellUrlValue);
	okBtn.click();
	
	
	Generic.swipeUp(driver);
	enterStaticOrDynamicId(BaseTest.getMerchantType());
	
	//driver.navigate().back();
}

public void setWebOverlay()
{
	String appPackageName=appPkgName.getText();
	System.out.println("App Package Name in Merchamt App Settings : "+appPackageName);
	
	if(BaseTest1.webOverlayStatus.get(Generic.getUdId(driver))==true && !appPackageName.contains("1")) 
	{
		System.out.println("Changing App package name for Web Overlay. Invisibility");
		appPkgName.click();
		currentField.clear();
		currentField.sendKeys(appPackageName+"1");		
		Log.info("==== App Settings Set for Web Overlay ====");
		okBtn.click();
	}
	
	if(BaseTest1.webOverlayStatus.get(Generic.getUdId(driver))==false && appPackageName.contains("1")) 
	{
		System.out.println("Changing App package name for Visibility");
		appPkgName.click();
		currentField.clear();
		currentField.sendKeys(appPackageName.replace("1",""));		
		okBtn.click();
	}
	
	
	Log.info("======== Adjusting App Package Name for Web Overlay ========");
	//click on App Package name
	// enter getPropVal (app package name +"1")	
}

/**
 * Checks whether the current settings are for Staging environment or not.
 *
 * @return true, if current settings are already for Staging environment.
 */
public boolean checkStagingSettings()
{
	Generic.wait(1); // UiAuto2 Workaround 
	
	String stagingChecker=defaultPackageNameValue.getText().toLowerCase();
	
	Log.info("======== Checking Merchant package name : "+stagingChecker+" ========");
	if(stagingChecker.contains("staging") && stagingChecker.length()<60)
	{
		Log.info("======== Merchant Staging settings already set ========");
		return true;
	}
	else
		return false;
	
}

/**
 * Sets incorrect hash value corresponding to static or dynamic merchant
 *
 * @return true, if current settings are already for Staging environment.
 */
public void setHashFailValue()
{
	if(BaseTest.merchantType=="static")
	{
		if(dynamicMerchantCheckbox.getAttribute("checked").equals("false"))		
			dynamicMerchantCheckbox.click();
	}
	else
	{
		if(dynamicMerchantCheckbox.getAttribute("checked").equals("true"))		
			dynamicMerchantCheckbox.click();
	}
}

/**
 * Sets correct hash value corresponding to static or dynamic merchant
 *
 * @return true, if current settings are already for Staging environment.
 */
public void setHashCorrectValue()
{
	if(BaseTest.merchantType=="static")
	{
		if(dynamicMerchantCheckbox.getAttribute("checked").equals("true"))		
			dynamicMerchantCheckbox.click();
	}
	else
	{
		if(dynamicMerchantCheckbox.getAttribute("checked").equals("false"))		
			dynamicMerchantCheckbox.click();
	}
}

public void setMerchant(String merchantId, String merchantAppId) 
{
	iapMerchantId.click();
	Log.info("======== Entering Merchant ID : "+merchantId+" ========" );
	iapMerchantId.sendKeys(merchantId);
	okBtn.click();	
	
	Generic.scroll(driver,"Wibmo IAP Merchant App ID").click();
	Log.info("======== Entering Wibmo IAP Merchant App ID :"+merchantAppId+" ========" );	
	iapMerchantAppId.sendKeys(merchantAppId);
	okBtn.click();	
}

public void enterMobAndEmailInMerchantSettings(String mobile,String email) {
	Generic.swipeToBottom(driver);
	Generic.scroll(driver, "iap Customer Mobile").click();;
	
	//merchantPassingCustomerMobNo.click();
	currentField.clear();
	currentField.sendKeys(mobile);
	okBtn.click();
	Generic.scroll(driver, "iap Customer Email").click();

	//merchantPassingCustomerEmail.click();
	currentField.clear();
	currentField.sendKeys(email);
	okBtn.click();
	Generic.wait(2000);
	Generic.swipeUp(driver);
	
}

}
