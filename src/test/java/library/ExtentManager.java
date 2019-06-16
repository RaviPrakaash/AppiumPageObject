package library;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.Zip;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;

/**
 * The Class ExtentManager for report generation.
 */
public class ExtentManager {
	
	/** The extent object. */
	static ExtentReports extent;
	
	static String path;
    
    /** The file path of Reports generated. */
    static String filePath;
    
    /** The screen path for screens generated. */
    public static String screenPath;
    
    public static String timeStamp;
    
    /**
     * Generates file path for reports
     */
    public static void generateFilePath() {
    	timeStamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());
    	path=System.getProperty("user.dir").replace("\\", "\\");
    	filePath=path+"/ExtReport/Report_"+timeStamp+"/Report_"+timeStamp+".html"; 
    	screenPath=path+"/ExtReport/Report_"+timeStamp+"/";    	
    	
    	System.out.println("Extent Filepath : "+filePath);
	}
    
    /**
     * Gets the reporter for Extent Reports.
     *
     * @return the reporter
     */
    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            extent = new ExtentReports(filePath, true);
            //extent.x(); // Use only on MongoDB
            extent.loadConfig(new File("config/extent-config.xml"));	            
        }        
        return extent;
    }   
    
    public static void createExtZip()
    {
    	if(!ExcelLibrary.getExcelData(".\\config\\config.xls", "Android_setup", 8,7).contains("jenkins"))
    		return;
    	
    	System.out.println("======== Archiving Extent ========");
    	
    	resetZipDir();
    	
    	getDashboard();
    	
    	File file_to_compress = new File(path+"/ExtReport/Report_"+timeStamp);
    	File compress_file_to = new File(path+"\\ExtReport\\ExtZip\\Report_"+timeStamp+".zip");
    	
    	Zip z = new Zip();
    	try
		{
			Thread.sleep(100);
			//z.zip(file_to_compress, compress_file_to);						
		}
		catch(Exception e)
		{
			System.err.println("-----------------------------------------------------------");
			System.err.println("Unable to zip Extent results ");
			e.printStackTrace();
			System.err.println("-----------------------------------------------------------");
		}
    }
    
    public static void resetZipDir()
    {
    	File dir_to_delete = new File(path+"\\ExtReport\\ExtZip");
    	try
    	{
    		FileUtils.deleteDirectory(dir_to_delete);
    		Thread.sleep(2000);
    		new File(path+"\\ExtReport\\ExtZip").mkdir();
    	}
    	catch(Exception e)
    	{
    		System.err.println("Unable to reset ExtZip");e.printStackTrace();
    	}
    }
    
    public static void getDashboard()
    {
      try{
	    	WebDriver driver = new FirefoxDriver();
	    	String htmlPath=filePath.replace("\\", "\\\\").replace("/", "\\\\");
	    	
	    	System.out.println("Result path : "+htmlPath);
	    	
	    	driver.get(htmlPath);	
	    	driver.manage().window().maximize();
	    	driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	    	new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.className("mdi-action-track-changes")));
	    	driver.findElement(By.className("mdi-action-track-changes")).click();
	    	Thread.sleep(2000);	    	
	
			if (driver != null)
	        {
	            File f = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	            try 
	            {
	            	FileUtils.copyFile(f, new File((path+"\\ExtReport\\ExtZip\\ReportSnapshot.png")));	            	
				} 
	            catch (Exception e) 
	            {
					System.err.println("Dashboard Exception");
					e.printStackTrace(); 
				}			
	        } 
			driver.close();
    	}
    	catch(Exception e)
    	{
    		System.err.println("Error opening result file");
    		e.printStackTrace();
    	}
    	
    }
    
    
}
