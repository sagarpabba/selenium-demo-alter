package com.selenium.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




/**
* @ClassName: EmailerUtils
* @Description: TODO
* @author alterhu2020@gmail.com
* @date Apr 9, 2014 5:55:47 PM
* 
*/

/**
* @ClassName: EmailerUtils
* @Description: TODO
* @author alterhu2020@gmail.com
* @date Apr 14, 2014 5:59:41 PM
* 
*/

public class EmailerUtils {
	
	
	private static final Logger logger=Logger.getLogger(EmailerUtils.class);
	
	
	
	public static void sendSeleniumEmail(){
		
		//1, this is the image picture inserting the commpany log 
		String frontsubject=null;
		StringBuilder sb=new StringBuilder();
		Document doc=null;
		try {
				doc = Jsoup.parse(new File(GlobalDefinition.REPORTRESULT_DIR,GlobalDefinition.DAILY_REPORT_FILE), "UTF-8");
				Element notetable=doc.select("p.MsoNormal").first();
				int logexisting=notetable.select("img").size();
				if(logexisting==0){
			        notetable.prepend("<img width=79 height=79"
			        		+ " src=\"cid:imagecompanylog@selenium\""
		        		+ "align=right hspace=12 alt=\"this image cannot show,maybe blocked by your email setting\">");
				} 
		    }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		//2,insert the first test step log ;
		Element overviewtable = doc.select("table.MsoNormalTable").get(1);
		int overviewrow=overviewtable.select("tr").size();
		int passedcase=0;
		int failedcase=0;
		int noruncase=0;
		int totalcases=0;
		int warningcase=0;
		
		Elements caserows=doc.select("table.MsoNormalTable").get(2).select("tbody").first().select("tr");
	    //    logger.debug("the table text is:"+steptable.text());
	    int rownumber=caserows.size();
	    if(overviewrow==1){
		    for(int i=1;i<rownumber;i++){
			    	// get the second column's text ,if the text we had generated before ,that means this step had done before
			    	totalcases=totalcases+1;
			    	Element statusrow=caserows.get(i).select("td:eq(3)").first();
			    	String statuscontent=statusrow.text().trim();
			    	if(statuscontent.toLowerCase().contains("pass")){
			    		passedcase=passedcase+1;
			    	}else if(statuscontent.toLowerCase().contains("fail")){
			    		failedcase=failedcase+1;
			    	}else if(statuscontent.toLowerCase().contains("warning")){
			    		warningcase=warningcase+1;
			    	}	 
		    }
		    //get the total run-time status
	        if(passedcase==totalcases){
	                frontsubject= "Passed";
	        }
	        else if(failedcase>0){
	                frontsubject= "Failed";
	        }
	        else if(warningcase>0){
	                frontsubject="Warning";
	        }
	        else
	        {
	                frontsubject="Not Completed";
	        }
	        
		    
			String starttime=TimeUtils.getCurrentTime(Calendar.getInstance().getTime());
			String tempstarttime=doc.select("table.MsoNormalTable").get(2).select("tbody").first().select("tr:eq(2)").select("td:eq(1)").first().text().trim();
			if(tempstarttime.contains("[Start Time:")){
				starttime=tempstarttime.split("Time:")[1];
				starttime=starttime.substring(0, starttime.length()-1);
			}
			
			String totaltime=TimeUtils.howManyMinutes(starttime, TimeUtils.getCurrentTime(Calendar.getInstance().getTime()));
	        String appendline=""
	+"  <tr style=\"mso-yfti-irow:0;mso-yfti-lastrow:yes;height:46.75pt\">"
	+"  <td width=\"277\" valign=\"top\" style=\"width:107.6pt;border:solid windowtext 1.0pt;"
	+"  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;"
	+"  background:#D9E2F3;mso-background-themecolor:accent5;mso-background-themetint:"
	+"  51;padding:0in 5.4pt 0in 5.4pt;height:46.75pt\">"
	+"  <p class=\"MsoListParagraphCxSpFirst\" style=\"margin-left:0in;mso-add-space:auto;"
	+"  mso-yfti-cnfc:68\"><b style=\"mso-bidi-font-weight:normal\"><span style=\"font-size:13.0pt;line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;"
	+"  mso-fareast-font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-theme-font:major-fareast;mso-bidi-font-family:"
	+"  &quot;Times New Roman&quot;;mso-bidi-theme-font:major-bidi\"><o:p></o:p></span></b></p>"
	+"  </td>"
	+"  <td width=\"252\" valign=\"top\" style=\"width:117.0pt;border-top:none;border-left:"
	+"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
	+"  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
	+"  mso-border-alt:solid windowtext .5pt;background:#D9E2F3;mso-background-themecolor:"
	+"  accent5;mso-background-themetint:51;padding:0in 5.4pt 0in 5.4pt;height:46.75pt\">"
	+"  <p class=\"MsoListParagraphCxSpMiddle\" style=\"margin-left:0in;mso-add-space:"
	+"  auto;mso-yfti-cnfc:64\"><b><span style=\"font-size:13.0pt;line-height:105%;"
	+"  font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-theme-font:"
	+"  major-fareast;mso-bidi-font-family:&quot;Times New Roman&quot;;mso-bidi-theme-font:"
	+"  major-bidi\">"+totaltime+"<o:p></o:p></span></b></p>"
	+"  </td>"
	+"  <td width=\"234\" valign=\"top\" style=\"width:2.5in;border-top:none;border-left:none;"
	+"  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
	+"  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
	+"  mso-border-alt:solid windowtext .5pt;background:#D9E2F3;mso-background-themecolor:"
	+"  accent5;mso-background-themetint:51;padding:0in 5.4pt 0in 5.4pt;height:46.75pt\">"
	+"  <p class=\"MsoListParagraphCxSpMiddle\" style=\"margin-left:0in;mso-add-space:"
	+"  auto;mso-yfti-cnfc:64\"><b><span style=\"font-size:13.0pt;line-height:105%;"
	+"  font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-theme-font:"
	+"  major-fareast;mso-bidi-font-family:&quot;Times New Roman&quot;;mso-bidi-theme-font:"
	+"  major-bidi\">"+(totalcases-1)+"<o:p></o:p></span></b></p>"
	+"  </td>"
	+"  <td width=\"216\" valign=\"top\" style=\"width:2.5in;border-top:none;border-left:none;"
	+"  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
	+"  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
	+"  mso-border-alt:solid windowtext .5pt;background:#00B050;padding:0in 5.4pt 0in 5.4pt;"
	+"  height:46.75pt\">"
	+"  <p class=\"MsoListParagraphCxSpMiddle\" style=\"margin-left:0in;mso-add-space:"
	+"  auto;mso-yfti-cnfc:64\"><b><span style=\"font-size:13.0pt;line-height:105%;"
	+"  font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-theme-font:"
	+"  major-fareast;mso-bidi-font-family:&quot;Times New Roman&quot;;mso-bidi-theme-font:"
	+"  major-bidi\">"+passedcase+"<o:p></o:p></span></b></p>"
	+"  </td>"
	+"  <td width=\"198\" valign=\"top\" style=\"width:215.85pt;border-top:none;border-left:"
	+"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
	+"  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
	+"  mso-border-alt:solid windowtext .5pt;background:#C00000;padding:0in 5.4pt 0in 5.4pt;"
	+"  height:46.75pt\">"
	+"  <p class=\"MsoListParagraphCxSpLast\" style=\"margin-left:0in;mso-add-space:auto;"
	+"  mso-yfti-cnfc:64\"><b><span style=\"font-size:13.0pt;line-height:105%;"
	+"  font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-theme-font:"
	+"  major-fareast;mso-bidi-font-family:&quot;Times New Roman&quot;;mso-bidi-theme-font:"
	+"  major-bidi\">"+failedcase+"<o:p></o:p></span></b></p>"
	+"  <p class=\"MsoNormal\" style=\"text-indent:.5in;mso-yfti-cnfc:64\"><o:p>&nbsp;</o:p></p>"
	+"  </td>"
	+"  <td width=\"216\" valign=\"top\" style=\"width:355.65pt;border-top:none;border-left:"
	+"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
	+"  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
	+"  mso-border-alt:solid windowtext .5pt;background:#FFC000;padding:0in 5.4pt 0in 5.4pt;"
	+"  height:46.75pt\">"
	+"  <p class=\"MsoListParagraph\" style=\"margin-left:0in;mso-add-space:auto;"
	+"  mso-yfti-cnfc:64\"><b><span style=\"font-size:13.0pt;line-height:105%;"
	+"  font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-theme-font:"
	+"  major-fareast;mso-bidi-font-family:&quot;Times New Roman&quot;;mso-bidi-theme-font:"
	+"  major-bidi\">"+noruncase+"<o:p></o:p></span></b></p>"
	+"  </td>"
	+"  <td width=\"192\" valign=\"top\" style=\"width:355.65pt;border-top:none;border-left:"
	+"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
	+"  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
	+"  mso-border-alt:solid windowtext .5pt;background:#FFC000;padding:0in 5.4pt 0in 5.4pt;"
	+"  height:46.75pt\">"
	+"  <p class=\"MsoListParagraph\" style=\"margin-left:0in;mso-add-space:auto;"
	+"  mso-yfti-cnfc:64\"><b><span style=\"font-size:13.0pt;line-height:105%;"
	+"  font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-theme-font:"
	+"  major-fareast;mso-bidi-font-family:&quot;Times New Roman&quot;;mso-bidi-theme-font:"
	+"  major-bidi\">"+warningcase+"<o:p></o:p></span></b></p>"
	+"  </td>"
	+" </tr>";
	        overviewtable.append(appendline);
	        
	        sb.append(doc.body().html());
	        FilesUtils.writeContents(GlobalDefinition.REPORTRESULT_DIR+File.separator+GlobalDefinition.DAILY_REPORT_FILE, sb.toString());
		}else{
			frontsubject="No Run";
		}
        //send the email
        String htmlcontents = FilesUtils.returnFileContents(GlobalDefinition.REPORTRESULT_DIR+File.separator+GlobalDefinition.DAILY_REPORT_FILE);
        sendEmail("smtp3.hp.com", false, "", "", "chang-yuan.hu@hp.com", "chang-yuan.hu@hp.com", "["+frontsubject+"]-Micro Automation Testing Flow ", htmlcontents);
        
		
	}
	
