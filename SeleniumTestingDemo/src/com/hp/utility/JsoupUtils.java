package com.hp.utility;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

	static String projectpath = SeleniumCore.getProjectWorkspace();

	static File inputfile = new File(projectpath + "resources" + separator
			+ "result_template.htm");
	private static final String TODAY_REPORT_DIR = projectpath + "reporter";
	private final static String CURRENT_TIME = new SimpleDateFormat(
			"yyyy-MM-dd").format(Calendar.getInstance().getTime());
	private final static String CURRENT_RUN_TIME = new SimpleDateFormat(
			"EEE,MMM dd HH:mm:ss z").format(Calendar.getInstance().getTime());
	private final static String TODAY_REPORT = TODAY_REPORT_DIR
			+ File.separator + "TestReporter" + "_" + CURRENT_TIME + ".htm";

	/**
	 * updateContents:(Here describle the usage of this function). 
	 *
	 * @author huchan
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @since JDK 1.6
	 */
	public static String updateContents() throws IOException, ParseException {
		StringBuilder builder = new StringBuilder();
		Document doc = Jsoup.parse(inputfile, "UTF-8");

		// find the second table for the test steps the third row is index 2
		Elements stepnode = doc.select("table.MsoNormalTable tbody tr");
		
		int stepsize = stepnode.size();
		int passcases=0;
		int failedcases=0;
		
		logger.debug("Find the step table rows are:" + (stepsize - 2 - 2));
		for (int stepindex = 4; stepindex < stepsize; stepindex++) {
			// String stephtml=stepnode.get(stepindex).outerHtml();
			// logger.debug("the current step node row's html code are:\n"+stephtml);
			// the status node in table
			Element statusnode = stepnode.get(stepindex).select("td:eq(3)")
					.first();
			String originalstatus = statusnode.text().trim();
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
		String taketime = SeleniumCore.betweenTime(starttime);
		String runtime = "Start time:" + changedtime + "\n End time:"
				+ CURRENT_RUN_TIME + "\ntotal time:" + taketime;

		int totalcases = stepsize - 2 - 2;
		//int passcases = FileUtils.textTimes("pass");
		//int failedcases = FileUtils.textTimes("fail");
		int noruncases = totalcases - passcases - failedcases;
		totalrun.add(runtime);
		totalrun.add(String.valueOf(totalcases));
		totalrun.add(String.valueOf(passcases));
		totalrun.add(String.valueOf(failedcases));
		totalrun.add(String.valueOf(noruncases));

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
		File[] errorshotfile = FileUtils.listFiles(TODAY_REPORT_DIR, ".png");
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

}
