package com.proserus.stocks.bp.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Seconds;
import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {

	private static final int DAYS_DIFF_BETWEEN_DATES_368 = 368;
	private static final int SECONDS_PER_DAY = 24 * 60 * 60;

	@Test
	public void getStartOfYearTest() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2008);
		c = DateUtils.getStartOfYear(c);
		Assert.assertEquals(2008, c.get(Calendar.YEAR));
		Assert.assertEquals(0, c.get(Calendar.MONTH));
		Assert.assertEquals(Calendar.JANUARY, c.get(Calendar.MONTH));
		Assert.assertEquals(1, c.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(1, c.get(Calendar.DAY_OF_YEAR));
		Assert.assertEquals(0, c.get(Calendar.HOUR));
		Assert.assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(0, c.get(Calendar.MINUTE));
		Assert.assertEquals(0, c.get(Calendar.SECOND));
		Assert.assertEquals(0, c.get(Calendar.MILLISECOND));
	}

	@Test
	public void beforeStartIsPreviousYearTest() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2008);
		c = DateUtils.getStartOfYear(c);
		c.add(Calendar.MILLISECOND, -1);
		Assert.assertEquals(2007, c.get(Calendar.YEAR));
		c.add(Calendar.MILLISECOND, 1);
		Assert.assertEquals(2008, c.get(Calendar.YEAR));
	}

	@Test
	public void afterEndIsNextYearTest() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2008);
		c = DateUtils.getEndOfYear(c);
		c.add(Calendar.MILLISECOND, 1);
		Assert.assertEquals(2009, c.get(Calendar.YEAR));
		c.add(Calendar.MILLISECOND, -1);
		Assert.assertEquals(2008, c.get(Calendar.YEAR));
	}

	@Test
	public void getEndOfYearTest() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 2008);
		c = DateUtils.getEndOfYear(c);
		Assert.assertEquals(2008, c.get(Calendar.YEAR));
		Assert.assertEquals(11, c.get(Calendar.MONTH));
		Assert.assertEquals(Calendar.DECEMBER, c.get(Calendar.MONTH));
		Assert.assertEquals(366, c.get(Calendar.DAY_OF_YEAR));
		Assert.assertEquals(11, c.get(Calendar.HOUR));
		Assert.assertEquals(Calendar.PM, c.get(Calendar.AM_PM));
		Assert.assertEquals(23, c.get(Calendar.HOUR_OF_DAY));
		Assert.assertEquals(59, c.get(Calendar.MINUTE));
		Assert.assertEquals(59, c.get(Calendar.SECOND));
		Assert.assertEquals(999, c.get(Calendar.MILLISECOND));
	}

	@Test
	public void getDaysBetweenTest() {

		Calendar start = Calendar.getInstance();
		start.set(Calendar.YEAR, 2008);
		start = DateUtils.getStartOfYear(start);

		Calendar end = Calendar.getInstance();
		end.set(Calendar.YEAR, 2008);
		end = DateUtils.getEndOfYear(end);

		assertEquals(366, (int) Math.ceil(DateUtils.getDaysBetween(start, end)));

		start.set(Calendar.DAY_OF_YEAR, 11);
		start.set(Calendar.MONTH, 3);
		start.set(Calendar.YEAR, 2007);
		start.set(Calendar.HOUR, 21);
		start.set(Calendar.MINUTE, 30);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		start.set(Calendar.AM_PM, Calendar.PM);

		end.set(Calendar.DAY_OF_YEAR, 15);
		end.set(Calendar.MONTH, 4);
		end.set(Calendar.YEAR, 2008);
		end.set(Calendar.HOUR, 10);
		end.set(Calendar.MINUTE, 15);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);
		end.set(Calendar.AM_PM, Calendar.AM);

		DateTime dtStart = new DateTime(start);
		DateTime dtEnd = new DateTime(end);
		int jodaDays = Days.daysBetween(dtStart, dtEnd).getDays();
		int remainingSec = Seconds.secondsBetween(dtStart, dtEnd).getSeconds() - (DAYS_DIFF_BETWEEN_DATES_368 * SECONDS_PER_DAY);
		double jodaFractionSecondsInDay = Double.valueOf(remainingSec) / SECONDS_PER_DAY;

		assertTrue(DateUtils.getDaysBetween(start, end) == 368.03125);
		assertTrue((jodaDays + jodaFractionSecondsInDay) == 368.03125);
	}
	// getCurrentDate()
	// getFilteredEndDate(Filter)
	// getFilteredYear(int, Filter)
	// getCurrentYear()
	// getNextYear()
	// getFilteredYear(Filter)
	// getFilteredPreviousYear(Filter)
	// stringToToDate(String, String)
	// getStartOfYear(int)
	// getEndOfYear(int)

}
