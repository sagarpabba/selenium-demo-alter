package com.hp.utility;


import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TimeUtils {
	
	
	public static String toString(Date datetime){
		
		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date (month/day/year)
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");

		// Get the date today using Calendar object.
		//Date today = Calendar.getInstance().getTime();        
		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		String strdate = df.format(datetime);
		return strdate;
	}
	
	public static String formatTime(String datetime){
		Date newdate=null;
		try {
			newdate =new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(datetime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newdate);
	}
	/**
	 * get the system's current time 
	 * @param date
	 * @return String the datetime
	 */
	public static String getCurrentTime(Date date){
		String currenttime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		System.out.println("Get current running time is :"+currenttime);
		return currenttime;
	}
	
	/**
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	/**
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public static String howManyMinutes(String starttime,String endtime) {
		long startseconds = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
				starttime, new ParsePosition(0)).getTime();
		String parseendtime;
		System.out.println("start time is :"+starttime+",end time is:"+endtime);
		if(endtime.trim()=="0"){
			
			parseendtime=starttime;
		}else{
			parseendtime=endtime;
		}
		System.out.println("then end time is :"+parseendtime);
		long endseconds =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
				parseendtime, new ParsePosition(0)).getTime();
		
		System.out.println("the start time is:" + starttime
				+ ",the end time is:" + endtime);
		long millonseconds = endseconds - startseconds;
		String totalminutes = String.valueOf(millonseconds / (1000 * 60))+" Minute(s)";
	    System.out.println("the total took time is:"+totalminutes);
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

	 
	/**
	 * get current week days as a list object
	 * @return
	 */
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
	
	/**
	 * get currently month's days as a list object
	 * @return
	 */
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
	/**
	 * get the first day of current month
	 * @return
	 */
	public static String getFirstdayofMonth()
	{
	  Calendar c=Calendar.getInstance();
	  c.set(Calendar.DAY_OF_MONTH, 1);
	  return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		
	}
	/**
	 * get the last day of current month
	 * @return
	 */
	public static String getLastdayofMonth()
	{
		Calendar c=Calendar.getInstance();
		int totalday=c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DAY_OF_MONTH, totalday);
		System.out.println(c.getTime());
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}
	
}
