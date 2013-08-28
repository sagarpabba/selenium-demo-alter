package com.hp.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class Resulter {

	// log a result status for the test case execution
	private static Logger logger = Logger.getLogger(Resulter.class);
	private static File logdir = null;
	private static String reporterpath =SeleniumCore.getProjectWorkspace()+"reporter";


	// log a status
	public static void log(String title, String result) {
		try {
			logdir = new File(reporterpath);
			if (!logdir.exists()) {
				logdir.mkdir();
			}

			FileWriter writer = new FileWriter(logdir + File.separator
					+ "reporter.log", true);
			BufferedWriter bufferwriter = new BufferedWriter(writer);
			bufferwriter.write(title + ">>>" + result + "\n");
			logger.debug("Write the test case exeuction:[" + title + "\t\t"
					+ result + "] into the log file: "
					+ logdir.getAbsolutePath() + "\n");

			bufferwriter.close();
		} catch (IOException e) {
			logger.error("Sorry Met the IOException for the reporter file :"
					+ logdir.getAbsolutePath() + " the error Exception is :"
					+ e.getMessage());

		}
	}

}
