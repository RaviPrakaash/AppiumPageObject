package library;

import com.relevantcodes.extentreports.LogStatus;

import wibmo.app.testScripts.BaseTest1;

//import com.libraries.Log; 
public class Log 
{
	public static void info(String infoMsg)
	{
		com.libraries.Log.info(infoMsg); if(BaseTest1.deviceTrialRun) return; // Distinguish between Individual Run & Suite Run
		
		ExtentTestManager.getTest().log(LogStatus.INFO,infoMsg);
	}
	
	public static void groupPass(String testName)
	{
		com.libraries.Log.info("========== Passed: "+testName+" ==========");if(BaseTest1.deviceTrialRun) return;
		
		ExtentTestManager.getTest().log(LogStatus.PASS,"========== Passed: "+testName+" ==========");
	}
	
	public static void skip(String skipMsg) // Please Note : This method is to be called from TestScripts only followed by script exit. 
	{
		com.libraries.Log.info(skipMsg);if(BaseTest1.deviceTrialRun) return;
		
		try
		{
			ExtentTestManager.getTest().log(LogStatus.SKIP,skipMsg);	
		}
		catch(Exception e)
		{
			System.err.println("Unable to log skip message to Extent  \n"+e.toString());e.printStackTrace();
		}			
	}
}
