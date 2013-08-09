package com.hp.RecycleBin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.filechooser.FileFilter;

import org.apache.bcel.classfile.Constant;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
//import org.testng.log4testng.Logger;


import com.hp.utility.FileUtil;

public class EmailReporter {

	
    private static Logger logger=Logger.getLogger(EmailReporter.class);
    String path=new File(".").getAbsolutePath();
	String probasepath=path.substring(0, path.length()-1);
	 
	//constant 
    public  final String RUN_RESULT_PATH=probasepath+"reporter\\reporter.log";
    public  final String REPORT_TEMPLATE_FILE_NAME="result_template";
    public  final String REPORT_TEMPLATE_PATH=probasepath+"resources\\"+REPORT_TEMPLATE_FILE_NAME+".htm";
    public  final String REPORTER_PATH=probasepath+"reporter";
    public  final String ERROR_SCREENSHOT_PATH=probasepath+"reporter";
    
    public final String CURRENT_TIME=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    public final String CURRENT_TIMEZONE=new SimpleDateFormat("EEEE,MMMM dd,yyyy h:mm:ss a z").format(Calendar.getInstance().getTime());
	
    public final String TODAY_REPORT=REPORTER_PATH+"\\"+REPORT_TEMPLATE_FILE_NAME+"_"+CURRENT_TIME+".htm";
    
    public final int TOTAL_TEST_CASES=25;
    
    public final String IMAGE_SPLITE_CODE="<span id=\"screenshotpart\" />";
	
	/*
	 * send the email via SMTP Sever ,this is the main test
	 * param: the email setting data from the excel file
	 */
	@Test(dataProviderClass=com.hp.dataprovider.ExcelDataProivderEmailSheet.class,dataProvider="devEmail")
	public void sendEmail(Map<String,String> emailmap) 
	{
	
		String smtp_server=emailmap.get("SMTP_HOST").trim();
		String username=emailmap.get("AUTHENUSER").trim();
		String password=emailmap.get("AUTHENPASS").trim();
		String from= emailmap.get("FROM_EMAIL").trim();
		String to=emailmap.get("TO_EMAIL").trim();
		String subject=emailmap.get("SUBJECT").trim();
		String isauthen=emailmap.get("ISAUTHEN").trim();
		if(isauthen.equals("yes")){
		sendMultiEmail(smtp_server,true,username,password,from,to,subject);
		}
		else{
			sendMultiEmail(smtp_server,false,username,password,from,to,subject);
		}
	}
	
