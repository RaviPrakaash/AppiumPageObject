package library;

import java.io.File;
import org.apache.commons.io.FileUtils;

public class SetTestDataFile 
{
	private static String env="";
	
	public static void main(String[] args) 
	{
		String jenkinsParam="";
		if (args.length>0)
		{
			jenkinsParam=args[0];
			System.out.println("Param :"+jenkinsParam);			
		}
		
		
		if(jenkinsParam=="") // If not jenkins read environment from config
		{
			
			String packageName=ExcelLibrary.getExcelData(".\\config\\config.xls", "Android_setup", 8,5);
			if(packageName.contains("staging"))
				env="staging";
			if(packageName.contains("qa"))
				env="qa";			
		}
		else
		{
			env=jenkinsParam;
			jenkinsConfigSelection();			
		}	
		
		System.out.println("Environment : "+env);
		String excelPath="./excel_lib/"+env+"/TestData.xls";
		try
		{
			System.out.println("Copying TestData");
			FileUtils.copyFile(new File(excelPath),new File("./excel_lib/TestData.xls"));
			System.out.println("TestData successfully copied from "+env+" folder");
		}
		catch(Exception e){System.out.println("File copy failed\n"+e.getMessage());}
		
		reEval();
	}
	
	public static void jenkinsConfigSelection()
	{
		String selectModule="Smoke"; // current jenkins configuration only for Smoke module
		
		
		// Communicate env to Maven execution
		ExcelLibrary.writeExcelData(".\\config\\config.xls", "Android_setup", 8,7,env);
		
		if(!ExcelLibrary.getExcelData(".\\config\\config.xls", "Android_setup", 8 , 5).contains("staging"))
			ExcelLibrary.writeExcelData(".\\config\\config.xls", "Android_setup", 8,5,"com.enstage.wibmo.staging.hdfc");
		
		
		// Selecting only Smoke as of now.
		
		try{
			for(int i=0;i<20;i++)
				if(ExcelLibrary.getExcelData(".\\config\\config.xls", "Modules", i , 0).contains(selectModule))	
				{				
					ExcelLibrary.writeExcelData(".\\config\\config.xls", "Modules", i , 1, "Yes");
					return;
				}
			}
		catch(Exception e)
			{
				System.out.println("Configuration setting was not done\n"+e.getMessage());
			}
	}
	
	public static void reEval()
	{
		try		
		{			
			ExcelLibrary.writeExcelData("./excel_lib/"+env+"/TestData.xls", "SmokeTest", 30, 10, "AutoGenerate");
			ExcelLibrary.writeExcelData("./excel_lib/"+env+"/TestData.xls", "Registration", 30, 10, "AutoGenerate");
			ExcelLibrary.writeExcelData("./excel_lib/"+env+"/TestData.xls", "SendMoney", 30, 10, "AutoGenerate");			
		}
		catch(Exception e)
		{
			System.err.println("Error in Auto generation of Test Data");
		}
	}

}
