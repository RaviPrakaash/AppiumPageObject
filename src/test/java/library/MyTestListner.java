package library;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.libraries.ExcelLibrary;
import com.libraries.Log;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.android.AndroidDriver;

/**
 * The Class MyTestListner 
 */
public class MyTestListner extends TestListenerAdapter {	

	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onTestFailure(org.testng.ITestResult)
	 */
	public void onTestFailure(ITestResult result)
    {
		Object currentClass = result.getInstance(); 
		Log.info("========== Failed: "+((BaseClass) currentClass).getClassName()+" ==========");
		Log.info("Reason for Failure --- "+result.getThrowable().getMessage());
		Log.info( result.getThrowable().getStackTrace().toString());
		
		String fileName = BackUp.path+"/screenshots/"+((BaseClass) currentClass).getClassName()+".jpg";		
		WebDriver driver = ((BaseClass) currentClass).getDriver();
		Log.info("Failure --- Capturing screenshot");	
		//---------------------------------------------------------------//			
		if(CSR.getCSRStatus())
			driver=CSR.getDriver();			
		//---------------------------------------------------------------//
		
	try{	
		if (driver != null)
        {
            File f = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            try 
            {
            	FileUtils.copyFile(f, new File(fileName));
            	try{Thread.sleep(2000);}catch(Exception e){} // wait for copy 
            	//FileUtils.copyFile(f, new File(ExtentManager.screenPath+((BaseClass) currentClass).getClassName()+".jpg")); // Extent
                //try{Thread.sleep(2000);}catch(Exception e){}
            	
			} catch (IOException e) {
				e.printStackTrace(); }			
        }
		}
	catch(Exception e){Log.info("======== Error in obtaining screenshot ========\n"+e.getMessage());}
	
	// ==== E ==== //
	String extScreens="./"+((BaseClass) currentClass).getClassName()+".jpg";		
	//ExtentTestManager.getTest().log(LogStatus.FAIL,"HTML", "There is an error: <br/><br/> "+result.getThrowable().getMessage()+" <br/><br/> Error Snapshot : "+ ExtentTestManager.getTest().addScreenCapture(extScreens));
	// =========== //
    }	
	
	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onTestSkipped(org.testng.ITestResult)
	 */
	public void onTestSkipped(ITestResult result)
	{
		try
		{
			Object currentClass = result.getInstance();
			Log.info("========== Skipped: "+((BaseClass) currentClass).getClassName()+" ==========");
			System.out.println("Skipped");
		
			try{Runtime.getRuntime().exec("adb devices");}catch(Exception e){Log.info("======== Error in finding connected devices ========");try{Thread.sleep(5000);}catch(Exception e1){}}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		// ==== E ==== //
		//ExtentTestManager.getTest().log(LogStatus.SKIP,"========== Skipped: "+((BaseClass) currentClass).getClassName()+" ==========");
		// =========== //
	}
	
	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onTestSuccess(org.testng.ITestResult)
	 */
	public void onTestSuccess(ITestResult result)
	{
		Object currentClass = result.getInstance();
		Log.info("========== Passed: "+((BaseClass) currentClass).getClassName()+" ==========");
		System.out.println("Pass");
		
		// ==== E ==== //
		//ExtentTestManager.getTest().log(LogStatus.PASS,"========== Passed: "+((BaseClass) currentClass).getClassName()+" ==========");
		// =========== //
	}
	
	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onTestStart(org.testng.ITestResult)
	 */
	@Override
	public void onTestStart(ITestResult result) {
		/*Object currentClass = result.getInstance();
		Log.info("-------------------------------------- ~ ~ ~ --------------------------------------------------");Log.info("------------------------  ~ ~ ~   Start: "+((BaseClass) currentClass).getClassName()+ "       -----------------------------");	
		try {
			Class.forName(((BaseClass) currentClass).getCompleteClassName()).getDeclaredField("TC").setAccessible(true);Log.info("               Scenario: "+(String)Class.forName(((BaseClass) currentClass).getCompleteClassName()).getDeclaredField("TC").get(currentClass));((BaseClass) currentClass).setTestScenario((String)Class.forName(((BaseClass) currentClass).getCompleteClassName()).getDeclaredField("TC").get(currentClass));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Log.info("------------------------------------ ~ ~ ~ ----------------------------------------------------");
		*/
	}
	
	
}