    public void sendMultiEmail(String smtpserver,boolean authenuser,final String username,final String password,String from,String to,String subject)
    {
    	  try{
    			String currenttime=new SimpleDateFormat("EEEE,MMMM dd,yyyy h:mm:ss a z").format(Calendar.getInstance().getTime());
    		
    			Properties prop=new Properties();
    		//	prop.put("mail.smtp.auth", "true");
    			prop.put("mail.smtp.host", smtpserver);	
    			prop.put("mail.smtp.port", "25");
    			prop.put("mail.debug", "true");
    			Session session=null;
    			if(authenuser){
    				prop.put("mail.smtp.auth", "true");    				
    				prop.put("mail.smtp.starttls.enable", "true");
    				session=Session.getDefaultInstance(prop, new Authenticator() {
    					@Override
    					protected PasswordAuthentication getPasswordAuthentication() {
    						// TODO Auto-generated method stub
    						return new PasswordAuthentication(username, password);
    					}
					})	;
    			}
    			else{
    			   session=Session.getDefaultInstance(prop);
    			}
    			
    			String subjecttitle=enumValues();
    			//email settings
    			MimeMessage mime=new MimeMessage(session);
    			mime.setFrom(new InternetAddress(from));
    			mime.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
    			mime.setSubject("["+subjecttitle+"] "+subject+" "+currenttime+"--");
    			mime.setSentDate(new Date());
    		
    			//set multipart email
    			MimeMultipart multipart=new MimeMultipart("related");
    			
    			//read the today's report content and send the email
    			String htmlcontents= FileUtil.returnFileContents(TODAY_REPORT);
    			logger.debug("read all the today's report content into the string already now");
    			
    			BodyPart bodypart=new MimeBodyPart();
    			bodypart.setContent(htmlcontents, "text/html;charset=UTF-8");
    			logger.debug("now add the html content into the email's body content");
    			bodypart.setHeader("Content-Type", "text/html");
    			//first set the body main content		
    			multipart.addBodyPart(bodypart);
    			logger.debug("complete parsing the html body content");
    	        
    	        //add the image file
    			int filesize=FileUtil.isFilesExisting(ERROR_SCREENSHOT_PATH,".png");		
    		    File[] errorshotfile=FileUtil.listFiles(ERROR_SCREENSHOT_PATH, ".png");
    			if(filesize>0)
    			{
   				    logger.debug("the current testing met the error and generate an error screenshot we will attach it into the email");
    				for(int fileindex=0;fileindex<filesize;fileindex++)
    				{
    					int imagecount=fileindex+1;
    					String errorfilepath=errorshotfile[fileindex].getAbsolutePath();			
   					    bodypart = new MimeBodyPart();
    					DataSource fds = new FileDataSource (errorfilepath);
    					bodypart.setDataHandler(new DataHandler(fds));
    					logger.debug("the error screenshot file long path is :"+errorfilepath);
    					logger.debug("the email's image content id is :image"+imagecount+"@hp");
    					//be careful this content must contain with <>
   					    bodypart.addHeader("Content-ID","<image"+imagecount+"@hp>");
    					bodypart.setDisposition("inline");    					
    					multipart.addBodyPart(bodypart);
   					    logger.debug("add the image embled into the html for this screenshot file :"+errorfilepath);
    				}
     			}
    			logger.debug("complete parsing the image body content");
    			 
    			// Part two is attachment,set the email's body  attachments
    			bodypart = new MimeBodyPart();
    		    DataSource source = new FileDataSource(RUN_RESULT_PATH);
    		    bodypart.setDataHandler(new DataHandler(source));
    		    bodypart.setFileName("reporter.log");
    			multipart.addBodyPart(bodypart);
    			logger.debug("complete parsing the attachment body content");        
    						
    	        //// Send the complete message parts	
    			mime.setContent(multipart,"text/html;charset=UTF-8");
    			logger.debug("now add  all the contents into html body,is sending the email now.....");
    			Transport.send(mime);
    			logger.debug("now  send the email successfully......");
    			//transport.close();
    		  }
    		  catch(MessagingException  e)
    		  {
    			  logger.error("sorry met the MessagingException  error cannot send the email ");
    			  logger.error(e);
    		  }
    }
    /*
     * update the values in html content
     */
    public String enumValues(){
    	Map<String,String> fieldpairs=new TreeMap<String, String>();
    	
    	//write the total numbers for the cases
    	int totalcases=TOTAL_TEST_CASES;   	
    	int passcases=FileUtil.textTimes("pass");
    	int failedcases=FileUtil.textTimes("fail");   	    	
    	int noruncases=totalcases-passcases-failedcases;
    	fieldpairs.put("CURRENT_TIME",CURRENT_TIMEZONE );
    	fieldpairs.put("TOTAL_NUMBERS", String.valueOf(totalcases));
    	fieldpairs.put("PASSED_NUMBERS", String.valueOf(passcases));
    	fieldpairs.put("FAILED_NUMBERS", String.valueOf(failedcases));
    	fieldpairs.put("NORUN_NUMBERS", String.valueOf(noruncases));
    	
    	
    	// this is all the fields we need to replace
    	for(Fields field:Fields.values())
    	{   	
    		//get the field name compare in the reporter file to find this field or not 
    		String reportresult=FileUtil.getValue(field.name());
    		if(reportresult==null)
    		{
    			fieldpairs.put(field.name(), "No Run");
    		}
    		else
    		{
    			fieldpairs.put(field.name(), reportresult);
    		}
    	}
    
    	//read the template file 
    	String returncontents=FileUtil.returnFileContents(REPORT_TEMPLATE_PATH);
    	for(String strkey: fieldpairs.keySet())
    	{
    		returncontents=returncontents.replace(strkey,fieldpairs.get(strkey));
    	}
    	
    	logger.debug("the replaced String is "+returncontents);
    	
    	//image attached
    	String bodytext=imageSpliteHtmlContents(returncontents);
    	
    	
       //create the new template file
    	boolean iscreated=FileUtil.writeContents(TODAY_REPORT,bodytext);
    	if(passcases==totalcases){
    		return "Passed";
    	}
    	else if(failedcases>0){
    		return "failed";
    	}
    	else if(noruncases==totalcases){
    		return "No Run";
    	}
    	else
    	{
    		return "Not Completed";
    	}
    	
    }
    
    /*
     * embela the image into html
     */
    public String imageSpliteHtmlContents(String originalcontents)
    {
    	String imagecontainer="";
		String[] mainpart=originalcontents.split(IMAGE_SPLITE_CODE);
		String imagepart=mainpart[0];
		String footerpart=mainpart[1];
		String imagecode_beginner=" <tr style='mso-yfti-irow:0;mso-yfti-lastrow:yes;border:8'>"+
                       "<td><p>";
		String imagecode_middle="</p>"+
                       "</td>"+
                       "<td><span> <img src=\"cid:image";
		String imagecode_screenshot="@hp\" alt=\" this image cannot show,maybe blocked by your email setting \"><br>" +
				       "</span>"+
                       "</td></tr>";				
		int filesize=FileUtil.isFilesExisting(ERROR_SCREENSHOT_PATH,".png");		
	    File[] errorshotfile=FileUtil.listFiles(ERROR_SCREENSHOT_PATH, ".png");
		if(filesize>0)
		{
			logger.debug("this testing had generated the error screenshot, so we will put into this screenshot into email ");
			for(int fileindex=0;fileindex<filesize;fileindex++)
		    {
			  int screenshotnum=fileindex+1;
			  imagecontainer=imagecontainer+imagecode_beginner+errorshotfile[fileindex].getName()+imagecode_middle+screenshotnum+imagecode_screenshot;
		      logger.debug("the embled image code is :"+imagecontainer);
		    }
		}
		String returncontent=imagepart+imagecontainer+footerpart;
		return returncontent;
    }
    /*
     * update the status color 
     * if is pass, update the color with greeen
     * if is failed ,update the color with red
     * if is no run ,update the color with yellow
     */
    public void updateStatusColors(String originalcontents)
    {
    	//find the status column and update the status's color
    	
    	
    }
	
	
}
