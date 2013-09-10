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

import static java.io.File.separator;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupUtils {

	private static Logger logger = Logger.getLogger(JsoupUtils.class);

	private static String projectpath = SeleniumCore.getProjectWorkspace();

	private static File inputfile = new File(projectpath + "resources" + separator
			+ "result_template.htm");
	private static final String TODAY_REPORT_DIR = projectpath + "reporter";
	private final static String CURRENT_TIME = new SimpleDateFormat(
			"yyyy-MM-dd").format(Calendar.getInstance().getTime());
	private final static String TODAY_REPORT = TODAY_REPORT_DIR
			+ File.separator + "TestReporter" + "_" + CURRENT_TIME + ".htm";
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
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @since JDK 1.6
	 */
	public static String updateTodayReport() throws IOException, ParseException {
		StringBuilder builder = new StringBuilder();
		Document doc = Jsoup.parse(inputfile, "UTF-8");

		// find the second table for the test steps the third row is index 2
		Elements stepnode = doc.select("table.MsoNormalTable tbody tr");
		
		int stepsize = stepnode.size();
		int passcases=0;
		int failedcases=0;
		
		logger.debug("Find the step table rows are:" + (stepsize - 2 - 1));
		// The row number start with 1 ,the first row will be 1,we will start the change status from the 3 line.
		for (int stepindex = 3; stepindex < stepsize; stepindex++) {
			// String stephtml=stepnode.get(stepindex).outerHtml();
			// logger.debug("the current step node row's html code are:\n"+stephtml);
			// the status node in table
			Element statusnode = stepnode.get(stepindex).select("td:eq(3)")
					.first();
			String originalstatus = statusnode.text().trim();
			if(originalstatus.contains("STATUS_SCAN_CUSTOMER"))
			{
				logger.debug("found this node now ");
			}
			logger.debug("the original status showed in template file is:"
					+ originalstatus);
			String replacedstatus = FileUtils.getValue(originalstatus);

			String originalstyle = statusnode.attr("style");
			logger.debug("the oringial style for the status code is :"
					+ originalstyle);
			if (replacedstatus != null) {
				if (replacedstatus.toLowerCase().contains("passed")) {
					statusnode.text("Passed");
					statusnode.attr("style", originalstyle
							+ ";background:#00B050");
					passcases=passcases+1;
				}
				if (replacedstatus.toLowerCase().contains("failed")) {
					statusnode.text("Failed");
					statusnode.attr("style", originalstyle
							+ ";background:#C00000");
					failedcases=failedcases+1;
				}
				if (replacedstatus.toLowerCase().contains("No Run")) {
					statusnode.text("No Run");
					statusnode.attr("style", originalstyle
							+ ";background:#FFC000");
				}
			} else {
				statusnode.text("No Run");
				statusnode.attr("style", originalstyle + ";background:#FFC000");
			}

			// the comment node in table
			Element commentnode = stepnode.get(stepindex).select("td:eq(4)")
					.first();
			String orignalcomment = commentnode.text().trim();
			logger.debug("the original comment showed in template file is:"
					+ orignalcomment);
			String replacedcomment = FileUtils.getValue(orignalcomment);
			if (replacedcomment != null) {
				commentnode.text(replacedcomment);
			} else {
				commentnode.text("No comments for this test step");

			}
			logger.debug("the coment we had replaced is:" + replacedcomment);
		}

		// update the total run values
		List<String> totalrun = new LinkedList<String>();

		String starttime = FileUtils.getValue("START_TIME");
		String changedtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
				starttime).toString();
		String taketime = TimeUtils.betweenTime(starttime);
		String runtime = "Run From:" + changedtime + ",End Run:"
				+ TimeUtils.getCurrentTime(Calendar.getInstance().getTime()) 
				+ ",Total Run Takes:" + taketime;

		int totalcases = stepsize - 2 - 1;
		//int passcases = FileUtils.textTimes("pass");
		//int failedcases = FileUtils.textTimes("fail");
		int noruncases = totalcases - passcases - failedcases;
		totalrun.add(runtime);
		totalrun.add(String.valueOf(totalcases));
		totalrun.add(String.valueOf(passcases));
		totalrun.add(String.valueOf(failedcases));
		totalrun.add(String.valueOf(noruncases));
		
		
		
		//insert the record into the database
		Connection con=DatabaseUtils.getConnection();
		//first delete the duplicate record for now
		int deletenum=DatabaseUtils.updateRecord(con, "delete from qtp_status where date(RUN_TIME)=CURDATE()");
        logger.info("We found before we insert today's record there are "+deletenum+"  duplicated records already for today ,so we delete these record...");
		int insertednum=DatabaseUtils.updateRecord(con, "insert into qtp_status(PASS_TOTAL,FAIL_TOTAL,NORUN_TOTAL) Values("+passcases+","+failedcases+","+noruncases+")");
        logger.info("we had inserted a new execution "+insertednum+" record for today");
        //generate a execution run status graph
        
        // first table update the status, the second row the
		Elements statustable = doc
				.select("table.MsoTable15Grid4Accent5 tr:eq(1) td");
		int statussize = statustable.size();
		logger.debug("Find the table rows are:" + (statussize - 1));
		for (int stausindex = 1; stausindex < statussize; stausindex++) {
			String tdhtml = statustable.get(stausindex).text();
			logger.debug("the current status  node html code are:" + tdhtml);
			String replacerun = totalrun.get(stausindex - 1);
			logger.debug("the current value text we need to replaced with:"
					+ replacerun);
			statustable.get(stausindex).text(replacerun);
			String tdreplaced = statustable.get(stausindex).text();
			logger.debug("the value had been replaced as this :" + tdreplaced);
		}
		// logger.debug("the new html contents are blow:\n"+doc.body().outerHtml());

		// find the values we need to update in the table
		// MsoTable15Grid5DarkAccent5
		Elements valuenode = doc
				.select("table.MsoTable15Grid5DarkAccent5 tbody tr");
		int valuesize = valuenode.size();
		logger.debug("Find the step table rows are:" + (valuesize - 1));
		for (int valueindex = 1; valueindex < valuesize; valueindex++) {
			// String valuehtml=valuenode.get(valueindex).outerHtml();
			// logger.debug("the current step node row's html code are:\n"+valuehtml);
			// the status node in table
			Element valuecomment = valuenode.get(valueindex).select("td:eq(1)")
					.first();
			String originalvalue = valuecomment.text().trim();
			logger.debug("the original status showed in template file is:"
					+ originalvalue);
			String replacedvalues = FileUtils.getValue(originalvalue);
			if (replacedvalues != null) {
				valuecomment.text(replacedvalues);
			} else {
				valuecomment.text("No values");
			}
			logger.debug("the coment we had replaced is:" + replacedvalues);

		}

		// this is the insertimage code MsoTableMediumShading2Accent5
		Element imagetbodynode = doc.select(
				"table.MsoTableMediumShading2Accent5 tbody").first();
		logger.debug("the image node is :" + imagetbodynode.text());

		String imagecode = "";
		String imagecode_beginner = "<tr><td><p>";
		String imagecode_middle = "</p>" + "</td>"
				+ "<td><span> <img src=\"cid:image";
		String imagecode_screenshot = "@hp\" alt=\" this image cannot show,maybe blocked by your email setting \"><br>"
				+ "</span>" + "</td><tr>";
		int filesize = FileUtils.isFilesExisting(TODAY_REPORT_DIR, ".png");
		File[] errorshotfile = FileUtils.listFilesEndsWith(TODAY_REPORT_DIR, ".png");
		if (filesize > 0) {
			logger.debug("this testing had generated the error screenshot, so we will put into this screenshot into email ");
			for (int fileindex = 0; fileindex < filesize; fileindex++) {
				int screenshotnum = fileindex + 1;
				imagecode = imagecode + imagecode_beginner
						+ errorshotfile[fileindex].getName() + imagecode_middle
						+ screenshotnum + imagecode_screenshot;

			}
		}
		imagetbodynode.append(imagecode);
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
		builder.append(doc.body().html());
		FileUtils.writeContents(TODAY_REPORT, builder.toString());
		// get the total run-time status
		if (passcases == totalcases) {
			return "Passed";
		} else if (failedcases > 0) {
			return "Failed";
		} else if (noruncases == totalcases) {
			return "No Run";
		} else {
			return "Not Completed";
		}

	}

	
	
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
		FileUtils.writeContents(OVERVIEW_REPORT, builder.toString());
	}
	
	
	
}
