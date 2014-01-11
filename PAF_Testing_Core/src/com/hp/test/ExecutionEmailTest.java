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
 */
public class ExecutionEmailTest {

	private static final Logger logger = Logger.getLogger(ExecutionEmailTest.class);
	String probasepath = SeleniumCore.getProjectWorkspace();
	private final String CURRENT_TIME = new SimpleDateFormat("yyyy-MM-dd")
			.format(Calendar.getInstance().getTime());
	// constant

	private final String TODAY_REPORT_DIR = probasepath + "reporter";
	private final String LOG_DIR=probasepath+"log";

	private final String TODAY_REPORT = TODAY_REPORT_DIR + File.separator
			+ "TestingExecutionReport_" + CURRENT_TIME + ".htm";

	/*
	 * send the email via SMTP Sever ,this is the main test param: the email
	 * setting data from the excel file
	 */
	/**
	 * Method sendEmail.
	 * @param emailmap Map<String,String>
	 * @throws IOException
	 * @throws ParseException
	 * @throws SQLException
	 */
	@Test(dataProviderClass = com.hp.utility.ExcelUtils.class, dataProvider = "devEmail")
	public void sendEmail(Map<String, String> emailmap) throws IOException,
			ParseException, SQLException {

		String smtp_server = emailmap.get("SMTP_HOST").trim();
		String username = emailmap.get("AUTHENUSER").trim();
		String password = emailmap.get("AUTHENPASS").trim();
		String from = emailmap.get("FROM_EMAIL").trim();
		String to = emailmap.get("TO_EMAIL").trim();
		String subject = emailmap.get("SUBJECT").trim();
		String isauthen = emailmap.get("ISAUTHEN").trim();
		if (isauthen.equals("yes")) {
			sendMultiEmail(smtp_server, true, username, password, from, to,
					subject);
		} else {
			sendMultiEmail(smtp_server, false, username, password, from, to,
					subject);
		}
	}

	/**
	 * Method sendMultiEmail.
	 * @param smtpserver String
	 * @param authenuser boolean
	 * @param username String
	 * @param password String
	 * @param from String
	 * @param to String
	 * @param subject String
	 * @throws IOException
	 * @throws ParseException
	 * @throws SQLException
	 */
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

			String subjecttitle = JsoupUtils.generateHtmlReport();
			// logger.debug("we had generate a test report file in this path:"+TODAY_REPORT);
			// email settings
			MimeMessage mime = new MimeMessage(session);
			mime.setFrom(new InternetAddress(from));
			mime.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			mime.setSubject("[" + subjecttitle + "] " + subject + " "
					+ currenttime);
			mime.setSentDate(new Date());

			// set multipart email
			MimeMultipart multipart = new MimeMultipart("related");

			// read the today's report content and send the email
			String htmlcontents = FilesUtils.returnFileContents(TODAY_REPORT);
			// FileUtil.writeContents("testlog.log", htmlcontents);
			logger.debug("read all the today's report content into the string already now");

			BodyPart bodypart = new MimeBodyPart();
			bodypart.setContent(htmlcontents, "text/html;charset=UTF-8");
			logger.debug("now add the html content into the email's body content");
			bodypart.setHeader("Content-Type", "text/html;charset=UTF-8");
			// first set the body main content
			multipart.addBodyPart(bodypart);
			logger.debug("complete parsing the html body content");

			// add the image file
			int filesize = FilesUtils.getSubFilesSize(TODAY_REPORT_DIR, ".png");
			File[] errorshotfile = FilesUtils
					.listFilesEndsWith(TODAY_REPORT_DIR, ".png");
			
			if (filesize > 0) {
				logger.debug("the current testing met the error and generate an error screenshot we will attach it into the email");
				for (int fileindex = 0; fileindex < filesize; fileindex++) {
					int imagecount = fileindex + 1;
					String errorfilepath = errorshotfile[fileindex]
							.getAbsolutePath();
					bodypart = new MimeBodyPart();
					DataSource fds = new FileDataSource(errorfilepath);
					bodypart.setDataHandler(new DataHandler(fds));
					logger.debug("the error screenshot file long path is :"
							+ errorfilepath);
					logger.debug("the email's image content id is :image"
							+ imagecount + "@hp");
					// be careful this content must contain with <>
					bodypart.addHeader("Content-ID", "<image" + imagecount
							+ "@hp>");
					bodypart.setDisposition("inline");
					multipart.addBodyPart(bodypart);
					logger.debug("add the image embled into the html for this screenshot file :"
							+ errorfilepath);
				}
			}
		
			logger.debug("complete parsing the image body content");

			// Part two is attachment,set the email's body attachments
//			bodypart = new MimeBodyPart();
//			DataSource source = new FileDataSource(RUN_RESULT_PATH);
//			bodypart.setDataHandler(new DataHandler(source));
//			bodypart.setFileName("reporter.log");
//			multipart.addBodyPart(bodypart);
//			logger.debug("complete parsing the attachment body content");
//			
			//the company logo picture  companylog
			String logopath=SeleniumCore.getProjectWorkspace()+"resources"+File.separator+"logo.png";
			bodypart = new MimeBodyPart();
			DataSource ds = new FileDataSource(logopath);
			bodypart.setDataHandler(new DataHandler(ds));
			logger.debug("the email logo file path is :"
					+ logopath);
			logger.debug("the email's image content id is :companylog");
			// be careful this content must contain with <>
			bodypart.addHeader("Content-ID", "<imagecompanylog@hp>");
			bodypart.setDisposition("inline");
			//bodypart.setFileName("logo.png");
			multipart.addBodyPart(bodypart);
			
			//attach the run log file 
			
			File[] logfiles=FilesUtils.listFilesStartWith(LOG_DIR, "PAF_STEPS_INFO");
			int totalfiles=logfiles.length;
			if(totalfiles>0){
				for(int fileindex=0;fileindex<totalfiles;fileindex++){
					String filepath=logfiles[fileindex].getAbsolutePath();
					String filename=logfiles[fileindex].getName();
					bodypart = new MimeBodyPart();
					DataSource source = new FileDataSource(filepath);
					bodypart.setDataHandler(new DataHandler(source));
					bodypart.setFileName(filename);
					multipart.addBodyPart(bodypart);					
				}
			}
			logger.info("had attached all the log files into the email for analsysing...");

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
