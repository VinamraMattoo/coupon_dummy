package com.portea.commp.smsen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);
    private static final String DAY_FORMAT = "EE";

    /**
     * Returns true if the elapsed time from a given date when compared to current time
     * is more than elapsedTimeInMilliSec
     */
    public static boolean hasElapsed(Date fromDate, Integer elapsedTimeInMilliSec) {

        Long remainingTime = getRemainingTime(fromDate, elapsedTimeInMilliSec);

        if (remainingTime > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static Long getRemainingTime(Date fromDate, Integer elapsedTimeInMilliSec) {
        Long fromTime = fromDate.getTime();
        Date currDate = new Date();
        Long currTime = currDate.getTime();
        return currTime - (fromTime + elapsedTimeInMilliSec);
    }

    public static boolean isValidDateFormat(String dateToValidate, String dateFromat){

        if(dateToValidate == null){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            sdf.parse(dateToValidate);
        } catch (ParseException e) {

            return false;
        }

        return true;
    }


    public static Date convertTimeZone(Date date, TimeZone toTz) {
        String format = "dd-M-yyyy HH:mm:ss";
        return convertTimeZone(date, format, toTz);
    }

    public static Date convertTimeZone(Date date, String format, TimeZone toTz) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        SimpleDateFormat toDateFormatter = new SimpleDateFormat(format);

        toDateFormatter.setTimeZone(toTz);

        String toDateFormat = toDateFormatter.format(date); // Convert to String first
        Date toDate;
        try {
            toDate = formatter.parse(toDateFormat);
        } catch (ParseException e) {
            LOG.warn("Unable to parse given timezone "+toTz+" using default timezone "+TimeZone.getDefault());
            toDate = new Date();
        }
        return toDate;
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static String getDateTime(Date date) {
        if (date == null) {
            return null;
        }
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String convertedDate = sdf.format(date);
        return convertedDate;
    }

    /**
     * Returns date in past after subtracting given days from fromDate.
     */
    public static Date priorDate(Date from, Integer days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(from);
        cal.add(Calendar.DATE, - days);
        return cal.getTime();
    }

    /**
     * Returns a specific day of a week for the given Date.
     * Example: mon, tue, etc.
     */
    public static String getDayForDate(Date lastUpdatedOn) {
        return new SimpleDateFormat(DAY_FORMAT).format(lastUpdatedOn);
    }
}
