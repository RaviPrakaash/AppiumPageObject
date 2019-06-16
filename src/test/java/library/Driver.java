package library;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class Driver
{
  static List<XmlClass> classes = null;
  static String pkgName = "";
  static int t = 0;
  
  public static void main(String[] args)
  {
    XmlSuite suite = new XmlSuite();
    suite.setName("Regression");
    //suite.setParallel("tests"); 
    suite.setPreserveOrder("true");
    
    int port=4722; // or use getPort();    
    
    int cr = ExcelLibrary.getExcelRowCount("./config/config.xls", "Program_Setup");
    int mr = ExcelLibrary.getExcelRowCount("./config/config.xls", "Modules");    
    
    for (int i = 0; i <= cr; i++) {   
    	
    	
    	// ==== If Program is selected Yes then only create xml file ==== //
      if (ExcelLibrary.getExcelData("./config/config.xls", "Program_Setup", i, 2).equalsIgnoreCase("yes"))
      {
    	  
    	  LinkedHashSet<String> devices=devices();
    	  if(devices.size()>1)
    	  {
    		  suite.setThreadCount(devices.size());
    		  suite.setParallel("tests"); 
    	  }
    	  
    	 for(String device : devices)
    	 {    	
    		 
	    	// ==== Assign Test and Test Name ==== //
	        pkgName = ExcelLibrary.getExcelData("./config/config.xls", "Program_Setup", i, 1);
	        String[] s = pkgName.split("\\.");
	        String bankName = s[(s.length - 1)];  
	        
	        XmlTest test = new XmlTest(suite);      // <---  
	        test.setName("Device_"+device.substring(0,4)+"_Test"); 
	        test.setPreserveOrder("true"); 
	        //test.setThreadCount(5); 
	        
	        // ==== Assign Parameter to Test ==== //
	        HashMap<String, String> m = new HashMap();
	        m.put("device-id", device);  
	      //  m.put("port-val", getPort()+""); port+=6; // or use getPort(); or Randomise()  on Observation
	        test.setParameters(m);
	        
	        // ==== Add classes from TestData.xls to Test==== //
	        classes = new ArrayList();        
	        
	        for (int j = 0; j <= mr; j++)
	        {
	          if ( ExcelLibrary.getExcelData("./config/config.xls", "Modules", j, 1).equalsIgnoreCase("yes") && ExcelLibrary.getExcelData("./config/config.xls", "Modules", j, 2).equalsIgnoreCase(device) ) 
	          { 
	        	
	            t += 1;
	            String module = ExcelLibrary.getExcelData("./config/config.xls", "Modules", j, 0);
	            
	            int tr = ExcelLibrary.getExcelRowCount("./excel_lib/TestData.xls", module);
	            for (int k = 0; k <= tr; k++) 
	            {
	              if (ExcelLibrary.getExcelData("./excel_lib/TestData.xls", module, k, 3).equalsIgnoreCase("yes"))
	              {
	                XmlClass cls = new XmlClass(); // Modules list and TestData sheet names should match 
	                cls.setName("wibmo.app.testScripts." + module + "." + ExcelLibrary.getExcelData("./excel_lib/TestData.xls", module, k, 0));	                
	                classes.add(cls);                  
	              }
	            } 
	          }
	        }	
	         test.setXmlClasses(classes);        
    	 }
        
      }
    }    
    
    // ==== Write Suite generated to scripts.xml ==== // 
    File f = new File("./scripts.xml");
    try
    {
      FileWriter writer = new FileWriter(f);
      writer.write(suite.toXml());
      writer.flush();
      writer.close();
    }
    catch (IOException localIOException) {}
    try
    {
      Thread.sleep(3000L);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    System.out.println("TestNG file generated");
  }
  
  public static LinkedHashSet<String> devices()
  {
	  LinkedHashSet<String> devices=new LinkedHashSet<String>();	 
	  	  
	  int mr = ExcelLibrary.getExcelRowCount("./config/config.xls", "Modules"); 
		  
	 for (int j = 0; j <= mr; j++)	        
	        if (ExcelLibrary.getExcelData("./config/config.xls", "Modules", j, 1).toLowerCase().contains("yes"))
	        		  devices.add(ExcelLibrary.getExcelData("./config/config.xls", "Modules", j, 2));
		  
	 System.out.println(":: Devices "+devices+" ::");
	 
	 checkDevices(devices);
	 
	  return devices;
  }
  
  public static void checkDevices(LinkedHashSet<String> devices)
  {
	  String checker=execCmd("adb devices");
	  
	  for(String device:devices)
		  if(!checker.contains(device))
		  {
			  System.err.println("-------------------------------------------------------------");
			  System.err.println(" -------- Device with udid "+device+" not detected -------- ");			 
			  System.err.println("-------------------------------------------------------------");
			  System.exit(0);
		  }
		  if(checker.contains("ffline"))
			  System.out.println("Warning : Device Offline ");			   
  }
  
  /**
	 * Executes a command line command and returns the output as a String  
	 *
	 * @return String the command output
	 */
	public static String execCmd(String cmd) 
	{
		try{
			java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");        
			return s.hasNext() ? s.next() : ""; // Implement close logic by storing in separate string before returning
			}
		catch(Exception e){System.err.println("Error in Executing Cmd ");e.printStackTrace(); return "";}
  }	
  
  private static String getPort()
	{	
		try
		{
			ServerSocket socket=new ServerSocket(0);
			socket.setReuseAddress(true);
			String port="";
			if(socket.getLocalPort()!= -1)
			{
				port=Integer.toString(socket.getLocalPort());
				socket.close();
				return port;
			}
			else
			{
				socket.close();
				return getPort();
			}
		}catch(Exception e){return getPort();}
		 catch(Error e){return getPort();} 
	}
}
