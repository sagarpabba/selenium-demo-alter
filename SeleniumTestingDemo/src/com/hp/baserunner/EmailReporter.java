package com.hp.baserunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
//import org.testng.log4testng.Logger;

import com.sun.mail.smtp.SMTPSaslAuthenticator;

public class EmailReporter {

	
    private static Logger log=Logger.getLogger(EmailReporter.class);
	@Test(dataProviderClass=com.hp.dataproviders.ExcelDataProivderEmailSheet.class,dataProvider="devEmail")
	public void sendEmail(Map<String,String> emailmap) throws Exception 
	{
		String currenttime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String username=emailmap.get("AUTHENUSER");
		String password=emailmap.get("AUTHENPASS");
		String from= emailmap.get("FROM_EMAIL");
		String to=emailmap.get("TO_EMAIL");
		String subject=emailmap.get("SUBJECT");
		String excelname=emailmap.get("TEMPLATE_FILE");
		Properties prop=new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.host", emailmap.get("SMTP_HOST"));	
		//prop.setProperty("mail.mime.address.strict", "false");
		//prop.put("from", emailmap.get("FROM_EMAIL"));
		//prop.put("to", emailmap.get("TO_EMAIL"));
		Session session=Session.getDefaultInstance(prop);
		//email settings
		MimeMessage mime=new MimeMessage(session);
		mime.setFrom(new InternetAddress(from));
		mime.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		mime.setSubject(subject+"--"+currenttime);
		mime.setSentDate(new Date());
		//html email setting
		Multipart htmlpart=new MimeMultipart("related");
		BodyPart bodypart=new MimeBodyPart();
		log.info("excel name from main is :"+excelname);
		String htmlcontent=createEmail(excelname);
		//System.out.println("body:"+bodycontents);
		bodypart.setDataHandler(new DataHandler(new ByteArrayDataSource(htmlcontent,"text/html;charset=UTF-8")));
		
		htmlpart.addBodyPart(bodypart);
		mime.setContent(htmlpart,"text/html;charset=UTF-8");
		
		//send the email 
		Transport transport =session.getTransport("smtp");
		transport.connect(username,password);
		transport.sendMessage(mime, mime.getAllRecipients());
		transport.close();
	}
	

	public void popEmailContent()
	{
		
	}

	public void updateStatusColors()
	{
		
	}
	
	
	public String createEmail(String htmlfilepath)
	{
		
		//GET the result html's content
		//get the html report file path
		String filenameextension=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		log.info("template excel file is :"+htmlfilepath);
		log.info("template file splite is :"+htmlfilepath.split(".htm")[0]);
		String filepathname=htmlfilepath.split(".htm")[0]+"_"+filenameextension+".htm";
		log.info("excel file is :"+filepathname);
		StringBuffer htmlcontent =new StringBuffer("");		
		BufferedReader file=null;
				
		try {
			file = new BufferedReader(new InputStreamReader(new FileInputStream(filepathname)));
			String strline=null;
			while((strline=file.readLine())!=null)
			{
				htmlcontent.append(strline);
				//System.out.println(strline);		
			}
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			log.error("we cannot find this html file :"+filepathname);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		//
		return htmlcontent.toString();
	}
	
	
}
