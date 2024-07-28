/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.util.DateUtil;

class TestDateUtil{

	@Test
	void test(){
		Calendar calendar=Calendar.getInstance();
		long ms=612986766006L;
		calendar.setTimeInMillis(ms);
		Date date=calendar.getTime();
		assertEquals("1989-06-04",DateUtil.toString(date,DateUtil.DF_YearMonthDay));
		assertEquals("1989",DateUtil.toString(date,DateUtil.DF_Year));
		assertEquals("1989-06-04-13-06",DateUtil.toString(date,DateUtil.DF_YearMonthDayHourMinute));
		assertEquals("1989-06-04 13:06:06",DateUtil.toString(date,DateUtil.DF_YearMonthDayHourMinuteSecond));
		assertEquals("1989-06-04-13-06-06-006",DateUtil.toString(date,DateUtil.DF_YearMonthDayHourMinuteSecondMillisecond));
		assertEquals("19890604-13",DateUtil.toString(date,DateUtil.DF_MDVersion));
		assertEquals("1989-06-04",DateUtil.toString(date,DateUtil.DF_YearMonthDay));
		assertEquals("1989",DateUtil.toString(ms,DateUtil.DF_Year));
		assertEquals("1989-06-04-13-06",DateUtil.toString(ms,DateUtil.DF_YearMonthDayHourMinute));
		assertEquals("1989-06-04 13:06:06",DateUtil.toString(ms,DateUtil.DF_YearMonthDayHourMinuteSecond));
		assertEquals("1989-06-04-13-06-06-006",DateUtil.toString(ms,DateUtil.DF_YearMonthDayHourMinuteSecondMillisecond));
		assertEquals("19890604-13",DateUtil.toString(ms,DateUtil.DF_MDVersion));
		assertEquals(0L,DateUtil.toEpochTime("",DateUtil.DF_YearMonthDay));
		assertEquals(ms,DateUtil.toEpochTime("1989-06-04-13-06-06-006",DateUtil.DF_YearMonthDayHourMinuteSecondMillisecond));
		assertEquals(ms-6L,DateUtil.toEpochTime("1989-06-04 13:06:06",DateUtil.DF_YearMonthDayHourMinuteSecond));
	}
}