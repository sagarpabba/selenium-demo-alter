package com.hp.utility;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.io.File.separator;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtils {

	private static Logger logger = Logger.getLogger(JsoupUtils.class);

	private static String projectpath = SeleniumCore.getProjectWorkspace();
	private static final String TODAY_REPORT_DIR = projectpath + "reporter";
	private final static String CURRENT_TIME = new SimpleDateFormat(
			"yyyy-MM-dd").format(Calendar.getInstance().getTime());
	
	 static String projectdir=SeleniumCore.getProjectWorkspace();
	 static String templatereport=projectdir+"resources"+File.separator+"report_template.htm";
	 static String reportfilename=projectdir+"reporter"+File.separator+"TestingExecutionReport_"+ 
                           new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())+".htm";
	//overview chart
	private static File overviewfile=new File(projectpath+"resources"+separator+"Automation_Overview_Template.htm");
    private final static String OVERVIEW_REPORT=TODAY_REPORT_DIR+ File.separator + "Automation_Overview.htm";
    
    private final static String WEEK_CHART_NAME="3dchart_weekly.jpg";
    private final static String MONTH_CHART_NAME="3dchart_month.jpg";
    private final static String PROJECT_CHART_NAME="3dchar_project.jpg";
	
	/**
	 * updateContents:(Here describle the usage of this function). 
	 *
	 * @author huchan
	 * @throws IOException
	 * @throws SQLException 
	 * @throws ParseException
	 * @since JDK 1.6
	 */
	public static void updateOverviewReport() throws IOException, SQLException {
		
		String monday=TimeUtils.getMondayOfThisWeek();
		String sunday=TimeUtils.getSundayOfThisWeek();
		String firstdayofmonth=TimeUtils.getFirstdayofMonth();
		String lastdayofmonth=TimeUtils.getLastdayofMonth();
		
		List<String> weeklist=TimeUtils.getCurrentWeekDays();
		List<String> monthlist=TimeUtils.getCurrentMonthday();
		//String currenttime=TimeUtils.getCurrentTime(Calendar.getInstance().getTime());
		
		//find today's report result
		List<String> databasestaus=new LinkedList<String>();
		Connection con=DatabaseUtils.getConnection();
		ResultSet rs=DatabaseUtils.getResultSet(con, "Select * from qtp_status where date(RUN_TIME)=CURDATE()");
		while(rs.next())
		{
			int passnumber=rs.getInt("PASS_TOTAL");
			int failnumber=rs.getInt("FAIL_TOTAL");
			int norunnumber=rs.getInt("NORUN_TOTAL");
			int totalnumber=passnumber+failnumber+norunnumber;
			databasestaus.add(String.valueOf(totalnumber));
			databasestaus.add(String.valueOf(passnumber));
			databasestaus.add(String.valueOf(failnumber));
			databasestaus.add(String.valueOf(norunnumber));			
		}
		//get all the result from database
		List<String> projectlist=new ArrayList<String>();
		ResultSet allrs=DatabaseUtils.getResultSet(con, "select * from qtp_status order by RUN_TIME asc");
		while(allrs.next()){
			String runtimestamp=new SimpleDateFormat("yyyy-MM-dd").format(new Date(allrs.getTimestamp("RUN_TIME").getTime()));
			projectlist.add(runtimestamp);
		}
		allrs.first();
		String projectstart=new SimpleDateFormat("yyyy-MM-dd").format(new Date(allrs.getTimestamp("RUN_TIME").getTime()));
		
		//generate the pictures
		DatabaseUtils.generate3DBarChart(monday, sunday, weeklist,1200,400,WEEK_CHART_NAME);
		DatabaseUtils.generate3DBarChart(firstdayofmonth, lastdayofmonth, monthlist,3000,1000,MONTH_CHART_NAME);
		DatabaseUtils.generate3DLineChart(projectstart, CURRENT_TIME, projectlist,3000,1000,PROJECT_CHART_NAME);
		
		DatabaseUtils.closeAllConnections(con, rs);
		
		//update the overview content
		StringBuilder builder = new StringBuilder();
		Document doc = Jsoup.parse(overviewfile, "UTF-8");
		// find the second table for the test steps the third row is index 2   Elements statustable = doc
		Elements todaystatus = doc.select("table.MsoTable15Grid4Accent5 tr:eq(1) td");
		int statussize = todaystatus.size();
		logger.debug("Find the today table status rows are:" + (statussize - 1));
		for (int stausindex = 1; stausindex < statussize; stausindex++) {
			String tdhtml = todaystatus.get(stausindex).text();
			logger.debug("the current status  node html code are:" + tdhtml);
			String replacerun = databasestaus.get(stausindex - 1);
			logger.debug("the current value text we need to replaced with:"+ replacerun);
			todaystatus.get(stausindex).text(replacerun);
			String tdreplaced = todaystatus.get(stausindex).text();
			logger.debug("the value we had been replaced as this :" + tdreplaced);
		}
		
		//attached the chart pictures
		Elements updatetable=doc.select("p.MsoNormal");
		for(Element eachtable:updatetable){
			String titletext=eachtable.text().trim();
			//if it's the weekly table
			if(titletext.contains("status at")){
				eachtable.attr("style", "tab-stops:217.5pt;font:bold 20px/20px arial,sans-serif;");
				eachtable.text("Automation Testing Execution status at "+CURRENT_TIME);
			}
			if(titletext.contains("Weekly")){
				eachtable.attr("style", "tab-stops:217.5pt;font:bold 20px/20px arial,sans-serif;");
				eachtable.text("PAF Automation Execution Overview Weekly ("+monday+" to "+sunday+")");
				eachtable.after("<br/><img src=\"cid:image3dweekly@hp\""
	        		+ "align=center alt=\"this image cannot show,maybe blocked by your email setting\" />");
				
			}
			if(titletext.contains("Monthly")){
				eachtable.attr("style", "tab-stops:217.5pt;font:bold 20px/20px arial,sans-serif;");
				eachtable.text("PAF Automation Execution Overview Monthly ("+firstdayofmonth+" to "+lastdayofmonth+")");
				eachtable.after("<br/><img src=\"cid:image3dmonthly@hp\""
	        		+ "align=center alt=\"this image cannot show,maybe blocked by your email setting\" />");
			}
			if(titletext.contains("Project")){
				eachtable.attr("style", "tab-stops:217.5pt;font:bold 20px/20px arial,sans-serif;");
				eachtable.text("PAF Automation Project Overview (Start from "+projectstart+")");
				eachtable.after("<br/><img src=\"cid:image3dproject@hp\""
		        		+ "align=center alt=\"this image cannot show,maybe blocked by your email setting\" />");
			}
		}
		
		
		builder.append(doc.body().html());
		FilesUtils.writeContents(OVERVIEW_REPORT, builder.toString());
	}
	
	/**
	 * this function is not used any more now
	 * @return
	 * @throws IOException
	 */
	public static Map<Integer,List<String>> getReportSteps() throws IOException{
		
		Map<Integer,List<String>> steplist=new TreeMap<Integer, List<String>>();
		
		Document doc = Jsoup.parse(TODAY_REPORT_DIR, "UTF-8");
        Elements stepnode = doc.select("table.MsoNormalTable tbody tr");
		
		int stepsize = stepnode.size();
		
		logger.debug("Find the step table rows are:" + (stepsize - 2 - 1));
		// The row number start with 1 ,the first row will be 1,we will start the change status from the 3 line.
		for (int stepindex = 3; stepindex < stepsize; stepindex++) {
			//START FROM INDEX 0
			List<String> stepinfo=new ArrayList<String>();
			String statusnode = stepnode.get(stepindex).select("td:eq(3)")
					.first().text().trim();
			String commentnode = stepnode.get(stepindex).select("td:eq(4)")
					.first().text().trim();
			stepinfo.add(statusnode);
			stepinfo.add(commentnode);
			steplist.put(stepindex-2, stepinfo);
		}
		logger.debug("Get the steps map is:"+steplist);
		return steplist;
	}
	
	//we will take to create a new template for the report,the report column will generate automacially
	/**
	 * generate the html report as we had run ,if the test had not run the step will not generate
	 * @return the test execution status:passed ,failed ,no run or not completed
	 * @throws IOException
	 * @throws SQLException 
	 */
	public static String generateHtmlReport() throws IOException, SQLException{
		
		 //first table for the overview table, 
/**************************************************************************************************************/
		 String testsuitestatus=updateOverviewTable();

/**************************************************************************************************************/
        //update the data value table
         
/**************************************************************************************************************/
/**************************************************************************************************************/
        updateScreenshotTable();  
	    return testsuitestatus;
	}
	public static void updateScreenshotTable(){
		// this is the insert image code MsoTableMediumShading2Accent5
		 File dailyreport=new File(reportfilename);
		 // if  today's report not generated ,we will copy the template report file firstly
		 if(!dailyreport.exists()){
			   try {
				    FileUtils.copyFile(new File(templatereport), dailyreport);
			   } catch (IOException e) {
				    // TODO Auto-generated catch block
			        logger.debug("Sorry cannot copy the report_template.html file into the report directory:"+reportfilename);
			   }
		 }else{
			 logger.debug("We found that today's report file had generated before...");
		 }
		 //parse the document
			StringBuilder sb=new StringBuilder();
			Document doc;
			try {
					doc = Jsoup.parse(dailyreport, "UTF-8"); 
					Element imagetbodynode = doc.select("table.MsoTableMediumShading2Accent5 tbody").first();
					logger.debug("the image node is :" + imagetbodynode.text());
			
					int filesize = FilesUtils.getSubFilesSize(TODAY_REPORT_DIR, ".png");
					File[] errorshotfile = FilesUtils.listFilesEndsWith(TODAY_REPORT_DIR, ".png");
					if (filesize > 0) {
						logger.debug("this testing had generated the error screenshot, so we will put into this screenshot into email ");
						for (int fileindex = 0; fileindex < filesize; fileindex++) {
								int screenshotnum = fileindex + 1;
								
								String imagecode=""
										+ "<tr><td><p>"+errorshotfile[fileindex].getName() +"</p></td><td><span> <img src=\"cid:image"+screenshotnum+"@hp\" alt=\" "
										+ "this image cannot show,maybe blocked by your email setting \"><br></span></td></tr>";
								imagetbodynode.append(imagecode);
						}
					}
					logger.debug("We had completed pop all the prior data into a Map type ,we will use it later");
					
					//emabled the company logo picture  ,the image CID must be started with image ...
					//image code is:<img width=79 height=79 src=\"cid:companylogo\" align=right hspace=12 alt=\"this image cannot show,maybe blocked by your email setting \" v:shapes=\"Picture_x0020_2\">
					Element notetable=doc.select("p.MsoNormal").first();
			        notetable.prepend("<img width=79 height=79"
			        		+ " src=\"cid:imagecompanylog@hp\""
			        		+ "align=right hspace=12 alt=\"this image cannot show,maybe blocked by your email setting\">");
			        logger.debug("we had attached the company logo into the email ...");
			        
			        logger.debug("notetable html is:"+notetable.toString());
					// generate the html report
				    /**************************************************************************************************************/
					sb.append(doc.body().html());
			}
			catch(IOException exception){
				logger.error("jsoup parse the image met the ioexception error");
			}
		    FilesUtils.writeContents(reportfilename, sb.toString());
	}
	
	public static String updateOverviewTable() throws SQLException{	
		 File dailyreport=new File(reportfilename);
		 // if  today's report not generated ,we will copy the template report file firstly
		 if(!dailyreport.exists()){
			   try {
				    FileUtils.copyFile(new File(templatereport), dailyreport);
			   } catch (IOException e) {
				    // TODO Auto-generated catch block
			        logger.debug("Sorry cannot copy the report_template.html file into the report directory:"+reportfilename);
			   }
		 }else{
			 logger.debug("We found that today's report file had generated before...");
		 }
		 
		//update the overview execution table
		int passedcase=0;
		int failedcase=0;
		int noruncase=0;
		int totalcases=0;
		int warningcase=0;
		 
		StringBuilder sb=new StringBuilder();
		Document doc;
		try {
				doc = Jsoup.parse(dailyreport, "UTF-8");
				Element overviewtable = doc.select("table.MsoTable15Grid4Accent5").first();
			    Elements caserows=doc.select("table.MsoNormalTable").get(1).select("tbody").first().select("tr");
		
			    //    logger.debug("the table text is:"+steptable.text());
			    int rownumber=caserows.size();
			    for(int i=2;i<rownumber;i++){
			    	// get the three column for the status 
			    	Element statuscontent=caserows.get(i).select("td:eq(3)").first();
			    	String updatedstatus=statuscontent.text().trim();
			    	if(updatedstatus.toLowerCase().trim().contains("pass")){
						passedcase=passedcase+1;
					}
					if(updatedstatus.toLowerCase().trim().contains("fail")){
						failedcase=failedcase+1;
					}
					if(updatedstatus.toLowerCase().trim().contains("no run")){
						noruncase=noruncase+1;
					}
					if(updatedstatus.toLowerCase().trim().contains("warning")){
						warningcase=warningcase+1;
					}
			    }
			    String starttime=BaseDriver.testStartTime;
			    String totaltime=TimeUtils.howManyMinutes(starttime, TimeUtils.getCurrentTime(Calendar.getInstance().getTime()));
			     
			    totalcases=passedcase+failedcase+noruncase+warningcase;
			    
			    String appendline=""
			    		+"  <tr style=\"mso-yfti-irow:0;mso-yfti-lastrow:yes;height:46.75pt\">"
			    		+"  <td width=\"143\" valign=\"top\" style=\"width:107.6pt;border:solid windowtext 1.0pt;"
			    		+"  border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;"
			    		+"  background:#D9E2F3;mso-background-themecolor:accent5;mso-background-themetint:"
			    		+"  51;padding:0in 5.4pt 0in 5.4pt;height:46.75pt\">"
			    		+"  <p class=\"MsoListParagraphCxSpFirst\" style=\"margin-left:0in;mso-add-space:auto;"
			    		+"  mso-yfti-cnfc:68\"><b style=\"mso-bidi-font-weight:normal\"><span style=\"font-size:13.0pt;line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;"
			    		+"  mso-fareast-font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-theme-font:major-fareast;mso-bidi-font-family:"
			    		+"  &quot;Times New Roman&quot;;mso-bidi-theme-font:major-bidi\">Total Numbers:<o:p></o:p></span></b></p>"
			    		+"  </td>"
			    		+"  <td width=\"156\" valign=\"top\" style=\"width:117.0pt;border-top:none;border-left:"
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
			    		+"  <td width=\"240\" valign=\"top\" style=\"width:2.5in;border-top:none;border-left:none;"
			    		+"  border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
			    		+"  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
			    		+"  mso-border-alt:solid windowtext .5pt;background:#D9E2F3;mso-background-themecolor:"
			    		+"  accent5;mso-background-themetint:51;padding:0in 5.4pt 0in 5.4pt;height:46.75pt\">"
			    		+"  <p class=\"MsoListParagraphCxSpMiddle\" style=\"margin-left:0in;mso-add-space:"
			    		+"  auto;mso-yfti-cnfc:64\"><b><span style=\"font-size:13.0pt;line-height:105%;"
			    		+"  font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-fareast-theme-font:"
			    		+"  major-fareast;mso-bidi-font-family:&quot;Times New Roman&quot;;mso-bidi-theme-font:"
			    		+"  major-bidi\">"+totalcases+"<o:p></o:p></span></b></p>"
			    		+"  </td>"
			    		+"  <td width=\"240\" valign=\"top\" style=\"width:2.5in;border-top:none;border-left:none;"
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
			    		+"  <td width=\"288\" valign=\"top\" style=\"width:215.85pt;border-top:none;border-left:"
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
			    		+"  <td width=\"474\" valign=\"top\" style=\"width:355.65pt;border-top:none;border-left:"
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
			    		+"  <td width=\"474\" valign=\"top\" style=\"width:355.65pt;border-top:none;border-left:"
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
			   sb.append(doc.body().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 logger.error("Jsoup parse the document met the IOException error");
		}
	
	  // logger.debug("Current report file is canwrite false or true:"+dailyreport.canWrite());
	   FilesUtils.writeContents(reportfilename, sb.toString());  
	   
	   
	   
		//insert the record into the database
		Connection con=DatabaseUtils.getConnection();
		//first delete the duplicate record for now
		int deletenum=DatabaseUtils.updateRecord(con, "delete from qtp_status where date(RUN_TIME)=CURDATE()");
		logger.info("We found before we insert today's record there are "+deletenum+"  duplicated records already for today ,so we delete these record...");
		int insertednum=DatabaseUtils.updateRecord(con, "insert into qtp_status(PASS_TOTAL,FAIL_TOTAL,NORUN_TOTAL) Values("+passedcase+","+failedcase+","+noruncase+")");
		logger.info("we had inserted a new execution "+insertednum+" record for today");
		con.close();
		
		// get the total run-time status
		
		if (passedcase == totalcases) {
			return "Passed";
		} else if (failedcase > 0) {
			return "Failed";
		} else if (noruncase == totalcases) {
			return "No Run";
		} else if((warningcase>0)&&(failedcase==0)&&(noruncase==0)){
			return "Warning";
		}else{
			return "Not Completed";
		}
  }
	
	
}
