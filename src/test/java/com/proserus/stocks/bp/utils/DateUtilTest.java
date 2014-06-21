package com.proserus.stocks.bp.utils;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {

    @Test
    public void getStartOfYearTest(){
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR, 2008);
       Date date = DateUtil.getStartOfYear(c.getTime());
       c = Calendar.getInstance();
       c.setTime(date);
       Assert.assertEquals(2008, c.get(Calendar.YEAR));
       Assert.assertEquals(0, c.get(Calendar.MONTH));
       Assert.assertEquals(Calendar.JANUARY, c.get(Calendar.MONTH));
       Assert.assertEquals(1, c.get(Calendar.DAY_OF_YEAR));
       Assert.assertEquals(0, c.get(Calendar.HOUR));
       Assert.assertEquals(0, c.get(Calendar.HOUR_OF_DAY));
       Assert.assertEquals(0, c.get(Calendar.MINUTE));
       Assert.assertEquals(0, c.get(Calendar.SECOND));
       Assert.assertEquals(0, c.get(Calendar.MILLISECOND));
       
       c.add(Calendar.MILLISECOND, -1);
       Assert.assertEquals(2007, c.get(Calendar.YEAR));
       c.add(Calendar.MILLISECOND, 1);
       Assert.assertEquals(2008, c.get(Calendar.YEAR));
   }
    
    @Test
    public void getEndOfYearTest(){
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR, 2008);
       Date date = DateUtil.getEndOfYear(c.getTime());
       c = Calendar.getInstance();
       c.setTime(date);
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
       
       c.add(Calendar.MILLISECOND, 1);
       Assert.assertEquals(2009, c.get(Calendar.YEAR));
       c.add(Calendar.MILLISECOND, -1);
       Assert.assertEquals(2008, c.get(Calendar.YEAR));
   }
    
//    getCurrentDate()
//    getFilteredStartDate(DateTime, Filter)
//    getFilteredEndDate(Filter)
//    getFilteredYear(DateTime, Filter)
//    getFilteredYear(int, Filter)
//    getCurrentYear()
//    getYear(Date)
//    getNextYear()
//    isFilteredForYear(DateTime, Filter)
//    getFilteredYear(Filter)
//    getFilteredPreviousYear(Filter)
//    getDaysBetween(DateTime, DateTime)
//    getYearsBetween(DateTime, DateTime)
//    stringToToDate(String, String)
//    getStartOfYear(Date)
//    getEndOfYear(Date)
//    getStartOfYear(int)
//    getEndOfYear(int)

}