	/** 
	* @Title: sendEmail 
	* @Description: TODO
	* @author alterhu2020@gmail.com
	* @param @param smtpserver
	* @param @param authenuser
	* @param @param authusername
	* @param @param authpassword
	* @param @param from
	* @param @param to
	* @param @param subject
	* @param @param bodycontent    
	* @return void    return type
	* @throws 
	*/ 
	
	public  static void sendEmail(String smtpserver,
			              boolean authenuser,
			              final String authusername, 
			              final String authpassword,
			              String from,
			              String to,
			              String subject,
			              String bodycontent){
		
		
		try {
				 
				SimpleDateFormat dateformat=new SimpleDateFormat(
						"EEEE,MMMM dd,yyyy h:mm:ss a z");
				dateformat.setTimeZone(TimeZone.getTimeZone("GMT"));
				String currenttime =dateformat.format(Calendar.getInstance().getTime());
	
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
							return new PasswordAuthentication(authusername, authpassword);
						}
					});
				} else {
					session = Session.getDefaultInstance(prop);
				}
				
				MimeMessage mime = new MimeMessage(session);
				mime.setFrom(new InternetAddress(from));
				mime.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(to));
				mime.setSubject(subject + " On "
						+ currenttime);
				mime.setSentDate(new Date());
				
				// set multipart email
				MimeMultipart multipart = new MimeMultipart("related");
				BodyPart bodypart = new MimeBodyPart();
				bodypart.setContent(bodycontent, "text/html;charset=UTF-8");
				System.out.println("now add the html content into the email's body content");
				bodypart.setHeader("Content-Type", "text/html;charset=UTF-8");
				// first set the body main content
				multipart.addBodyPart(bodypart);
				System.out.println("complete parsing the html body content");
				
				
				// add the image file
				int filesize = FilesUtils.getSubFilesSize(GlobalDefinition.REPORTRESULT_DIR, ".png");
				File[] errorshotfile = FilesUtils
						.listFilesEndsWith(GlobalDefinition.REPORTRESULT_DIR, ".png");
				
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
								+ imagecount + "@selenium");
						// be careful this content must contain with <>
						bodypart.addHeader("Content-ID", "<image" + imagecount
								+ "@selenium>");
						bodypart.setDisposition("inline");
						multipart.addBodyPart(bodypart);
						logger.debug("add the image embled into the html for this screenshot file :"
								+ errorfilepath);
					}
				}
			
				logger.debug("complete parsing the image body content");
				
				/*
				 * attached the company log file 
				 */
				//String logopath="resources/logo.png";
				bodypart = new MimeBodyPart();
				DataSource ds = new FileDataSource(GlobalDefinition.RESOURCES_DIR+File.separator+GlobalDefinition.EMAIL_LOG_FILE);
				bodypart.setDataHandler(new DataHandler(ds));
				logger.debug("the email logo file path is :"
						+ GlobalDefinition.EMAIL_LOG_FILE);
				logger.debug("the email's image content id is :companylog");
				// be careful this content must contain with <>
				bodypart.addHeader("Content-ID", "<imagecompanylog@selenium>");
				bodypart.setDisposition("inline");
				//bodypart.setFileName("logo.png");
				multipart.addBodyPart(bodypart);
				
				File[] logfiles=FilesUtils.listFilesStartWith(GlobalDefinition.Log4J_LOG_DIR, "Project_ERROR");
				//int totalfiles=logfiles.length;
				if(logfiles!=null){
					for(int fileindex=0;fileindex<logfiles.length;fileindex++){
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
				
				//mime.setContent(multipart, "text/html;charset=UTF-8");
				mime.setContent(multipart, "text/html;charset=UTF-8");
				logger.debug("now add  all the contents into html body,is sending the email now.....");
				Transport.send(mime);
				logger.debug("now  send the email successfully......");
		} catch (MessagingException e) {
			logger.error("sorry met the MessagingException  error cannot send the email ");
			logger.error(e);
		}
			
	}
	
	
	public static void main(String[] args) {
		sendEmail("smtp3.hp.com", false, "", "", "chang-yuan.hu@hp.com", "chang-yuan.hu@hp.com", "test is ", "hello");
	}
	
}
