package com.proserus.stocks.bp.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.proserus.stocks.bp.model.Filter;

public class DateUtils {

	private static final long MILLISECONDS_PER_DAY = 1000L * 60L * 60L * 24L;
	private static final int NUMBER_OF_DAYS_PER_YEAR = 365;

	static public int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	static public Calendar getTomorrow() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, 1);
		return c;
	}

	static public int getPreviousYear() {
		return getCurrentYear() - 1;
	}

	static public int getCalendarYear(Calendar val) {
		return val.get(Calendar.YEAR);
	}

	static public int getNextYear() {
		return DateUtils.getCurrentYear() + 1;
	}

	static public boolean isFilteredForYear(Calendar yearCalendar, Filter filter) {
		if (filter.isDateFiltered()) {
			if (yearCalendar.get(Calendar.YEAR) != filter.getYear()) {
				return false;
			}
		}
		return true;
	}

	static public int getFilteredYear(Filter filter) {
		if (filter.isDateFiltered()) {
			return filter.getYear();
		} else {
			return getCurrentYear();
		}
	}

	static public int getFilteredPreviousYear(Filter filter) {
		return getFilteredYear(filter) - 1;
	}

	static public double getDaysBetween(Calendar start, Calendar end) {
		long timeDifference = end.getTimeInMillis() - start.getTimeInMillis();
		return (double) timeDifference / (double) MILLISECONDS_PER_DAY;

		// DateTime dtStart = new DateTime(start);
		// DateTime dtEnd = new DateTime(end);
		// int jodaDays = Days.daysBetween(dtStart, dtEnd).getDays();
		// int remainingSec = Seconds.secondsBetween(dtStart,
		// dtEnd).getSeconds() - (jodaDays * SECONDS_PER_DAY);
		// double fractionSecondsInDay = Double.valueOf(remainingSec) /
		// SECONDS_PER_DAY;
		// return jodaDays + fractionSecondsInDay;
	}

	static public double getYearsBetween(Calendar start, Calendar end) {
		double value = getDaysBetween(start, end);
		if (value == 0) {
			return 0d;
		}
		return value / NUMBER_OF_DAYS_PER_YEAR;
	}

	static public Calendar stringToToCalendar(String pattern, String date) {
		Format formatter = new SimpleDateFormat(pattern);
		try {
			return DateUtils.getCalendar((Date) formatter.parseObject(date));
		} catch (ParseException e) {
			return null;
		}
	}

	static public Calendar getStartOfYear(Calendar c) {
		c.set(Calendar.DAY_OF_YEAR, 1);
		c.set(Calendar.AM_PM, Calendar.AM);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	static public Calendar getEndOfYear(Calendar calendar) {
		Calendar c = getStartOfYear(calendar);
		c.add(Calendar.YEAR, 1);
		c.add(Calendar.MILLISECOND, -1);
		return c;
	}

	static public Calendar getStartOfYear(int year) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		return getStartOfYear(c);
	}

	static public Calendar getEndOfYear(int year) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		return getEndOfYear(c);
	}

	public static Calendar getCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	public static String format(String format, Calendar calendar) {
		Format formatter = new SimpleDateFormat(format);
		return formatter.format(calendar.getTime());
	}
}
