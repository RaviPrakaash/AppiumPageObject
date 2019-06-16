package library;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.io.Zip;
import com.libraries.Log;

/**
 * The Class BackUp.
 */
public class BackUp {
	
	/** The sdf date. */
	static SimpleDateFormat sdfDate;
	
	/** The path. */
	static String path = System.getProperty("user.dir").replace("\\", "\\");	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		int b;
		File curr_out = null;
	try{	
		
		Log.info("= Generating Reports Please Wait =");
		
		try
		{
			File file_in = new File(path+"\\target\\surefire-reports\\testng-results.xml");
			File file_out = new File(path+"\\Result_Archive\\results.txt");
			
			new File(path+"\\Reports\\results_archive\\current").mkdir();
			try{Thread.sleep(3000);}catch(Exception e){}
			
			curr_out = new File(path+"\\Reports\\results_archive\\current\\results.txt");
			FileInputStream fis = new FileInputStream(file_in);
			FileOutputStream fos = new FileOutputStream(file_out);
			FileOutputStream fos1 = new FileOutputStream(curr_out);
			
			try {
				Log.info("= Copying results =");
				while ( (b= fis.read()) != -1 )
					try {
						fos.write(b);
						fos1.write(b);
					} catch (IOException e) {
						System.err.println("-----------------------------------------------------------");
						System.err.println("Unable to write in to results.txt in Result_Archive folder");
						Log.info("Unable to write in to results.txt in Result_Archive folder\n"+e.getMessage());
						e.printStackTrace();
						System.err.println("-----------------------------------------------------------");
					}
			} catch (IOException e) {
				System.err.println("-----------------------------------------------------------");
				System.err.println("Error in reading target\\surefire-reports\\testng-results.xml file or  Result_Archive\\results.txt");
				Log.info("Error in reading target\\surefire-reports\\testng-results.xml file or  Result_Archive\\results.txt \n"+e.getMessage());
				e.printStackTrace();
				System.err.println("-----------------------------------------------------------");
			}			
		} 
		
		catch (FileNotFoundException e) {
			
			System.err.println("-----------------------------------------------------------");
			System.err.println("Error in locating target\\surefire-reports\\testng-results.xml file or  Result_Archive\\results.txt");
			Log.info("Error in locating target\\surefire-reports\\testng-results.xml file or  Result_Archive\\results.txt \n"+e.getMessage());
			e.printStackTrace();
			System.err.println("-----------------------------------------------------------");
		}
		
		
		sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String strDate =sdfDate.format(now).replace(":", "_");
		
		new File(path+"\\Reports\\results_archive\\"+strDate).mkdir();
		
		File file_to_compress = new File(path+"\\target\\surefire-reports");
		File compress_file_to = new File(path+"\\Result_Archive\\"+strDate+".zip");
		Zip z = new Zip();
		Log.info("= Archiving Reports =");
		try
		{
			//z.zip(file_to_compress, compress_file_to);
			File result_file = new File(path+"\\target\\surefire-reports\\testng-results.xml");
			FileUtils.copyFile(result_file, new File(path+"\\Result_Archive\\Build_Results\\"+strDate+".txt"));	
			try{Thread.sleep(3000);}catch(Exception e){}
		}
		catch(Exception e)
		{
			System.err.println("-----------------------------------------------------------");
			System.err.println("Unable to zip target\\surefire-reports folder");
			Log.info("Unable to zip target\\surefire-reports folder"+e.getMessage());
			e.printStackTrace();
			System.err.println("-----------------------------------------------------------");
		}
		
		File file_to_delete1 = new File(path+"\\Reports\\results_archive\\foldernames.txt");
		File file_to_delete2 = new File(path+"\\Reports\\results_archive\\file.csv");
		Log.info("= Clearing csv =");
		try
		{
			FileUtils.forceDelete(file_to_delete1);
			FileUtils.forceDelete(file_to_delete2);
		}
		catch(Exception e)
		{
			System.err.println("-----------------------------------------------------------");
			System.err.println("Unable to delete file foldernames.txt or file.csv");
			Log.info("Unable to delete file foldernames.txt or file.csv\n"+e.getMessage());
			e.printStackTrace();
			System.err.println("-----------------------------------------------------------");
		}
		
		File[] file1 = sortFolderName();
		
		for (int i=0;i<file1.length;i++) {
			writeToCsv1(file1[i].getName(), true);		        
		}
		
		File read_file = new File(path+"\\Reports\\results_archive\\file.csv");
		
		FileUtils.copyFile(read_file, new File(path+"\\Reports\\results_archive\\foldernames.txt"));
		
		Log.info("-Updating current screenshots-");		
		FileUtils.copyDirectory(new File(path+"\\screenshots"), new File(path+"\\Reports\\results_archive\\current\\screenshots"));		
		
		Log.info("-Updating archive screenshots-");		
		FileUtils.copyDirectory(new File(path+"\\screenshots"), new File(path+"\\Reports\\results_archive\\"+strDate+"\\screenshots"));
		
		try{
		
		FileUtils.copyFile(curr_out, new File(path+"\\Reports\\results_archive\\"+strDate+"\\results.txt"));
		
		FileUtils.copyFile(new File(path+"\\basicinfo.txt"), new File(path+"\\Reports\\results_archive\\current\\basicinfo.txt"));
		
		FileUtils.copyFile(new File(path+"\\basicinfo.txt"), new File(path+"\\Reports\\results_archive\\"+strDate+"\\basicinfo.txt"));
		
		}
		catch(Exception e)
		{
			Log.info("Error in copying results and basicinfo to results_archive\n"+e.getMessage());
		}
	}
	catch(Exception e){Log.info("Error in executing main :\n"+e.getMessage());e.printStackTrace();}
		
