/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DateUtil{
	//this is not all possible formats - only ones I use
	public final static DateFormat DF_YearMonthDay=new SimpleDateFormat("yyyy-MM-dd");
	public final static DateFormat DF_Year=new SimpleDateFormat("yyyy");
	public final static DateFormat DF_YearMonthDayHourMinute=new SimpleDateFormat("yyyy-MM-dd-HH-mm");
	public final static DateFormat DF_YearMonthDayHourMinuteSecond=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static DateFormat DF_YearMonthDayHourMinuteSecondMillisecond=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
	public final static DateFormat DF_MDVersion=new SimpleDateFormat("yyyyMMdd-HH");
	
	public static String now(final DateFormat df){
		return(df.format(now()));
	}	

	public static String toString(long ms,final DateFormat df){
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(ms);
		Date date=calendar.getTime();
		return(df.format(date));
	}		
	
	public static String toString(Date date,final DateFormat df){
		return(df.format(date));
	}		
	
	public static Date now(){
		Calendar calendar=Calendar.getInstance();
		return(calendar.getTime());
	}
	
	public static int getCurrentMonth(){
		return(Calendar.getInstance().get(Calendar.MONTH));
	}
}