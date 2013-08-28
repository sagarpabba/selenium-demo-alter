package com.hp.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class FileUtils {

	private static Logger logger = Logger.getLogger(FileUtils.class);

	private static final String RUN_RESULT_PATH = SeleniumCore.getProjectWorkspace() + "reporter"+ File.separator + "reporter.log";
	private static File logfile;

	public static boolean newFile(String filename) {
		File file = new File(filename);
		boolean iscreated;
		try {
			iscreated = file.createNewFile();
			if (iscreated) {
				return true;
			} else {
				return false;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public static boolean writeContents(String filepath, String Contents) {

		try {
			File file = new File(filepath);
			if (!file.exists()) {
				file.createNewFile();
			}
			logger.debug("New a reporter file content now ...");
			FileWriter writer;
			writer = new FileWriter(file);
			BufferedWriter bufferedwriter = new BufferedWriter(writer);
			logger.debug("now write the content into the html file...... ");
			bufferedwriter.write(Contents);

			bufferedwriter.close();
			writer.close();
			logger.debug("completed writing the email's html content now ");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public static String getValue(String begintext) {

		logfile = new File(RUN_RESULT_PATH);
		String result = null;
		FileReader reader = null;
		BufferedReader bufferedreader = null;
		if (!logfile.exists()) {
			logger.debug("we cannot find the reporter.log file so we return the found value is null");
			return null;
		}

		try {
			reader = new FileReader(logfile);
			bufferedreader = new BufferedReader(reader);
			String strline = null;
			while ((strline = bufferedreader.readLine()) != null) {
				if (strline.contains(begintext)) {
					logger.debug("the Search line content is " + strline);
					result = strline.split(">>>")[1].trim();
					break;
				}
			}
			bufferedreader.close();
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}

		return result;

	}

	public static int isFilesExisting(String directory,
			final String filenamefilter) {
		File dir = new File(directory);
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(filenamefilter);
			}
		};
		File[] errorshotfile = dir.listFiles(filter);
		int filesize = errorshotfile.length;
		if (filesize > 0) {
			return filesize;
		} else {
			return 0;
		}
	}

	public static File[] listFiles(String directory, final String filenamefilter) {
		File dir = new File(directory);
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(filenamefilter);
			}
		};
		File[] errorshotfile = dir.listFiles(filter);
		int filesize = errorshotfile.length;
		if (filesize > 0) {
			return errorshotfile;
		} else {
			return null;
		}

	}

	public static int textTimes(String searchtext) {

		logfile = new File(RUN_RESULT_PATH);
		String result = null;
		FileReader reader = null;
		BufferedReader bufferedreader = null;

		int textcount = 0;
		if (!logfile.exists()) {
			return 0;
		}

		try {
			reader = new FileReader(logfile);
			bufferedreader = new BufferedReader(reader);
			String strline = "";
			while ((strline = bufferedreader.readLine()) != null) {
				if (!strline.equals("")) {
					result = strline.split(":")[1].toLowerCase().trim();
					if (result.contains(searchtext)) {
						textcount = textcount + 1;
					}
				}

			}

			bufferedreader.close();
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return 0;
		}

		return textcount;
	}

	public static String returnFileContents(String filepath) {
		StringBuffer htmlcontent = new StringBuffer("");
		BufferedReader file = null;

		try {
			file = new BufferedReader(new InputStreamReader(
					new FileInputStream(filepath)));
			String strline = null;
			while ((strline = file.readLine()) != null) {
				htmlcontent.append(strline);
				// logger.debug("now read the  file,this line content is : "+strline);
			}
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("we cannot find this  file :" + filepath);
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		//
		return htmlcontent.toString();
	}
	public static List<String> getSubFiles(String listfolder){
		List<String> filelists=new ArrayList<String>();
		File folder=new File(listfolder);
		File[] files=folder.listFiles();
		for(File subfile:files){
			String filenamepath=subfile.getAbsolutePath();
			filelists.add(filenamepath);
		}	
		return filelists;
	}
}