	Log.info("======== Reports generated under Reports Folder-->index.html ========");
	try{Thread.sleep(3000);}catch(Exception e){}
		
		// ----  Currently email functionality is not used ---- // 
		//BackUp.createEmailReport();
		/*if(ExcelLibrary.getExcelData(path+"\\config\\config.xls", "config", 1, 2).equals("Yes"))
		{			
			sendEmail();
		}
		if(ExcelLibrary.getExcelData(path+"\\config\\config.xls", "config", 1, 2).equals("No"))
		{}*/
		
	}

	/**
	 * Creates the email report.
	 */
	static void createEmailReport()
	{
		 try {
			 		File file_to_delete = new File(path+"\\email_report\\report_zip");
			 		try
					{
						FileUtils.deleteDirectory(file_to_delete);
					}
					catch(Exception e)
					{	
						System.err.println("-----------------------------------------------------------");
						System.err.println("Unable to delete folder email_report\\report_zip ");
						e.printStackTrace();
						System.err.println("-----------------------------------------------------------");
					}
			 		FileUtils.copyFile(new File(path+"\\Reports\\css\\customstyle.css"), new File(path+"\\email_report\\Reports\\css\\customstyle.css"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\html_reports\\buildhistory.html"), new File(path+"\\email_report\\Reports\\html_reports\\buildhistory.html"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\html_reports\\failures.html"), new File(path+"\\email_report\\Reports\\html_reports\\failures.html"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\html_reports\\result.html"), new File(path+"\\email_report\\Reports\\html_reports\\result.html"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\js\\custom.js"), new File(path+"\\email_report\\Reports\\js\\custom.js"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\js\\highcharts.js"), new File(path+"\\email_report\\Reports\\js\\highcharts.js"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\js\\highcharts-3d.js"), new File(path+"\\email_report\\Reports\\js\\highcharts-3d.js"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\js\\jquery.highchartTable-min.js"), new File(path+"\\email_report\\Reports\\js\\jquery.highchartTable-min.js"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\js\\jquery-1.11.2.js"), new File(path+"\\email_report\\Reports\\js\\jquery-1.11.2.js"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\js\\jquery-1.11.2.min.js"), new File(path+"\\email_report\\Reports\\js\\jquery-1.11.2.min.js"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\images\\build_history.png"), new File(path+"\\email_report\\Reports\\images\\build_history.png"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\images\\current_report.png"), new File(path+"\\email_report\\Reports\\images\\current_report.png"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\results_archive\\current\\basicinfo.txt"), new File(path+"\\email_report\\Reports\\results_archive\\current\\basicinfo.txt"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\results_archive\\current\\results.txt"), new File(path+"\\email_report\\Reports\\results_archive\\current\\results.txt"));
			 		File source = new File(path+"\\Reports\\results_archive\\current\\screenshots");

			 		File dest = new File(path+"\\email_report\\Reports\\results_archive\\current\\screenshots");
			 		// Create a file filter which represents "*.jpg"
			 		FilenameFilter jpgFilter;
			 		jpgFilter = new FilenameFilter() {

			 		    public boolean accept(File dir, String name) {

			 		        return name.toUpperCase().endsWith(".JPG");

			 		    }

			 		};
			 		// generate the list of files ending in ".jpg" in the source directory
			 		File[] jpgs = source.listFiles(jpgFilter);
			 		for (File thisSource : jpgs) {
			 		    // construct the destination path
			 		    File thisDest = new File(path+"\\email_report\\Reports\\results_archive\\current\\screenshots" + "\\" + thisSource.getName());
			 		    // attempt to move the file
			 		    boolean success = thisSource.renameTo(thisDest);
			 		    if (!success) {
			 		        //throw new RuntimeException("Moving file " + thisSource + " to " + thisDest + " failed.");
			 		    	Log.info("Exception in moving "+thisSource);
			 		    }
			 		}
			 		
			 		FileUtils.copyFile(new File(path+"\\Reports\\results_archive\\foldernames.txt"), new File(path+"\\email_report\\Reports\\results_archive\\foldernames.txt"));
			 		FileUtils.copyFile(new File(path+"\\Reports\\index.html"), new File(path+"\\email_report\\Reports\\index.html"));
			 		File file_to_compress = new File(path+"\\email_report\\Reports");
			 		new File(path+"\\email_report\\report_zip").mkdir();
			 		File compress_file_to = new File(path+"\\email_report\\report_zip\\report"+".zip");
					Zip z = new Zip();
					try
					{
						Thread.sleep(100);
						//z.zip(file_to_compress, compress_file_to);						
					}
					catch(Exception e)
					{

						System.err.println("-----------------------------------------------------------");
						System.err.println("Unable to zip the results ");
						e.printStackTrace();
						System.err.println("-----------------------------------------------------------");
					}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Send email.
	 */
	static void sendEmail()
	{
			String host = ExcelLibrary.getExcelData(path+"\\config\\config.xls", "Email_settings", 1, 0);
	        String port = ExcelLibrary.getExcelData(path+"\\config\\config.xls", "Email_settings", 1, 1);
	        String mailFrom = ExcelLibrary.getExcelData(path+"\\config\\config.xls", "Email_settings", 1, 2);
	        String password = ExcelLibrary.getExcelData(path+"\\config\\config.xls", "Email_settings", 1, 3);
	        // message info
	        String mailTo = ExcelLibrary.getExcelData(path+"\\config\\config.xls", "Email_settings", 1, 4);
	        String subject = ExcelLibrary.getExcelData(path+"\\config\\config.xls", "Email_settings", 1, 5);
	        String message = ExcelLibrary.getExcelData(path+"\\config\\config.xls", "Email_settings", 1, 6);
	        // attachments
	        String[] attachFiles = new String[1];
	        attachFiles[0] = path+"\\email_report\\report_zip\\report.zip";
	 
	        try {
	            sendEmailWithAttachments(host, port, mailFrom, password, mailTo,
	                subject, message, attachFiles);
	     
	        } catch (Exception ex) {
	        	System.err.println("-----------------------------------------------------------------------------------------------------------------------------");
				System.err.println("Please Check The Configuration Excel file under config folder and make sure to fill all the fields in Email_Settings sheet");
				System.err.println("-----------------------------------------------------------------------------------------------------------------------------");
				System.exit(0);
	        }
	}
	
	
	/**
	 * Write to csv 1.
	 *
	 * @param val the val
	 * @param finalVal the final val
	 */
	static public void writeToCsv1(String val, boolean finalVal){
		try {
			BufferedWriter b = new BufferedWriter(new FileWriter(new File(path+"\\Reports\\results_archive\\file.csv"),true));
			b.write(val+",");
			if(finalVal){
				b.write("");
			}
			b.close();		
		} catch (IOException e) {

			System.out.println(e);
		}
	}
	
	/**
	 * Send email with attachments.
	 *
	 * @param host the host
	 * @param port the port
	 * @param userName the user name
	 * @param password the password
	 * @param toAddress the to address
	 * @param subject the subject
	 * @param message the message
	 * @param attachFiles the attach files
	 * @throws AddressException the address exception
	 * @throws MessagingException the messaging exception
	 */
	public static void sendEmailWithAttachments(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message, String[] attachFiles) throws AddressException, MessagingException
           {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
            }
        }
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        // sends the e-mail
        Transport.send(msg);
 
    }
	
	/**
	 * Sort folder name.
	 *
	 * @return the file[]
	 */
	private static File[] sortFolderName()
	{
		Log.info("= Parsing Archive =");
		File dir = new File(path+"\\Reports\\results_archive");
	    File[] files = dir.listFiles();
	    FileFilter fileFilter = new FileFilter() {
	       public boolean accept(File file) {
	          return file.isDirectory();
	       }
	    };
	    files = dir.listFiles(fileFilter);
	    
	    if (files.length == 0) {
	       System.out.println("Either dir does not exist or is not a directory");
	    }
	    else {
	       for (int i=0; i< files.length; i++) {
	          File filename = files[i];
//	          System.out.println(filename.toString());
	       }
	    }
	    
	    Arrays.sort( files, new Comparator()
	    {
	        public int compare(Object o1, Object o2) {

	            if (((File)o1).lastModified() > ((File)o2).lastModified()) {
	                return -1;
	            } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
	                return +1;	                
	            } else {
	                return 0;
	            }
	        }

	    });
		return files;
	}
	

}
