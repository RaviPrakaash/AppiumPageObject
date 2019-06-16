package wibmo.app.testScripts.IAP_Transaction;



import library.Generic;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wibmo.app.testScripts.BaseTest1;
import wibmo.webapp.pagerepo.ConnectingPhonePage;
import wibmo.webapp.pagerepo.DeviceVerificationPage;
import wibmo.webapp.pagerepo.IAPPostReportPage;
import wibmo.webapp.pagerepo.Merchant3DSPage;
import wibmo.webapp.pagerepo.MerchantCheckOutPage;
import wibmo.webapp.pagerepo.MerchantMainPage;
import wibmo.webapp.pagerepo.OTPFetchPage;
import wibmo.webapp.pagerepo.PayZappLoginPage;
import wibmo.webapp.pagerepo.TestMerchantPage;

/**
 * The Class IAPTS_034 Web-SDK transaction using external card  & Successful data pick up

 */
public class IAPTS_034 extends BaseTest {

	/** The tc. */
	public String TC=getTestScenario("IAPTS_034");

	
	@BeforeMethod
	public void launchApplication()
	{		   
	  launchAsWebDriver();
	}	
	
	/**
	 * Iapts 034.
	 */
	@Test
	public void IAPTS_034()	
	{
		Reporter.log(getTestScenario());
		String data=getTestData("IAPTS_034");
		String str[]=data.split(",");
		double amt=Double.parseDouble(str[3]);
		String loginPassword=str[4];
		String balanceMobileNumber=str[1];
		String cardType=str[5];
		String securePin=str[6];		
		
		MerchantMainPage main=new MerchantMainPage(driver);
		main.basicInfoSubmit(data);
		
		MerchantCheckOutPage checkout=new MerchantCheckOutPage(driver); 
		checkout.merchantCheckout(data);
		
		ConnectingPhonePage cpp=new ConnectingPhonePage(driver);
		cpp.clickonskip();
		
		PayZappLoginPage login=new PayZappLoginPage(driver);
		login.addlogindetails(loginPassword);
		
		String otp=Generic.getOTP(balanceMobileNumber,bankCode,Generic.getPropValues("DVC", BaseTest1.configPath));
		
		DeviceVerificationPage dvc=new DeviceVerificationPage(driver);
		dvc.adddvcdetails(otp);
		
		TestMerchantPage merchant=new TestMerchantPage(driver);
		merchant.addtestmerchantdetails(cardType);
		
		Merchant3DSPage ds=new Merchant3DSPage(driver);
		ds.submitVisaVerification(securePin);
		
		IAPPostReportPage icpr=new IAPPostReportPage(driver);
		String transactionid=icpr.fetchwibmoid();	
		//icpr.checkDataPickup("chargeSuccessful,authenticationSuccessful");	hash fail for data pickup + wrong version displayed	
		
		//CSR.verifyIAPTransactionDataPickupSuccess(transactionid);	hash fail for datapickup ,  using v2 submit => no data pickup in CSR
	}
	
}
