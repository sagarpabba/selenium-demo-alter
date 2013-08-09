package com.hp.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class Resulter {
	
	
	//log a result status for the test case execution
	private static Logger logger=Logger.getLogger(Resulter.class);
	private static File logfile=null;
	public static void log(String title,String result)
	{
		try{
		   String filepath=System.getProperty("user.dir")+"\\reporter\\reporter.log";
		   logfile=new File(filepath);
		   if(!logfile.exists()){
			  logfile.createNewFile();
		   }
		  
		   FileWriter writer=new FileWriter(logfile, true);
		   BufferedWriter bufferwriter=new BufferedWriter(writer);
		   bufferwriter.write(title+":"+result+"\n");
		   logger.debug("Write the test case exeuction:["+title+"\t\t"+result+"] into the log file: "+logfile.getAbsolutePath()+"\n");
		   
		   bufferwriter.close();
		}
		catch(IOException  e)
		{
			logger.error("Sorry cannot generate the reporter file in this path:"+logfile.getAbsolutePath()+" the error Exception is :"+e.getMessage());
			
		}
	}

}
