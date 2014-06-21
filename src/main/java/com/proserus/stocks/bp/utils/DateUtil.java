package com.proserus.stocks.bp.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.proserus.stocks.bp.model.Filter;

public class DateUtil {

	private static final int NUMBER_OF_DAYS_PER_YEAR = 365;

	static public DateTime getFilteredStartDate(DateTime date, Filter filter){
		if(filter.isDateFiltered()){
			DateTime start = new DateTime(getStartOfYear(filter.getYear()));//TODO remove Joda
			if(date.isBefore(start)){
				date = start;
			}
		}
		return date;
	}
	
	static public DateTime getFilteredEndDate(Filter filter){
		if(filter.isDateFiltered()){
			DateTime endOfYear = new DateTime(getEndOfYear(filter.getYear()));//TODO remove Joda
			if(endOfYear.isBeforeNow()){
				return endOfYear;
			}
		}
		return new DateTime(Calendar.getInstance());//TODO remove Joda
	}
	
	static public int getFilteredYear(DateTime date, Filter filter){
		return getFilteredYear(date.getYear(),filter);
	}
	static public int getFilteredYear(int year, Filter filter){
		if(filter.isDateFiltered()){
			//TODO fix filter date
			if(year  < filter.getYear()){
				year = filter.getYear();
			}
		}
		return year;
	}
	
	static public int getCurrentYear(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	static public int getYear(Date date){
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
        return c.get(Calendar.YEAR);
    }
	
	static public int getNextYear(){
	    return DateUtil.getCurrentYear()+1;
	}
	
	static public boolean isFilteredForYear(DateTime year, Filter filter){
		if(filter.isDateFiltered()){
			if(year.getYear() != filter.getYear()){
				return false;
			}
		}
		return true;
	}
	
	static public int getFilteredYear(Filter filter){
		if(filter.isDateFiltered()){
			return filter.getYear();
		}else{
			return getCurrentYear();
		}
	}
	
	static public int getFilteredPreviousYear(Filter filter){
        return getFilteredYear(filter)-1;
    }
	
	
	
	static public double getDaysBetween(DateTime from, DateTime to){
		return Days.daysBetween(from, to).getDays();
	}
	
	static public double getYearsBetween(DateTime from, DateTime to){
		double value = getDaysBetween(from, to);
		if(value==0){
			return 0d;
		}
		return value / NUMBER_OF_DAYS_PER_YEAR;
	}
	
	static public Date stringToToDate(String pattern, String date){
		Format formatter = new SimpleDateFormat(pattern);
		try {
	        return (Date)formatter.parseObject(date);
        } catch (ParseException e) {
        	//TODO not good..
	        return null;
        }
	}
	
	
	static public Date getStartOfYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_YEAR, 1);
        c.set(Calendar.AM_PM, Calendar.AM);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
    
    static public Date getEndOfYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, 1);
        
        c.setTime(getStartOfYear(c.getTime()));
        
        c.add(Calendar.MILLISECOND, -1);
        return c.getTime();
    }
    
    static public Date getStartOfYear(int year){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        return getStartOfYear(c.getTime());
    }
    
    static public Date getEndOfYear(int year){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        return getEndOfYear(c.getTime());
    }
}
