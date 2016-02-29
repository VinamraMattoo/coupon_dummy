package com.portea.cpnen.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static Date getCurrentUtcDate() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);
        format.setTimeZone(TimeZoneEnum.UTC.getTimeZone());
        String formatted = format.format(date);
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            return simpleDateFormat.parse(formatted);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
