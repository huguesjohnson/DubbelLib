/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class DateUtil{
	//this is not all possible formats - only ones I use
	public final static DateFormat DF_YearMonthDay=new SimpleDateFormat("yyyy-MM-dd");
	public final static DateFormat DF_Year=new SimpleDateFormat("yyyy");
	public final static DateFormat DF_YearMonthDayHourMinute=new SimpleDateFormat("yyyy-MM-dd-hh-mm");
	public final static DateFormat DF_YearMonthDayHourMinuteSecond=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static DateFormat DF_YearMonthDayHourMinuteSecondMillisecond=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");

	public static String now(final DateFormat df){
		Calendar calendar=Calendar.getInstance();
		return(df.format(calendar.getTime()));
	}	
}