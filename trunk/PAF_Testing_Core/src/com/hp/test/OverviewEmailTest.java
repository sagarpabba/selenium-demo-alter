/**
 * Project Name:PAF_HC
 * File Name:AutomationOverviewTest.java
 * Package Name:com.hp.tests
 * Date:Sep 7, 201310:49:06 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

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

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.hp.utility.FilesUtils;
import com.hp.utility.JsoupUtils;
import com.hp.utility.SeleniumCore;

/**
 * ClassName:AutomationOverviewTest 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 7, 2013 10:49:06 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class OverviewEmailTest {

	private static final Logger logger = Logger.getLogger(OverviewEmailTest.class);
	String probasepath = SeleniumCore.getProjectWorkspace();
	private final String TODAY_REPORT_DIR = probasepath + "reporter";
	private final String OVERVIEW_REPORT_FILE=TODAY_REPORT_DIR+File.separator+"Automation_Overview.htm";

	/*
	 * send the email via SMTP Sever ,this is the main test param: the email
	 * setting data from the excel file
	 */
	@Test(dataProviderClass = com.hp.utility.ExcelUtils.class, dataProvider = "devEmail")
	public void sendEmail(Map<String, String> emailmap) throws IOException,
			ParseException, SQLException {

		String smtp_server = emailmap.get("SMTP_HOST").trim();
		String username = emailmap.get("AUTHENUSER").trim();
		String password = emailmap.get("AUTHENPASS").trim();
		String from = emailmap.get("FROM_EMAIL").trim();
		String to = emailmap.get("TO_EMAIL").trim();
		String subject = emailmap.get("OVERVIEW_SUBJECT").trim();
		String isauthen = emailmap.get("ISAUTHEN").trim();
		if (isauthen.equals("yes")) {
			sendMultiEmail(smtp_server, true, username, password, from, to,
					subject);
		} else {
			sendMultiEmail(smtp_server, false, username, password, from, to,
					subject);
		}
	}

	public void sendMultiEmail(String smtpserver, boolean authenuser,
			final String username, final String password, String from,
			String to, String subject) throws IOException, ParseException, SQLException {
		try {
			String currenttime = new SimpleDateFormat(
					"EEEE,MMMM dd,yyyy h:mm:ss a z").format(Calendar
					.getInstance().getTime());

			Properties prop = new Properties();
			// prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.host", smtpserver);
			prop.put("mail.smtp.port", "25");
			//prop.put("mail.debug", "true");
			Session session = null;
			if (authenuser) {
				prop.put("mail.smtp.auth", "true");
				// prop.put("mail.smtp.starttls.enable", "true");
				// prop.put("mail.smtp.ssl.enable","true");
				session = Session.getDefaultInstance(prop, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
			} else {
				session = Session.getDefaultInstance(prop);
			}

			JsoupUtils.updateOverviewReport();
			// email settings
			MimeMessage mime = new MimeMessage(session);
			mime.setFrom(new InternetAddress(from));
			mime.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			mime.setSubject(subject + "  "+ currenttime);
			mime.setSentDate(new Date());

			// set multipart email
			MimeMultipart multipart = new MimeMultipart("related");

			// read the today's report content and send the email
			String htmlcontents = FilesUtils.returnFileContents(OVERVIEW_REPORT_FILE);
			// FileUtil.writeContents("testlog.log", htmlcontents);
			logger.debug("read all the today's report content into the string already now");

			BodyPart bodypart = new MimeBodyPart();
			bodypart.setContent(htmlcontents, "text/html;charset=UTF-8");
			logger.debug("now add the html content into the email's body content");
			bodypart.setHeader("Content-Type", "text/html;charset=UTF-8");
			// first set the body main content
			multipart.addBodyPart(bodypart);
			logger.debug("complete parsing the html body content");

			// add the image file,the weekly image and monthly image
			String weeklyimage=SeleniumCore.getProjectWorkspace()+"reporter"+File.separator+"3dchart_weekly.jpg";
			bodypart = new MimeBodyPart();
			DataSource weeklyds = new FileDataSource(weeklyimage);
			bodypart.setDataHandler(new DataHandler(weeklyds));
			logger.debug("the email logo file path is :"
					+ weeklyimage);
			logger.debug("the email's image content id is :3dweekly");
			// be careful this content must contain with <>
			bodypart.addHeader("Content-ID", "<image3dweekly@hp>");
			bodypart.setDisposition("inline");
			multipart.addBodyPart(bodypart);
			//monthly image
			String monthlyimage=SeleniumCore.getProjectWorkspace()+"reporter"+File.separator+"3dchart_month.jpg";
			bodypart = new MimeBodyPart();
			DataSource monthds = new FileDataSource(monthlyimage);
			bodypart.setDataHandler(new DataHandler(monthds));
			logger.debug("the email weekly file path is :"
					+ monthlyimage);
			logger.debug("the email's image content id is :3dweekly");
			// be careful this content must contain with <>
			bodypart.addHeader("Content-ID", "<image3dmonthly@hp>");
			bodypart.setDisposition("inline");
			multipart.addBodyPart(bodypart);
			//project image 
			String projectimage=SeleniumCore.getProjectWorkspace()+"reporter"+File.separator+"3dchar_project.jpg";
			bodypart = new MimeBodyPart();
			DataSource projectds = new FileDataSource(projectimage);
			bodypart.setDataHandler(new DataHandler(projectds));
			logger.debug("the email monthly file path is :"
					+ projectimage);
			logger.debug("the email's image content id is :3dweekly");
			// be careful this content must contain with <>
			bodypart.addHeader("Content-ID", "<image3dproject@hp>");
			bodypart.setDisposition("inline");
			multipart.addBodyPart(bodypart);
		
			// // Send the complete message parts
			mime.setContent(multipart, "text/html;charset=UTF-8");
			logger.debug("now add  all the contents into html body,is sending the email now.....");
			Transport.send(mime);
			logger.debug("now  send the email successfully......");
			// transport.close();
		} catch (MessagingException e) {
			logger.error("sorry met the MessagingException  error cannot send the email ");
			logger.error(e);
		}
	}

	
}

