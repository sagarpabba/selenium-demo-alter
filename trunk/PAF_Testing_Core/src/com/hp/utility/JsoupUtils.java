package com.hp.utility;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
		 String report_dir=SeleniumCore.getProjectWorkspace()+"reporter";
		 String outpuvaluetfile=report_dir+File.separator+"dataoutput.log";
		 String stepfile=report_dir+File.separator+"stepoutput.log";
		 String pageloadingfile=report_dir+File.separator+"pageloading.log";
		 
		 String templatereport=SeleniumCore.getProjectWorkspace()+"resources"+File.separator+"report_template.htm";
		 String reportfilename=report_dir+File.separator+"TestingExecutionReport_"+ 
		                      new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())+".htm";
		 StringBuilder sb=new StringBuilder();
		 Document doc = Jsoup.parse(new File(templatereport), "UTF-8");
	   
		 //first table for the overview table, 
/**************************************************************************************************************/
		 Element overviewtable = doc.select("table.MsoTable15Grid4Accent5").first();
		 int passedcase=0;
		 int failedcase=0;
		 int noruncase=0;
		 int totalcases=0;
		 int warningcase=0;
		 BufferedReader br=null;
		 try {
			br=new BufferedReader(new FileReader(stepfile));
			String strline=null;
			while((strline=br.readLine())!=null){
				totalcases=totalcases+1;
				String status=strline.split("\\|")[2];
				if(status.toLowerCase().trim().contains("pass")){
					passedcase=passedcase+1;
				}
				if(status.toLowerCase().trim().contains("fail")){
					failedcase=failedcase+1;
				}
				if(status.toLowerCase().trim().contains("norun")){
					noruncase=noruncase+1;
				}
				if(status.toLowerCase().trim().contains("warning")){
					warningcase=warningcase+1;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		   br.close();
		}
		logger.debug("Get the case overview ,passed cases are:"+passedcase+",failed cases are:"+failedcase+",no run cases are:"+noruncase);
      
		//insert the record into the database
		Connection con=DatabaseUtils.getConnection();
		//first delete the duplicate record for now
		int deletenum=DatabaseUtils.updateRecord(con, "delete from qtp_status where date(RUN_TIME)=CURDATE()");
		logger.info("We found before we insert today's record there are "+deletenum+"  duplicated records already for today ,so we delete these record...");
		int insertednum=DatabaseUtils.updateRecord(con, "insert into qtp_status(PASS_TOTAL,FAIL_TOTAL,NORUN_TOTAL) Values("+passedcase+","+failedcase+","+noruncase+")");
		logger.info("we had inserted a new execution "+insertednum+" record for today");
		con.close();
		//generate a execution run status graph
		
		//  int totalcases=totalcases-1;
        String starttime=FilesUtils.getSpliteValue(outpuvaluetfile, "Execution Start Time", "|", 1);
        String totaltime=TimeUtils.howManyMinutes(starttime, TimeUtils.getCurrentTime(Calendar.getInstance().getTime()));
        
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
+" </tr>";
        overviewtable.append(appendline);
               
/**************************************************************************************************************/
        //the step table,this is the index 1
        Element steptable=doc.select("table.MsoNormalTable").get(1).select("tbody").first();
        logger.debug("the table text is:"+steptable.text());
        BufferedReader stepbr=null;
        stepbr=new BufferedReader(new FileReader(stepfile));
        String stepline=null;
        int stepnumber=0;
        while((stepline=stepbr.readLine())!=null){
        	stepnumber=stepnumber+1;
        	String[] rowdata=stepline.split("\\|");
        	String stepname=rowdata[0].trim();
        	String checkername=rowdata[1].trim();
        	String stepstatus=rowdata[2].trim();
        	String commentdata=rowdata[3].trim();
        	
        	String backgroundcolor="";
        	if(stepstatus.toLowerCase().contains("pass")){
        		backgroundcolor="background:#00B050;";
        	}
        	if(stepstatus.toLowerCase().contains("failed")){
        		backgroundcolor="background:#C00000;";
        	}
        	if(stepstatus.toLowerCase().contains("norun")){
        		backgroundcolor="background:#FFC000;";
        	}
        	if(stepstatus.toLowerCase().contains("warning")){
        		backgroundcolor="background:#FFC000;";
        	}
            
        String tablerow=""
+ " <tr style=\"mso-yfti-irow:"+(stepnumber+1)+";height:15.75pt\">"
+"  <td width=\"65\" valign=\"top\" style=\"width:49.05pt;border:solid windowtext 1.0pt;"
+"   border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;"
+"   padding:0in 0in 0in 0in;height:15.75pt\">"
+"   <p style=\"margin-left:0in;mso-add-space:auto\" class=\"MsoListParagraphCxSpFirst\"><b><span style=\"font-size:13.0pt;line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;"
+"   mso-fareast-font-family:  &quot;Times New Roman&quot;;mso-fareast-theme-font:major-fareast;mso-bidi-font-family:"
+"   &quot;Times New Roman&quot;;mso-bidi-theme-font:major-bidi\">"+stepnumber+"<o:p></o:p></span></b></p>"
+"   </td>"
+"   <td width=\"198\" valign=\"bottom\" style=\"width:148.55pt;border-top:none;border-left:"
+"   none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
+"   mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
+"   mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:15.75pt\">"
+"   <p style=\"margin-left:0in;mso-add-space:"
+"   auto\" class=\"MsoListParagraphCxSpMiddle\"><b><span style=\"font-size:13.0pt;line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;"
+"   mso-fareast-font-family:  &quot;Times New Roman&quot;;mso-fareast-theme-font:major-fareast;mso-bidi-font-family:"
+"   &quot;Times New Roman&quot;;mso-bidi-theme-font:major-bidi\">"+stepname+"<o:p></o:p></span></b></p>"
+"   </td>"
+"   <td width=\"433\" valign=\"bottom\" style=\"width:324.45pt;border-top:none;border-left:"
+"   none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
+"   mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
+"   mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:15.75pt\">"
+"   <p style=\"margin-left:0in;mso-add-space:"
+"   auto\" class=\"MsoListParagraphCxSpMiddle\"><span style=\"font-size:13.0pt;line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;"
+"   mso-fareast-font-family: &quot;Times New Roman&quot;;;mso-fareast-theme-font:major-fareast;mso-bidi-font-family:"
+"   &quot;Times New Roman&quot;;mso-bidi-theme-font:major-bidi;mso-bidi-font-weight:bold\">"+checkername+"<o:p></o:p></span></p>"
+"   </td>"
+"   <td width=\"323\" valign=\"bottom\" nowrap=\"\" style=\"width:242.55pt;border-top:none;"+backgroundcolor
+"   border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
+"   mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
+"   mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:15.75pt\">"
+"   <p style=\"margin-left:0in;mso-add-space:"
+"   auto\" class=\"MsoListParagraphCxSpMiddle\"><b><span style=\"font-size:13.0pt;line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;"
+"   mso-fareast-font-family: &quot;Times New Roman&quot;;mso-fareast-theme-font:major-fareast;mso-bidi-font-family:"
+"   &quot;Times New Roman&quot;;mso-bidi-theme-font:major-bidi\">"+stepstatus+"<o:p></o:p></span></b></p>"
+"   </td>"
+"   <td width=\"528\" valign=\"bottom\" nowrap=\"\" style=\"width:5.5in;border-top:none;"
+"   border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
+"   mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
+"   mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:15.75pt\">"
+"   <p style=\"margin-left:0in;mso-add-space:auto\" class=\"MsoListParagraphCxSpLast\"><span style=\"font-size:11.0pt;line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;"
+"   mso-fareast-font-family: &quot;Times New Roman&quot;;mso-fareast-theme-font:major-fareast;mso-bidi-font-family:"
+"   &quot;Times New Roman&quot;;mso-bidi-theme-font:major-bidi\">"+commentdata+"<o:p></o:p></span></p>"
+"   </td>"
+"   </tr>";
        
        steptable.append(tablerow);
       }
       stepbr.close();
/**************************************************************************************************************/
        //update the data value table
        
        Element valuetable=doc.select("table.MsoTable15Grid5DarkAccent5 tbody").first();
        BufferedReader valuebr=null;
        valuebr=new BufferedReader(new FileReader(outpuvaluetfile));
        String valueline=null;
        int valuenumber=0;
        while((valueline=valuebr.readLine())!=null){
        	valuenumber=valuenumber+1;
        	String[] rowdatavalue=valueline.split("\\|");
        	String datades=rowdatavalue[0];
        	String datausedvalue=rowdatavalue[1];
        String valuerow=""
+"  <tr style=\"mso-yfti-irow:0;height:18.2pt\">"
+"  <td width=\"269\" valign=\"top\" style=\"width:202.1pt;border:solid white 1.0pt;"
+"  mso-border-themecolor:background1;border-top:none;mso-border-top-alt:solid white .5pt;"
+"  mso-border-top-themecolor:background1;mso-border-alt:solid white .5pt;"
+"  mso-border-themecolor:background1;background:#4472C4;mso-background-themecolor:"
+"  accent5;padding:0in 5.4pt 0in 5.4pt;height:18.2pt\">"
+"  <p style=\"tab-stops:center 95.6pt;mso-yfti-cnfc:68\" class=\"MsoNormal\"><b><span style=\"font-size:9.0pt;line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;"
+"  mso-bidi-font-family:Arial;color:white;mso-themecolor:background1\">"+datades+"<o:p></o:p></span></b></p>"
+"  </td>"
+"  <td width=\"1278\" valign=\"top\" style=\"width:958.5pt;border-top:none;border-left:"
+"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
+"  mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
+"  mso-border-alt:solid windowtext .5pt;background:#B4C6E7;mso-background-themecolor:"
+"  accent5;mso-background-themetint:102;padding:0in 5.4pt 0in 5.4pt;height:18.2pt\">"
+"  <p style=\"mso-yfti-cnfc:64\" class=\"MsoNormal\"><span style=\"font-size:9.0pt;"
+"  line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;mso-bidi-font-family:Arial\">"+datausedvalue+"<o:p></o:p></span></p>"
+"  </td>"
+" </tr>";
        valuetable.append(valuerow);
        }
        valuebr.close();
        
/**************************************************************************************************************/
//this is to update the page loading time table,change the color
        Element pageloadingnode=doc.select("table.MsoTableGrid tbody").first();
        Element pageaveragenode=pageloadingnode.select("tr").get(1).select("td").get(1).select("p span").first();
        BufferedReader pagebr=null;
        pagebr=new BufferedReader(new FileReader(pageloadingfile));
        String pageline=null;
        int pagenumber=0;
        long pagetotal=0;
        while((pageline=pagebr.readLine())!=null){
        	pagenumber=pagenumber+1;
        	String[] rowdatavalue=pageline.split("\\|");
        	String pagename=rowdatavalue[0];
        	String pagetime=rowdatavalue[1];
        	long pagetimelong=Long.parseLong(pagetime);
        	pagetotal=pagetotal+pagetimelong;
        	long averagetime=pagetotal/pagenumber;
        	String pageloadrow=""
+ "<tr style=\"mso-yfti-irow:0;mso-yfti-lastrow:yes;height:24.5pt\">"
+ "<td width=\"431\" valign=\"top\" style=\"width:323.6pt;border-top:none;border-left:"
+ "solid white 1.0pt;mso-border-left-themecolor:background1;border-bottom:solid white 1.0pt;"
+ "  mso-border-bottom-themecolor:background1;border-right:solid white 1.5pt;"
+ "  mso-border-right-themecolor:background1;mso-border-top-alt:solid white .5pt;"
+ "  mso-border-top-themecolor:background1;mso-border-alt:solid white .5pt;"
+ "  mso-border-themecolor:background1;mso-border-right-alt:solid white 1.5pt;"
+ "  mso-border-right-themecolor:background1;background:#4472C4;mso-background-themecolor:"
+ "  accent5;padding:0in 5.4pt 0in 5.4pt;height:24.5pt\">"
+ "  <p style=\"mso-yfti-cnfc:68\" class=\"MsoNormal\"><span style=\"font-size:14.0pt;"
+ "  line-height:105%;color:white;mso-themecolor:background1;mso-bidi-font-weight:"
+ "  bold\">"+pagename+"<o:p></o:p></span></p>"
+ "  </td>"
+ "  <td width=\"588\" valign=\"top\" style=\"width:441pt;border-top:none;border-left:"
+ "  none;border-bottom:solid windowtext 1.0pt;border-right:solid white 1.0pt;"
+ "  mso-border-right-themecolor:background1;mso-border-top-alt:solid white .5pt;"
+ "  mso-border-top-themecolor:background1;mso-border-left-alt:solid windowtext 1.5pt;"
+ "  mso-border-top-alt:white .5pt;mso-border-top-themecolor:background1;"
+ "  mso-border-left-alt:windowtext 1.5pt;mso-border-bottom-alt:windowtext .5pt;"
+ "  mso-border-right-alt:white .5pt;mso-border-right-themecolor:background1;"
+ "  mso-border-style-alt:solid;background:#B4C6E7;mso-background-themecolor:accent5;"
+ "  mso-background-themetint:102;padding:0in 5.4pt 0in 5.4pt;height:24.5pt\">"
+ "  <p style=\"mso-yfti-cnfc:64\" class=\"MsoNormal\"><b style=\"mso-bidi-font-weight:"
+ "  normal\"><span style=\"font-size:14.0pt;line-height:105%\"><o:p>&nbsp;"+pagetime+"s</o:p></span></b></p>"
+ "  </td>"
+ " </tr>";
        	pageloadingnode.append(pageloadrow);
        	pageaveragenode.text(averagetime+"s");
        }
        pagebr.close();
/**************************************************************************************************************/
    	// this is the insert image code MsoTableMediumShading2Accent5
		Element imagetbodynode = doc.select("table.MsoTableMediumShading2Accent5 tbody").first();
		logger.debug("the image node is :" + imagetbodynode.text());


		int filesize = FilesUtils.getSubFilesSize(report_dir, ".png");
		File[] errorshotfile = FilesUtils.listFilesEndsWith(report_dir, ".png");
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
		FilesUtils.writeContents(reportfilename, sb.toString());
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
	
	public void updateStepHtml(String reportfile) throws IOException{
	    //the step table,this is the index 1
		StringBuilder sb=new StringBuilder();
		//String reportfile="abc.xml";
		Document doc = Jsoup.parse(new File(reportfile), "UTF-8");
        Element steptable=doc.select("table.MsoNormalTable").get(1).select("tbody").first();
        logger.debug("the table text is:"+steptable.text());
        String stepline="abc";
        int stepnumber=0;
        	stepnumber=stepnumber+1;
         	String[] rowdata=stepline.split("\\|");
        	String stepname=rowdata[0].trim();
        	String checkername=rowdata[1].trim();
        	String stepstatus=rowdata[2].trim();
        	String commentdata=rowdata[3].trim();
        	
        	String backgroundcolor="";
        	if(stepstatus.toLowerCase().contains("pass")){
        		backgroundcolor="background:#00B050;";
        	}
        	if(stepstatus.toLowerCase().contains("failed")){
        		backgroundcolor="background:#C00000;";
        	}
        	if(stepstatus.toLowerCase().contains("norun")){
        		backgroundcolor="background:#FFC000;";
        	}
        	if(stepstatus.toLowerCase().contains("warning")){
        		backgroundcolor="background:#FFC000;";
        	}
            
        String tablerow=""
+ " <tr style=\"mso-yfti-irow:"+(stepnumber+1)+";height:15.75pt\">"
+"  <td width=\"65\" valign=\"top\" style=\"width:49.05pt;border:solid windowtext 1.0pt;"
+"   border-top:none;mso-border-top-alt:solid windowtext .5pt;mso-border-alt:solid windowtext .5pt;"
+"   padding:0in 0in 0in 0in;height:15.75pt\">"
+"   <p style=\"margin-left:0in;mso-add-space:auto\" class=\"MsoListParagraphCxSpFirst\"><b><span style=\"font-size:13.0pt;line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;"
+"   mso-fareast-font-family:  &quot;Times New Roman&quot;;mso-fareast-theme-font:major-fareast;mso-bidi-font-family:"
+"   &quot;Times New Roman&quot;;mso-bidi-theme-font:major-bidi\">"+stepnumber+"<o:p></o:p></span></b></p>"
+"   </td>"
+"   <td width=\"198\" valign=\"bottom\" style=\"width:148.55pt;border-top:none;border-left:"
+"   none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
+"   mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
+"   mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:15.75pt\">"
+"   <p style=\"margin-left:0in;mso-add-space:"
+"   auto\" class=\"MsoListParagraphCxSpMiddle\"><b><span style=\"font-size:13.0pt;line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;"
+"   mso-fareast-font-family:  &quot;Times New Roman&quot;;mso-fareast-theme-font:major-fareast;mso-bidi-font-family:"
+"   &quot;Times New Roman&quot;;mso-bidi-theme-font:major-bidi\">"+stepname+"<o:p></o:p></span></b></p>"
+"   </td>"
+"   <td width=\"433\" valign=\"bottom\" style=\"width:324.45pt;border-top:none;border-left:"
+"   none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
+"   mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
+"   mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:15.75pt\">"
+"   <p style=\"margin-left:0in;mso-add-space:"
+"   auto\" class=\"MsoListParagraphCxSpMiddle\"><span style=\"font-size:13.0pt;line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;"
+"   mso-fareast-font-family: &quot;Times New Roman&quot;;;mso-fareast-theme-font:major-fareast;mso-bidi-font-family:"
+"   &quot;Times New Roman&quot;;mso-bidi-theme-font:major-bidi;mso-bidi-font-weight:bold\">"+checkername+"<o:p></o:p></span></p>"
+"   </td>"
+"   <td width=\"323\" valign=\"bottom\" nowrap=\"\" style=\"width:242.55pt;border-top:none;"+backgroundcolor
+"   border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
+"   mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
+"   mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:15.75pt\">"
+"   <p style=\"margin-left:0in;mso-add-space:"
+"   auto\" class=\"MsoListParagraphCxSpMiddle\"><b><span style=\"font-size:13.0pt;line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;"
+"   mso-fareast-font-family: &quot;Times New Roman&quot;;mso-fareast-theme-font:major-fareast;mso-bidi-font-family:"
+"   &quot;Times New Roman&quot;;mso-bidi-theme-font:major-bidi\">"+stepstatus+"<o:p></o:p></span></b></p>"
+"   </td>"
+"   <td width=\"528\" valign=\"bottom\" nowrap=\"\" style=\"width:5.5in;border-top:none;"
+"   border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;"
+"   mso-border-top-alt:solid windowtext .5pt;mso-border-left-alt:solid windowtext .5pt;"
+"   mso-border-alt:solid windowtext .5pt;padding:0in 5.4pt 0in 5.4pt;height:15.75pt\">"
+"   <p style=\"margin-left:0in;mso-add-space:auto\" class=\"MsoListParagraphCxSpLast\"><span style=\"font-size:11.0pt;line-height:105%;font-family:&quot;Cambria&quot;,&quot;serif&quot;;"
+"   mso-fareast-font-family: &quot;Times New Roman&quot;;mso-fareast-theme-font:major-fareast;mso-bidi-font-family:"
+"   &quot;Times New Roman&quot;;mso-bidi-theme-font:major-bidi\">"+commentdata+"<o:p></o:p></span></p>"
+"   </td>"
+"   </tr>";
        
        steptable.append(tablerow);
       }
      // stepbr.close();
		
	
	public void updateHtmlOverall(){
		
	}
	
}
