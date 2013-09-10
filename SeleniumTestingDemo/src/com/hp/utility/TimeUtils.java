/**
 * Project Name:PAF_HC
 * File Name:TimeUtils.java
 * Package Name:com.hp.utility
 * Date:Sep 7, 201310:33:19 AM
 * Copyright (c) 2013, alterhu2020@gmail.com All Rights Reserved.
 *
*/

package com.hp.utility;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * ClassName:TimeUtils 
 * Function: TODO ADD FUNCTION. 
 * Reason:	 TODO ADD REASON. 
 * Date:     Sep 7, 2013 10:33:19 AM 
 * @author   huchan
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class TimeUtils {

	
	   public static Logger logger=Logger.getLogger(TimeUtils.class);
		/*
		 * get the total minutes between the two time
		 */
		public static String betweenTime(String starttime) {
			String betweentime = null;
			long startseconds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
					starttime, new ParsePosition(0)).getTime();
			long endseconds = new Date().getTime();
			logger.debug("the start time is :" + starttime + ",end time is:"
					+ new Date().toString());
			logger.debug("the start second is:" + startseconds
					+ ",the end seconds is:" + endseconds);
			long millonseconds = endseconds - startseconds;
			long totalhours = millonseconds / (1000 * 60 * 60);
			long totalminutes = millonseconds / (1000 * 60);
			long intervalminutes = totalminutes % 60;
			if (totalminutes > 60) {
				betweentime = String.valueOf(totalhours) + " hours "
						+ String.valueOf(intervalminutes) + " minutes";
			} else {
				logger.error("Sorry the end testing time is less than the testing start time");
				betweentime = String.valueOf(totalminutes) + " minutes";
			}

			return betweentime;

		}
		
		
		/**
		 * get the system's current time 
		 * @param date
		 * @return String the datetime
		 */
		public static String getCurrentTime(Date date){
			String currenttime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			logger.debug("Get current running time is :"+currenttime);
			return currenttime;
		}
		
		/**
		 * @param starttime
		 * @param endtime
		 * @return
		 */
		public static String timeLastMinutes(String starttime,String endtime) {
			long startseconds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
					starttime, new ParsePosition(0)).getTime();
			long endseconds =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
					endtime, new ParsePosition(0)).getTime();
			
			logger.debug("the start time is:" + starttime
					+ ",the end time is:" + endtime);
			long millonseconds = endseconds - startseconds;
			String totalminutes = String.valueOf(millonseconds / (1000 * 60));
		    logger.debug("the total took time is:"+totalminutes);
			return totalminutes;

		}
		
		/**
		 * get  the random number from 1 to 100000000
		 * @return the random number we need
		 */
		public static int getRandomNumber() {
			int minimum = 1;
			int maximum = 100000000;
			int returnvalue = minimum + (int) (Math.random() * maximum);
			return returnvalue;
		}
		
		
		/**
		  * get the monday
		  * 
		  * @return yyyy-MM-dd
		  */
		 public static String getMondayOfThisWeek() {
		  Calendar c = Calendar.getInstance();
		  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		  if (day_of_week == 0)
		     day_of_week = 7;
		  c.add(Calendar.DATE, -day_of_week + 1);
		  return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		 }
		 
		 /**
		  * get the sunday
		  * 
		  * @return yyyy-MM-dd
		  */
		 public static String getSundayOfThisWeek() {
		  Calendar c = Calendar.getInstance();
		  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		  if (day_of_week == 0)
		   day_of_week = 7;
		  c.add(Calendar.DATE, -day_of_week + 7);
		  return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		 }

		 
		public static List<String> getCurrentWeekDays()
		{
			 List<String> currentweek=new ArrayList<String>();
			 for(int kindex=1;kindex<=7;kindex++)
			 {
			     Calendar c = Calendar.getInstance();
			     int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
			     if (day_of_week == 0)
			         day_of_week = 7;
				 c.add(Calendar.DATE, -day_of_week+kindex);
				 String currentday=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
				 currentweek.add(currentday);
			 }
			return currentweek;
		}
		
		public static List<String> getCurrentMonthday()
		{
			List<String> currentmonth=new ArrayList<String>();
			Calendar c=Calendar.getInstance();
			int totalday=c.getActualMaximum(Calendar.DAY_OF_MONTH);
			System.out.println("current month total day is :"+totalday);
			c.set(Calendar.DAY_OF_MONTH, 1);
			System.out.println("first day is :"+new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));
			//currentmonth.add(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));
			for(int index=1;index<=totalday;index++)
			{
				c.set(Calendar.DAY_OF_MONTH, index);
				currentmonth.add(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));
			}
			return currentmonth;
		}
		public static String getFirstdayofMonth()
		{
		  Calendar c=Calendar.getInstance();
		  c.set(Calendar.DAY_OF_MONTH, 1);
		  return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
			
		}
		public static String getLastdayofMonth()
		{
			Calendar c=Calendar.getInstance();
			int totalday=c.getActualMaximum(Calendar.DAY_OF_MONTH);
			c.set(Calendar.DAY_OF_MONTH, totalday);
			System.out.println(c.getTime());
			return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		}
		
}

