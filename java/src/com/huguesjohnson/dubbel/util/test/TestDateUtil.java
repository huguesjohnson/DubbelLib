/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.util.DateUtil;

class TestDateUtil{

	@Test
	void test(){
		TimeZone tz=TimeZone.getTimeZone("America/Chicago");
		Calendar calendar=Calendar.getInstance();
		long ms=612986766006L;
		calendar.setTimeInMillis(ms);
		calendar.setTimeZone(tz);
		Date date=calendar.getTime();
		/*
		 * test converting date to DF_YearMonthDay string
		 */
		DateFormat df=DateUtil.DF_YearMonthDay;
		df.setTimeZone(tz);
		assertEquals("1989-06-04",DateUtil.toString(date,df));
		/*
		 * test converting date to DF_Year string
		 */
		df=DateUtil.DF_Year;
		df.setTimeZone(tz);
		assertEquals("1989",DateUtil.toString(date,df));
		/*
		 * test converting date to DF_YearMonthDayHourMinute string
		 */
		df=DateUtil.DF_YearMonthDayHourMinute;
		df.setTimeZone(tz);
		assertEquals("1989-06-04-13-06",DateUtil.toString(date,df));
		/*
		 * test converting date to DF_YearMonthDayHourMinuteSecond string
		 */
		df=DateUtil.DF_YearMonthDayHourMinuteSecond;
		df.setTimeZone(tz);
		assertEquals("1989-06-04 13:06:06",DateUtil.toString(date,df));
		/*
		 * test converting date to DF_YearMonthDayHourMinuteSecondMillisecond string
		 */
		df=DateUtil.DF_YearMonthDayHourMinuteSecondMillisecond;
		df.setTimeZone(tz);
		assertEquals("1989-06-04-13-06-06-006",DateUtil.toString(date,df));
		/*
		 * test converting date to DF_MDVersion string
		 */
		df=DateUtil.DF_MDVersion;
		df.setTimeZone(tz);
		assertEquals("19890604-13",DateUtil.toString(date,df));
		/*
		 * test converting date to DF_YearMonthDay string
		 */
		df=DateUtil.DF_YearMonthDay;
		df.setTimeZone(tz);
		assertEquals("1989-06-04",DateUtil.toString(date,df));
		/*
		 * test converting date to DF_Year string
		 */
		df=DateUtil.DF_Year;
		df.setTimeZone(tz);
		assertEquals("1989",DateUtil.toString(ms,df));
		/*
		 * test converting date to DF_YearMonthDayHourMinute string
		 */
		df=DateUtil.DF_YearMonthDayHourMinute;
		df.setTimeZone(tz);
		assertEquals("1989-06-04-13-06",DateUtil.toString(ms,df));
		/*
		 * test converting date to DF_YearMonthDayHourMinuteSecond string
		 */
		df=DateUtil.DF_YearMonthDayHourMinuteSecond;
		df.setTimeZone(tz);
		assertEquals("1989-06-04 13:06:06",DateUtil.toString(ms,df));
		/*
		 * test converting date to DF_YearMonthDayHourMinuteSecondMillisecond string
		 */
		df=DateUtil.DF_YearMonthDayHourMinuteSecondMillisecond;
		df.setTimeZone(tz);
		assertEquals("1989-06-04-13-06-06-006",DateUtil.toString(ms,df));
		/*
		 * test converting date to DF_MDVersion string
		 */
		df=DateUtil.DF_MDVersion;
		df.setTimeZone(tz);
		assertEquals("19890604-13",DateUtil.toString(ms,df));
		/*
		 * test converting date to DF_WeekdayCommaDayMonthYearHourMinuteSecondTimezone string
		 */
		df=DateUtil.DF_RFC822;
		df.setTimeZone(tz);
		if(tz.getDSTSavings()>0){
			assertEquals("Sun, 04 Jun 1989 13:06:06 CDT",DateUtil.toString(ms,df));
		}else{
			assertEquals("Sun, 04 Jun 1989 13:06:06 CST",DateUtil.toString(ms,df));
		}
		df=DateUtil.DF_RFC822_OPML_ALT;
		df.setTimeZone(tz);
		if(tz.getDSTSavings()>0){
			assertEquals("Sun, 04 Jun 89 13:06:06 CDT",DateUtil.toString(ms,df));
		}else{
			assertEquals("Sun, 04 Jun 89 13:06:06 CST",DateUtil.toString(ms,df));
		}
		/*
		 * test epoch time conversions
		 */
		df=DateUtil.DF_YearMonthDay;
		df.setTimeZone(tz);
		assertEquals(0L,DateUtil.toEpochTime("",df));
		df=DateUtil.DF_YearMonthDayHourMinuteSecondMillisecond;
		df.setTimeZone(tz);
		assertEquals(ms,DateUtil.toEpochTime("1989-06-04-13-06-06-006",df));
		df=DateUtil.DF_YearMonthDayHourMinuteSecond;
		df.setTimeZone(tz);
		assertEquals(ms-6L,DateUtil.toEpochTime("1989-06-04 13:06:06",df));
	}
}