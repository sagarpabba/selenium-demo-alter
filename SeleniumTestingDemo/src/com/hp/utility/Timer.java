package com.hp.utility;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class Timer {
	
	
	
	
	private static Logger logger=Logger.getLogger(Timer.class);
	
	/*
	 * get the total minutes between the two time
	 */
	public static String betweenTime(String starttime){
		String betweentime=null;
		long startseconds=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").parse(starttime,new ParsePosition(0)).getTime();
		long endseconds=new Date().getTime();
		logger.debug("the start time is :"+starttime+",end time is:"+new Date().toString());
		logger.debug("the start second is:"+startseconds+",the end seconds is:"+endseconds);
		long millonseconds=endseconds-startseconds;
		long totalhours=millonseconds/(1000*60*60);
		long totalminutes=millonseconds/(1000*60);
		long intervalminutes=totalminutes%60;
		if(totalminutes>60){	
		   betweentime=String.valueOf(totalhours)+" hours "+String.valueOf(intervalminutes)+" minutes";
		}
		else
		{
			logger.error("Sorry the end testing time is less than the testing start time");
			betweentime= String.valueOf(totalminutes)+"minutes";
		}
		
		return betweentime;
		
	}

}
